package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare;

import com.config.Symbol;
import com.liujun.datastruct.utils.FileUtils;

/**
 * 文件对比结果存放
 *
 * @author liujun
 * @version 0.0.1
 */
public class FileDataCompareRsp {

  /** 对比结果中添加的数据 */
  private static final String ADD_FILE_NAME = "add-data";

  /** 对比结果中修改的数据 */
  private static final String UPDATE_FILE_BEFORE_NAME = "upd-data-before";

  /** 对比结果中修改的数据的内容 */
  private static final String UPDATE_FILE_AFTER_NAME = "upd-data-after";

  /** 对比结果删除的数据 */
  private static final String DEL_FILE_NAME = "del_data";

  /** 数据完成后的命名 */
  private static final String NAME_SUFFIX_ING = ".ing";

  /** 数据完成后的命名 */
  private static final String NAME_SUFFIX_FINISH = ".txt";

  /** 增加数据内容 */
  private FileOutputOperator addFile;

  /** 修改的之前的数据内容 */
  private FileOutputOperator updateFileBefore;

  /** 修改的之后的数据的内容 */
  private FileOutputOperator updateFileAfter;

  /** 删除数据内容 */
  private FileOutputOperator deleteFile;

  /** 基础的路径 */
  private final String basePath;

  public FileDataCompareRsp(String basePath) {
    this.basePath = basePath;
    // 修改文件需要修改目录，以区分before和after
    FileUtils.checkAndMakeDir(this.getUpdBeforePath());
    FileUtils.checkAndMakeDir(this.getUpdAfterPath());
  }

  /** 打开文件写入操作 */
  public void openWriteFile() {
    this.addFile = new FileOutputOperator(getAddFilePath());
    this.updateFileBefore = new FileOutputOperator(this.getUpdBeforeFilePath());
    this.updateFileAfter = new FileOutputOperator(this.getUpdAfterFilePath());
    this.deleteFile = new FileOutputOperator(this.getDelFileName());

    this.addFile.openWrite();
    this.updateFileBefore.openWrite();
    this.updateFileAfter.openWrite();
    this.deleteFile.openWrite();
  }

  /**
   * 添加集合的数据
   *
   * @param dataInfo
   */
  public void writeAddData(String dataInfo) {
    this.addFile.writeData(dataInfo);
  }

  /**
   * 添加修改的数据
   *
   * @param dataInfo
   */
  public void writeUpdateBeforeData(String dataInfo) {
    this.updateFileBefore.writeData(dataInfo);
  }

  /**
   * 写入修改之后的数据
   *
   * @param data 内容信息
   */
  public void writeUpdateAfterData(String data) {
    this.updateFileAfter.writeData(data);
  }

  /**
   * 删除的数据
   *
   * @param deleteData
   */
  public void writeDeleteData(String deleteData) {
    this.deleteFile.writeData(deleteData);
  }

  /**
   * 获取默认的文件名
   *
   * @return 文件名
   */
  public String getAddFilePath() {
    return this.basePath + Symbol.PATH + ADD_FILE_NAME + NAME_SUFFIX_FINISH + NAME_SUFFIX_ING;
  }

  /**
   * 获取修改之前的路径
   *
   * @return 获取修改之前的路径
   */
  private String getUpdBeforePath() {
    return this.basePath + Symbol.PATH + UPDATE_FILE_BEFORE_NAME;
  }

  /**
   * 获取修改之后的路径
   *
   * @return 修改之后的路径
   */
  private String getUpdAfterPath() {
    return this.basePath + Symbol.PATH + UPDATE_FILE_AFTER_NAME;
  }

  /**
   * 获取修改之前的文件名
   *
   * @return 结果数据
   */
  public String getUpdBeforeFilePath() {
    return this.getUpdBeforePath()
        + Symbol.PATH
        + UPDATE_FILE_BEFORE_NAME
        + NAME_SUFFIX_FINISH
        + NAME_SUFFIX_ING;
  }

  /**
   * 获取修改之后的文件路径
   *
   * @return 结果数据
   */
  public String getUpdAfterFilePath() {
    return this.getUpdAfterPath()
        + Symbol.PATH
        + UPDATE_FILE_AFTER_NAME
        + NAME_SUFFIX_FINISH
        + NAME_SUFFIX_ING;
  }

  /**
   * 删除的文件名
   *
   * @return
   */
  public String getDelFileName() {
    return this.basePath + Symbol.PATH + DEL_FILE_NAME + NAME_SUFFIX_FINISH + NAME_SUFFIX_ING;
  }

  /** 执行写入之后的关闭操作 */
  public void closeWrite() {
    closeWrite(this.addFile);
    closeWrite(this.updateFileAfter);
    closeWrite(this.updateFileBefore);
    closeWrite(this.deleteFile);
  }

  /** 操作完成后，进行重命名操作 */
  public void rename() {
    FileUtils.rename(getAddFilePath(), ADD_FILE_NAME + NAME_SUFFIX_FINISH);
    FileUtils.rename(getUpdBeforeFilePath(), UPDATE_FILE_BEFORE_NAME + NAME_SUFFIX_FINISH);
    FileUtils.rename(getUpdAfterFilePath(), UPDATE_FILE_AFTER_NAME + NAME_SUFFIX_FINISH);
    FileUtils.rename(getDelFileName(), DEL_FILE_NAME + NAME_SUFFIX_FINISH);
  }

  private void closeWrite(FileOutputOperator operator) {
    operator.closeWrite();
  }
}
