package com.liujun.datastruct.base.datastruct.queue.implementcode;

import javax.naming.NameAlreadyBoundException;

/**
 * use array implement cycle queue
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/25
 */
public class MyCycleQueue implements MyQueueInf {

  private final int[] array;

  private final int capacity;

  private int head;

  private int tail;

  private int size;

  public MyCycleQueue(int maxSize) {
    this.capacity = maxSize;
    this.array = new int[capacity];
  }

  public boolean enqueue(int value) {
    // check cycle is full
    if ((tail + 1) % capacity == head) {
      return false;
    } else {
      array[tail] = value;
      tail = (tail + 1) % capacity;
      size++;
      return true;
    }
  }

  public int dequeue() {
    // check cycle is null
    int getvalue = -1;
    if (head == tail) {
      throw new NegativeArraySizeException("queue is empty");
    } else {
      getvalue = array[head];
      head = (head + 1) % capacity;
      size--;
    }

    return getvalue;
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
