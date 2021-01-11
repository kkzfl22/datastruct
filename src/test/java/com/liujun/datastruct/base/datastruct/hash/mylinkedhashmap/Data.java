package com.liujun.datastruct.base.datastruct.hash.mylinkedhashmap;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Data implements Comparable<Data> {
  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Data(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public int hashCode() {
    return id%8;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Data data = (Data) o;
    return id == data.id && name.equals(data.name);
  }

  @Override
  public int compareTo(Data o) {
    if (this.id < o.id) {
      return 1;
    } else if (this.id > o.id) {
      return -1;
    }

    return 0;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Data{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
