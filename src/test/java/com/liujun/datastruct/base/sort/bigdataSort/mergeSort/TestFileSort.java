package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.liujun.constant.TestPropertyEnum;
import com.liujun.utils.PropertiesUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 进行文件切分有序测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/24
 */
public class TestFileSort {

  /** 基础路径 */
  private static final String BASEPATH =
      PropertiesUtils.getInstance().getValue(TestPropertyEnum.TEST_BASE_PATH);

  private static final String TEST_SORT_PATH = "/test_sort";

  private static final String OUTPATH = BASEPATH + TEST_SORT_PATH;

  @Test
  public void testFileSoft() {

    List<String> fileList = FileOperate.Generate(OUTPATH, 1024, 1024, GenerateData::enerateLogData);

    // 对数据进行排序操作
    List<String> outSort = FileSort.INSTANCE.sort(fileList);

    Assert.assertEquals(fileList.size(), outSort.size());

    // 完成后进行文件的清理操作
    FileOperate.CycleDelete(OUTPATH);
  }
}
