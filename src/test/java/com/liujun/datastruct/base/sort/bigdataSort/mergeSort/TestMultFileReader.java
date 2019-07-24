package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.liujun.constant.TestPropertyEnum;
import com.liujun.utils.PropertiesUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 测试多文件连接读取
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/23
 */
public class TestMultFileReader {

  private static final String BASEPATH =
          PropertiesUtils.getInstance().getValue(TestPropertyEnum.TEST_BASE_PATH);

  private static final String multReadPath = "/readPath";

  /** 进行文件读取测试 */
  @Test
  public void testMultRead() {
    String readPath = BASEPATH;

    MultFileReader read = new MultFileReader(readPath);

    try {
      read.open();
      String line = null;

      int index = 0;
      while ((line = read.readLine()) != null) {
        index++;
      }
      System.out.println(index);
      Assert.assertNotEquals(1, index);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      read.close();
    }
  }
}
