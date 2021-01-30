package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.entity.DataCompareRsp;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.ManyFileWrite;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.AbstractManyFileWrite;
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

  /** 对比结果中修改的数据 */
  public static final String UPDATE_FILE_BEFORE_NAME = "upd-data-before";

  /** 对比结果中修改的数据的内容 */
  public static final String UPDATE_FILE_AFTER_NAME = "upd-data-after";

  /** 修改的最终目录 */
  public static final String UPDATE_RSP_NAME = "update-data";

  /** 对比结果删除的数据 */
  public static final String DEL_FILE_NAME = "del-data";

  /** 增加数据内容 */
  private AbstractManyFileWrite addFile;

  /** 修改的之前的数据内容 */
  private AbstractManyFileWrite updateFileBefore;

  /** 修改的之后的数据的内容 */
  private AbstractManyFileWrite updateFileAfter;

  /** 删除数据内容 */
  private AbstractManyFileWrite deleteFile;

  /** 基础的路径 */
  private final String basePath;

  public DataCompareFileOutput(String basePath) {
    this.basePath = basePath;
    // 修改文件需要修改目录，以区分before和after
    FileUtils.checkAndMakeDir(this.getAddDirPath());
    FileUtils.checkAndMakeDir(this.getUpdBeforeDirPath());
    FileUtils.checkAndMakeDir(this.getUpdAfterDirPath());
    FileUtils.checkAndMakeDir(this.getDeleteDirPath());
  }

  /** 打开文件写入操作 */
  public void openWriteFile() {
    this.addFile = ManyFileWrite.manyFileWriteBySize(this.getAddDirPath(), this.getAddFileName());
    this.updateFileBefore =
        ManyFileWrite.manyFileWriteBySize(this.getUpdBeforeDirPath(), this.getUpdBeforeName());
    this.updateFileAfter =
        ManyFileWrite.manyFileWriteBySize(this.getUpdAfterDirPath(), this.getUpdAfterFileName());
    this.deleteFile =
        ManyFileWrite.manyFileWriteBySize(this.getDeleteDirPath(), this.getDeleteFileName());

    this.addFile.openFile();
    this.updateFileBefore.openFile();
    this.updateFileAfter.openFile();
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
  public void writeUpdateBeforeData(String dataInfo) throws IOException {
    this.updateFileBefore.writeLine(dataInfo);
  }

  /**
   * 写入修改之后的数据
   *
   * @param data 内容信息
   */
  public void writeUpdateAfterData(String data) throws IOException {
    this.updateFileAfter.writeLine(data);
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
  public String getUpdBeforeDirPath() {
    return this.basePath + Symbol.PATH + UPDATE_FILE_BEFORE_NAME;
  }

  /**
   * 修改文件之前的名称
   *
   * @return 名称信息
   */
  public String getUpdBeforeName() {
    return UPDATE_FILE_BEFORE_NAME;
  }

  /**
   * 获取完整的文件名
   *
   * @return
   */
  public String getUpdBeforeFilePath() {
    return this.getUpdBeforeDirPath() + Symbol.PATH + this.getUpdBeforeName();
  }

  /**
   * 修改文件的最终输出目录
   *
   * @return
   */
  public String getUpdPath() {
    return this.basePath + Symbol.PATH + UPDATE_RSP_NAME;
  }

  /**
   * 获取文件修改的名称
   *
   * @return 名称
   */
  public String getUpdName() {
    return UPDATE_RSP_NAME;
  }

  /**
   * 获取修改之后的路径
   *
   * @return 修改之后的路径
   */
  public String getUpdAfterDirPath() {
    return this.basePath + Symbol.PATH + UPDATE_FILE_AFTER_NAME;
  }

  /**
   * 获取修改的之后的文件名
   *
   * @return
   */
  public String getUpdAfterFileName() {
    return UPDATE_FILE_AFTER_NAME;
  }

  /**
   * 获取当前修改的文件路径
   *
   * @return 路径
   */
  public String getUpdAfterFilePath() {
    return this.getUpdAfterDirPath() + Symbol.PATH + this.getUpdAfterFileName();
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

  @Override
  public void close() throws Exception {
    this.addFile.close();
    this.updateFileAfter.close();
    this.updateFileBefore.close();
    this.deleteFile.close();
  }

  /**
   * 数据输出操作
   *
   * @param rsp 数据信息
   * @throws IOException 异常
   */
  public void dataLineWrite(DataCompareRsp rsp) throws IOException {
    // 添加数据输出
    if (rsp.getAddList() != null && !rsp.getAddList().isEmpty()) {
      for (String line : rsp.getAddList()) {
        this.addFile.writeLine(line);
      }
    }
    // 修改后输出
    if (rsp.getUpdateAfterList() != null && !rsp.getUpdateAfterList().isEmpty()) {
      for (String line : rsp.getUpdateAfterList()) {
        this.updateFileAfter.writeLine(line);
      }
    }
    // 修改前输出
    if (rsp.getUpdateBeforeList() != null && !rsp.getUpdateBeforeList().isEmpty()) {
      for (String line : rsp.getUpdateBeforeList()) {
        this.updateFileBefore.writeLine(line);
      }
    }
    // 删除的输出
    if (rsp.getDeleteList() != null && !rsp.getDeleteList().isEmpty()) {
      for (String line : rsp.getDeleteList()) {
        this.deleteFile.writeLine(line);
      }
    }
  }
}
