package com.liujun.datastruct.datacompare.bigfilecompare;

import com.config.Symbol;

import java.util.Arrays;

/**
 * 内容的实体信息
 *
 * @author liujun
 * @version 0.0.1
 */
public class FileDataEntity implements Comparable<FileDataEntity> {

  /** 主键信息 */
  private int key;

  private String[] item;

  public static FileDataEntity lineToEntity(String line) {
    String[] data = line.split(Symbol.COMMA);

    FileDataEntity entity = new FileDataEntity();
    entity.setKey(Integer.parseInt(data[0]));
    entity.setItem(data);

    return entity;
  }

  public String entityToLine() {
    if (null == item) {
      return Symbol.EMPTY;
    }

    StringBuilder outLine = new StringBuilder();
    outLine.append(key).append(Symbol.COMMA);
    for (String dataItem : item) {
      outLine.append(dataItem).append(Symbol.COMMA);
    }

    outLine.deleteCharAt(outLine.length() - 1);

    return outLine.toString();
  }

  @Override
  public int compareTo(FileDataEntity o) {

    if (this.getKey() < o.getKey()) {
      return -1;
    } else if (this.getKey() > o.getKey()) {
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

  public String[] getItem() {
    return item;
  }

  public void setItem(String[] item) {
    this.item = item;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("FileDataEntity{");
    sb.append("key=").append(key);
    sb.append(", item='").append(Arrays.toString(item)).append('\'');

    sb.append('}');
    return sb.toString();
  }
}
