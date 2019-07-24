package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.config.Symbol;
import com.liujun.datastruct.base.sort.bigdataSort.mergeSort.bean.LogInfoBUSI;
import com.liujun.datastruct.utils.IOUtils;
import com.liujun.datastruct.utils.PathUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 针对指定的文件进行排序操作
 *
 * <p>将数据读取到内存中命名用内存排序进行操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/24
 */
public class FileSort {

  /** 排序完成后的文件名 */
  private static final String OUT_SUFFIX_NAME = ".order";

  /** 文件排放实例 */
  public static final FileSort INSTANCE = new FileSort();

  /**
   * 进行文件排序操作
   *
   * @param fileList 文件列表
   */
  public List<String> sort(List<String> fileList) {

    List<String> resultList = new ArrayList<>(fileList.size());

    for (String fileItem : fileList) {

      // 进行文件的排序操作
      resultList.add(this.sort(fileItem));
    }
    return resultList;
  }

  /**
   * 进行单文件的排序操作
   *
   * @param srcFile 原始文件
   * @return 排序后的文件
   */
  private String sort(String srcFile) {

    // 1,读取指定的文件
    List<LogInfoBUSI> srcList = this.readFile(srcFile);

    // 2, 进行数据排序操作
    Collections.sort(srcList);

    // 3，计算输出的文件名
    String ourDirFileName = this.getOutFileName(srcFile);

    // 4，将排完整的数据输出
    this.wirteFile(ourDirFileName, srcList);

    // 5,删除排序前的原始文件
    new File(srcFile).delete();

    return ourDirFileName;
  }

  /**
   * 进行数据读取操作
   *
   * @param srcFile
   * @return
   */
  private List<LogInfoBUSI> readFile(String srcFile) {

    List<LogInfoBUSI> resultList = new ArrayList<>();

    FileReader reader = null;
    BufferedReader bufferedReader = null;
    try {
      reader = new FileReader(srcFile);
      bufferedReader = new BufferedReader(reader);

      String line = null;
      while ((line = bufferedReader.readLine()) != null) {
        resultList.add(LoginfoDataParse.INSTANCE.parseLine(line));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.Close(bufferedReader);
      IOUtils.Close(reader);
    }

    return resultList;
  }

  /**
   * 生成排序后的文件名
   *
   * @param srcFile 原始文件名
   * @return 排序后的文件名
   */
  private String getOutFileName(String srcFile) {
    File ourDir = new File(srcFile);

    String basePath = ourDir.getParent();

    String outName = ourDir.getName();

    String suffixName = PathUtils.GetPrefixName(outName);

    return basePath + Symbol.PATH + suffixName + OUT_SUFFIX_NAME;
  }

  /**
   * 进行排序 后的数据输出
   *
   * @param outFilePath 数据输出的路径
   * @param sortList 排序后的数据内容信息
   */
  private void wirteFile(String outFilePath, List<LogInfoBUSI> sortList) {
    FileWriter outWrite = null;
    BufferedWriter buffedWrite = null;

    try {
      outWrite = new FileWriter(outFilePath);
      buffedWrite = new BufferedWriter(outWrite);

      for (LogInfoBUSI loginBean : sortList) {
        buffedWrite.write(loginBean.getLoginfo());
        buffedWrite.write(Symbol.LINE);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.Close(buffedWrite);
      IOUtils.Close(outWrite);
    }
  }
}
