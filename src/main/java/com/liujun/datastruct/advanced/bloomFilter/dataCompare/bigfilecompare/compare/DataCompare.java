package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.entity.DataCompareRsp;
import com.liujun.datastruct.utils.FileUtils;

import java.io.IOException;
import java.util.List;

/**
 * 数据对比操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class DataCompare<V> {

  public static final String SRC_BLOOM_PATH = "bloom_src";

  public static final String TARGET_BLOOM_PATH = "bloom_target";

  /** 原始数据填充布隆过滤器 */
  private final BoomFilterManager srcBloomFilter;

  /** 目标数据填充布隆过滤器 */
  private final BoomFilterManager targetBloomFilter;

  /** 相关生成对比函数信息 */
  private final BigCompareKeyInf<V> compareKeyInterface;

  /** java行与对象之彰的转换操作 */
  private final DataParseInf<V> dataParse;

  /** 保存数据的目录 */
  private final String savePath;

  public DataCompare(
      BigCompareKeyInf<V> compareKeyInterface,
      DataParseInf<V> dataParse,
      long srcDataNum,
      long targetDataNum,
      String savePath) {
    this.srcBloomFilter = new BoomFilterManager(srcDataNum);
    this.targetBloomFilter = new BoomFilterManager(targetDataNum);
    this.compareKeyInterface = compareKeyInterface;
    this.dataParse = dataParse;
    this.savePath = savePath;
  }

  public DataCompare(
      BigCompareKeyInf<V> compareKeyInterface, DataParseInf<V> dataParse, String savePath) {
    this.compareKeyInterface = compareKeyInterface;
    this.dataParse = dataParse;
    this.savePath = savePath;
    this.srcBloomFilter = this.loadSrc();
    this.targetBloomFilter = this.loadTarget();
  }

  /**
   * 将一批数据放入到布隆过滤器中
   *
   * @param data
   */
  public void putSrcData(V data) {
    this.putData(srcBloomFilter, data);
  }

  /**
   * 将一批数据目标数据放入到布隆过滤器中
   *
   * @param data
   */
  public void putTargetData(V data) {
    this.putData(targetBloomFilter, data);
  }

  /**
   * 将一批数据放入到布隆过滤器中
   *
   * @param dataList
   */
  public void putSrcList(List<V> dataList) {
    for (V itemData : dataList) {
      this.putData(srcBloomFilter, itemData);
    }
  }

  /**
   * 将一批数据目标数据放入到布隆过滤器中
   *
   * @param dataList 数据集
   */
  public void putTargetList(List<V> dataList) {
    for (V itemData : dataList) {
      this.putData(targetBloomFilter, itemData);
    }
  }

  /**
   * 集合数据加入到布隆过滤器中
   *
   * @param bloomFilter
   * @param data
   */
  private void putData(BoomFilterManager bloomFilter, V data) {
    if (null == data) {
      return;
    }
    // 1,将数据放入到布隆过滤器中
    bloomFilter.put(compareKeyInterface.getKey(data));
    bloomFilter.put(compareKeyInterface.getKeyMany(data));
  }

  /**
   * 对比目标数据集信息
   *
   * @param dataList 数据集
   * @throws IOException
   */
  public DataCompareRsp compareTargetList(List<V> dataList) {

    DataCompareRsp rsp = new DataCompareRsp();
    // 初始化原始结果集合
    rsp.initCompareSrc();
    // 数据遍历对比操作
    for (V dataLine : dataList) {
      this.compareTarget(dataLine, rsp);
    }
    return rsp;
  }

  /**
   * 通过当前的目标数据找出添加的记录
   *
   * @param valueInfo 目标数据
   * @return
   */
  public void compareTarget(V valueInfo, DataCompareRsp rsp) {
    if (valueInfo == null) {
      return;
    }

    String key = compareKeyInterface.getKey(valueInfo);
    String keyMany = compareKeyInterface.getKeyMany(valueInfo);

    // 布隆过滤器不存在的，一定是不存的,仅存在的，可能会存在误差问题
    if (!srcBloomFilter.mightContain(key)) {
      rsp.getAddList().add(dataParse.toFileLine(valueInfo));
    }
    // 如果主键能找到，多个对比数据不存在，则执行修改操作
    else if (srcBloomFilter.mightContain(key) && !srcBloomFilter.mightContain(keyMany)) {
      rsp.getUpdateAfterList().add(dataParse.toFileLine(valueInfo));
    }
  }

  /**
   * 对比目标数据集信息
   *
   * @param dataList 数据集
   * @throws IOException
   */
  public DataCompareRsp compareSrcList(List<V> dataList) {

    DataCompareRsp rsp = new DataCompareRsp();
    // 初始化原始结果集合
    rsp.initCompareTarget();

    for (V dataLine : dataList) {
      this.compareSrc(dataLine, rsp);
    }

    return rsp;
  }

  /**
   * 通过原始数据的找到不在目标数据中，也就是要删除的记录
   *
   * @param dataInfo 原始数据类型
   * @return
   */
  public void compareSrc(V dataInfo, DataCompareRsp rsp) {

    if (dataInfo == null) {
      return;
    }

    String key = compareKeyInterface.getKey(dataInfo);
    String keyMany = compareKeyInterface.getKeyMany(dataInfo);

    // 布隆过滤器不存在的，一定是不存的,仅存在的，可能会存在误差问题
    if (!targetBloomFilter.mightContain(key)) {
      rsp.getDeleteList().add(dataParse.toFileLine(dataInfo));
    }
    // 如果主键能找到，多个对比数据不存在，则执行修改操作
    else if (targetBloomFilter.mightContain(key) && !targetBloomFilter.mightContain(keyMany)) {
      rsp.getUpdateBeforeList().add(dataParse.toFileLine(dataInfo));
    }
  }

  /** 保存数据 */
  public void save() {
    FileUtils.checkAndMakeDir(this.savePath + Symbol.PATH + SRC_BLOOM_PATH);
    // 原数据的布隆过滤器保存
    srcBloomFilter.save(this.savePath + Symbol.PATH + SRC_BLOOM_PATH);
    FileUtils.checkAndMakeDir(this.savePath + Symbol.PATH + TARGET_BLOOM_PATH);
    // 目标数据的布隆过滤器保存
    targetBloomFilter.save(this.savePath + Symbol.PATH + TARGET_BLOOM_PATH);
  }

  public BoomFilterManager loadSrc() {
    // 原数据的布隆过滤器保存
    return new BoomFilterManager(this.savePath + Symbol.PATH + SRC_BLOOM_PATH);
  }

  public BoomFilterManager loadTarget() {
    // 数据的布隆过滤器加载
    return new BoomFilterManager(this.savePath + Symbol.PATH + TARGET_BLOOM_PATH);
  }
}
