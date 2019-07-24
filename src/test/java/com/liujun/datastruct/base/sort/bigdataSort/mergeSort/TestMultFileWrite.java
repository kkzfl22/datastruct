package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.config.Symbol;
import com.liujun.constant.TestPropertyEnum;
import com.liujun.utils.PropertiesUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 进行多文件边续写操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/23
 */
public class TestMultFileWrite {

  private static final String BASEPATH =
      PropertiesUtils.getInstance().getValue(TestPropertyEnum.TEST_BASE_PATH);

  @Test
  public void testSeqWrite() {

    List<String> list = FileOperate.DefaultGenerate16(BASEPATH);

    Assert.assertNotEquals(0, list.size());
  }

  @After
  public void clean() {
    FileOperate.CycleDelete(BASEPATH);
  }
}
