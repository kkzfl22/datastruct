package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.entity;

import com.config.Symbol;

/**
 * 内容的实体信息
 *
 * @author liujun
 * @version 0.0.1
 */
public class FileDataEntity implements Comparable<FileDataEntity> {

  /** 主键信息 */
  private int key;

  private String item1;

  private String item2;

  private String item3;

  private String item4;

  public static FileDataEntity lineToEntity(String line) {
    String[] data = line.split(Symbol.COMMA);

    FileDataEntity entity = new FileDataEntity();
    entity.setKey(Integer.parseInt(data[0]));
    entity.setItem1(data[1]);
    entity.setItem2(data[2]);
    entity.setItem3(data[3]);
    entity.setItem4(data[4]);

    return entity;
  }

  public String entityToLine() {
    StringBuilder outLine = new StringBuilder();

    outLine.append(key).append(Symbol.COMMA);
    outLine.append(item1).append(Symbol.COMMA);
    outLine.append(item2).append(Symbol.COMMA);
    outLine.append(item3).append(Symbol.COMMA);
    outLine.append(item4);

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

  public String getItem1() {
    return item1;
  }

  public void setItem1(String item1) {
    this.item1 = item1;
  }

  public String getItem2() {
    return item2;
  }

  public void setItem2(String item2) {
    this.item2 = item2;
  }

  public String getItem3() {
    return item3;
  }

  public void setItem3(String item3) {
    this.item3 = item3;
  }

  public String getItem4() {
    return item4;
  }

  public void setItem4(String item4) {
    this.item4 = item4;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("FileDataEntity{");
    sb.append("key=").append(key);
    sb.append(", item1='").append(item1).append('\'');
    sb.append(", item2='").append(item2).append('\'');
    sb.append(", item3='").append(item3).append('\'');
    sb.append(", item4='").append(item4).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
