package com.liujun.datastruct.datastruct.heap.solution.bigFileTopN.pojo;

/**
 * 关键字信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/02
 */
public class KeyBusi {

  /** 关键字 */
  private String key;

  /** 统计的关键字次数 */
  private int countNum;

  public KeyBusi(String key, int countNum) {
    this.key = key;
    this.countNum = countNum;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public int getCountNum() {
    return countNum;
  }

  public void setCountNum(int countNum) {
    this.countNum = countNum;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("KeyBusi{");
    sb.append("key='").append(key).append('\'');
    sb.append(", countNum=").append(countNum);
    sb.append('}');
    return sb.toString();
  }
}
