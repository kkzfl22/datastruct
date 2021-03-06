package com.liujun.datastruct.base.datastruct.hash.myhashmap;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 自己实现动态数据结构hashMap
 *
 * <p>特征： 1. 支持动态扩容。 每次以2倍大小进行扩容。
 *
 * <p>2. 初始化大小为16
 *
 * <p>3。 装载因子为0.75
 *
 * <p>4. 键冲突时，在8个以内，使用链表法，当超过8个时，使用跳表。以保证logn的时间复杂度的操作。
 *
 * <p>5. 当数据删除不足8个时，则切换为链表存储
 *
 * <p>6. 当数据数据搬移使用分批搬移的策略，将搬移的压力分担到多次操作中。
 *
 * @author liujun
 * @version 0.0.1
 */
public class MyHashMap<K, V> {

  /** 初始化默认大小 */
  private static final int INIT_SIZE = 16;

  /** 默认装载因子 */
  private static final float INIT_DIVISOR = 0.75f;

  /** 链表法的大小 */
  private static final int LINK_FLAG = 8;

  /** 最少跳表的个数 */
  private static final int SKIP_LINKED_SIZE = 16;

  /** 默认跳表层级 */
  private static final int DEF_MAX_SKIP_LEVEL = 3;

  /** 每次搬移数据的个数 */
  private static final int MOVE_SIZE = INIT_SIZE / 2;

  /**
   * The maximum capacity, used if a higher value is implicitly specified by either of the
   * constructors with arguments. MUST be a power of two <= 1<<30.
   */
  private static final int MAXIMUM_CAPACITY = 1 << 30;

  /** 容量 */
  private int capacity;

  /** 装载因子 */
  private float divisor;

  /** 数据存储信息 */
  private Node[] table;

  /** 新的数据存储 */
  private Node[] newTable;

  /** 当前map的大小 */
  private int size;

  /** 当前数据移动标识 */
  private boolean moveFlag;

  /** 搬移到的索引位置 */
  private int moveIndex;

  /** 占用槽的数量 */
  private int slotNum;

  /** 计算当前阈值 */
  private int threshold;

  /** 跳表的扩张阈值 */
  private int skipThresholdExpand;

  /** 跳表的收缩 */
  private int skipThresholdShrink;

  public MyHashMap() {
    this.capacity = INIT_SIZE;
    this.divisor = INIT_DIVISOR;
    this.init();
  }

  public MyHashMap(int capacity) {
    this.capacity = capacity;
    this.divisor = INIT_DIVISOR;
    this.init();
  }

  /** 初始化 */
  private void init() {
    this.table = new Node[capacity];
    // 计算当前的阈值
    threshold = (int) (capacity * divisor);
  }

  /**
   * 数据存入
   *
   * @param key 键信息
   * @param value 值信息
   */
  public void put(K key, V value) {

    // 检查占用的槽数
    if (slotNum > threshold) {
      // 执行扩容
      newTable();
      moveIndex = 0;
      moveFlag = true;
    }

    int hashCode = hash(key);
    // 计算hash位置
    int hashIndex = hashIndex(hashCode, capacity);

    // 如果当前容器为空，说明首次插入
    if (null == table[hashIndex]) {
      table[hashIndex] = new Node(null, null);
      table[hashIndex].next = newLinkedNode(key, value, hashCode);
      table[hashIndex].size++;
      slotNum++;
    }

    // 将数据插入至容器中
    nodeInsert(table, key, value, hashCode);

    // 执行部分数据的搬移。
    if (moveFlag) {
      // 分批搬移数据，每次按固定大小8个进行移动操作
      moveData(newTable, table, MOVE_SIZE);
    }
    // 容量的更新
    size++;
  }

  /**
   * 创建新节点
   *
   * @param key
   * @param value
   * @param hashCode
   * @return
   */
  private Node newLinkedNode(K key, V value, int hashCode) {
    // 构建当前对象节点信息
    Node currInsert = new Node(key, value);
    currInsert.hnext = hashCode;
    return currInsert;
  }

  /** 新的hash表 */
  private void newTable() {
    capacity = capacity * 2;
    newTable = new Node[capacity];
    // 将新集合与老集合位置互换
    Node[] dataTmp = table;
    table = newTable;
    newTable = dataTmp;
    // 计算当前的阈值
    threshold = (int) (capacity * divisor);
  }

