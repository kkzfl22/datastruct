package com.liujun.datastruct.advanced.bloomFilter.dataCompare;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.entity.DataCompareRsp;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.entity.DataCompareValueInfo;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.entity.UpdateObjectEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据对比
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestDataCompare {

  private static final int DATA_SIZE = 2 << 15;

  public List<Map<String, String>> getData() {
    List<Map<String, String>> dataMap = new ArrayList<>(DATA_SIZE + 128);
    for (int i = 0; i < DATA_SIZE; i++) {
      dataMap.add(this.getEntity(i));
    }
    return dataMap;
  }

  private Map<String, String> getEntity(int va) {
    Map<String, String> data = new HashMap<>();
    for (int j = 0; j < 5; j++) {
      data.put(j + "", j + "" + va);
    }
    return data;
  }

  /** 原始数据 */
  private List<Map<String, String>> dataSrc;

  /** 目标数据 */
  private List<Map<String, String>> dataTarget;

  @Before
  public void dataInit() {
    dataSrc = this.getData();
    dataTarget = this.getData();
  }

  /** 执行数据差异的对比，对比出添加，修改，删除操作 */
  @Test
  public void dataCompare() {
    // 构建一条添加数据
    Map<String, String> newEntity = getEntity(DATA_SIZE + 1);
    dataTarget.add(newEntity);

    // 构建修改记录
    Map<String, String> updEntity = dataTarget.get(0);
    updEntity.put("j++", "value");

    // 构建删除记录
    Map<String, String> deleteEntity = dataTarget.remove(dataSrc.size() - 1);

    long startTime = System.currentTimeMillis();

    // 获取对比实例
    DataCompare<Map<String, String>> compareInstance = new DataCompare<>(new DataCompareImpl());
    compareInstance.putSrc(dataSrc);
    compareInstance.putTarget(dataTarget);

    // 执行数据对比操作
    DataCompareRsp dataRsp =
        compareInstance.dataCompare(new DataCompareValueInfo<>(dataSrc, dataTarget));

    long endTime = System.currentTimeMillis();

    // 添加的数据对比
    Assert.assertEquals(dataRsp.getAddList().get(0), newEntity);

    // 修改的对比
    Map<String, UpdateObjectEntity<Map<String, String>>> dataMap = dataRsp.getUpdateMap();
    UpdateObjectEntity<Map<String, String>> entityInfo = dataMap.get("00");
    Assert.assertEquals(entityInfo.getAfter(), updEntity);

    // 删除的对比
    List<Map<String, String>> dataList = dataRsp.getRemoveList();
    Map<String, String> deleteRemoveEntity = dataList.get(0);
    Assert.assertEquals(deleteRemoveEntity, deleteEntity);
    System.out.println("数据量:" + DATA_SIZE + ",对比用时:" + (endTime - startTime));
  }

  /** 执行数据差异的对比， */
  @Test
  public void dataCompareInsert() {
    dataSrc.clear();

    long startTime = System.currentTimeMillis();

    // 获取对比实例
    DataCompare<Map<String, String>> compareInstance = new DataCompare<>(new DataCompareImpl());
    compareInstance.putSrc(dataSrc);
    compareInstance.putTarget(dataTarget);

    // 执行数据对比操作
    DataCompareRsp dataRsp =
        compareInstance.dataCompare(new DataCompareValueInfo<>(dataSrc, dataTarget));

    long endTime = System.currentTimeMillis();

    // 添加的数据对比
    Assert.assertEquals(dataRsp.getAddList(), dataTarget);

    System.out.println("数据量:" + DATA_SIZE + ",对比用时:" + (endTime - startTime));
  }

  /** 执行数据差异对比出删除 */
  @Test
  public void dataCompareDelete() {
    dataTarget.clear();

    long startTime = System.currentTimeMillis();

    // 获取对比实例
    DataCompare<Map<String, String>> compareInstance = new DataCompare<>(new DataCompareImpl());
    compareInstance.putSrc(dataSrc);
    compareInstance.putTarget(dataTarget);

    // 执行数据对比操作
    DataCompareRsp dataRsp =
        compareInstance.dataCompare(new DataCompareValueInfo<>(dataSrc, dataTarget));

    long endTime = System.currentTimeMillis();

    // 添加的数据对比
    Assert.assertEquals(dataRsp.getRemoveList(), dataSrc);

    System.out.println("数据量:" + DATA_SIZE + ",对比用时:" + (endTime - startTime));
  }

  private class DataCompareImpl implements CompareKeyInterface<Map<String, String>> {

    @Override
    public String getKey(Map<String, String> data) {
      return data.get(String.valueOf(0));
    }

    @Override
    public String getKeyMany(Map<String, String> data) {

      StringBuilder manyKey = new StringBuilder();

      for (Map.Entry<String, String> dataEntry : data.entrySet()) {
        manyKey.append(dataEntry.getKey());
        manyKey.append(Symbol.UNLINE);
        manyKey.append(dataEntry.getValue());
      }

      return manyKey.toString();
    }
  }
}
