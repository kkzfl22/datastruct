package com.liujun.algorithm.greedyAlgorithm.huffman;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 使用霍夫漫编码后的数据进行写入磁盘文件
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/19
 */
public class DataOutputStreamHuffman {

  public static final DataOutputStreamHuffman OUTPUT = new DataOutputStreamHuffman();

  public static final String path = "D:\\java\\test\\datastruct\\hoffman\\";

  public void outtoFile(byte[] value) {
    FileOutputStream output = null;
    try {
      output = new FileOutputStream(path + "src.file");
      output.write(value);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != output) {
        try {
          output.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void outHuffmantoFile(byte[] value) {
    FileOutputStream output = null;
    try {
      output = new FileOutputStream(path + "out.huff");
      output.write(value);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != output) {
        try {
          output.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }





}
