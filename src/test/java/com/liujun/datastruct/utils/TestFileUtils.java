package com.liujun.datastruct.utils;

import com.config.Symbol;
import com.google.common.reflect.TypeToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  @Test
  public void testReadTop() {
    String path = this.getClass().getClassLoader().getResource(".").getPath();
    String fileName = path + Symbol.PATH + "testName.txt";

    List<String> dataList = new ArrayList<>(20);
    for (int i = 0; i < 20; i++) {
      dataList.add(RandomStringUtils.randomAlphabetic(100));
    }
    FileUtils.writeList(fileName, dataList);

    List<String> dataListTop = FileUtils.readTop(fileName, 10);

    for (String dataLine : dataListTop) {
      Assert.assertNotNull(dataLine);
    }

    FileUtils.deleteFile(fileName);
  }

  @Test
  public void getIndex() {

    String name = "data:/data/data-12.data";
    int startPosition = name.lastIndexOf(Symbol.MINUS);
    int endPosition = name.lastIndexOf(".data");
    String indexStr = name.substring(startPosition + Symbol.MINUS.length(), endPosition);

    int index = Integer.parseInt(indexStr);
    Assert.assertEquals(index, 12);
  }

  @Test
  public void readList() {
    String path = this.getClass().getClassLoader().getResource(".").getPath();
    String fileName = path + Symbol.PATH + "struct/leetcode/249/target1.txt";
    List<List<String>> dataList = FileUtils.readList(fileName);
    Assert.assertNotNull(dataList);
  }

  @Test
  public void readArray() {
    String path = this.getClass().getClassLoader().getResource(".").getPath();
    String fileName = path + Symbol.PATH + "struct/leetcode/249/data1.txt";
    String[] dataList = FileUtils.readArray(fileName);
    Assert.assertNotNull(dataList);
  }

  @Test
  public void readJonToObject() {
    String path = this.getClass().getClassLoader().getResource(".").getPath();
    String fileName = path + Symbol.PATH + "struct/leetcode/036/data1.json";
    List<List<Character>> dataList =
        FileUtils.readJsonToObject(fileName, new TypeToken<List<List<Character>>>() {}.getType());

    char[][] dataItem = new char[dataList.size()][];
    for (int i = 0; i < dataList.size(); i++) {
      dataItem[i] = new char[dataList.get(i).size()];
      for (int j = 0; j < dataList.get(i).size(); j++) {
        dataItem[i][j] = dataList.get(i).get(j);
      }
    }


  }

  @Test
  public void testCleanCycle() {
    String path = this.getClass().getClassLoader().getResource(".").getPath();

    StringBuilder pathOut = new StringBuilder();
    path = path + Symbol.PATH + "0";
    pathOut.append(path);
    for (int i = 1; i < 10; i++) {
      pathOut.append(Symbol.PATH).append(i);
    }

    FileUtils.checkAndMakeDir(pathOut.toString());
    FileUtils.cleanDirAll(path);

    Assert.assertEquals(false, new File(path).exists());
  }

  @Test
  public void testGetPath() {
    String getPath = FileUtils.getBasePath();
    System.out.println(getPath);
    Assert.assertNotNull(getPath);
  }
}
