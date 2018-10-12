package com.liujun.datastruct.linkedlist;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/12
 */
public class StrCheck {

  /**
   * 判断一个字符串是否为回文字符串 ，使用数组实现
   *
   * @param str 字符串信息
   * @return true 表示当前为回文字符串
   */
  public static boolean check(String str) {
    if (null == str || "".equals(str)) {
      return false;
    }
    int i = 0;
    int j = str.length() - 1;
    String[] strings = str.split("");
    for (; i <= j; i++, j--) {
      if (!strings[i].equals(strings[j])) {
        return false;
      }
    }
    return true;
  }

  /**
   * 判断一个字符串是否回文字符串，使用自定义链表来实现
   *
   * @param str 待判断的字符串
   * @return true 表示为回文字符串，false 表示非回文字符串
   */
  public static boolean linkedCheck(String str) {
    if (null == str || "".equals(str)) {
      return false;
    }

    String[] arr = str.split("");

    MyLinkedListStr list = new MyLinkedListStr();

    for (String strs : arr) {
      list.add(strs);
    }

    String value = null;

    int index = 0;

    // 操作思路，从队尾移除一个，然后与队头的比较
    while ((value = list.removeLast()) != null) {

      MyLinkedListStr.Node node = list.findByIndex(index);

      if (null != node && !node.getValue().equals(value)) {
        return false;
      } else {
        index++;
      }
    }

    return true;
  }

  public static void main(String[] args) {
    System.out.println(StrCheck.check("noon"));
    System.out.println(StrCheck.linkedCheck("non"));
  }
}
