package com.liujun.datastruct.base.datastruct.hash.leetcode.problem.code036;

import java.util.HashMap;
import java.util.Map;

/**
 * 有效的数独
 *
 * <p>使用一个map以值为key，索引为值
 *
 * @author liujun
 * @version 0.0.1
 */
public class Solution {

  public boolean isValidSudoku(char[][] board) {
    if (board.length < 1) {
      return false;
    }

    // 1. 数字 `1-9` 在每一行只能出现一次。
    // 2. 数字 `1-9` 在每一列只能出现一次。
    // 3. 数字 `1-9` 在每一个以粗实线分隔的 `3x3` 宫内只能出现一次。
    Map<Integer, Integer>[] rows = new HashMap[9];
    Map<Integer, Integer>[] columns = new HashMap[9];
    Map<Integer, Integer>[] boxes = new HashMap[9];

    // 初始化二维map
    for (int i = 0; i < 9; i++) {
      rows[i] = new HashMap<>();
      columns[i] = new HashMap<>();
      boxes[i] = new HashMap<>();
    }

    // 进行9*9的数独遍历
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        // 如果当前未填写，则跳过
        if (board[i][j] == '.') {
          continue;
        }

        int item = board[i][j];
        // 小的3*3的数独检查
        int boxIndex = (i / 3) * 3 + j / 3;

        // 将行进行存储操作
        rows[i].put(item, rows[i].getOrDefault(item, 0) + 1);
        // 进行列的存储操作
        columns[j].put(item, columns[j].getOrDefault(item, 0) + 1);
        // 小的数独
        boxes[boxIndex].put(item, boxes[boxIndex].getOrDefault(item, 0) + 1);

        // 进行行列的检查
        if (rows[i].get(item) > 1 || columns[j].get(item) > 1 || boxes[boxIndex].get(item) > 1) {
          return false;
        }
      }
    }

    return true;
  }
}
