package com.liujun.datastruct.advanced.bplusTree.codedemo;

import java.util.*;

public class BPlusTree<K extends Comparable<? super K>, V> {

  public static enum RangePolicy {
    EXCLUSIVE,
    INCLUSIVE
  }

  /** The branching factor used when none specified in constructor. */
  private static final int DEFAULT_BRANCHING_FACTOR = 128;

  /**
   * The branching factor for the B+ tree, that measures the capacity of nodes (i.e., the number of
   * children nodes) for internal nodes in the tree.
   */
  private int branchingFactor;

  /** The root node of the B+ tree. */
  private Node root;

  public BPlusTree() {
    this(DEFAULT_BRANCHING_FACTOR);
  }

  public BPlusTree(int branchingFactor) {
    if (branchingFactor <= 2)
      throw new IllegalArgumentException("Illegal branching factor: " + branchingFactor);
    this.branchingFactor = branchingFactor;
    root = new LeafNode();
  }

  /**
   * Returns the value to which the specified key is associated, or {@code null} if this tree
   * contains no association for the key.
   *
   * <p>A return value of {@code null} does not <i>necessarily</i> indicate that the tree contains
   * no association for the key; it's also possible that the tree explicitly associates the key to
   * {@code null}.
   *
   * @param key the key whose associated value is to be returned
   * @return the value to which the specified key is associated, or {@code null} if this tree
   *     contains no association for the key
   */
  public V search(K key) {
    return root.getValue(key);
  }

  /**
   * Returns the values associated with the keys specified by the range: {@code key1} and {@code
   * key2}.
   *
   * @param key1 the start key of the range
   * @param policy1 the range policy, {@link RangePolicy#EXCLUSIVE} or {@link RangePolicy#INCLUSIVE}
   * @param key2 the end end of the range
   * @param policy2 the range policy, {@link RangePolicy#EXCLUSIVE} or {@link RangePolicy#INCLUSIVE}
   * @return the values associated with the keys specified by the range: {@code key1} and {@code
   *     key2}
   */
  public List<V> searchRange(K key1, RangePolicy policy1, K key2, RangePolicy policy2) {
    return root.getRange(key1, policy1, key2, policy2);
  }

  /**
   * Associates the specified value with the specified key in this tree. If the tree previously
   * contained a association for the key, the old value is replaced.
   *
   * @param key the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   */
  public void insert(K key, V value) {
    root.insertValue(key, value);
  }

  /**
   * Removes the association for the specified key from this tree if present.
   *
   * @param key the key whose association is to be removed from the tree
   */
  public void delete(K key) {
    root.deleteValue(key);
  }

  public String toString() {
    Queue<List<Node>> queue = new LinkedList<List<Node>>();
    queue.add(Arrays.asList(root));
    StringBuilder sb = new StringBuilder();
    while (!queue.isEmpty()) {
      Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
      while (!queue.isEmpty()) {
        List<Node> nodes = queue.remove();
        sb.append('{');
        Iterator<Node> it = nodes.iterator();
        while (it.hasNext()) {
          Node node = it.next();
          sb.append(node.toString());
          if (it.hasNext()) sb.append(", ");
          if (node instanceof BPlusTree.InternalNode) nextQueue.add(((InternalNode) node).children);
        }
        sb.append('}');
        if (!queue.isEmpty()) sb.append(", ");
        else sb.append('\n');
      }
      queue = nextQueue;
    }

    return sb.toString();
  }

  private abstract class Node {
    /** 索引的键信息 */
    List<K> keys;

    /**
     * 当前索引键的元素大小
     *
     * @return
     */
    int keyNumber() {
      return keys.size();
    }

    /**
     * 获取值信息
     *
     * @param key
     * @return
     */
    abstract V getValue(K key);

    /**
     * 删除数据的方法
     *
     * @param key
     */
    abstract void deleteValue(K key);

    /**
     * 插入数据的方法
     *
     * @param key
     * @param value
     */
    abstract void insertValue(K key, V value);

    /**
     * 获取存储数据节点的首个节点信息
     *
     * @return 首个元素的节点信息
     */
    abstract K getFirstLeafKey();

    abstract List<V> getRange(K key1, RangePolicy policy1, K key2, RangePolicy policy2);

    abstract void merge(Node sibling);

    /**
     * 拆分的抽象方法
     *
     * @return 新的节点信息
     */
    abstract Node split();

