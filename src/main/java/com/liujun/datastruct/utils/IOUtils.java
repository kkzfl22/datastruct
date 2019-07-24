package com.liujun.datastruct.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/29
 */
public class IOUtils {

  /**
   * 关闭操作
   * @param stream
   */
  public static void Close(Closeable stream) {
    if (null != stream) {
      try {
        stream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
