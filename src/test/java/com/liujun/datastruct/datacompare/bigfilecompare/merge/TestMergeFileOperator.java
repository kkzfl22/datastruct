package com.liujun.datastruct.datacompare.bigfilecompare.merge;

import com.liujun.datastruct.datacompare.bigfilecompare.FileDataTimeStampEntity;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * 测试文件合并操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestMergeFileOperator {

  /** 测试文件读取操作 */
  @Test
  public void mergeReader() {

    String srcPath = this.getClass().getClassLoader().getResource("files/merge/data1").getPath();

    try (MergeFileOperator<FileDataTimeStampEntity> mergeReader =
        new MergeFileOperator<>(
            FileDataTimeStampEntity.class, srcPath, getDataParse(), getCompareKey()); ) {
      mergeReader.open();
      mergeReader.firstLoader();

      int index = 0;
      while (true) {
        FileDataTimeStampEntity dataEntity = mergeReader.readerMin();

        if (dataEntity == null) {
          break;
        }

        // System.out.println(dataEntity);
        Assert.assertEquals(dataEntity.getKey(), index);
        Assert.assertThat(dataEntity.getTimeStamp(), Matchers.greaterThanOrEqualTo(1729899911800l));
        index++;
      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static DataParseInf<FileDataTimeStampEntity> getDataParse() {
    return new DataParseCompare();
  }

  public static BigCompareKeyInf<FileDataTimeStampEntity> getCompareKey() {
    return new BigCompareKeyImpl();
  }

  /** 用于对比的主键信息 */
  public static class BigCompareKeyImpl implements BigCompareKeyInf<FileDataTimeStampEntity> {

    @Override
    public String getKey(FileDataTimeStampEntity data) {
      return String.valueOf(data.getKey());
    }

    @Override
    public String getKeyMany(FileDataTimeStampEntity data) {
      return data.entityToLine();
    }
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
