# 跳表加散列表实现有序的散列表(MySkipListHashMap)



## 介绍

有序的，还是散列表，是不是听起来就觉得这不太现实啊！但现实往往就是这么打脸，听起来不可思议的东西，却是可以做到的。

散列表，一个无序的数据结构。通过散列函数将数据散列分布到各个数据的槽里。那如何保证数据的有序呢？在java的库中有一个是实现的有序的数据结构LinkedHashMap,按照插入顺序来保存的有序,这个数据结构是通过散列表与双向链表的结合，来实现的一个保存数据。受此启发。我来实现一个按照数据的顺序来保存的有序数结构。



## 操作： 

有序散列表的操作：

* 添加一个成员对象
* 按照键来查找一个成员对象
* 按照键来删除一个成员对象
* 按照值区间查找数据，比如查找用户年龄20-30的成员对象。
* 按照值做有序的迭代输出

如果用代码表示出来就是:

```java
public class MySkipListHashMap<K, V> {

  /**
   * 添加一个成员对象
   *
   * @param key 键信息
   * @param value 值信息
   */
  public void put(K key, V value) {{}
    
  /**
   * 按照键来查找一个成员对象
   *
   * @param key 键信息
   * @return 数据
   */
  public V get(K key) {}
                                   
  /**
   * 按照键来删除一个成员对象
   *
   * @param key
   */
  public void remove(K key) {}
                                   
  /**
   * 按照值区间查找数据，比如查找用户年龄20-30的成员对象。
   *
   * @param start 开始值
   * @param end 结束值
   * @return 当前的结果
   */
  public List<V> scopeGet(V start, V end) {}

  /**
   * 按照值做有序的迭代输出
   *
   * @return
   */
  public Iterator<V> valueIterator() {}
    
}
```



整合的结构分析：

![](D:\doc\博客\数据结构与算法\散列表\myskiplistHash-整体结构.png)

我来解析下这个节点：data(用来存储数据的节点)，forward（这是一个数组，用于保存跳表高度,全部数据在forward[0]，以上都是索引节点），还有一个hnext节点，那这个hnext节点是用来做什么的呢？

这个还得从散列表说下，这个散列表是通过链表法来解决散列冲突的，所以每个数据都会在两个链表中，一个是散列表的拉链中，还有一个是跳表的多级链表中。forward是为了将节点串在跳表的多层节点中，hnext则为了将节点串在散列表的多层拉链中。



### 添加一个成员对象

添加一个成员，也分为几种情况：

1. **散列槽未到达扩容的阈值，也未到达跳表添加层级的阈值，添加新数据。**

![](D:\doc\博客\数据结构与算法\散列表\myskiplistHash-未到阈值添加新数据.png)

此情况下，散列表不需要扩容，跳表也无需增加层极来维持跳表索引与数据之间的一个平衡，将数据构建成一个节点，节点需要生成跳表的高度，以此高度为搜索的层级，找到每一层的前驱节点。再将当前节点加入到跳表中，跳表数据添加完成。接下就是将数据添加到散列表中，这个通过散列函数就可以找到槽所在的拉链，再将节点加入到拉链即可。

2. **覆盖已有的数据。**

   ![](D:\doc\博客\数据结构与算法\散列表\myskiplistHash- 覆盖老数据.png)

   此情况下，首先通过散列表查找到槽的拉链，再通过拉链遍历查找到对应的数据节点，将数据节点的数据覆盖即可。

   

3. 到达散列槽的扩容的阈值，未到达跳表添加层级的阈值，添加新数据。

已经到达扩容阈值的情况下，首先要做的就是扩容 。

![](D:\doc\博客\数据结构与算法\散列表\myskiplistHash-扩容.png)

扩容仅是申请了新的存储空间，但未对数据进行搬移工作。那接下来就是对数据的搬移工作。将newtab中的数据插入到tab中，再删除newtab中的数据。但搬移不能一次完成，如果一次的话，插入操作就会占用过多的时间，需要将搬移操作分担到多次插入操作中。这样不会对插入操作造成影响。

![](D:\doc\博客\数据结构与算法\散列表\myskiplistHash-搬移.png)

