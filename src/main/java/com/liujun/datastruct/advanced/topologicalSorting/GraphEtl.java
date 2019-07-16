package com.liujun.datastruct.advanced.topologicalSorting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/13
 */
public class GraphEtl {

  private static final int NUM_START = 1080146;

  /** 顶点的个数 */
  private int v;

  /** 邻接表 */
  private LinkedList<Integer> adj[];

  public GraphEtl(int v) {
    // 初始化顶点数
    this.v = v;
    this.adj = new LinkedList[v];
    // 初始化领接表
    for (int i = 0; i < v; i++) {
      this.adj[i] = new LinkedList<>();
    }
  }

  /**
   * s 先于t,则添加一条s到t 边
   *
   * <p>如果a先于b执行，则设置一条a指向b的边
   *
   * @param s
   * @param t
   */
  public void addEdge(int s, int t) {
    s = s - NUM_START;
    t = t - NUM_START;
    // 在顶点s，添加一条到t的边
    adj[s].add(t);
  }

  public void kahn(int startPonint) {

    startPonint = startPonint - NUM_START;

    // 1,找到入度为0的顶点
    int[] ingree = new int[v];

    // 统计顶点的个数
    for (int i = 0; i < v; i++) {
      for (int j = 0; j < adj[i].size(); j++) {
        int ponts = adj[i].get(j);
        // System.out.println("当前值:" + ponts);
        ingree[ponts]++;
      }
    }

    // 首先将入库为0的顶点放入队列
    LinkedList<Integer> linkedList = new LinkedList<>();

    List<List<Integer>> pointList = new ArrayList<>();

    for (int i = 0; i < v; i++) {
      // if (ingree[i] == 0 && ingree[i] == startPonint) {
      if (ingree[i] == 0 && i == startPonint) {
        System.out.println("找到顶点");
        linkedList.add(i);
        List<Integer> list = new ArrayList();
        list.add(i);
        pointList.add(list);
      }
    }

    int lastpointval = -1;

    // 然后开始打印顶点
    while (!linkedList.isEmpty()) {
      int pointsval = linkedList.remove();
      System.out.print("-->" + (pointsval + NUM_START));

      // 将对应的顶点的入度减1
      for (int i = 0; i < adj[pointsval].size(); i++) {
        int k = adj[pointsval].get(i);
        ingree[k]--;
        // 如果当间当前边的入度不为0，说明还有边没有访问到，需要
        if (ingree[k] == 0) {
          if (lastpointval == -1) {
            lastpointval = pointsval;
            List<Integer> pontList = new ArrayList<>();
            pontList.add(k);
            pointList.add(pontList);
          } else if (lastpointval == pointsval) {
            pointList.get(pointList.size() - 1).add(k);
          } else {
            lastpointval = pointsval;
            List<Integer> pontList = new ArrayList<>();
            pontList.add(k);
            pointList.add(pontList);
          }

          linkedList.add(k);
        }
      }
    }
    System.out.println();
    System.out.println("--------------------------------------");

    for (int i = 0; i < pointList.size(); i++) {
      System.out.print("-->");
      for (int j = 0; j < pointList.get(i).size(); j++) {
        System.out.print(pointList.get(i).get(j) + NUM_START + ",");
      }
    }

    boolean cycleFlag = false;

    // 当完成之后，检查表的表中的入度，如果发现还有不为0，说明存在环
    for (int i = 0; i < ingree.length; i++) {
      if (ingree[i] != 0) {

        cycleFlag = true;

        break;
      }
    }

    if (cycleFlag) {
      System.out.println("当前存在环");
    } else {
      System.out.println("当前不存在环");
    }
  }

  public void topoSortByDfs() {
    // 1,构建逆邻接表
    LinkedList<Integer> inverseAdj[] = new LinkedList[v];

    // 初始化逆邻接表
    for (int i = 0; i < v; i++) {
      inverseAdj[i] = new LinkedList<>();
    }

    // 开始构建逆邻表
    for (int i = 0; i < v; i++) {
      for (int j = 0; j < adj[i].size(); j++) {
        int targetPoint = adj[i].get(j);

        inverseAdj[targetPoint].add(i);
      }
    }

    // 开始使用dfs尝试优先搜索算法
    // 1,需要记录下访问过的顶点
    boolean[] visited = new boolean[v];

    for (int i = 0; i < v; i++) {
      // 如果当前顶点未访问过，则开始
      if (visited[i] == false) {
        // 将当前顶点标识改为true
        visited[i] = true;
        dfs(i, inverseAdj, visited);
      }
    }
  }

  private void dfs(int i, LinkedList<Integer> inverseAdj[], boolean[] visited) {
    // 访问逆邻接表，找到当前顶的的依赖，如果当前顶点还有依赖则进行依赖的打印
    for (int j = 0; j < inverseAdj[i].size(); j++) {
      int currPointValue = inverseAdj[i].get(j);

      // 如果当前顶点已经被访问过，则跳过
      if (visited[currPointValue] == true) {
        continue;
      }

      // 未访问，将顶点标识为已经访问
      visited[currPointValue] = true;

      // 继续进行深度遍历
      dfs(currPointValue, inverseAdj, visited);
    }

    // 当其他顶点都打印完毕后，则打印当前顶点
    System.out.print("-->" + i);
  }
}
