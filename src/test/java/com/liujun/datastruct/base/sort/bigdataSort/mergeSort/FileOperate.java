package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 文件测试的一些公共操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/24
 */
public class FileOperate {

  private static final String PREFIX = "outseq-";
  private static final String SUFIXNAME = ".txt";
  /** 限制单文件夹为5个文件 */
  private static final int MAXFILE_NUM = 8;
  /** 限制单文件的最大大小 */
  private static final long MAX_FILE_SIZE = 1024;

  /**
   * 根据指定的大小进行数据生成操作
   *
   * @return
   */
  public static List<String> Generate(String basePath, int size, int lineSize) {
    MultFileWrite write =
        new MultFileWrite(basePath, PREFIX, SUFIXNAME, MAXFILE_NUM, MAX_FILE_SIZE);

    try {
      write.open();

      char data;
      for (int i = 0; i < size; i++) {
        String lineData = "";

        for (int j = 0; j < lineSize; j++) {
          data = (char) ThreadLocalRandom.current().nextInt(0x61, 0x7a);
          lineData = lineData + data;
        }

        write.write(lineData);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      write.close();
    }

    return write.outFileList();
  }

  /**
   * 根据指定的大小进行数据生成操作,此生成每行16个字符
   *
   * @return
   */
  public static List<String> DefaultGenerate16(String basePath) {
    return Generate(basePath, 1024, 16);
  }

  /**
   * 执行文件夹的删除操作
   *
   * @param filePath
   */
  public static void CycleDelete(File filePath) {
    // 先删除子文件夹信息
    CycleChildDelete(filePath);
    // 最后删除当前文件夹
    filePath.delete();
  }

  /**
   * 执行文件夹的删除操作
   *
   * @param filePath
   */
  private static void CycleChildDelete(File filePath) {
    for (File file : filePath.listFiles()) {
      // 文件直接删除操作
      if (file.isFile()) {
        file.delete();
      }
      // 文件夹，需要先删除里面的文件，再删除文件夹
      else if (file.isDirectory()) {
        // 删除子文件夹中的数据
        CycleDelete(file);
        // 再删除当前文件夹
        file.delete();
      }
    }
  }

  /**
   * 包装接收一个String类型的参数，进行指定路径的删除操作
   *
   * @param base
   */
  public static void CycleDelete(String base) {
    CycleDelete(new File(base));
  }
}
