package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.liujun.datastruct.utils.IOUtils;

import java.io.*;
import java.util.List;
import java.util.function.Supplier;

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

  /** 默认写入总行数 */
  private static final int DEFAUL_SUM_NUM = 1000;
  /** 限制单文件夹为5个文件 */
  private static final int DEF_MAXFILE_NUM = 8;
  /** 限制单文件的最大大小 */
  private static final long DEF_MAX_FILE_SIZE = 1024;

  /**
   * 根据指定的大小进行数据生成操作
   *
   * @param basePath 基础路径
   * @param sumNum 写入总行数
   * @param fileMaxFileSize 单文件最大大小
   * @param methos 生成数据的方法
   * @return
   */
  public static List<String> Generate(
      String basePath, int sumNum, long fileMaxFileSize, Supplier<String> methos) {
    MultFileWrite write =
        new MultFileWrite(basePath, PREFIX, SUFIXNAME, DEF_MAXFILE_NUM, fileMaxFileSize);

    try {
      write.open();

      for (int i = 0; i < sumNum; i++) {
        write.write(methos.get());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      write.close();
    }

    return write.outFileList();
  }

  /**
   * 生成一个1024行，每行16个字符的数据
   *
   * @return
   */
  public static List<String> DefaultGenerate16(String basePath) {
    return Generate(basePath, DEFAUL_SUM_NUM, DEF_MAX_FILE_SIZE, GenerateData::dataStr);
  }

  /**
   * 执行文件夹的删除操作
   *
   * @param filePath
   */
  public static void CycleDelete(File filePath) {
    if (!filePath.exists()) {
      return;
    }

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
    if (filePath.isFile()) {
      return;
    }

    for (File file : filePath.listFiles()) {
      // 文件直接删除操作
      if (file.isFile()) {
        file.delete();
      }
      // 文件夹，需要先删除里面的文件，再删除文件夹
      else if (file.isDirectory()) {
        // 删除子文件夹中的数据
        CycleChildDelete(file);
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

  /**
   * 读取文件行数
   *
   * @param path 路径
   * @return 行数信息
   */
  public static int GetDataLineNum(String path) {

    int readNum = 0;

    FileReader reader = null;
    BufferedReader bufferedReader = null;

    try {
      reader = new FileReader(path);
      bufferedReader = new BufferedReader(reader);

      while (bufferedReader.readLine() != null) {
        readNum++;
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.Close(bufferedReader);
      IOUtils.Close(reader);
    }

    return readNum;
  }
}
