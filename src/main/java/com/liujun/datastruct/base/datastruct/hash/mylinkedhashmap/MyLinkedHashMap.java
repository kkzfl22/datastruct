package com.liujun.datastruct.base.datastruct.hash.mylinkedhashmap;

import java.util.Iterator;
import java.util.Objects;

/**
 * 自己实现一个linkedhashmap
 *
 * <p>1. 插入时，检查数据是否已经存在，已经存在，则删除将数据移动至末尾。否则直接移动至末尾。
 *
 * <p>2. 获取一个数据时，移动至末尾。
 *
 * <p>3，删除时默认从队头做删除。
 *
 * @author liujun
 * @version 0.0.1
 */
public class MyLinkedHashMap<K, V> {

  /** 默认哈希表的大小 */
  private static final int DEFAULT_HASH_SIZE = 16;

  /** 默认限制16 */
  private static final int DEFAULT_SIZE = 16;

  /** 当前数据存储的节点 */
  private LinkedNode[] data;

  /** 链表的头节点 */
  private LinkedNode root = new LinkedNode();

  /** 最后的节点,默认为root */
  private LinkedNode last = root;

  /** 容量信息 */
  private final int capacity;

  /** 链表大小 */
  private final int linkedSize;

  /** 当前容量的大小 */
  private int size;

  public MyLinkedHashMap() {
    this(DEFAULT_HASH_SIZE, DEFAULT_SIZE);
  }

  public MyLinkedHashMap(int capacity, int linkedSize) {
    this.capacity = capacity;
    this.linkedSize = linkedSize;
    this.data = new LinkedNode[this.capacity];
  }

  public void put(K key, V value) {

    // 如果当前节点未超过规定的大小，则加入节点
    if (size < linkedSize) {
      this.addNode(key, value);
      size++;
    }
    // 如果超过大小，则大头节点移除一个，然后加入一个节点
    else {
      // 移除头节点，将节点加入尾部
      LinkedNode<K, V> firstNode = root.next;
      LinkedNode<K, V> nextNode = firstNode.next;
      root.next = nextNode;
      nextNode.prev = root;

      // 将数据加入散列列和双向链表中
      addNode(key, value);
    }
  }

  private void addNode(K key, V value) {
    LinkedNode<K, V> node = this.newNode(key, value);
    if (data[node.hashIndex] == null) {
      data[node.hashIndex] = new LinkedNode();
      data[node.hashIndex].hnext = node;
    } else {
      LinkedNode tmpRoot = data[node.hashIndex];
      while (tmpRoot.hnext != null) {
        // 不能为链表的根节点
        if (tmpRoot != data[node.hashIndex] && tmpRoot.key.equals(key)) {
          tmpRoot.value = value;
          break;
        }
        tmpRoot = tmpRoot.hnext;
      }
      if (tmpRoot.hnext == null) {
        tmpRoot.hnext = node;
      }
    }

    // 进行拉链表的写入
    node.prev = last;
    last.next = node;

    last = node;
  }

  /**
   * 新节点信息
   *
   * @param key
   * @param value
   * @return
   */
  private LinkedNode<K, V> newNode(K key, V value) {
    LinkedNode<K, V> node = new LinkedNode();
    node.key = key;
    node.value = value;
    // 将记录的位置写入至链表中
    node.hashIndex = hashIndex(key, capacity);

    return node;
  }

  private int hashIndex(K key, int capacity) {
    int hashCode = Objects.hashCode(key);
    return hashCode % capacity;
  }

  public V get(K key) {

    // 查到到数据
    LinkedNode<K, V> result = this.findPrevData(key);

    if (null == result) {
      return null;
    }

    // 当数据被查找到后，从链表中移除节点,然后,将数据放入到链表的尾部
    LinkedNode<K, V> dataNode = result.hnext;
    // 前驱指针修改
    dataNode.prev.next = dataNode.next;
    // 后缀指针修改
    dataNode.next.prev = dataNode.prev;

    // 节点加入尾部
    last.next = dataNode;
    dataNode.prev = last;
    dataNode.next = null;
    last = dataNode;

    return dataNode.value;
  }

  /**
   * 查找前驱指针
   *
   * @param key
   * @return
   */
  private LinkedNode<K, V> findPrevData(K key) {
    int hashIndex = this.hashIndex(key, capacity);
    LinkedNode value = data[hashIndex];

    LinkedNode<K, V> result = null;

    if (value == null) {
      return null;
    } else {
      LinkedNode<K, V> rootTmp = value;
      while (rootTmp.hnext != null) {
        if (rootTmp.hnext.key.equals(key)) {
          result = rootTmp;
          break;
        }
        rootTmp = rootTmp.hnext;
      }
    }
    return result;
  }

  /** 数据删除操作 */
  public void remove(K key) {
    // 查到到数据
    LinkedNode<K, V> resultPrev = this.findPrevData(key);

    if (null == resultPrev) {
      return;
    }

    LinkedNode<K, V> node = resultPrev.hnext;

    // 移除在散列表中拉链的节点
    resultPrev.hnext = node.hnext;
    // 前驱指针
    node.prev.next = node.next;
    // 后缀指针修改
    node.next.prev = node.prev;
  }

  /** 用于进行迭代 */
  public class MyLinkedIterator<K, V> implements Iterator<K> {

    /** 当前节点信息 */
    private LinkedNode<K, V> currNode;

    public MyLinkedIterator(LinkedNode<K, V> currNode) {
      this.currNode = currNode;
    }

    @Override
    public boolean hasNext() {
      return currNode != null;
    }

    @Override
    public K next() {
      K result = currNode.key;
      currNode = currNode.next;
      return result;
    }
  }

  public Iterator<K> iterator() {
    return new MyLinkedIterator(root.next);
  }

  /** 存储的链表的节点信息 */
  private class LinkedNode<K, V> {

    /** key的信息 */
    protected K key;

    /** 当前的值 */
    protected V value;

    /** 用于对 */
    protected LinkedNode<K, V> hnext;

    /** 计算的索引位置 */
    protected int hashIndex;

    /** 前驱指针 */
    private LinkedNode<K, V> prev;

    /** 后续指针 */
    private LinkedNode<K, V> next;
  }
}
