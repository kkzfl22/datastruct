package com.liujun.datastruct.base.datastruct.array.leetcode15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * a+b+c=0
 *
 * <p>leetcode上排名靠前的解法,不使用hashset来进行存储，
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/15
 */
public class CountSumThirdNumArraysThird {

  public static final CountSumThirdNumArraysThird INSTANCE = new CountSumThirdNumArraysThird();

  /**
   * 计算三数之和
   *
   * @param arrays
   * @return
   */
  public List<List<Integer>> threeSum(int[] arrays) {

    List<List<Integer>> reslut = new ArrayList<>();

    // 进行数据进行排序操作
    Arrays.sort(arrays);

    // 如果当前的值都为0，则直接返回三个0
    if (null == arrays || arrays.length < 3) {
      return reslut;
    }

    for (int i = 0; i < arrays.length; i++) {

      // 直接排除最小值大于0的情况
      if (arrays[i] > 0) {
        break;
      }
      // 排除掉i与i-1相同的情况
      if (i > 0 && arrays[i - 1] == arrays[i]) {
        continue;
      }

      int low = i + 1;
      int hight = arrays.length - 1;

      while (low < hight) {
        int sum = arrays[i] + arrays[low] + arrays[hight];

        if (sum == 0) {
          reslut.add(Arrays.asList(arrays[i], arrays[low], arrays[hight]));

          while (low < hight && arrays[low] == arrays[low + 1]) {
            low++;
          }

          low++;
          hight--;
        } else if (sum < 0) {
          low++;
        } else if (sum > 0) {
          hight--;
        }
      }
    }

    return reslut;
  }
}
