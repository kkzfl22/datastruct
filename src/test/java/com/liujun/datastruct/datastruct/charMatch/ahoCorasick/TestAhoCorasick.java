package com.liujun.datastruct.datastruct.charMatch.ahoCorasick;

import org.junit.Test;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/16
 */
public class TestAhoCorasick {

  @Test
  public void testAcNode() {
    AhoCorasick node = new AhoCorasick();

    node.add("c");
    node.add("bc");
    node.add("bcd");
    node.add("abcd");

    // node.buildFailurePointer();
    node.buildFailNode();

    node.printFailNode();

    // String src = "what are you doing? abcd";
    // node.match(src);
  }

  @Test
  public void testMatch() {
    AhoCorasick node = new AhoCorasick();

    node.add("c");
    node.add("bc");
    node.add("bcd");
    node.add("abcd");

    node.buildFailNode();

    String src = "acbc";
    node.match(src);
    System.out.println("------------------");
    node.myMatch(src);
    // node.match(src.toCharArray());
  }
}
