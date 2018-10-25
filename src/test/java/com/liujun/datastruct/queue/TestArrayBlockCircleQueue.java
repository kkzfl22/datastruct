package com.liujun.datastruct.queue;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/10/25
 */
public class TestArrayBlockCircleQueue {

  public static void startThread(Runnable run) {
    new Thread(run).start();
  }

  public static void main(String[] args) {
    ArrayBlockCircleQueue queue = new ArrayBlockCircleQueue(128);

    Runnable run1 =
        () -> {
          int i = 0;
          while (true) {
            if (!queue.push(i)) {
              // System.out.println("放入失败，延迟操作");

              try {
                Thread.sleep(1);
                // queue.print();
                //  System.out.println("放入");
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            } else {
              i++;
              // System.out.println("放入i" + i);
            }
          }
        };

    Runnable run2 =
        () -> {
          int i = 0;
          while (true) {
            if ((i = queue.pop()) == -1) {
              System.out.println("获取失败，重试:" + i);

            } else {
              System.out.println("获取:" + i);
            }

            try {

              Thread.sleep(1);
              // queue.print();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        };



    TestArrayBlockCircleQueue.startThread(run1);
    TestArrayBlockCircleQueue.startThread(run1);
    TestArrayBlockCircleQueue.startThread(run1);
    TestArrayBlockCircleQueue.startThread(run2);

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
