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
public class MyHashMap {

  /** 初始化默认大小 */
  private static final int INIT_SIZE = 16;

  /** 默认装载因子 */
  private static final double INIT_DIVISOR = 0.75;

  /** 链表法的大小 */
  private static final int LINK_FLAG = 8;

  /** 最少跳表的个数 */
  private static final int SKIP_LINKED_SIZE = 16;

  /** 默认跳表层级 */
  private static final int DEF_MAX_SKIP_LEVEL = 4;

  /** 每次搬移数据的个数 */
  private static final int MOVE_SIZE = INIT_SIZE / 2;

  /** 链表存储 */
  private static final int MODE_LINKED = 1;

  /** 跳表存储 */
  private static final int MODE_SKIP_LIST = 2;

  /** 容量 */
  private int capacity;

  /** 装载因子 */
  private double divisor;

  /** 数据存储信息 */
  private Stage[] table;

  /** 新的数据存储 */
  private Stage[] newTable;

  /** 当前map的大小 */
  private int size;

  /** 槽位占用数 */
  private int stateSize;

  /** 当前数据移动标识 */
  private boolean moveFlag;

  /** 搬移到的索引位置 */
  private int moveIndex;

  public MyHashMap() {
    this.capacity = INIT_SIZE;
    this.divisor = INIT_DIVISOR;
    this.init();
  }

  public MyHashMap(int init) {
    this.capacity = init;
    this.divisor = INIT_DIVISOR;
    this.init();
  }

  /** 初始化 */
  private void init() {
    this.table = new Stage[capacity];
    for (int i = 0; i < capacity; i++) {
      this.table[i] = new Stage();
    }
  }

  /**
   * 数据存入
   *
   * @param key 键信息
   * @param value 值信息
   */
  public void put(Object key, Object value) {
    double currDivisor = ((double) stateSize) / capacity;
    if (currDivisor > divisor) {
      // 执行扩容
      newTable();
      moveIndex = 0;
      moveFlag = true;
    }

    // 将数据插入至容器中
    nodeInsert(table, key, value, capacity);

    // 执行部分数据的搬移。
    if (moveFlag) {
      // 分批搬移数据，每次按固定大小8个进行移动操作
      moveData(newTable, table, MOVE_SIZE);
    }
    // 容量的更新
    size++;
  }

  /** 新的hash表 */
  private void newTable() {
    capacity = capacity * 2;
    newTable = new Stage[capacity];
    for (int i = 0; i < capacity; i++) {
      newTable[i] = new Stage();
    }

    // 将新集合与老集合位置互换
    Stage[] dataTmp = table;
    table = newTable;
    newTable = dataTmp;
  }

