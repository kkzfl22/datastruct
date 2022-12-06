package com.liujun.datastruct.advanced.bplusTree.disk.v2.convert;

import com.liujun.datastruct.advanced.bplusTree.disk.v2.disk.SystemInfo;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.entity.DataMetadata;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.entity.DataMetadataItem;
import com.liujun.datastruct.advanced.bplusTree.disk.v2.entity.DataMetadataKey;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 将数据转换为字节进行存储或者读取
 *
 * @author liujun
 * @since 2022/11/21
 */
public class DiskValueParse {

  private static final int PAGE_SIZE = SystemInfo.getPageSize();

  /**
   * 将元数据转换为元数据的信息保存
   *
   * @param metaData 元数据信息
   * @return
   */
  public static ByteBuffer writeMetadata(DataMetadata metaData) {

    // 首先计算主键的长度
    int keyLength = countKeyLength(metaData.getMetadataKey());
    // 计算元数据的长度
    int metadataLength = countMetadataList(metaData.getMetadataValue());

    // 计算存储元数据的块的数量
    int bufferSize = countChunkSize(keyLength + metadataLength);
    ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

    // 首先写入主键的类型
    buffer.putInt(metaData.getMetadataKey().getDataType());
    // 写入主键的长度
    buffer.putInt(metaData.getMetadataKey().getLength());
    // 写入主键的名称
    ValueToBufferParse.INSTANCE.putStringToBuffer(metaData.getMetadataKey().getName(), buffer);
    // 主键的描述
    ValueToBufferParse.INSTANCE.putStringToBuffer(metaData.getMetadataKey().getComment(), buffer);

    List<DataMetadataItem> metaDataList = metaData.getMetadataValue();
    // 写入数据集合的总数
    buffer.putInt(metaDataList.size());

    for (int i = 0; i < metaDataList.size(); i++) {
      DataMetadataItem metaDataValue = metaDataList.get(i);
      buffer.putInt(metaDataValue.getIndex());
      // 写入主键的名称
      ValueToBufferParse.INSTANCE.putStringToBuffer(metaDataValue.getName(), buffer);
      // 主键的描述
      ValueToBufferParse.INSTANCE.putStringToBuffer(metaDataValue.getComment(), buffer);
      // 写入主键的类型
      buffer.putInt(metaDataList.get(i).getDataType());
      // 写入长度
      buffer.putInt(metaDataList.get(i).getLength());
    }

    return buffer;
  }

  /**
   * 计算块总大小
   *
   * @param dataLength
   * @return
   */
  private static int countChunkSize(int dataLength) {
    if (dataLength <= PAGE_SIZE) {
      return PAGE_SIZE;
    }

    int chunkSum = dataLength % PAGE_SIZE;

    // 正好为内存页的倍数，直接使用
    if (chunkSum == 0) {
      return dataLength;
    }

    int sumChunk = dataLength / PAGE_SIZE + 1;
    return sumChunk * PAGE_SIZE;
  }

  /**
   * 计算主键的长度
   *
   * @param key
   * @return
   */
  private static int countKeyLength(DataMetadataKey key) {
    int countKey = 0;
    // 标识符的长度
    countKey = countKey + 4;
    // 数据的长度
    countKey = countKey + 4;
    // 名称的长度
    countKey = countKey + key.getName().getBytes(StandardCharsets.UTF_8).length;
    // 描述的长度
    countKey = countKey + key.getComment().getBytes(StandardCharsets.UTF_8).length;

    return countKey;
  }

  /**
   * 计算元数据的长度
   *
   * @param metaDataList
   * @return
   */
  private static int countMetadataList(List<DataMetadataItem> metaDataList) {
    int countLength = 0;
    // 首先写入的是4位长度
    countLength = countLength + 4;
    for (DataMetadataItem item : metaDataList) {
      countLength += (countItemLength(item));
    }
    return countLength;
  }

  private static int countItemLength(DataMetadataItem item) {
    int countLength = 0;
    // 标识符的长度
    countLength = countLength + 4;
    // 名称的长度
    countLength = countLength + item.getName().getBytes(StandardCharsets.UTF_8).length;
    // 描述的长度
    countLength = countLength + item.getComment().getBytes(StandardCharsets.UTF_8).length;
    // 数据类型
    countLength = countLength + 4;
    // 主键的长度
    countLength = countLength + 4;

    return countLength;
  }

  // public static DataChunkData readMetadata(ByteBuffer buffer) {
  //  DataChunkData medataChunk = new DataChunkData();
  //  medataChunk.setPosition(0);
  //
  //  medataChunk.setMetadata(new DataMetadata());
  //
  //  DataMetadata metaData = medataChunk.getMetadata();
  //
  //  metaData.setMetadataKey(new DataMetadataKey());
  //  // 主键的类型
  //  metaData.getMetadataKey().setDataType(buffer.getInt());
  //  // 主键的长度
  //  metaData.getMetadataKey().setLength(buffer.getInt());
  //  // 主键名称
  //  metaData.getMetadataKey().setName(ValueToBufferParse.INSTANCE.getBufferToString(buffer));
  //  // 主键描述
  //  metaData.getMetadataKey().setComment(ValueToBufferParse.INSTANCE.getBufferToString(buffer));
  //
  //  int valueSize = buffer.getInt();
  //  List<DataMetadataItem> metadataItemList = new ArrayList<>(valueSize);
  //
  //  for (int i = 0; i < valueSize; i++) {
  //    DataMetadataItem metadataItem = new DataMetadataItem();
  //    // 索引号
  //    metadataItem.setIndex(buffer.getInt());
  //    // 名称
  //    metadataItem.setName(ValueToBufferParse.INSTANCE.getBufferToString(buffer));
  //    // 描述
  //    metadataItem.setComment(ValueToBufferParse.INSTANCE.getBufferToString(buffer));
  //    // 主键的类型
  //    metadataItem.setDataType(buffer.getInt());
  //    // 主键的长度
  //    metadataItem.setLength(buffer.getInt());
  //
  //    metadataItemList.add(metadataItem);
  //  }
  //
  //  metaData.setMetadataValue(metadataItemList);
  //
  //  // 存储元数据信息
  //  medataChunk.setMetadata(metaData);
  //
  //  return medataChunk;
  // }
}
