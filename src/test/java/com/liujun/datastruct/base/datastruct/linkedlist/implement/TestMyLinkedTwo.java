package com.liujun.datastruct.base.datastruct.linkedlist.implement;

import org.junit.Assert;
import org.junit.Test;

/**
 * 进行双向环链表的测试操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/30
 */
public class TestMyLinkedTwo {

  /** 双向环链表的添加以及头删除测试 */
  @Test
  public void testToploop() {
    MyLinkedTwoNode node = new MyLinkedTwoNode();
    int maxValue = 100;

    for (int i = 0; i < maxValue; i++) {
      node.add(i);
      int value = node.getValue(i);
      Assert.assertEquals(i, value);
    }

    Assert.assertEquals(maxValue, node.size());

    int start = 0;
    // 进行头节点的删除操作
    while (!node.empty()) {
      int deletevalue = node.deleteTop();
      Assert.assertEquals(deletevalue, start);
      start++;
    }
  }

  /** 双向环链表添加的及尾删除测试 */
  @Test
  public void testEndloop() {
    MyLinkedTwoNode node = new MyLinkedTwoNode();
    int maxValue = 100;

    for (int i = 0; i < maxValue; i++) {
      node.add(i);
      int value = node.getValue(i);
      Assert.assertEquals(i, value);
    }

    Assert.assertEquals(maxValue, node.size());

    int endvalue = maxValue - 1;
    // 进行头节点的删除操作
    while (!node.empty()) {
      int deletevalue = node.deleteLast();
      Assert.assertEquals(deletevalue, endvalue);
      endvalue--;
    }
  }
}
