package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare;

import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.DataCompare;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.DataCompareFileOutput;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.entity.DataCompareRsp;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.fileoperator.ManyFileReader;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.threadpool.ScheduleTaskThreadPool;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.updfile.BigFileSort;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.updfile.FileSortMerge;
import com.liujun.datastruct.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * 大型文件比较
 *
 * @author liujun
 * @version 0.0.1
 */
public class BigFileCompare<V> {

  /** 抽样数 */
  private static final int SAMPLE_NUM = 10;

  /** 等待的数量 */
  private static final int QUEUE_WAIT_NUM = 16;

  /** 增量输出一次的处理点 */
  private static final long INCREMENT_PRINT = 1000000;

  /** 数据条数 */
  private long dataNum;

  /** 打输输出的阈值，默认100万输出一次 */
  private long threshold = INCREMENT_PRINT;

  /** 开始运行的时间 */
  private long startTime = System.currentTimeMillis();

  /** 区间计时 */
  private long scopeTime = System.currentTimeMillis();

  /**
   * 执行源文件的比较
   *
   * @param inputEntity 输入对比的相关信息
   * @param compareKey 对比相关的函数
   * @return 对比结果 true 对比成功 false 对比失败
   */
  public boolean fileCompare(
      BigFileCompareInputEntity inputEntity,
      BigCompareKeyInf compareKey,
      DataParseInf<V> dataParse) {
    if (inputEntity == null || compareKey == null) {
      return false;
    }

    // 1,获得对比的原始文件
    ManyFileReader readSrc = new ManyFileReader(inputEntity.getSrcPath());
    ManyFileReader readTarget = new ManyFileReader(inputEntity.getTargetPath());

    // 执行数据对比的操作
    DataCompare compare =
        new DataCompare(
            compareKey,
            dataParse,
            countSumNum(inputEntity.getSrcPath()),
            countSumNum(inputEntity.getTargetPath()),
            null);

    // 读取源文件，加载到对比
    putSrcData(readSrc, compare, dataParse);
    // 读取目标数据，加载对比
    putTargetData(readTarget, compare, dataParse);

    // 输出操作
    DataCompareFileOutput output = new DataCompareFileOutput(inputEntity.getCompareOutPath());
    try {
      // 操作前需打开输出文件
      output.openWriteFile();
      // 执行数据的对比操作
      putSrcDataCompare(readSrc, compare, dataParse, output);
      putTargetDataCompare(readTarget, compare, dataParse, output);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // 执行相关的对比输出的关闭操作
      output.close();
    }

    // 修改文件的对比并有序输出
    this.updateDataOut(output, dataParse);

    return true;
  }

  /**
   * 统计总行数
   *
   * @param path 路径
   * @return 估算总行数
   */
  protected long countSumNum(String path) {
    File[] fileList = FileUtils.getFileList(path);
    if (fileList.length < 1) {
      return 0;
    }
    // 统计总大小
    long sumTotalSize = this.countFileSize(fileList);
    // 统计得到单行大小
    long lineSize = this.countLineDataNum(fileList);
    return sumTotalSize / lineSize;
  }

  /**
   * 统计总文件大小
   *
   * @param fileList
   * @return
   */
  private long countFileSize(File[] fileList) {
    long sumTotal = 0;
    for (File item : fileList) {
      sumTotal += item.length();
    }
    return sumTotal;
  }

  /**
   * 采用抽样的方式，计算得单行大小
   *
   * @return 平均大小
   */
  private long countLineDataNum(File[] fileList) {

    long avgLength = 0;
    for (File item : fileList) {
      List<String> dataList = FileUtils.readTop(item.getPath(), SAMPLE_NUM);
      avgLength += this.averageSize(dataList);
    }

    return avgLength / (long) fileList.length;
  }

  /**
   * 统计一个平均值
   *
   * @param dataSize
   * @return
   */
  private long averageSize(List<String> dataSize) {
    if (null == dataSize || dataSize.isEmpty()) {
      return 0;
    }

    long sumNum = 0;
    for (String item : dataSize) {
      sumNum += item.getBytes().length;
    }

    return sumNum / (long) dataSize.size();
  }

  /**
   * 数据修改输出
   *
   * @param output 输出内容
   * @param dataParse 转换函数
   */
  protected void updateDataOut(DataCompareFileOutput output, DataParseInf<V> dataParse) {
    // 2,当对比完成后，需要对数据做进一步的处理，由于现在数据是无序的，让两个修改的文件变得有序的。
    // 修改之前的文件排序
    String beforeSortPath = fileSort(output.getUpdBeforeDirPath(), dataParse);
    // 修改之后的文件排序
    String afterSortPath = fileSort(output.getUpdAfterDirPath(), dataParse);

    // 进行最后的文件输出
    FileSortMerge merge =
        new FileSortMerge(beforeSortPath, afterSortPath, output.getUpdPath(), output.getUpdName());
    // 相关的数据合并操作
    merge.merge();

    // 删除用于对比的时间文件
    FileUtils.deleteDir(beforeSortPath);
    FileUtils.deleteDir(afterSortPath);
    FileUtils.deleteDir(output.getUpdBeforeDirPath());
    FileUtils.deleteDir(output.getUpdAfterDirPath());
  }

