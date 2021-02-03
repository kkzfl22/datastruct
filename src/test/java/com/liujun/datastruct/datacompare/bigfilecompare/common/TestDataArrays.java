package com.liujun.datastruct.datacompare.bigfilecompare.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestDataArrays {

  @Test
  public void testArrays() {
    DataArrays<Integer> instance = new DataArrays(Integer.class, 10);
    instance.set(5, 5);
    Assert.assertEquals(instance.get(5), (Integer) 5);
  }

  @Test
  public void testArraysClass() {
    DataArrays<DataValues> instance = new DataArrays(DataValues.class, 10);
    instance.set(5, new DataValues("test5"));
    Assert.assertEquals(instance.get(5).getName(), "test5");
  }

  @Test
  public void testArraysMin() {
    DataArrays<Integer> instance = new DataArrays(Integer.class, 10);
    instance.set(5, 5);
    instance.set(2, 2);
    instance.set(3, 8);
    instance.set(1, 9);
    System.out.println(instance.outList());
  }

  private class DataValues {
    private String name;

    public DataValues(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("data{");
      sb.append("name='").append(name).append('\'');
      sb.append('}');
      return sb.toString();
    }
  }
}
