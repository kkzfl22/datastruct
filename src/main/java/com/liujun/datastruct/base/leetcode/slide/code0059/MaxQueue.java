package com.liujun.datastruct.base.leetcode.slide.code0059;

/**
 * 执行结果： 通过
 *
 * <p>显示详情 执行用时： 37 ms ,
 *
 * <p>在所有 Java 提交中击败了 98.63% 的用户
 *
 * <p>内存消耗： 46.4 MB , 在所有 Java 提交中击败了 49.96% 的用户
 *
 * @author liujun
 * @version 0.0.1
 */
public class MaxQueue {

  /** 默认的大小 */
  private static final int DEFAULT_SIZE = 16;

  /** 容量 */
  private int capacity = DEFAULT_SIZE;

  /** 实例 */
  private int[] instance = new int[capacity];

  /** 用于在扩容时的实例对象 */
  private int[] newInstance;

  /** 窗口左指针位置 */
  private int left;

  /** 窗口右指针位置 */
  private int right;

  /** 当前的最大值 */
  private int maxValue = -1;

  public MaxQueue() {}

  public int max_value() {

    return maxValue;
  }

  public void push_back(int value) {

    // 1,检查当前是否需要扩容
    if (right == capacity) {

      capacity = right - left < capacity ? capacity : (right - left) * 2;
      newInstance = new int[capacity];

      // 交换示例与新实例
      int[] temp = instance;
      instance = newInstance;
      newInstance = temp;

      // 将数据进行一次搬移操作
      for (int i = left; i < right; i++) {
        instance[i - left] = newInstance[i];
      }

      // 设置新的位置
      right = right - left;
      left = 0;
    }

    // 计算最大值
    if (maxValue < value) {
      this.maxValue = value;
    }

    instance[right++] = value;
  }

  public int pop_front() {

    if (left >= right) {
      return -1;
    }

    int value = instance[left++];

    // 当最大值从队列移除时，需要重新计算
    if (value == maxValue) {
      maxValue = -1;
      // 计算滑动窗口内的最大值
      for (int i = left; i < right; i++) {
        // 计算最大值
        if (maxValue < instance[i]) {
          this.maxValue = instance[i];
        }
      }
    }

    return value;
  }
}