单次数据搬移完成后，将数据新的数据插入到散列表中。这个操作同新数据插入逻辑。将数据加入到散列表与跳表中即可。





![](D:\doc\博客\数据结构与算法\散列表\myskiplistHash-搬移2.png)





穿插在每次的数据插入的过程中即可完成对数据的搬移工作。



4. **未到达散列槽扩容的阈值，到达跳表添加层级的阈值，添加新数据。**

   当跳表的数据量随着插入操作，也变得越来越多，需要增加跳表层级的高度与维持数据操作的一个平均时间复杂度为O(logn),这个操作是通过root节点的高度来记录当前整个跳表的最高高度。每次到达跳表阈值的高度时，增加高度值。这样就通过这个值维护跳表高度与数据之间的一个平衡。



### 按照键来查找一个成员对象



![](D:\doc\博客\数据结构与算法\散列表\myskiplistHash-获取数据.png)

通过散列函数得到散列的槽，再通过槽的拉链顺序遍历就可得到所要查找的数据。





### 按照键来删除一个成员对象



![](D:\doc\博客\数据结构与算法\散列表\myskiplistHash-删除数据.png)

通过散列函数找到散列槽，在槽的拉链中顺序遍历可得到待删除的数据项前驱节点，通过前驱节点就可直接删除当前节点，散列表中已经删除成功。接来下就是删除跳表中的节点，首先在跳表的索引层中查找待删除节点的前驱节点，找到前驱节点后，就可进行删除节点的操作。由于节点的删除，可能带来数据个数与跳表的高度的一个不平衡，所以在跳表节点个数达到减少到一个阈值时，就需要进行跳表节点高度的减少。





### 按照值区间查找数据，比如查找用户年龄20-30的成员对象。

![](D:\doc\博客\数据结构与算法\散列表\myskiplistHash-区间数据查找.png)

此查找仅需要通过跳表参与，以值的开始节点在跳表的搜索找到起始顶点，以起始顶点在数据层向后遍历，将每个数据都记录到集合中，当找到区间结束值时，遍历结束。集合中的数据，即为区间的数据。







### 按照值做有序的迭代输出



![](D:\doc\博客\数据结构与算法\散列表\myskiplistHash-迭代输出.png)

数据的迭代输出，此操作在找到跳表的数据层起始顶点后，依次向后使用迭代器进行遍历。



## 代码实现

```java
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
   * 添加一个成员对象
   *
   * @param key 键信息
   * @param value 值信息
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
        // key查找
        if (tmpLastNode.hnext.key.equals(key)) {
          break;
        }
        tmpLastNode = tmpLastNode.hnext;
      }

      // 当节点不为空，说明数据存在，直接覆盖即可
      if (null != tmpLastNode.hnext) {
        tmpLastNode.hnext.value = value;
      }
      // 否则将数据加入
      else {
        // 将最后节点加入
        tmpLastNode.hnext = addSkipNode(hashcode, key, value);
      }
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
   * 按照键来查找一个成员对象
   *
   * @param key 键信息
   * @return 数据
   */
  public V get(K key) {
    HashSkipNode<K, V> valueItem = this.getPrevNode(key);
    if (valueItem != null) {
      return valueItem.hnext.value;
    }

    return null;
  }

  /**
   * 按照值区间查找数据，比如查找用户年龄20-30的成员对象。
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
   * 按照键来删除一个成员对象
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
          && rootNodeTmp.forward[i].key == currNode.key
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
   * 按照值做有序的迭代输出
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

```





## 总结

有序的散列表就已经完成了，通过此MySkipListHashMap可以高效的完成数据插入、删除、查找操作，最好情况为O(1),最坏情况下也是O(logn)，由于数据做了有序存储，就可以做到非常高效的按区间查找及查找以及有序数据迭代，如果是HashMap结构，如果获取区间数据的操作，需要将数据所有取出，先排序，再获取区间数据。

散列表是一种无序的动态数据结构，数据会不停的插入，删除，每当希望按照数据的顺序遍历时，需要先排序，那效率势必会很低。本文引入了跳表与散列表一起做为一种数据结构，结合跳表的有序性及散列表的特性，可以做到非常的高效数据操作。