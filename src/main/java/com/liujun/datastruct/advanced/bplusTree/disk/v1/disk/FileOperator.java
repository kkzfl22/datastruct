package com.liujun.datastruct.advanced.bplusTree.disk.v1.disk;

import com.config.Symbol;
import com.liujun.datastruct.utils.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 磁盘操作的类
 *
 * @author liujun
 * @since 2022/11/18
 */
public class FileOperator implements AutoCloseable {

  /** 文件路径 */
  private final String filePath;

  /** 文件名称 */
  private final String fileName;

  /** 文件路径 */
  private final String outFilePath;

  /** 输入输出流 */
  private RandomAccessFile randomAccessFile;

  /** 渠道信息 */
  private FileChannel channel;

  public FileOperator(String filePath, String fileName) {
    boolean exists = existsAndMake(filePath);
    if (!exists) {
      throw new IllegalArgumentException("file path create fail");
    }

    this.filePath = filePath;
    this.fileName = fileName;
    this.outFilePath = this.filePath + Symbol.PATH + this.fileName;
  }

  /** 文件读取文件流 */
  public void open() {
    try {
      randomAccessFile = new RandomAccessFile(this.outFilePath, "rw");
      channel = randomAccessFile.getChannel();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 文件夹存在检查
   *
   * @param filePath
   * @return
   */
  private boolean existsAndMake(String filePath) {
    File outPath = new File(filePath);

    if (!outPath.exists()) {
      outPath.mkdirs();
    }

    return outPath.exists();
  }

  /**
   * 文件存在检查
   *
   * @return
   */
  public boolean exists() {
    File outPath = new File(this.outFilePath);
    return outPath.exists();
  }

  /**
   * 读取一个chunk的数据
   *
   * @param position
   * @param buffer
   * @return
   */
  public void readerChunk(long position, ByteBuffer buffer) {

    try {
      // 如果未写入数据，则不读取
      if (this.channel.size() <= position) {
        return;
      }

      // 从指定的文件开始位置，读取一个内存页的数据
      this.channel.read(buffer, position);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 将一个chunk块的数据写入至磁盘中
   *
   * @param position
   * @param buffer
   */
  public void writeChunk(long position, ByteBuffer buffer) {
    try {
      this.channel.write(buffer, position);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 进行数据范盘操作 */
  public void force() {
    try {
      this.channel.force(true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void close() throws Exception {
    IOUtils.close(channel);
    IOUtils.close(randomAccessFile);
  }

  public RandomAccessFile getFile() {
    return randomAccessFile;
  }

  public FileChannel getChannel() {
    return channel;
  }
}
