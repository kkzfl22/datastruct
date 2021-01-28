package com.liujun.datastruct.base.datastruct.queue.implementcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * use linked implement blocker queue
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/28
 */
public class MyBlockerLinkedQuque implements MyQueueInf {

  class Node {
    private int val;

    private Node next;

    public Node(int val) {
      this.val = val;
    }
  }

  private final Node root = new Node(-1);

  private Node tail = root;

  private final int capacity;

  private volatile int size;

  private Lock lock = new ReentrantLock();

  private Condition enqueueLock = lock.newCondition();

  private Condition deququeLock = lock.newCondition();

  public MyBlockerLinkedQuque(int capacity) {
    this.capacity = capacity;
  }

  public boolean enqueue(int value) {
    lock.lock();
    try {
      while (size >= capacity) {
        deququeLock.signal();
        enqueueLock.await();
      }

      Node curTmpVal = new Node(value);
      tail.next = curTmpVal;
      tail = curTmpVal;
      size++;

      deququeLock.signal();

      return true;

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }

    return false;
  }

  public int dequeue() {

    int getValue = -1;
    lock.lock();
    try {
      while (size == 0) {
        enqueueLock.signal();
        deququeLock.await();
      }

      Node getValNode = root.next;
      getValue = getValNode.val;
      root.next = getValNode.next;

      if (root.next == null) {
        tail = root;
      }

      size--;
      enqueueLock.signal();

      return getValue;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }

    return getValue;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isfull() {
    return size == capacity;
  }

  @Override
  public void clean() {

    this.root.next = null;
    this.tail = root;
    size = 0;
  }
}
