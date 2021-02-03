package com.liujun.datastruct.datacompare.bigfilecompare.fileoperator;

/**
 * 按行数进行文件的切割
 *
 * @author liujun
 * @version 0.0.1
 */
public class ManyFileWriteLine extends AbstractManyFileWrite {

  /** 默认的文件行数,20000 */
  private static final long DEFAULT_FILE_NUM = 20000;

  /** 增加添加文件的行数 */
  private static final long ADD_INCREMENT_NUM = 1;

  /** 当前文件按行切分的标识符 */
  private final long spitFileNum;

  /** 当前文件的行数 */
  private long fileNum;

  /**
   * 使用默认的大小切换大小
   *
   * @param path
   * @param fileName
   */
  public ManyFileWriteLine(String path, String fileName) {
    this(path, fileName, DEFAULT_FILE_NUM);
  }

  public ManyFileWriteLine(String path, String fileName, long spitFileNum) {
    super(path, fileName);
    this.spitFileNum = spitFileNum;
    this.fileNum = 0;
  }

  @Override
  protected boolean checkSwitchFile(String data) {
    return this.fileNum + ADD_INCREMENT_NUM > spitFileNum;
  }

  @Override
  protected void newSet(String data) {
    this.fileNum = this.fileNum + ADD_INCREMENT_NUM;
  }

  @Override
  protected void reset() {
    this.fileNum = 0;
  }
}
