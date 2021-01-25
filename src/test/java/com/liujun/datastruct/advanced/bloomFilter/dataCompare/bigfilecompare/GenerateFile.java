package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare;

import com.config.Symbol;
import com.liujun.datastruct.utils.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
  private static final String DATE_COMPARE = "datafile-";

  /** 文件后缀名 */
  private static final String DATA_COMPARE_SUFFIX = ".data";

  /** 生成8,589,934,592行记录 */
  private static final long DATA_LINE_SUM = 2l << 27;

  /** 单文件大小 */
  private static final int FILE_LINE = 100000000;

  /** 生成8,589,934,592行记录 */
  private static final long DATA_LINE_LITTLE = 2l << 10;

  /** 单文件大小 */
  private static final int FILE_LINE_LITTLE = 100;

  /** 数据列数 */
  private static final int DATA_COLUMN = 5;

  /** 对比结果 */
  private static final String COMPARE_OUTPUT = "compareRsp";

  /** 路径信息 */
  public static final String PATh = "D:\\run\\compare\\bigfile";

  /** 小文件 */
  public static final String PATh_LITTLE = "D:\\run\\compare\\littleFile";

  @Test
  public void generateFile() {
    // 1,生成大文件
    // this.generateBigAllData();
    // 生成小文件
    this.generateLittleAllData();
  }

  /** 生成小文件 */
  private void generateLittleAllData() {
    generateFile(getSrcPath(PATh_LITTLE), DATE_COMPARE, DATA_LINE_LITTLE, FILE_LINE_LITTLE);
    generateFile(getTargetPath(PATh_LITTLE), DATE_COMPARE, DATA_LINE_LITTLE, FILE_LINE_LITTLE);
  }

  /** 生成大文件 */
  private void generateBigAllData() {
    generateFile(getSrcPath(PATh), DATE_COMPARE, DATA_LINE_SUM, FILE_LINE);
    generateFile(getTargetPath(PATh), DATE_COMPARE, DATA_LINE_SUM, FILE_LINE);
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
  private void generateFile(String path, String fileName, long dataLineSum, int fileLineNum) {
    FileUtils.checkAndMakeDir(path);
    // 生成一个文件在

    int fileNum = (int) (dataLineSum / fileLineNum);
    for (int i = 0; i < fileNum; i++) {
      String filePath = path + Symbol.PATH + fileName + i + DATA_COMPARE_SUFFIX;
      // 90%的数据相同
      int sameNum = (int) (fileLineNum - fileLineNum * 0.1);
      GenerateFile.generateSameData(filePath, DATA_COLUMN, sameNum, i * fileLineNum);
      // 10%的数据随机数据
      GenerateFile.generateRandom(
          filePath, DATA_COLUMN, (int) (fileLineNum * 0.1), i * fileLineNum + sameNum);
    }
  }

  /** 随机生成相关的数据内容 */
  public static void generateRandom(String path, int columnSize, int lineSize, int startNum) {
    // 使用文件流操作
    try (FileWriter writer = new FileWriter(path, true);
        BufferedWriter bufferedWriter = new BufferedWriter(writer); ) {
      int randNum = ThreadLocalRandom.current().nextInt(startNum, startNum + lineSize);
      for (int i = startNum; i < randNum; i++) {
        StringBuilder dataLine = new StringBuilder();
        dataLine.append(i);
        dataLine.append(Symbol.COMMA);
        for (int j = 0; j < columnSize; j++) {
          dataLine.append(RandomStringUtils.randomAlphabetic(2 + j));
          dataLine.append(Symbol.COMMA);
        }
        // 去除尾标识符
        dataLine.deleteCharAt(dataLine.length() - 1);
        dataLine.append(Symbol.LINE);
        bufferedWriter.write(dataLine.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 按指定的内容生成 */
  public static void generateSameData(String path, int columnSize, int lineSize, int startNum) {
    // 使用文件流操作
    try (FileWriter writer = new FileWriter(path, true);
        BufferedWriter bufferedWriter = new BufferedWriter(writer); ) {

      for (int i = startNum; i < startNum + lineSize; i++) {
        StringBuilder dataLine = new StringBuilder();
        dataLine.append(i);
        dataLine.append(Symbol.COMMA);
        for (int j = 0; j < columnSize; j++) {
          dataLine.append(j);
          dataLine.append(Symbol.COMMA);
        }
        dataLine.deleteCharAt(dataLine.length() - 1);
        dataLine.append(Symbol.LINE);
        bufferedWriter.write(dataLine.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
