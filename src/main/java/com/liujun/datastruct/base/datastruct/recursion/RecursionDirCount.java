package com.liujun.datastruct.base.datastruct.recursion;

import java.io.File;

/**
 * directory file count space
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/09/06
 */
public class RecursionDirCount {

  public long directoryCount(File file) {

    long sum = 0;

    if (file.listFiles().length > 0) {
      for (File item : file.listFiles()) {
        if (item.isFile()) {
          sum += item.length();
        } else if (item.isDirectory()) {
          sum += directoryCount(item);
        }
      }
    }

    return sum;
  }
}
