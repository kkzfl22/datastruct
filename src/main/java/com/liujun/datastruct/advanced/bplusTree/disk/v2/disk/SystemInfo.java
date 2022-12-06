package com.liujun.datastruct.advanced.bplusTree.disk.v2.disk;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 提供对系统环境变量的一些访问
 *
 * @author liujun
 * @since 2022/11/21
 */
public class SystemInfo {

  private static final Unsafe UNSAFE_INSTANCE = getUnsafe();

  private static Unsafe getUnsafe() {
    Unsafe unsafe = null;
    try {
      Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
      theUnsafeField.setAccessible(true);
      unsafe = (Unsafe) theUnsafeField.get(null);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return unsafe;
  }

  /**
   * 获取内存页的大小
   *
   * @return
   */
  public static int getPageSize() {
    return UNSAFE_INSTANCE.pageSize();
  }
}
