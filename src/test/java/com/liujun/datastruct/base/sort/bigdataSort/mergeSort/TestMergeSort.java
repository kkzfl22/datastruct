package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.liujun.constant.TestPropertyEnum;
import com.liujun.utils.PropertiesUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/24
 */
public class TestMergeSort {

  /** 基础路径 */
  private static final String BASEPATH =
      PropertiesUtils.getInstance().getValue(TestPropertyEnum.TEST_BASE_PATH);

  private static final String TEST_IN_PATH = BASEPATH + "/test_in_sort";

  private static final String TEST_OUT_PATH = BASEPATH + "/test_out_sort";

  private static final String TEST_OUT_FILENAME = "/merge.sort";

  @Test
  public void testMergeSort() {

    // 生成测试数据
    FileOperate.Generate(TEST_IN_PATH, 4096, 4096, GenerateData::enerateLogData);

    MergeSort.INSTANCE.sort(TEST_IN_PATH, TEST_OUT_PATH, TEST_OUT_FILENAME);

    int testData = FileOperate.GetDataLineNum(TEST_OUT_PATH + TEST_OUT_FILENAME);

    Assert.assertEquals(4096, testData);
  }

  @After
  public void clean() {
    FileOperate.CycleDelete(TEST_IN_PATH);
    FileOperate.CycleDelete(TEST_OUT_PATH);
    FileOperate.CycleDelete(TEST_OUT_PATH + TEST_OUT_FILENAME);
  }
}
