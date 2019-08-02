package com.liujun.datastruct.base.datastruct.array;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 测试合并数组
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/30
 */
public class TestMergeSortArray {

  @Test
  public void mergeArray() {
    int[] data1 = new int[] {1, 2, 3, 4, 5, 6, 7};
    int[] data2 = new int[] {1, 2, 3, 4, 5, 6, 7};
    MergeSortArray array = new MergeSortArray();
    List<Integer> reslut = array.mergeSortArray(data1, data2);
    System.out.println(reslut);
    Assert.assertEquals(data1.length + data2.length, reslut.size());
  }

  @Test
  public void mergeArray2() {
    int[] data1 = new int[] {1, 3, 5, 7, 9};
    int[] data2 = new int[] {2, 4, 6, 8, 10};
    MergeSortArray array = new MergeSortArray();
    List<Integer> reslut = array.mergeSortArray(data1, data2);
    System.out.println(reslut);
    Assert.assertEquals(data1.length + data2.length, reslut.size());
  }

  @Test
    public void mergeArray3() {
        int[] data1 = new int[] {1, 3, 5, 7, 9};
        int[] data2 = new int[] {2};
        MergeSortArray array = new MergeSortArray();
        List<Integer> reslut = array.mergeSortArray(data1, data2);
        System.out.println(reslut);
        Assert.assertEquals(data1.length + data2.length, reslut.size());
    }

    @Test
    public void mergeArray4() {
        int[] data1 = new int[] {1};
        int[] data2 = new int[] {2};
        MergeSortArray array = new MergeSortArray();
        List<Integer> reslut = array.mergeSortArray(data1, data2);
        System.out.println(reslut);
        Assert.assertEquals(data1.length + data2.length, reslut.size());
    }
}
