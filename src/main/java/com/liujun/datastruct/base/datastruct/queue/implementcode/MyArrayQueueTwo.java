package com.liujun.datastruct.base.datastruct.queue.implementcode;

/**
 * use array implement queue
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/23
 */
public class MyArrayQueueTwo implements MyQueueInf {

  private int[] array;

  /** max queue size */
  private final int MaxArraySize;

  private int size;

  private int head;

  private int tail;

  public MyArrayQueueTwo(int maxArraySize) {
    this.MaxArraySize = maxArraySize;
    this.array = new int[maxArraySize];
    this.head = 0;
    this.tail = 0;
  }

  public boolean enqueue(int value) {
    if (tail <= MaxArraySize) {

      if (tail == head) {
        tail = 0;
        head = 0;
      }

      // 如果空间已经被占用，则触发一次搬移操作
      if (tail == MaxArraySize) {
        for (int i = head; i < tail; i++) {
          array[i - head] = array[i];
        }
        tail = head;
        head = 0;
      }
      //
      if (tail < MaxArraySize) {
        array[tail] = value;
        tail++;
        size++;
      }

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

      head++;
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
