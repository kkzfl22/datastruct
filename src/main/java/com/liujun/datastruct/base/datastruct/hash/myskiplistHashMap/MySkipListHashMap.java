package com.liujun.datastruct.base.datastruct.hash.myskiplistHashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 使用跳表与散列表结合实现一个数据有序的保存,可做到最好时间复杂度O(1)，最坏情况情况下的时间复杂度为O(logn)
 *
 * @author liujun
 * @version 0.0.1
 */
public class MySkipListHashMap<K, V> {

  /** 默认的散列表大小 */
  private static final int DEFAULT_HASH_TABLE_SIZE = 16;

  /** 扩容的阈值 */
  private static final double DEFAULT_MORE_THRESHOLD = 0.75;

  /** 默认的跳表的高度 */
  private static final int DEFAULT_SKIP_HIGH = 4;

  /** 随机数对象 */
  private static final Random random = ThreadLocalRandom.current();

  /**
   * The maximum capacity, used if a higher value is implicitly specified by either of the
   * constructors with arguments. MUST be a power of two <= 1<<30.
   */
  private static final int MAXIMUM_CAPACITY = 1 << 30;

  /** 每次重新hash数据量 */
  private static final int MOVE_SIZE = 4;

  /** 上次移动到的索引位置 */
  private int dataMoveIndex;

  /** 容量信息 */
  private int capacity;

  /** 扩容的占比 */
  private double moreThreshold;

  /** 扩容的阈值 */
  private int moreThresholdValue;

  /** 存储的链表的信息 */
  private HashSkipNode<K, V>[] tab;

  /** 用于扩容后存储的原始数据信息，做分步迁移 */
  private HashSkipNode<K, V>[] newTab;

  /** 默认的跳表的根节点 */
  private HashSkipNode rootNode = new HashSkipNode(DEFAULT_SKIP_HIGH, -1, null, null);

  /** 当前存入的元素个数 */
  private int size;

  /** 数据搬移标识 */
  private boolean moveFlag;

  /** 跳表扩容阈值 */
  private int skipMoreThreshold;

  /** 跳表缩容的阈值 */
  private int shipReduceThreshold;

  public MySkipListHashMap() {
    this(DEFAULT_HASH_TABLE_SIZE, DEFAULT_MORE_THRESHOLD);
  }

  public MySkipListHashMap(int capacity, double moreThreshold) {
    // 最小保证16个大小
    if (capacity < DEFAULT_HASH_TABLE_SIZE) {
      capacity = DEFAULT_HASH_TABLE_SIZE;
    }
    // 用于确保容量必须中2的n次方
    this.capacity = tableSizeFor(capacity);
    this.moreThreshold = moreThreshold;
    // 计算扩容的阈值,每次扩容后，都需要重新计算此值
    this.moreThresholdValue = (int) (this.capacity * moreThreshold);
    this.tab = new HashSkipNode[this.capacity];

    // 计算跳表增加调度的阈值与减少调度的阈值
    this.skipMoreThreshold = tableSizeFor(this.capacity + 2);
    this.skipMoreThreshold = tableSizeFor(this.capacity - 2);
  }

  /**
   * 跳表的承机高度
   *
   * @param maxHigh 最大高度
   * @return 当前生成的高度
   */
  private static int randomHigh(int maxHigh) {
    int high = 1;
    for (int i = 1; i < maxHigh; i++) {
      if (random.nextInt() % 2 == 0) {
        high++;
      }
    }
    return high;
  }

