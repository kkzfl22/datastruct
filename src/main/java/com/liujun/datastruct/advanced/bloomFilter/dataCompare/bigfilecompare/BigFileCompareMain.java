package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare;

import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.entity.FileDataEntity;

/**
 * 单元测试大型文件比较
 *
 * @author liujun
 * @version 0.0.1
 */
public class BigFileCompareMain {

  public static void main(String[] args) {
    // 获取相关的目录
    String srcPath = "D:\\run\\compare\\bigfile\\src";
    String targetPath = "D:\\run\\compare\\bigfile\\target";
    String compareOutput = "D:\\run\\compare\\bigfile\\compareRsp";

    BigFileCompareInputEntity input =
        new BigFileCompareInputEntity(srcPath, targetPath, compareOutput);

    BigFileCompare<FileDataEntity> bigCompare = new BigFileCompare<>();
    boolean compareRsp = bigCompare.fileCompare(input, new BigCompareKeyImpl(), getDataParse());
    System.out.println(compareRsp);
  }

  /** 用于对比的主键信息 */
  private static class BigCompareKeyImpl implements BigCompareKeyInf<FileDataEntity> {

    @Override
    public String getKey(FileDataEntity data) {
      return String.valueOf(data.getKey());
    }

    @Override
    public String getKeyMany(FileDataEntity data) {
      return data.entityToLine();
    }
  }

  public static DataParseInf<FileDataEntity> getDataParse() {
    return new DataParseCompare();
  }

  private static class DataParseCompare implements DataParseInf<FileDataEntity> {

    @Override
    public String toFileLine(FileDataEntity data) {
      return data.entityToLine();
    }

    @Override
    public FileDataEntity lineToData(String line) {
      return FileDataEntity.lineToEntity(line);
    }
  }
}
