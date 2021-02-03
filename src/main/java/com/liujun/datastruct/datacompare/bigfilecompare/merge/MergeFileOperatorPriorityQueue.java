package com.liujun.datastruct.datacompare.bigfilecompare.merge;

import com.liujun.datastruct.datacompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.MergeReaderData;
import com.liujun.datastruct.datacompare.bigfilecompare.uniquerows.FileDataReader;
import com.liujun.datastruct.utils.FileUtils;
import com.liujun.datastruct.utils.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 合并的文件操作,使用小顶堆的方式
 *
 * @author liujun
 * @version 0.0.1
 */
public class MergeFileOperatorPriorityQueue<V> implements AutoCloseable {

  /** 文件操作 */
  private final File[] files;

  /** 数据读取器操作 */
  private final FileDataReader<V>[] dataReader;

  /** 存储的数据 */
  private PriorityBlockingQueue<MergeReaderData> dataList;

  /** 数据转换操作 */
  private final DataParseInf<V> dataParse;

  /** 对比的键信息 */
  private final BigCompareKeyInf<V> compareKey;

  public MergeFileOperatorPriorityQueue(
      Class<V> installClass,
      String filePath,
      DataParseInf<V> dataParse,
      BigCompareKeyInf<V> compareKey) {
    this.files = FileUtils.getFileList(filePath);
    this.dataReader = new FileDataReader[this.files.length];
    this.dataParse = dataParse;
    // 构建初始小顶堆的大小
    this.dataList = new PriorityBlockingQueue<>(this.files.length);
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
      dataList.add(new MergeReaderData(dataReader[i].read(), i));
    }
  }

  /**
   * 读取最小的一个数据，并将相同的数据进行过滤操作
   *
   * @return 最后一条记录，其他记录都被过滤
   * @throws IOException 异常信息
   */
  public V readerMin() throws IOException {

    // 获取并移除小顶堆的头
    MergeReaderData<V> data = dataList.poll();
    if (null == data) {
      return null;
    }
    V dataLine = dataReader[data.getIndex()].read();
    if (dataLine != null) {
      // 移除一个，就加入一个
      dataList.add(new MergeReaderData(dataLine, data.getIndex()));
    }

    while (true) {
      // 再检查是否与头一致
      MergeReaderData<V> getFirst = dataList.peek();

      if (getFirst == null) {
        break;
      }

      String dataKey = this.compareKey.getKey(data.getDataEntity());
      String readKey = this.compareKey.getKey(getFirst.getDataEntity());

      // 当主键不一致时，则跳过
      if (!dataKey.equals(readKey)) {
        break;
      }

      // 当数据一样时，则移除当前数据寻找最大的
      if (((Comparable) data.getDataEntity()).compareTo(getFirst.getDataEntity()) <= 0) {
        data = dataList.poll();
      }
      // 非最大直接抛弃
      else {
        dataList.poll();
      }
      V dataEntity = dataReader[data.getIndex()].read();
      if (null != dataEntity) {
        dataList.add(new MergeReaderData(dataEntity, data.getIndex()));
      }
    }

    if (data != null) {
      return data.getDataEntity();
    }

    return null;
  }

  @Override
  public void close() throws Exception {
    for (int i = 0; i < dataReader.length; i++) {
      IOUtils.close(dataReader[i]);
    }
  }
}
