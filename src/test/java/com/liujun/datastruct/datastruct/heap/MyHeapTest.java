package com.liujun.datastruct.datastruct.heap;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/27
 */
public class MyHeapTest {

  @Test
  public void insert() {
    MyHeap heap = new MyHeap(8);
    heap.insert(1);
    heap.insert(2);
    heap.insert(4);
    heap.insert(5);
    heap.insert(6);
    heap.insert(7);
    heap.print();
  }

  @Test
  public void deleteMax() {
    MyHeap heap = new MyHeap(18);
    int[] val = new int[] {33, 27, 21, 16, 13, 15, 9, 5, 6, 7, 8, 1, 2, 22};
    heap.setData(val);
    heap.deleteMax();
    heap.print();
  }

  @Test
  public void createHeap() {
    MyHeap heap = new MyHeap(15);
    int[] val = new int[] {0, 33, 27, 21, 16, 13, 15, 9, 5, 6, 7, 8, 1, 2, 22};

    heap.createheap(val, val.length - 1);

    for (int i = 0; i < val.length; i++) {
      System.out.print(val[i] + "\t");
    }
    System.out.println();

    heap.heapSort(val, val.length - 1);

    for (int i = 0; i < val.length; i++) {
      System.out.print(val[i] + "\t");
    }
    // heap.print();
  }

  @Test
  public void createHeap2() {
    int[] val = new int[] {33, 27, 21, 16, 13, 15, 9, 5, 6, 7, 8, 1, 2, 22};
    for (int i = 0; i < val.length; i++) {
      System.out.print(val[i] + "\t");
    }
    System.out.println("length:" + (val.length - 1));
    MyHeap.buildHeap(val, val.length - 1);
    for (int i = 0; i < val.length; i++) {
      System.out.print(val[i] + "\t");
    }
  }


}
