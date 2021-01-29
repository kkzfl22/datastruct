package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare;

import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.common.DataStatistics;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.entity.DataCompareRsp;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.ManyFileReader;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.threadpool.ScheduleTaskThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 文件多线程执行对比的流程,并且线程是带返回值的操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class FileTaskCallableCompare {

  /** 等待的数量 */
  private static final int QUEUE_WAIT_NUM = 16;

  /** 增量输出一次的处理点 */
  private static final long INCREMENT_PRINT = 1000000;

  /**
   * 读取数据的流程处理
   *
   * @param reader 流程读取器
   * @param dataParse 转换函数
   * @param runTaskMethod 线程内运行的函数
   */
  public static <V> void readerDataProc(
      ManyFileReader reader,
      DataParseInf<V> dataParse,
      Function<List<V>, DataCompareRsp> runTaskMethod,
      Consumer<DataCompareRsp> dataOutput) {

    // 统计计数器
    DataStatistics statistics = new DataStatistics(INCREMENT_PRINT);

    // 重新加载操作
    reader.reload();
    // 打开流程
    reader.openFile();

    ConcurrentLinkedQueue<Future> rsp = new ConcurrentLinkedQueue<>();
    try {
      System.out.println("处理开始...");
      while (true) {
        List<String> dataList = reader.readLineList();
        if (null == dataList || dataList.isEmpty()) {
          break;
        }

        // 异步调用数据处理
        Future<?> future = runTask(dataParse, dataList, runTaskMethod);

        // 当超过队列大小后则，必须待最前一个完成。
        if (rsp.size() > QUEUE_WAIT_NUM) {
          // 获取半移除队列的头
          Future item = rsp.poll();
          // 任务响应处理
          taskRspProc(item, dataOutput);
        }
        // 否则可直接加入到队列中
        else {
          // 将数据加入队列尾部
          rsp.add(future);
        }

        // 统计操作
        statistics.dataAdd(dataList.size());
      }

      // 必须所有都完成
      for (Future item : rsp) {
        // 任务响应处理
        taskRspProc(item, dataOutput);
      }
      System.out.println("处理结束...");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      reader.close();
    }
  }

  /**
   * 任务的响应处理
   *
   * @param item 响应信息
   * @param runMethod 完成后执行的方法
   * @param <V> 泛型对象
   */
  private static <V> void taskRspProc(Future item, Consumer<DataCompareRsp> runMethod) {
    // 由于此处单个任务失败，不能影响其他的，帮需要单个异常处理，流程继续
    try {
      // 从线程是获取输出,由于是队列，可保证数据的有序性
      DataCompareRsp dataBatch = (DataCompareRsp) item.get();
      // 当完成后之后，将数据进行输出到文件操作
      runMethod.accept(dataBatch);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

  /**
   * 任务的的异步提交流程
   *
   * @param dataParse 转换函数
   * @param dataList 读取到的数据集合
   */
  private static <V> Future<?> runTask(
      DataParseInf<V> dataParse,
      List<String> dataList,
      Function<List<V>, DataCompareRsp> runMethod) {

    // 以线程池的方式并行运算
    Future<?> future =
        ScheduleTaskThreadPool.INSTANCE.submit(
            () -> {
              List<V> dataBatch = new ArrayList<>(dataList.size());
              for (String dataLine : dataList) {
                dataBatch.add(dataParse.lineToData(dataLine));
              }
              // 执行数据处理
              return runMethod.apply(dataBatch);
            });

    return future;
  }
}
