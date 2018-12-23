package com.liujun.datastruct.datastruct.linkedlist;

/**
 * 使用链表来实现一个最近最少使用策略的缓存
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/30
 */
public class CacheLinkedList {

  /** 有序的单链表 */
  public MyLinkedList linkedList = new MyLinkedList();

  /** 缓存的大小 */
  private static final int CACHE_SIZE = 10;

  public void putCache(int value) {
    // 查找节点
    MyLinkedList.Node findNode = linkedList.findByValue(value);

    // 如果数据在缓存队列中
    if (null != findNode) {
      // 将原来节点删除
      linkedList.removeNode(findNode);
      // 将数据插入头节点
      linkedList.insertToHead(value);
    }
    // 如果数据不在缓存队列中
    else {
      // 如果已满，移除除尾的元素,然后将节点插入头部
      if (linkedList.size() >= CACHE_SIZE) {
        linkedList.removeLast();
        linkedList.insertToHead(value);
      }
      // 如果队列未满，直接插入
      else {
        linkedList.insertToHead(value);
      }
    }
  }

  public int getCahce() {
    // 首先是找到节点，这里使用
    MyLinkedList.Node findNode = linkedList.findByIndex(0);

    if (null != findNode) {
      // 将原来节点删除
      linkedList.removeNode(findNode);
      // 将数据插入头节点
      linkedList.insertToHead(findNode.getValue());
    }

    return findNode.getValue();
  }
}
