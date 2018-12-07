package com.liujun.datastruct.graph;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/05
 */
public class TestGraph {

  @Test
  public void bfs() {

    Graph graph = new Graph(12);

    graph.add(0, new int[] {1, 4});
    graph.add(1, new int[] {0, 2, 5});
    graph.add(2, new int[] {1, 3, 6});
    graph.add(3, new int[] {2, 7});
    graph.add(4, new int[] {0, 5, 8});
    graph.add(5, new int[] {1, 4, 9, 6});
    graph.add(6, new int[] {2, 5, 10, 7});
    graph.add(7, new int[] {3, 6, 11});
    graph.add(8, new int[] {4, 9});
    graph.add(9, new int[] {5, 8, 10});
    graph.add(10, new int[] {6, 9, 11});
    graph.add(11, new int[] {7, 10});

    // 进行搜索
    graph.bdfs(0, 11);
  }
}
