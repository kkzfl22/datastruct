package com.liujun.datastruct.base.datastruct.queue.leetcode.code641;

/**
 * design implement circular double-ended queue
 *
 * <p>use linked list
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/25
 */
public class MyCircularDequeLinked {

  class Node {
    private int val;

    private Node next;

    private Node prev;

    public Node(int val) {
      this.val = val;
    }
  }

  private final int capacity;

  private int size;

  private Node root = new Node(-1);

  private Node tail = root;

  /** Initialize your data structure here. Set the size of the deque to be k. */
  public MyCircularDequeLinked(int k) {
    this.capacity = k;
    size = 0;
  }

  /** Adds an item at the front of Deque. Return true if the operation is successful. */
  public boolean insertFront(int value) {

    if (size < capacity) {

      Node tmpValue = new Node(value);
      tmpValue.prev = root;
      tmpValue.next = root.next;

      // set next
      if (root.next != null) {
        root.next.prev = tmpValue;
      }

      root.next = tmpValue;

      if (tail == root) {
        tail = tmpValue;
      }

      size++;
      return true;
    } else {
      return false;
    }
  }

  /** Adds an item at the rear of Deque. Return true if the operation is successful. */
  public boolean insertLast(int value) {
    if (size < capacity) {
      Node tmpValue = new Node(value);
      tmpValue.prev = tail;
      tail.next = tmpValue;
      tail = tmpValue;

      size++;
      return true;
    } else {
      return false;
    }
  }

  /** Deletes an item from the front of Deque. Return true if the operation is successful. */
  public boolean deleteFront() {
    if (size > 0) {
      Node front = root.next;
      if (front.next != null) {
        front.next.prev = root;
        root.next = front.next;
      } else {
        root.next = null;
        tail = root;
      }

      size--;
      return true;
    } else {
      return false;
    }
  }

  /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
  public boolean deleteLast() {
    if (size > 0) {
      Node lastPrent = tail.prev;
      lastPrent.next = tail.next;
      tail = lastPrent;
      size--;
      return true;
    } else {
      return false;
    }
  }

  /** Get the front item from the deque. */
  public int getFront() {
    if (size > 0) {
      return root.next.val;
    } else {
      return -1;
    }
  }

  /** Get the last item from the deque. */
  public int getRear() {
    if (size > 0) {
      return tail.val;
    } else {
      return -1;
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
    return size == capacity;
  }
}
