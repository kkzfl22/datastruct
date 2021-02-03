package com.liujun.datastruct.datacompare.bigfilecompare;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.AbstractManyFileWrite;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.ManyFileWrite;
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
public class GenerateSupperGigFile {

  /** 对比的源文件 */
  private static final String COMPARE_SRC = "src";

  /** 对比的目标文件 */
  private static final String COMPARE_TARGET = "target";

  /** 文件名 */
  private static final String DATE_COMPARE = "datafile";

  /** 超大文件路径信息 */
  public static final String PATh_SUPPER_BIG = "F:\\run\\compare\\supperBigfile";

  /** 小型文件路径信息 */
  public static final String PATH_LITTLE_FILE = "F:\\run\\compare\\little";

  @Test
  public void generateFile() {
    // 设置以128M为大小切分数据
    ManyFileWriteSize.DEFAULT_FILE_SIZE = 1024 * 1024 * 128;

    // 生成小文件
    // this.generateSupperBigData();
    this.generateLitterFileData();
  }

  /** 生成超大文件 */
  private void generateLitterFileData() {
    // 1,生成3个亿的删除数据,和3亿的修改数据
    generateFile(
        getSrcPath(PATH_LITTLE_FILE), DATE_COMPARE, 0, 1000, 7000, new String[] {"1", "2"});

    generateFile(
        getTargetPath(PATH_LITTLE_FILE), DATE_COMPARE, 3000, 7000, 10000, new String[] {"2", "5"});
  }

  /** 生成超大文件 */
  private void generateSupperBigData() {
    // 1,生成3个亿的删除数据,和3亿的修改数据
    generateFile(
        getSrcPath(PATh_SUPPER_BIG),
        DATE_COMPARE,
        0,
        300000000,
        700000000,
        new String[] {"1", "2"});

    generateFile(
        getTargetPath(PATh_SUPPER_BIG),
        DATE_COMPARE,
        300000000,
        700000000,
        1000000000,
        new String[] {"2", "5"});
  }

  public static String getSrcPath(String path) {
    return path + Symbol.PATH + COMPARE_SRC;
  }

  public static String getTargetPath(String path) {
    return path + Symbol.PATH + COMPARE_TARGET;
  }

  /**
   * 文件的生成操作
   *
   * @param path
   */
  private void generateFile(
      String path, String fileName, long start, long point, long end, String[] value) {
    FileUtils.checkAndMakeDir(path);

    // 生成一个文件在
    try (AbstractManyFileWrite writeData = ManyFileWrite.manyFileWriteBySize(path, fileName)) {
      writeData.openFile();
      long timeStampValue = System.nanoTime();

      // 前段数据
      this.writeData(writeData, timeStampValue, start, point, value);
      // 后段数据
      this.writeData(writeData, timeStampValue, point, end, value);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 进行数据的写入操作
   *
   * @param writeData 数据写入对象
   * @param timeStampValue 时间值
   * @param start 开始的标识
   * @param end 结束标识
   * @param value 数据值
   * @throws IOException 异常信息
   */
  private void writeData(
      AbstractManyFileWrite writeData, long timeStampValue, long start, long end, String[] value)
      throws IOException {
    for (long i = start; i < end; i++) {
      // 一亿行数据
      String data = this.getSameData(i, timeStampValue, value);
      writeData.writeLine(data);
      // 写入重复数据做为测试
      if (i % 10 == 0) {
        data = this.getSameData(i, timeStampValue + 10, value);
        writeData.writeLine(data);
      }

      // 写入重复数据做为测试,
      if (i % 10 == 2) {
        data = this.getSameData(i, timeStampValue - 10, value);
        writeData.writeLine(data);
      }
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
