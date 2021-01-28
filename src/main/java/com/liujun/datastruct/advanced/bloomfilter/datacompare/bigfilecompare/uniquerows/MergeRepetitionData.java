package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.uniquerows;

import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.AbstractManyFileWrite;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.ManyFileWrite;
import com.liujun.datastruct.utils.FileUtils;
import com.liujun.datastruct.utils.IOUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * 进行有序数据的合并操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class MergeRepetitionData<V> {

  /** 去重后的文件名 */
  private static final String MERGE_REPETITION = "merge-repetition";

  /** 文件操作 */
  private final File[] files;

  /** 数据读取器操作 */
  private final FileDataReader<V>[] dataReader;

  /** 存储的数据 */
  private V[] dataList;

  /** 数据转换操作 */
  private final DataParseInf<V> dataParse;

  /** 输出的路径 */
  private final String outPath;

  public MergeRepetitionData(
      Class<V> installClass, String filePath, String outPath, DataParseInf<V> dataParse) {
    this.files = FileUtils.getFileList(filePath);
    this.dataReader = new FileDataReader[this.files.length];
    this.dataParse = dataParse;
    this.dataList = (V[]) Array.newInstance(installClass, this.files.length);
    this.outPath = outPath;
  }

  /** 读打开文件 */
  public void open() {
    for (int i = 0; i < files.length; i++) {
      dataReader[i] = new FileDataReader(dataParse, files[i]);
      dataReader[i].open();
    }
  }

  /**
   * 首次的全量加载
   *
   * @throws IOException
   */
  public void firstLoader() throws IOException {
    for (int i = 0; i < files.length; i++) {
      dataList[i] = dataReader[i].read();
    }
  }

  /** 进行相关的合并流程 */
  public void merge() {

    // 进行文件的首次打开操作
    open();

    AbstractManyFileWrite manyDataOut =
        ManyFileWrite.manyFileWriteBySize(outPath, MERGE_REPETITION);
    try {
      // 进行首次的数据加载
      firstLoader();

      // 打开写入流操作
      manyDataOut.openFile();

      while (true) {
        V data = this.readerMin();

        if (data == null) {
          break;
        }

        // 执行数据的输出操作
        manyDataOut.writeLine(dataParse.toFileLine(data));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // 执行关闭操作
      IOUtils.close(manyDataOut);
      for (int i = 0; i < dataReader.length; i++) {
        IOUtils.close(dataReader[i]);
      }
    }
  }

  /**
   * 读取最小的一个数据，并将相同的数据进行过滤操作
   *
   * @return 最后一条记录，其他记录都被过滤
   * @throws IOException 异常信息
   */
  private V readerMin() throws IOException {
    V dataTmp = null;
    int minLast = -1;

    for (int i = 0; i < dataList.length; i++) {
      // 确保数据不为空
      if (null == dataList[i]) {
        continue;
      }
      // 第一次找到不为空的
      if (null != dataList[i] && minLast == -1) {
        minLast = i;
        dataTmp = dataList[i];
        continue;
      }

      int compareRsp = ((Comparable) dataList[minLast]).compareTo(dataList[i]);
      // 找出最小的数
      if (compareRsp == 1) {
        dataTmp = dataList[i];
        minLast = i;
      }
      // 当发生数据相同时，后一个数据，覆盖前一个数据
      else if (compareRsp == 0) {
        // 读取到的数据需要继续读取，当数据不与当前数据不同为止,即将所有重复都过滤掉，只留最新的
        while (true) {
          dataList[minLast] = dataReader[minLast].read();

          if (null == dataList[minLast]) {
            break;
          }

          if (((Comparable) dataList[minLast]).compareTo(dataList[i]) != 0) {
            break;
          }
        }

        dataTmp = dataList[i];
        minLast = i;
      }
    }

    if (minLast != -1) {
      // 将最小后，
      while (true) {
        dataList[minLast] = dataReader[minLast].read();

        if (dataList[minLast] == null) {
          break;
        }

        // 仅找到不同的数据才，进行相关数据的退出操作
        if (((Comparable) dataList[minLast]).compareTo(dataTmp) != 0) {
          break;
        }
        dataTmp = dataList[minLast];
      }
    }
    return dataTmp;
  }
}
