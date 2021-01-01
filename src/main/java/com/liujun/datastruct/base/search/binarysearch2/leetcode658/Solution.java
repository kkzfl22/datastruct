package com.liujun.datastruct.base.search.binarysearch2.leetcode658;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 先用二分查找找到与X最接近的下标i, 再从i往前后截取k成为新数组 再用双指针在最前最后判断去掉差值更大的那一边直到数组长度等于K
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public List<Integer> findClosestElements(int[] arr, int k, int x) {

    if (arr == null || arr.length < 1) {
      return Collections.emptyList();
    }

    // 查找最接近的下标
    int matchIndex = -1;

    int low = 0, mid, high = arr.length - 1;
    while (low <= high) {
      mid = low + (high - low) / 2;

      // 找到最接近x的下标
      if (arr[mid] <= x) {
        if (mid == high || arr[mid + 1] > x) {
          matchIndex = mid;
          break;
        } else {
          low = mid + 1;
        }
      } else {
        high = mid - 1;
      }
    }

    // 获取数组开始位置
    int start = Math.max(matchIndex - k, 0);
    int end = Math.min(matchIndex + k, arr.length - 1);
    int length = end - start + 1;
    int[] arrayData = new int[length];
    System.arraycopy(arr, start, arrayData, 0, length);

    int startIndex = 0, endindex = length - 1;
    while ((endindex - startIndex + 1) > k) {
      // 1,检查左指针是否满足小于右指针
      if (Math.abs(arrayData[startIndex] - x) > Math.abs(arrayData[endindex] - x)) {
        startIndex = startIndex + 1;
      } else {
        endindex = endindex - 1;
      }
    }

    // 返回的结果集
    List<Integer> dataList = new ArrayList<>(k);
    for (int i = startIndex; i <= endindex; i++) {
      dataList.add(arrayData[i]);
    }

    return dataList;
  }


}
