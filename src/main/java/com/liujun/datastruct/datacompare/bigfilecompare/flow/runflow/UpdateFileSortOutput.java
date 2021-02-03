package com.liujun.datastruct.datacompare.bigfilecompare.flow.runflow;

import com.liujun.datastruct.datacompare.bigfilecompare.compare.DataCompareFileOutput;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.CompareKeyEnum;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.ContextContainer;
import com.liujun.datastruct.datacompare.bigfilecompare.flow.FlowInf;

/**
 * 对比中的文件做有序的输出操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class UpdateFileSortOutput implements FlowInf {

  public static final UpdateFileSortOutput INSTANCE = new UpdateFileSortOutput();

  @Override
  public boolean invokeFlow(ContextContainer context) {
    // 输出对象
    DataCompareFileOutput output =
        (DataCompareFileOutput) context.get(CompareKeyEnum.PROC_COMPARE_MANY_OUTPUT.getKey());

    //// 进行最后的文件输出
    // UpdateFileMerge merge =
    //    new UpdateFileMerge(
    //        output.getUpdBeforeDirPath(),
    //        output.getUpdAfterDirPath(),
    //        output.getUpdPath(),
    //        output.getUpdName());
    //// 相关的数据合并操作
    // merge.merge();
    //
    // if (CompareConfig.DELETE_TMP_FILE_FLAG) {
    //  // 删除用于修改的临时文件
    //  FileUtils.deleteDir(output.getUpdBeforeDirPath());
    //  FileUtils.deleteDir(output.getUpdAfterDirPath());
    // }

    return true;
  }
}
