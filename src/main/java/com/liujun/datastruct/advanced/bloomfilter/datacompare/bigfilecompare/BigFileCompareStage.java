package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare;

import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.DataCompare;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.DataCompareFileOutput;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.SampleCount;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.fileoperator.ManyFileReader;
import com.liujun.datastruct.utils.IOUtils;

/**
 * 大型文件比较，分阶段进行
 *
 * @author liujun
 * @version 0.0.1
 */
public class BigFileCompareStage<V> extends BigFileCompare<V> {

  /** 抽样数 */
  private static final int SAMPLE_NUM = 10;

  /** 等待的数量 */
  private static final int QUEUE_WAIT_NUM = 16;

  /** 增量输出一次的处理点 */
  private static final long INCREMENT_PRINT = 1000000;

  /** 数据条数 */
  private long dataNum;

  /** 打输输出的阈值，默认100万输出一次 */
  private long threshold = INCREMENT_PRINT;

  /** 开始运行的时间 */
  private long startTime = System.currentTimeMillis();

  /** 区间计时 */
  private long scopeTime = System.currentTimeMillis();

  /**
   * 执行源文件的比较
   *
   * @param inputEntity 输入对比的相关信息
   * @param compareKey 对比相关的函数
   * @return 对比结果 true 对比成功 false 对比失败
   */
  public boolean fileCompareStateFullBloom(
      BigFileCompareInputEntity inputEntity,
      BigCompareKeyInf compareKey,
      DataParseInf<V> dataParse,
      String path) {
    if (inputEntity == null || compareKey == null) {
      return false;
    }

    // 1,获得对比的原始文件
    ManyFileReader readSrc = new ManyFileReader(inputEntity.getSrcPath());
    ManyFileReader readTarget = new ManyFileReader(inputEntity.getTargetPath());

    // 执行数据对比的操作
    DataCompare compare =
        new DataCompare(
            compareKey,
            dataParse,
            SampleCount.countSumNum(inputEntity.getSrcPath()),
            SampleCount.countSumNum(inputEntity.getTargetPath()),
            path);

    // 读取源文件，加载到对比
    putSrcData(readSrc, compare, dataParse);
    // 读取目标数据，加载对比
    putTargetData(readTarget, compare, dataParse);

    // 数据保存操作
    compare.save();

    return true;
  }

  /**
   * 文件比较操作
   *
   * @param inputEntity
   * @param compareKey
   * @param dataParse
   * @param path
   */
  public void fileCompare(
      BigFileCompareInputEntity inputEntity,
      BigCompareKeyInf compareKey,
      DataParseInf<V> dataParse,
      String path) {

    // 1,获得对比的原始文件
    ManyFileReader readSrc = new ManyFileReader(inputEntity.getSrcPath());
    ManyFileReader readTarget = new ManyFileReader(inputEntity.getTargetPath());

    // 执行数据对比的操作
    DataCompare compare = new DataCompare(compareKey, dataParse, path);

    // 输出操作
    DataCompareFileOutput output = new DataCompareFileOutput(inputEntity.getCompareOutPath());
    try {
      // 操作前需打开输出文件
      output.openWriteFile();
      // 执行数据的对比操作
      putSrcDataCompare(readSrc, compare, dataParse, output);
      putTargetDataCompare(readTarget, compare, dataParse, output);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // 执行相关的对比输出的关闭操作
      IOUtils.close(output);
    }

    // 修改文件的对比并有序输出
    this.updateDataOutput(output, dataParse);
  }
}
