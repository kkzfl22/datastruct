package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code599;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * 查找共同的爱好
 *
 * @author liujun
 * @version 0.0.1
 */
public class TestSolution {

  @Test
  public void findRestaurant() {
    this.compare(
        new String[] {"Shogun", "Tapioca Express", "Burger King", "KFC"},
        new String[] {"Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"},
        new String[] {"Shogun"});

    this.compare(
        new String[] {"Shogun", "Tapioca Express", "Burger King", "KFC"},
        new String[] {"KFC", "Shogun", "Burger King"},
        new String[] {"Shogun"});

    this.compare(
        new String[] {"Shogun", "Tapioca Express", "Burger King", "KFC"},
        new String[] {"KFC", "Burger King", "Tapioca Express", "Shogun"},
        new String[] {"KFC", "Burger King", "Tapioca Express", "Shogun"});
  }

  @Test
  public void findRestaurant2() {
    this.compare2(
        new String[] {"Shogun", "Tapioca Express", "Burger King", "KFC"},
        new String[] {"Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"},
        new String[] {"Shogun"});

    this.compare2(
        new String[] {"Shogun", "Tapioca Express", "Burger King", "KFC"},
        new String[] {"KFC", "Shogun", "Burger King"},
        new String[] {"Shogun"});

    this.compare2(
        new String[] {"Shogun", "Tapioca Express", "Burger King", "KFC"},
        new String[] {"KFC", "Burger King", "Tapioca Express", "Shogun"},
        new String[] {"KFC", "Burger King", "Tapioca Express", "Shogun"});
  }

  private void compare(String[] src, String[] target, String[] rsp) {

    Solution instance = new Solution();

    String[] runRsp = instance.findRestaurant(src, target);
    System.out.println(Arrays.toString(runRsp));
    Assert.assertThat(runRsp, Matchers.hasItemInArray(rsp[0]));
  }

  private void compare2(String[] src, String[] target, String[] rsp) {
    Solution2 instance2 = new Solution2();

    String[] runRsp2 = instance2.findRestaurant(src, target);
    System.out.println(Arrays.toString(runRsp2));
    Assert.assertThat(runRsp2, Matchers.hasItemInArray(rsp[0]));
  }
}
