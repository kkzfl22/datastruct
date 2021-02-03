package com.liujun.datastruct.datacompare.bigfilecompare.flow.runflow;

import com.liujun.datastruct.datacompare.bigfilecompare.BigFileCompareMain;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.FileDataEntity;
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
public class TestBloomFilterFull {

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

    context.put(
        CompareKeyEnum.PROC_REMOVE_DUPLICATE_OUTPUT_SRC.getKey(),
        "D:\\run\\compare\\miffile\\output\\src\\unique-data-after");
    context.put(
        CompareKeyEnum.PROC_REMOVE_DUPLICATE_OUTPUT_TARGET.getKey(),
        "D:\\run\\compare\\miffile\\output\\target\\unique-data-after");

    boolean exec = BloomFilterFull.INSTANCE.invokeFlow(context);
    System.out.println(exec);
    Assert.assertEquals(true, exec);
  }
}
