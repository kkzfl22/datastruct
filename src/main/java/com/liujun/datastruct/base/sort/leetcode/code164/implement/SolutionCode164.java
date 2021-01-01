package com.liujun.datastruct.base.sort.leetcode.code164.implement;

/**
 * 常规的解法是先排序，然后计算两两的间距
 *
 * @author liujun
 * @version 0.0.1
 * @date 2020/01/13
 */
public class SolutionCode164 {

  public int maximumGap(int[] nums) {

    if (nums == null || nums.length <= 1) {
      return 0;
    }

    // this.insertSort(nums);
    this.quickSort(nums);

    int dataLength = 0;

    for (int i = 1; i < nums.length; i++) {
      int tmpLength = nums[i] - nums[i - 1];
      if (tmpLength > dataLength) {
        dataLength = tmpLength;
      }
    }

    return dataLength;
  }

  /**
   * 使用插入排序来解决
   *
   * @param nums
   */
  public void insertSort(int[] nums) {
    // 未排序区间的遍历
    for (int i = 1; i < nums.length; i++) {
      // 查找插入点
      int insertIndex = i;
      int data = nums[i];
      for (int j = 0; j < i; j++) {
        if (nums[j] > nums[i]) {
          insertIndex = j;
          break;
        }
      }

      // 将数据进行搬移操作
      for (int j = i; j > insertIndex; j--) {
        nums[j] = nums[j - 1];
      }

      // 将数据放入到插入点中
      nums[insertIndex] = data;
    }
  }

  /**
   * 使用快速排序来解决
   *
   * @param data 待排序的数据
   */
  public void quickSort(int[] data) {
    sortQuick(data, 0, data.length - 1);
  }

  public void sortQuick(int[] data, int start, int end) {

    if (start == end) {
      return;
    }

    // 分区函数
    int point = partition(data, start, end);

    sortQuick(data, start, point);
    sortQuick(data, point + 1, end);
  }

  public int partition(int[] data, int start, int end) {
    // 分区点，目前选择数组中的最后一个数
    int point = end;
    int left = start;
    for (int right = start; right <= end - 1; right++) {
      if (data[left] < data[point]) {
        swap(data, left, right);
        left += 1;
      }
    }
    // 将最后的分区点放入到位置中
    swap(data, left, point);
    // 分区点的位置
    return left;
  }

  private void swap(int[] data, int src, int target) {
    int tmp = data[target];
    data[target] = data[src];
    data[src] = tmp;
  }
}
