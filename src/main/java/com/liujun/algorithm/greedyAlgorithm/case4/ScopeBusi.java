package com.liujun.algorithm.greedyAlgorithm.case4;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/17
 */
public class ScopeBusi {

  /** 启点 */
  private int start;

  /** 终点 */
  private int end;

  public ScopeBusi(int start, int end) {
    this.start = start;
    this.end = end;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getEnd() {
    return end;
  }

  public void setEnd(int end) {
    this.end = end;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ScopeBusi{");
    sb.append("start=").append(start);
    sb.append(", end=").append(end);
    sb.append('}');
    return sb.toString();
  }
}
