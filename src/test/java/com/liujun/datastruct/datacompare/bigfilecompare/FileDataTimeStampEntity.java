package com.liujun.datastruct.datacompare.bigfilecompare;

import com.config.Symbol;

import java.util.Arrays;

/**
 * 内容的实体信息
 *
 * @author liujun
 * @version 0.0.1
 */
public class FileDataTimeStampEntity implements Comparable<FileDataTimeStampEntity> {

  /** 主键信息 */
  private int key;

  /** 时间戳 */
  private long timeStamp;

  /** 数据信息 */
  private String[] item;

  public static FileDataTimeStampEntity lineToEntity(String line) {
    String[] data = line.split(Symbol.COMMA);

    FileDataTimeStampEntity entity = new FileDataTimeStampEntity();
    entity.setKey(Integer.parseInt(data[0]));
    entity.setTimeStamp(Long.parseLong(data[1]));
    entity.setItem(data);

    return entity;
  }

  public String entityToLine() {
    if (null == item) {
      return Symbol.EMPTY;
    }

    StringBuilder outLine = new StringBuilder();
    for (String dataItem : item) {
      outLine.append(dataItem).append(Symbol.COMMA);
    }

    outLine.deleteCharAt(outLine.length() - 1);

    return outLine.toString();
  }

  @Override
  public int compareTo(FileDataTimeStampEntity o) {

    if (this.getKey() < o.getKey()) {
      return -1;
    } else if (this.getKey() > o.getKey()) {
      return 1;
    }

    // 当主键相同时，则比较时间戳
    if (this.getTimeStamp() < o.getTimeStamp()) {
      return -1;
    } else if (this.getTimeStamp() > o.getTimeStamp()) {
      return 1;
    }

    return 0;
  }

  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String[] getItem() {
    return item;
  }

  public void setItem(String[] item) {
    this.item = item;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("FileDataTimeStampEntity{");
    sb.append("key=").append(key);
    sb.append(", timeStamp=").append(timeStamp);
    sb.append(", item=").append(Arrays.toString(item));
    sb.append('}');
    return sb.toString();
  }
}
