package com.liujun.datastruct.datacompare.bigfilecompare.flow.runflow;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataCompare;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.FileTaskCompare;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.SampleCount;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.ManyFileReader;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.CompareKeyEnum;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.ContextContainer;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.FlowInf;
import com.liujun.datastruct.utils.FileUtils;

/**
 * 进行布隆过滤器的数据填充操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class BloomFilterFull implements FlowInf {

  /** 布隆过滤器保存路径 */
  private static final String BLOOM_FILTER_PATH = "bloomfilter";

  /** 工厂对象 */
  public static final BloomFilterFull INSTANCE = new BloomFilterFull();

  @Override
  public boolean invokeFlow(ContextContainer context) {

    // 输入的实体信息
    BigFileCompareInputEntity inputEntity =
        (BigFileCompareInputEntity) context.get(CompareKeyEnum.INPUT_BIGFILE_PATH.getKey());
    // 对比的key的函数
    BigCompareKeyInf compareKey =
        (BigCompareKeyInf) context.get(CompareKeyEnum.INPUT_COMPARE_KEY.getKey());
    // 对比的转换函数
    DataParseInf dataParse =
        (DataParseInf) context.get(CompareKeyEnum.INPUT_COMPARE_PARSE.getKey());

    // 读取去重后的结果
    String srcPath = (String) context.get(CompareKeyEnum.PROC_REMOVE_DUPLICATE_OUTPUT_SRC.getKey());
    String targetPath =
        (String) context.get(CompareKeyEnum.PROC_REMOVE_DUPLICATE_OUTPUT_TARGET.getKey());

    // 1,获得对比的原始文件
    ManyFileReader readSrc = new ManyFileReader(srcPath);
    ManyFileReader readTarget = new ManyFileReader(targetPath);

    String bloomFilterPath = inputEntity.getCompareOutPath() + Symbol.PATH + BLOOM_FILTER_PATH;
    FileUtils.checkAndMakeDir(bloomFilterPath);

    // 执行数据对比的操作
    DataCompare compare =
        new DataCompare(
            compareKey,
            dataParse,
            // 统计抽样
            SampleCount.countSumNum(srcPath),
            // 统计抽样
            SampleCount.countSumNum(targetPath),
            bloomFilterPath);

    // 读取源文件，加载到布隆过滤器中
    FileTaskCompare.readerDataProc(readSrc, dataParse, compare::putSrcList);
    // 读取目标数据，加载到布隆过滤器中
    FileTaskCompare.readerDataProc(readTarget, dataParse, compare::putTargetList);

    // 对比处理器
    context.put(CompareKeyEnum.PROC_COMPARE_INSTANCE_OBJECT.getKey(), compare);
    // 原始文件读取器
    context.put(CompareKeyEnum.PROC_COMPARE_MANY_READER_SRC.getKey(), readSrc);
    // 目标文件读取器
    context.put(CompareKeyEnum.PROC_COMPARE_MANY_READER_TARGET.getKey(), readTarget);

    return true;
  }
}