    /**
     * 是否超过B+树的限制 最低m/2,最大m-1
     *
     * @return
     */
    abstract boolean isOverflow();

    abstract boolean isUnderflow();

    public String toString() {
      return keys.toString();
    }
  }

  private class InternalNode extends Node {

    /** 索引层中的子节点信息 */
    List<Node> children;

    InternalNode() {
      this.keys = new ArrayList<K>();
      this.children = new ArrayList<Node>();
    }

    @Override
    V getValue(K key) {
      return getChild(key).getValue(key);
    }

    @Override
    void deleteValue(K key) {
      Node child = getChild(key);
      child.deleteValue(key);
      if (child.isUnderflow()) {
        Node childLeftSibling = getChildLeftSibling(key);
        Node childRightSibling = getChildRightSibling(key);
        Node left = childLeftSibling != null ? childLeftSibling : child;
        Node right = childLeftSibling != null ? child : childRightSibling;
        left.merge(right);
        deleteChild(right.getFirstLeafKey());
        if (left.isOverflow()) {
          Node sibling = left.split();
          insertChild(sibling.getFirstLeafKey(), sibling);
        }
        if (root.keyNumber() == 0) root = left;
      }
    }

    @Override
    void insertValue(K key, V value) {
      // 1,获取当前节点的子节点
      Node child = getChild(key);
      // 根据索引节点下推找到要插入数据的节点
      child.insertValue(key, value);
      // 检查子节点是否超过了限制，最低m/2,最大m-1
      if (child.isOverflow()) {
        // 进行子节点的分裂操作,即数据节点的分裂操作
        Node sibling = child.split();
        // 针对索引层就是插入首个节点
        insertChild(sibling.getFirstLeafKey(), sibling);
      }
      // 检查索根节点是否需要分裂,即超过了m人大小，就需要分裂
      if (root.isOverflow()) {
        // 首先分裂出节点
        Node sibling = split();
        InternalNode newRoot = new InternalNode();
        newRoot.keys.add(sibling.getFirstLeafKey());
        newRoot.children.add(this);
        newRoot.children.add(sibling);
        root = newRoot;
      }
    }

    @Override
    K getFirstLeafKey() {
      return children.get(0).getFirstLeafKey();
    }

    @Override
    List<V> getRange(K key1, RangePolicy policy1, K key2, RangePolicy policy2) {
      return getChild(key1).getRange(key1, policy1, key2, policy2);
    }

    @Override
    void merge(Node sibling) {
      @SuppressWarnings("unchecked")
      InternalNode node = (InternalNode) sibling;
      keys.add(node.getFirstLeafKey());
      keys.addAll(node.keys);
      children.addAll(node.children);
    }

    @Override
    Node split() {
      int from = keyNumber() / 2 + 1, to = keyNumber();
      InternalNode sibling = new InternalNode();

      // 将链值进行切分
      List<Node> childrenNodeList = children.subList(from, to + 1);

      sibling.keys.addAll(keys.subList(from, to));
      sibling.children.addAll(childrenNodeList);

      keys.subList(from - 1, to).clear();
      childrenNodeList.clear();

      return sibling;
    }

    @Override
    boolean isOverflow() {
      return children.size() > branchingFactor;
    }

    @Override
    boolean isUnderflow() {
      return children.size() < (branchingFactor + 1) / 2;
    }

    Node getChild(K key) {
      // 查找到当前节点插入的位置,如果当前节点存在，返回位置，不存在在，则返回插入位置
      // 因为key仅存储首个
      int loc = Collections.binarySearch(keys, key);
      // 如果查找到位置，
      int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
      return children.get(childIndex);
    }

    void deleteChild(K key) {
      int loc = Collections.binarySearch(keys, key);
      if (loc >= 0) {
        keys.remove(loc);
        children.remove(loc + 1);
      }
    }

    void insertChild(K key, Node child) {
      // 1,查询索引层key的位置
      int loc = Collections.binarySearch(keys, key);
      // 2,插入索引所在的位置
      int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
      if (loc >= 0) {
        children.set(childIndex, child);
      } else {
        keys.add(childIndex, key);
        children.add(childIndex + 1, child);
      }
    }

    Node getChildLeftSibling(K key) {
      int loc = Collections.binarySearch(keys, key);
      int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
      if (childIndex > 0) return children.get(childIndex - 1);

      return null;
    }

