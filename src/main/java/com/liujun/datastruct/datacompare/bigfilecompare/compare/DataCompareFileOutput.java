package com.liujun.datastruct.datacompare.bigfilecompare.compare;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.DataCompareOutRsp;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.ManyFileWrite;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.AbstractManyFileWrite;
import com.liujun.datastruct.utils.FileUtils;

import java.io.IOException;

/**
 * 文件对比结果存放
 *
 * @author liujun
 * @version 0.0.1
 */
public class DataCompareFileOutput implements AutoCloseable {

  /** 对比结果中添加的数据 */
  public static final String ADD_FILE_NAME = "add-data";

  /** 修改的最终目录 */
  public static final String UPDATE_RSP_NAME = "update-data";

  /** 对比结果删除的数据 */
  public static final String DEL_FILE_NAME = "del-data";

  /** 增加数据内容 */
  private AbstractManyFileWrite addFile;

  /** 修改的之后的数据的内容 */
  private AbstractManyFileWrite updateFile;

  /** 删除数据内容 */
  private AbstractManyFileWrite deleteFile;

  /** 基础的路径 */
  private final String basePath;

  public DataCompareFileOutput(String basePath) {
    this.basePath = basePath;
    // 修改文件需要修改目录，以区分before和after
    FileUtils.checkAndMakeDir(this.getAddDirPath());
    FileUtils.checkAndMakeDir(this.getUpdDirPath());
    FileUtils.checkAndMakeDir(this.getDeleteDirPath());
  }

  /** 打开文件写入操作 */
  public void openWriteFile() {
    this.addFile = ManyFileWrite.manyFileWriteBySize(this.getAddDirPath(), this.getAddFileName());
    this.updateFile = ManyFileWrite.manyFileWriteBySize(this.getUpdDirPath(), this.getUpdateName());
    this.deleteFile =
        ManyFileWrite.manyFileWriteBySize(this.getDeleteDirPath(), this.getDeleteFileName());

    this.addFile.openFile();
    this.updateFile.openFile();
    this.deleteFile.openFile();
  }

  /**
   * 添加集合的数据
   *
   * @param dataInfo
   */
  public void writeAddData(String dataInfo) throws IOException {
    this.addFile.writeLine(dataInfo);
  }

  /**
   * 添加修改的数据
   *
   * @param dataInfo
   */
  public void writeUpdateData(String dataInfo) throws IOException {
    this.updateFile.writeLine(dataInfo);
  }

  /**
   * 删除的数据
   *
   * @param deleteData
   */
  public void writeDeleteData(String deleteData) throws IOException {
    this.deleteFile.writeLine(deleteData);
  }

  /**
   * 获取默认的文件名
   *
   * @return 文件名
   */
  public String getAddFileName() {
    return ADD_FILE_NAME;
  }

  /**
   * 获取添加目录
   *
   * @return
   */
  public String getAddDirPath() {
    return this.basePath + Symbol.PATH + ADD_FILE_NAME;
  }

  /**
   * 获取添加文件的完整路径
   *
   * @return
   */
  public String getAddFilePath() {
    return this.getAddDirPath() + Symbol.PATH + getAddFileName();
  }

  /**
   * 获取修改之前的路径
   *
   * @return 获取修改之前的路径
   */
  public String getUpdDirPath() {
    return this.basePath + Symbol.PATH + UPDATE_RSP_NAME;
  }

  /**
   * 修改文件之前的名称
   *
   * @return 名称信息
   */
  public String getUpdateName() {
    return UPDATE_RSP_NAME;
  }

  /**
   * 获取完整的文件名
   *
   * @return
   */
  public String getUpdFilePath() {
    return this.getUpdDirPath() + Symbol.PATH + this.getUpdateName();
  }

  /**
   * 待删除数据的目录
   *
   * @return
   */
  public String getDeleteDirPath() {
    return this.basePath + Symbol.PATH + DEL_FILE_NAME;
  }

  /**
   * 对比后删除文件名
   *
   * @return
   */
  public String getDeleteFileName() {
    return DEL_FILE_NAME;
  }

  /**
   * 删除文件路径
   *
   * @return
   */
  public String getDeleteFilePath() {
    return this.getDeleteDirPath() + Symbol.PATH + this.getDeleteFileName();
  }

  public void writeData(DataCompareOutRsp rsp) throws IOException {
    // 添加数据
    if (null != rsp.getAddList() && !rsp.getAddList().isEmpty()) {
      for (String dataItem : rsp.getAddList()) {
        this.writeAddData(dataItem);
      }
    }
    // 修改数据
    if (null != rsp.getUpdateList() && !rsp.getUpdateList().isEmpty()) {
      for (String dataItem : rsp.getUpdateList()) {
        this.writeUpdateData(dataItem);
      }
    }
    // 删除数据
    if (null != rsp.getDeleteList() && !rsp.getDeleteList().isEmpty()) {
      for (String dataItem : rsp.getDeleteList()) {
        this.writeDeleteData(dataItem);
      }
    }
  }

  @Override
  public void close() throws Exception {
    this.addFile.close();
    this.updateFile.close();
    this.deleteFile.close();
  }
}
