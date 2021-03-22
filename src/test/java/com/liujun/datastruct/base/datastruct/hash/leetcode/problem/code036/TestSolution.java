package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code036;

import com.google.common.reflect.TypeToken;
import com.liujun.datastruct.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 测试数独的检查
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void isValidSudoku() {

    this.assertCheck("data1.json", true);
    this.assertCheck("data2.json", false);
  }

  private void assertCheck(String fileName, boolean rsp) {
    Solution instance = new Solution();
    List<List<Character>> dataList =
        FileUtils.readJsonToObject(
            FileUtils.getBasePath() + "/struct/leetcode/036/" + fileName,
            new TypeToken<List<List<Character>>>() {}.getType());

    char[][] dataItem = new char[dataList.size()][];
    for (int i = 0; i < dataList.size(); i++) {
      dataItem[i] = new char[dataList.get(i).size()];
      for (int j = 0; j < dataList.get(i).size(); j++) {
        dataItem[i][j] = dataList.get(i).get(j);
      }
    }

    boolean dataRsp = instance.isValidSudoku(dataItem);
    Assert.assertEquals(rsp, dataRsp);
  }

  @Test
  public void isValidSudoku2() {
    this.assertCheck2("data1.json", true);
    this.assertCheck2("data2.json", false);
    this.assertCheck2("data3.json", false);
  }

  private void assertCheck2(String fileName, boolean rsp) {
    Solution2 instance = new Solution2();
    List<List<Character>> dataList =
        FileUtils.readJsonToObject(
            FileUtils.getBasePath() + "/struct/leetcode/036/" + fileName,
            new TypeToken<List<List<Character>>>() {}.getType());

    char[][] dataItem = new char[dataList.size()][];
    for (int i = 0; i < dataList.size(); i++) {
      dataItem[i] = new char[dataList.get(i).size()];
      for (int j = 0; j < dataList.get(i).size(); j++) {
        dataItem[i][j] = dataList.get(i).get(j);
      }
    }

    boolean dataRsp = instance.isValidSudoku(dataItem);
    Assert.assertEquals(rsp, dataRsp);
  }
}
