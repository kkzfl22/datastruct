package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import com.liujun.datastruct.base.sort.algorithm.SortInf;
import com.liujun.datastruct.base.sort.algorithm.TestSortBase;
import org.junit.Test;

import java.util.Arrays;

/**
 * 测试插入排序
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/16
 */
public class TestSelectSort extends TestSortBase {

  private SortInf<Integer> instance = new SelectSort<>();

  @Override
  public SortInf getInstance() {
    return instance;
  }

  @Test
  public void testSort() {
    Integer[] data = new Integer[] {5, 3, 8, 2, 7, 1};
    System.out.println("当前前第:-次：" + Arrays.toString(data));
    instance.sort(data);
    System.out.println(Arrays.toString(data));
  }
}
