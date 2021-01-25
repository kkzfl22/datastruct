package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare;

/**
 * 大型文件对比
 *
 * @author liujun
 * @version 0.0.1
 */
public interface BigCompareKeyInf<V> {

  /**
   * 获取的key的信息
   *
   * @param data
   * @return
   */
  String getKey(V data);

  /**
   * 多结果集对比的key
   *
   * @param data
   * @return
   */
  String getKeyMany(V data);
}
