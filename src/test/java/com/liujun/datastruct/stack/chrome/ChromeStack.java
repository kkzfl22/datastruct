package com.liujun.datastruct.stack.chrome;

import com.liujun.datastruct.stack.ArrayStrStack;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/24
 */
public class ChromeStack {

  /** 流程器前进按钮栈 */
  private ArrayStrStack forwardStack = new ArrayStrStack(10);

  /** 浏览器后退保存的栈信息 */
  private ArrayStrStack backStack = new ArrayStrStack(10);

  /**
   * 在后退栈中加入
   *
   * @param url
   */
  public void open(String url) {
    backStack.push(url);
    forwardStack.clean();
  }

  /**
   * 浏览器后退功能
   *
   * @return
   */
  public String back() {
    String back = backStack.pop();
    forwardStack.push(back);
    return back;
  }

  /**
   * 进行浏览器前进按钮操作
   *
   * @return
   */
  public String forward() {
    String forwardUrl = forwardStack.pop();
    backStack.push(forwardUrl);
    return forwardUrl;
  }
}
