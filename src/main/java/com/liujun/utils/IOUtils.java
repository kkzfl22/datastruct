package com.liujun.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/29
 */
public class IOUtils {

  public static void closeStream(Closeable stream) {
    if (null != stream) {
      try {
        stream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
