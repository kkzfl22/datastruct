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
 * @author liujun
 * @version 0.0.1
 * @date 2019/10/07
 */
public class WatchBinaryCodeMyself {

  /** 小时表示 */
  private static final int[] HOUR = {1, 2, 4, 8};

  /** 表示分钟 */
  private static final int[] MINUTE = {1, 2, 4, 8, 16, 32};

  /** 输出符号 */
  private static final String OUT_FLAG = ":";

  /** 需要补0的最大数 */
  private static final int MAX_BIN_NUM = 10;

  private static final String ZERO = "0";

  /**
   * todo二过制手表
   *
   * @param num
   * @return
   */
  public List<String> readBinaryWatch(int num) {

    List<String> outList = new ArrayList<>();

    if (num <= 0) {
      outList.add("0:00");
      return outList;
    }

    List<Integer> hourList = new ArrayList<>();
    List<Integer> minuteList = new ArrayList<>();
    Set<String> outSet = new HashSet<>();

    this.recurstion(num, 0, 0, hourList, minuteList, outSet);

    outList.addAll(outSet);

    return outList;
  }

  private void recurstion(
      int maxNum,
      int hourindex,
      int minuteIndex,
      List<Integer> hour,
      List<Integer> minute,
      Set<String> list) {

    // 进行减枝
    if (hour.size() >= 2) {
      int sum = this.sumValue(hour);

      if (sum > 11) {
        return;
      }
    }

    // 进行减枝
    if (minute.size() >= 3) {
      int sum = this.sumValue(minute);
      if (sum > 59) {
        return;
      }
    }

    if (maxNum == hourindex + minuteIndex) {
      String outtime = this.outtime(hour, minute);

      if (null != outtime) {
        // System.out.println("输出时间: hour:" + hour + ",minute:" + minute + ":" + outtime);
        list.add(outtime);
      }
      return;
    }

    // 进行小时的枚举
    for (int i = hourindex; i < HOUR.length; i++) {
      if (!hour.contains(HOUR[i])) {
        hour.add(HOUR[i]);
        this.recurstion(maxNum, hourindex + 1, minuteIndex, hour, minute, list);
        hour.remove(hour.size() - 1);
      }
    }

    // 进行分钟的枚举操作
    for (int i = minuteIndex; i < MINUTE.length; i++) {
      if (!minute.contains(MINUTE[i])) {
        minute.add(MINUTE[i]);
        this.recurstion(maxNum, hourindex, minuteIndex + 1, hour, minute, list);
        minute.remove(minute.size() - 1);
      }
    }
  }

  private String outtime(List<Integer> hour, List<Integer> minute) {
    StringBuilder outtime = new StringBuilder();

    int outHour = 0;

    // 累加小时
    for (int i = 0; i < hour.size(); i++) {
      outHour += hour.get(i);
    }

    // 累加分钟
    int minuteOut = 0;
    for (int i = 0; i < minute.size(); i++) {
      minuteOut += minute.get(i);
    }

    if (minuteOut == 0 && outHour == 0) {
      return null;
    }

    outtime.append(outHour);
    outtime.append(OUT_FLAG);

    if (minuteOut < MAX_BIN_NUM) {
      outtime.append(ZERO);
    }
    outtime.append(minuteOut);

    return outtime.toString();
  }

  private int sumValue(List<Integer> list) {
    int sum = 0;
    for (Integer value : list) {
      sum += value;
    }
    return sum;
  }
}
