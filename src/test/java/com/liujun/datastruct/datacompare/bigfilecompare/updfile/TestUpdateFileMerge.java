package com.liujun.datastruct.datacompare.bigfilecompare.updfile;

import org.junit.Test;

/**
 * 测试文件件合并操作
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestUpdateFileMerge {

  @Test
  public void merge() {
    String dataBeforePath = "D:\\run\\compare\\miffile\\output\\upd-data-before";
    String dataAfterPath = "D:\\run\\compare\\miffile\\output\\upd-data-after";
    String updatePath = "D:\\run\\compare\\miffile\\output\\update-data";
    String outFileName = "update-file";

    UpdateFileMerge merge =
        new UpdateFileMerge(dataBeforePath, dataAfterPath, updatePath, outFileName);

    merge.merge();
  }
}
