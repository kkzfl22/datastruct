package com.liujun.datastruct.advanced.topologicalSorting;

import org.junit.Test;

/**
 * 进行测试
 *
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/13
 */
public class TestGraph {

  @Test
  public void testKnhn() {
    Graph instance = new Graph(6);
    instance.addEdge(0, 1);
    instance.addEdge(2, 3);
    instance.addEdge(4, 5);
    instance.addEdge(1, 2);
    instance.addEdge(3, 4);
    // instance.addEdge(5, 0);
    instance.kahn();
    // instance.topoSortByKahn();
  }

  @Test
  public void testKnhn2() {
    Graph instance = new Graph(10);
    instance.addEdge(6, 7);
    instance.addEdge(5, 8);
    instance.addEdge(7, 8);
    instance.addEdge(8, 9);
    instance.addEdge(4, 5);
    instance.addEdge(4, 6);
    instance.addEdge(1, 4);
    instance.addEdge(2, 4);
    instance.addEdge(3, 4);

    instance.kahn();
    // instance.topoSortByKahn();
  }

  @Test
  public void testDfs() {
    Graph instance = new Graph(10);
    instance.addEdge(6, 7);
    instance.addEdge(5, 8);
    instance.addEdge(7, 8);
    instance.addEdge(8, 9);
    instance.addEdge(4, 5);
    instance.addEdge(4, 6);
    instance.addEdge(1, 4);
    instance.addEdge(2, 4);
    instance.addEdge(3, 4);

    instance.topoSortByDfs();
    // instance.topoSortByKahn();
  }
}
