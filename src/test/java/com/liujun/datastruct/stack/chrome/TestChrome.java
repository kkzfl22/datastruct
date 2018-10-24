package com.liujun.datastruct.stack.chrome;

/**
 * 测试浏览器的前进与后进的功能
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/24
 */
public class TestChrome {

  public static void main(String[] args) {
    ChromeStack chrome = new ChromeStack();

    chrome.open("1-www.baidu.com");
    chrome.open("2-www.google.com");
    chrome.open("3-www.taobao.com");
    chrome.open("4-www.qq.com");
    chrome.open("5-www.sina.com");

    // 浏览器后退功能
    System.out.println("浏览器后退至:" + chrome.back());
    System.out.println("浏览器后退至:" + chrome.back());
    System.out.println("浏览器后退至:" + chrome.back());
    System.out.println("浏览器后退至:" + chrome.back());
    System.out.println("浏览器后退至:" + chrome.back());

    System.out.println();
    // 浏览器前进
    System.out.println("浏览器前进按钮操作:" + chrome.forward());
    System.out.println("浏览器前进按钮操作:" + chrome.forward());
    System.out.println("浏览器前进按钮操作:" + chrome.forward());
    System.out.println("浏览器前进按钮操作:" + chrome.forward());
    System.out.println("浏览器前进按钮操作:" + chrome.forward());

    System.out.println();

    System.out.println("浏览器后退:" + chrome.back());
    System.out.println("浏览器后退:" + chrome.back());
    System.out.println("浏览器后退:" + chrome.back());

    System.out.println("------------");

    chrome.open("4-www.zol.com.cn");

    System.out.println("浏览器后退:" + chrome.back());
    System.out.println("浏览器后退:" + chrome.back());
    System.out.println("浏览器后退:" + chrome.back());
    System.out.println("浏览器后退:" + chrome.back());
  }
}
