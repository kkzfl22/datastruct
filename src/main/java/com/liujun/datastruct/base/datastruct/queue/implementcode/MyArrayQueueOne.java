package com.liujun.datastruct.base.datastruct.queue.implementcode;

/**
 * use array implement queue
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/23
 */
public class MyArrayQueueOne implements MyQueueInf {

  private int[] array;

  /** max queue size */
  private final int MaxArraySize;

  private int size;

  private int head;

  private int tail;

  public MyArrayQueueOne(int maxArraySize) {
    this.MaxArraySize = maxArraySize;
    this.array = new int[maxArraySize];
    this.head = 0;
    this.tail = 0;
  }

  public boolean enqueue(int value) {
    if (tail < MaxArraySize) {
      array[tail] = value;
      tail++;
      size++;
      return true;
    } else {
      return false;
    }
  }

  public int size() {
    return size;
  }

  public boolean isfull() {
    return size == MaxArraySize;
  }

  public int dequeue() {

    int getValue = -1;

    if (head < tail) {
      getValue = array[head];

      for (int i = 1; i < tail; i++) {
        array[i - 1] = array[i];
      }
      tail = tail - 1;
      size--;
    }

    return getValue;
  }

  @Override
  public void clean() {

    this.head = 0;
    this.tail = 0;
    size = 0;

    for (int i = 0; i < MaxArraySize; i++) {
      this.array[i] = 0;
    }
  }
}
