package com.liujun.datastruct.base.datastruct.queue.leetcode.code622;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/29
 */
public class MyCircularQueue {

  private final int[] array;

  private final int capacity;

  private int head;

  private int tail;

  private int size;

  /** Initialize your data structure here. Set the size of the queue to be k. */
  public MyCircularQueue(int k) {
    this.array = new int[k + 1];
    this.capacity = k + 1;
  }

  /** Insert an element into the circular queue. Return true if the operation is successful. */
  public boolean enQueue(int value) {
    if ((tail + 1) % capacity == head) {
      return false;
    }
    array[tail] = value;
    tail = (tail + 1) % capacity;
    size++;
    return true;
  }

  /** Delete an element from the circular queue. Return true if the operation is successful. */
  public boolean deQueue() {
    if (tail == head) {
      return false;
    }
    head = (head + 1) % capacity;
    size--;
    return true;
  }

  /** Get the front item from the queue. */
  public int Front() {
    if (this.isEmpty()) {
      return -1;
    }

    return this.array[head];
  }

  /** Get the last item from the queue. */
  public int Rear() {
    if (this.isEmpty()) {
      return -1;
    }

    int tailiIndex = (tail - 1 + capacity) % capacity;
    return array[tailiIndex];
  }

  /** Checks whether the circular queue is empty or not. */
  public boolean isEmpty() {
    return tail == head;
  }

  /** Checks whether the circular queue is full or not. */
  public boolean isFull() {
    return (tail + 1) % capacity == head;
  }

  public int size() {
    return size;
  }
}
