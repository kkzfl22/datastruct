package com.liujun.datastruct.datacompare.bigfilecompare.fileoperator;

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
  public void testFileWriteSize() {
    String path = "D:\\run\\compare\\littleFile\\dataout\\";

    FileUtils.checkAndMakeDir(path);

    String fileName = "this-file-name";

    try (AbstractManyFileWrite instance = ManyFileWrite.manyFileWriteBySize(path, fileName)) {
      instance.openFile();
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

  @Test
  public void testFileWriteLine() {
    String path = "D:\\run\\compare\\littleFile\\dataout\\";

    FileUtils.checkAndMakeDir(path);

    String fileName = "this-file-name";

    try (AbstractManyFileWrite instance = ManyFileWrite.manyFileWriteByLine(path, fileName)) {
      instance.openFile();
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
