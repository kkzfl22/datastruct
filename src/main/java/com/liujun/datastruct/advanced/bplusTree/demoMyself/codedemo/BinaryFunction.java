package com.liujun.datastruct.advanced.bplusTree.demoMyself.codedemo;

import java.util.List;

/**
 * 二分查找数据算法
 *
 * @author liujun
 * @since 2022/11/8
 */
public class BinaryFunction {

  // public static <T> int indexedBinarySearch(List<? extends Comparable<? super T>> list, T key) {
  //  int low = 0;
  //  int high = list.size() - 1;
  //
  //  while (low <= high) {
  //    int mid = (low + high) >>> 1;
  //    Comparable<? super T> midVal = list.get(mid);
  //    int cmp = midVal.compareTo(key);
  //
  //    if (cmp < 0) low = mid + 1;
  //    else if (cmp > 0) high = mid - 1;
  //    else return mid; // key found
  //  }
  //  return -(low + 1); // key not found
  // }

  /**
   * 进行通用的二分查找
   *
   * @param list 集合数据
   * @param key 查找的值
   * @param <T> 查找的键
   * @return
   */
  public static <T> int indexedBinarySearch(List<? extends Comparable<? super T>> list, T key) {
    int low = 0;
    int high = list.size() - 1;

    while (low <= high) {
      // 此为除2操作,也可使用移位
      int mid = (high + low) >>> 1;
      Comparable<? super T> midVal = list.get(mid);

      int compRsp = midVal.compareTo(key);

      // 如果当前小于0，则当值说明值在右区间
      if (compRsp < 0) {
        low = mid + 1;
      }
      // 如果>0,说明在右区间
      else if (compRsp > 0) {
        high = mid - 1;
      }
      // 如果相同，则就是查找的数据
      else {
        return mid;
      }
    }

    // 如果找不则，则返回为负数当前要插入的位置
    return -(low + 1);
  }
}
