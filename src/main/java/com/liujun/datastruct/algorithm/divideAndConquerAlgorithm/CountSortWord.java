package com.liujun.datastruct.algorithm.divideAndConquerAlgorithm;

/**
 * 给定一个数组，使用分治算法来统计有序对的个数
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/20
 */
public class CountSortWord {

  public int num;

  /**
   * 统计有序对的个数
   *
   * @param works
   */
  public void countSort(int[] works) {}

  /**
   * 此使用递归进行解决
   *
   * <p>主要思路： 1，将数据一分为2，统计左有序对个数，再统计右序对个数
   *
   * <p>2，当数据仅有两个时，并且左大于右，有序对个数加1
   *
   * <p>3，将左右两个数据的统计结果
   *
   * @param works
   * @param start
   * @param end
   */
  public void countSort(int[] works, int start, int end) {

    if (start >= end) {
      return;
    }

    // 1,将数据一分为二进行计算
    int mid = (end + start) / 2;
    countSort(works, start, mid);
    countSort(works, mid + 1, end);

    // 进行最终的数据合并操作
    merge(works, start, mid, end);
  }

  /**
   * 进行数据的合并操作
   *
   * <p>因为要构建逆序度，所以需要使用从大到小,先做排序操作，再做统计
   *
   * @param works
   * @param start
   * @param mid
   * @param end
   */
  public void merge(int[] works, int start, int mid, int end) {
    System.out.println("进入,start" + start + ",mid" + mid + ",end:" + end);

    int[] newarrays = new int[end - start + 1];

    int newArrayIndex = 0;

    int leftStart = start, rightstart = mid + 1;
    // 进行遍历
    while (leftStart <= mid && rightstart <= end) {
      // 如果左边大于右边
      if (works[leftStart] >= works[rightstart]) {
        //num += (mid - leftStart + 1);
        //此处统计的是每次比左区间有几个比右区间大，这样一总和就是所有的
        num += (mid - leftStart + 1);
        // num = num + 1;
        //        System.out.println(
        //            "当前计算:leftstart:"
        //                + leftStart
        //                + ",rightstart"
        //                + rightstart
        //                + "，num:"
        //                + num
        //                + ",start:"
        //                + start
        //                + ",mid:"
        //                + mid
        //                + ",end:"
        //                + end);
        newarrays[newArrayIndex] = works[rightstart];
        rightstart++;
        newArrayIndex++;
      }
      // 如果右小小于右边，则
      else {

        newarrays[newArrayIndex] = works[leftStart];
        leftStart++;
        newArrayIndex++;
      }
    }

    while (rightstart <= end) {
      newarrays[newArrayIndex] = works[rightstart];
      rightstart++;
      newArrayIndex++;
    }

    while (leftStart <= mid) {
      newarrays[newArrayIndex] = works[leftStart];
      leftStart++;
      newArrayIndex++;
    }

    // 将数据替换到原集合中
    for (int i = start; i <= end; i++) {
      works[i] = newarrays[i - start];
    }
  }
}
