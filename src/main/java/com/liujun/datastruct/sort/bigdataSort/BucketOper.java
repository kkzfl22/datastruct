package com.liujun.datastruct.sort.bigdataSort;

import com.liujun.datastruct.sort.bigdataSort.logTimeMerge.FileMergeScope;
import com.liujun.datastruct.sort.bigdataSort.logTimeMerge.LogInfoBean;
import com.liujun.datastruct.utils.IOUtils;
import com.liujun.datastruct.sort.bigdataSort.logTimeMerge.BucketFileInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 桶操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/29
 */
public class BucketOper {

  public static final BucketOper INSTANCE = new BucketOper();

  private static final String BUCKET_PREFIX = "bucket_";

  private static final Random RAND = new Random();

  private static List<BucketFileInfo> CACHE;

  private byte[] BUFFERCACHE = new byte[1024 * 16];

  /**
   * 计算得到分桶信息
   *
   * @param scope 范围
   * @param spitNum 分桶数
   */
  public List<BucketFileInfo> bucketSpit(FileMergeScope scope, int spitNum, String basePath) {

    long start = scope.getStartTime();
    long end = scope.getEndTime();

    long value = (end - start) / spitNum;

    List<BucketFileInfo> list = new ArrayList<>(spitNum);

    BucketFileInfo marge;

    long currValue = start;

    for (int i = 0; i < spitNum; i++) {
      marge = new BucketFileInfo();
      if (i == 0) {
        marge.setStartTime(currValue);
      } else {
        currValue = currValue + 1;
        marge.setStartTime(currValue);
      }

      if (i == spitNum - 1) {
        currValue = scope.getEndTime();
      } else {
        currValue = currValue + value;
      }

      marge.setEndTime(currValue);
      marge.setIndex(i);
      marge.setFileOutInfo(outFileName(basePath, i));
      marge.setDiffTime(marge.getEndTime() - marge.getStartTime());
      list.add(marge);
    }

    return list;
  }

  /**
   * 获取文件
   *
   * @return
   */
  private String outFileName(String basePath, int index) {
    return basePath
        + BUCKET_PREFIX
        + System.nanoTime()
        + RAND.nextInt(10000)
        + "_"
        + index
        + ".bucket";
  }

  public void openBucket(List<BucketFileInfo> bucketList) {
    try {
      for (int i = 0; i < bucketList.size(); i++) {
        bucketList.get(i).setWrite(new FileWriter(bucketList.get(i).getFileOutInfo()));
        bucketList.get(i).setBufferWrite(new BufferedWriter(bucketList.get(i).getWrite()));

        CACHE = bucketList;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void close() {
    for (int i = 0; i < CACHE.size(); i++) {
      IOUtils.closeStream(CACHE.get(i).getBufferWrite());
      IOUtils.closeStream(CACHE.get(i).getWrite());
    }
  }

  /**
   * 桶数据写入
   *
   * @param time 时间搓
   * @param line 行记录
   * @throws IOException 异常
   */
  public void bucketWrite(long time, String line) throws IOException {
    for (int i = 0; i < CACHE.size(); i++) {
      if (CACHE.get(i).getStartTime() <= time && time <= CACHE.get(i).getEndTime()) {
        CACHE.get(i).getBufferWrite().write(line);
        CACHE.get(i).getBufferWrite().newLine();
        return;
      }
    }
  }

  /**
   * 进行桶文件读取操作
   *
   * @param path 文件路径
   * @return 读取完成的数据
   */
  public List<LogInfoBean> dataReader(String path) {

    List<LogInfoBean> list = new ArrayList<>(1000000);

    FileReader read = null;
    BufferedReader buffered = null;
    try {
      read = new FileReader(path);
      buffered = new BufferedReader(read);

      String line = null;

      LogInfoBean loginBean = null;

      while ((line = buffered.readLine()) != null) {

        loginBean = new LogInfoBean();

        loginBean.setTimeCurr(Long.parseLong(line.substring(0, line.indexOf(" "))));
        loginBean.setLoginfo(line);
        list.add(loginBean);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeStream(buffered);
      IOUtils.closeStream(read);
    }

    return list;
  }

  /** 进行桶排序操作 */
  public void bucketSort() {
    for (int i = 0; i < CACHE.size(); i++) {
      String fileInput = CACHE.get(i).getFileOutInfo();

      List<LogInfoBean> readList = dataReader(fileInput);
      Collections.sort(readList);
      this.writeData(readList, fileInput);
    }
  }

  /**
   * 合并最终的文件
   *
   * @param outFile 输出的文件信息
   */
  public void margeAllFile(String outFile) {
    for (int i = 0; i < CACHE.size(); i++) {
      String fileInput = CACHE.get(i).getFileOutInfo();
      this.fileMarge(fileInput, outFile);
    }
  }

  /**
   * 将单文件合并的方法
   *
   * @param input 输入文件
   * @param output 输出文件
   */
  public void fileMarge(String input, String output) {
    FileInputStream inputStream = null;

    FileOutputStream outputStream = null;
    try {
      inputStream = new FileInputStream(input);
      outputStream = new FileOutputStream(output, true);

      int index = -1;
      while ((index = inputStream.read(BUFFERCACHE)) != -1) {

        outputStream.write(BUFFERCACHE, 0, index);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeStream(outputStream);
      IOUtils.closeStream(inputStream);
    }
  }

  /**
   * 将排序完的数据写回桶中
   *
   * @param readList 集合数据
   * @param fileOut 桶文件路径
   */
  public void writeData(List<LogInfoBean> readList, String fileOut) {
    FileWriter outWrite = null;
    BufferedWriter bufferedWriter = null;

    try {
      outWrite = new FileWriter(fileOut);
      bufferedWriter = new BufferedWriter(outWrite);

      for (LogInfoBean log : readList) {
        bufferedWriter.write(log.getLoginfo());
        bufferedWriter.newLine();
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeStream(bufferedWriter);
      IOUtils.closeStream(outWrite);
    }
  }

  public static void main(String[] args) {
    FileMergeScope marer = new FileMergeScope();
    marer.setStartTime(1540800000000L);
    marer.setEndTime(1540803538000L);

    BucketOper oper = new BucketOper();
    List<BucketFileInfo> list = oper.bucketSpit(marer, 10, "");

    for (BucketFileInfo mrs : list) {
      System.out.println(mrs);
    }

    // System.out.println(list);
  }
}
