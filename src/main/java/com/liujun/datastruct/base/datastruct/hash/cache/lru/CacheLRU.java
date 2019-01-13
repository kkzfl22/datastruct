package com.liujun.datastruct.base.datastruct.hash.cache.lru;

/**
 * 通过LRU来实现缓存淘汰算法
 *
 * @author liujun
 * @version 0.0.1
 * @date 2018/11/09
 */
public class CacheLRU {

  /** 散列表 */
  private final Data[] hashData;

  /** 头链表 */
  private Data head = new Data();

  /** 尾链表 */
  private Data last = head;

  /** 容量大小 */
  private final int capacity;

  /** 链表的容量 */
  private int size;

  /** 最大的缓存数 */
  private final int MAX_CACHE;

  public CacheLRU(int capacity, int cacheMax) {
    this.capacity = capacity;
    this.hashData = new Data[capacity];
    MAX_CACHE = cacheMax;
  }

  class Data {

    /** 存储的key的信息 */
    private String key;

    /** 存储的value信息 */
    private String value;

    /** 上一个节点 */
    private Data top;

    /** 下一个节点 */
    private Data next;

    /** 链表法中的拉链 */
    private Data hnext;
  }

  public int size() {
    return size;
  }

  /**
   * 放入数据
   *
   * @param key key
   * @param value value
   */
  public void put(String key, String value) {

    int index = hash(key);

    // 首先将数据加入散列的拉链中
    Data data = new Data();
    data.key = key;
    data.value = value;

    if (hashData[index] == null) {
      hashData[index] = new Data();
    }

    // 在散列表中查询节点
    Data findNode = this.findNode(key, index);

    // 1,如果数据被找到，说明说明仅需要更新值即可
    if (null != findNode) {
      findNode.value = value;
    }
    // 未找到说明需要将节点加入链表层
    else {
      // 链表加入有两种情况，
      // 1,情况1，首次散列表中的链为空
      if (hashData[index].hnext == null) {
        hashData[index].hnext = data;
      }
      // 否则，加入队列尾部
      else {
        // 获取尾节点
        Data findLastData = this.getLastNode(index);
        data.hnext = findLastData.hnext;
        findLastData.hnext = data;
      }
    }

    // 2,将数据加入到双向链表中
    this.lruLink(findNode, data);
  }

  /**
   * 进行删除操作
   *
   * @param key 待删除的键的信息
   */
  public void delete(String key) {
    // 1,在散列表中找到待删除的元素
    int index = hash(key);

    Data data = this.findNode(key, index);

    // 如果节点被找到，进行删除操作
    if (null != data) {
      // 1,删除链表中的位置
      // 1。1--查找前驱节点信息
      Data prev = this.findHashPrev(data);
      // 进行删除操作
      prev.hnext = data.hnext;

      // 进行链表中的位置更新
      data.top.next = data.next;
      if (null != data.next) {
        data.next.top = data.top;
      }
      size--;

      // 进行当前节点的清理
      data.next = null;
      data.hnext = null;
      data.top = null;
    }
  }

  /**
   * 最近最少访问的数据链路节点更新
   *
   * @param findNode 查询到的节点
   * @param insertNode 插入的数据节点
   */
  private void lruLink(Data findNode, Data insertNode) {
    // 1,检查数据是否已经在缓存中
    if (null != findNode) {
      // 在缓存中，从队列头移除，放入队尾
      findNode.top = findNode.next;
      last.next = insertNode;
      insertNode.top = last;
      last = insertNode;
    }

    // 2,不在队列中，检查队列是否已经满了
    else {
      // 如果已经满了，多队头移除一个，加入队中
      if (size >= MAX_CACHE) {

        // 找到前驱节点
        Data hashPrev = findHashPrev(head.next);

        // 找到前驱节点后，直接删除节点
        if (null != hashPrev) {
          hashPrev.hnext = head.next.hnext;
        }

        if (head.next != null) {
          head.next = head.next.next;
          head.next.top = head;
          size--;
        }

        last.next = insertNode;
        insertNode.top = last;
        last = insertNode;
        size++;

      }
      // 3,如果未满，直接加入队列
      else {
        last.next = insertNode;
        insertNode.top = last;
        last = insertNode;
        size++;
      }
    }
  }

  /**
   * 获取一个数据
   *
   * @param key 散列信息
   * @return 数据的值
   */
  public String get(String key) {
    // 1,计算散列
    int index = this.hash(key);

    Data findData = hashData[index].hnext;

    // 在散列桶中查找数据
    while (findData != null) {
      if (key.equals(findData.key)) {
        break;
      }
      findData = findData.hnext;
    }

    // 如果数据被查找到，需要更新此数据，在缓存中移除，加入到队尾中
    if (null != findData) {
      // 删除在链表中的位置
      findData.top.next = findData.next;
      findData.next.top = findData.top;
      findData.next = null;

      // 将节点加入到链表的尾部
      last.next = findData;
      findData.top = last;
      last = findData;

      return findData.value;
    }

    return null;
  }

  /**
   * 在散列中查询查找最后一个节点
   *
   * @param index 索引信息
   * @return 查找的节点
   */
  private Data getLastNode(int index) {
    Data findLastData = hashData[index];
    // 查找当前的key是否已经存在
    while (findLastData.hnext != null) {
      findLastData = findLastData.hnext;
    }
    return findLastData;
  }

  /**
   * 在散列中查询节点
   *
   * @param key key信息
   * @param index 索引信息
   * @return 查找的节点
   */
  private Data findNode(String key, int index) {
    Data findLastData = hashData[index];
    Data findNode = null;
    // 查找当前的key是否已经存在
    while (findLastData != null) {
      if (key.equals(findLastData.key)) {
        findNode = findLastData;
        break;
      }
      findLastData = findLastData.hnext;
    }
    return findNode;
  }

  /**
   * 找到散列中拉链中的前驱节点
   *
   * @param findNode 待查找的节点
   * @return 前驱节点
   */
  private Data findHashPrev(Data findNode) {
    Data prevNode = hashData[hash(findNode.key)];

    Data find = null;

    while (prevNode != null) {
      if (prevNode.hnext == findNode) {
        find = prevNode;
        break;
      }
      prevNode = prevNode.hnext;
    }

    return find;
  }

  public void printLinkedTreeNode() {
    System.out.println("");
    System.out.println("");

    Data link = head;

    while (link != null) {
      System.out.print("curr{" + link.key + ":" + link.value + "}");

      if (null != link && null != link.top) {
        System.out.print(",top:{" + link.top.key + ":" + link.top.value + "}" + "\t");
      }
      link = link.next;

      if (null != link) {
        System.out.println(",next:{" + link.key + ":" + link.value + "}" + "\t");
      }
    }
  }

  public void printHashTreeNode() {
    Data hashLinked;
    for (int i = 0; i < capacity; i++) {
      System.out.print("index:" + i + "\t");
      hashLinked = hashData[i];
      while (hashLinked != null) {

        System.out.print("curr{" + hashLinked.key + ":" + hashLinked.value + "}");
        hashLinked = hashLinked.hnext;

        if (null != hashLinked) {
          System.out.print(",hnext{" + hashLinked.key + ":" + hashLinked.value + "};\t");
        }
      }
      System.out.println();
    }
  }

  private int hash(String key) {

    int h = key.hashCode();
    // capicity 表示散列表的大小
    return (h ^ (h >>> 16)) & (capacity - 1);
  }
}
