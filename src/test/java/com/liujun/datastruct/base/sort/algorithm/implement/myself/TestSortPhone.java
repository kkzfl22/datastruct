package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import org.junit.Test;

/**
 * 进行数据排序操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/12/25
 */
public class TestSortPhone {

  @Test
  public void sortPhone() {
    String[] data =
        new String[] {
          "134519128882",
          "134519728283",
          "134519128824",
          "137519128888",
          "138512128881",
          "138513128887",
          "138549128888"
        };
    SortPhone phone = new SortPhone();
    phone.sortPhone(data);
  }
}
