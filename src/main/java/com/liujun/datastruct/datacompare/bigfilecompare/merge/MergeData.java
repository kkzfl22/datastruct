package com.liujun.datastruct.datacompare.bigfilecompare.merge;

import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;

/**
 * 有序数据操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class MergeData<V> {




  public MergeData(
      Class<V> installClass, String filePath, String outPath, DataParseInf<V> dataParse) {

  }

  /// ** 进行相关的合并流程 */
  // public void merge() {
  //
  //  // 进行文件的首次打开操作
  //  open();
  //
  //  AbstractManyFileWrite manyDataOut =
  //      ManyFileWrite.manyFileWriteBySize(outPath, MERGE_REPETITION);
  //  try {
  //    // 进行首次的数据加载
  //    firstLoader();
  //
  //    // 打开写入流操作
  //    manyDataOut.openFile();
  //
  //    while (true) {
  //      V data = this.readerMin();
  //      if (data == null) {
  //        break;
  //      }
  //
  //      // 执行数据的输出操作
  //      manyDataOut.writeLine(dataParse.toFileLine(data));
  //    }
  //  } catch (IOException e) {
  //    e.printStackTrace();
  //  } finally {
  //    // 执行关闭操作
  //    IOUtils.close(manyDataOut);
  //    for (int i = 0; i < dataReader.length; i++) {
  //      IOUtils.close(dataReader[i]);
  //    }
  //  }
  // }

}
