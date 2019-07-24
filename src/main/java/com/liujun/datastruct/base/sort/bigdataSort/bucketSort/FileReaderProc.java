package com.liujun.datastruct.base.sort.bigdataSort.bucketSort;

import com.liujun.datastruct.base.sort.bigdataSort.bucketSort.logTimeMerge.FileMergeScope;
import com.liujun.datastruct.utils.IOUtils;

import java.io.*;
import java.util.Date;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/29
 */
public class FileReaderProc {

  public static final FileReaderProc INSTANCE = new FileReaderProc();

  /**
   * 读取文件中的时间，并取得最小和最大时间
   *
   * @param fileName 文件名
   * @param compare 比较
   */
  public void getFileStartScope(String fileName, FileMergeScope compare) {
    FileReader fileReader = null;
    BufferedReader bufferedReader = null;

    try {
      fileReader = new FileReader(fileName);
      bufferedReader = new BufferedReader(fileReader);

      String line;

      while ((line = bufferedReader.readLine()) != null) {

        long currTime = Long.parseLong(line.substring(0, line.indexOf(" ")));

        if (compare.getStartTime() == 0) {
          compare.setStartTime(currTime);
        }

        if (compare.getEndTime() == 0) {
          compare.setEndTime(currTime);
        }

        if (currTime < compare.getStartTime()) {
          compare.setStartTime(currTime);
        } else if (currTime > compare.getEndTime()) {
          compare.setEndTime(currTime);
        }
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.Close(bufferedReader);
      IOUtils.Close(fileReader);
    }
  }

  public void bucketWrite(String filePath) {
    FileReader fileReader = null;
    BufferedReader bufferedReader = null;

    try {
      fileReader = new FileReader(filePath);
      bufferedReader = new BufferedReader(fileReader);

      String line;

      while ((line = bufferedReader.readLine()) != null) {

        long currTime = Long.parseLong(line.substring(0, line.indexOf(" ")));

        BucketOper.INSTANCE.bucketWrite(currTime, line);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.Close(bufferedReader);
      IOUtils.Close(fileReader);
    }
  }

  public void fileReader(String basePath) {

    File readFile = new File(basePath);

    if (readFile.isDirectory()) {
      File[] listFiles = readFile.listFiles();

      for (File itemList : listFiles) {
        if (itemList.isFile()) {
          this.bucketWrite(itemList.getPath());
        }
      }
    }
  }

  public FileMergeScope getFileMerge(String basePath) {
    FileMergeScope scope = new FileMergeScope();

    File readFile = new File(basePath);

    if (readFile.isDirectory()) {
      File[] listFiles = readFile.listFiles();

      for (File itemList : listFiles) {
        if (itemList.isFile()) {
          this.getFileStartScope(itemList.getPath(), scope);
        }
      }
    }

    return scope;
  }

  public static void main(String[] args) {

    String filePath = "D:\\java\\test\\datastruct\\sort\\bigdata\\";

    long startTime = System.currentTimeMillis();

    // 1,
    FileMergeScope scope = FileReaderProc.INSTANCE.getFileMerge(filePath);

    long endTime = System.currentTimeMillis();

    System.out.println("开始时间:" + scope.getStartTime());
    System.out.println("结束时间:" + scope.getEndTime());

    System.out.println(new Date(scope.getStartTime()).toLocaleString());
    System.out.println(new Date(scope.getEndTime()).toLocaleString());

    System.out.println("用时:" + (endTime - startTime));
  }
}
