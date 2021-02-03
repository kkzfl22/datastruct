package com.liujun.datastruct.datacompare.bigfilecompare;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataCompareFileOutput;
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
      compareInstance.writeUpdateData("this update before");
      compareInstance.writeDeleteData("this delete");

      compareInstance.close();

      Assert.assertEquals(
          "this is add\n", FileUtils.readLittleFile(compareInstance.getAddFilePath() + "-0.txt"));
      Assert.assertEquals(
          "this update before\n",
          FileUtils.readLittleFile(compareInstance.getUpdFilePath() + "-0.txt"));
      Assert.assertEquals(
          "this delete\n",
          FileUtils.readLittleFile(compareInstance.getDeleteFilePath() + "-0.txt"));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      FileUtils.deleteFile(compareInstance.getAddFilePath() + "-0.txt");
      FileUtils.deleteFile(compareInstance.getUpdFilePath() + "-0.txt");
      FileUtils.deleteFile(compareInstance.getDeleteFilePath() + "-0.txt");
    }
  }
}
