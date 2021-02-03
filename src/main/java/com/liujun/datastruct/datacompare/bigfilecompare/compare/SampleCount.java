package com.liujun.datastruct.datacompare.bigfilecompare.compare;

import com.liujun.datastruct.utils.FileUtils;

import java.io.File;
import java.util.List;

/**
 * 对文件进行投样统计操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class SampleCount {

  /** 抽样数 */
  private static final int SAMPLE_NUM = 10;

  /**
   * 统计总行数
   *
   * @param path 路径
   * @return 估算总行数
   */
  public static long countSumNum(String path) {
    File[] fileList = FileUtils.getFileList(path);
    if (fileList.length < 1) {
      return 0;
    }
    // 统计总大小
    long sumTotalSize = countFileSize(fileList);
    // 统计得到单行大小
    long lineSize = countLineDataNum(fileList);
    return sumTotalSize / lineSize;
  }

  /**
   * 统计总文件大小
   *
   * @param fileList
   * @return
   */
  public static long countFileSize(File[] fileList) {
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
  public static long countLineDataNum(File[] fileList) {

    long avgLength = 0;
    for (File item : fileList) {
      List<String> dataList = FileUtils.readTop(item.getPath(), SAMPLE_NUM);
      avgLength += averageSize(dataList);
    }

    return avgLength / (long) fileList.length;
  }

  /**
   * 统计一个平均值
   *
   * @param dataSize
   * @return
   */
  public static long averageSize(List<String> dataSize) {
    if (null == dataSize || dataSize.isEmpty()) {
      return 0;
    }

    long sumNum = 0;
    for (String item : dataSize) {
      sumNum += item.getBytes().length;
    }

    return sumNum / (long) dataSize.size();
  }
}
