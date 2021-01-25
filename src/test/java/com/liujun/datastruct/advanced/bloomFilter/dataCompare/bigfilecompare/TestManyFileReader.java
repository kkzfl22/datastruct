package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare;

import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.ManyFileReader;
import org.junit.Test;

import java.util.List;

/**
 * 测试多文件读取
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestManyFileReader {

  @Test
  public void manyReader() {
    String fileDataPath = "D:\\run\\compare\\littleFile\\src";

    ManyFileReader read = new ManyFileReader(fileDataPath);

    for (int i = 0; i < 3; i++) {
      try {
        read.reload();
        // 打开文件操作
        read.open();

        while (true) {
          List<String> dataList = read.readLineMany(500);
          if (dataList.isEmpty()) {
            break;
          }
          System.out.println(dataList.size());
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        read.close();
      }
      System.out.println("----");
    }
  }
}
