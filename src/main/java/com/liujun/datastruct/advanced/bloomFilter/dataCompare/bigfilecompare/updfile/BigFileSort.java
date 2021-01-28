package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.updfile;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.fileoperator.ManyFileReader;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.fileoperator.ManyFileWrite;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.fileoperator.ManyFileWriteBase;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.threadpool.ScheduleTaskThreadPool;
import com.liujun.datastruct.utils.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 大文件的排序操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class BigFileSort<V> {

  /** 最大等待数量 */
  private static final int QUEUE_WAIT_NUM = 16;

  /** 文件转换接口 */
  private final DataParseInf<V> dataParse;

  /** 文件路径 */
  private final String readPath;

  public BigFileSort(String readPath, DataParseInf<V> dataParse) {
    this.readPath = readPath;
    this.dataParse = dataParse;
  }

  /**
   * 文件排序操作
   *
   * @return 文件输出的路径
   */
  public String bigFileSort() {
    // 2,对每个文件执行排序操作
    this.fileSort();

    return this.readPath;
  }

  /** 执行文件排序操作 */
  private void fileSort() {
    // 文件列表
    File[] dataList = FileUtils.getFileList(this.readPath);
    // 对文件执行排序
    fileSoft(dataList);
  }

  /**
   * 将文件转换为集合
   *
   * @param dataFile 数据信息
   * @return 结果信息
   */
  private List<V> fileToList(File dataFile) {
    List<V> dataList = new ArrayList<>();
    try (FileReader reader = new FileReader(dataFile);
        BufferedReader bufferedReader = new BufferedReader(reader)) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        dataList.add(dataParse.lineToData(line));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dataList;
  }

  /**
   * 将排序后的文件输出到文件中
   *
   * @param data 排序后的数据
   * @param outFile 文件信息
   */
  private void listToFile(List<V> data, File outFile) {
    try (FileWriter write = new FileWriter(outFile);
        BufferedWriter bufferedWriter = new BufferedWriter(write)) {
      for (V item : data) {
        bufferedWriter.write(dataParse.toFileLine(item) + Symbol.LINE);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 读取数据的流程处理
   *
   * @param dataList 文件列表
   */
  protected void fileSoft(File[] dataList) {
    ConcurrentLinkedQueue<Future> rsp = new ConcurrentLinkedQueue<>();
    try {
      System.out.println("并行排序开始...");
      for (File fileItem : dataList) {
        // 异步调用数据处理
        Future<?> future = this.runTask(fileItem);

        // 当超过队列大小后则，必须待最前一个完成。
        if (rsp.size() > QUEUE_WAIT_NUM) {
          // 获取并移除队列的头
          Future item = rsp.poll();
          // 由于此处单个任务失败，不能影响其他的，需要单个异常处理，流程继续
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
      }
      // 必须所有都完成
      for (Future item : rsp) {
        item.get();
      }
      System.out.println("并行排序结束...");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 异常执行
   *
   * @param fileItem 文件
   * @return 异常结果
   */
  private Future<?> runTask(File fileItem) {

    // 以线程池的方式并行运算
    Future<?> future =
        ScheduleTaskThreadPool.INSTANCE.submit(
            () -> {
              // 将文件转换为集合
              List<V> oneFileList = this.fileToList(fileItem);
              // 将集合排序
              QuickSort.quickSortList(oneFileList);
              // 再输出至文件中
              this.listToFile(oneFileList, fileItem);
            });

    return future;
  }
}
