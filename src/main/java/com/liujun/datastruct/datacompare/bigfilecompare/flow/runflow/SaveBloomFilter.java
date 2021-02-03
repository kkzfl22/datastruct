package com.liujun.datastruct.datacompare.bigfilecompare.flow.runflow;

import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataCompare;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.CompareKeyEnum;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.ContextContainer;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.FlowInf;

/**
 * 对比中的文件做有序的输出操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class SaveBloomFilter implements FlowInf {

  public static final SaveBloomFilter INSTANCE = new SaveBloomFilter();

  @Override
  public boolean invokeFlow(ContextContainer context) {
    // 执行数据对比的操作
    DataCompare compare =
        (DataCompare) context.get(CompareKeyEnum.PROC_COMPARE_INSTANCE_OBJECT.getKey());

    // 执行布隆过滤器的保存操作
    compare.save();

    return true;
  }
}
