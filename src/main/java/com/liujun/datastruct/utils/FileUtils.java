package com.liujun.datastruct.utils;

import com.config.Symbol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 公共的文件夹创建
 *
 * @author liujun
 * @version 0.0.1
 */
public class FileUtils {

  /** 小文件的标识 */
  private static final long LITTLE_SIZE = 1 * 1024 * 1024;

  /**
   * 文件的检查创建操作
   *
   * @param path 文件路信息
   */
  public static void checkAndMakeDir(String path) {
    File fileCheck = new File(path);
    // 如果当前文件已经存在，则执行返回
    if (fileCheck.exists()) {
      return;
    }
    // 当文件不存在时，则执行文件的创建操作
    else {
      fileCheck.mkdirs();
    }
  }

  /**
   * 执行文件的删除操作
   *
   * @param path 路径信息
   * @return true 删除成功 false 删除失败
   */
  public static boolean deleteFile(String path) {
    return new File(path).delete();
  }

  /**
   * 检查文件是否存在，存在则删除操作
   *
   * @param path 文件路径
   * @return true 删除成功 false 删除失败
   */
  public static boolean deleteOnExists(String path) {

    File fileCheck = new File(path);

    if (fileCheck.exists()) {
      return fileCheck.delete();
    }

    return false;
  }

  /**
   * 读取小文件的内容
   *
   * @param path
   * @return
   */
  public static String readLittleFile(String path) {
    File fileInfo = new File(path);

    if (fileInfo.isFile() && fileInfo.length() <= LITTLE_SIZE) {
      StringBuilder dataLine = new StringBuilder((int) fileInfo.length());
      try (FileReader reader = new FileReader(path);
          BufferedReader bufferedReader = new BufferedReader(reader)) {
        String lineMsg = null;
        while ((lineMsg = bufferedReader.readLine()) != null) {
          dataLine.append(lineMsg + Symbol.LINE);
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return dataLine.toString();
    }
    return Symbol.EMPTY;
  }

  /**
   * 获取文件列表
   *
   * @param path 路径信息
   * @return 成功返回列表，不存在，则返回一个空对象
   */
  public static File[] getFileList(String path) {
    File fileInfo = new File(path);
    if (fileInfo.exists()) {
      return fileInfo.listFiles();
    }
    return new File[0];
  }

  /**
   * 对文件执行重命名操作
   *
   * @param filePath
   * @param newName
   */
  public static void rename(String filePath, String newName) {
    File file = new File(filePath);

    File newFilePath = null;
    if (file.isFile()) {
      newFilePath = file.getParentFile();
    } else if (file.isDirectory()) {
      newFilePath = new File(file.getParent());
    }

    file.renameTo(new File(newFilePath, newName));
  }

  /**
   * 获取文件名不带后缀
   *
   * @param fileName 文件名
   */
  public static String getFileNameNotSuffix(String fileName) {
    File fileInfo = new File(fileName);
    // 非文件无后缀名
    if (!fileInfo.isFile()) {
      return Symbol.EMPTY;
    }
    // 去掉文件后缀名
    String dataName = fileInfo.getName();
    int dot = dataName.indexOf(Symbol.POINT);
    if (dot > -1 && dot < dataName.length() - 1) {
      return dataName.substring(0, dot);
    }
    return Symbol.EMPTY;
  }

  /**
   * 删除文件夹，文件存在时，也删除文件,不执行递归删除
   *
   * @param filePath 文件路径
   */
  public static void deleteDir(String filePath) {
    File dataFile = new File(filePath);

    if (dataFile.isDirectory()) {
      for (File dataItem : dataFile.listFiles()) {
        dataItem.delete();
      }
      dataFile.delete();
    }
  }
}
