package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.liujun.datastruct.utils.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 多文件合并读取操作
 *
 * <p>非线程安全
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/23
 */
public class MultFileReader {

  /** 文件列表 */
  private List<String> files;

  /** 文件索引 */
  private int index;

  /** 文件流读取对象 */
  private FileReader reader;

  /** 缓存读取对象 */
  private BufferedReader bufferedReader;

  public MultFileReader(String basePath) {
    File readBaseFile = new File(basePath);

    if (readBaseFile.exists()) {

      List<String> fileList = new ArrayList<>();

      // 进行递归查找所有文件
      cyclieDir(readBaseFile, fileList);

      // 将列表赋值给局部变量
      this.files = fileList;
      this.index = 0;
    }
  }

  /**
   * 打开流操作
   *
   * @throws FileNotFoundException
   */
  public void open() throws FileNotFoundException {

    String readPath = files.get(this.index);

    reader = new FileReader(readPath);
    bufferedReader = new BufferedReader(this.reader);
  }

  public String readLine() throws IOException {
    String line = bufferedReader.readLine();

    if (null == line) {
      // 读取完成后，则检查是否存在下一个文件
      if (this.checkNext()) {
        // 首先关闭当前文件
        this.close();
        // 进行索引的添加
        this.index++;
        // 打开下一个文件
        this.open();

        line = bufferedReader.readLine();
      }
    }

    return line;
  }

  public void close() {
    IOUtils.Close(bufferedReader);
    IOUtils.Close(reader);
  }

  /**
   * 进行递归读取文件列表
   *
   * @param readBaseFile 基础文件列表
   * @param fileList 存储文件列表的集合
   */
  private void cyclieDir(File readBaseFile, List<String> fileList) {

    for (File fileItem : readBaseFile.listFiles()) {
      // 如果当前是文件，则直接加入集合中
      if (fileItem.isFile()) {
        fileList.add(fileItem.getPath());
      }
      // 如果非文件，则继续递归
      else if (fileItem.isDirectory()) {
        cyclieDir(fileItem, fileList);
      }
    }
  }

  private boolean checkNext() {
    return this.index + 1 < this.files.size();
  }
}
