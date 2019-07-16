package com.liujun.datastruct.advanced.mmrgb;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 使用a*搜索算法来进行搜索次优路线问题
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/02/22
 */
public class MyGraphSearchA {

  /** 顶点数 */
  private int v;

  /** 顶点连接的信息 */
  private LinkedList<Edge>[] adj;

  /** 坐标信息 */
  private Vertext[] vertexts;

  public MyGraphSearchA(int v) {
    this.v = v;
    this.adj = new LinkedList[v];

    // 初始化顶点连接的有向边信息
    for (int i = 0; i < v; i++) {
      adj[i] = new LinkedList<>();
    }

    // 初始化坐标顶点
    this.vertexts = new Vertext[this.v];
  }

  /**
   * 添加两向之前的有向边
   *
   * @param s 起始顶点
   * @param t 结束顶点
   * @param weight 权重，此处表示为距离
   */
  public void addEdge(int s, int t, int weight) {
    adj[s].add(new Edge(s, t, weight));
  }

  /**
   * 添加顶点的坐标
   *
   * @param id id信息
   * @param x 横坐标
   * @param y 纵坐标
   */
  public void addVertext(int id, int x, int y) {
    this.vertexts[id] = new Vertext(id, x, y);
  }

  /** 图中边的信息 */
  private class Edge {

    /** 起始顶点 */
    private int s;

    /** 结束顶点 */
    private int t;

    /** 权重,可以为两个顶点的距离，也可以为两个顶点之间其他的信息 */
    private int weight;

    /**
     * 边的信息
     *
     * @param s 顶点
     * @param t 顶点
     * @param weight 权重，此表示距离
     */
    public Edge(int s, int t, int weight) {
      this.s = s;
      this.t = t;
      this.weight = weight;
    }
  }

  /** 用于两点之间的权重计算 */
  private class Vertext implements Comparable<Vertext> {

    /** 从起始顶点到当前顶点 */
    private int id;

    /** 从起始顶点到当前顶点的计算，如果为距离 ，就是起始顶点到当前顶点的距离 */
    private int dist;

    /** 使用估值函数计算出来的f值，用于估计两点之间的距离 */
    private int f;

    /** 地图中的坐标信息 */
    private int x, y;

    public Vertext(int id, int x, int y) {
      this.id = id;
      this.x = x;
      this.y = y;
      this.dist = Integer.MAX_VALUE;
      this.f = Integer.MAX_VALUE;
    }

    @Override
    public int compareTo(Vertext o) {
      // 使用估价函数来代替，按权重的计算
      if (this.f > o.f) {
        return 1;
      } else if (this.dist == o.dist) {
        return 0;
      }

      return -1;
    }
  }

  /**
   * 启发函数
   *
   * <p>使用曼哈顿距离来进行计算两点之间的距离
   *
   * <p>也可以使用欧几里德距离，但会涉及比较耗时的开根计算，因此使用曼哈顿距离
   *
   * @param v1 顶点1
   * @param v2 顶点2
   * @return
   */
  public int hManhattan(Vertext v1, Vertext v2) {
    return Math.abs(v1.x - v2.y) + Math.abs(v1.y - v2.y);
  }

  /**
   * 使用a*搜索算法进行搜索操作
   *
   * @param s
   * @param t
   */
  public void astar(int s, int t) {
    // 记录下路径
    int[] paths = new int[this.v];
    // 构建优先级队列
    PriorityQueue<Vertext> priQueue = new PriorityQueue<>(this.v);
    // 顶点是否被访问过
    boolean[] exists = new boolean[this.v];

    // 将初始顶点的f值与dist更新
    vertexts[s].f = 0;
    vertexts[s].dist = 0;

    // 将起始顶点放入到队列中
    priQueue.add(vertexts[s]);

    Vertext finNodeItem;

    while (!priQueue.isEmpty()) {
      finNodeItem = priQueue.remove();

      for (int i = 0; i < adj[finNodeItem.id].size(); i++) {
        // 取出一条有向边
        Edge edge = adj[finNodeItem.id].get(i);
        // 取出边的另外一个顶点
        Vertext vertexNext = vertexts[edge.t];
        // 进行搜索算法的减枝操作
        if (finNodeItem.dist + edge.weight < vertexNext.dist) {
          // 进行估价函数与dist值的更新
          vertexNext.dist = finNodeItem.dist + edge.weight;
          vertexNext.f = vertexNext.dist + this.hManhattan(finNodeItem, vertexNext);
          // 记录下访问的路径
          paths[edge.t] = edge.s;

          // 检查顶点是否被访问
          if (!exists[edge.t]) {
            exists[edge.t] = true;
            priQueue.add(vertexNext);
          }
        }

        if (vertexNext.id == t) {
          priQueue.clear();
          break;
        }
      }
    }

    System.out.println("求解的路径:");
    System.out.println(s);
    printPath(s, t, paths);
  }

  private void printPath(int s, int t, int[] paths) {
    if (s == t) {
      return;
    }

    printPath(s, paths[t], paths);

    System.out.print("-->" + t);
  }
}
