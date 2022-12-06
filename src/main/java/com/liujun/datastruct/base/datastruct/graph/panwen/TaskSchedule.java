package com.liujun.datastruct.base.datastruct.graph.panwen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author liujun
 * @since 2022/8/13
 */
public class TaskSchedule {

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  static class Node {
    String nodeVal;
    int in;
    int out;
    List<Node> outList;

    public Node(String nodeVal) {
      this.nodeVal = nodeVal;
      in = 0;
      out = 0;
      outList = new ArrayList<>();
    }
  }

  public static void main(String[] args) throws IOException {
    String[] s = br.readLine().split(" ");
    int n = Integer.parseInt(s[0]);
    int m = Integer.parseInt(s[1]);
    Map<String, Node> map = new HashMap<>(n);
    String h;
    String f;
    for (int i = 0; i < m; i++) {
      String[] ss = br.readLine().split("->");
      h = ss[0];
      f = ss[1];
      if (!map.containsKey(h)) {
        map.put(h, new Node(h));
      }
      if (!map.containsKey(f)) {
        map.put(f, new Node(f));
      }
      Node a = map.get(h);
      Node b = map.get(f);
      a.in++;
      a.out++;
      b.outList.add(a);
    }
    Queue<Node> queue = new LinkedList<>();
    for (Node node : map.values()) {
      if (node.in == 0) {
        queue.offer(node);
      }
    }
    int cnt = 0;
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      cnt++;
      for (Node outNode : node.outList) {
        outNode.in--;
        if (outNode.in == 0) {
          queue.offer(outNode);
        }
      }
    }
    System.out.println(cnt == map.size() ? "YES" : "NO");
  }
}
