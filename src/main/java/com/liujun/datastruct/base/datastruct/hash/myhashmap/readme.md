# 自己实现HashMap
在平时的开发中，jdk库中的HashMap是我们在平时的开发中用的非常多的容器之一，我常常在想，自己能否也实现一个呢？以前觉得非常的复杂，但当我学习了跳表，和散列表后，我觉得这个问题我可以尝试了，于是就开始了自己实现MyHashMap之旅。

## 1. 需求定义

既然要实现一个HashMap，那还是先需求吧。

```java
public class MyHashMap<K, V> {
	public MyHashMap(int capacity){}
    public void put(K key, V value) {}
    public V get(K key) {
        return null;
    }
    public void remove(K key){}
}
```
1. 能够支持数据最基本的操作。包括添加一个键值对，根据key获取值，以及按key删除值三个基本的方法。
2. 能够支持动态的扩容。扩容每次不能占用太多的时间，以免单个操作等待时间过长。
4. 当遇到极端情况（hash到一个槽）时间复杂度也不能退化为O(n)。

   
## 2. 实现分析
针对以上的需求，可以归纳为这几个问题
1. 如何设计散列函数？
2. 装载因子过大怎么办？
3. 如何避免低效的扩容？
4. 链表冲突的问题解决？



### 如何设计散列函数？

散列函数设计的好坏决定散列冲突的概率，也决定了散列表的性能。什么样的散列函数才是一个好的散列函数？

1. 散列函数的设计不能太复杂。复杂的函数会消耗大量的计算资源，间接影响了散列表性能。

2. 散列函数生成的值要尽可能的随机并且均匀分布。这样能才避免或者最小化冲突，即使冲突，散列到每个槽内的数据也会比较的平均。不会出现严重的数据倾斜问题。

实际上散列方法有很多，这里采用的是HashMap中的使用散列函数.
```java
  private int hash(K key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
  }
```



### 装载因子过大怎么办？

散列表的装载因子 = 装入散列表中的元素个数 / 散列表的长度  

装载因子越大说明空闲位置越少，冲突越多，散列表的性能会下降。

针对目前的需求这是一个动态的散列表，数据的集合是变化 的，我们事先根本无法预估出数据个数，我们也无法事先申请一个足够大的散列表，装载因子就会慢慢的变大，当装载因子大到一定程序后，散列冲突就会变得不可接受。针以地这个问题该如何处理？

当装载因子过大的时候，可以采用扩容的办法来解决这个问题。重新申请一个更大的散列表。将数据搬移到这个新的散列表中，假如每次扩容都申请一个原来散列表大小2倍的空间，如果原来散列表的装载因子是1，经过扩容后，新的散列表的装载因子就变成了0.5



### 如何避免低效的扩容？

在大部分情况，动态扩容的散列表插入都很快，但在特殊情况下，当装载因子已经到阈值，需要先扩容，再将原有的数据搬移到新的散列表中，再插入数据。这个时候就会很慢。甚至无法接受的程度。

举个例子，假如现在的散列表当前大小为10万个大小，想要扩容为原来的2倍大小，那需要新申请一个20万个大小的散列表，然后对这10万个数据进行重新计算hash，将数据保存到新的散列表中，并且将原散列表中的数据删除，这个不用计算了，这个听起来就非常的耗时。那如何来解决这个问题呢？

为了解决一次性扩容耗时过多的问题。可以将扩容操作放到插入操作中进行，分批完成数据的搬移。当装载因子阈值到达时，只申请新的空间，但并不搬移老的数据到新的散列表中。当有新的数据要插入时，将新数据插入到散列表中，并从老的散列表中拿出几人数据放到散列表中。每插入一个数据，重复这一过程。经过多次的插入手，老的散列表中的数据，就被一点一点的搬移到了，新的散列表中。这样没有了一次性搬移操作，插入操作就会变得很快。

但这样操作也会带来新的问题？由于现在存在两个散列表，新散列表与旧散列表。那查询怎么办？

针对这个问题，我们可以分开查询两个散列表，优先从新的散列表开始搜索。当数据被搜索到，直接返回，搜索不到再从旧在的散列表中去搜索。