  /**
   * 文件排序操作
   *
   * @param fileSortPath 文件路径
   * @param dataParse 对比函数
   */
  private String fileSort(String fileSortPath, DataParseInf<V> dataParse) {
    BigFileSort fileSort = new BigFileSort(fileSortPath, dataParse);
    // 执行大文件排序操作
    return fileSort.bigFileSort();
  }

  /**
   * 将原始数据放入到对比相关文件中
   *
   * @param readSrc 读取的原始文件中
   * @param compare 对比处理逻辑
   */
  protected void putSrcData(
      ManyFileReader readSrc, DataCompare compare, DataParseInf<V> dataParse) {
    this.readDataProc(readSrc, dataParse, compare::putSrcList);
  }

  /**
   * 将目标数据装载到布隆过滤器中
   *
   * @param readTarget
   * @param compare
   */
  protected void putTargetData(
      ManyFileReader readTarget, DataCompare compare, DataParseInf<V> dataParse) {
    this.readDataProc(readTarget, dataParse, compare::putTargetList);
  }

  /**
   * 将原始数据放入到对比相关文件中
   *
   * @param readSrc 读取的原始文件中
   * @param compare 对比处理逻辑
   */
  protected void putSrcDataCompare(
      ManyFileReader readSrc,
      DataCompare compare,
      DataParseInf<V> dataParse,
      DataCompareFileOutput compareOutput) {
    // 执行数据的并行处理
    this.readDataProc(
        readSrc,
        dataParse,
        (data) -> {
          DataCompareRsp rsp = compare.compareSrcList(data);
          try {
            // 数据输出操作
            compareOutput.dataLineWrite(rsp);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  /**
   * 将目标数据装载到布隆过滤器中
   *
   * @param readTarget
   */
  protected void putTargetDataCompare(
      ManyFileReader readTarget,
      DataCompare compare,
      DataParseInf<V> dataParse,
      DataCompareFileOutput compareOutput) {

    // 执行数据的并行处理
    this.readDataProc(
        readTarget,
        dataParse,
        (data) -> {
          DataCompareRsp rsp = compare.compareTargetList(data);
          try {
            // 数据输出操作
            compareOutput.dataLineWrite(rsp);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  /**
   * 读取数据的流程处理
   *
   * @param reader 流程读取器
   * @param dataParse 转换函数
   * @param runMethod 运行函数
   */
  protected void readDataProc(
      ManyFileReader reader, DataParseInf<V> dataParse, Consumer<List<V>> runMethod) {
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
        Future<?> future = this.runTask(dataParse, dataList, runMethod);

        // 当超过队列大小后则，必须待最前一个完成。
        if (rsp.size() > QUEUE_WAIT_NUM) {
          // 获取半移除队列的头
          Future item = rsp.poll();
          // 由于此处单个任务失败，不能影响其他的，帮需要单个异常处理，流程继续
          try {
            item.get();
          } catch (InterruptedException e) {
            e.printStackTrace();
          } catch (ExecutionException e) {
            e.printStackTrace();
          }
        }
        // 否则可直接加入到队列中
        else {
          // 将数据加入队列尾部
          rsp.add(future);
        }

        dataNum += dataList.size();

        if (dataNum > threshold) {
          long currTime = System.currentTimeMillis();
          System.out.println(
              "运行总时间:"
                  + (currTime - startTime)
                  + "，区间用时:"
                  + (currTime - scopeTime)
                  + "当前总量:"
                  + dataNum);
          scopeTime = System.currentTimeMillis();

          threshold += INCREMENT_PRINT;
        }
      }

      // 必须所有都完成
      for (Future item : rsp) {
        item.get();
      }
      System.out.println("处理结束...");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      reader.close();
    }
  }

  /**
   * 任务的的异步提交流程
   *
   * @param dataParse 转换函数
   * @param dataList 读取到的数据集合
   * @param runMethod 传递单参数的方法
   * @param runMethod
   */
  private Future<?> runTask(
      DataParseInf<V> dataParse, List<String> dataList, Consumer<List<V>> runMethod) {

    // 以线程池的方式并行运算
    Future<?> future =
        ScheduleTaskThreadPool.INSTANCE.submit(
            () -> {
              List<V> dataBatch = new ArrayList<>(dataList.size());
              for (String dataLine : dataList) {
                dataBatch.add(dataParse.lineToData(dataLine));
              }
              // 执行相关的方法
              runMethod.accept(dataBatch);
            });

    return future;
  }
}
