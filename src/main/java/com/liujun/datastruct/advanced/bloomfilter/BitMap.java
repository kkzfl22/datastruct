package com.liujun.datastruct.advanced.bloomfilter;

/**
 * 由降过滤器的基础存储结构
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/15
 */
public class BitMap {

  /** 用来进行数据存储的结构 */
  private char[] bytes;

  /** 存储结构的大小 */
  private int nbits;

  public BitMap(int nbits) {
    this.nbits = nbits;
    this.bytes = new char[nbits / 8 + 1];
  }

  public void set(int k) {
    // 超过范围直接返回
    if (k > nbits) {
      return;
    }
    int byteIndex = k / 8;
    int bitIndex = k % 8;
    bytes[byteIndex] |= (1 << bitIndex);
  }

  public boolean get(int k) {
    if (k > nbits) {
      return false;
    }
    int byteIndex = k / 8;
    int bitIndex = k % 8;
    return (bytes[byteIndex] & (1 << bitIndex)) != 0;
  }
}
