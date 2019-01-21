package com.liujun.datastruct.advanced.shortestPath;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 计算地图的最短距离
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/13
 */
public class MyGraph {

  /** 顶点的个数 */
  private int v;

  private LinkedList<Edge> adj[];

  public MyGraph(int v) {
    this.v = v;
    this.adj = new LinkedList[v];

    for (int i = 0; i < v; i++) {
      this.adj[i] = new LinkedList<>();
    }
  }

  /**
   * 添加一条区，
   *
   * @param s 起始顶点
   * @param t 结束顶点
   * @param w 权重，现在权重为距离
   */
  public void addEdge(int s, int t, int w) {
    this.adj[s].add(new Edge(s, t, w));
  }

  /** */
  private class Edge {

    /** 起始顶点 */
    public int sid;

    /** 结束顶点 */
    public int tid;

    /** 权重 */
    public int w;

    public Edge(int sid, int tid, int w) {
      this.sid = sid;
      this.tid = tid;
      this.w = w;
    }
  }

  /** 为实现dijkstra算法 */
  private class Vertex implements Comparable<Vertex> {

    /** 顶点编号的id */
    public int id;

    /** 从起始顶点到这个顶点的距离 */
    public int dist;

    public Vertex(int id, int dist) {
      this.id = id;
      this.dist = dist;
    }

    @Override
    public int compareTo(Vertex o) {

      if (o.dist > this.dist) {
        return -1;
      }

      return 1;
    }
  }

  /**
   * 顶点s到顶点t的最短路径,dijkstra算法
   *
   * @param s 开始顶点
   * @param t 结束顶点
   */
  public void dijkstra(int s, int t) {
    // 1,声明一个数组用来记录下已经访问过的下标
    int[] process = new int[this.v];

    // 2,用来记录从开始顶点到每个顶点的权重，当前权重为距离
    Vertex[] vertextNext = new Vertex[this.v];

    // 3,用于标识当前对象是否被访问过
    boolean[] visted = new boolean[this.v];

    // 初始化vertextNext数组
    for (int i = 0; i < this.v; i++) {
      vertextNext[i] = new Vertex(i, Integer.MAX_VALUE);
    }

    // 小顶堆，用来计算最短距离
    PriorityQueue<Vertex> queue = new PriorityQueue<>();

    // 放入第一个顶点
    queue.add(vertextNext[s]);
    // 首个顶点的权重更新，即距离更新为1
    vertextNext[s].dist = 0;
    // 标识当前第一个顶点被访问
    visted[s] = true;

    Vertex minVertext = null;

    while (!queue.isEmpty()) {
      minVertext = queue.poll();
      // 检查当前顶点是否为目标顶点，如果为目标顶点，则退出
      if (minVertext.id == t) {
        break;
      }

      for (int i = 0; i < adj[minVertext.id].size(); i++) {
        Edge runItem = adj[minVertext.id].get(i);

        // 得到下一个顶点
        Vertex verNext = vertextNext[runItem.tid];

        if (minVertext.dist + runItem.w < verNext.dist) {

          verNext.dist = minVertext.dist + runItem.w;

          // 记录下当前的顶点的前一个顶点
          process[verNext.id] = minVertext.id;

          // 如果当前顶点未访问过，则加入队列继续访问
          if (visted[verNext.id] == false) {
            queue.add(verNext);
            visted[verNext.id] = true;
          }
        }
      }
    }

    System.out.println("打印路径");
    System.out.print(s);
    print(s, t, process);
  }

  private void print(int s, int t, int[] process) {
    if (s == t) {
      return;
    }

    print(s, process[t], process);

    System.out.print("-->" + t);
  }
}
