package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 数据生成类
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/24
 */
public class GenerateData {

  /**
   * 生成字符串的方法
   *
   * @return
   */
  public static String dataStr() {

    char data;
    int lineSize = 16;

    StringBuilder outData = new StringBuilder();

    for (int j = 0; j < lineSize; j++) {
      data = (char) ThreadLocalRandom.current().nextInt(0x61, 0x7a);
      outData.append(data);
    }
    return outData.toString();
  }

  /**
   * 生成数字的字符的方法
   *
   * @return 字符信息
   */
  public static String dataNum1() {

    int lineSize = ThreadLocalRandom.current().nextInt(0, 9);

    return String.valueOf(lineSize);
  }

  /**
   * 生成日志数据
   *
   * @return
   */
  public static String enerateLogData() {
    StringBuilder outLogData = new StringBuilder();

    long data = ThreadLocalRandom.current().nextLong(-100000, 99999);
    long logTime = System.currentTimeMillis() + data;

    outLogData.append(logTime);
    outLogData.append(" ");
    outLogData.append("[INFO] [2018-01-12 13:04:09,607] ");
    outLogData.append(
        "[org.springframework.web.context.ContextLoader.initWebApplicationContext(ContextLoader.java:272)] ");
    outLogData.append(
        "[localhost-startStop-1] [2964]   : Root WebApplicationContext: initialization started");

    return outLogData.toString();
  }
}
