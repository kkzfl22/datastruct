package com.liujun.datastruct.base.sort.bigdataSort;

import com.liujun.datastruct.base.sort.bigdataSort.logTimeMerge.BucketFileInfo;
import com.liujun.datastruct.base.sort.bigdataSort.logTimeMerge.FileMergeScope;

import java.util.List;

/**
 * 进行桶排序操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/29
 */
public class BucketSort {

  public static void main(String[] args) {

    long startCurrTime = System.currentTimeMillis();

    String filePath = "D:\\java\\test\\datastruct\\sort\\bigdata\\";

    long startTime = System.currentTimeMillis();

    // 1，读取数据范围
    FileMergeScope scope = FileReaderProc.INSTANCE.getFileMerge(filePath);

    String basePath = filePath + "bucket/";

    // 2,进行分桶操作
    List<BucketFileInfo> bucketList = BucketOper.INSTANCE.bucketSpit(scope, 100, basePath);

    for (int i = 0; i < bucketList.size(); i++) {
      System.out.println(bucketList.get(i));
    }

    BucketOper.INSTANCE.openBucket(bucketList);

    // 3,将数据写入分桶中
    FileReaderProc.INSTANCE.fileReader(filePath);

    // 将文件关闭
    BucketOper.INSTANCE.close();

    // 针对文件进行排序操作
    BucketOper.INSTANCE.bucketSort();

    // 进行文件的最终合并操作
    BucketOper.INSTANCE.margeAllFile(basePath + "all_bucket");

    long endTime = System.currentTimeMillis();
    System.out.println("用时:" + (endTime - startCurrTime));
  }
}