  /** Returns a power of two size for the given target capacity. */
  public static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
  }

  private int hash(K key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
  }

  /**
   * 放入一个数据
   *
   * @param key
   * @param value
   */
  public void put(K key, V value) {
    this.resize();
    // 1, 将数据存入到散列表中
    int hashcode = hash(key);
    // 取余算法，但必须保证capacity为2的n次方，否则分布将极不均衡
    int sizeIndex = hashcode & (capacity - 1);
    HashSkipNode data = tab[sizeIndex];

    if (data == null) {
      // 1，创建一个根节点
      tab[sizeIndex] = new HashSkipNode(-1, null, null);
      // 创建一级节点
      tab[sizeIndex].hnext = addSkipNode(hashcode, key, value);
    } else {
      // 找到拉链的最后一个节点，将节点加入
      HashSkipNode tmpLastNode = tab[sizeIndex];
      while (tmpLastNode.hnext != null) {
        tmpLastNode = tmpLastNode.hnext;
      }
      // 将最后节点加入
      tmpLastNode.hnext = addSkipNode(hashcode, key, value);
    }

    // 数据搬移操作
    if (moveFlag) {
      hashMove();
    }

    size++;
  }

  /** 执行哈希表的数据重新分布操作 */
  private void hashMove() {
    int moveIndex = 0;

    while (moveIndex < MOVE_SIZE && moveFlag) {
      if (null != newTab[dataMoveIndex] && newTab[dataMoveIndex].hnext != null) {
        HashSkipNode<K, V> hashNode = newTab[dataMoveIndex].hnext;
        // 1, 将数据存入到散列表中
        int hashcode = hash(hashNode.key);
        // 取余算法，但必须保证capacity为2的n次方，否则分布将极不均衡
        int sizeIndex = hashcode & (capacity - 1);

        // 将节点指向下级节点
        newTab[dataMoveIndex].hnext = hashNode.hnext;
        hashNode.hnext = null;

        if (null == tab[sizeIndex]) {
          tab[sizeIndex] = new HashSkipNode(-1, null, null);
          tab[sizeIndex].hnext = hashNode;
        } else {
          // 取得拉链的最后一个位置
          HashSkipNode<K, V> moveNode = tab[sizeIndex];
          while (moveNode.hnext != null) {
            moveNode = moveNode.hnext;
          }
          moveNode.hnext = hashNode;
        }
      } else {
        dataMoveIndex++;
      }

      if (dataMoveIndex >= newTab.length) {
        moveFlag = false;
        newTab = null;
        dataMoveIndex = 0;
      }

      moveIndex++;
    }
  }

  /**
   * 将节点加入到跳表中
   *
   * @param hashCode hash
   * @param key 加入的键
   * @param value 值信息
   * @return 结果
   */
  private HashSkipNode addSkipNode(int hashCode, K key, V value) {
    int high = randomHigh(rootNode.currHigh);
    HashSkipNode node = new HashSkipNode(high, hashCode, key, value);
    node.currHigh = high;

    HashSkipNode[] nodeForward = new HashSkipNode[high];
    HashSkipNode rootNodeTmp = rootNode;

    // 找到跳表的每层的前驱节点
    for (int i = high - 1; i >= 0; i--) {
      while (rootNodeTmp.forward[i] != null
          && ((Comparable) rootNodeTmp.forward[i].value).compareTo(value) == -1) {
        rootNodeTmp = rootNodeTmp.forward[i];
      }
      nodeForward[i] = rootNodeTmp;
    }

    // 将节点加入到跳表中
    for (int i = high - 1; i >= 0; i--) {
      node.forward[i] = nodeForward[i].forward[i];
      nodeForward[i].forward[i] = node;
    }

    return node;
  }

  /** 进行扩容计算 */
  public void resize() {

    // 检查跳表增加高度的阈值
    if (this.skipMoreThreshold == size) {
      // 计算跳表增加调度的阈值与减少调度的阈值
      this.skipMoreThreshold = tableSizeFor(size + 4);
      this.shipReduceThreshold = tableSizeFor(size - 4);
      // 跳表的调度就在原基础上加上1
      this.rootNode.currHigh = this.rootNode.currHigh + 1;
      // 重新计算根节点
      HashSkipNode<K, V> forward[] = new HashSkipNode[this.rootNode.currHigh];
      for (int i = 0; i < this.rootNode.forward.length; i++) {
        forward[i] = this.rootNode.forward[i];
      }
      this.rootNode.forward = forward;
    }

    // 当容量大阈值后，执行扩容操作
    if (size > moreThresholdValue) {
      this.capacity = capacity * 2;
      newTab = new HashSkipNode[this.capacity];
      moveFlag = true;
      HashSkipNode[] tmpValue = tab;
      tab = newTab;
      newTab = tmpValue;

      // 重新计算扩容点
      moreThresholdValue = (int) (this.capacity * moreThreshold);
    }
  }

  /**
   * 获取一个数据
   *
   * @param key
   * @return
   */
  public V get(K key) {
    HashSkipNode<K, V> valueItem = this.getPrevNode(key);
    if (valueItem != null) {
      return valueItem.hnext.value;
    }

    return null;
  }

  /**
   * 执行区间搜索
   *
   * @param start 开始值
   * @param end 结束值
   * @return 当前的结果
   */
  public List<V> scopeGet(V start, V end) {
    // 1,在跳表节点中开始搜索
    HashSkipNode<K, V> rootNodeTmp = rootNode;

    for (int i = rootNode.currHigh - 1; i >= 0; i--) {
      while (rootNodeTmp.forward[i] != null
          && ((Comparable) rootNodeTmp.forward[i].value).compareTo(start) == -1) {
        rootNodeTmp = rootNodeTmp.forward[i];
      }
    }

    if (rootNodeTmp.forward[0] != null
        && ((Comparable) rootNodeTmp.forward[0].value).compareTo(start) == 0) {
      List<V> dataList = new ArrayList<>();

      while (((Comparable) rootNodeTmp.forward[0].value).compareTo(end) == -1) {
        dataList.add(rootNodeTmp.forward[0].value);
        rootNodeTmp = rootNodeTmp.forward[0];
      }

      return dataList;
    }

    return Collections.emptyList();
  }

  /**
   * 获取当前指定值的前驱节点
   *
   * @param key 当前待查询的key
   * @return 节点信息
   */
  private HashSkipNode<K, V> getPrevNode(K key) {
    HashSkipNode<K, V> valueItem = this.getPrevNode(tab, key);
    if (null != valueItem) {
      return valueItem;
    }

    return this.getPrevNode(newTab, key);
  }

  /**
   * 获取当前指定散列表的节点信息
   *
   * @param node
   * @param key
   * @return
   */
  private HashSkipNode<K, V> getPrevNode(HashSkipNode<K, V>[] node, K key) {
    // 1, 将数据存入到散列表中
    int hashcode = hash(key);
    // 取余算法，但必须保证capacity为2的n次方，否则分布将极不均衡
    int sizeIndex = hashcode & (capacity - 1);

    if (sizeIndex >= node.length || null == node[sizeIndex]) {
      return null;
    }

    HashSkipNode<K, V> item = node[sizeIndex];
    while (item != null && item.hnext != null) {
      if (item.hnext.key.equals(key)) {
        return item;
      }
      item = item.hnext;
    }

    return null;
  }

  /**
   * 移除一个数据
   *
   * @param key
   */
  public void remove(K key) {

    // 1,使用hash表找到节点
    HashSkipNode<K, V> valueItem = getPrevNode(key);

    // 在散列表中移除当前节
    if (valueItem == null) {
      return;
    }

    HashSkipNode<K, V> currNode = valueItem.hnext;
    valueItem.hnext = currNode.hnext;
    currNode.hnext = null;

    // 进行跳表节点的删除
    int currHigh = currNode.currHigh;
    if (currHigh >= rootNode.currHigh) {
      currHigh = rootNode.currHigh;
    }

    HashSkipNode<K, V>[] prevNode = new HashSkipNode[currHigh];
    HashSkipNode<K, V> rootNodeTmp = rootNode;
    for (int i = currHigh - 1; i >= 0; i--) {
      while (rootNodeTmp.forward[i] != null
          && ((Comparable) rootNodeTmp.forward[i].value).compareTo(currNode.value) == -1) {
        rootNodeTmp = rootNodeTmp.forward[i];
      }
      prevNode[i] = rootNodeTmp;
    }

    // 进行节点的移除操作
    for (int i = currHigh - 1; i >= 0; i--) {
      prevNode[i].forward[i] = prevNode[i].forward[i].forward[i];
    }

    // 如果到达缩容的阈值,进行跳表节点高度的更新,并保证最少4层跳表
    if (size == shipReduceThreshold && this.rootNode.currHigh > DEFAULT_SKIP_HIGH) {
      // 计算跳表增加调度的阈值与减少调度的阈值
      this.skipMoreThreshold = tableSizeFor(size / 2 + 4);
      this.shipReduceThreshold = tableSizeFor(size / 2 - 4);
      // 跳表的调度就在原基础上加上1
      this.rootNode.currHigh = this.rootNode.currHigh - 1;
      // 重新计算根节点
      HashSkipNode<K, V> forward[] = new HashSkipNode[this.rootNode.currHigh];
      for (int i = 0; i < this.rootNode.currHigh; i++) {
        forward[i] = this.rootNode.forward[i];
      }
      this.rootNode.forward = forward;
    }
    size--;
  }

  /** 按值顺序遍历 */
  private class SkipListIterator implements Iterator<V> {

    /** 节点信息 */
    private HashSkipNode<K, V> node;

    public SkipListIterator(HashSkipNode<K, V> node) {
      this.node = node;
    }

    @Override
    public boolean hasNext() {
      return node.forward[0] != null;
    }

    @Override
    public V next() {
      node = this.node.forward[0];

      V value = null;
      if (null != node) {
        value = node.value;
      }

      return value;
    }
  }

  /**
   * 获取迭代器
   *
   * @return
   */
  public Iterator<V> valueIterator() {
    return new SkipListIterator(this.rootNode);
  }

  /** 数据的节点信息 */
  private class HashSkipNode<K, V> {

    /** 数据的key信息 */
    private K key;

    /** 数据的值信息 */
    private V value;

    /** 记录下hashCode; */
    private int hashCode;

    /** 拉链节点 */
    private HashSkipNode<K, V> hnext;

    /** 跳表的级层信息 */
    private HashSkipNode<K, V> forward[];

    /** 当前跳表的高度 */
    private int currHigh;

    public HashSkipNode(int hashCode, K key, V value) {
      this.hashCode = hashCode;
      this.key = key;
      this.value = value;
    }

    public HashSkipNode(int currHigh, int hashCode, K key, V value) {
      this(hashCode, key, value);
      this.currHigh = currHigh;
      this.forward = new HashSkipNode[currHigh];
    }
  }
}
