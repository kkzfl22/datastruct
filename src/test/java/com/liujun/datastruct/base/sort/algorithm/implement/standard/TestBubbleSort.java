package com.liujun.datastruct.base.sort.algorithm.implement.standard;

import com.liujun.datastruct.base.sort.algorithm.SortInf;
import com.liujun.datastruct.base.sort.algorithm.TestSortBase;
import org.junit.Test;

import java.util.Arrays;

/**
 * 进行标准的冒泡排序操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/19
 */
public class TestBubbleSort extends TestSortBase {

  private SortInf<Integer> optimizeInstance = new BubbleSort<>();

  @Override
  public SortInf getInstance() {
    return optimizeInstance;
  }

  @Test
  public void testSort() {
    Integer[] data = new Integer[] {5, 3, 8, 2, 7, 1};
    System.out.println("当前前第:-次：" + Arrays.toString(data));
    optimizeInstance.sort(data);
    System.out.println(Arrays.toString(data));
  }
}