    Node getChildRightSibling(K key) {
      int loc = Collections.binarySearch(keys, key);
      int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
      if (childIndex < keyNumber()) return children.get(childIndex + 1);

      return null;
    }
  }

  private class LeafNode extends Node {

    /** 当前存储的值的信息 */
    List<V> values;

    LeafNode next;

    LeafNode() {
      keys = new ArrayList<K>();
      values = new ArrayList<V>();
    }

    @Override
    V getValue(K key) {
      int loc = Collections.binarySearch(keys, key);
      return loc >= 0 ? values.get(loc) : null;
    }

    @Override
    void deleteValue(K key) {
      int loc = Collections.binarySearch(keys, key);
      if (loc >= 0) {
        keys.remove(loc);
        values.remove(loc);
      }
    }

    @Override
    void insertValue(K key, V value) {
      // 查找key所在的位置，如果存在，返回索引，不存在，返回插入位置, (-(插入点) - 1)
      int loc = Collections.binarySearch(keys, key);
      int valueIndex = loc >= 0 ? loc : -loc - 1;
      // 如果当前插入的元素已经存在，则直接设置值
      if (loc >= 0) {
        values.set(valueIndex, value);
      }
      // 如果当前不存在，则按照指定的位置，插入数据
      else {
        // 先插索引位置
        keys.add(valueIndex, key);
        // 再将值插入到结果中
        values.add(valueIndex, value);
      }
      // 检查当前节点的个数，是否超过了，B+树中个数的最大限制，
      // 如果当前超过了限制，则开始拆分
      // 当发生第一次分裂后，则执行索引节点的相关操作
      if (root.isOverflow()) {
        // 拆分出数据子节点
        Node sibling = split();
        // 创建索引节点
        InternalNode newRoot = new InternalNode();
        // 将新节点的首节点作为索引节点的第一节点
        newRoot.keys.add(sibling.getFirstLeafKey());
        // 将当前子节点加入到索引节点中
        newRoot.children.add(this);
        // 将分裂的节点也加入节点中
        newRoot.children.add(sibling);
        // 设置新的节点为root节点
        root = newRoot;
      }
    }

    @Override
    K getFirstLeafKey() {
      return keys.get(0);
    }

    @Override
    List<V> getRange(K key1, RangePolicy policy1, K key2, RangePolicy policy2) {
      List<V> result = new LinkedList<V>();
      LeafNode node = this;
      while (node != null) {
        Iterator<K> kIt = node.keys.iterator();
        Iterator<V> vIt = node.values.iterator();
        while (kIt.hasNext()) {
          K key = kIt.next();
          V value = vIt.next();
          int cmp1 = key.compareTo(key1);
          int cmp2 = key.compareTo(key2);
          if (((policy1 == RangePolicy.EXCLUSIVE && cmp1 > 0)
                  || (policy1 == RangePolicy.INCLUSIVE && cmp1 >= 0))
              && ((policy2 == RangePolicy.EXCLUSIVE && cmp2 < 0)
                  || (policy2 == RangePolicy.INCLUSIVE && cmp2 <= 0))) result.add(value);
          else if ((policy2 == RangePolicy.EXCLUSIVE && cmp2 >= 0)
              || (policy2 == RangePolicy.INCLUSIVE && cmp2 > 0)) return result;
        }
        node = node.next;
      }
      return result;
    }

    @Override
    void merge(Node sibling) {
      @SuppressWarnings("unchecked")
      LeafNode node = (LeafNode) sibling;
      keys.addAll(node.keys);
      values.addAll(node.values);
      next = node.next;
    }

    @Override
    Node split() {
      // 声明一个新的节点
      LeafNode sibling = new LeafNode();

      // 开始拆分在起始为m/2,至m
      int from = (keyNumber() + 1) / 2, to = keyNumber();

      List<K> subKeys = keys.subList(from, to);
      List<V> valuesList = values.subList(from, to);

      // 将数据与索引拆分到数据中
      sibling.keys.addAll(subKeys);
      sibling.values.addAll(valuesList);

      // 清空临时数据区
      subKeys.clear();
      valuesList.clear();

      subKeys = null;
      valuesList = null;

      // 更新下一个节点的指向
      sibling.next = next;
      next = sibling;
      return sibling;
    }

    @Override
    boolean isOverflow() {
      return values.size() > branchingFactor - 1;
    }

    @Override
    boolean isUnderflow() {
      return values.size() < branchingFactor / 2;
    }
  }
}
