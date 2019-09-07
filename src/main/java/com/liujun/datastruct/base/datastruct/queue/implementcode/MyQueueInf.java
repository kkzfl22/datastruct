package com.liujun.datastruct.base.datastruct.queue.implementcode;

/**
 * queue interface
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/28
 */
public interface MyQueueInf {

  /**
   * en queue
   *
   * @param value value info
   */
  boolean enqueue(int value);

  /**
   * get size
   *
   * @return
   */
  int size();

  /**
   * is full
   *
   * @return
   */
  boolean isfull();

  /**
   * dequeue
   *
   * @return
   */
  int dequeue();

  /** clean */
  void clean();
}