  /**
   * 数据搬移
   *
   * @param src 原始数据
   * @param target 目标数据
   * @param size 大小
   */
  private void moveData(Node[] src, Node[] target, int size) {

    if (null == src || moveIndex >= src.length) {
      moveFlag = false;
      return;
    }

    // 执行数据的搬移操作
    int moveIndexTmp = 0;
    while (moveIndexTmp < size && moveFlag) {
      Node<K, V> stage = src[moveIndex];

      // 老数据模式为链表存储
      if (stage instanceof Node) {
        Node<K, V> node = stage.next;

        // 进行数据的移动操作
        if (node != null) {
          // 将老数据插入到新的容器中
          nodeInsert(target, node.key, node.value, node.hnext);
          // 旧数据的移除操作
          stage.next = node.next;
          stage.size--;
        }

        // 检查数据项
        if (stage.next == null) {
          moveIndex++;
        }
      }
      // 老数据模式为跳表存储
      else if (stage instanceof SkipNode) {
        SkipNode<K, V> skipNode = (SkipNode) stage;
        SkipNode<K, V> nodeTmp = skipNode.forward[0];

        if (nodeTmp != null) {
          // 将老数据插入至新的容器中
          nodeInsert(target, nodeTmp.key, nodeTmp.value, nodeTmp.hnext);
          // 进行老数据的移除操作
          skipRemove(nodeTmp, skipNode);
          stage.size--;
        }

        if (((SkipNode) stage).forward[0] == null) {
          moveIndex++;
        }
      }

      if (moveIndex >= src.length) {
        moveFlag = false;
      }

      moveIndexTmp++;
    }
  }

  /**
   * 节点插入
   *
   * @param nodes 容器
   * @param key 插入的key
   * @param value 值信息
   */
  private void nodeInsert(Node[] nodes, K key, V value, int hashCode) {
    int nodeIndex = hashIndex(hashCode, nodes.length);
    // 如果为空节点，则进行创建根节点
    if (null == nodes[nodeIndex]) {
      nodes[nodeIndex] = newLinkedNode(key, value, hashCode);
      slotNum++;
    }

    // 当在规则数值内，执行链表法冲突解决
    if (nodes[nodeIndex].size < LINK_FLAG && nodes[nodeIndex] instanceof Node) {
      // 1,检查当前key是否存在
      Node nodeTmp = nodes[nodeIndex];
      while (nodeTmp.next != null) {
        if (nodeTmp.next.key.equals(key)) {
          break;
        }
        nodeTmp = nodeTmp.next;
      }

      // 如果为空说明，都不存在，直接在尾部插入即可
      if (nodeTmp != null) {
        // 构建当前对象节点信息
        nodeTmp.next = newLinkedNode(key, value, hashCode);
        nodes[nodeIndex].size++;
      }
      // 当前不为空，说明已经存在在相同的key,直接替换即可
      else {
        nodeTmp.value = value;
      }
    }
    // 当超过后，则启用跳表
    else {
      // 将数据存储至跳表中,权首次需要，最后都不需要
      if (nodes[nodeIndex] instanceof Node && nodes[nodeIndex].size == LINK_FLAG) {
        // 计算当前的扩容点
        skipThresholdExpand = tableSizeFor(nodes[nodeIndex].size + 2);
        // 计算缩容点
        skipThresholdShrink = tableSizeFor(nodes[nodeIndex].size - 2);

        SkipNode skipRoot = linkedToSkipList(nodes[nodeIndex]);
        nodes[nodeIndex] = skipRoot;
      }

      // 检查当跳表的节点个数到达2的幂次方时，进行跳表节点层数的修改
      if (nodes[nodeIndex].size >= skipThresholdExpand) {
        // 计算跳表的层级
        int countPow = powTwo(nodes[nodeIndex].size);
        if (countPow != -1 && DEF_MAX_SKIP_LEVEL < countPow) {
          // 仅在首次链表转跳表时进行数据搬移
          SkipNode root = new SkipNode(countPow, null, null);
          SkipNode oldRoot = (SkipNode) nodes[nodeIndex];
          // 执行根节点的下数据的转移
          for (int i = oldRoot.currLevel - 1; i >= 0; i--) {
            root.forward[i] = oldRoot.forward[i];
          }
          root.size = oldRoot.size;
          nodes[nodeIndex] = root;
          oldRoot = null;
          // 计算增加层级的阈值
          skipThresholdExpand = tableSizeFor(nodes[nodeIndex].size + 2);
          // 计算减少层级的阈值
          skipThresholdShrink = tableSizeFor(nodes[nodeIndex].size - 2);
        }
      }

      SkipNode root = (SkipNode) nodes[nodeIndex];
      // 将数据插入跳表
      skipAdd(root, key, value, hashCode);
      nodes[nodeIndex].size++;
    }
  }

