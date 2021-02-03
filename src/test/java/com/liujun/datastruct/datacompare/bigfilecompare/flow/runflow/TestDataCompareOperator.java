package com.liujun.datastruct.datacompare.bigfilecompare.flow.runflow;

import com.liujun.datastruct.datacompare.bigfilecompare.BigFileCompareMain;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataCompare;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.FileDataEntity;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.ManyFileReader;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.CompareKeyEnum;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.ContextContainer;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试数据对比操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestDataCompareOperator {

  @Test
  public void dataCompare() {

    String srcPath = "D:\\run\\compare\\miffile\\src";
    String targetPath = "D:\\run\\compare\\miffile\\target";
    String compareOutput = "D:\\run\\compare\\miffile\\output";

    BigFileCompareInputEntity input =
        new BigFileCompareInputEntity(srcPath, targetPath, compareOutput);

    ContextContainer context = new ContextContainer();

    BigCompareKeyInf<FileDataEntity> compareKey = new BigFileCompareMain.BigCompareKeyImpl();
    DataParseInf<FileDataEntity> dataParse = BigFileCompareMain.getDataParse();

    context.put(CompareKeyEnum.INPUT_BIGFILE_PATH.getKey(), input);
    context.put(CompareKeyEnum.INPUT_COMPARE_KEY.getKey(), compareKey);
    context.put(CompareKeyEnum.INPUT_COMPARE_PARSE.getKey(), dataParse);
    context.put(CompareKeyEnum.INPUT_COMPARE_ENTITY_CLASS.getKey(), FileDataEntity.class);

    // 1,获得对比的原始文件
    ManyFileReader readSrc =
        new ManyFileReader("D:\\run\\compare\\miffile\\output\\src\\unique-data-after");
    ManyFileReader readTarget =
        new ManyFileReader("D:\\run\\compare\\miffile\\output\\target\\unique-data-after");

    // 原始文件读取器
    context.put(CompareKeyEnum.PROC_COMPARE_MANY_READER_SRC.getKey(), readSrc);
    // 目标文件读取器
    context.put(CompareKeyEnum.PROC_COMPARE_MANY_READER_TARGET.getKey(), readTarget);

    // 执行数据对比的操作
    DataCompare compare =
        new DataCompare(compareKey, dataParse, "D:\\run\\compare\\miffile\\output\\bloomfilter");
    // 对比处理器
    context.put(CompareKeyEnum.PROC_COMPARE_INSTANCE_OBJECT.getKey(), compare);

    boolean exec = DataCompareOperator.INSTANCE.invokeFlow(context);
    System.out.println(exec);
    Assert.assertEquals(true, exec);
  }
}
