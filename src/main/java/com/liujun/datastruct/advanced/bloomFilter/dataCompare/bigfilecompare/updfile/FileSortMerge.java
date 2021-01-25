package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.updfile;

/**
 * 执行两个有序文件的文件夹的合并操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class FileSortMerge {

  /** 修改的之前的文件路径 */
  private final String dataBeforePath;

  /** 修改的之后的文件路径 */
  private final String dataAfterPath;

  public FileSortMerge(String dataBeforePath, String dataAfterPath) {
    this.dataBeforePath = dataBeforePath;
    this.dataAfterPath = dataAfterPath;
  }
}
