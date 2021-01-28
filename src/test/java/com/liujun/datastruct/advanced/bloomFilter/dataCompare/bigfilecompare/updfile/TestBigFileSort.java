package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.updfile;

import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.TestBigFileCompare;
import com.liujun.datastruct.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * 进行大文件的切分排序操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestBigFileSort {

  @Test
  public void testSort() {
    String filePath = "D:\\run\\compare\\littleFile\\compareRsp\\del-data\\";
    // 测试文件的切片操作
    BigFileSort fileSort = new BigFileSort(filePath, TestBigFileCompare.getDataParse());
    fileSort.bigFileSort();
    File[] dataItem = new File(filePath).listFiles();
    for (int i = 0; i < dataItem.length; i++) {
      Assert.assertEquals(dataItem[i].length() > 0, true);
    }

    // 文件文件夹删除操作
    FileUtils.deleteDir(filePath);
  }
}
