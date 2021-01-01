package com.liujun.datastruct.base.search.binarysearch2.leetcode367;

/**
 * 有效的完全平方数
 *
 * <p>通过将一个数取中，做除法操作，验证
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public boolean isPerfectSquare(int num) {

    if (num == 0 || num == 1) {
      return true;
    }

    int runNum = num;

    if (runNum < 0) {
      runNum = Math.abs(runNum);
    }

    int low = 1, mid, high = runNum;
    double sale = 1.0;

    double countRsp;

    while (low <= high) {
      mid = low + (high - low) / 2;
      countRsp = (runNum + 0d) / mid;

      if (countRsp == mid || high - mid <= sale) {
        if (countRsp == mid) {
          return true;
        }
        return false;
      } else if (mid > countRsp) {
        high = mid;
      } else {
        low = mid;
      }
    }

    return Boolean.FALSE;
  }
}
