package com.liujun.datastruct.datacompare.bigfilecompare.compare;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.FileDataEntity;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.DataCompareRsp;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试比较器，实体数据
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestDataCompareEntity {

  @Test
  public void dataCompare() {

    String dataPath =
        TestDataCompareEntity.class.getClassLoader().getResource(".").getPath()
            + Symbol.PATH
            + "output";

    int maxValue = 2 << 29;
    System.out.println("运行次数:" + maxValue);

    DataCompare<FileDataEntity> compareInstance =
        new DataCompare(getCompareKey(), getDataParse(), maxValue, maxValue, dataPath);

    // 原始数据分成2个部分数据中1-100万为删除，100万至200万修改之前
    // 目标数据分面2个部分数据100万-200万为修改之后，200万-300为添加

    int deleteEndNum = maxValue / 2;
    int updStartNum = maxValue;
    int addEndNum = maxValue + deleteEndNum;
    // 原始数据删除部分
    for (int i = 0; i < deleteEndNum; i += 10) {
      List<FileDataEntity> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(getEntity(j + i));
      }
      compareInstance.putSrcList(dataList);
    }

    // 原始数据修改之前
    for (int i = deleteEndNum; i < updStartNum; i += 10) {
      List<FileDataEntity> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(getEntity(j + i));
      }
      compareInstance.putSrcList(dataList);
    }

    // 目标数据修改之后的数据
    for (int i = deleteEndNum; i < updStartNum; i += 10) {
      List<FileDataEntity> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(getRandEntity(j + i));
      }
      compareInstance.putTargetList(dataList);
    }

    System.out.println("数据填充完成......");

    // 目标中需要添加的部分
    for (int i = updStartNum; i < addEndNum; i += 10) {
      List<FileDataEntity> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(getEntity(j + i));
      }
      compareInstance.putTargetList(dataList);
    }

    // 删除的记录,即原始中存在，目录中不存的，即为删除
    for (int i = 0; i < deleteEndNum; i += 10) {
      List<FileDataEntity> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(getEntity(j + i));
      }

      DataCompareRsp dataRsp = compareInstance.compareSrcList(dataList);
      if (i + 10 > deleteEndNum) {
        Assert.assertNotEquals(dataRsp.getDeleteList().size(), 0);
      } else {
        Assert.assertEquals(dataRsp.getDeleteList().size(), 10);
      }
    }

    // 修改之前的记录,由于对比都一样，所以，不用修改
    for (int i = deleteEndNum; i < updStartNum; i += 10) {
      List<FileDataEntity> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(getEntity(j + i));
      }

      DataCompareRsp dataRsp = compareInstance.compareSrcList(dataList);

      if (i + 10 > updStartNum) {
        Assert.assertNotEquals(dataRsp.getUpdateBeforeList().size(), 0);
      } else {
        Assert.assertEquals(dataRsp.getUpdateBeforeList().size(), 10);
      }
    }

    // 修改之后的记录
    for (int i = deleteEndNum; i < updStartNum; i += 10) {
      List<FileDataEntity> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(getRandEntity(j + i));
      }
      DataCompareRsp dataRsp = compareInstance.compareTargetList(dataList);

      if (i + 10 > updStartNum) {
        Assert.assertNotEquals(dataRsp.getUpdateAfterList().size(), 0);
      } else {
        Assert.assertEquals(dataRsp.getUpdateAfterList().size(), 10);
      }
    }

    // 添加记录
    for (int i = updStartNum; i < addEndNum; i += 10) {
      List<FileDataEntity> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(getEntity(j + i));
      }
      DataCompareRsp dataRsp = compareInstance.compareTargetList(dataList);
      if (i + 10 > addEndNum) {
        Assert.assertNotEquals(0, dataRsp.getAddList().size());
      } else {
        Assert.assertEquals(dataRsp.getAddList().size(), 10);
      }
    }
  }

  public static BigCompareKeyInf<FileDataEntity> getCompareKey() {
    return new CompareKeyImpl();
  }

  private static FileDataEntity getEntity(int keyId) {
    FileDataEntity entity = new FileDataEntity();

    entity.setKey(keyId);

    String[] data = new String[5];
    for (int i = 0; i < 5; i++) {
      data[i] = String.valueOf(i);
    }
    entity.setItem(data);
    return entity;
  }

  private static FileDataEntity getRandEntity(int keyId) {
    FileDataEntity entity = new FileDataEntity();

    entity.setKey(keyId);

    String[] data = new String[5];
    for (int i = 0; i < 5; i++) {
      data[i] = "(" + i + ")";
    }
    entity.setItem(data);
    return entity;
  }

  /** 用于对比的主键信息 */
  private static class CompareKeyImpl implements BigCompareKeyInf<FileDataEntity> {

    @Override
    public String getKey(FileDataEntity data) {
      return String.valueOf(data.getKey());
    }

    @Override
    public String getKeyMany(FileDataEntity data) {
      return data.entityToLine();
    }
  }

  public static DataParseInf<FileDataEntity> getDataParse() {
    return new DataParseCompare();
  }

  public static class DataParseCompare implements DataParseInf<FileDataEntity> {

    @Override
    public String toFileLine(FileDataEntity data) {
      return data.entityToLine();
    }

    @Override
    public FileDataEntity lineToData(String line) {
      return FileDataEntity.lineToEntity(line);
    }
  }
}
