package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.config.Symbol;
import com.liujun.datastruct.base.sort.bigdataSort.mergeSort.bean.LogInfoBUSI;
import com.liujun.datastruct.base.sort.bigdataSort.mergeSort.bean.SortFileBUSI;
import com.liujun.datastruct.utils.IOUtils;

import java.io.*;
import java.util.*;

/**
 * 针对已经排序完成的所有小文件，进行组合为一个大的有序文件
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/24
 */
public class MergeFile {

  /** 数据缓存的条数 */
  private static final int LOADCACHE_SIZE = 8;

  /** 有序小文件map操作 */
  private final List<SortFileBUSI> sortFileList;

  /** 待排序的数据列表 */
  private final List<LogInfoBUSI> sortList;

  /** 写入的目标文件路径 */
  private final String outfilePath;

  private FileWriter fileWriter;

  private BufferedWriter bufferedWriter;

  public MergeFile(List<String> fileList, String outfilePath) {
    this.sortFileList = this.buildSortFileList(fileList);
    this.sortList = new ArrayList<>(fileList.size());
    this.outfilePath = outfilePath;
    // 打开文件流
    this.open();
  }

  /** 进行文件的合并操作 */
  public void merge() {
    // 1,所有文件都进行第一次文件的缓存加载
    this.loadAllFileDataFirst(sortFileList);
    // 2, 进行一遍所有缓存数据加载
    this.firstLoadSortList(sortFileList);

    try {
      // 循环检查所有数据是否读取完成
      while (!readFinish()) {
        if (sortFileList.size() > 0) {
          // 1,对文件进行排序
          Collections.sort(sortList);

          // 2,将数据列表中的第一数据写入目标文件
          LogInfoBUSI loginInfo = sortList.remove(0);
          // 将数据写入到文件中
          this.writeData(loginInfo);

          // 然后将文件中的数据加载至队列中
          LogInfoBUSI getData = getLoginfo(loginInfo.getFileIndex());

          if (null != getData) {
            this.sortList.add(getData);
          }
        }
      }

      // 完成后检查是否还有数据未写入文件
      if (!this.sortList.isEmpty()) {
        // 1,对文件进行排序
        Collections.sort(sortList);
        while (sortList.size() > 0) {
          // 2,将数据列表中的第一数据写入目标文件
          LogInfoBUSI loginInfo = sortList.remove(0);
          // 将数据写入到文件中
          this.writeData(loginInfo);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      close();
    }
  }

  /**
   * 提取日志数据
   *
   * @param fileIndex
   * @return
   */
  private LogInfoBUSI getLoginfo(int fileIndex) {

    SortFileBUSI fileBusi = sortFileList.get(fileIndex);
    List<LogInfoBUSI> listBusi = fileBusi.getDataLine();

    if (listBusi.size() > 0) {
      // 从提取的缓存中再加载一个到待排序的队列中
      return listBusi.remove(0);
    } else {
      // 如果当前未结束，则需要再将加载
      if (!fileBusi.isReadFinish()) {
        // 进行数据加载
        loadFileData(fileBusi);

        if (listBusi.size() > 0) {
          return listBusi.remove(0);
        }
      }
    }
    return null;
  }

  /**
   * 读取完成检查
   *
   * @return true 已经完成 false 未完成
   */
  private boolean readFinish() {
    for (SortFileBUSI sortFile : sortFileList) {
      // 检查是否加载完成
      if (!sortFile.isReadFinish() || !sortFile.getDataLine().isEmpty()) {
        return false;
      }
    }

    // 当所有文件都已经加载完毕，则标识已经完成
    return true;
  }

  /**
   * 第一次数据加载到待排序的集合中
   *
   * @param sortFileList
   */
  private void firstLoadSortList(List<SortFileBUSI> sortFileList) {
    for (SortFileBUSI sortFile : sortFileList) {
      sortList.add(sortFile.getDataLine().remove(0));
    }
  }

  /**
   * 进行首次的数据加载操作
   *
   * @param sortFileList 文件列表集合
   */
  private void loadAllFileDataFirst(List<SortFileBUSI> sortFileList) {
    for (SortFileBUSI fileBusi : sortFileList) {
      this.loadFileData(fileBusi);
    }
  }

  /**
   * 加载文件的首次数据
   *
   * @param fileBusi 文件信息
   */
  private void loadFileData(SortFileBUSI fileBusi) {

    // 数据未读取完成，则进行读取操作
    if (!fileBusi.isReadFinish()) {

      FileReader reader = null;
      BufferedReader buffered = null;

      try {
        reader = new FileReader(fileBusi.getFilePath());
        reader.skip(fileBusi.getFilePosition());
        buffered = new BufferedReader(reader);

        long position = 0;
        boolean finish = false;
        String line = null;
        for (int i = 0; i < LOADCACHE_SIZE; i++) {
          // 如果能读取到数据，则处理
          if ((line = buffered.readLine()) != null) {
            // 累加读取到的byte数据长度,加1是因为换行符
            position += line.getBytes().length + 1;

            LogInfoBUSI loginfo = LoginfoDataParse.INSTANCE.parseLine(line);
            loginfo.setFileIndex(fileBusi.getFileIndex());

            // 添加到缓存集合中
            fileBusi.getDataLine().add(loginfo);
          }
          // 如果已经读取完成，则退出
          else {
            finish = true;
            break;
          }
        }

        if (finish) {
          fileBusi.setReadFinish(true);
        }

        // 设置新的文件位置
        fileBusi.setFilePosition(fileBusi.getFilePosition() + position);

      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        IOUtils.Close(buffered);
        IOUtils.Close(reader);
      }
    }
  }

  private void open() {
    try {
      this.fileWriter = new FileWriter(this.outfilePath);
      this.bufferedWriter = new BufferedWriter(fileWriter);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void close() {
    IOUtils.Close(bufferedWriter);
    IOUtils.Close(fileWriter);
  }

  /**
   * 构建合并文件列表集合信息
   *
   * @param fileList 输入的文件列表
   * @return 文件列信息
   */
  private List<SortFileBUSI> buildSortFileList(List<String> fileList) {

    List<SortFileBUSI> sortList = new ArrayList<>(fileList.size());

    for (int i = 0; i < fileList.size(); i++) {
      SortFileBUSI sortFileBusi = new SortFileBUSI();

      sortFileBusi.setFilePath(fileList.get(i));
      sortFileBusi.setFilePosition(0L);
      sortFileBusi.setFileIndex(i);

      sortList.add(sortFileBusi);
    }

    return sortList;
  }

  private void writeData(LogInfoBUSI outfile) throws IOException {

    this.bufferedWriter.write(outfile.getLoginfo());
    this.bufferedWriter.write(Symbol.LINE);
  }
}
