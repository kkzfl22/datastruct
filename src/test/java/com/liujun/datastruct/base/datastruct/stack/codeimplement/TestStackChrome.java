package com.liujun.datastruct.base.datastruct.stack.codeimplement;

import com.liujun.datastruct.base.datastruct.stack.implement.StackChrome;
import org.junit.Test;

/**
 * 测试浏览器的前进与后退功能
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/15
 */
public class TestStackChrome {

  @Test
  public void chrome() {
    StackChrome stack = new StackChrome();

    int maxNum = 20;

    for (int i = 0; i < maxNum; i++) {
      stack.openWeb("http://www.baidu.com" + i);
    }

    // 添加数据
    for (int i = 0; i < 5; i++) {
      String backUrl = stack.backUrl();
      System.out.println("后退:" + backUrl);
    }

    System.out.println("-----------------------");
    // 前进
    for (int i = 0; i < 5; i++) {
      String gotoUrl = stack.gotoUrl();
      System.out.println("前进:" + gotoUrl);
    }
  }
}
