package com.liujun.datastruct.linkedlist;

import com.liujun.datastruct.linkedlist.sample.SkipList;
import org.junit.Test;

import java.util.Random;

/**
 * 进行跳表的数据信息测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/06
 */
public class TestSkipList {

  private SkipList skipInstance = new SkipList();

  private Random rand = new Random();

  @Test
  public void testput() {

    skipInstance.insert(1);
    skipInstance.insert(3);
    skipInstance.insert(4);
    skipInstance.insert(5);
    skipInstance.insert(6);
    skipInstance.insert(7);
    skipInstance.insert(8);
    skipInstance.insert(9);
    skipInstance.insert(10);
    skipInstance.insert(13);
    skipInstance.insert(16);
    skipInstance.insert(17);
    skipInstance.insert(18);



     skipInstance.printAll();
   // skipInstance.printAllNodeTree();
  }
}
