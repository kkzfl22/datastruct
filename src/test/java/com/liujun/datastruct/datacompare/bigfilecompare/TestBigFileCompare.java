package com.liujun.datastruct.datacompare.bigfilecompare;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataCompareFileOutput;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.ManyFileWriteSize;
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
    ManyFileWriteSize.DEFAULT_FILE_SIZE = 1024 * 1024 * 8;

    String srcPath = this.getClass().getClassLoader().getResource("files/littlefile/src").getPath();
    String targetPath =
        this.getClass().getClassLoader().getResource("files/littlefile/target").getPath();
    String compareOutput =
        this.getClass().getClassLoader().getResource("files/littlefile").getPath();
    compareOutput = compareOutput + "/output";
    FileUtils.checkAndMakeDir(compareOutput);

    String addPath = null;
    String updatePath = null;
    String deletePath = null;
    try {
      BigFileCompareInputEntity input =
          new BigFileCompareInputEntity(srcPath, targetPath, compareOutput);

      BigFileCompare<FileDataTimeStampEntity> bigCompare = new BigFileCompare<>();

      bigCompare.fileCompare(
          input, new BigCompareKeyImpl(), getDataParse(), FileDataTimeStampEntity.class);

      // 当前时间戳的参考
      long timestampAdd = 552014794700l;

      // 结果检查
      addPath = compareOutput + Symbol.PATH + DataCompareFileOutput.ADD_FILE_NAME;

      // 读取的行数
      int readLine = 3000;

      // 1,添加的数据
      List<String> dataList = FileUtils.readDirTop(addPath, readLine);
      for (int i = 0; i < dataList.size(); i++) {
        if (i % 10 == 0) {
          Assert.assertEquals(dataList.get(i), (i + 7000) + "," + (timestampAdd + 10) + ",2,5");
        } else {
          Assert.assertEquals(dataList.get(i), (i + 7000) + "," + (timestampAdd) + ",2,5");
        }
      }

      // 修改的时间戳
      long updateTimestamp = 551974988500L;
      // 2,修改的数据
      updatePath = compareOutput + Symbol.PATH + DataCompareFileOutput.UPDATE_RSP_NAME;
      List<String> updateList = FileUtils.readDirTop(updatePath, readLine);
      for (int i = 0; i < updateList.size(); i++) {
        if (i % 10 == 0) {
          Assert.assertEquals(
              updateList.get(i), getUpdateLine(i, updateTimestamp, timestampAdd, +10));
        } else {
          Assert.assertEquals(
              updateList.get(i), getUpdateLine(i, updateTimestamp, timestampAdd, 0));
        }
      }

      // 3,删除的数据
      long deleteTimestamp = 551974988500L;
      deletePath = compareOutput + Symbol.PATH + DataCompareFileOutput.DEL_FILE_NAME;
      List<String> deleteListData = FileUtils.readDirTop(deletePath, readLine);
      for (int i = 0; i < deleteListData.size(); i++) {
        if (i % 10 == 0) {
          Assert.assertEquals(deleteListData.get(i), i + "," + (deleteTimestamp + 10) + ",1,2");
        } else {
          Assert.assertEquals(deleteListData.get(i), i + "," + (deleteTimestamp) + ",1,2");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      //// 文件清理操作
      FileUtils.deleteDir(deletePath);
      FileUtils.deleteDir(updatePath);
      FileUtils.deleteDir(addPath);

      FileUtils.cleanDirAll(compareOutput);
    }
  }

  private String getUpdateLine(int index, long timestampFlag, long timestampAfter, int increment) {
    StringBuilder outData = new StringBuilder();

    outData.append(index + 3000);
    outData.append(",");
    outData.append(timestampFlag + increment);
    outData.append(",1,2-->");
    outData.append(index + 3000);
    outData.append(",");
    outData.append(timestampAfter + increment);
    outData.append(",2,5");

    return outData.toString();
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

    BigFileCompare<FileDataTimeStampEntity> bigCompare = new BigFileCompare<>();
    boolean compareRsp =
        bigCompare.fileCompare(
            input, getCompareKey(), getDataParse(), FileDataTimeStampEntity.class);
    System.out.println(compareRsp);
  }

  public static BigCompareKeyInf<FileDataTimeStampEntity> getCompareKey() {
    return new BigCompareKeyImpl();
  }

  /** 用于对比的主键信息 */
  private static class BigCompareKeyImpl implements BigCompareKeyInf<FileDataTimeStampEntity> {

    @Override
    public String getKey(FileDataTimeStampEntity data) {
      return String.valueOf(data.getKey());
    }

    @Override
    public String getKeyMany(FileDataTimeStampEntity data) {
      return data.entityToLine();
    }
  }

  public static DataParseInf<FileDataTimeStampEntity> getDataParse() {
    return new DataParseCompare();
  }

  public static class DataParseCompare implements DataParseInf<FileDataTimeStampEntity> {

    @Override
    public String toFileLine(FileDataTimeStampEntity data) {
      return data.entityToLine();
    }

    @Override
    public FileDataTimeStampEntity lineToData(String line) {
      return FileDataTimeStampEntity.lineToEntity(line);
    }
  }
}
