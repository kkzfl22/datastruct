package com.liujun.datastruct.datacompare.bigfilecompare.merge;

import com.liujun.datastruct.datacompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.datacompare.bigfilecompare.uniquerows.FileDataReader;
import com.liujun.datastruct.utils.FileUtils;
import com.liujun.datastruct.utils.IOUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * 合并的文件操作,使用数组的方式
 *
 * @author liujun
 * @version 0.0.1
 */
public class MergeFileOperator<V> implements AutoCloseable {

  /** 文件操作 */
  private final File[] files;

  /** 数据读取器操作 */
  private final FileDataReader<V>[] dataReader;

  /** 存储的数据 */
  private V[] dataList;

  /** 数据转换操作 */
  private final DataParseInf<V> dataParse;

  /** 对比的键信息 */
  private final BigCompareKeyInf<V> compareKey;

  public MergeFileOperator(
      Class<V> installClass,
      String filePath,
      DataParseInf<V> dataParse,
      BigCompareKeyInf<V> compareKey) {
    this.files = FileUtils.getFileList(filePath);
    this.dataReader = new FileDataReader[this.files.length];
    this.dataParse = dataParse;
    this.dataList = (V[]) Array.newInstance(installClass, this.files.length);
    this.compareKey = compareKey;
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

  /**
   * 读取最小的一个数据，并将相同的数据进行过滤操作
   *
   * @return 最后一条记录，其他记录都被过滤
   * @throws IOException 异常信息
   */
  public V readerMin() throws IOException {
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

      // 查找最小值
      int compareRsp = ((Comparable) dataList[minLast]).compareTo(dataList[i]);
      if (compareRsp == 1) {
        dataTmp = dataList[i];
        minLast = i;

        continue;
      }
    }

    if (null != dataTmp) {
      dataTmp = findKeyMax(dataTmp);
    }
    return dataTmp;
  }

  /**
   * 在相同key中查找最大的值
   *
   * @param dataMinTemp 时间最大值
   * @throws IOException
   */
  private V findKeyMax(V dataMinTemp) throws IOException {
    V dataRspMax = dataMinTemp;
    String valueKey = compareKey.getKey(dataRspMax);

    for (int i = 0; i < dataList.length; i++) {

      // 当数据为空时，则跳过
      if (dataList[i] == null) {
        continue;
      }

      String currKey = compareKey.getKey(dataList[i]);
      if (currKey.equals(valueKey)) {
        V rspMaxTmp = this.nextOtherKey(i, valueKey, dataRspMax);
        // 如果查到的结果比当前大，则说设置当前为最大值
        if ((((Comparable) dataRspMax).compareTo(rspMaxTmp)) == -1) {
          dataRspMax = rspMaxTmp;
        }
      }
    }

    return dataRspMax;
  }

  /**
   * 单个结果 切换到下一个结果
   *
   * @param index
   * @param dataKey
   * @param compareData
   * @return
   * @throws IOException
   */
  private V nextOtherKey(int index, String dataKey, V compareData) throws IOException {
    V rspMax = compareData;
    // 文件需要切换到后一个数据
    while (true) {

      if (dataList[index] == null) {
        break;
      }
      // 优先对当前执行对比
      String key = compareKey.getKey(dataList[index]);
      // 仅找到不同的数据才，进行相关数据的退出操作
      if (!dataKey.equals(key)) {
        break;
      }

      // 对比的结果,找到比当前时间大的，则结果为当前值
      int compareRsp = ((Comparable) rspMax).compareTo(dataList[index]);
      if (compareRsp == -1) {
        rspMax = dataList[index];
      }

      // 读取下一条
      dataList[index] = dataReader[index].read();
      if (dataList[index] == null) {
        break;
      }
    }

    return rspMax;
  }

  @Override
  public void close() throws Exception {
    for (int i = 0; i < dataReader.length; i++) {
      IOUtils.close(dataReader[i]);
    }
  }
}
