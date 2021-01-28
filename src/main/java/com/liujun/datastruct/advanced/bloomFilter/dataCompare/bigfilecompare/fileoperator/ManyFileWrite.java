package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.fileoperator;

/**
 * 文件写操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class ManyFileWrite {

  /**
   * 按文件大小进行切分操作,使用默认大小32M
   *
   * @param path 路径
   * @param fileNme 文件名
   * @return 切分对象
   */
  public static ManyFileWriteBase manyFileWriteBySize(String path, String fileNme) {
    return new ManyFileWriteSize(path, fileNme);
  }

  /**
   * 按文件按行进行切分操作,使用默认2万行
   *
   * @param path 路径
   * @param fileNme 文件名
   * @return 切分对象
   */
  public static ManyFileWriteBase manyFileWriteByLine(String path, String fileNme) {
    return new ManyFileWriteLine(path, fileNme);
  }
}
