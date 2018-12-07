package com.liujun.datastruct.graph;

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

    // 用来标识顶点是否被访问
    boolean[] visited = new boolean[v];

    // 使用队列来记录将要访问的队列
    Queue<Integer> queue = new LinkedList<>();
    // 首先将初始化顶点压入队列
    queue.add(s);

    // 用来记录访问过的顶点信息
    int[] topArrays = new int[v];
    for (int i = 0; i < v; i++) {
      topArrays[i] = -1;
    }

    while (true) {
      // 从队列中取出一个顶点，开始访问
      int w = queue.poll();

      for (int i = 0; i < topLinked[w].size(); i++) {
        int value = topLinked[w].get(i);

        // 检查是否被访问
        if (!visited[value]) {
          // 记录下当前过来的前一个节点
          topArrays[value] = w;
          // 检查当前顶点是否为要查找的节点，如果是，则直接打印
          if (value == t) {
            System.out.println(Arrays.toString(topArrays));
            print(topArrays, s, t);
            return;
          }

          queue.add(value);
          visited[value] = true;
        }
      }
    }
  }

  /**
   * 进行顶点链的打印
   *
   * @param topArrays 当前的顶点链
   * @param start 开始顶点
   * @param top 上一个顶点
   */
  private void print(int[] topArrays, int start, int top) {
    if (topArrays[start] != -1 && start != top) {
      print(topArrays, start, topArrays[top]);
    }
    System.out.print(top + "\t");
  }
}
