package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.updfile;

import com.liujun.datastruct.utils.FileUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * 多路文件定性主测试
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestManyFileWrite {

  @Test
  public void testFileWrite() {
    String path = "D:\\run\\compare\\littleFile\\dataout\\";

    FileUtils.checkAndMakeDir(path);

    String fileName = "this-file-name";

    try (ManyFileWrite instance = new ManyFileWrite(path, fileName, 1024)) {
      instance.open();
      for (int i = 0; i < 1024; i++) {
        String dataLine = i + "-- this is test index,name;";
        instance.writeLine(dataLine);
      }
      System.out.println("finish");
    } catch (IOException e) {
      e.printStackTrace();
    }

    // 文件清理操作
    FileUtils.deleteDir(path);
  }
}
