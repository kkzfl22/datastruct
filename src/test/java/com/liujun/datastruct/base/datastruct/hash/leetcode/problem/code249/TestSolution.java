package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code249;

import com.liujun.datastruct.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 测试字符串分级
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void groupString() {
    this.invokeTest(
        new String[] {"abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"},
        Arrays.asList(
            Arrays.asList("abc", "bcd", "xyz"),
            Arrays.asList("az", "ba"),
            Arrays.asList("acef"),
            Arrays.asList("a", "z")));

    this.invokeTest(
        new String[] {"af", "fk", "b", "d"},
        Arrays.asList(Arrays.asList("af", "fk"), Arrays.asList("b", "d")));

    this.invokeTest(
        new String[] {"aa", "bb", "b"},
        Arrays.asList(Arrays.asList("aa", "bb"), Arrays.asList("b")));

    this.invokeTest(
        new String[] {
          "fpbnsbrkbcyzdmmmoisaa",
          "cpjtwqcdwbldwwrryuclcngw",
          "a",
          "fnuqwejouqzrif",
          "js",
          "qcpr",
          "zghmdiaqmfelr",
          "iedda",
          "l",
          "dgwlvcyubde",
          "lpt",
          "qzq",
          "zkddvitlk",
          "xbogegswmad",
          "mkndeyrh",
          "llofdjckor",
          "lebzshcb",
          "firomjjlidqpsdeqyn",
          "dclpiqbypjpfafukqmjnjg",
          "lbpabjpcmkyivbtgdwhzlxa",
          "wmalmuanxvjtgmerohskwil",
          "yxgkdlwtkekavapflheieb",
          "oraxvssurmzybmnzhw",
          "ohecvkfe",
          "kknecibjnq",
          "wuxnoibr",
          "gkxpnpbfvjm",
          "lwpphufxw",
          "sbs",
          "txb",
          "ilbqahdzgij",
          "i",
          "zvuur",
          "yfglchzpledkq",
          "eqdf",
          "nw",
          "aiplrzejplumda",
          "d",
          "huoybvhibgqibbwwdzhqhslb",
          "rbnzendwnoklpyyyauemm"
        },
        Arrays.asList(
            Arrays.asList("a", "d", "i", "l"),
            Arrays.asList("eqdf", "qcpr"),
            Arrays.asList("lpt", "txb"),
            Arrays.asList("yfglchzpledkq", "zghmdiaqmfelr"),
            Arrays.asList("kknecibjnq", "llofdjckor"),
            Arrays.asList("cpjtwqcdwbldwwrryuclcngw", "huoybvhibgqibbwwdzhqhslb"),
            Arrays.asList("lbpabjpcmkyivbtgdwhzlxa", "wmalmuanxvjtgmerohskwil"),
            Arrays.asList("iedda", "zvuur"),
            Arrays.asList("js", "nw"),
            Arrays.asList("lebzshcb", "ohecvkfe"),
            Arrays.asList("dgwlvcyubde", "ilbqahdzgij"),
            Arrays.asList("lwpphufxw", "zkddvitlk"),
            Arrays.asList("qzq", "sbs"),
            Arrays.asList("dclpiqbypjpfafukqmjnjg", "yxgkdlwtkekavapflheieb"),
            Arrays.asList("mkndeyrh", "wuxnoibr"),
            Arrays.asList("firomjjlidqpsdeqyn", "oraxvssurmzybmnzhw"),
            Arrays.asList("gkxpnpbfvjm", "xbogegswmad"),
            Arrays.asList("fpbnsbrkbcyzdmmmoisaa", "rbnzendwnoklpyyyauemm"),
            Arrays.asList("aiplrzejplumda", "fnuqwejouqzrif")));

    this.invokeTest(
        FileUtils.readArray(FileUtils.getBasePath() + "/struct/leetcode/249/data1.txt"),
        FileUtils.readList(FileUtils.getBasePath() + "/struct/leetcode/249/target1.txt"));
  }

  /**
   * 执行测试
   *
   * @param input 输入
   * @param dataList 结果
   */
  private void invokeTest(String[] input, List<List<String>> dataList) {

    solution1(input,dataList);

    Solution2 instance2 = new Solution2();
    List<List<String>> outDataList = instance2.groupStrings(input);
    // 进行结果的对比操作
    Assert.assertEquals(outDataList.size(), dataList.size());
  }

  private void solution1(String[] input, List<List<String>> dataList) {
    Solution instance = new Solution();
    List<List<String>> groupList = instance.groupStrings(input);
    // 进行结果的对比操作
    Assert.assertEquals(groupList.size(), dataList.size());
  }
}
