package com.liujun.datastruct.heap.solution.margeSmailFile;

import com.liujun.datastruct.heap.solution.margeSmailFile.pojo.OutFileBusi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件输出操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/30
 */
public class FileOutProcess {

  /** 文件处理操作 */
  public static final FileOutProcess INSTANCE = new FileOutProcess();

  /** 最大缓冲区大小 */
  private static final int MAX_BUFFER = 10;

  /**
   * 打开文件操作
   *
   * @param path
   * @return
   */
  public OutFileBusi openFile(String path) {

    OutFileBusi outFile = new OutFileBusi(MAX_BUFFER);

    outFile.setOutPath(path);
    try {
      outFile.setOutputStream(new FileOutputStream(path));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return outFile;
  }

  public void fileWrite(OutFileBusi busiBuff) {
    FileOutputStream outputStream = busiBuff.getOutputStream();

    try {
      outputStream.write(busiBuff.getBuffer(), 0, busiBuff.getOutIndex());
      // 将数据刷入磁盘
      outputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    busiBuff.setOutIndex(0);
  }

  public void close(OutFileBusi busiBuff) {
    if (null != busiBuff) {
      try {
        busiBuff.getOutputStream().close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
