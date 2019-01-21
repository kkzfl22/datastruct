package com.liujun.datastruct.advanced.bplusTree.codedemo.demo;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/21
 */
public class CharGetLength {
  public long longValue = Long.MAX_VALUE;
  public int intValue = Integer.MAX_VALUE;
  public char charValue = '1';
  public short shortValue = Short.MAX_VALUE;
  public byte byvalue = Byte.MAX_VALUE;
  public boolean boolvalue = Boolean.TRUE;

  public static void main(String[] args) {
    Unsafe UNSAFE;

    try {
      Field field = Unsafe.class.getDeclaredField("theUnsafe");

      field.setAccessible(true);

      UNSAFE = (Unsafe) field.get(null);

    } catch (Exception e) {
      e.printStackTrace();
      throw new Error();
    }

    //    System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
    //
    //    Field[] fieds = CharGetLength.class.getDeclaredFields();
    //
    //    // UNSAFE.
    //
    //    for (Field fiedItem : fieds) {
    //
    //      System.out.println(fiedItem.getName() + ":" + UNSAFE.objectFieldOffset(fiedItem));
    //    }

    System.out.println(VM.current().details());
    System.out.println(ClassLayout.parseClass(CharGetLength.class).toPrintable());
  }
}
