package com.liujun.datastruct.advanced.bloomfilter.datacompare.listcompare.entity;

/**
 * 数据修改的对象
 *
 * @author liujun
 * @version 0.0.1
 */
public class UpdateObjectEntity<V> {

  /** 原对象 */
  private V before;

  /** 修改后的对象 */
  private V after;

  public V getBefore() {
    return before;
  }

  public void setBefore(V before) {
    this.before = before;
  }

  public V getAfter() {
    return after;
  }

  public void setAfter(V after) {
    this.after = after;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UpdateObjectEntity{");
    sb.append("before=").append(before);
    sb.append(", after=").append(after);
    sb.append('}');
    return sb.toString();
  }
}
