package com.liujun.datastruct.charMatch.trieTree;

import org.junit.Test;

import java.util.Set;

/**
 * 测试trie树结构
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/14
 */
public class TestMyTrie {

  @Test
  public void find() {
    MyTrie inst = new MyTrie();

    inst.insert("how");
    inst.insert("hi");
    inst.insert("her");
    inst.insert("hello");
    inst.insert("so");
    inst.insert("see");
    System.out.println(inst.find("hi"));
  }

  @Test
  public void find2() {

    MyTrie inst = new MyTrie();

    inst.insert("how");
    inst.insert("hi");
    inst.insert("her");
    inst.insert("hello");
    inst.insert("helower");
    inst.insert("so");
    inst.insert("see");
    // System.out.println(inst.find("so"));

    Set<String> value = inst.find2("he");

    if (null != value) {
      for (String vsl : value) {
        System.out.println(vsl);
      }
    }
  }

  @Test
  public void toCharset() {
    String value = "打印";
    char[] vaCharrs = value.toCharArray();

    for (int i = 0; i < vaCharrs.length; i++) {
      System.out.println((int) vaCharrs[i]);
    }

    int utf8value = 21360;
    char vals = (char) utf8value;
    System.out.println("vals :" + vals);
  }
}
