package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.config.Symbol;

import java.io.File;
import java.util.List;

/**
 * 使用归并排序对1Tb的数据进行排序操作
 *
 * <p>1,归并排序的思想是将文件切分成足够的小文件，
 *
 * <p>2,然后将小文件进行排序
 *
 * <p>3,最后将有充文件进行合并
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/23
 */
public class MergeSort {

  /** 单文件最大大小 */
  private static final long FILE_MAX_SIZE = 256 * 1024 * 1024;

  /** 切分的小文件路径 */
  private static final String SPIT_PATH = "/spitorder";

  public static final MergeSort INSTANCE = new MergeSort();

  /**
   * 进行大文件的排序操作
   *
   * @param srcPath
   * @param outPath
   */
  public void sort(String srcPath, String outPath, String fileName) {

    String outspitPath = outPath + SPIT_PATH;

    // 1, 按指定的大小进行文件的切分操作(但需要保证完整的行，所以非完整的指定大小，会比指定文件略小)
    List<String> fileList = FileSpit.INSTANCE.spitFile(srcPath, outspitPath, FILE_MAX_SIZE);
    // 2， 将所有已经切分的文件进行排序操作
    List<String> sortFileList = FileSort.INSTANCE.sort(fileList);

    // 检查输出文件夹并创建
    File outFile = new File(outPath);
    if (!outFile.exists()) {
      outFile.mkdirs();
    }

    // 3， 针对已经排序完成的所有小文件合并为一个有序大文件
    MergeFile moreFile = new MergeFile(sortFileList, outPath + Symbol.PATH + fileName);
    moreFile.merge();

    // 当操作完成后，需要清理掉磁盘中的数据
    this.cycleChildDelete(new File(outspitPath));
  }

  /**
   * 执行文件夹的删除操作
   *
   * @param filePath
   */
  private void cycleChildDelete(File filePath) {
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
        cycleChildDelete(file);
        // 再删除当前文件夹
        file.delete();
      }
    }
  }
}
