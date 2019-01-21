package com.liujun.datastruct.base.sort;

import java.util.Arrays;

/**
 * 按年年龄对用户信息进行排序
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/31
 */
public class CountSortAge {

  public void sortAge(int[] age) {
    // 1,按年龄进行统计最小与最大值
    // int minAge = age[0];
    int maxAge = age[0];

    for (int i = 1; i < age.length; i++) {
      if (age[i] > maxAge) {
        maxAge = age[i];
      }

      //      else if (age[i] < minAge) {
      //        minAge = age[i];
      //      }
    }

    System.out.println("最小:" + maxAge + ",最大:" + maxAge);

    // int countLength = maxAge - minAge + 1;
    int countLength = maxAge + 1;

    int[] countAge = new int[countLength];

    // 2,声明一个数组进行统计，各年龄的人数
    for (int i = 0; i < age.length; i++) {
      countAge[age[i]]++;
    }

    System.out.println("统计结果:" + Arrays.toString(countAge));

    // 对数据统计的数据进行求和操作
    int[] countSumAge = new int[countLength];
    countSumAge[0] = countAge[0];
    for (int i = 1; i < countAge.length; i++) {
      countSumAge[i] = countSumAge[i - 1] + countAge[i];
    }

    System.out.println("求和结果:" + Arrays.toString(countSumAge));

    // 进行数据的排列操作
    int[] result = new int[age.length];

    for (int i = age.length - 1; i >= 0; i--) {
      int index = countSumAge[age[i]] - 1;
      System.out.println("当前计算索引:" + index);
      result[index] = age[i];
      countSumAge[age[i]]--;
    }

    System.out.println("结果" + Arrays.toString(result));
  }

  public void sortAgeOpTim(int[] age) {
    // 1,按年龄进行统计最小与最大值
    int minAge = age[0];
    int maxAge = age[0];

    for (int i = 1; i < age.length; i++) {
      if (age[i] > maxAge) {
        maxAge = age[i];
      } else if (age[i] < minAge) {
        minAge = age[i];
      }
    }

    System.out.println("最小:" + minAge + ",最大:" + maxAge);

    int countLength = maxAge - minAge + 1;

    int[] countAge = new int[countLength];

    // 2,声明一个数组进行统计，各年龄的人数
    for (int i = 0; i < age.length; i++) {
      countAge[age[i] - minAge]++;
    }

    System.out.println("统计结果:" + Arrays.toString(countAge));

    // 对数据统计的数据进行求和操作

    for (int i = 1; i < countAge.length; i++) {
      countAge[i] = countAge[i - 1] + countAge[i];
    }

    System.out.println("求和结果:" + Arrays.toString(countAge));

    // 进行数据的排列操作
    int[] result = new int[age.length];

    for (int i = age.length - 1; i >= 0; i--) {
      int index = countAge[age[i] - minAge] - 1;
      result[index] = age[i];
      countAge[age[i] - minAge]--;
    }

    System.out.println("结果" + Arrays.toString(result));
  }
}
