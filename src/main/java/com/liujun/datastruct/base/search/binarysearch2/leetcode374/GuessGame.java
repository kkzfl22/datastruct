package com.liujun.datastruct.base.search.binarysearch2.leetcode374;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 猜字游戏
 *
 * @author liujun
 * @version 0.0.1
 */
public abstract class GuessGame {

  private int pick;

  public void targetValue(int n) {
    this.pick = n;
  }

  public void randomValue(int n) {
    this.pick = ThreadLocalRandom.current().nextInt(0, n);
  }

  public int guess(int num) {
    if (pick < num) {
      return -1;
    } else if (pick > num) {
      return 1;
    } else {
      return 0;
    }
  }

  public abstract int guessNumber(int n);
}
