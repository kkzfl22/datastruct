package com.liujun.datastruct.base.sort.bigdataSort.mergeSort;

import com.config.Symbol;
import com.liujun.constant.TestPropertyEnum;
import com.liujun.utils.PropertiesUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 测试文件切分操作
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/07/24
 */
public class TestFileSpit {

  /** 基础路径 */
  private static final String BASEPATH =
      PropertiesUtils.getInstance().getValue(TestPropertyEnum.TEST_BASE_PATH);

  /** 文件切分路径 */
  private static final String TEST_IN_PATH = "/filespitsrc";

  private static final String READPATH = BASEPATH + Symbol.PATH + TEST_IN_PATH;

  /** 文件切分路径 */
  private static final String TEST_OUT_PATH = "/filespitout";

  private static final String OUTPATH = BASEPATH + Symbol.PATH + TEST_OUT_PATH;

  @Before
  public void generate() {
    FileOperate.DefaultGenerate16(READPATH);
  }

  /** 测试文件切分操作 */
  @Test
  public void testFileSpit() {
    // 1,单元测试生成一批文件
    List<String> list = FileSpit.INSTANCE.spitFile(READPATH, OUTPATH, 2048);
    System.out.println(list);
    Assert.assertNotEquals(0, list.size());
  }

  @After
  public void clean() {
    FileOperate.CycleDelete(READPATH);
    FileOperate.CycleDelete(OUTPATH);
  }
}
