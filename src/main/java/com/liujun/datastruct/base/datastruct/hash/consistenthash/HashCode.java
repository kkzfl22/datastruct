package com.liujun.datastruct.base.datastruct.hash.consistenthash;

/**
 * 传统hash的问题
 *
 * @author liujun
 * @version 0.0.1
 */
public class HashCode {

  /**
   * hash函数
   *
   * @param key
   * @return
   */
  public static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
  }
}
