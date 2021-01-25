package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.config.Symbol;
import com.liujun.datastruct.utils.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 多文件切换写操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/23
 */
public class MultFileWrite {

  /** 文件列表集合 */
  private List<String> fileList = new ArrayList<>();

  /** 文件序号的长度 */
  private static final int NAME_LENGTH = 3;

  /** 文件写入前缀信息 */
  private String fileNamePrefix;

  /** 文件后缀名 */
  private String fileNameSuffix;
  /** 索引信息 */
  private int fileIndex;

  /** 文件夹索引 */
  private int dirIndex;

  /** 单目录文件最大数 */
  private int fileDirMax;

  /** 基础路径 */
  private String basePath;

  /** 文件写入对象 */
  private FileWriter writer;

  /** 带缓冲区的文件写入 */
  private BufferedWriter buffedWrite;

  /** 当前文件大小 */
  private long currFileSize;

  /** 单文件的最大大小 */
  private long maxFileSize;

  public MultFileWrite(
      String basePath, String fileNamePrefix, String suffixName, int fileDirMax, long maxFileSize) {
    this.basePath = basePath;
    this.fileNamePrefix = fileNamePrefix;
    this.fileIndex = 0;
    this.fileDirMax = fileDirMax;
    this.fileNameSuffix = suffixName;
    this.currFileSize = 0;
    this.maxFileSize = maxFileSize;
  }

  public void open() throws IOException {
    String outFile = this.getOutFile();

    this.fileList.add(outFile);

    this.writer = new FileWriter(outFile);
    this.buffedWrite = new BufferedWriter(writer);
  }

  public void write(String line) throws IOException {
    // 1,检查当前数据写入大小加上新写入的数据大小是否超过指写的大小
    byte[] data = line.getBytes();

    // 当数据的写入已经到达了指定的大小时，则切换文件
    if (currFileSize + data.length >= maxFileSize) {
      // 1,关闭当前流
      this.close();
      // 2,检查是否要进行文件夹的切换
      if (maxDirSize()) {
        this.dirIndex++;
        fileIndex = 0;
      } else {
        // 3,进行文件的切换操作
        this.fileIndex++;
      }

      // 4，进行文件的打开操作
      this.open();
      // 重新设置文件的大小
      this.currFileSize = 0;
    }

    // 进行数据写入操作
    this.buffedWrite.write(line);
    // 每写完一次，写入一个换行符
    this.buffedWrite.write(Symbol.LINE);
    this.currFileSize += data.length;
  }

  public List<String> outFileList() {
    return this.fileList;
  }

  public void close() {
    IOUtils.close(buffedWrite);
    IOUtils.close(writer);
  }

  private boolean maxDirSize() {
    return this.fileIndex + 1 >= this.fileDirMax;
  }

  private String getOutFile() {

    StringBuilder outFilName = new StringBuilder();
    outFilName.append(basePath);
    outFilName.append(Symbol.PATH);
    // 第一层路径为文件夹
    outFilName.append(this.getIndexNum(this.dirIndex));
    outFilName.append(Symbol.PATH);

    // 检查文件夹是否存在，不存在，则需要创建
    File outDir = new File(outFilName.toString());

    if (!outDir.exists()) {
      outDir.mkdirs();
    }

    StringBuilder fileName = new StringBuilder();
    // 第二层路径为文件名
    fileName.append(fileNamePrefix);
    fileName.append(this.getIndexNum(this.fileIndex));
    fileName.append(fileNameSuffix);

    // 添加文件名
    outFilName.append(fileName.toString());

    return outFilName.toString();
  }

  private String getIndexNum(int indexParm) {
    String value = String.valueOf(indexParm);
    StringBuilder outSeqIndex = new StringBuilder();

    for (int i = value.length(); i < NAME_LENGTH; i++) {
      outSeqIndex.append("0");
    }
    outSeqIndex.append(indexParm);

    return outSeqIndex.toString();
  }
}
