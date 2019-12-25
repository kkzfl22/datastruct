package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import com.liujun.datastruct.base.sort.algorithm.SortInf;
import org.junit.Test;

import java.util.Arrays;

/**
 * 进行归并排序的测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/24
 */
public class TestMergerSort {

  private SortInf<Integer> instance = new MergerSort<>();

  public SortInf getInstance() {
    return instance;
  }

  @Test
  public void testSort() {
    Integer[] data = new Integer[] {5, 3, 8, 2, 7, 1};
    System.out.println("当前前第:-次：" + Arrays.toString(data));
    instance.sort(data);
    System.out.println("结果:" + Arrays.toString(data));
  }
}
