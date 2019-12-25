package com.liujun.datastruct.base.sort.algorithm.implement.myself;

import com.liujun.datastruct.base.sort.algorithm.SortInf;
import com.liujun.datastruct.base.sort.algorithm.TestSortBase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * 快速排序
 *
 * <p>采用随机数的方法选取分区点进行排序操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/11/07
 */
public class TestQuickSortRandomPoint extends TestSortBase {

  private SortInf<Integer> instance = new QuickSortRandomPoint<>();

  @Override
  public SortInf getInstance() {
    return instance;
  }

  @Test
  public void testSort() {
    // Integer[] data = new Integer[] {5, 3, 8, 2, 7, 1};
    Integer[] data = new Integer[] {8, 6, 8, 9, 3, 5, 7};
    System.out.println("当前前第:-次：" + Arrays.toString(data));
    instance.sort(data);
    System.out.println(Arrays.toString(data));
  }

  /** 测试分区函数1 */
  @Test
  public void testParttern1() {
    QuickSortThirdMod<Integer> inst = new QuickSortThirdMod();

    Integer[] src = new Integer[] {8, 6, 8, 9, 3, 5, 7};
    int midValue = inst.dataMid(src, 0, src.length - 1);
    System.out.println("数据结果:" + src[midValue] + "index:" + midValue);
  }

  /** 测试分区函数1 */
  @Test
  public void testParttern2() {
    QuickSortThirdMod<Float> inst = new QuickSortThirdMod();

    Float[] src = new Float[] {2.0f, 1.0f, 5.0f, 3.2f, 7.1f, 4f, 6f, 9.2f};
    int midValue = inst.dataMid(src, 0, src.length - 1);
    System.out.println("数据结果:" + src[midValue] + "index:" + midValue);
    Assert.assertEquals(3, midValue);
  }

  /** 测试分区函数2 */
  @Test
  public void testParttern3() {
    QuickSortThirdMod<Short> inst = new QuickSortThirdMod();

    Short[] src = new Short[] {8, 6, 8, 9, 3, 5, 7};
    int midValue = inst.dataMid(src, 0, src.length - 1);
    System.out.println("数据结果:" + src[midValue] + "index:" + midValue);
    Assert.assertEquals(0, midValue);
  }
}
