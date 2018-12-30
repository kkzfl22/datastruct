package com.liujun.math.chapter02Iterate;

import org.junit.Test;

import java.sql.SQLOutput;
import java.util.Arrays;

/**
 * 测试二分法在字符串查询的应用
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/30
 */
public class TestDichotomyStringFind {

  @Test
  public void dichotomyFind() {

    String[] findValue = {"ggg", "sss", "a", "b", "cc", "ddd", "eeeeee", "ffff"};
    Arrays.sort(findValue);
    System.out.println(Arrays.toString(findValue));

    DichotomyStringFind instance = new DichotomyStringFind();
    int rsp = instance.search(findValue, "sec");
    System.out.println("查询结果:" + rsp);
  }
}
