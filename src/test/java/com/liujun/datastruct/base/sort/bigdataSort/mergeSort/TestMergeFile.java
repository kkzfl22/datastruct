package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.liujun.constant.TestPropertyEnum;
import com.liujun.utils.PropertiesUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 测试文件合并操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/24
 */
public class TestMergeFile {

  /** 基础路径 */
  private static final String BASEPATH =
      PropertiesUtils.getInstance().getValue(TestPropertyEnum.TEST_BASE_PATH);

  private static final String TEST_IN_PATH = "/test_in_sort";

  private static final String INPATH = BASEPATH + TEST_IN_PATH;

  private static final String TEST_OUT_PATH = "/merge-file.out";

  private static final String OUTPATH = BASEPATH + TEST_OUT_PATH;

  @Test
  public void testFileMerge() {

    // 生成待排序的数据
    List<String> fileList = FileOperate.Generate(INPATH, 4096, 4096, GenerateData::enerateLogData);

    // 对数据进行排序操作
    List<String> outSort = FileSort.INSTANCE.sort(fileList);

    Assert.assertEquals(fileList.size(), outSort.size());

    MergeFile mergeFile = new MergeFile(outSort, OUTPATH);

    // 进行文件合并操作
    mergeFile.merge();

    int dataNum = FileOperate.GetDataLineNum(OUTPATH);
    Assert.assertEquals(4096, dataNum);

    // 完成后进行文件的清理操作
    FileOperate.CycleDelete(INPATH);
    FileOperate.CycleDelete(OUTPATH);
  }
}
