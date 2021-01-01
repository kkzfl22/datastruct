package com.liujun.datastruct.base.search.binarysearch2.leetcode374;

/**
 * 猜字游戏测试
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution extends GuessGame {

  public int guessNumber(int n) {

    int low = 0, mid, high = n;

    while (low <= high) {
      mid = low + (high - low) / 2;

      int status = this.guess(mid);

      // 如果等于目标值，说明就是我们要找到
      if (status == 0) {
        return mid;
      }
      // 如果目标值比当前猜的数据大
      else if (status == -1) {
        high = mid - 1;
      }
      // 如果目标值比当前猜的数字小
      else {
        low = mid + 1;
      }
    }

    return -1;
  }
}
