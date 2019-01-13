package com.liujun.datastruct.base.datastruct.queue;

/**
 * 使用数组实现队列优化
 *
 * <p>由于这前的队列存在问题，只能使用一次，需要改进，只有还有空余空间都可以进行插入
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/24
 */
public class ArrayQueue1 {

  /** 用来存储数据的数组 */
  private final int[] data;

  /** 当前大小 */
  private int count;

  /** 头指针 */
  private int head;

  /** 总容量大小 */
  private int capacity;

  public ArrayQueue1(int capacity) {
    this.data = new int[capacity];
    this.count = 0;
    this.capacity = capacity;
  }

  /**
   * 数据放入操作
   *
   * @param value 参数值
   * @return true 添加成功 false 添加失败
   */
  public boolean push(int value) {
    // 检查队列是否已经到尾部了
    if (count >= capacity) {

      // 检查是否还存在可用空间
      if (count - head < capacity) {
        if (count == head) {
          head = 0;
          count = 0;
        } else {
          // 进行数据的搬移操作
          for (int i = head; i < count; i++) {
            data[i - head] = data[i];
          }
          count = count - head;
          head = 0;

        }
      } else {
        return false;
      }
    }

    this.data[count] = value;
    count++;

    return true;
  }

  /**
   * 进行出队列操作
   *
   * @return 返回值
   */
  public int pop() {

    if (head >= count) {
      return -1;
    }

    int value = this.data[head];

    head++;

    return value;
  }
}
