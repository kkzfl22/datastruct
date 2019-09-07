package com.liujun.datastruct.base.datastruct.queue.implementcode;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock free use cycle blocker queue implement
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/28
 */
public class MyCycleArrayQuqueLockFree implements MyQueueInf {

  private final int[] array;

  private final int capacity;

  private AtomicInteger head;

  private AtomicInteger tail;

  private AtomicInteger size;

  public MyCycleArrayQuqueLockFree(int capacity) {
    this.array = new int[capacity];
    this.capacity = capacity;
    this.head = new AtomicInteger(0);
    this.tail = new AtomicInteger(0);
    this.size = new AtomicInteger(0);
  }

  public boolean enqueue(int value) {

    // check queue full
    if ((tail.get() + 1) % capacity == head.get()) {
      return false;
    }

    int tailVal = tail.get();
    int tailNextValue = (tailVal + 1) % capacity;

    boolean updRsp = tail.compareAndSet(tailVal, tailNextValue);

    if (updRsp) {
      array[tailVal] = value;
      size.incrementAndGet();
      return true;
    }
    return false;
  }

  public int dequeue() {

    if ((tail.get() == head.get())) {
      return -1;
    }
    int headBefvalue = head.get();
    int headUpdValue = (headBefvalue + 1) % capacity;

    if (head.compareAndSet(headBefvalue, headUpdValue)) {

      int getValue = array[headBefvalue];
      size.decrementAndGet();

      return getValue;
    }

    return -1;
  }

  @Override
  public int size() {
    return size.get();
  }

  @Override
  public boolean isfull() {
    return (tail.get() + 1) % capacity == head.get();
  }

  @Override
  public void clean() {
    this.head.set(0);
    this.tail.set(0);
    this.size.set(0);

    for (int i = 0; i < capacity; i++) {
      this.array[i] = 0;
    }
  }
}
