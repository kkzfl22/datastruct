package com.liujun.datastruct.datastruct.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 自定义图的代码实现，并进行搜索
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/07
 */
public class MyGraph {

  /** 顶点个数 */
  private int v;

  /** 用来存储顶点的链 */
  private LinkedList<Integer>[] topLinked;

  public MyGraph(int v) {
    this.v = v;
    // 初始化顶点链信息
    topLinked = new LinkedList[v];
    for (int i = 0; i < v; i++) {
      topLinked[i] = new LinkedList<>();
    }
  }

  public void add(int s, int t) {
    topLinked[s].add(t);
    topLinked[t].add(s);
  }

  public void add(int s, int[] tops) {
    for (int i = 0; i < tops.length; i++) {
      topLinked[s].add(tops[i]);
    }
  }

  /**
   * 使用bdfs算法进行搜索路径的算法
   *
   * @param s 起始顶点
   * @param t 结束顶点
   */
  public void bfs(int s, int t) {

    if (s == t) {
      return;
    }

    // 1，当前顶点是否被访问过的数组
    boolean[] visited = new boolean[v];

    // 2，队列，用来记录下同层的待扫描的节点,初始扫描为起始顶点
    Queue<Integer> queue = new LinkedList<>();
    queue.add(s);

    // 3,记录下访问顺序的
    int[] prev = new int[v];
    for (int i = 0; i < v; i++) {
      prev[i] = -1;
    }

    // 进行队列扫描
    while (true) {
      // 进行遍历操作
      int queuePoint = queue.poll();

      // 遍历顶点链
      for (int i = 0; i < topLinked[queuePoint].size(); i++) {
        int currPoint = topLinked[queuePoint].get(i);
        // 检查顶点是否被访问
        if (!visited[currPoint]) {
          visited[currPoint] = true;
          // 记录下前一个顶点
          prev[currPoint] = queuePoint;

          // 如果顶点的关联关系被找到，则进行打印信息
          if (currPoint == t) {
            this.print(prev, s, currPoint);
            return;
          }

          // 将元素夺入队列，继续层层推进
          queue.add(currPoint);
        }
      }
    }
  }

  /**
   * 进行顶点的打印操作
   *
   * @param prev 访问的顶点链操作
   * @param start 开始节点
   * @param top 上一个节点
   */
  private void print(int[] prev, int start, int top) {
    if (prev[top] != -1 && start != top) {
      print(prev, start, prev[top]);
    }

    System.out.print(top + "\t");
  }

  /**
   * 深度优先搜索算法
   *
   * @param s
   * @param t
   */
  public void dfs(int s, int t) {
    if (s == t) {
      return;
    }

    // 声明访问过的对象信息
    boolean[] visited = new boolean[v];

    // 访问对象信息
    int[] prev = new int[v];
    for (int i = 0; i < v; i++) {
      prev[i] = -1;
    }

    // 进行使用递归进行深度优先搜索算法
    boolean found = recurDfs(s, t, visited, prev);
    if (found) {
      print(prev, s, t);
    }
  }

  private boolean recurDfs(int s, int t, boolean[] visited, int[] prev) {

    visited[s] = true;
    if (s == t) {
      return true;
    }

    for (int i = 0; i < topLinked[s].size(); i++) {
      int tmpQ = topLinked[s].get(i);

      if (!visited[tmpQ]) {
        prev[tmpQ] = s;
        return recurDfs(tmpQ, t, visited, prev);
      }
    }

    return false;
  }
}
