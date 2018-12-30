package com.liujun.math.chapter02Iterate;

/**
 * 使用二分法进行字符串的查询
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/30
 */
public class DichotomyStringFind {

  /**
   * 使用二法查询查找字符串
   *
   * @param src 排序好的原始字符串数组
   * @param value 查询的值的信息
   * @return 当前字符所有的索引下标
   */
  public int search(String[] src, String value) {

    if (null == src || src.length == 0) {
      return -1;
    }

    if (value == null || value.isEmpty()) {
      return -1;
    }

    // 开始进行二分的查找
    int startIndex = 0;
    int endIndex = src.length;
    int mid = startIndex + (endIndex - startIndex) / 2;

    do {
      // 检查当前中间值是否匹配查询值
      if (src[mid].equals(value)) {
        return mid;
      } else {
        // 如果当前匹配的字符大于匹配的串，则需要左区间中继续查询
        if (src[mid].compareTo(value) > 0) {
          endIndex = mid - 1;
        }
        // 如果当前匹配的字符小于匹配串，说明需要在右区间中查询
        else {
          startIndex = mid + 1;
        }
      }

      mid = startIndex + (endIndex - startIndex) / 2;
    } while ((mid <= endIndex));

    return -1;
  }
}
