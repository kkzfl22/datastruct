package com.liujun.datastruct.base.search.skipList.leetcode1206;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author liujun
 * @version 0.0.1
 */
public class Skiplist2 {

  static class Node {
    int val;
    Node right, down;

    public Node(Node r, Node d, int val) {
      right = r;
      down = d;
      this.val = val;
    }
  }

  Node head = new Node(null, null, 0);

  public boolean search(int target) {
    for (Node p = head; p != null; p = p.down) {
      while (p.right != null && p.right.val < target) {
        p = p.right;
      }
      if (p.right != null && p.right.val == target) {
        return true;
      }
    }
    return false;
  }

  Random rand = new Random();

  // 2^64 已经相当大了
  Node[] stack = new Node[64];

  public void add(int num) {
    int lv = -1;
    for (Node p = head; p != null; p = p.down) {
      while (p.right != null && p.right.val < num) {
        p = p.right;
      }
      stack[++lv] = p;
    }
    boolean insertUp = true;
    Node downNode = null;
    while (insertUp && lv >= 0) {
      Node insert = stack[lv--];
      insert.right = new Node(insert.right, downNode, num);
      downNode = insert.right;
      insertUp = (rand.nextInt() & 1) == 0;
    }
    if (insertUp) {
      head = new Node(new Node(null, downNode, num), head, 0);
    }
  }

  public boolean erase(int num) {
    boolean exists = false;
    for (Node p = head; p != null; p = p.down) {
      while (p.right != null && p.right.val < num) {
        p = p.right;
      }
      if (p.right != null && p.right.val == num) {
        exists = true;
        p.right = p.right.right;
      }
    }
    return exists;
  }
}
