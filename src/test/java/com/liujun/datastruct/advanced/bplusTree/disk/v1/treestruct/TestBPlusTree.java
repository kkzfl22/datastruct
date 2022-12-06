package com.liujun.datastruct.advanced.bplusTree.disk.v1.treestruct;

import com.liujun.datastruct.advanced.bplusTree.disk.v1.convert.ValueTypeEnum;
import com.liujun.datastruct.advanced.bplusTree.disk.v1.disk.RootNodeOperator;
import com.liujun.datastruct.advanced.bplusTree.disk.v1.entity.DataMetadata;
import com.liujun.datastruct.advanced.bplusTree.disk.v1.entity.DataMetadataItem;
import com.liujun.datastruct.advanced.bplusTree.disk.v1.entity.DataMetadataKey;
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
 * @author liujun
 * @since 2022/11/18
 */
public class TestBPlusTree {

  /** 使用B+树进行存储操作 */
  @Test
  public void testDiskBPlus() {
    DataMetadata metaData = new DataMetadata();

    // 定义key的信息
    metaData.setMetadataKey(new DataMetadataKey());
    metaData.getMetadataKey().setDataType(ValueTypeEnum.INT.getTypeFlag());
    metaData.getMetadataKey().setName("id");
    metaData.getMetadataKey().setComment("主键的id");
    metaData.getMetadataKey().setLength(4);

    // 定义值的信息，存在两列，一个运行时间和地址信息
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

    int maxMum = 5000;
    for (int i = 0; i < maxMum; i += 10) {
      Map<String, Object> value = new HashMap<>(2);
      value.put("runTime", (long) i);
      String valueStr = RandomStringUtils.randomAlphabetic(20) + i;
      value.put("address", valueStr);

      tree.add(i, value);
    }
    // 插入一半
    System.out.println("完成0");

    for (int i = 5; i < maxMum; i += 10) {
      Map<String, Object> value = new HashMap<>(2);
      value.put("runTime", (long) i);
      String valueStr = RandomStringUtils.randomAlphabetic(20) + i;
      value.put("address", valueStr);

      tree.add(i, value);
    }
    // 再插入一半
    System.out.println("完成5");

    for (int i = 3; i < maxMum; i += 10) {
      Map<String, Object> value = new HashMap<>(2);
      value.put("runTime", (long) i);
      String valueStr = RandomStringUtils.randomAlphabetic(20) + i;
      value.put("address", valueStr);

      tree.add(i, value);
    }
    System.out.println("完成2");

    // 测试获取数据
    Map<String, Object> value = tree.get(maxMum / 2);
    System.out.println(value);

    // 测试获取数据
    Map<String, Object> valueMaxGet = tree.get(maxMum - 10);
    System.out.println(valueMaxGet);

    Map<String, Object> value2 = tree.get(maxMum / 10 + 3);
    System.out.println(value2);

    System.out.println("单一查找结束");
    // 测试范围取值
    int start = 1024;
    int end = 2048;
    List<Map<String, Object>> list = tree.rangeGet(start, end);
    for (Map<String, Object> dataItem : list) {
      System.out.println(dataItem);
    }

    // 进行根节点的保存操作
    tree.saveRootNode();
  }

  /** 使用B+树进行存储操作,测试已经存在的记录 */
  @Test
  public void testDiskBPlusExists() {
    DataMetadata metaData = new DataMetadata();

    // 定义key的信息
    metaData.setMetadataKey(new DataMetadataKey());
    metaData.getMetadataKey().setDataType(ValueTypeEnum.INT.getTypeFlag());
    metaData.getMetadataKey().setName("id");
    metaData.getMetadataKey().setComment("主键的id");
    metaData.getMetadataKey().setLength(4);

    // 定义值的信息，存在两列，一个运行时间和地址信息
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

    BPlusTree tree = new BPlusTree(metaData, path, fileName);


    int maxMum = 5000;
    for (int i = 2; i < maxMum; i += 10) {
      Map<String, Object> value = new HashMap<>(2);
      value.put("runTime", (long) i);
      String valueStr = RandomStringUtils.randomAlphabetic(20) + i;
      value.put("address", valueStr);

      tree.add(i, value);
    }
    System.out.println("完成2");

    // 测试获取数据
    Map<String, Object> value = tree.get(maxMum / 2);
    System.out.println(value);

    // 测试获取数据
    Map<String, Object> valueMaxGet = tree.get(maxMum - 10);
    System.out.println(valueMaxGet);

    Map<String, Object> value2 = tree.get(maxMum / 10 + 3);
    System.out.println(value2);

    System.out.println("单一查找结束");
    // 测试范围取值
    int start = 1024;
    int end = 2048;
    List<Map<String, Object>> list = tree.rangeGet(start, end);
    for (Map<String, Object> dataItem : list) {
      System.out.println(dataItem);
    }

    // 进行根节点的保存操作
    tree.saveRootNode();

  }
}
