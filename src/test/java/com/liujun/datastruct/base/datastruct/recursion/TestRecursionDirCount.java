package com.liujun.datastruct.base.datastruct.recursion;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * test directory file space count
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/06
 */
public class TestRecursionDirCount {

  @Test
  public void testDirectoryCount() {
    File readFile = new File("D:\\java\\test\\datastruct");

    RecursionDirCount instance = new RecursionDirCount();
    long value = instance.directoryCount(readFile);
    Assert.assertEquals(58001412, value);
  }
}