  /**
   * 数据搬移
   *
   * @param src 原始数据
   * @param target 目标数据
   * @param size 大小
   */
  private void moveData(Stage[] src, Stage[] target, int size) {

    if (null == src || moveIndex >= src.length) {
      moveFlag = false;
      return;
    }

    // 执行数据的搬移操作
    int moveIndexTmp = 0;
    while (moveIndexTmp < size && moveFlag) {
      Stage stage = src[moveIndex];

      // 老数据模式为链表存储
      if (stage.stateMode == MODE_LINKED) {
        Node node = stage.root.next;

        // 进行数据的移动操作
        if (node != null) {
          // 将老数据插入到新的容器中
          nodeInsert(target, node.key, node.value, target.length);
          // 旧数据的移除操作
          stage.root.next = node.next;
          stage.size--;
        }

        // 检查数据项
        if (stage.root.next == null) {
          moveIndex++;
        }
      }
      // 老数据模式为跳表存储
      else if (stage.stateMode == MODE_SKIP_LIST) {
        SkipNode skipNode = (SkipNode) stage.root;
        SkipNode nodeTmp = skipNode.forward[0];

        if (nodeTmp != null) {
          // 将老数据插入至新的容器中
          nodeInsert(target, nodeTmp.key, nodeTmp.value, target.length);
          // 进行老数据的移除操作
          skipRemove(nodeTmp, skipNode);
          stage.size--;
        }

        if (((SkipNode) stage.root).forward[0] == null) {
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
  private void nodeInsert(Stage[] nodes, Object key, Object value, int capacity) {
    int insertIndex = hashIndex(key, capacity);
    // 当在规则数值内，执行链表法冲突解决
    if (nodes[insertIndex].size < LINK_FLAG && nodes[insertIndex].stateMode == MODE_LINKED) {
      // 1,检查当前key是否存在
      Node nodeTmp = nodes[insertIndex].root;
      while (nodeTmp.next != null) {
        if (nodeTmp.next.key.equals(key)) {
          break;
        }
        nodeTmp = nodeTmp.next;
      }

      // 如果为空说明，都不存在，直接在尾部插入即可
      if (nodeTmp != null) {
        // 构建当前对象节点信息
        Node currInsert = new Node(key, value);
        currInsert.hnext = key.hashCode();
        nodeTmp.next = currInsert;
        nodes[insertIndex].size++;
      }
      // 当前不为空，说明已经存在在相同的key,直接替换即可
      else {
        nodeTmp.value = value;
      }
    }
    // 当超过后，则启用跳表
    else {
      // 将数据存储至跳表中,权首次需要，最后都不需要
      if (nodes[insertIndex].stateMode == MODE_LINKED) {
        linkedToSkipList(nodes[insertIndex]);
      }

      // 检查当跳表的节点个数到达2的幂次方时，进行跳表节点层数的修改
      if (nodes[insertIndex].size % 8 == 0) {
        int countPow = powTow(nodes[insertIndex].size);
        if (countPow != -1 && DEF_MAX_SKIP_LEVEL < countPow) {
          // 仅在首次链表转跳表时进行数据搬移
          SkipNode root = new SkipNode(countPow, null, null);
          SkipNode oldRoot = (SkipNode) nodes[insertIndex].root;
          // 执行根节点的下数据的转移
          for (int i = oldRoot.currLevel - 1; i >= 0; i--) {
            root.forward[i] = oldRoot.forward[i];
          }

          nodes[insertIndex].root = root;
          oldRoot = null;
        }
      }

      SkipNode root = (SkipNode) nodes[insertIndex].root;
      // 将数据插入跳表
      skipAdd(root, key, value, key.hashCode());
      nodes[insertIndex].size++;
    }

    if (nodes[insertIndex].size == 1) {
      stateSize++;
    }
  }

  /**
   * 检查当前是否为2的幂
   *
   * @param size
   * @return
   */
  public int powTow(int size) {
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
   * 计算索引位置
   *
   * @param key hash的key
   * @return 插入的索引位置
   */
  private int hashIndex(Object key, int capacity) {
    int hashCode = key.hashCode();
    int capIndex = hashCode % capacity;



    return capIndex;
  }



  /**
   * 获取数据的操作
   *
   * @param key 当前的链信息
   * @return 结果
   */
  public Object get(Object key) {

    // 优先在旧的哈希表中搜索
    Object value = containerGet(table, key);

    // 如果没有找到，再到新的map中搜索
    if (null == value) {
      value = containerGet(newTable, key);
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
  private Object containerGet(Stage[] searchData, Object key) {
    if (null == searchData) {
      return null;
    }

    // capacity
    int insertIndex = hashIndex(key, searchData.length);
    Node node = searchData[insertIndex].root.next;
    // 如果数据还在链表中，遍历链表
    if (searchData[insertIndex].stateMode == MODE_LINKED) {
      while (node != null) {
        if (node.key.equals(key)) {
          return node.value;
        }
        node = node.next;
      }
    }
    // 当超过8个顶点时，则在跳表中搜索
    else if (searchData[insertIndex].stateMode == MODE_SKIP_LIST) {
      SkipNode searchNode = skipGet(searchData[insertIndex], key);
      if (null != searchNode) {
        return searchNode.value;
      }
    }

    return null;
  }

  /**
   * 进行数据的删除操作，当低于分界线时，切换为链表
   *
   * @param key
   */
  public void remove(Object key) {
    // 在旧容器中移除
    boolean removeFlag = nodeRemove(table, key);

    // 当删除失败时，则进行尝试删除
    if (!removeFlag) {
      // 在新容器中移除
      nodeRemove(newTable, key);
    }
    // 跳表切换为链表
    this.skipListMoveToLinked(key);
  }

  /**
   * 将跳表切换为链表
   *
   * @param key 键信息
   */
  private void skipListMoveToLinked(Object key) {
    // 当小于8个顶点时，则切换为链表法
    int insertIndex = hashIndex(key, table.length);
    if (table[insertIndex].stateMode == MODE_SKIP_LIST && table[insertIndex].size < LINK_FLAG) {
      // 执行跳表的迭代操作
      SkipNode tmpNode = ((SkipNode) table[insertIndex].root).forward[0];
      Node linkedRoot = new Node(null, null);
      Node tmpNodeItem = linkedRoot;
      while (tmpNode != null) {
        Node linkNode = new Node(tmpNode.key, tmpNode.value);
        tmpNodeItem.next = linkNode;
        tmpNodeItem = linkNode;
        // 跳表中节点的移除操作
        this.skipRemove(tmpNode, (SkipNode) table[insertIndex].root);
        tmpNode = tmpNode.forward[0];
      }
      // 设置根节点
      table[insertIndex].root = linkedRoot;
      // 模式切换为链表
      table[insertIndex].stateMode = MODE_LINKED;
    }
    // 当跳表节点减少时，也需要减少跳表的层数
    else if (table[insertIndex].stateMode == MODE_SKIP_LIST
        && table[insertIndex].size > SKIP_LINKED_SIZE) {
      // 检查当跳表的节点个数到达2的幂次方时，进行跳表节点层数的修改
      if (table[insertIndex].size % 8 == 0) {
        int countPow = powTow(table[insertIndex].size);
        SkipNode oldRoot = (SkipNode) table[insertIndex].root;
        // 节点数减少时，进行相应层的减少
        if (countPow != -1 && countPow < oldRoot.currLevel) {
          // 仅在首次链表转跳表时进行数据搬移
          SkipNode root = new SkipNode(countPow, null, null);
          // 执行根节点的下数据的转移
          for (int i = root.currLevel - 1; i >= 0; i--) {
            root.forward[i] = oldRoot.forward[i];
          }

          table[insertIndex].root = root;
          oldRoot = null;
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
  private boolean nodeRemove(Stage[] searchData, Object key) {
    if (null == searchData) {
      return false;
    }
    boolean removeFlag = false;
    // capacity
    int insertIndex = hashIndex(key, searchData.length);

    // 如果数据还在链表中，遍历链表
    if (searchData[insertIndex].stateMode == MODE_LINKED) {
      Node node = searchData[insertIndex].root;
      while (node != null) {
        if (node.next.key.equals(key)) {
          node.next = node.next.next;
          removeFlag = true;
          break;
        }
        node = node.next;
      }
    }
    // 当启用跳表后，则在跳表移除
    else if (searchData[insertIndex].stateMode == MODE_SKIP_LIST) {
      removeFlag = skipRemoveKey(searchData[insertIndex], key);
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

  public boolean containsKey(Object key) {
    Object value = this.get(key);
    if (null != value) {
      return true;
    }
    return false;
  }

  public void clean() {
    // 数据清空
    for (int i = 0; i < capacity; i++) {
      this.table[i].root.next = null;
    }

    size = 0;
  }

  /** 进行空检查 */
  public boolean checkEmpty() {
    boolean emptyFlag = true;

    for (int i = 0; i < table.length; i++) {
      if (table[i].stateMode == MODE_LINKED) {
        if (table[i].root.next != null) {
          emptyFlag = false;
        }
      } else if (table[i].stateMode == MODE_SKIP_LIST) {
        if (((SkipNode) table[i].root).forward[0] != null) {
          emptyFlag = false;
        }
      }
    }

    return emptyFlag;
  }

  public int size() {
    return size;
  }

  private class Stage {
    /** 数据存储 */
    private Node root = new Node(null, null);

    /** 当前大小 */
    private int size;

    /** 存储模式：1:链表，2.跳表 */
    private int stateMode = MODE_LINKED;
  }

  private class Node {
    /** 当前值 */
    protected Object value;
    /** 后继指针 */
    protected Node next;
    /** 用于记录下当前的hast值 */
    protected int hnext;
    /** 记录下key用作比较使用 */
    protected Object key;
    /** 当前链表法中的节点个数 */
    protected int size;

    public Node(Object key, Object value) {
      this.value = value;
      this.key = key;
    }
  }

  private class SkipNode extends Node {
    /** 当前的跳表层级 */
    private int currLevel;
    /** 跳表的层级 */
    private SkipNode[] forward;

    public SkipNode(int currLevel, Object key, Object value) {
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
  private void linkedToSkipList(Stage data) {

    SkipNode root = null;
    Node tmpNode = null;

    // 仅在首次链表转跳表时进行数据搬移
    root = new SkipNode(DEF_MAX_SKIP_LEVEL, null, null);
    data.size = 0;
    // 标识当前启用跳表
    data.stateMode = MODE_SKIP_LIST;
    tmpNode = data.root.next;

    while (tmpNode != null) {
      skipAdd(root, tmpNode.key, tmpNode.value, tmpNode.hnext);
      data.size++;
      tmpNode = tmpNode.next;

      data.root = root;
    }
  }

  /**
   * 向跳表中添加一个数据
   *
   * @param root 节点信息
   * @param key 插入的键
   * @param value 插入的值
   * @param hnext hashcode
   */
  private void skipAdd(SkipNode root, Object key, Object value, int hnext) {
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
  private SkipNode skipGet(Stage stateInfo, Object key) {
    SkipNode skipRootTmp = (SkipNode) stateInfo.root;
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
  private boolean skipRemoveKey(Stage stateInfo, Object key) {
    SkipNode skipRootTmp = (SkipNode) stateInfo.root;
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
