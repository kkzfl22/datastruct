package com.liujun.datastruct.datacompare.bigfilecompare.flow.runflow;

import com.config.Symbol;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.BigCompareKeyInf;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataCompareFileOutput;
import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataParseInf;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.BigFileCompareInputEntity;
import com.liujun.datastruct.datacompare.bigfilecompare.entity.DataCompareOutRsp;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.CompareKeyEnum;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.ContextContainer;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.FlowInf;
import com.liujun.datastruct.datacompare.bigfilecompare.merge.MergeFileOperatorPriorityQueue;

import java.io.IOException;

/**
 * 超大型数据合并操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class BigDataMerge<V> implements FlowInf {

  private static final int DEFAULT_BATCH_SIZE = 512;

  /** 实例对象 */
  public static final BigDataMerge INSTANCE = new BigDataMerge();

  @Override
  public boolean invokeFlow(ContextContainer context) {

    // 原始数据去除重复的行
    String srcPath = (String) context.get(CompareKeyEnum.PROC_REMOVE_DUPLICATE_OUTPUT_SRC.getKey());
    // 目标数据去除重复的行
    String targetPath =
        (String) context.get(CompareKeyEnum.PROC_REMOVE_DUPLICATE_OUTPUT_TARGET.getKey());
    // 对比的转换函数
    DataParseInf<V> dataParse =
        (DataParseInf<V>) context.get(CompareKeyEnum.INPUT_COMPARE_PARSE.getKey());
    // 对比数据的行的实体信息
    Class dataEntity = (Class) context.get(CompareKeyEnum.INPUT_COMPARE_ENTITY_CLASS.getKey());

    // 输入的实体信息
    BigFileCompareInputEntity inputEntity =
        (BigFileCompareInputEntity) context.get(CompareKeyEnum.INPUT_BIGFILE_PATH.getKey());

    // 对比的key的函数
    BigCompareKeyInf compareKey =
        (BigCompareKeyInf) context.get(CompareKeyEnum.INPUT_COMPARE_KEY.getKey());

    try ( // 原始文件读取器
    MergeFileOperatorPriorityQueue<V> mergeSrcFileOperator =
            new MergeFileOperatorPriorityQueue<>(dataEntity, srcPath, dataParse, compareKey);
        // 目标数据读取器
        MergeFileOperatorPriorityQueue<V> mergeTargetFileOperator =
            new MergeFileOperatorPriorityQueue<>(dataEntity, targetPath, dataParse, compareKey);
        // 输出操作
        DataCompareFileOutput output =
            new DataCompareFileOutput(inputEntity.getCompareOutPath()); ) {
      // 打开文件读取器
      mergeSrcFileOperator.open();
      mergeTargetFileOperator.open();
      // 首次数据加载
      mergeSrcFileOperator.firstLoader();
      mergeTargetFileOperator.firstLoader();

      // 打开文件写入器
      output.openWriteFile();

      // 得到原始中最小的
      V srcMinData = mergeSrcFileOperator.readerMin();
      // 得到目标中最小的
      V targetMinData = mergeTargetFileOperator.readerMin();

      DataCompareOutRsp dataRsp = new DataCompareOutRsp(DEFAULT_BATCH_SIZE);

      int index = 0;
      int threshold = DEFAULT_BATCH_SIZE;

      while (true) {
        // 当两个都为空时，做出退出操作
        if (srcMinData == null && targetMinData == null) {
          break;
        }
        // 这是两个都不为空的情况
        else if (null != srcMinData && targetMinData != null) {
          String srcKey = compareKey.getKey(srcMinData);
          String targetKey = compareKey.getKey(targetMinData);

          // 如果数据key相同，说明可能是修改，则进一步检查数据值是否相同，
          if (srcKey.equals(targetKey)) {
            // 检查数据是否完全一致
            String srcFullDataKey = compareKey.getKeyMany(srcMinData);
            String targetFullDataKey = compareKey.getKeyMany(targetMinData);

            // 当数据不一至，则写入至文件中
            if (!srcFullDataKey.equals(targetFullDataKey)) {
              String dataSrcFull1 = dataParse.toFileLine(srcMinData);
              String dataTargetFull = dataParse.toFileLine(targetMinData);
              dataRsp
                  .getUpdateList()
                  .add(
                      dataSrcFull1
                          + Symbol.MINUS
                          + Symbol.MINUS
                          + Symbol.RIGHT_FLAG
                          + dataTargetFull);
            }

            // 因为key相同，则读取下一行
            srcMinData = mergeSrcFileOperator.readerMin();
            targetMinData = mergeTargetFileOperator.readerMin();
          }
          // 如果不同，则进说明可能在添加或者是删除
          else {
            // 数据比较后的结果
            int compareRsp = ((Comparable) srcMinData).compareTo(targetMinData);
            // 说明原始数据，小于目标数据,则为删除操作,
            // 解释下为什么 是删除操作，删除原始数据小于目标数据，这说明在原始数据中存在，在目标数据是不存在，这就是删除数据
            // 完成添加后，将原始数据写入至删除中，原始读取下一个
            if (compareRsp < 0) {
              dataRsp.getDeleteList().add(dataParse.toFileLine(srcMinData));
              srcMinData = mergeSrcFileOperator.readerMin();
            }
            // 如果原始数据大于目标数据，说明为添加操作，
            // 这明也解释下为什么是添加操作，原始数据大于目标数据，说明在原始中不存，在目标数据中存在，这就是添加数据
            // 写入完成后，需将目标数据写入至添加中，读取下一个
            else if (compareRsp > 0) {
              dataRsp.getAddList().add(dataParse.toFileLine(targetMinData));
              targetMinData = mergeTargetFileOperator.readerMin();
            }
          }
        }
        // 原始为空，目标不为空，则进行添加操作
        else if (srcMinData == null && targetMinData != null) {
          // output.writeAddData(dataParse.toFileLine(targetMinData));
          dataRsp.getAddList().add(dataParse.toFileLine(targetMinData));
          targetMinData = mergeTargetFileOperator.readerMin();
        }
        // 原始不为空，目标为空，则为删除操作
        else if (srcMinData != null && targetMinData == null) {
          // output.writeDeleteData(dataParse.toFileLine(srcMinData));
          dataRsp.getDeleteList().add(dataParse.toFileLine(srcMinData));
          srcMinData = mergeSrcFileOperator.readerMin();
        }

        if (index >= threshold - 1) {
          // 执行数据的写入操作
          output.writeData(dataRsp);
          dataRsp.clean();
          index = 0;
        }
        index++;
      }

      // 完成后执行数据的写入操作
      output.writeData(dataRsp);
      dataRsp.clean();
      index = 0;

    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }
}
