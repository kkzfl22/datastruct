package com.liujun.datastruct.datacompare.bigfilecompare.uniquerows;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.common.BigFileSort;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.AbstractManyFileWrite;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.ManyFileReader;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.ManyFileWrite;
import com.liujun.datastruct.utils.FileUtils;

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

  /** 去重的前的数据 */
  private static final String UNIQUE_DATA_OUT_PATH = "unique-data-before";

  /** 去重的后的数据 */
  private static final String MERGE_DATA_OUTPUT_PATH = "unique-data-after";

  /**
   * 执行大型文件中的去重操作
   *
   * @param path
   */
  public <V> String uniqueRows(
          String path, String outPath, DataParseInf<V> dataParse) {

    String outDataPath = outPath + Symbol.PATH + UNIQUE_DATA_OUT_PATH;
    FileUtils.checkAndMakeDir(outDataPath);

    // 1,按顺序执行文件分隔操作
    this.spitFile(path, outDataPath);
    // 多路文件排序操作,由于需要保证算法的稳定性，选择了JDK提供的插入排序算法
    BigFileSort<V> dataRsp = new BigFileSort<>(outDataPath, dataParse);
    String sortPath = dataRsp.bigFileSort();
    // 进行数据有序行的合并操作,在相同id中，保留最后最一条数据
    // String outMergePath = outPath + Symbol.PATH + MERGE_DATA_OUTPUT_PATH;
    // FileUtils.checkAndMakeDir(outMergePath);
    // MergeRepetitionData merge =
    //    new MergeRepetitionData(dataClass, sortPath, outMergePath, dataParse);
    // merge.merge();

    // if (CompareConfig.DELETE_TMP_FILE_FLAG) {
    //  // 完成后删除去重的临时文件
    //  FileUtils.deleteDir(outDataPath);
    // }

    return sortPath;
  }

  /**
   * 执行文件的切分操作
   *
   * @param path 原始路径
   * @param spitOutPath 分隔后的路径
   */
  private void spitFile(String path, String spitOutPath) {
    try (ManyFileReader reader = new ManyFileReader(path);
         AbstractManyFileWrite write =
            ManyFileWrite.manyFileWriteBySize(spitOutPath, UNIQUE_DATA_OUT_PATH)) {

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
