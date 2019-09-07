package com.liujun.datastruct.base.datastruct.queue.implementcode;

/**
 * use linked implement queue
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/23
 */
public class MyLinkedQueue implements MyQueueInf {

  class LinkNode {
    private int val;

    private LinkNode next;

    public LinkNode(int valu) {
      this.val = valu;
    }
  }

  /** max queue size */
  private final int MaxQueueSize;

  private LinkNode root = new LinkNode(-1);

  private LinkNode tail = root;

  private int size;

  public MyLinkedQueue(int maxQueueSize) {
    this.MaxQueueSize = maxQueueSize;
  }

  public int size() {
    return size;
  }

  public boolean isfull() {
    return size == MaxQueueSize;
  }

  public boolean enqueue(int value) {

    if (size < MaxQueueSize) {
      LinkNode tmpValue = new LinkNode(value);

      tail.next = tmpValue;
      tail = tmpValue;

      size++;
      return true;
    }
    return false;
  }

  public int dequeue() {

    int getvalue = -1;
    if (size > 0) {
      LinkNode linkNode = root.next;
      getvalue = linkNode.val;

      root.next = linkNode.next;

      if (null == root.next) {
        tail = root;
      }

      size--;
    }

    return getvalue;
  }

  @Override
  public void clean() {
    root.next = null;
    this.tail = root;
    size = 0;
  }
}
