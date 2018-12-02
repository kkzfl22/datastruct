package com.liujun.datastruct.heap.solution.margeSmailFile;

import com.liujun.datastruct.heap.solution.margeSmailFile.pojo.FileMargeBusi;
import org.junit.Test;

import java.io.File;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/30
 */
public class TestMargeFile {

  @Test
  public void runLoad() {
    String path = "D:/java/test/run";
    String outPath = path + "/marge.txt";

    File cleanOut = new File(outPath);

    if (cleanOut.exists()) {
      // 进行输出文件的删除操作
      cleanOut.delete();
    }

    FileMargeBusi[] margeList = MargeProc.INSTANCE.getMargeBean(path);

    MargeProc.INSTANCE.reader(margeList, outPath);

    System.out.println("有序文件合并完成");
  }
}
