package com.liujun.datastruct.advanced.mmrgb;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2019/02/25
 */
public class TestMyGraphSearchA {

  @Test
  public void testShortPath() {
    MyGraphSearchA instance = new MyGraphSearchA(14);

    instance.addVertext(0, 320, 630);
    instance.addVertext(1, 300, 630);
    instance.addVertext(2, 280, 625);
    instance.addVertext(3, 270, 630);
    instance.addVertext(4, 320, 700);
    instance.addVertext(5, 360, 620);
    instance.addVertext(6, 320, 590);
    instance.addVertext(7, 370, 580);
    instance.addVertext(8, 350, 730);
    instance.addVertext(9, 390, 690);
    instance.addVertext(10, 400, 620);

    instance.addEdge(0, 1, 20);
    instance.addEdge(0, 4, 60);
    instance.addEdge(0, 6, 60);
    instance.addEdge(0, 5, 60);
    instance.addEdge(1, 2, 20);
    instance.addEdge(2, 3, 10);
    instance.addEdge(3, 12, 40);
    instance.addEdge(3, 13, 30);
    instance.addEdge(13,6,50);
    instance.addEdge(12,4,40);
    instance.addEdge(4,8,50);
    instance.addEdge(8,9,50);
    instance.addEdge(6,7,70);
    instance.addEdge(7,11,50);
    instance.addEdge(10,11,60);
    instance.addEdge(5,10,50);
    instance.addEdge(5,9,80);
    instance.addEdge(9,10,60);
    instance.addEdge(10,11,60);

    instance.astar(4,9);

  }
}
