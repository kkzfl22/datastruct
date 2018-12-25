package com.liujun.datastruct.algorithm.backtrackingAlgorithm.packageZoneOne;

/**
 * 背包中物品的信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/25
 */
public class PkgValue {

  /** 物品名称 */
  private String name;

  /** 物品的重量 */
  private int weight;

  /** 物品的总价值 */
  private int value;

  public PkgValue(String name, int weight, int value) {
    this.name = name;
    this.weight = weight;
    this.value = value;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("PkgValue{");
    sb.append("name='").append(name).append('\'');
    sb.append(", weight=").append(weight);
    sb.append(", value=").append(value);
    sb.append('}');
    return sb.toString();
  }
}
