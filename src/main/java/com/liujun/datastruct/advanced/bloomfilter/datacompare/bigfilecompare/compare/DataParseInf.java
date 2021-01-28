package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare;

/**
 * 大型文件对比
 *
 * @author liujun
 * @version 0.0.1
 */
public interface DataParseInf<V> {

  /**
   * java对象转化为行
   *
   * @param data
   * @return
   */
  String toFileLine(V data);

  /**
   * 行结果转化为java对象
   *
   * @param line
   * @return
   */
  V lineToData(String line);
}
