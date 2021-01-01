package com.liujun.datastruct.base.search.binarysearch2.leetcode278;

/**
 * The isBadVersion API is defined in the parent class VersionControl. boolean isBadVersion(int
 * version);
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution extends VersionControl {

  public int firstBadVersion(int n) {

    if (n < 1) {
      return -1;
    }

    int low = 0, mid, high = n;

    while (low <= high) {
      mid = low + (high - low) / 2;

      // 当前需要找到首个错误的版本
      if (this.isBadVersion(mid)) {
        // 由于当发生第一个错误版本后，后续版本都可能为发生错误
        // 所有必须满足条件,前一个不是发生错误的版本,当为0，则不用再找，
        if (mid == 0 || !this.isBadVersion(mid - 1)) {
          return mid;
        } else {
          high = mid - 1;
        }
      }
      // 由于
      else {
        low = mid + 1;
      }
    }

    return -1;
  }
}
