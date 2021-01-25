package com.liujun.datastruct.base.datastruct.heap.solution.bigFileTopN;

import com.liujun.datastruct.base.datastruct.heap.solution.bigFileTopN.pojo.PartitionBusi;
import com.liujun.datastruct.utils.IOUtils;

import java.io.*;

/**
 * 进行文件的分区操作，以hash取模进行分区
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/02
 */
public class PartitionFile {

  /** 分隔的实例信息 */
  public static final PartitionFile INSTANCE = new PartitionFile();

  /** 分隔成64个文件 */
  private static final int MAX_PARTITION_SIZE = 4;

  /** 分区的文件夹名称 */
  private static final String PARTITION_DIR_NAME = "partition";

  /** 中间的临时分隔文件 */
  private static final String SUFFIXE_NAME = ".buffer";

  public PartitionBusi[] getPartition(String basePath) {
    PartitionBusi[] result = new PartitionBusi[MAX_PARTITION_SIZE];

    File basFile = new File(basePath + File.separator + PARTITION_DIR_NAME);

    if (basFile.exists()) {
      File[] rsp = basFile.listFiles();

      for (File fins : rsp) {
        fins.delete();
      }

      System.out.println("删除结果:" + rsp);
    }
    basFile.mkdir();

    for (int i = 0; i < MAX_PARTITION_SIZE; i++) {
      result[i] = new PartitionBusi();
      result[i].setIndex(i);
      String path = basFile.getPath() + File.separator + i + SUFFIXE_NAME;
      result[i].setPath(path);

      // 生成输出文件
      try {
        result[i].setOutWrite(new FileWriter(path));
        result[i].setBufferedWriter(new BufferedWriter(result[i].getOutWrite()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return result;
  }

  /**
   * 进行数据的写入操作
   *
   * @param busi 分隔的数据信息
   * @param data 写入的数据信息
   */
  public void writeData(PartitionBusi busi, String data) {
    try {
      busi.getBufferedWriter().write(data);
      busi.getBufferedWriter().newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 将所有的写入文件流关闭
   *
   * @param partitionBusi 分区信息
   */
  public void closeOutput(PartitionBusi[] partitionBusi) {

    for (int i = 0; i < partitionBusi.length; i++) {
      IOUtils.close(partitionBusi[i].getBufferedWriter());
      IOUtils.close(partitionBusi[i].getFileReader());
    }
  }

  /**
   * 进行文件的读取操作
   *
   * @param file 输入文件信息
   */
  public void openReader(PartitionBusi file) {
    try {
      file.setFileReader(new FileReader(file.getPath()));
      file.setBufferReader(new BufferedReader(file.getFileReader()));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 进行文件的关闭操作
   *
   * @param file 文件信息
   */
  public void closeReader(PartitionBusi file) {
    IOUtils.close(file.getFileReader());
    IOUtils.close(file.getBufferReader());
  }
}
