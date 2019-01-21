package com.liujun.datastruct.advanced.shortestPath;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/01/13
 */
public class TestGraph {

  @Test
  public void testShortPath() {
    Graph instance = new Graph(15);
    instance.addEdge(1, 5, 5);
    instance.addEdge(2, 5, 10);
    instance.addEdge(3, 7, 7);
    instance.addEdge(3, 6, 6);
    instance.addEdge(4, 6, 8);
    instance.addEdge(4, 7, 6);
    instance.addEdge(5, 8, 3);
    instance.addEdge(6, 8, 2);
    instance.addEdge(6, 9, 3);
    instance.addEdge(7, 9, 2);
    instance.addEdge(8, 10, 1);
    instance.addEdge(8, 11, 1);
    instance.addEdge(9, 11, 2);
    instance.addEdge(9, 12, 1);
    instance.addEdge(10, 13, 3);
    instance.addEdge(11, 13, 2);
    instance.addEdge(11, 14, 2);
    instance.addEdge(11, 14, 1);
    instance.addEdge(12, 14, 2);
    instance.dijkstra(3, 14);
  }

  @Test
  public void testShortPath2() {
    Graph instance = new Graph(17);
    instance.addEdge(1, 5, 5);
    instance.addEdge(2, 5, 10);
    instance.addEdge(3, 7, 7);
    instance.addEdge(3, 6, 6);
    instance.addEdge(4, 6, 8);
    instance.addEdge(4, 7, 6);
    instance.addEdge(5, 8, 3);
    instance.addEdge(6, 8, 2);
    instance.addEdge(6, 9, 3);
    instance.addEdge(7, 9, 2);
    instance.addEdge(8, 10, 1);
    instance.addEdge(8, 11, 1);
    instance.addEdge(9, 11, 2);
    instance.addEdge(9, 12, 10);
    instance.addEdge(10, 13, 3);
    instance.addEdge(11, 13, 2);
    instance.addEdge(11, 14, 20);
    instance.addEdge(12, 14, 2);
    instance.addEdge(7, 15, 1);
    instance.addEdge(15, 14, 3);
    instance.dijkstra(3, 14);
  }

  @Test
  public void testShortPath3() {
    Graph instance = new Graph(17);

    instance.addEdge(1, 4, 1);
    instance.addEdge(1, 5, 2);
    instance.addEdge(2, 4, 3);
    instance.addEdge(2, 5, 4);
    instance.addEdge(4, 7, 8);
    instance.addEdge(5, 7, 3);
    instance.addEdge(5, 8, 4);
    instance.addEdge(7, 9, 5);
    instance.addEdge(8, 9, 10);
    instance.addEdge(5, 9, 20);

    instance.dijkstra(1, 9);
  }

  @Test
  public void testShortPath4() {
    Graph instance = new Graph(17);
    instance.addEdge(1, 5, 5);
    instance.addEdge(2, 5, 10);
    instance.addEdge(3, 6, 6);
    instance.addEdge(3, 7, 7);

    instance.addEdge(4, 6, 8);
    instance.addEdge(4, 7, 6);
    instance.addEdge(5, 8, 3);
    instance.addEdge(6, 8, 2);
    instance.addEdge(6, 9, 3);
    instance.addEdge(7, 9, 2);
    instance.addEdge(7, 15, 1);
    instance.addEdge(8, 10, 1);
    instance.addEdge(8, 11, 1);
    instance.addEdge(9, 11, 2);
    instance.addEdge(9, 12, 10);
    instance.addEdge(10, 13, 3);
    instance.addEdge(11, 13, 10);
    instance.addEdge(11, 14, 10);
    instance.addEdge(12, 14, 2);

    instance.addEdge(15, 14, 3);
    // instance.dijkstrademo(3, 14);
  }
}
