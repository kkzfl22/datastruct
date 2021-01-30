package com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.flow.runflow;

import com.config.Symbol;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.flow.CompareKeyEnum;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.flow.ContextContainer;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.flow.FlowInf;
import com.liujun.datastruct.advanced.bloomfilter.datacompare.bigfilecompare.uniquerows.UniqueRowProcess;

/**
 * 进行数据的去重操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class DataCleanRepetition implements FlowInf {

  /** 工厂方法对象 */
  public static final DataCleanRepetition INSTANCE = new DataCleanRepetition();

  /** 原文件路径标识 */
  private static final String SRC_PATH_FLAG = "src";

  /** 目标路径标识 */
  private static final String TARGET_PATH_FLAG = "target";

  /** 数据去重处理 */
  private UniqueRowProcess rowProcess = new UniqueRowProcess();

  @Override
  public boolean invokeFlow(ContextContainer context) {
    // 输入的实体信息
    BigFileCompareInputEntity inputEntity =
        (BigFileCompareInputEntity) context.get(CompareKeyEnum.INPUT_BIGFILE_PATH.getKey());
    // 对比的转换函数
    DataParseInf dataParse =
        (DataParseInf) context.get(CompareKeyEnum.INPUT_COMPARE_PARSE.getKey());
    // 对比数据的行的实体信息
    Class dataEntity = (Class) context.get(CompareKeyEnum.INPUT_COMPARE_ENTITY_CLASS.getKey());

    // 1,针对原始数据去重操作
    String outSrcPath = inputEntity.getCompareOutPath() + Symbol.PATH + SRC_PATH_FLAG;
    String srcPath =
        rowProcess.uniqueRows(inputEntity.getSrcPath(), outSrcPath, dataParse, dataEntity);

    // 对目标数据执行去重操作
    String outTargetPath = inputEntity.getCompareOutPath() + Symbol.PATH + TARGET_PATH_FLAG;
    
    String targetPath =
        rowProcess.uniqueRows(inputEntity.getTargetPath(), outTargetPath, dataParse, dataEntity);

    // 原始数据去除重复的行
    context.put(CompareKeyEnum.PROC_REMOVE_DUPLICATE_OUTPUT_SRC.getKey(), srcPath);
    // 目标数据去除重复的行
    context.put(CompareKeyEnum.PROC_REMOVE_DUPLICATE_OUTPUT_TARGET.getKey(), targetPath);

    return true;
  }
}
