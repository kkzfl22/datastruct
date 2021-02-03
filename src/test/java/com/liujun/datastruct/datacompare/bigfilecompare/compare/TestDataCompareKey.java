package com.liujun.datastruct.datacompare.bigfilecompare.compare;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.DataCompareRsp;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试比较器，仅比较主键数据
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestDataCompareKey {

  @Test
  public void dataCompare() {

    String dataPath =
        TestDataCompareKey.class.getClassLoader().getResource(".").getPath()
            + Symbol.PATH
            + "output";

    int maxValue = 2000000;
    System.out.println("运行次数:" + maxValue);

    DataCompare<Integer> compareInstance =
        new DataCompare(getCompareKey(), getDataParse(), maxValue, maxValue, dataPath);

    // 原始数据分成2个部分数据中1-100万为删除，100万至200万修改之前
    // 目标数据分面2个部分数据100万-200万为修改之后，200万-300为添加

    // 原始数据删除部分
    for (int i = 0; i < 1000000; i += 10) {
      List<Integer> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(j + i);
      }
      compareInstance.putSrcList(dataList);
    }

    // 原始数据修改之前
    for (int i = 1000000; i < 2000000; i += 10) {
      List<Integer> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(j + i);
      }
      compareInstance.putSrcList(dataList);
    }

    // 目标数据修改之后的数据
    // 原始数据修改之前
    for (int i = 1000000; i < 2000000; i += 10) {
      List<Integer> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(j + i);
      }
      compareInstance.putTargetList(dataList);
    }

    // 目标中需要添加的部分
    for (int i = 2000000; i < 3000000; i += 10) {
      List<Integer> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(j + i);
      }
      compareInstance.putTargetList(dataList);
    }

    // 删除的记录,即原始中存在，目录中不存的，即为删除
    for (int i = 0; i < 1000000; i += 10) {
      List<Integer> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(j + i);
      }

      DataCompareRsp dataRsp = compareInstance.compareSrcList(dataList);
      if (i + 10 > 1000000) {
        Assert.assertNotEquals(dataRsp.getDeleteList().size(), 0);
      } else {
        Assert.assertEquals(dataRsp.getDeleteList().size(), 10);
      }
    }

    // 修改之前的记录,由于对比都一样，所以，不用修改
    for (int i = 1000000; i < 2000000; i += 10) {
      List<Integer> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(j + i);
      }

      DataCompareRsp dataRsp = compareInstance.compareSrcList(dataList);
      Assert.assertEquals(dataRsp.getUpdateBeforeList().size(), 0);
    }

    // 修改之后的记录
    for (int i = 1000000; i < 2000000; i += 10) {
      List<Integer> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(j + i);
      }
      DataCompareRsp dataRsp = compareInstance.compareTargetList(dataList);
      Assert.assertEquals(dataRsp.getUpdateAfterList().size(), 0);
    }

    // 添加记录
    for (int i = 2000000; i < 3000000; i += 10) {
      List<Integer> dataList = new ArrayList<>(10);
      for (int j = 0; j < 10; j++) {
        dataList.add(j + i);
      }
      DataCompareRsp dataRsp = compareInstance.compareTargetList(dataList);
      if (i + 10 > 3000000) {
        Assert.assertNotEquals(0, dataRsp.getAddList().size());
      } else {
        Assert.assertEquals(dataRsp.getAddList().size(), 10);
      }
    }
  }

  public static BigCompareKeyInf<Integer> getCompareKey() {
    return new CompareKeyImpl();
  }

  /** 用于对比的主键信息 */
  private static class CompareKeyImpl implements BigCompareKeyInf<Integer> {

    @Override
    public String getKey(Integer data) {
      return String.valueOf(data);
    }

    @Override
    public String getKeyMany(Integer data) {
      return String.valueOf(data);
    }
  }

  public static DataParseInf<Integer> getDataParse() {
    return new DataParseCompare();
  }

  public static class DataParseCompare implements DataParseInf<Integer> {

    @Override
    public String toFileLine(Integer data) {
      return String.valueOf(data);
    }

    @Override
    public Integer lineToData(String line) {
      return Integer.parseInt(line);
    }
  }
}
