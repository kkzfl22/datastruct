package com.liujun.utils;

import java.util.Random;

/**
 * 获取随机数
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/17
 */
public class RandomUtils {

  private static final Random RAND = new Random();

  public static int getNextInt() {
    return RAND.nextInt();
  }

  public static int getNextInt(int scope) {
    return RAND.nextInt(scope);
  }
}
