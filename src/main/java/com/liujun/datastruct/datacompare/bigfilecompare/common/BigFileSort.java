package com.liujun.datastruct.datacompare.bigfilecompare.common;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.datacompare.bigfilecompare.threadpool.ScheduleTaskThreadPool;
import com.liujun.datastruct.utils.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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

  /** 文件转换接口 */
  private final DataParseInf<V> dataParse;

  /** 文件路径 */
  private final String readPath;

  /** 用于统计输出操作 */
  private final DataStatistics dataStatistics;

  /** 当我数据排序时，每10个文件，执行一次输出 */
  private static final int INCREMENT = 20;

  public BigFileSort(String readPath, DataParseInf<V> dataParse) {
    this.readPath = readPath;
    this.dataParse = dataParse;
    this.dataStatistics = new DataStatistics(INCREMENT);
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

    System.out.println("开始执行线程池任务，信息输出");
    ScheduleTaskThreadPool.INSTANCE.outPoolInfo();

    LinkedList<Future> rsp = new LinkedList<>();
    try {
      for (File fileItem : dataList) {
        // 异步调用数据处理
        Future<?> future = this.runTask(fileItem);
        // 将数据加入队列尾部
        rsp.add(future);
        // 统计计数
        dataStatistics.dataAdd(1);

        // 优先检查线程池是否已经满载
        if (ScheduleTaskThreadPool.INSTANCE.isFull()) {
          // 获取并移除队列的头
          Future item = rsp.poll();
          // 由于此处单个任务失败，不能影响其他的，需要单个异常处理，流程继续
          try {
            Object value = item.get();
            // 打印任务信息
            System.out.println("用时:" + value);
            ScheduleTaskThreadPool.INSTANCE.outPoolInfo();

          } catch (InterruptedException e) {
            e.printStackTrace();
          } catch (ExecutionException e) {
            e.printStackTrace();
          }
        }
      }
      // 必须所有都完成
      for (Future item : rsp) {
        Object value = item.get();
        // 打印任务信息
        System.out.println("用时:" + value);
        ScheduleTaskThreadPool.INSTANCE.outPoolInfo();
      }

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
              long startTime = System.currentTimeMillis();
              // 将文件转换为集合
              List oneFileList = this.fileToList(fileItem);

              // 使用插入排序，保持数据的稳定，不改前数据的前后顺序,但时间复杂度为n的平方.
              Collections.sort(oneFileList);
              // 快速排序，快速排序非稳定的排序，会改变前后顺序。
              // QuickSort.quickSortList(oneFileList);
              // 再输出至文件中
              this.listToFile(oneFileList, fileItem);

              long endTime = System.currentTimeMillis();
              return endTime - startTime;
            });

    return future;
  }
}