通过这样均摊的办法，将一次扩容的代价，均摊到了多次插入操作。就避免了一次性扩容过多的情况。这样的实现方式下，任何情况下，插入的数据时间复杂度都是O(1）。



### 链表冲突的问题解决？

由于散列冲突是一定会存在的。那如何解决散列冲突？

解决链表冲突办法有多种，常用的有两种，分别为开放寻址法和链表法。

这里采用的链表法，但采用链表法在极端情况下，操作的时间复杂度会退化为O(n)，这里我加入了跳表来解决这个问题.

那来看看链表与跳表配合解决这个问题的：



来一起看看这整体一个存储的结构吧：

![](D:\doc\博客\数据结构与算法\散列表\myhashMap的整体结构.png)





在插入数据时将检查当前槽内的数据个数，小于8个则为链表，当大于8个时，则将链表转换为跳表，并将链表中的数据搬移到跳表中。

这就是采用链表与跳表结合的方法来解决散列冲突的问题，这样当一个数据插入时，就算遇到极遇的情况，也不会退化成O(n)，而是O(logn)，那为什么不将所有的都切换为跳表呢？

这是因为跳表不仅需要额外的空间，也是需要对操作进行遍历，当数据个数很少时，其数据量很少时，没有任何的优势。当数据量大了以后，跳表的优势才能发挥。采用跳表，又带来了新的问题了，那就是跳表的高度？

由于数据不断的被插入，如果跳表的高度是不变的情况下，时间复杂度就会退化为O(n/m)，这样就需要保证跳表的高度随着数据增加而增加。同样的当数据减少后进行跳表层级的减少。那具体怎么解决这个问题呢？

首先我是通过定义阈值来解决的，添加跳表层级的阈值，这个值是来的呢？跳表的高度是随机分布的，它能证在平均情况下的时间复杂度为O(logn),先来看看跳表的随机函数吧！

```java
private int randomLevel() {
    int level = 1;
    for (int i = 1; i < MAX_LEVEL; ++i) {
      if (random.nextInt() % 2 == 1) {
        level++;
      }
    }

    return level;
  }
```

可以看出这个随机函数与跳表的最大高度的直接关系，最大高度定义跳表随机能到达最大层级数。这样当跳表的节点增加到某个阈值时，我们以阈值来计算下跳表的高度即可得到最大值层级高度。那来看看计算跳表的高度的方法吧：

```java
 /**
   * 计算log2n
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
```

这是采用二分法计算的以2为底的n的对数。

这样跳表的高度可以计算了，那跳表的增加的阈值如何计算呢？这里正常来说可以直接使用以2为底，以跳表的调度为指数计算即可，但在阅读jdk8源码时，发现一个更牛的计算方法，可直接用这个方法更好。

```java
 /**
   * The maximum capacity, used if a higher value is implicitly specified by either of the
   * constructors with arguments. MUST be a power of two <= 1<<30.
   */
  private static final int MAXIMUM_CAPACITY = 1 << 30;
  
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
```

这是计算跳表高度以及计算跳表层级的阈值。有了增加跳表层级，那需要减少跳表的层级吗？

如果不减少层级会有影响吗？这个可以想像下，当数据量扩容到1024个节点后，然后再执行删除，一直删除到只有32个顶点，如果跳表的层级还是10层？这时候就会有一些多余的遍历在这个跳表上，所以需要维护跳表的高度与跳表节点之间的一个平衡。这样也得定义一个减少跳表层级的阈值。这个计算过程就同增加跳表层级类似了。

来看看具体是如何被计算的吧

```java
          // 计算增加层级的阈值
          skipThresholdExpand = tableSizeFor(nodes[nodeIndex].size + 2);
          // 计算减少层级的阈值
          skipThresholdShrink = tableSizeFor(nodes[nodeIndex].size - 2);
```





## 3. 最终的代码实现

最后还是把自己实现的HashMap的完整代码贴出来：

```java
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
```



这就是我自己实现的一个HashMap。与java原生的HashMap相比，性能相差无几。这简单的做了一个单元测试的对比。

```java
public class TestMyHashMapCompare {

  private static final int MAX_SIZE = 2999999;

  @Test
  public void testHashMap() {

    HashMap instanceMap = new HashMap();
    for (int i = 0; i < MAX_SIZE; i++) {
      Data item = new Data(i, "i" + i);
      instanceMap.put(item, "name:" + i);
    }

    for (int i = MAX_SIZE - 1; i > 0; i--) {
      Data item = new Data(i, "i" + i);
      Object value = instanceMap.get(item);
      Assert.assertEquals(value, "name:" + i);
      instanceMap.remove(item);
    }
  }

  /** 自己实现的HashMap测试 */
  @Test
  public void testHashMapMe() {

    MyHashMap instance = new MyHashMap();

    for (int i = 0; i < MAX_SIZE; i++) {
      Data item = new Data(i, "i" + i);
      instance.put(item, "name:" + i);
    }

    for (int i = MAX_SIZE - 1; i > 0; i--) {
      Data item = new Data(i, "i" + i);
      Object value = instance.get(item);
      Assert.assertEquals(value, "name:" + i);
      instance.remove(item);
    }
  }
}
```



来看下对比结果:

![](D:\doc\博客\数据结构与算法\散列表\myhashMap对比测试结果.png)



这个结果可以看作与java原生版本的HashMap相关无几。甚至可能快个几秒。但这里仅是简单的一个测试。并不能证明我实现的Map比java原生的好。



## 总结



我通过将自己实现一个散列表。将散列表中相关的知识做了一个实践。关于散列表，需要重点掌握散列函数、装载因子、动态扩容策略、散列冲突的解决。通过这些实践与学习能更好的理解HashMap这一工业级的散列表的实现。当然java版本的HashMap使用红黑树来做的动态数据结构。







