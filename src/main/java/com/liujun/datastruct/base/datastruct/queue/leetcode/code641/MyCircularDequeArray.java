package com.liujun.datastruct.base.datastruct.queue.leetcode.code641;

/**
 * use array implement circular double-ended queue
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/26
 */
public class MyCircularDequeArray {

  private final int capacity;

  private final int[] array;

  private int size;

  private int head;

  private int tail;

  /** Initialize your data structure here. Set the size of the deque to be k. */
  public MyCircularDequeArray(int k) {
    this.array = new int[k + 1];
    this.capacity = k + 1;
    size = 0;
  }

  /** Adds an item at the front of Deque. Return true if the operation is successful. */
  public boolean insertFront(int value) {
    // when queue is full
    if ((tail + 1) % capacity == head) {
      return false;
    } else {

      head = (head + capacity - 1) % capacity;
      array[head] = value;
      size++;

      return true;
    }
  }

  /** Adds an item at the rear of Deque. Return true if the operation is successful. */
  public boolean insertLast(int value) {
    // when queue is full
    if ((tail + 1) % capacity == head) {
      return false;
    } else {
      array[tail] = value;
      tail = (tail + 1) % capacity;
      size++;
      return true;
    }
  }

  /** Deletes an item from the front of Deque. Return true if the operation is successful. */
  public boolean deleteFront() {
    if (tail == head) {
      return false;
    } else {
      head = (head + 1) % capacity;
      size--;
      return true;
    }
  }

  /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
  public boolean deleteLast() {
    if (tail == head) {
      return false;
    } else {
      tail = (tail - 1 + capacity) % capacity;
      size--;
      return true;
    }
  }

  /** Get the front item from the deque. */
  public int getFront() {
    if (tail == head) {
      return -1;
    } else {
      return array[head];
    }
  }

  /** Get the last item from the deque. */
  public int getRear() {
    if (tail == head) {
      return -1;
    } else {
      int readIndex = (tail - 1 + capacity) % capacity;
      return array[readIndex];
    }
  }

  public int size() {
    return size;
  }

  /** Checks whether the circular deque is empty or not. */
  public boolean isEmpty() {
    return size == 0;
  }

  /** Checks whether the circular deque is full or not. */
  public boolean isFull() {
    return (tail + 1) % capacity == head;
  }
}
