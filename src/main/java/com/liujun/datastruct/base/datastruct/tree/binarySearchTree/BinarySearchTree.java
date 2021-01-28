package com.liujun.datastruct.base.datastruct.tree.binarySearchTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 二叉查找树
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/14
 */
public class BinarySearchTree {

  public class treeNode {

    /** 数据信息 */
    private int value;

    /** 左子树 */
    private treeNode left;

    /** 右子树 */
    private treeNode right;

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("treeNode{");
      sb.append("value=").append(value);
      sb.append('}');
      return sb.toString();
    }
  }

  /** 根节点树信息 */
  public treeNode root;

  /**
   * 进行二叉查找树的插入操作
   *
   * @param value
   */
  public void insert(int value) {
    // 1,检查当前树节点是否为空，如果为空，则插入根节点
    if (null == root) {
      root = new treeNode();
      root.value = value;
      return;
    }

    treeNode findInser = root;
    // 循环检查
    while (findInser != null) {
      // 1,情况1，如果当前值小于当前值，则插入左子树
      if (value < findInser.value) {
        // 检查左子树是否为空，如果为空，直接插入，
        if (findInser.left == null) {
          treeNode leftNode = new treeNode();
          leftNode.value = value;
          findInser.left = leftNode;
          return;
        }
        // 不为空，则继续遍历
        findInser = findInser.left;
      }

      // 2,如果值大于当前节点，则插入右子树
      else if (value > findInser.value) {
        // 检查右子树是否为空，为空，则直接插入
        if (findInser.right == null) {
          treeNode rightNode = new treeNode();
          rightNode.value = value;
          findInser.right = rightNode;
          return;
        }
        // 不为空，则继续遍历
        findInser = findInser.right;
      }
    }
  }

  /**
   * 二叉查找树的原理，从根节点开始遍历，
   *
   * <p>发现比根节点小的值，就在左边继续查询
   *
   * <p>发现比根节点大的值，就在右边继续查找
   *
   * @param value
   * @return
   */
  public treeNode searchNode(int value) {
    treeNode findNode = root;

    while (findNode != null) {

      if (value == findNode.value) {
        return findNode;
      }

      // 如果值比当前节点小，说明需要在左树继续查找
      else if (value < findNode.value) {
        findNode = findNode.left;
      }
      // 如果值比当前节点大，说明需要在右树继续查找
      else {
        findNode = findNode.right;
      }
    }

    return null;
  }

  /**
   * 使用左序遍历打印二叉树
   *
   * @param node 节点信息
   */
  public void printNode(treeNode node) {

    if (null == node) {
      return;
    }

    System.out.print(node.value + "\t");

    printNode(node.left);
    printNode(node.right);
  }

  /**
   * 按层级打印
   *
   * @param node
   */
  public void printTreeeNode(treeNode node) {

    Map<Integer, List<treeNode>> nodeMap = new HashMap<>();

    this.groupLevelNode(node, nodeMap, 1);

    for (Map.Entry<Integer, List<treeNode>> item : nodeMap.entrySet()) {
      System.out.println(item);
    }
  }

  /**
   * 删除二叉树节点
   *
   * <p>有三种情况
   *
   * <p>1，删除的节点为子节点，将父节点指向当前删除节点的引用改为null
   *
   * <p>2，删除的节点只有一个子节点，需要将上级节点，指向当前节点的下级节点
   *
   * <p>3，删除的节点，存在多个子节点，需要在右子树中找到最小的节点， 将它替换到删除的节点上，然后再删除右子树中找到的最小的节点
   *
   * @param value
   */
  public void delete(int value) {
    // 1,查找当前需要删除的节点
    treeNode findNode = root;
    treeNode findNodeParent = root;

    while (findNode != null) {
      if (findNode.value == value) {
        break;
      }
      // 如果当前的的值，小于查找的值，向左子树查找
      else if (value < findNode.value) {
        findNodeParent = findNode;
        findNode = findNode.left;
      }
      // 如果当前的值，大于查找的值，向右子树查找
      else if (value > findNode.value) {
        findNodeParent = findNode;
        findNode = findNode.right;
      }
    }

    // 如果当前值为空，说明未查找到，需要退出
    if (findNode == null) {
      return;
    }

    // 检查当前删除的节点是否为存在左右两个节点
    // 如果是需要在当前节点的右子树中查找最小的值
    if (findNode.left != null && findNode.right != null) {
      treeNode righMinNode = findNode.right;
      treeNode righMinNodeParent = righMinNode;

      while (righMinNode != null) {
        // 如果左子树不为空，则直接在左子树中查找
        // 二叉搜索树，左边比右边最，最终节点，肯定在左子树中
        if (righMinNode.left != null) {
          righMinNodeParent = righMinNode;
          righMinNode = righMinNode.left;
          continue;
        }

        break;
      }

      // 将删除的节点替换为当前节点
      // 1,因为查找到的节点，可能在左节点，也可能在右节点，所以，需要检查
      setChildTreeNodeValue(findNodeParent, findNode, righMinNode);

      // 将右子树中查找的最小节点，指向为空
      setChildTreeNode(righMinNodeParent, righMinNode, null);
    }

    // 待删除的节点，存在一个节点，可能为左子树，也可能为子树
    else if (findNode.left != null || findNode.right != null) {
      if (findNode.left != null) {
        setChildTreeNode(findNodeParent, findNode, findNode.left);
      } else if (findNode.right != null) {
        setChildTreeNode(findNodeParent, findNode, findNode.right);
      }
    }
    // 如果左右节点都为空，则直接将当前节点在父节点中删除即可
    else {
      setChildTreeNode(findNodeParent, findNode, null);
    }
  }

  /**
   * 进行节点的删除操作
   *
   * @param value 值信息
   */
  public void delete2(int value) {
    // 1,找到删除的节点,及其父节点
    treeNode findNode = root;
    treeNode findNodeParent = findNode;

    while (findNode != null) {
      // 如果查找到直接退出
      if (findNode.value == value) {
        break;
      }
      // 如果当前节点大于查找的值，说明要在左子树查找
      else if (value < findNode.value) {
        findNodeParent = findNode;
        findNode = findNode.left;
      }
      // 如果当前节点小于查找的值，说明要在右子树中查找
      else {
        findNodeParent = findNode;
        findNode = findNode.right;
      }
    }

    // 如果查找的节点为空，说明未查找到节点，直接退出即可
    if (findNode == null) {
      return;
    }

    // 如果当前查找的节点，左子树与右子树都不为空，则需要查找右子树
    if (findNode.left != null && findNode.right != null) {
      treeNode rightMinNode = findNode.right;
      treeNode rightMinNodeParent = rightMinNode;

      // 找到右子树中最小的节点
      while (rightMinNode.left != null) {
        rightMinNodeParent = rightMinNode;
        rightMinNode = rightMinNode.left;
      }

      // 将待删除的值，指向待删除的值
      findNode.value = rightMinNode.value;

      // 将删除的对象指向未节点
      findNodeParent = rightMinNodeParent;
      findNode = rightMinNode;
    }

    // 如果当前存在一个子节点，左子树，或者右子树
    // 找到删除节点的后缀节点
    treeNode findNext;
    if (findNode.left != null) {
      findNext = findNode.left;
    } else if (findNode.right != null) {
      findNext = findNode.right;
    }
    // 后缀节点则为空
    else {
      findNext = null;
    }

    // 将找到的节点挂载到原父节点中
    // 如果原来的爷节点为空，说明删除为根节点,
    if (findNodeParent == null) {
      findNodeParent = findNext;
    }
    // 如果原来左子树为删除的节点，则挂在左子树中
    else if (findNodeParent.left == findNode) {
      findNodeParent.left = findNext;
    }
    // 如果原来右子树为删除的节点，则将后缀节点挂在节点中
    else {
      findNodeParent.right = findNext;
    }
  }

  /**
   * 计算层级的重点于在写出递推公式
   *
   * <p>count(level) = max(count(level.left),count(level.right))
   *
   * @param root
   * @param index
   * @return
   */
  public int getBinaryLevel(treeNode root, int index) {
    if (null == root) {
      return index;
    }

    int maxleftLevel = 0;
    if (root.left != null) {
      maxleftLevel = getBinaryLevel(root.left, index + 1);
    }

    int maxRightLevel = 0;

    if (root.right != null) {
      maxRightLevel = getBinaryLevel(root.right, index + 1);
    }

    return Math.max(maxleftLevel, maxRightLevel) + 1;
  }

  /**
   * 进行子树的节点设置
   *
   * @param findNodeParent 查找到的父节点
   * @param src 源节点
   * @param targetNode 目标节点
   */
  private void setChildTreeNode(treeNode findNodeParent, treeNode src, treeNode targetNode) {
    if (findNodeParent.left == src) {
      findNodeParent.left = targetNode;
    } else if (findNodeParent.right == src) {
      findNodeParent.right = targetNode;
    }
  }

  /**
   * 进行子树的节点设置
   *
   * @param findNodeParent 查找到的父节点
   * @param src 源节点
   * @param targetNode 目标节点
   */
  private void setChildTreeNodeValue(treeNode findNodeParent, treeNode src, treeNode targetNode) {
    if (findNodeParent.left == src) {
      findNodeParent.left.value = targetNode.value;
    } else if (findNodeParent.right == src) {
      findNodeParent.right.value = targetNode.value;
    }
  }

  /**
   * 按层级对节点进行分组
   *
   * @param node
   * @param nodeMap
   */
  public void groupLevelNode(treeNode node, Map<Integer, List<treeNode>> nodeMap, int level) {
    if (null == node) {
      return;
    }

    List<treeNode> levelList = nodeMap.get(level);
    if (null == levelList) {
      levelList = new ArrayList<>();
    }
    levelList.add(node);
    nodeMap.put(level, levelList);
    level = level + 1;
    groupLevelNode(node.left, nodeMap, level);
    level = level - 1;
    level = level + 1;
    groupLevelNode(node.right, nodeMap, level);
    level = level - 1;
  }
}
