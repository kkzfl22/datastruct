package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code004;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void isIsomorphic() {
    Solution instnace = new Solution();

    Assert.assertEquals(instnace.isIsomorphic("egg", "add"), true);
    Assert.assertEquals(instnace.isIsomorphic("foo", "bar"), false);
    Assert.assertEquals(instnace.isIsomorphic("paper", "title"), true);
    Assert.assertEquals(instnace.isIsomorphic("badc", "baba"), false);
    Assert.assertEquals(instnace.isIsomorphic("bbbaaaba", "aaabbbba"), false);
  }

  @Test
  public void isIsomorphic2() {
    Solution2 instnace = new Solution2();

    Assert.assertEquals(instnace.isIsomorphic("egg", "add"), true);
    Assert.assertEquals(instnace.isIsomorphic("foo", "bar"), false);
    Assert.assertEquals(instnace.isIsomorphic("paper", "title"), true);
    Assert.assertEquals(instnace.isIsomorphic("badc", "baba"), false);
    Assert.assertEquals(instnace.isIsomorphic("bbbaaaba", "aaabbbba"), false);
  }

  @Test
  public void isIsomorphic3() {
    Solution3 instnace = new Solution3();

    Assert.assertEquals(instnace.isIsomorphic("egg", "add"), true);
    Assert.assertEquals(instnace.isIsomorphic("foo", "bar"), false);
    Assert.assertEquals(instnace.isIsomorphic("paper", "title"), true);
    Assert.assertEquals(instnace.isIsomorphic("badc", "baba"), false);
    Assert.assertEquals(instnace.isIsomorphic("bbbaaaba", "aaabbbba"), false);
  }

}
