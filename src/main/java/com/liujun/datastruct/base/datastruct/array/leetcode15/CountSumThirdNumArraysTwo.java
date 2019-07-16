package com.liujun.datastruct.base.datastruct.array.leetcode15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * a+b+c=0
 *
 * <p>进行求解，使用两层循环来解决
 *
 * <p>使用hashSet来解决复杂的问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/15
 */
public class CountSumThirdNumArraysTwo {

  public static final CountSumThirdNumArraysTwo INSTANCE = new CountSumThirdNumArraysTwo();

  /**
   * 计算三数之和
   *
   * @param arrays
   * @return
   */
  public List<List<Integer>> threeSum(int[] arrays) {

    List<List<Integer>> reslut = new ArrayList<>();

    // 如果当前的值都为0，则直接返回三个0
    if (null == arrays || arrays.length < 3) {
      return reslut;
    }

    // 容器进行存储,使用hash可以防止重复操作
    HashSet<List<Integer>> collectValue = new HashSet<>();

    // 进行数据进行排序操作
    Arrays.sort(arrays);

    for (int i = 0; i <= arrays.length - 3; i++) {
      int low = i + 1;
      int hight = arrays.length - 1;

      while (low < hight) {
        int sum = arrays[i] + arrays[low] + arrays[hight];

        if (sum == 0) {
          List<Integer> item = new ArrayList<>(3);
          item.add(arrays[i]);
          item.add(arrays[low]);
          item.add(arrays[hight]);

          if (!collectValue.contains(item)) {
            collectValue.add(item);
            reslut.add(item);
          }

          // 进指针的移动操作
          hight--;
          low++;

        } else if (sum > 0) {
          hight--;
        } else if (sum < 0) {
          low++;
        }
      }
    }

    return reslut;
  }
}
