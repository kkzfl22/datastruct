package com.liujun.datastruct.datacompare.bigfilecompare.uniquerows;

import com.liujun.datastruct.datacompare.bigfilecompare.FileDataEntity;
import com.liujun.datastruct.datacompare.bigfilecompare.FileDataTimeStampEntity;
import com.liujun.datastruct.datacompare.bigfilecompare.TestBigFileCompare;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 测试数据合并操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestMergeData {

  @Test
  public void testMergeData() {
    String src =
        this.getClass().getClassLoader().getResource("files/uniquerows/merge/item1").getPath();

    String target = this.getClass().getClassLoader().getResource("output/").getPath();

    target = target + "output";
    FileUtils.checkAndMakeDir(target);

    try {
      DataParseInf<FileDataTimeStampEntity> parse = TestBigFileCompare.getDataParse();

      MergeData instance = new MergeData(FileDataEntity.class, src, target, parse);
      // instance.merge();

      List<String> dataList = FileUtils.readTop(target + "/merge-repetition-0.txt", 90);

      Assert.assertEquals("0,0,1,2,3,6", dataList.get(0));
      Assert.assertEquals("1,0,1,2,3,4", dataList.get(1));
      Assert.assertEquals("2,0,1,2,3,6", dataList.get(2));
      Assert.assertEquals(90, dataList.size());
    } finally {
      FileUtils.deleteDir(target);
    }
  }
}
