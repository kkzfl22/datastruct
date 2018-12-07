package com.liujun.datastruct.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 图这种数据结构的表示
 *
 * <p>此为无向图的实现
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/04
 */
public class Graph {

  /** 顶点的个数 */
  private int v;

  /** 邻接表信息 */
  private LinkedList<Integer>[] adj;

  public Graph(int v) {
    this.v = v;

    adj = new LinkedList[v];

    // 初始化邻接表数组
    for (int i = 0; i < v; i++) {
      adj[i] = new LinkedList<>();
    }
  }

  /**
   * 在无向图中一条边存两次
   *
   * @param s 顶点s
   * @param t 顶点t
   */
  public void add(int s, int t) {
    adj[s].add(t);
    adj[t].add(s);
  }

  /**
   * 在无向图中一条边存两次
   *
   * @param s 顶点s
   * @param t 顶点t
   */
  public void add(int s, int[] t) {
    for (int i = 0; i < t.length; i++) {
      adj[s].add(t[i]);
      // adj[t[i]].add(s);
    }
  }

  /**
   * 广度优先搜索算法
   *
   * @param s 顶点s
   * @param t 顶点t
   */
  public void bdfs(int s, int t) {
    if (s == t) {
      return;
    }

    // 用来标识已经访问过顶点，避免重复访问
    boolean[] visited = new boolean[v];
    visited[s] = true;
    // 队列用来存储已经被访问、但相领的还没有被访问的顶点
    Queue<Integer> queue = new LinkedList<>();
    queue.add(s);

    // 记录搜索的路径
    int[] prev = new int[v];
    // 将记录搜索的路径，初始化为-1
    for (int i = 0; i < v; i++) {
      prev[i] = -1;
    }

    while (queue.size() != 0) {
      int w = queue.poll();
      // System.out.println("队列的值为:" + w);
      // 遍历顶点链
      for (int i = 0; i < adj[w].size(); i++) {
        // 遍历当前顶点关联的顶点
        int q = adj[w].get(i);
        // 如果当前未被访问过
        if (!visited[q]) {
          // 当前从哪个顶点过来的,是一条顶点链
          prev[q] = w;
          System.out.println("当前的q:" + q);
          System.out.println(Arrays.toString(prev));
          if (q == t) {
            print(prev, s, t);
            return;
          }
          visited[q] = true;
          queue.add(q);
          System.out.println("压入队列:" + q);
          System.out.println();
          System.out.println();
        }
      }
    }
  }

  public void print(int[] prev, int s, int t) {
    if (prev[t] != -1 && t != s) {
      print(prev, s, prev[t]);
    }
    System.out.print(t + " ");
  }

  boolean found = false;

  /**
   * 使用深度优先的搜索算法进行搜索
   *
   * @param s 开始顶点
   * @param t 结束顶点
   */
  public void dfs(int s, int t) {
    if (s == t) {
      return;
    }

    boolean[] visited = new boolean[v];

    // 顶点遍历链
    int[] prev = new int[v];
    for (int i = 0; i < v; i++) {
      prev[i] = -1;
    }

    recurDfs(s, t, visited, prev);

    print(prev, s, t);
  }

  private void recurDfs(int s, int t, boolean[] visited, int[] prev) {

    System.out.println("当前顶点:" + s);
    if (found) {
      return;
    }
    visited[s] = true;
    if (s == t) {
      found = true;
      return;
    }

    for (int i = 0; i < adj[s].size(); i++) {
      int q = adj[s].get(i);
      if (!visited[q]) {
        prev[q] = s;
        recurDfs(q, t, visited, prev);
      }
    }
  }
}
