package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.fileoperator;

import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.fileoperator.ManyFileReader;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试多文件读取
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestManyFileReader {

  /** 测试多文件读取操作 */
  @Test
  public void fileReader() {

    String path = "D:\\run\\compare\\littleFile\\src\\";

    try (ManyFileReader manyReader = new ManyFileReader(path); ) {
      manyReader.openFile();
      String line = null;
      while ((line = manyReader.readLine()) != null) {
        Assert.assertNotNull(line);
        System.out.println(line);
      }
      System.out.println("finish1");
      // System.out.println("finish:" + manyReader.readLine());
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("finish2");
  }
}
