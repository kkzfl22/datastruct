package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.uniquerows;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.FileDataEntity;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.TestBigFileCompare;
import com.liujun.datastruct.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 文件去重操作测试
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestUniqueRowProcess {

  @Test
  public void testUnique() {
    UniqueRowProcess instance = new UniqueRowProcess();
    String src =
        this.getClass().getClassLoader().getResource("files/uniquerows/proc/little/src").getPath();
    String output =
        this.getClass().getClassLoader().getResource("files/uniquerows/proc/little/").getPath();
    output = output + Symbol.PATH + "output";

    FileUtils.checkAndMakeDir(output);

    try {
      instance.uniqueRows(src, output, TestBigFileCompare.getDataParse(), FileDataEntity.class);
      List<String> dataList = FileUtils.readTop(output + "/merge-repetition-0.txt", 10);

      Assert.assertEquals(dataList.get(0), "0,0,1,2,3,8");
      Assert.assertEquals(dataList.get(1), "1,0,1,2,3,8");
      Assert.assertEquals(dataList.get(2), "2,0,1,2,3,8");
      Assert.assertEquals(dataList.get(3), "3,0,1,2,3,8");
      Assert.assertEquals(dataList.get(4), "4,0,1,2,3,8");
      Assert.assertEquals(dataList.get(5), "5,0,1,2,3,8");
      Assert.assertEquals(dataList.get(6), "6,0,1,2,3,8");

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      FileUtils.deleteDir(output);
    }
  }
}
