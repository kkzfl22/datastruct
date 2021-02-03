package com.liujun.datastruct.datacompare.bigfilecompare.updfile;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.ManyFileReader;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.ManyFileWrite;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.AbstractManyFileWrite;
import com.liujun.datastruct.utils.FileUtils;

import java.io.IOException;

/**
 * 执行两个有序文件的文件夹的合并操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class UpdateFileMerge {

  /** 修改的之前的文件路径 */
  private final String dataBeforePath;

  /** 修改的之后的文件路径 */
  private final String dataAfterPath;

  /** 修改的文件输出目录 */
  private final String updateOutPath;

  /** 最终输出的文件名 */
  private final String updateOutFileName;

  public UpdateFileMerge(
      String dataBeforePath, String dataAfterPath, String updatePath, String outFileName) {
    this.dataBeforePath = dataBeforePath;
    this.dataAfterPath = dataAfterPath;
    this.updateOutPath = updatePath;
    this.updateOutFileName = outFileName;
    FileUtils.checkAndMakeDir(this.updateOutPath);
  }

  /** 数据合并操作 */
  public void merge() {
    try (
            // 修改之前的文件读取器
            ManyFileReader beforeFileReader = new ManyFileReader(dataBeforePath);
            // 修改之后的文件读取器
            ManyFileReader afterFileReader = new ManyFileReader(dataAfterPath);
            // 打开最终文件写入器
            AbstractManyFileWrite updateWrite =
            ManyFileWrite.manyFileWriteBySize(updateOutPath, updateOutFileName); ) {

      beforeFileReader.openFile();
      afterFileReader.openFile();
      updateWrite.openFile();

      while (true) {
        String beforeReaderLine = beforeFileReader.readLine();
        if (null == beforeReaderLine) {
          break;
        }

        String afterReaderLine = afterFileReader.readLine();

        // 将数据定入至分区文件中
        updateWrite.writeLine(getOutLine(beforeReaderLine, afterReaderLine));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取输出的文件
   *
   * @param beforeLine 之前的文件行
   * @param afterLine 输出之后文件行
   * @return 返回的数据
   */
  private String getOutLine(String beforeLine, String afterLine) {
    String dataBefore = beforeLine.substring(0, beforeLine.indexOf(Symbol.COMMA));
    String dataAfter = afterLine.substring(0, afterLine.indexOf(Symbol.COMMA));
    if (!dataBefore.equals(dataAfter)) {
      throw new IllegalArgumentException(beforeLine + "<-->" + afterLine);
    }

    StringBuilder outData = new StringBuilder();
    outData.append(beforeLine);
    outData.append(Symbol.MINUS);
    outData.append(Symbol.MINUS);
    outData.append(Symbol.RIGHT_FLAG);
    outData.append(afterLine);

    return outData.toString();
  }
}
