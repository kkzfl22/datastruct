package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare;

import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.entity.BigFileCompareInputEntity;
import org.junit.Test;

/**
 * 单元测试大型文件比较
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestBigFileCompareStage {

  /// ** 数据填充得到布隆过滤器的数据 */
  // @Test
  // public void bigFileCompareStageFull() {
  //  // 获取相关的目录
  //  String srcPath = GenerateFile.getSrcPath(GenerateFile.PATh_BIG);
  //  String targetPath = GenerateFile.getTargetPath(GenerateFile.PATh_BIG);
  //  String compareOutput = GenerateFile.getCompareOutput(GenerateFile.PATh_BIG);
  //
  //  BigFileCompareInputEntity input =
  //      new BigFileCompareInputEntity(srcPath, targetPath, compareOutput);
  //
  //  BigFileCompareStage<FileDataEntity> bigCompare = new BigFileCompareStage<>();
  //
  //  String outPath = "D:\\run\\compare\\bigfile\\compareRsp\\stateSave";
  //
  //  boolean compareRsp =
  //      bigCompare.fileCompareStateFullBloom(
  //          input, new BigCompareKeyImpl(), getDataParse(), outPath);
  //  System.out.println(compareRsp);
  // }
  //
  /// ** 执行对比 */
  // @Test
  // public void bigFileCompare() {
  //  // 获取相关的目录
  //  String srcPath = GenerateFile.getSrcPath(GenerateFile.PATh_BIG);
  //  String targetPath = GenerateFile.getTargetPath(GenerateFile.PATh_BIG);
  //  String compareOutput = GenerateFile.getCompareOutput(GenerateFile.PATh_BIG);
  //
  //  BigFileCompareInputEntity input =
  //      new BigFileCompareInputEntity(srcPath, targetPath, compareOutput);
  //
  //  BigFileCompareStage<FileDataEntity> bigCompare = new BigFileCompareStage<>();
  //
  //  String outPath = "D:\\run\\compare\\bigfile\\compareRsp\\stateSave";
  //
  //  bigCompare.fileCompare(input, new BigCompareKeyImpl(), getDataParse(), outPath);
  // }

  /** 用于对比的主键信息 */
  private class BigCompareKeyImpl implements BigCompareKeyInf<FileDataEntity> {

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
