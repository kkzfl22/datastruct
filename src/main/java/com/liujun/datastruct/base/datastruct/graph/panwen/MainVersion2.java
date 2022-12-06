package com.liujun.datastruct.base.datastruct.graph.panwen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class MainVersion2 {

    /** 非环路标识 */
  private static final String CYCLE_NONE = "cycle_not";

  private static final String SPIT_COLUMN = "->";

  public static void main(String[] args) {
    Scanner sin = new Scanner(System.in);
    int index = 0;
    String firstLine = null;
    List<String> dependList = null;

    int taskNum = 0;
    int dependNum = 0;

    while (sin.hasNext()) {
      if (index == 0) {
        firstLine = sin.nextLine();
        // 输入错误，直接返回
        if (null == firstLine || firstLine.isEmpty()) {
          return;
        }

        // 以空行符切换
        String[] dataArray = spitDef(firstLine);

        taskNum = Integer.parseInt(dataArray[0]);
        dependNum = Integer.parseInt(dataArray[1]);

        dependList = new ArrayList<>();

        index++;
      } else {
        String dataValue = sin.nextLine();
        dependList.add(dataValue);

        // 如果达到数量，则跳过
        if (dependList.size() == dependNum) {
          break;
        }
      }
    }

    // 错误数据返回
    if (taskNum < 1 || dependNum < 1) {
      return;
    }
    if (dependList.size() != dependNum) {
      return;
    }

    Set<String> pointList = getPoint(dependList, dependNum);
    Graph graph = new Graph(pointList);

    for (String line : dependList) {
      String[] lineArray = spit(line, SPIT_COLUMN);
      graph.addEdge(lineArray[0], lineArray[1]);
    }

    // 检查是否存在环，只要不存在环，就可以结束
    String cyclePoint = graph.checkCycle();

    if (CYCLE_NONE.equals(cyclePoint)) {
      System.out.println("YES");
      return;
    }

    System.out.println("NO");
    return;
  }

  private static Set<String> getPoint(List<String> dependList, int point) {
    Set<String> dataSet = new HashSet<>(point);
    for (String line : dependList) {
      String[] lineArray = spit(line, SPIT_COLUMN);
      dataSet.add(lineArray[0]);
      dataSet.add(lineArray[1]);
    }

    return dataSet;
  }

  private static String[] spit(String line, String flagSpit) {
    StringTokenizer token = new StringTokenizer(line, flagSpit);

    String[] result = new String[token.countTokens()];
    int index = 0;
    while (token.hasMoreElements()) {
      result[index] = token.nextToken();
      index++;
    }

    return result;
  }

  private static String[] spitDef(String line) {
    StringTokenizer token = new StringTokenizer(line);

    String[] result = new String[token.countTokens()];
    int index = 0;
    while (token.hasMoreElements()) {
      result[index] = token.nextToken();
      index++;
    }

    return result;
  }

  public static class Graph {

    /** 按值与索引的对应关系 */
    private Map<String, Integer> srcToIndexMap;

    /** 顶点的个数 */
    private int v;

    /** 邻接表 */
    private List<Integer>[] adj;

    public Graph(Set<String> vertextValueSet) {

      if (null == vertextValueSet || vertextValueSet.size() < 1) {
        throw new IllegalArgumentException("grap point is 0");
      }

      int v = vertextValueSet.size();

      // 初始化顶点数
      this.v = v;
      this.adj = new LinkedList[v];
      // 初始化领接表
      for (int i = 0; i < v; i++) {
        this.adj[i] = new LinkedList<>();
      }

      this.srcToIndexMap = new HashMap<>(v);

      int index = 0;
      for (String vertextItem : vertextValueSet) {
        srcToIndexMap.put(vertextItem, index);
        index++;
      }

      vertextValueSet = null;
    }

    /**
     * s 先于t,则添加一条s到t 边
     *
     * <p>如果a先于b执行，则设置一条a指向b的边
     *
     * @param s
     * @param t
     */
    public void addEdge(String s, String t) {

      if (!srcToIndexMap.containsKey(s)) {
        throw new RuntimeException("point s :" + s + ",not exists");
      }

      if (!srcToIndexMap.containsKey(t)) {
        throw new RuntimeException("point t :" + t + ",not exists");
      }

      int outs = srcToIndexMap.get(s);
      int outt = srcToIndexMap.get(t);

      // 在顶点s，添加一条到t的边
      if (!adj[outs].contains(outt)) {
        adj[outs].add(outt);
      }
    }

    /**
     * 依赖环地检测
     *
     * @return -1表示不存在环，其他表示存在环
     */
    public String checkCycle() {
      // 1,找到入度为0的顶点
      int[] ingree = new int[v];

      // 统计顶点的个数
      for (int i = 0; i < v; i++) {
        for (int j = 0; j < adj[i].size(); j++) {
          int ponts = adj[i].get(j);
          ingree[ponts]++;
        }
      }

      // 首先将入库为0的顶点放入队列
      LinkedList<Integer> linkedList = new LinkedList<>();

      for (int i = 0; i < v; i++) {
        if (ingree[i] == 0) {
          linkedList.add(i);
        }
      }

      // 然后开始打印顶点
      while (!linkedList.isEmpty()) {
        int pointsval = linkedList.remove();
        // 将对应的顶点的入度减1
        for (int i = 0; i < adj[pointsval].size(); i++) {
          int k = adj[pointsval].get(i);
          ingree[k]--;
          // 如果当间当前边的入库不为0，说明还有边没有访问到，需要
          if (ingree[k] == 0) {
            linkedList.add(k);
          }
        }
      }

      // 检查环的存在操作
      int cycleId = -1;

      // 当完成之后，检查表的表中的入度，如果发现还有不为0，说明存在环
      for (int i = 0; i < ingree.length; i++) {
        if (ingree[i] != 0) {
          cycleId = i;
          break;
        }
      }

      if (cycleId != -1) {
        return String.valueOf(cycleId);
      }

      return CYCLE_NONE;
    }
  }
}
