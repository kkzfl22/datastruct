package com.liujun.datastruct.algorithm.greedyAlgorithm.case1;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/17
 */
public class Goods {

  /** 物品的名称 */
  private String name;

  /** 重量 */
  private int wight;

  /** 总价值 */
  private float value;

  /** 单价 */
  private float unitPrice;

  public Goods(String name, int wight, float value) {
    this.name = name;
    this.wight = wight;
    this.value = value;
    // 计算物品单价
    this.unitPrice = value / wight;
  }

    public Goods(String name, int wight, float value, float unitPrice) {
        this.name = name;
        this.wight = wight;
        this.value = value;
        this.unitPrice = unitPrice;
    }

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getWight() {
    return wight;
  }

  public void setWight(int wight) {
    this.wight = wight;
  }

  public float getValue() {
    return value;
  }

  public void setValue(float value) {
    this.value = value;
  }

  public float getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(float unitPrice) {
    this.unitPrice = unitPrice;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Goods{");
    sb.append("name='").append(name).append('\'');
    sb.append(", wight=").append(wight);
    sb.append(", value=").append(value);
    sb.append(", unitPrice=").append(unitPrice);
    sb.append('}');
    return sb.toString();
  }
}
