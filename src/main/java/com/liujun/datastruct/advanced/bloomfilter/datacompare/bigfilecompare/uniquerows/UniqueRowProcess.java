package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.uniquerows;

import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.common.BigFileSort;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.AbstractManyFileWrite;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.ManyFileReader;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.ManyFileWrite;

import java.io.IOException;

/**
 * 进行数据的去重操作
 *
 * <p>使用归并排序的原理，将数据分区，对每个分区进行排序。再进行重复数据的合并操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class UniqueRowProcess {

  private static final String UNIQUE_DATA_OUT_FILENAME = "unique-data";

  /**
   * 执行大型文件中的去重操作
   *
   * @param path
   */
  public <V> void uniqueRows(
      String path, String outPath, DataParseInf<V> dataParse, Class<V> dataClass) {
    // 1,按顺序执行文件分隔操作
    this.spitFile(path, outPath);
    // 多路文件排序操作,由于需要保证算法的稳定性，选择了JDK提供的插入排序算法
    BigFileSort<V> dataRsp = new BigFileSort<>(outPath, dataParse);
    String sortPath = dataRsp.bigFileSort();
    // 进行数据有序行的合并操作,在相同id中，保留最后最一条数据
    MergeRepetitionData merge = new MergeRepetitionData(dataClass, sortPath, outPath, dataParse);
    merge.merge();
  }

  /**
   * 执行文件的切分操作
   *
   * @param path
   * @param spitOutPath
   */
  private void spitFile(String path, String spitOutPath) {
    try (ManyFileReader reader = new ManyFileReader(path);
        AbstractManyFileWrite write =
            ManyFileWrite.manyFileWriteBySize(spitOutPath, UNIQUE_DATA_OUT_FILENAME)) {

      reader.openFile();
      write.openFile();

      String line;
      while ((line = reader.readLine()) != null) {
        write.writeLine(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
