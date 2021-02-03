package com.liujun.datastruct.datacompare.bigfilecompare.fileoperator;

import com.config.Symbol;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

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

  /** 测试多文件排序读取操作 */
  @Test
  public void fileSortReader() {

    String path = "D:\\run\\compare\\littleFile\\src\\";

    try (ManyFileReader manyReader =
        new ManyFileReader(
            path,
            (o1, o2) -> {
              int index1 = getFileIndex(o1);
              int index2 = getFileIndex(o2);
              if (index1 < index2) {
                return -1;
              } else if (index1 > index2) {
                return 1;
              }

              return 0;
            })) {
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

  private int getFileIndex(File file) {
    String name = file.getName();
    int suffixIndex = name.lastIndexOf(Symbol.POINT);
    int spitIndex = name.lastIndexOf(Symbol.MINUS);
    return Integer.parseInt(name.substring(spitIndex + Symbol.MINUS.length(), suffixIndex));
  }
}
