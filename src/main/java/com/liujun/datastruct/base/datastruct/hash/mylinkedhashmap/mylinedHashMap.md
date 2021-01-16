# 自己实现一个LinkedHashMap
在上一了章节，我实现一个MyHashMap,那今天继续来实现一个LinkedHashMap，linked相对于HashMap保存了数据插入的顺序，不过想想觉得还是挺神的，HashMap我们都知道，通过散列函数，将数据分散到不同的槽中，再从槽中去读取数据。这一过程听起来就是无法保证顺序的。那要如何保证插入的顺序呢？

其实在LinkedHashMap中，加了下一个双向链表。

```java
    /**
     * HashMap.Node subclass for normal LinkedHashMap entries.
     */
    static class Entry<K,V> extends HashMap.Node<K,V> {
        Entry<K,V> before, after;
        Entry(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }
```



## 整体结构

还是先整体看下这个linkedHashMap的结构吧

![](D:\doc\博客\数据结构与算法\散列表\mylinkedHashMap的整体结构.png)

这个双向链表中的节点有: prev前驱指针、data存储节点、next后继指针，还有一个hnext节点，与标准的双向链比相比，这个多出来的hnext是做什么用呢？

这是因为我使用链表法解决散列冲突，所以每个节点都会在两条链中，一条在在双向链表中，而另外一条就是散列表中的拉链。前驱指针与后继指针是为了将数据串连到双向链表中，而hnext指针是为了将节点串在散列表中的拉链中。



通过这样一组结构就可以做到以下三个操作的时间复杂度为O(1):

1. 添加一个数据

2. 删除一个数据

3. 查询一个数据

## 添加数据

添加数据分为几种情况，

1. 容量未满，添加新的数据：

   

   ![](D:\doc\博客\数据结构与算法\散列表\mylinkedHashMap-put的容量未满添加新数据.png)

   通过散列函数找到槽，将数据放入槽内的拉链中，再将数据放入双向链表的末尾。



2. 覆盖已有数据。

   ![](D:\doc\博客\数据结构与算法\散列表\mylinkedHashMap-put覆盖数据.png)

   通过散列函数找到槽，通过槽的拉链找到与数据一致的key，直接将数据替换即可，指针无需更新。

3. 容量已满，添加新数据

   ![](D:\doc\博客\数据结构与算法\散列表\mylinkedHashMap-put缓存已满.png)

当容量已经满的，首先将双向链表中将链头中的头节点进行移除操作。头元素移除完成后，就是新元素的加个了，优先计算hash获得槽的位置，获取槽的拉链，将新数据加入到拉链中，拉链加入完成后，就是将元素加入到双向链表的末尾节点即可。



## 删除数据

相对于添加来说，删除就比较容易了。



![](D:\doc\博客\数据结构与算法\散列表\mylinkedHashMap-删除数据.png)

首先还是通过hash函数找到槽，再通过槽的拉链找到待删除的节点，找到节点后，这个删除的过程是：一是将槽的拉链中将节点进行删除。二是通过双向链表将将当前节点在双向链表中删除，整个移除的过程都是指针的改变即可。



## 查询数据

![](D:\doc\博客\数据结构与算法\散列表\mylinkedHashMap-查询数据.png)

1. 通过hash函数找到槽，再通过槽的拉链查找当前查询的数据。
2. 当数据被查找到了以后，将查找到的元素信息从原双向链表中进行移除。
3. 然后将查找到的元素加入到双向链表的末尾。
4. 返回查询数据即可。



这就是linkedHashMap中最重要的三个操作，通过这个也能明白linkedHashMap为什么会是这样子的一个效果。这其实也是一个LRU缓存淘汰算法一个实现。



最后还是将代码的实现贴出来吧！

```java
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
```



## 总结

散列表这种数据结构虽然支持非常高效的数据插入、删除、查找操作。但是散列表中的数据都通过散列函数打乱之后无规律存储的。也就是说它无法按照某种顺序快速的遍历数据。如果希望按照顺序遍历散列表。那就需要将散列表中的数据拷贝到数组中，排序，再遍历。

因为散列表是动态数据结构。不停的会有数据的插入和删除，每当我们希望按顺序遍历散列表中的数据的时候，都需要先排序，那效率就会很低。为解决这个问题，将散列表与链表结合在一起使用。就右以完美的规避这个问题。但当数据量大了以后，由于链表只能按顺序遍历。性能必然下降很多。最差的情况可能到O(n),这个时候可以使用跳表替换链表，跳表这种数据结构，所有操作的时间复杂度都可以做到O(logn)，这样就能保证在大数据量的情况下，依然能够高效的插入、查询和删除数据了。