package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.constant.CompareConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 文件切分输出
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/23
 */
public class FileSpit {

  /** 文件前缀 */
  private static final String NAME_PREFIX = "spitdata-";

  /** 限制单夹的文件个数 */
  private static final int MAX_DIR_FILE = 10;

  /** 文件后缀名 */
  private static final String NAME_SUFFIX = CompareConfig.TEXT_SUFFIX_NAME;

  public static final FileSpit INSTANCE = new FileSpit();

  /**
   * 按指定的大小对原始文件进行切分操作
   *
   * @param srcPath 原始文件路径
   * @param outPath 输出的基础路径
   * @param maxFileSize 限制的文件大小
   * @return 输出的文件列表
   */
  public List<String> spitFile(String srcPath, String outPath, long maxFileSize) {

    // 1,进行多文件连续读取操作
    MultFileReader reader = new MultFileReader(srcPath);
    // 多路文件连接写操作
    MultFileWrite write =
        new MultFileWrite(outPath, NAME_PREFIX, NAME_SUFFIX, MAX_DIR_FILE, maxFileSize);

    try {
      // 打开读取写通道
      reader.open();
      write.open();

      String readLine;
      while ((readLine = reader.readLine()) != null) {
        write.write(readLine);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      write.close();
      reader.close();
    }

    return write.outFileList();
  }
}