  /** Returns a power of two size for the given target capacity. */
  static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
  }

  /**
   * 检查当前是否为2的幂
   *
   * @param size
   * @return
   */
  public int powTwo(int size) {
    int powNum = 1;
    int low = 0, mid, high = size;

    while (low <= high && high >= 4) {
      if ((high - low) % 2 != 0) {
        return -1;
      }

      mid = low + (high - low) / 2;
      if (mid % 2 == 0) {
        powNum++;
        high = mid;
      }
      // 如果当前不能被2整除，则说明当前非2的幂
      else {
        return -1;
      }
    }

    return powNum;
  }

  /**
   * 计算hash值
   *
   * @param hasCode 哈希码
   * @param capacity 容量
   * @return 索引位置
   */
  private int hashIndex(int hasCode, int capacity) {
    return hasCode % capacity;
  }

  private int hash(K key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
  }

  /**
   * 获取数据的操作
   *
   * @param key 当前的链信息
   * @return 结果
   */
  public V get(K key) {

    int hashCode = hash(key);
    // 优先在旧的哈希表中搜索
    V value = containerGet(table, key, hashCode);

    // 如果没有找到，再到新的map中搜索
    if (null == value) {
      value = containerGet(newTable, key, hashCode);
    }

    return value;
  }

  /**
   * 在指定的容器中搜索
   *
   * @param searchData 容器对象
   * @param key key信息
   * @return
   */
  private V containerGet(Node[] searchData, K key, int hashCode) {
    if (null == searchData) {
      return null;
    }

    // capacity
    int insertIndex = hashIndex(hashCode, searchData.length);

    // 如果为跳表节点，优先在跳表中搜索
    if (searchData[insertIndex] instanceof SkipNode) {
      SkipNode<K, V> searchNode = skipGet((SkipNode) searchData[insertIndex], key);
      if (null != searchNode) {
        return searchNode.value;
      }
    }
    // 如果数据还在链表中，遍历链表
    else if (searchData[insertIndex] instanceof Node) {
      Node<K, V> node = searchData[insertIndex].next;
      while (node != null) {
        if (node.key.equals(key)) {
          return node.value;
        }
        node = node.next;
      }
    }

    return null;
  }

  /**
   * 进行数据的删除操作，当低于分界线时，切换为链表
   *
   * @param key
   */
  public void remove(K key) {

    int hashCode = hash(key);
    // 在旧容器中移除
    boolean removeFlag = nodeRemove(table, key, hashCode);

    // 当删除失败时，则进行尝试删除
    if (!removeFlag) {
      // 在新容器中移除
      nodeRemove(newTable, key, hashCode);
    }
    // 跳表切换为链表
    this.skipListMoveToLinked(hashCode);
  }

  /**
   * 将跳表切换为链表
   *
   * @param hashCode 索引信息
   */
  private void skipListMoveToLinked(int hashCode) {
    // 当小于8个顶点时，则切换为链表法
    int insertIndex = hashIndex(hashCode, table.length);
    if (table[insertIndex] instanceof SkipNode && table[insertIndex].size < LINK_FLAG) {
      // 执行跳表的迭代操作
      SkipNode tmpNode = ((SkipNode) table[insertIndex]).forward[0];
      Node linkedRoot = new Node(null, null);
      Node tmpNodeItem = linkedRoot;
      while (tmpNode != null) {
        Node linkNode = new Node(tmpNode.key, tmpNode.value);
        tmpNodeItem.next = linkNode;
        tmpNodeItem = linkNode;
        // 跳表中节点的移除操作
        this.skipRemove(tmpNode, (SkipNode) table[insertIndex]);
        tmpNode = tmpNode.forward[0];
      }
      // 设置根节点
      table[insertIndex] = linkedRoot;
    }
    // 当跳表节点减少时，也需要减少跳表的层数
    else if (table[insertIndex] instanceof SkipNode && table[insertIndex].size > SKIP_LINKED_SIZE) {
      // 检查当跳表的节点个数到达2的幂次方时，进行跳表节点层数的修改
      if (table[insertIndex].size <= skipThresholdShrink) {
        int countPow = powTwo(table[insertIndex].size);
        SkipNode oldRoot = (SkipNode) table[insertIndex];
        // 节点数减少时，进行相应层的减少
        if (countPow != -1 && countPow < oldRoot.currLevel) {
          // 仅在首次链表转跳表时进行数据搬移
          SkipNode root = new SkipNode(countPow, null, null);
          // 执行根节点的下数据的转移
          for (int i = root.currLevel - 1; i >= 0; i--) {
            root.forward[i] = oldRoot.forward[i];
          }
          root.size = oldRoot.size;
          table[insertIndex] = root;
          oldRoot = null;

          // 计算当前的扩容点
          skipThresholdExpand = tableSizeFor(table[insertIndex].size + 2);
          // 计算缩容点
          skipThresholdShrink = tableSizeFor(table[insertIndex].size - 2);
        }
      }
    }
  }

  /**
   * 在指定的容器中搜索
   *
   * @param searchData 容器对象
   * @param key key信息
   * @return
   */
  private boolean nodeRemove(Node[] searchData, K key, int hashCode) {
    if (null == searchData) {
      return false;
    }
    boolean removeFlag = false;
    // capacity
    int insertIndex = hashIndex(hashCode, searchData.length);

    // 优先处理跳表
    if (searchData[insertIndex] instanceof SkipNode) {
      removeFlag = skipRemoveKey((SkipNode) searchData[insertIndex], key);
    }
    // 如果数据还在链表中，遍历链表
    else if (searchData[insertIndex] instanceof Node) {
      Node node = searchData[insertIndex];
      while (node != null) {
        if (node.next.key.equals(key)) {
          node.next = node.next.next;
          removeFlag = true;
          break;
        }
        node = node.next;
      }
    }

    if (removeFlag) {
      searchData[insertIndex].size--;
      size--;
    }

    return removeFlag;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public boolean containsKey(K key) {
    V value = this.get(key);
    if (null != value) {
      return true;
    }
    return false;
  }

  public void clean() {
    // 数据清空
    for (int i = 0; i < capacity; i++) {
      this.table[i] = null;
    }

    size = 0;
  }

  /** 进行空检查 */
  public boolean checkEmpty() {
    boolean emptyFlag = true;

    for (int i = 0; i < table.length; i++) {
      if (table[i] instanceof Node) {
        if (table[i].next != null) {
          emptyFlag = false;
        }
      } else if (table[i] instanceof SkipNode) {
        if (((SkipNode) table[i]).forward[0] != null) {
          emptyFlag = false;
        }
      }
    }

    return emptyFlag;
  }

  public int size() {
    return size;
  }

  private class Node<K, V> {
    /** 当前值 */
    protected V value;
    /** 后继指针 */
    protected Node next;
    /** 用于记录下当前的hast值 */
    protected int hnext;
    /** 记录下key用作比较使用 */
    protected K key;
    /** 当前链表法中的节点个数 */
    protected int size;

    public Node(K key, V value) {
      this.value = value;
      this.key = key;
    }
  }

  private class SkipNode<K, V> extends Node<K, V> {
    /** 当前的跳表层级 */
    private int currLevel;
    /** 跳表的层级 */
    private SkipNode<K, V>[] forward;

    public SkipNode(int currLevel, K key, V value) {
      super(key, value);
      this.currLevel = currLevel;
      this.forward = new SkipNode[this.currLevel];
    }
  }

  /**
   * 将数据转化为跳表存储
   *
   * @param data 原链表数据
   * @return 跳表的节点
   */
  private SkipNode linkedToSkipList(Node data) {
    // 仅在首次链表转跳表时进行数据搬移
    SkipNode root = new SkipNode(DEF_MAX_SKIP_LEVEL, null, null);
    root.size = 1;
    Node<K,V> tmpNode = data.next;

    while (tmpNode != null) {
      skipAdd(root, tmpNode.key, tmpNode.value, tmpNode.hnext);
      root.size++;
      tmpNode = tmpNode.next;
    }
    return root;
  }

  /**
   * 向跳表中添加一个数据
   *
   * @param root 节点信息
   * @param key 插入的键
   * @param value 插入的值
   * @param hnext hashcode
   */
  private void skipAdd(SkipNode root, K key, V value, int hnext) {
    int randomLevel = randomLevel(root);
    SkipNode curr = new SkipNode(randomLevel, key, value);
    curr.hnext = hnext;

    // 找到单链表的位置
    SkipNode[] findNode = new SkipNode[randomLevel];
    for (int i = 0; i < randomLevel; i++) {
      findNode[i] = root;
    }
    SkipNode temp = root;
    for (int i = randomLevel - 1; i >= 0; i--) {
      while (temp.forward[i] != null && ((Comparable) temp.forward[i].key).compareTo(key) == -1) {
        temp = temp.forward[i];
      }
      findNode[i] = temp;
    }
    // 将数据插入跳表
    for (int i = 0; i < randomLevel; i++) {
      curr.forward[i] = findNode[i].forward[i];
      findNode[i].forward[i] = curr;
    }
  }

  private int randomLevel(SkipNode root) {
    int level = 1;
    Random random = ThreadLocalRandom.current();
    for (int i = 1; i < root.currLevel; i++) {
      if (random.nextInt() % 2 == 0) {
        level++;
      }
    }

    return level;
  }

  /**
   * 获取一个数据
   *
   * @param key
   * @return
   */
  private SkipNode skipGet(SkipNode stateInfo, K key) {
    SkipNode skipRootTmp = (SkipNode) stateInfo;
    for (int i = skipRootTmp.currLevel - 1; i >= 0; i--) {
      while (skipRootTmp.forward[i] != null
          && ((Comparable) skipRootTmp.forward[i].key).compareTo(key) == -1) {
        skipRootTmp = skipRootTmp.forward[i];
      }
    }

    if (null != skipRootTmp.forward[0] && skipRootTmp.forward[0].key.equals(key)) {
      return skipRootTmp.forward[0];
    }
    return null;
  }

  /**
   * 删除一个数据
   *
   * @param key
   * @return
   */
  private boolean skipRemoveKey(SkipNode skipRootTmp, K key) {
    SkipNode[] deletePrefix = new SkipNode[skipRootTmp.currLevel];
    for (int i = skipRootTmp.currLevel - 1; i >= 0; i--) {
      while (skipRootTmp.forward[i] != null
          && ((Comparable) skipRootTmp.forward[i].key).compareTo(key) == -1) {
        skipRootTmp = skipRootTmp.forward[i];
      }
      deletePrefix[i] = skipRootTmp;
    }

    boolean removeFlag = false;

    // 如果数据被找到，则执行移除操作
    if (null != skipRootTmp && skipRootTmp.forward[0].key.equals(key)) {
      for (int i = deletePrefix.length - 1; i >= 0; i--) {
        if (deletePrefix[i].forward[i] != null
            && ((Comparable) deletePrefix[i].forward[i].key).compareTo(key) == 0) {
          deletePrefix[i].forward[i] = deletePrefix[i].forward[i].forward[i];
          removeFlag = true;
        }
      }
    }
    return removeFlag;
  }

  /**
   * 数据的移除操作
   *
   * @return 移除结果
   */
  private boolean skipRemove(SkipNode node, SkipNode root) {
    SkipNode[] deletePrefix = new SkipNode[root.currLevel];
    SkipNode skipRootTmp = root;

    for (int i = root.currLevel - 1; i >= 0; i--) {
      while (skipRootTmp.forward[i] != null
          && ((Comparable) skipRootTmp.forward[i].key).compareTo(node.key) == -1) {
        skipRootTmp = skipRootTmp.forward[i];
      }
      deletePrefix[i] = skipRootTmp;
    }

    boolean removeFlag = false;

    // 如果数据被找到，则执行移除操作
    if (null != skipRootTmp && skipRootTmp.forward[0].key.equals(node.key)) {
      for (int i = root.currLevel - 1; i >= 0; i--) {
        if (deletePrefix[i].forward[i] != null
            && ((Comparable) deletePrefix[i].forward[i].key).compareTo(node.key) == 0) {
          deletePrefix[i].forward[i] = deletePrefix[i].forward[i].forward[i];
          removeFlag = true;
        }
      }
    }
    return removeFlag;
  }
}
