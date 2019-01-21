package com.liujun.datastruct.base.datastruct.hash.linked;

import org.junit.Test;

/**
 * 使用链表法来解决散列冲突测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/09
 */
public class TestMyLinkedHash {

  @Test
  public void testMylinked() {
    MyLinkedHash hash = new MyLinkedHash(8);

    int max = 64;

    for (int i = 0; i < max; i++) {
      String msg = String.valueOf(i);
      hash.push(msg, msg);
    }

    hash.push("0", "0000");

    hash.printNodeTree();

    for (int i = 0; i < max; i++) {
      String msg = String.valueOf(i);
      System.out.print(hash.get(msg) + "\t");
    }

    System.out.println();
    System.out.println("----------------------");

    int value = -2;

    System.out.println(Integer.toBinaryString(value));
    int bit = 10;
    int show1value = value >>> bit;
    System.out.println(Integer.toBinaryString(show1value));
    int show2Value = value >> bit;
    System.out.println(Integer.toBinaryString(show2Value));
  }
}
