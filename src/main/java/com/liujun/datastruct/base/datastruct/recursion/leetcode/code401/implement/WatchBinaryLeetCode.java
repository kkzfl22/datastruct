package com.liujun.datastruct.base.datastruct.recursion.leetcode.code401.implement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 给定一个非负整数 n 代表当前 LED 亮着的数量，返回所有可能的时间。
 *
 * <p>案例:
 *
 * <p>输入: n = 1 返回: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
 *
 * <p>输出的顺序没有要求。
 *
 * <p>小时不会以零开头，比如 “01:00” 是不允许的，应为 “1:00”。
 *
 * <p>分钟必须由两位数组成，可能会以零开头，比如 “10:2” 是无效的，应为 “10:02”。
 *
 * <p>来源：力扣（LeetCode） 链接：https://leetcode-cn.com/problems/binary-watch
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * <p>此为leetcode上4ms的解
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/07
 */
public class WatchBinaryLeetCode {

  private int[] nums = new int[] {1, 2, 4, 8, 1, 2, 4, 8, 16, 32};
  private int[] bin_watch = new int[10];
  private List<String> watch = new ArrayList<>();

  public List<String> readBinaryWatch(int num) {
    backtrack(num, 0, 0);
    return watch;
  }

  private void backtrack(int num, int step, int start) {
    if (step == num) {
      watch.add(format_bin(bin_watch));
    } else {
      for (int i = start; i < nums.length; i++) {
        bin_watch[i] = 1;
        if (!isValid(bin_watch)) {
          bin_watch[i] = 0;
          continue;
        }
        backtrack(num, step + 1, i + 1);
        bin_watch[i] = 0;
      }
    }
  }

  private boolean isValid(int[] bin) {
    int h_sum = 0;
    int m_sum = 0;
    for (int i = 0; i < bin.length; i++) {
      if (bin[i] == 1) {
        if (i < 4) {
          h_sum += nums[i];
        } else {
          m_sum += nums[i];
        }
      }
    }
    return h_sum <= 11 && h_sum >= 0 && m_sum <= 59 && m_sum >= 0;
  }

  private String format_bin(int[] bin) {
    int h_sum = 0;
    int m_sum = 0;
    String ret = "";
    for (int i = 0; i < bin.length; i++) {
      if (bin[i] == 1) {
        if (i < 4) {
          h_sum += nums[i];
        } else {
          m_sum += nums[i];
        }
      }
    }
    if (m_sum < 10) {
      ret = ret + "0" + m_sum;
    } else {
      ret = ret + m_sum;
    }
    return h_sum + ":" + ret;
  }
}
