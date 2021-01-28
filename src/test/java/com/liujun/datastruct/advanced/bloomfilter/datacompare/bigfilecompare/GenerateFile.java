package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.ManyFileWrite;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.AbstractManyFileWrite;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.ManyFileWriteSize;
import com.liujun.datastruct.utils.FileUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 生成相关的测试数据文件
 *
 * @author liujun
 * @version 0.0.1
 */
public class GenerateFile {

  /** 对比的源文件 */
  private static final String COMPARE_SRC = "src";

  /** 对比的目标文件 */
  private static final String COMPARE_TARGET = "target";

  /** 文件名 */
  private static final String DATE_COMPARE = "datafile";

  /** 生成8,589,934,592行记录 */
  private static final long DATA_LINE_SUM = 100000000;

  /** 生成8,589,934,592行记录 */
  private static final long DATA_LINE_LITTLE = 2l << 10;

  /** 单文件大小 */
  private static final int FILE_LINE_LITTLE = 100;

  /** 数据列数 */
  private static final int DATA_COLUMN = 5;

  /** 对比结果 */
  private static final String COMPARE_OUTPUT = "compareRsp";

  /** 路径信息 */
  public static final String PATh_BIG = "D:\\run\\compare\\bigfile";

  /** 小文件 */
  public static final String PATh_LITTLE = "D:\\run\\compare\\littleFile";

  @Test
  public void generateFile() {
    // 设置以128M为大小切分数据
    ManyFileWriteSize.DEFAULT_FILE_SIZE = 1024 * 1024 * 128;

    // 1,生成大文件
    this.generateBigAllData();
    // 生成小文件
    // this.generateLittleAllData();
  }

  /** 生成小文件 */
  private void generateLittleAllData() {
    generateFile(getSrcPath(PATh_LITTLE), DATE_COMPARE, FILE_LINE_LITTLE);
    generateFile(getTargetPath(PATh_LITTLE), DATE_COMPARE, FILE_LINE_LITTLE);
  }

  /** 生成大文件 */
  private void generateBigAllData() {
    generateFile(getSrcPath(PATh_BIG), DATE_COMPARE, DATA_LINE_SUM);
    generateFile(getTargetPath(PATh_BIG), DATE_COMPARE, DATA_LINE_SUM);
  }

  public static String getSrcPath(String path) {
    return path + Symbol.PATH + COMPARE_SRC;
  }

  public static String getTargetPath(String path) {
    return path + Symbol.PATH + COMPARE_TARGET;
  }

  /**
   * 对比结果
   *
   * @param path
   * @return
   */
  public static String getCompareOutput(String path) {
    return path + Symbol.PATH + COMPARE_OUTPUT;
  }

  /**
   * 文件的生成操作
   *
   * @param path
   */
  private void generateFile(String path, String fileName, long dataSize) {
    FileUtils.checkAndMakeDir(path);

    long start = System.currentTimeMillis();
    long threshold = 2000000;
    // 生成一个文件在
    try (AbstractManyFileWrite writeData = ManyFileWrite.manyFileWriteBySize(path, fileName)) {

      writeData.openFile();

      for (long i = 0; i < dataSize; i++) {
        if (i % 2 == 0) {
          // 一行正常的数据
          String data = this.getSameData(i, DATA_COLUMN);
          writeData.writeLine(data);
        } else {
          String data = this.getRandomData(i, DATA_LINE_SUM, DATA_COLUMN);
          writeData.writeLine(data);
        }
        if (i > threshold) {
          long endTime = System.currentTimeMillis();
          System.out.println("当前生成:" + i + "，用时" + (endTime - start) + "毫秒");
          threshold = threshold + 2000000;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取相关的数据
   *
   * @param key 键信息
   * @param columnSize 列大小
   * @return 行数据
   */
  private String getSameData(long key, int columnSize) {
    StringBuilder dataLine = new StringBuilder();
    dataLine.append(key);
    dataLine.append(Symbol.COMMA);
    dataLine.append(System.nanoTime());
    dataLine.append(Symbol.COMMA);
    for (int j = 0; j < columnSize; j++) {
      dataLine.append(j);
      dataLine.append(Symbol.COMMA);
    }
    dataLine.deleteCharAt(dataLine.length() - 1);

    return dataLine.toString();
  }

  /**
   * 随机生成数据
   *
   * @param start 开始区间
   * @param end 结束区间
   * @param columnSize 列数
   * @return 数据
   */
  private String getRandomData(long start, long end, int columnSize) {
    long randNum = ThreadLocalRandom.current().nextLong(start, end);
    StringBuilder dataLine = new StringBuilder();
    dataLine.append(randNum);
    dataLine.append(Symbol.COMMA);
    dataLine.append(System.nanoTime());
    dataLine.append(Symbol.COMMA);
    for (int j = 0; j < columnSize; j++) {
      dataLine.append(j);
      dataLine.append(Symbol.COMMA);
    }
    dataLine.deleteCharAt(dataLine.length() - 1);

    return dataLine.toString();
  }
}
