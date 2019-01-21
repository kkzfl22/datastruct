package com.liujun.datastruct.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/21
 */
public class UnsafeInstance {

  public static final Unsafe UNSAFEINSTANCE = getUnsafe();

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
}
