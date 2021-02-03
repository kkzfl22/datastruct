package com.liujun.datastruct.datacompare.bigfilecompare.compare;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.DataCompareRsp;
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

  /** 原始数据主键的布隆过滤器 */
  public static final String SRC_KEY_BLOOM_PATH = "bloom_src_key";

  /** 原始数据完整记录的布隆过滤器 */
  public static final String SRC_FULL_BLOOM_PATH = "bloom_src_full";

  /** 目标数据的主键的布隆过滤器标识 */
  public static final String TARGET_KEY_BLOOM_PATH = "bloom_target_key";

  /** 目标数据的完整数据的布隆过滤器标识 */
  public static final String TARGET_FULL_BLOOM_PATH = "bloom_target_full";

  /** 原始数据的主键的布隆过滤器 */
  private final BoomFilterManager srcKeyBloomFilter;

  /** 原始数据的所有数据的布隆过滤器 */
  private final BoomFilterManager srcFullBloomFilter;

  /** 目标数据主键的布隆过滤器 */
  private final BoomFilterManager targetKeyBloomFilter;

  /** 目标数据的完整的布隆过滤器 */
  private final BoomFilterManager targetFullBloomFilter;

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
    this.srcKeyBloomFilter = new BoomFilterManager(srcDataNum);
    this.srcFullBloomFilter = new BoomFilterManager(srcDataNum);
    this.targetKeyBloomFilter = new BoomFilterManager(targetDataNum);
    this.targetFullBloomFilter = new BoomFilterManager(targetDataNum);
    this.compareKeyInterface = compareKeyInterface;
    this.dataParse = dataParse;
    this.savePath = savePath;
  }

  public DataCompare(
      BigCompareKeyInf<V> compareKeyInterface, DataParseInf<V> dataParse, String savePath) {
    this.compareKeyInterface = compareKeyInterface;
    this.dataParse = dataParse;
    this.savePath = savePath;
    this.srcKeyBloomFilter = this.loadBloomFilter(SRC_KEY_BLOOM_PATH);
    this.srcFullBloomFilter = this.loadBloomFilter(SRC_FULL_BLOOM_PATH);
    this.targetKeyBloomFilter = this.loadBloomFilter(TARGET_KEY_BLOOM_PATH);
    this.targetFullBloomFilter = this.loadBloomFilter(TARGET_FULL_BLOOM_PATH);
  }

  /**
   * 将一批数据放入到布隆过滤器中
   *
   * @param dataList
   */
  public void putSrcList(List<V> dataList) {
    for (V itemData : dataList) {
      // 主键加入到主键布隆过滤器中
      srcKeyBloomFilter.put(compareKeyInterface.getKey(itemData));
      // 全量数据加入到布隆过滤器中
      srcFullBloomFilter.put(compareKeyInterface.getKeyMany(itemData));
    }
  }

  /**
   * 将一批数据目标数据放入到布隆过滤器中
   *
   * @param dataList 数据集
   */
  public void putTargetList(List<V> dataList) {
    for (V itemData : dataList) {
      // 主键加入到主键布隆过滤器中
      targetKeyBloomFilter.put(compareKeyInterface.getKey(itemData));
      // 全量数据加入到布隆过滤器中
      targetFullBloomFilter.put(compareKeyInterface.getKeyMany(itemData));
    }
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
    rsp.initCompareTarget();
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

    // 检查数据是否存在于主键中
    boolean srcKeyContain = srcKeyBloomFilter.mightContain(key);

    // 布隆过滤器对于仅存在的，可能会存在误差问题，但不存在的，一定不存在
    // 原始数据中不存在的，一定是不存的
    if (!srcKeyContain) {
      rsp.getAddList().add(dataParse.toFileLine(valueInfo));
    }
    // 如果主键存在，则检查完整数据
    else {

      // 数据必须在目标数据中
      boolean targetKeyFlag = targetKeyBloomFilter.mightContain(key);
      if (!targetKeyFlag) {
        return;
      }
      // 全量数据也必须存在也全量数据中
      boolean targetFullFlag = targetFullBloomFilter.mightContain(keyMany);
      if (!targetFullFlag) {
        return;
      }

      // 如果原始数据与目标数据一致，则退出
      boolean srcFullFlag = srcFullBloomFilter.mightContain(keyMany);
      if (srcFullFlag) {
        return;
      }

      // 如果所有条件都满足，则加入到目标数据中
      rsp.getUpdateAfterList().add(dataParse.toFileLine(valueInfo));
    }
  }

  /**
   * 对比原始数据集信息
   *
   * @param dataList 数据集
   * @throws IOException
   */
  public DataCompareRsp compareSrcList(List<V> dataList) {

    DataCompareRsp rsp = new DataCompareRsp();
    // 初始化原始结果集合
    rsp.initCompareSrc();

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

    boolean targetExist = targetKeyBloomFilter.mightContain(key);

    // 在目标数据中不存在，说明要删除
    if (!targetExist) {
      rsp.getDeleteList().add(dataParse.toFileLine(dataInfo));
    }
    // 在目标中存在，则检查全量数据
    else {
      // 原始数据必须在原始数据中，
      boolean srcKeyFlag = srcKeyBloomFilter.mightContain(key);
      if (!srcKeyFlag) {
        return;
      }

      // 全量数据也必须匹配
      boolean srcFullFlag = srcFullBloomFilter.mightContain(keyMany);
      if (!srcFullFlag) {
        return;
      }

      // 如果原始数据与目标数据一致也退出
      boolean targetFullFlag = targetFullBloomFilter.mightContain(keyMany);
      if (targetFullFlag) {
        return;
      }

      // 如果全量数据不存在于原始数据中
      rsp.getUpdateBeforeList().add(dataParse.toFileLine(dataInfo));
    }
  }

  /** 保存数据 */
  public void save() {
    // 1,保存原始主键布隆过滤器
    this.saveBloomFilter(srcKeyBloomFilter, this.savePath + Symbol.PATH + SRC_KEY_BLOOM_PATH);
    // 2，保存原始全量布隆过滤器
    this.saveBloomFilter(srcFullBloomFilter, this.savePath + Symbol.PATH + SRC_FULL_BLOOM_PATH);
    // 3,保存目标主键布隆过滤器
    this.saveBloomFilter(targetKeyBloomFilter, this.savePath + Symbol.PATH + TARGET_KEY_BLOOM_PATH);
    // 4，保存目标全量布隆过滤器
    this.saveBloomFilter(
        targetFullBloomFilter, this.savePath + Symbol.PATH + TARGET_FULL_BLOOM_PATH);
  }

  /**
   * 保存布隆过滤器
   *
   * @param manager 过滤器信息
   * @param path 保存路径
   */
  private void saveBloomFilter(BoomFilterManager manager, String path) {
    FileUtils.checkAndMakeDir(path);
    // 原数据的布隆过滤器保存
    manager.save(path);
  }

  /**
   * 加载布隆过滤器
   *
   * @param dataPath 路径信息
   * @return 返回布隆过滤器
   */
  public BoomFilterManager loadBloomFilter(String dataPath) {
    // 原数据的布隆过滤器保存
    return new BoomFilterManager(this.savePath + Symbol.PATH + dataPath);
  }
}
