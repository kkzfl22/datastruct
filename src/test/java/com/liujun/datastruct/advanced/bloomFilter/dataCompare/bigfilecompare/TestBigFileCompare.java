package com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.advanced.bloomFilter.dataCompare.bigfilecompare.entity.BigFileCompareInputEntity;
import org.junit.Test;

/**
 * 单元测试大型文件比较
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestBigFileCompare {

  /** 大型文件比较 */
  @Test
  public void bigFileCompare() {
    // 获取相关的目录
    String srcPath = GenerateFile.getSrcPath(GenerateFile.PATh_LITTLE);
    String targetPath = GenerateFile.getTargetPath(GenerateFile.PATh_LITTLE);
    String compareOutput = GenerateFile.getCompareOutput(GenerateFile.PATh_LITTLE);

    BigFileCompareInputEntity input =
        new BigFileCompareInputEntity(srcPath, targetPath, compareOutput);

    BigFileCompare bigCompare = new BigFileCompare();
    boolean compareRsp = bigCompare.fileCompare(input, new BigCompareKeyImpl(), getDataParse());
    System.out.println(compareRsp);
  }

  /** 用于对比的主键信息 */
  private class BigCompareKeyImpl implements BigCompareKeyInf<String> {

    @Override
    public String getKey(String data) {
      String[] dataArrays = data.split(Symbol.COMMA);
      return dataArrays[0];
    }

    @Override
    public String getKeyMany(String data) {
      return data;
    }
  }

  public static DataParseInf<FileDataEntity> getDataParse() {
    return new DataParseCompare();
  }

  private static class DataParseCompare implements DataParseInf<FileDataEntity> {

    @Override
    public String toFileLine(FileDataEntity data) {
      return data.entityToLine() + Symbol.LINE;
    }

    @Override
    public FileDataEntity lineToData(String line) {
      return FileDataEntity.lineToEntity(line);
    }
  }
}
