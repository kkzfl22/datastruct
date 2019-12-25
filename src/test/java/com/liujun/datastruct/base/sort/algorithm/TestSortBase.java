package com.liujun.datastruct.base.sort.algorithm;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/16
 */
public abstract class TestSortBase {

  public List<Object[]> geteData() {
    List<Object[]> resultList = new ArrayList<>();

    resultList.add(new Short[] {8, 6, 8, 9, 3, 5, 7});
    resultList.add(new Integer[] {5, 3, 8, 2, 7, 1});
    resultList.add(new Integer[] {4, 5, 6, 3, 2, 1});
    resultList.add(new Long[] {4l, 9l, 7l, 6l, 5l, 4l, 10l, 13l});
    resultList.add(new Character[] {'2', '5', '4', '7', '6', '5', '3'});
    resultList.add(new Float[] {2.0f, 1.0f, 5.0f, 3.2f, 7.1f, 4f, 6f, 9.2f});
    resultList.add(new Double[] {1.0d, 9.0d, 5.0d, 3.2d, 7.5d, 8d, 10d, 6.2d});

    // 随机放入100组数据
    for (int i = 0; i < 1000; i++) {
      resultList.add(this.randData(250));
    }
    return resultList;
  }

  private Integer[] randData(int randomNum) {
    Integer[] data = new Integer[randomNum];

    for (int i = 0; i < randomNum; i++) {
      data[i] = RandomUtils.nextInt(0, 100);
    }

    return data;
  }

  @Test
  public void runSort() {
    List<Object[]> result = this.geteData();

    SortInf instance = getInstance();

    for (Object[] data : result) {
      Object[] targetSelf = dataCopy(data);
      instance.sort(targetSelf);
      Object[] targetArrays = dataCopy(data);
      Arrays.sort(targetArrays);
      Assert.assertEquals(targetSelf[0], targetArrays[0]);
      Assert.assertEquals(targetSelf[targetSelf.length / 2], targetArrays[targetArrays.length / 2]);
      Assert.assertEquals(targetSelf[targetSelf.length - 1], targetArrays[targetArrays.length - 1]);
    }
  }

  private Object[] dataCopy(Object[] src) {
    if (null == src || src.length == 0) {
      return new Object[0];
    }
    Object[] target = new Object[src.length];

    for (int i = 0; i < src.length; i++) {
      target[i] = src[i];
    }

    return target;
  }

  public abstract SortInf getInstance();
}
