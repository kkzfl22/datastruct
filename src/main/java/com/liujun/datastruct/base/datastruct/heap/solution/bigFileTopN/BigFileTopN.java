package com.liujun.datastruct.base.datastruct.heap.solution.bigFileTopN;

import com.liujun.datastruct.base.datastruct.hash.consistenthash.old.HashCode;
import com.liujun.datastruct.base.datastruct.heap.solution.bigFileTopN.pojo.KeyBusi;
import com.liujun.datastruct.base.datastruct.heap.solution.bigFileTopN.pojo.PartitionBusi;
import com.liujun.datastruct.utils.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对超大文件进行的TopN的关键词统计
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/02
 */
public class BigFileTopN {

  /** 分隔符 */
  private static final String SPIT_FLAG = " ";

  /**
   * 求关键词的TopN问题
   *
   * @param file 文件路径径
   * @param n 求排名前N的关键字
   * @return 前N的关键字
   */
  public KeyBusi[] topN(String file, int n) {
    // 提取目录信息
    File cufile = new File(file);

    // 1,将当前一个文件切分到多个文件中,按关键字的hash进行分片操作,hash算法使用FNV1_32_HASH计算,分拆到多个文件中
    PartitionBusi[] partis = PartitionFile.INSTANCE.getPartition(cufile.getParent());
    this.fileToPartition(file, partis);
    // 关闭所有分片文件,以写入磁盘
    PartitionFile.INSTANCE.closeOutput(partis);

    // 2,针对每个文件进行求TokN
    List<KeyBusi[]> topkList = this.countTopN(partis, n);

    // 3, 针对求得的TokN再求中总的TopN即为，最后结果的TokN
    KeyBusi[] topBusis = CountTopN.INSTANCE.getTopN(topkList, n);

    return topBusis;
  }

  /**
   * 进行单文件和topn求解
   *
   * @param partis
   * @param n
   * @return
   */
  private List<KeyBusi[]> countTopN(PartitionBusi[] partis, int n) {
    List<KeyBusi[]> list = new ArrayList<>();

    for (int i = 0; i < partis.length; i++) {
      PartitionFile.INSTANCE.openReader(partis[i]);
      // 进行数据读取
      try {
        String line = null;
        while ((line = partis[i].getBufferReader().readLine()) != null) {
          CountTopN.INSTANCE.putData(line);
        }
        // 完成一个文件进行一次topN的求解
        list.add(CountTopN.INSTANCE.getTopN(n));

        CountTopN.INSTANCE.dataClear();

      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        // 进行文件的关闭操作
        PartitionFile.INSTANCE.closeReader(partis[i]);
      }
    }

    return list;
  }

  /**
   * 读取源文件将数据写入分区文件中
   *
   * @param file 源文件信息
   * @param partitions 分区文件信息
   */
  private void fileToPartition(String file, PartitionBusi[] partitions) {

    FileReader fileReader = null;
    BufferedReader bufferedReader = null;

    String line = null;

    try {
      fileReader = new FileReader(file);
      bufferedReader = new BufferedReader(fileReader);

      while ((line = bufferedReader.readLine()) != null) {
        // 将行数据输出到分片文件信息中
        this.lineToPartition(line, partitions);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.close(bufferedReader);
      IOUtils.close(fileReader);
    }
  }

  /**
   * 将文件内容输出到分区文件中
   *
   * @param line
   * @param partitions
   */
  private void lineToPartition(String line, PartitionBusi[] partitions) {
    // 分隔字符串
    int index = 0;
    int findIndex;
    line = line.trim();

    while (index < line.length()) {
      // 切分字符串
      if ((findIndex = line.indexOf(SPIT_FLAG, index)) != -1) {
        String key = line.substring(index, findIndex);

        // 按关键证书进行分片操作
        this.keyToPartition(key, partitions);

        index = findIndex + 1;
      } else {
        String key = line.substring(index);

        // 按关键证书进行分片操作
        this.keyToPartition(key, partitions);

        index += line.length();
      }
    }
  }

  /**
   * 将内容输出到分片文件信息中
   *
   * @param key 关键字key
   * @param partitions 分片文件信息
   */
  private void keyToPartition(String key, PartitionBusi[] partitions) {
    int partLength = partitions.length;
    // 计算hash值
    int hashCode = HashCode.getHash(key);
    // 计算得到分片
    int modeVal = hashCode % partLength;
    // 将数据输出到分处文件中
    PartitionFile.INSTANCE.writeData(partitions[modeVal], key);
  }
}
