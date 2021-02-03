package com.liujun.datastruct.datacompare.bigfilecompare.flow.runflow;

import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataCompare;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataCompareFileOutput;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.datacompare.bigfilecompare.constant.CompareConfig;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.datacompare.bigfilecompare.fileoperator.ManyFileReader;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.CompareKeyEnum;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.ContextContainer;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.FlowInf;
import com.liujun.datastruct.utils.FileUtils;
import com.liujun.datastruct.utils.IOUtils;

/**
 * 执行数据的对比操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class DataCompareOperator implements FlowInf {

  /** 实例对象 */
  public static final DataCompareOperator INSTANCE = new DataCompareOperator();

  @Override
  public boolean invokeFlow(ContextContainer context) {
    // 输入的实体信息
    BigFileCompareInputEntity inputEntity =
        (BigFileCompareInputEntity) context.get(CompareKeyEnum.INPUT_BIGFILE_PATH.getKey());
    // 对比的转换函数
    DataParseInf dataParse =
        (DataParseInf) context.get(CompareKeyEnum.INPUT_COMPARE_PARSE.getKey());
    // 1,获得对比的原始文件
    ManyFileReader readSrc =
        (ManyFileReader) context.get(CompareKeyEnum.PROC_COMPARE_MANY_READER_SRC.getKey());
    ManyFileReader readTarget =
        (ManyFileReader) context.get(CompareKeyEnum.PROC_COMPARE_MANY_READER_TARGET.getKey());

    // 执行数据对比的操作
    DataCompare compare =
        (DataCompare) context.get(CompareKeyEnum.PROC_COMPARE_INSTANCE_OBJECT.getKey());

    // 输出操作
    DataCompareFileOutput output = new DataCompareFileOutput(inputEntity.getCompareOutPath());
    try {
      // 操作前需打开输出文件
      output.openWriteFile();
      // 执行数据的对比操作
      putSrcDataCompare(readSrc, compare, dataParse, output);
      // 目标数据的对比操作
      putTargetDataCompare(readTarget, compare, dataParse, output);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // 执行相关的对比输出的关闭操作
      IOUtils.close(output);
    }

    context.put(CompareKeyEnum.PROC_COMPARE_MANY_OUTPUT.getKey(), output);

    // 对比完成，可以清理去重后的数据
    if (CompareConfig.DELETE_TMP_FILE_FLAG) {
      // 原始数据去除重复的行
      String srcPath =
          (String) context.get(CompareKeyEnum.PROC_REMOVE_DUPLICATE_OUTPUT_SRC.getKey());
      FileUtils.deleteParentDir(srcPath);

      // 目标数据去除重复的行
      String targetPath =
          (String) context.get(CompareKeyEnum.PROC_REMOVE_DUPLICATE_OUTPUT_TARGET.getKey());
      FileUtils.deleteParentDir(targetPath);
    }

    return true;
  }

  /**
   * 将原始数据放入到对比相关文件中
   *
   * @param readSrc 读取的原始文件中
   * @param compare 对比处理逻辑
   */
  protected void putSrcDataCompare(
      ManyFileReader readSrc,
      DataCompare compare,
      DataParseInf dataParse,
      DataCompareFileOutput compareOutput) {
    //// 执行数据的并行处理
    //FileTaskCallableCompare.readerDataProc(
    //    readSrc,
    //    dataParse,
    //    // 数据转换函数
    //    (data) -> {
    //      DataCompareRsp rsp = compare.compareSrcList(data);
    //      return rsp;
    //    },
    //    // 数据写入函数
    //    (rsp) -> {
    //      try {
    //        // 数据输出操作
    //        compareOutput.dataLineWrite(rsp);
    //      } catch (IOException e) {
    //        e.printStackTrace();
    //      }
    //    });
  }

  /**
   * 将目标数据装载到布隆过滤器中
   *
   * @param readTarget
   */
  protected void putTargetDataCompare(
      ManyFileReader readTarget,
      DataCompare compare,
      DataParseInf dataParse,
      DataCompareFileOutput compareOutput) {

    //// 执行数据的并行处理
    // FileTaskCallableCompare.readerDataProc(
    //    readTarget,
    //    dataParse,
    //    // 转换函数
    //    (data) -> {
    //      DataCompareRsp rsp = compare.compareTargetList(data);
    //      return rsp;
    //    },
    //    // 数据写入函数
    //    (rsp) -> {
    //      try {
    //        // 数据输出操作
    //        compareOutput.dataLineWrite(rsp);
    //      } catch (IOException e) {
    //        e.printStackTrace();
    //      }
    //    });
  }
}
