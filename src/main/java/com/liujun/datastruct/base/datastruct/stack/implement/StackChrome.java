package com.liujun.datastruct.base.datastruct.stack.implement;

/**
 * 实现浏览器的前进与后退功能
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/08/14
 */
public class StackChrome {

  /** 浏览器缓存的栈的最大值 */
  private static final int MAX_SIZE = 10;

  /** 缓存栈的后退实现 */
  private MyLinkedChromeStack chromeHistoryStacke = new MyLinkedChromeStack(MAX_SIZE);

  /** 缓存栈的前进实现 */
  private MyLinkedChromeStack chromeGotoStacke = new MyLinkedChromeStack(MAX_SIZE);

  public void openWeb(String url) {
    // 如果当前栈已满，移除最老访问的数据，添加最新的数据
    if (chromeHistoryStacke.full()) {
      chromeHistoryStacke.popFirst();
    }

    chromeHistoryStacke.push(url);
  }

  /**
   * 浏览的后退功能实现
   *
   * @return
   */
  public String backUrl() {
    String backUrl = chromeHistoryStacke.pop();

    // 将其加入到前进
    if (chromeGotoStacke.full()) {
      chromeGotoStacke.popFirst();
    }
    chromeGotoStacke.push(backUrl);

    return backUrl;
  }

  /**
   * 浏览器的缓存前进实现
   *
   * @return
   */
  public String gotoUrl() {
    String gotoUrl = chromeGotoStacke.pop();

    if (chromeHistoryStacke.full()) {
      chromeHistoryStacke.popFirst();
    }

    chromeHistoryStacke.push(gotoUrl);

    return gotoUrl;
  }
}
