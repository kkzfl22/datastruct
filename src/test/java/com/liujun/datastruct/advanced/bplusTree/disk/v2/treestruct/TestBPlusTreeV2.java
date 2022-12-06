package com.liujun.datastruct.advanced.bplusTree.disk.v2.treestruct;

import com.liujun.datastruct.advanced.bplusTree.disk.v2.convert.ValueTypeEnum;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.disk.RootNodeOperator;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.entity.DataMetadata;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.entity.DataMetadataItem;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.entity.DataMetadataKey;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试磁盘的B+树的实现
 *
 * <p>V2版本的优化重要在于对于IO的优化。
 *
 * <p>在V1版本中，由于每个块是整个写入的，对于数据的操作都是整个的，IO对于一些重复的数据有大量的写入操作，此版本优化重点在于去掉这些重复的写入
 *
 * @author liujun
 * @since 2022/11/18
 */
public class TestBPlusTreeV2 {

  /** 使用B+树进行存储操作 */
  @Test
  public void testDiskBPlus() {
    DataMetadata metaData = new DataMetadata();

    metaData.setMetadataKey(new DataMetadataKey());
    metaData.getMetadataKey().setDataType(ValueTypeEnum.INT.getTypeFlag());
    metaData.getMetadataKey().setName("id");
    metaData.getMetadataKey().setComment("主键的id");
    metaData.getMetadataKey().setLength(4);

    // 值
    metaData.setMetadataValue(new ArrayList<>());
    DataMetadataItem metadataColumn1 = new DataMetadataItem();
    metadataColumn1.setLength(8);
    metadataColumn1.setDataType(ValueTypeEnum.LONG.getTypeFlag());
    metadataColumn1.setName("runTime");
    metadataColumn1.setComment("时间信息");
    metaData.getMetadataValue().add(metadataColumn1);

    DataMetadataItem metadataColumn2 = new DataMetadataItem();
    metadataColumn2.setLength(32);
    metadataColumn2.setDataType(ValueTypeEnum.STRING.getTypeFlag());
    metadataColumn2.setName("address");
    metadataColumn2.setComment("详细地址信息");
    metaData.getMetadataValue().add(metadataColumn2);

    String path = "D:\\run\\bplustree\\";
    String fileName = "bplustree.data";

    // 进行文件清除
    new File(path + fileName).delete();
    new File(path + RootNodeOperator.ROOT_FILE_NAME).delete();

    BPlusTree tree = new BPlusTree(metaData, path, fileName);

    int maxMum = 20480;
    for (int i = 0; i < maxMum; i++) {
      Map<String, Object> value = new HashMap<>(2);
      value.put("runTime", (long) i);
      String valueStr = RandomStringUtils.randomAlphabetic(20) + i;
      value.put("address", valueStr);

      tree.add(i, value);
      System.out.println("插入成功:" + i);
    }

    // 测试获取数据
    Map<String, Object> value = tree.get(maxMum / 2);
    Assertions.assertNotNull(value);

    // 测试获取数据
    Map<String, Object> valueMaxGet = tree.get(maxMum - 10);
    Assertions.assertNotNull(valueMaxGet);

    // 测试范围取值
    int start = 1024;
    int end = 2048;
    List<Map<String, Object>> list = tree.rangeGet(start, end);
    for (Map<String, Object> dataItem : list) {
      System.out.println(dataItem);
    }
    Assertions.assertEquals(end - start, list.size());
  }
}
