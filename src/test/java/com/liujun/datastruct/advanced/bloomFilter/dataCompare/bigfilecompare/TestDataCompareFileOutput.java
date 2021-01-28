package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.DataCompareFileOutput;
import com.liujun.datastruct.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试结果的数据写入与读取操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestDataCompareFileOutput {

  private static final String TEST_OUTPUT_PATH = "dataCompare";

  /** 测试操作 */
  @Test
  public void testRspData() {
    String path = TestDataCompareFileOutput.class.getClassLoader().getResource(".").getPath();

    DataCompareFileOutput compareInstance =
        new DataCompareFileOutput(path + Symbol.PATH + TEST_OUTPUT_PATH);

    // 执行文件打开
    compareInstance.openWriteFile();

    try {
      // 文件写入
      compareInstance.writeAddData("this is add");
      compareInstance.writeUpdateBeforeData("this update before");
      compareInstance.writeUpdateAfterData("this update after");
      compareInstance.writeDeleteData("this delete");

      compareInstance.close();

      Assert.assertEquals(
          "this is add\n", FileUtils.readLittleFile(compareInstance.getAddFilePath()));
      Assert.assertEquals(
          "this update before\n", FileUtils.readLittleFile(compareInstance.getUpdBeforeFilePath()));
      Assert.assertEquals(
          "this update after\n", FileUtils.readLittleFile(compareInstance.getUpdAfterFilePath()));
      Assert.assertEquals(
          "this delete\n", FileUtils.readLittleFile(compareInstance.getDeleteFilePath()));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      FileUtils.deleteFile(compareInstance.getAddFileName());
      FileUtils.deleteFile(compareInstance.getUpdBeforeFilePath());
      FileUtils.deleteFile(compareInstance.getUpdAfterFilePath());
      FileUtils.deleteFile(compareInstance.getDeleteFilePath());
    }
  }
}
