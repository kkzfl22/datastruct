package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code049;

import com.liujun.datastruct.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  private static final List<DataEntity> DATA_LIST = new ArrayList<>();

  public static final String[] BIGDATA =
      FileUtils.readLittleFile(FileUtils.getBasePath() + "/struct/leetcode/049/data.txt")
          .trim()
          .split(",");

  static {
    DATA_LIST.add(new DataEntity(new String[] {"eat", "tea", "tan", "ate", "nat", "bat"}, 3));
    DATA_LIST.add(new DataEntity(new String[] {"ac", "d"}, 2));
    DATA_LIST.add(
        new DataEntity(
            new String[] {
              "eat", "tea", "tan", "ate", "nat", "bat", "ac", "bd", "aac", "bbd", "aacc", "bbdd",
              "acc", "bdd"
            },
            11));

    DATA_LIST.add(new DataEntity(BIGDATA, 9869));
  }

  @Test
  public void groupAnagrams() {

    DataCountInf instance = new Solution();

    for (int i = 0; i < DATA_LIST.size(); i++) {
      check(DATA_LIST.get(i), instance);
    }
  }

  @Test
  public void groupAnagrams2() {

    DataCountInf instance = new Solution2();

    for (int i = 0; i < DATA_LIST.size(); i++) {
      check(DATA_LIST.get(i), instance);
    }
  }

  @Test
  public void groupAnagrams3() {

    DataCountInf instance = new Solution3();

    for (int i = 0; i < DATA_LIST.size(); i++) {
      check(DATA_LIST.get(i), instance);
    }
  }

  @Test
  public void groupAnagrams4() {
    DataCountInf instance = new Solution4();

    for (int i = 0; i < DATA_LIST.size(); i++) {
      check(DATA_LIST.get(i), instance);
    }
  }

  @Test
  public void groupAnagrams5() {
    DataCountInf instance = new Solution5();

    for (int i = 0; i < DATA_LIST.size(); i++) {
      check(DATA_LIST.get(i), instance);
    }
  }

  @Test
  public void groupAnagrams6() {
    DataCountInf instance = new Solution6();

    for (int i = 0; i < DATA_LIST.size(); i++) {
      check(DATA_LIST.get(i), instance);
    }
  }
  /**
   * 执行检查操作
   *
   * @param dataEntity 数据对象
   * @param instance 实例对象
   */
  private void check(DataEntity dataEntity, DataCountInf instance) {
    List<List<String>> dataList = instance.groupAnagrams(dataEntity.data);
    Assert.assertEquals(dataEntity.datNum, dataList.size());
    System.out.println(dataList);
  }

  private static class DataEntity {

    private String[] data;

    private int datNum;

    public DataEntity(String[] data, int datNum) {
      this.data = data;
      this.datNum = datNum;
    }
  }
}
