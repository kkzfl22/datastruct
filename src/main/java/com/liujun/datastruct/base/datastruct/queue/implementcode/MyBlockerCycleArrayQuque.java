package com.liujun.datastruct.base.datastruct.queue.implementcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * use cycle blocker queue implement
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/28
 */
public class MyBlockerCycleArrayQuque implements MyQueueInf {

  private final int[] array;

  private final int capacity;

  private volatile int head;

  private volatile int tail;

  private volatile int size;

  private Lock lock = new ReentrantLock();

  private Condition enqueueLock = lock.newCondition();

  private Condition dequeueLock = lock.newCondition();

  public MyBlockerCycleArrayQuque(int capacity) {
    this.array = new int[capacity];
    this.capacity = capacity;
  }

  public boolean enqueue(int value) {
    lock.lock();
    try {

      // check queue full
      while ((tail + 1) % capacity == head) {
        enqueueLock.await();
        dequeueLock.signal();
      }

      array[tail] = value;
      tail = (tail + 1) % capacity;
      size++;
      dequeueLock.signal();

      return true;

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }

    return false;
  }

  public int dequeue() {
    lock.lock();
    int getValue = 0;
    try {

      while ((tail == head)) {
        dequeueLock.await();
        enqueueLock.signal();
      }
      getValue = array[head];
      head = (head + 1) % capacity;
      size--;
      enqueueLock.signal();

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
    return (tail + 1) % capacity == head;
  }

  @Override
  public void clean() {

    this.head = 0;
    this.tail = 0;
    size = 0;

    for (int i = 0; i < capacity; i++) {
      this.array[i] = 0;
    }
  }
}
