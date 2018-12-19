package com.liujun.datastruct.charMatch.ahoCorasick;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ac自动机算法的节点信息
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/12/16
 */
public class AhoCorasick {

  private static final char CHARA = 'a';

  private static final int CHAR_LENGTH = 26;

  public class AcNode {

    public char data;

    public AcNode[] children = new AcNode[CHAR_LENGTH];

    /** 结尾字符为true */
    public boolean isEndingChar = false;

    /** 当isEndingChar为true时，记录模式串长度 */
    public int length;

    /** 失败指针 */
    public AcNode fail;

    public AcNode(char data) {
      this.data = data;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("AcNode{");
      sb.append("data=").append(data);
      sb.append('}');
      return sb.toString();
    }
  }

  private AcNode root = new AcNode('/');

  /**
   * 构建Ac自动机的节点，即插入数据
   *
   * @param values 值信息
   */
  public void add(String values) {

    char[] charValues = values.toCharArray();

    AcNode procNode = root;

    for (int i = 0; i < charValues.length; i++) {
      int ascIndex = charValues[i] - CHARA;
      if (procNode.children[ascIndex] == null) {
        AcNode childArrays = new AcNode(charValues[i]);
        procNode.children[ascIndex] = childArrays;
      }
      procNode = procNode.children[ascIndex];
    }

    procNode.length = charValues.length;
    procNode.isEndingChar = true;
  }

  /** 自己构建失败的节点关联关系 */
  public void buildFailNode() {
    Queue<AcNode> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {

      AcNode queuOneNode = queue.remove();

      // 遍成子节点，找出与之对应的失败节点
      for (int i = 0; i < CHAR_LENGTH; i++) {
        AcNode nodeTwo = queuOneNode.children[i];

        // 当前子节点为空，直接跳过
        if (null == nodeTwo) {
          continue;
        }

        // 如果当前节点为根节点，直接写失败节点信息
        if (queuOneNode == root) {
          nodeTwo.fail = root;
        }
        // 遍历查找子节点为其设置根节点
        else {
          AcNode currFail = queuOneNode.fail;

          while (currFail != null) {
            // 检查在子节点中是否匹配当前的子节点信息
            int ascIndex = nodeTwo.data - CHARA;
            AcNode twoNode = currFail.children[ascIndex];

            // 如果在子节点信息中被找到，则设置当前子节的失败节点为找到的节点
            if (twoNode != null) {
              nodeTwo.fail = twoNode;
              break;
            }
            currFail = currFail.fail;
          }

          // 如果查找到最后也没有找到，则设置为根节点
          if (null == currFail) {
            nodeTwo.fail = root;
          }
        }

        queue.add(nodeTwo);
      }
    }
  }

  public void match(String src) {
    char[] srcChars = src.toCharArray();

    int length = srcChars.length;

    AcNode node = root;

    for (int i = 0; i < length; i++) {

      int ascInx = srcChars[i] - CHARA;

      // 当当前节点中不存在此索引下标，并且不等于root节点
      while (node.children[ascInx] == null && node != root) {
        node = node.fail;
      }

      node = node.children[ascInx];

      // 如果未发生匹配，则从root节点开始进行匹配
      if (node == null) {
        node = root;
      }

      AcNode matchNode = node;

      while (matchNode != root) {
        if (matchNode.isEndingChar == true) {
          int pos = i - matchNode.length + 1;
          System.out.println("匹配开始下标:" + pos + ";长度" + matchNode.length);
        }
        matchNode = matchNode.fail;
      }
    }
  }

  public void myMatch(String src) {
    char[] srcArrays = src.toCharArray();

    // 从根节点开始检查
    AcNode currNode = root;

    for (int i = 0; i < srcArrays.length; i++) {
      // 计算在数据中的下标位置
      int currAscIndex = srcArrays[i] - CHARA;

      // 首先检查前一个节点下是否可以匹配当前节点,不能匹配，则查找前一节点的失败节点
      while (currNode.children[currAscIndex] == null && currNode != root) {
        currNode = currNode.fail;
      }

      // 将处理节点指向能匹配的的节点
      currNode = currNode.children[currAscIndex];

      // 如果节点为空，则默认使用root节点
      if (currNode == null) {
        currNode = root;
      }

      AcNode pringNode = currNode;

      // 进行匹配打印
      while (pringNode != root) {
        if (pringNode.isEndingChar) {
          int index = i - pringNode.length + 1;
          System.out.println("计算得到的当前的节点索引为:" + index + "，字符中为" + pringNode.length);
        }
        pringNode = pringNode.fail;
      }
    }
  }

  public void printFailNode() {
    AcNode node = root;
    Queue<AcNode> queue = new LinkedList<>();

    queue.add(root);

    AcNode nodeItem = null;

    while (!queue.isEmpty()) {
      nodeItem = queue.remove();

      if (nodeItem != null) {
        AcNode[] childs = nodeItem.children;

        for (int i = 0; i < childs.length; i++) {
          if (childs[i] != null) {
            queue.add(childs[i]);
          }
        }
      }

      System.out.println("当前节点为:" + nodeItem.data + "，失败指针为:" + nodeItem.fail);
    }
  }
}
