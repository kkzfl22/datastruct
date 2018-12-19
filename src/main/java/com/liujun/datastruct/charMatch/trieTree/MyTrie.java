package com.liujun.datastruct.charMatch.trieTree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/14
 */
public class MyTrie {

  /** 字符a的asc码值 */
  private static final char CHARA = 'a';

  /** trie 树的基本节点信息 */
  public class TrieNode {

    /** 当前节点的值 */
    public char data;

    /** 子节点的信息 */
    public TrieNode[] children = new TrieNode[26];

    /** 用来标识当前是否被全完匹配 */
    public boolean isEndChar = false;

    public TrieNode(char data) {
      this.data = data;
    }
  }

  private TrieNode root = new TrieNode('/');

  /**
   * 向trie树中插入一个字符
   *
   * @param addVal 字符信息
   */
  public void insert(String addVal) {

    char[] charsData = addVal.toCharArray();

    TrieNode procNode = root;

    for (int i = 0; i < charsData.length; i++) {
      // 计算当前字符的asc码值
      int ascValue = charsData[i] - CHARA;

      if (procNode.children[ascValue] == null) {
        TrieNode newNode = new TrieNode(charsData[i]);
        procNode.children[ascValue] = newNode;
      }
      procNode = procNode.children[ascValue];
    }
    procNode.isEndChar = true;
  }

  /**
   * 查找一个trie树
   *
   * @param find 字符信息
   * @return 是否能被查找到
   */
  public boolean find(String find) {

    char[] charsData = find.toCharArray();

    TrieNode procNode = root;

    for (int i = 0; i < charsData.length; i++) {
      int ascValue = charsData[i] - CHARA;

      if (procNode.children[ascValue].data != charsData[i]) {
        return false;
      }
      procNode = procNode.children[ascValue];
    }

    if (!procNode.isEndChar) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * 查找一个trie树
   *
   * @param find 字符信息
   * @return 是否能被查找到
   */
  public Set<String> find2(String find) {

    char[] findChars = find.toCharArray();

    TrieNode node = root;

    for (int i = 0; i < findChars.length; i++) {
      int index = findChars[i] - 'a';

      if (node.children[index] == null) {
        return null;
      }
      node = node.children[index];
    }

    List<Character> matchOthers = new ArrayList<>();

    for (int i = 0; i < findChars.length - 1; i++) {
      matchOthers.add(findChars[i]);
    }

    Set<String> mvalueSet = new HashSet<>();
    addMatches(matchOthers, mvalueSet, node);

    return mvalueSet;
  }

  public void addMatches(List<Character> matchOthers, Set<String> matchValue, TrieNode node) {

    if (node != null) {
      matchOthers.add(node.data);

      // 检查是否为最后一级
      boolean isLast = true;

      for (int i = 0; i < node.children.length; i++) {
        if (node.children[i] != null) {
          isLast = false;
        }
      }

      if (isLast) {

        char[] msgChars = new char[matchOthers.size()];

        for (int i = 0; i < matchOthers.size(); i++) {
          msgChars[i] = matchOthers.get(i);
        }
        matchValue.add(new String(msgChars));
        return;
      }
    } else {
      return;
    }

    for (TrieNode nodeItem : node.children) {
      int befSize = matchOthers.size();

      addMatches(matchOthers, matchValue, nodeItem);

      int afterSize = matchOthers.size();

      if (afterSize > befSize) {
        // 完成后需要移除最后一次添加的，数据，以防止数据重复
        matchOthers.remove(matchOthers.size() - 1);
      }
    }
  }
}
