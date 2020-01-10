package com.liujun.datastruct.base.sort.leetcode.code57.implement;

/**
 * 测试插入区间，先插入排序，再合并区间
 *
 * @author liujun
 * @version 0.0.1
 * @date 2020/01/10
 */
public class TestSolutionCode57Other extends TestAbsSolutionCode57 {
  @Override
  public SolutionCode57Interface getInstance() {
    return new SolutionCode57other();
  }
}
