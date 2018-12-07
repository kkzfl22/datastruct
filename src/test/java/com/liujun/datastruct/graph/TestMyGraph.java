package com.liujun.datastruct.graph;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/07
 */
public class TestMyGraph {

  @Test
  public void testBfs() {
    MyGraph graph = new MyGraph(12);

    // 顶点0
    graph.add(0, 1);
    graph.add(0, 4);

    // 顶点1
    graph.add(1, 0);
    graph.add(1, 2);
    graph.add(1, 5);

    // 顶点2
    graph.add(2, 1);
    graph.add(2, 3);
    graph.add(2, 6);

    // 顶点3
    graph.add(3, 2);
    graph.add(3, 7);

    // 顶点4
    graph.add(4, 0);
    graph.add(4, 5);
    graph.add(4, 8);

    // 顶点5
    graph.add(5, 1);
    graph.add(5, 4);
    graph.add(5, 6);
    graph.add(5, 9);

    // 顶点6
    graph.add(6, 2);
    graph.add(6, 5);
    graph.add(6, 7);
    graph.add(6, 10);

    // 顶点7
    graph.add(7, 3);
    graph.add(7, 6);
    graph.add(7, 11);

    // 顶点8
    graph.add(8, 4);
    graph.add(8, 9);

    // 顶点9
    graph.add(9, 5);
    graph.add(9, 8);
    graph.add(9, 10);

    // 顶点10
    graph.add(10, 6);
    graph.add(10, 9);
    graph.add(10, 11);

    // 顶点11
    graph.add(11, 7);
    graph.add(11, 10);

    graph.bfs(0, 11);
  }
}
