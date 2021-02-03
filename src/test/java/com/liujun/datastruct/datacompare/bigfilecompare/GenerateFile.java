package com.liujun.datastruct.datacompare.bigfilecompare;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.ManyFileWrite;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.AbstractManyFileWrite;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.ManyFileWriteSize;
import com.liujun.datastruct.utils.FileUtils;
import org.junit.Test;

import java.io.IOException;

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

  /** 生成十亿行记录 */
  private static final long DATA_LINE_SUM = 1000000000;

  /** 单文件大小 */
  private static final int FILE_LINE_LITTLE = 1000;

  /** 对比结果 */
  private static final String COMPARE_OUTPUT = "compareRsp";

  /** 路径信息 */
  public static final String PATh_BIG = "D:\\run\\compare\\bigfile";

  /** 超大文件路径信息 */
  public static final String PATh_SUPPER_BIG = "D:\\run\\compare\\supperBigfile";

  /** 小文件 */
  public static final String PATh_LITTLE = "D:\\run\\compare\\littleFile";

  @Test
  public void generateFile() {
    // 设置以128M为大小切分数据
    ManyFileWriteSize.DEFAULT_FILE_SIZE = 1024 * 1024 * 128;

    // 1,生成大文件
    // this.generateBigAllData();
    // 生成小文件
    this.generateLittleAllData();
  }

  /** 生成小文件 */
  private void generateLittleAllData() {
    generateFile(getSrcPath(PATh_LITTLE), DATE_COMPARE, FILE_LINE_LITTLE, new String[] {"1", "2"});
    generateFile(
        getTargetPath(PATh_LITTLE), DATE_COMPARE, FILE_LINE_LITTLE, new String[] {"1", "2"});
  }

  /** 生成超大文件 */
  private void generateSupperBigData() {
    // 1,生成3个亿的删除数据
    // 2,再生成7个亿的修改数据
    // 3.再生成3个亿的添加数据

    generateFile(
        getSrcPath(PATh_SUPPER_BIG), DATE_COMPARE, FILE_LINE_LITTLE, new String[] {"1", "2"});
    generateFile(
        getTargetPath(PATh_SUPPER_BIG), DATE_COMPARE, FILE_LINE_LITTLE, new String[] {"1", "2"});
  }

  /** 生成大文件 */
  private void generateBigAllData() {
    generateFile(getSrcPath(PATh_BIG), DATE_COMPARE, DATA_LINE_SUM, new String[] {"1", "2"});
    generateFile(getTargetPath(PATh_BIG), DATE_COMPARE, DATA_LINE_SUM, new String[] {"1", "2"});
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
  private void generateFile(String path, String fileName, long dataSize, String[] value) {
    FileUtils.checkAndMakeDir(path);

    // 生成一个文件在
    try (AbstractManyFileWrite writeData = ManyFileWrite.manyFileWriteBySize(path, fileName)) {
      writeData.openFile();
      long timeStampValue = System.nanoTime();
      for (long i = 0; i < dataSize; i++) {
        // 一行数据
        String data = this.getSameData(i, timeStampValue, value);
        writeData.writeLine(data);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取相关的数据
   *
   * @param key 键信息
   * @param timestampValue 列大小
   * @return 行数据
   */
  private String getSameData(long key, long timestampValue, String[] value) {
    StringBuilder dataLine = new StringBuilder();
    dataLine.append(key);
    dataLine.append(Symbol.COMMA);
    dataLine.append(timestampValue);
    dataLine.append(Symbol.COMMA);
    // 数据填充
    for (int j = 0; j < value.length; j++) {
      dataLine.append(value[j]);
      dataLine.append(Symbol.COMMA);
    }
    dataLine.deleteCharAt(dataLine.length() - 1);

    return dataLine.toString();
  }
}
