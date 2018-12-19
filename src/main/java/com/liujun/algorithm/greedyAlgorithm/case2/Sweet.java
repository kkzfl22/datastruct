package com.liujun.algorithm.greedyAlgorithm.case2;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/17
 */
public class Sweet {

  /** 糖果的名称 */
  private String name;

  /** 糖果的数量 */
  private int num;

  /** 糖果的大小,类型 */
  private int type;

  public Sweet(String name, int num, int type) {
    this.name = name;
    this.num = num;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Sweet{");
    sb.append("name='").append(name).append('\'');
    sb.append(", num=").append(num);
    sb.append(", type=").append(type);
    sb.append('}');
    return sb.toString();
  }
}
