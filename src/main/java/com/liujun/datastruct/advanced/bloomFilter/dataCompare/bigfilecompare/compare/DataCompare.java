package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 数据对比操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class DataCompare<V> {

  /** 占用512K的空间 */
  private static final int DEFAULT_SIZE = 4194304;

  /** 出现冲突的概率4千分之一 */
  private static final double DEFAULT_FPP = 0.001;

  /** 原始数据填充布隆过滤器 */
  private final BloomFilter<String> srcBloomFilter;

  /** 目标数据填充布隆过滤器 */
  private final BloomFilter<String> targetBloomFilter;

  /** 相关生成对比函数信息 */
  private final BigCompareKeyInf<V> compareKeyInterface;

  /** 数据对比的结果存储 */
  private final FileDataCompareRsp dataCompRsp;

  /** java行与对象之彰的转换操作 */
  private final DataParseInf<V> dataParse;

  /** 修改之前的行计数 */
  private long updateNum;

  /** 统计总文件大小 */
  private long updateSumSize;

  public DataCompare(
      int size,
      double fpp,
      BigCompareKeyInf<V> compareKeyInterface,
      FileDataCompareRsp dataCompRsp,
      DataParseInf<V> dataParse) {
    this.srcBloomFilter =
        BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), size, fpp);
    this.targetBloomFilter =
        BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), size, fpp);
    this.compareKeyInterface = compareKeyInterface;
    this.dataCompRsp = dataCompRsp;
    this.dataParse = dataParse;

    // 打开对比结果文件通道
    dataCompRsp.openWriteFile();
  }

  /**
   * 获取实例对象
   *
   * @param compareKeyInterface
   * @return
   */
  public DataCompare(
      BigCompareKeyInf compareKeyInterface,
      FileDataCompareRsp dataCompRsp,
      DataParseInf<V> dataParse) {
    this(DEFAULT_SIZE, DEFAULT_FPP, compareKeyInterface, dataCompRsp, dataParse);
  }

  /**
   * 将一批数据放入到布隆过滤器中
   *
   * @param data
   */
  public void putSrcList(List<V> data) {
    this.putDataList(srcBloomFilter, data);
  }

  /**
   * 将一批数据目标数据放入到布隆过滤器中
   *
   * @param data
   */
  public void putTargetList(List<V> data) {
    this.putDataList(targetBloomFilter, data);
  }

  /**
   * 集合数据加入到布隆过滤器中
   *
   * @param bloomFilter
   * @param data
   */
  private void putDataList(BloomFilter<String> bloomFilter, List<V> data) {
    if (null == data || data.isEmpty()) {
      return;
    }
    // 1,将数据放入到布隆过滤器中
    for (V dataItem : data) {
      this.dataPutBloomFilterOne(bloomFilter, dataItem);
    }
  }

  /**
   * 单项数据增加至布隆过滤器中
   *
   * @param bloomFilter 布隆过滤器
   * @param dataItem 数据信息
   */
  private void dataPutBloomFilterOne(BloomFilter<String> bloomFilter, V dataItem) {
    if (null == dataItem) {
      return;
    }
    bloomFilter.put(compareKeyInterface.getKey(dataItem));
    bloomFilter.put(compareKeyInterface.getKeyMany(dataItem));
  }

  /**
   * 通过当前的目标数据找出添加的记录
   *
   * @param targetList 目标数据
   * @return
   */
  public void compareTarget(List<V> targetList) {

    // 检查不在过滤器中的数据
    for (V valueItem : targetList) {
      String key = compareKeyInterface.getKey(valueItem);
      String keyMany = compareKeyInterface.getKeyMany(valueItem);

      // 布隆过滤器不存在的，一定是不存的,仅存在的，可能会存在误差问题
      if (!srcBloomFilter.mightContain(key)) {
        dataCompRsp.writeAddData(dataParse.toFileLine(valueItem));
      }
      // 如果主键能找到，多个对比数据不存在，则执行修改操作
      else if (srcBloomFilter.mightContain(key) && !srcBloomFilter.mightContain(keyMany)) {
        String dataValue = dataParse.toFileLine(valueItem);
        dataCompRsp.writeUpdateAfterData(dataValue);
        // 对修改操作做计数
        updateNum++;
        // 统计内容字大小
        updateSumSize += dataValue.getBytes().length;
      }
    }
  }

  /**
   * 通过原始数据的找到不在目标数据中，也就是要删除的记录
   *
   * @param srcList 原始数据类型
   * @return
   */
  public void compareSrc(List<V> srcList) {

    // 检查不在过滤器中的数据
    for (V dataItem : srcList) {
      String key = compareKeyInterface.getKey(dataItem);
      String keyMany = compareKeyInterface.getKeyMany(dataItem);

      // 布隆过滤器不存在的，一定是不存的,仅存在的，可能会存在误差问题
      if (!targetBloomFilter.mightContain(key)) {
        dataCompRsp.writeDeleteData(dataParse.toFileLine(dataItem));
      }
      // 如果主键能找到，多个对比数据不存在，则执行修改操作
      else if (targetBloomFilter.mightContain(key) && !targetBloomFilter.mightContain(keyMany)) {
        dataCompRsp.writeUpdateBeforeData(dataParse.toFileLine(dataItem));
      }
    }
  }

  /**
   * 修改行数
   *
   * @return 行数
   */
  public long updSumNum() {
    return updateNum;
  }

  /**
   * 修改的总大小
   *
   * @return 总大小
   */
  public long updateSumSize() {
    return updateSumSize;
  }
}
