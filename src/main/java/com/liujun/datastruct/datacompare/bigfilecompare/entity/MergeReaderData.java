package com.liujun.datastruct.datacompare.bigfilecompare.entity;

/**
 * 合并时的文件读取操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class MergeReaderData<V> implements Comparable<MergeReaderData<V>> {

  /** 数据实体信息 */
  private V dataEntity;

  /** 文件编号 */
  private int index;

  public MergeReaderData(V dataEntity, int index) {
    this.dataEntity = dataEntity;
    this.index = index;
  }

  public V getDataEntity() {
    return dataEntity;
  }

  @Override
  public int compareTo(MergeReaderData<V> o) {
    if (this.getDataEntity() == null || o == null) {
      return 0;
    }

    if (((Comparable) this.dataEntity).compareTo(o.getDataEntity()) == -1) {
      return -1;
    } else if (((Comparable) dataEntity).compareTo(o.getDataEntity()) == 1) {
      return 1;
    }

    return 0;
  }

  public void setDataEntity(V dataEntity) {
    this.dataEntity = dataEntity;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ReaderData{");
    sb.append("dataEntity=").append(dataEntity);
    sb.append(", index=").append(index);
    sb.append('}');
    return sb.toString();
  }
}
