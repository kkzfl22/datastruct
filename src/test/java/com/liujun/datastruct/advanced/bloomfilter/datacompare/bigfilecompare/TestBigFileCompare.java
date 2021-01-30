package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.DataCompareFileOutput;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.constant.CompareConfig;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.ManyFileWriteSize;
import com.liujun.datastruct.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 单元测试大型文件比较
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestBigFileCompare {

  /** 上文件比较 */
  @Test
  public void littleFileCompare() {
    ManyFileWriteSize.DEFAULT_FILE_SIZE = 4096;
    // 获取相关的目录
    // String srcPath = GenerateFile.getSrcPath(GenerateFile.PATh_LITTLE);
    // String targetPath = GenerateFile.getTargetPath(GenerateFile.PATh_LITTLE);
    // String compareOutput = GenerateFile.getCompareOutput(GenerateFile.PATh_LITTLE);

    String srcPath = this.getClass().getClassLoader().getResource("files/littlefile/src").getPath();
    String targetPath =
        this.getClass().getClassLoader().getResource("files/littlefile/target").getPath();
    String compareOutput =
        this.getClass().getClassLoader().getResource("files/littlefile").getPath();
    compareOutput = compareOutput + "/output";
    FileUtils.checkAndMakeDir(compareOutput);

    BigFileCompareInputEntity input =
        new BigFileCompareInputEntity(srcPath, targetPath, compareOutput);

    BigFileCompare<FileDataEntity> bigCompare = new BigFileCompare<>();
    boolean compareRsp =
        bigCompare.fileCompare(
            input, new BigCompareKeyImpl(), getDataParse(), FileDataEntity.class);

    // 结果检查
    String addPath = compareOutput + Symbol.PATH + DataCompareFileOutput.ADD_FILE_NAME;
    // 1,添加的数据
    List<String> dataList = FileUtils.readDirTop(addPath, 90);
    for (int i = 0; i < dataList.size(); i++) {
      Assert.assertEquals(dataList.get(i), i + "," + i + ",0,1,2,35");
    }

    // 2,修改的数据
    String updatePath = compareOutput + Symbol.PATH + DataCompareFileOutput.UPDATE_RSP_NAME;
    List<String> updateList = FileUtils.readDirTop(updatePath, 90);
    for (int i = 0; i < updateList.size(); i++) {
      Assert.assertEquals(
          updateList.get(i), (i + 100) + ",0,1,2,3,4-->" + (i + 100) + ",0,1,2,3,250");
    }

    // 3,删除的数据
    String deletePath = compareOutput + Symbol.PATH + DataCompareFileOutput.DEL_FILE_NAME;
    List<String> deleteListData = FileUtils.readDirTop(deletePath, 90);
    for (int i = 0; i < deleteListData.size(); i++) {
      Assert.assertEquals(deleteListData.get(i), (i + 3200) + ",0,1,2,3,46");
    }

    System.out.println(compareRsp);

    // 文件清理操作
    FileUtils.deleteDir(deletePath);
    FileUtils.deleteDir(updatePath);
    FileUtils.deleteDir(addPath);
  }

  /** 上文件比较 */
  // @Test
  public void bigFileCompare() {
    // 获取相关的目录
    String srcPath = GenerateFile.getSrcPath(GenerateFile.PATh_BIG);
    String targetPath = GenerateFile.getTargetPath(GenerateFile.PATh_BIG);
    String compareOutput = GenerateFile.getCompareOutput(GenerateFile.PATh_BIG);

    BigFileCompareInputEntity input =
        new BigFileCompareInputEntity(srcPath, targetPath, compareOutput);

    BigFileCompare<FileDataEntity> bigCompare = new BigFileCompare<>();
    boolean compareRsp =
        bigCompare.fileCompare(
            input, new BigCompareKeyImpl(), getDataParse(), FileDataEntity.class);
    System.out.println(compareRsp);
  }

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

  public static class DataParseCompare implements DataParseInf<FileDataEntity> {

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
