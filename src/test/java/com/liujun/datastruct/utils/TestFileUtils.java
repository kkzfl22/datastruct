package com.liujun.datastruct.utils;

import com.config.Symbol;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 测试文件
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestFileUtils {

  @Test
  public void testFileRename() throws IOException {
    String path = this.getClass().getClassLoader().getResource(".").getPath();

    String fileName = path + Symbol.PATH + "testName.txt";
    new File(fileName).createNewFile();
    FileUtils.rename(fileName, "test-new-file.txt");
    FileUtils.deleteFile(path + Symbol.PATH + "test-new-file.txt");
  }

  @Test
  public void testDirRename() {
    String path = this.getClass().getClassLoader().getResource(".").getPath();

    String fileName = path + Symbol.PATH + "testDir";
    new File(fileName).mkdirs();
    FileUtils.rename(fileName, "outdir");
  }

  @Test
  public void testGetFileName() throws IOException {
    String path = this.getClass().getClassLoader().getResource(".").getPath();
    String fileName = path + Symbol.PATH + "testName.txt";
    new File(fileName).createNewFile();
    String outFileName = FileUtils.getFileNameNotSuffix(fileName);
    FileUtils.deleteFile(fileName);
    Assert.assertEquals("testName", outFileName);
  }
}
