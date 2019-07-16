package com.liujun.datastruct.base.datastruct.array.leetcode15;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * a+b+c=0
 *
 * <p>进行暴力求解，此解决方案的时间复杂度为n的5次方,复杂度太高
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/15
 */
public class CountSumThirdNumArrays {

  public static final CountSumThirdNumArrays INSTANCE = new CountSumThirdNumArrays();

  /**
   * 计算三数之和
   *
   * @param arrays
   * @return
   */
  public List<List<Integer>> threeSum(int[] arrays) {

    List<List<Integer>> reslut = new ArrayList<>();

    for (int i = 0; i < arrays.length; i++) {

      for (int j = 0; j < arrays.length; j++) {

        for (int k = 0; k < arrays.length; k++) {
          if (i != j && j != k && i != k) {
            int zone = arrays[i] + arrays[j] + arrays[k];

            if (zone == 0) {
              if (!check(i, j, k, arrays, reslut)) {
                reslut.add(this.getResult(i, j, k, arrays));
              }
            }
          }
        }
      }
    }

    return reslut;
  }

  /**
   * 封装结果为集合
   *
   * @param i 数据1
   * @param j 数据2
   * @param k 数据3
   * @return
   */
  private List<Integer> getResult(int i, int j, int k, int[] arrays) {
    List<Integer> result = new ArrayList<>();

    result.add(arrays[i]);
    result.add(arrays[j]);
    result.add(arrays[k]);

    return result;
  }

  /**
   * 检查这几项数据在集合中是否已经存在
   *
   * @param i 第一1上数据
   * @param j 第二个数据
   * @param k 第三个数据
   * @param collection 容器对象
   * @return true 已经存在，false 不存在
   */
  private boolean check(int i, int j, int k, int[] datas, List<List<Integer>> collection) {
    for (List<Integer> resp : collection) {
      if (check(resp, i, j, k, datas)) {
        return true;
      }
    }

    return false;
  }

  /**
   * 检查当前数据在集合中是否存在
   *
   * @param resp 存储数据集合
   * @param i 第一个数据
   * @param j 第二个数据
   * @param k 第三个数据
   * @return
   */
  private boolean check(List<Integer> resp, int i, int j, int k, int[] datas) {

    if (datas[i] == 0 && datas[j] == 0 && datas[k] == 0 && i != j && j != k && i != k) {

      // 检查下是否存在3个0的情况
      for (int valueItem : resp) {
        if (valueItem != 0) {
          return false;
        }
      }

      return true;
    }

    return resp.contains(datas[i]) && resp.contains(datas[j]) && resp.contains(datas[k]);
  }
}
