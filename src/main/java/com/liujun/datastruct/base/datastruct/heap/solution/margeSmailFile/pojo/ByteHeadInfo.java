package com.liujun.datastruct.base.datastruct.heap.solution.margeSmailFile.pojo;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/30
 */
public class ByteHeadInfo {

  /** 当前存储的byte值信息 */
  private byte value;

  /** 文件所有的索引信息 */
  private int index;

  public ByteHeadInfo(byte value, int index) {
    this.value = value;
    this.index = index;
  }

  public byte getValue() {
    return value;
  }

  public void setValue(byte value) {
    this.value = value;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ByteHeadInfo{");
    sb.append("value=").append(value);
    sb.append(", index=").append(index);
    sb.append('}');
    return sb.toString();
  }
}
