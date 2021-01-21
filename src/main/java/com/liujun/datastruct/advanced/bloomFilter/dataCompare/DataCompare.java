package com.liujun.datastruct.advanced.bloomFilter.dataCompare;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.entity.DataCompareRsp;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.entity.DataCompareValueInfo;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.entity.UpdateObjectEntity;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
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
  private final CompareKeyInterface<V> compareKeyInterface;

  private DataCompare(int size, double fpp, CompareKeyInterface<V> compareKeyInterface) {
    this.srcBloomFilter =
        BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), size, fpp);
    this.targetBloomFilter =
        BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), size, fpp);
    this.compareKeyInterface = compareKeyInterface;
  }

  /**
   * 获取实例对象
   *
   * @param compareKeyInterface
   * @return
   */
  public DataCompare(CompareKeyInterface compareKeyInterface) {
    this(DEFAULT_SIZE, DEFAULT_FPP, compareKeyInterface);
  }

  /**
   * 将数据放入到布隆过滤器中
   *
   * @param data
   */
  public void putSrc(List<V> data) {
    this.putData(srcBloomFilter, data);
  }

  /**
   * 将目标数据放入到布隆过滤器中
   *
   * @param data
   */
  public void putTarget(List<V> data) {
    this.putData(targetBloomFilter, data);
  }

  private void putData(BloomFilter<String> bloomFilter, List<V> data) {
    if (null == data || data.isEmpty()) {
      return;
    }
    // 1,进行当前的数据id的生成
    for (V dataItem : data) {
      bloomFilter.put(compareKeyInterface.getKey(dataItem));
      bloomFilter.put(compareKeyInterface.getKeyMany(dataItem));
    }
  }

  /**
   * 进行数据对比操作
   *
   * @param dataInfo
   */
  public DataCompareRsp dataCompare(DataCompareValueInfo<V> dataInfo) {

    DataCompareRsp rsp = new DataCompareRsp();

    // 数据容器的初始化
    rsp.init();
    // 输入为空标识,true当前为空,false当前存在值
    boolean databaseNullFlag = dataInfo.getSrcList() == null || dataInfo.getSrcList().isEmpty();
    // 数据库为空标识，true当前为空,false当前存在值
    boolean inputNullFlag = dataInfo.getTargetList() == null || dataInfo.getTargetList().isEmpty();

    // 如果都为空，则啥都不做
    if (databaseNullFlag && inputNullFlag) {
      return rsp;
    }
    // 如果输入为空，数据库不为空，说明全部删除
    if (!databaseNullFlag && inputNullFlag) {
      rsp.getRemoveList().addAll(dataInfo.getSrcList());
      return rsp;
    }
    // 如果输入为不为空，数据库为空，说明全部添加
    if (databaseNullFlag && !inputNullFlag) {
      rsp.getAddList().addAll(dataInfo.getTargetList());
      return rsp;
    }

    // 插入的对比
    this.filterInsert(dataInfo, rsp, compareKeyInterface);

    // 删除的对比
    this.filterDelete(dataInfo, rsp, compareKeyInterface);
    return rsp;
  }

  /**
   * 从布隆过滤器中过滤出差异的数据,找出不存在
   *
   * @param dataValue 请求数据
   * @param rsp 响应数据
   * @param compareKeyInterface 对比接口
   * @return
   */
  private void filterInsert(
      DataCompareValueInfo<V> dataValue,
      DataCompareRsp rsp,
      CompareKeyInterface<V> compareKeyInterface) {

    // 检查不在过滤器中的数据
    for (V valueItem : dataValue.getTargetList()) {

      String key = compareKeyInterface.getKey(valueItem);
      String keyMany = compareKeyInterface.getKeyMany(valueItem);

      // 布隆过滤器不存在的，一定是不存的,仅存在的，可能会存在误差问题
      if (!srcBloomFilter.mightContain(key)) {
        rsp.getAddList().add(valueItem);
      }
      // 如果主键能找到，多个对比数据不存在，则执行修改操作
      else if (srcBloomFilter.mightContain(key) && !srcBloomFilter.mightContain(keyMany)) {
        Map<String, UpdateObjectEntity<V>> dataMap = rsp.getUpdateMap();
        UpdateObjectEntity<V> dataInfoEntity = dataMap.get(key);
        if (null == dataInfoEntity) {
          rsp.getUpdateMap().put(key, this.instanceAfter(valueItem));
        } else {
          dataInfoEntity.setAfter(valueItem);
        }
      }
    }
  }

  private UpdateObjectEntity<V> instanceBefore(V before) {
    UpdateObjectEntity<V> instance = new UpdateObjectEntity<>();
    instance.setBefore(before);
    return instance;
  }

  private UpdateObjectEntity<V> instanceAfter(V before) {
    UpdateObjectEntity<V> instance = new UpdateObjectEntity<>();
    instance.setAfter(before);
    return instance;
  }

  /**
   * 从布隆过滤器中过滤出差异的数据,找出不存在
   *
   * @param dataInfo 请求数据
   * @param rsp 响应数据
   * @param compareKeyInterface 对比接口
   * @return
   */
  private void filterDelete(
      DataCompareValueInfo<V> dataInfo,
      DataCompareRsp rsp,
      CompareKeyInterface<V> compareKeyInterface) {

    // 检查不在过滤器中的数据
    for (V dataItem : dataInfo.getSrcList()) {
      String key = compareKeyInterface.getKey(dataItem);
      String keyMany = compareKeyInterface.getKeyMany(dataItem);

      // 布隆过滤器不存在的，一定是不存的,仅存在的，可能会存在误差问题
      if (!targetBloomFilter.mightContain(key)) {
        rsp.getRemoveList().add(dataItem);
      }
      // 如果主键能找到，多个对比数据不存在，则执行修改操作
      else if (targetBloomFilter.mightContain(key) && !targetBloomFilter.mightContain(keyMany)) {
        Map<String, UpdateObjectEntity<V>> dataMap = rsp.getUpdateMap();
        UpdateObjectEntity<V> dataInfoEntity = dataMap.get(key);
        if (null == dataInfoEntity) {
          rsp.getUpdateMap().put(key, this.instanceBefore(dataItem));
        } else {
          dataInfoEntity.setBefore(dataItem);
        }
      }
    }
  }
}
