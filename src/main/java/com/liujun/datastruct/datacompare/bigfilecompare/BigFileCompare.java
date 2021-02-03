package com.liujun.datastruct.datacompare.bigfilecompare;

import com.liujun.datastruct.datacompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.CompareKeyEnum;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.ContextContainer;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.FlowInf;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.runflow.BigDataMerge;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.runflow.DataCleanRepetitionSort;

import java.util.ArrayList;
import java.util.List;

/**
 * 大型文件比较
 *
 * @author liujun
 * @version 0.0.1
 */
public class BigFileCompare<V> {

  private static final List<FlowInf> DATA_FLOW = new ArrayList<>(8);

  static {
    // 流程:去重排序操作
    DATA_FLOW.add(DataCleanRepetitionSort.INSTANCE);
    //// 流程:填充布隆过滤器
    // DATA_FLOW.add(BloomFilterFull.INSTANCE);
    //// 保存布隆过滤器
    // DATA_FLOW.add(SaveBloomFilter.INSTANCE);
    //// 流程:执行数据的对比操作
    // DATA_FLOW.add(DataCompareOperator.INSTANCE);
    //// 流程:修改的数据合并输出
    // DATA_FLOW.add(UpdateFileSortOutput.INSTANCE);
    // 执行文件的合并操作
    DATA_FLOW.add(BigDataMerge.INSTANCE);
  }

  /**
   * 执行源文件的比较
   *
   * @param inputEntity 输入对比的相关信息
   * @param compareKey 对比相关的函数
   * @return 对比结果 true 对比成功 false 对比失败
   */
  public boolean fileCompare(
      BigFileCompareInputEntity inputEntity,
      BigCompareKeyInf<V> compareKey,
      DataParseInf<V> dataParse,
      Class dataEntity) {
    if (inputEntity == null || compareKey == null) {
      return false;
    }

    ContextContainer context = new ContextContainer();

    context.put(CompareKeyEnum.INPUT_BIGFILE_PATH.getKey(), inputEntity);
    context.put(CompareKeyEnum.INPUT_COMPARE_KEY.getKey(), compareKey);
    context.put(CompareKeyEnum.INPUT_COMPARE_PARSE.getKey(), dataParse);
    context.put(CompareKeyEnum.INPUT_COMPARE_ENTITY_CLASS.getKey(), dataEntity);

    for (FlowInf dataFlow : DATA_FLOW) {
      boolean dataRsp = dataFlow.invokeFlow(context);
      // 当流程返回false，则退出流程
      if (!dataRsp) {
        break;
      }
    }

    return true;
  }
}
