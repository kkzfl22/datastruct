package com.liujun.datastruct.heap.solution.margeSmailFile;

import com.liujun.datastruct.heap.solution.margeSmailFile.pojo.FileMargeBusi;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/30
 */
public class TestMargeFile {

  @Test
  public void runLoad() {
    String path = "D:\\java\\test\\run";
    FileMargeBusi[] margeList = MargeProc.INSTANCE.getMargeBean(path);
    MargeProc.INSTANCE.reader(margeList);
  }
}
