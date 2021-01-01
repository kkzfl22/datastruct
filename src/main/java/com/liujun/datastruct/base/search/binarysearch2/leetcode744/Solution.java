package com.liujun.datastruct.base.search.binarysearch2.leetcode744;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public char nextGreatestLetter(char[] letters, char target) {

    // 将小于最式值或者大于最大值的分开处理
    if (target < letters[0]) {
      return letters[0];
    }
    // 当超过最大值，也返回最小值
    else if (target > letters[letters.length - 1]) {
      return letters[0];
    }

    int low = 0, mid, high = letters.length - 1;

    while (low <= high) {
      mid = low + (high - low) / 2;

      // 当值相等时，存在几种情况,由于可存在重复，需要进行检查
      if (target == letters[mid]) {
        // 当小于最大时，则向下取最小
        if (mid < high && letters[mid + 1] > target) {
          return letters[mid + 1];
        }
        // 当等于最后一个时，则取最后一个
        else if (mid == high) {
          return letters[0];
        }
        // 否则向右区间继续查找
        else {
          low = mid + 1;
        }
      }
      // 中间值比目标值要小，则说明需要向右区间查找
      else if (letters[mid] < target) {
        low = mid + 1;
      }
      // 当中间值比目标值大，则检查是否为否为我们所查找的目标值
      else {
        // 当满足当前中间值大于目标值，前一个值小于目标或者等于目标此，此为所查找的
        if (letters[mid] > target && letters[mid - 1] <= target) {
          return letters[mid];
        }
        // 其他情况向左查找
        else {
          high = mid - 1;
        }
      }
    }

    return 'a';
  }
}
