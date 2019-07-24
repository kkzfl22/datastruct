package com.liujun.datastruct.base.sort.bigdataSort.bucketSort;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * 时间打印
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/29
 */
public class TimeShow {

  public static void main(String[] args) {
    long startTime = 1540803538000L;
    // long startTime = 1540800075000;

    System.out.println(
        LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), FileCreate.ZONE8).toString());
  }
}
