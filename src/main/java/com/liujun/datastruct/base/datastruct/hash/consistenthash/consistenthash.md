#  一致性哈希算法

## 一. 传统的哈希算法的局限性
要了解一致性哈希算法，就得先了解传统的哈希算法在分布式场景下的局限性。哈希算法就是按键值对的存储，给定一个键，可以做到O(1)的时间复杂度内的数据查找。例如根据学生的学号查找学生的相关的信息。一种简单的存储形式就是以哈希表的形式来存储<code,studentinfo>。

假如某个学校的学生太多了。一台机器上的哈希表已经存储不了。需要使用多台机器来进行存储学生信息，那这时该怎么办呢？这个最简单的办法就是使用哈希取模来确定，计算公式如下

> 节点编号=hash(key) % N #N为机器的个数

如果将这个用图形来表示就是:

![](D:\doc\博客\新技术学习\一致性hash\me\hash分布.png)

我以java的HashMap的哈函数为例，来计算哈希分布

```java
public class HashCode {

  /**
   * hash函数
   *
   * @param key
   * @return
   */
  public static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
  }
}

public class TestHashCode {
  @Test
  public void countHashCode() {
    int nodeSum = 4;
    this.countNode("jiansheng", nodeSum);
    this.countNode("feifei", nodeSum);
    this.countNode("meilin", nodeSum);
    this.countNode("manggo", nodeSum);
  }

  private void countNode(String key, int nodeSum) {
    int hashCode = HashCode.hash(key);
    // 由于hashcode可能存在负数，进一步运算变正数
    hashCode = hashCode & Integer.MAX_VALUE;
    int nodeIndex = hashCode % nodeSum;
    System.out.println("key : " + key + "-->" + hashCode + "-->" + +nodeIndex);
  }
}

```



可看到输出:

```java
key : jiansheng-->1294502165-->1
key : feifei-->869165070-->2
key : meilin-->1069572320-->0
key : manggo-->1066011775-->3
```



结果汇聚成表格就是:


key|哈希值|节点编号
:---:|:---:|:---:
jiansheng|1294502165|1
feifei|869165070|2
meilin|1069572320|0
manggo|1066011775|3



既然是分布式系统，节点的增加和减少是非常常见的

### 1.1当节点增加时

在分布式系统场景下，当系统的容量不够时，需要增加机器进行扩容，以应对数据的增长。原始节点为4个，如果将节点扩容到6个，那哈希会发发生什么变化呢？

原始哈希

|    key    |   哈希值   | 节点编号 |
| :-------: | :--------: | :------: |
| jiansheng | 1294502165 |    1     |
|  feifei   | 869165070  |    2     |
|  meilin   | 1069572320 |    0     |
|  manggo   | 1066011775 |    3     |

当增加到6个节点时

|    key    |   哈希值   | 节点编号 |
| :-------: | :--------: | :------: |
| jiansheng | 1294502165 |    5     |
|  feifei   | 869165070  |    0     |
|  meilin   | 1069572320 |    2     |
|  manggo   | 1066011775 |    1     |

可以很明显的观察到，当节点增加时，所对应的节点也发生了变化。比如"jiansheng",在节点数仅为4个的情况下，对应的节点编号为1，但当节点增加到6个时，对应的节点编号就改变成为5。



### 1.2 当节点减法时

在分布式场景下，某个节点很出现无预兆的宕机。针对这种情况，我们希望的是仅影响到的是部分数据，功能不受影响。针对这种情况，来观察下下数据的变化

原始哈希

|    key    |   哈希值   | 节点编号 |
| :-------: | :--------: | :------: |
| jiansheng | 1294502165 |    1     |
|  feifei   | 869165070  |    2     |
|  meilin   | 1069572320 |    0     |
|  manggo   | 1066011775 |    3     |

当减少到3个节点时

|    key    |   哈希值   | 节点编号 |
| :-------: | :--------: | :------: |
| jiansheng | 1294502165 |    2     |
|  feifei   | 869165070  |    0     |
|  meilin   | 1069572320 |    2     |
|  manggo   | 1066011775 |    1     |

可以很明显的观察观察到，数据所对应的节点也发生了变化。还是以"jiansheng"为例，在节点为4个的情况下，对应的节点编号为1.当节点减少到3个时，对应的节点编号就改变成了2。



当集群中节点的数量发生变化时，之前映射的规则就可能发生了变化。如果集群中的节点提供的服务是没有差别的，这不会有任何影响。但对于分布式缓存这种应用系统而言。映射失效就意味着缓存的失效，若同一时刻大量的缓存失效。这将是灾难性的，请求将直接到达数据库，数据库会因为压力过大而崩溃，这就是一般常说的“缓存雪崩”。

要解决此问题就需要在所有节点上，将已有的键全部重新分配。这个操作是非常昂贵的操作，并且可能对正在运行的系统产生不利的影响，当然除了重新全配已有的键外，还有一个更好的方案——一致性哈希算法。



## 二. 一致性哈希算法历史

维基百科是这样解释的

>**一致哈希** 是一种特殊的[哈希](https://zh.wikipedia.org/wiki/哈希)算法。在使用一致哈希算法后，哈希表槽位数（大小）的改变平均只需要对K/n 个关键字重新映射，其中 K是关键字的数量，n是槽位数量。然而在传统的[哈希表](https://zh.wikipedia.org/wiki/哈希表)中，添加或删除一个槽位的几乎需要对所有关键字进行重新映射。

历史

>一致哈希由MIT的[Karger](https://zh.wikipedia.org/w/index.php?title=David_Karger&action=edit&redlink=1)及其合作者提出，现在这一思想已经扩展到其它领域。在这篇1997年发表的学术论文中介绍了“一致哈希”如何应用于用户易变的分布式Web服务中。哈希表中的每一个代表分布式系统中一个节点，在系统添加或删除节点只需要移动k/n项。[[1\]](https://zh.wikipedia.org/wiki/一致哈希#cite_note-KargerEtAl1997-1)
>
>一致哈希也可用于实现健壮缓存来减少大型Web应用中系统部分失效带来的负面影响。[[2\]](https://zh.wikipedia.org/wiki/一致哈希#cite_note-KargerEtAl1999-2)
>
>一致哈希的概念还被应用于[分布式散列表](https://zh.wikipedia.org/wiki/分布式散列表)（DHT）的设计。DHT使用一致哈希来划分分布式系统的节点。所有关键字都可以通过一个连接所有节点的覆盖网络高效地定位到某个节点。



## 三. 一致性哈希算法核心思想



一致性哈通过一个一致性哈希环的数据结构来实现，它的起点是0，最大为2^32-1,并且起点与终点连接，所有这个环的数据分布为[0,2^32-1]，这也是哈希函数结果的分布。

![](D:\doc\博客\新技术学习\一致性hash\me\一致性hash的范围.png)

还是以"jiansheng"、“feifei”、“meilin”、“manggo”,将这4个键的哈希值分别记为"h0"、“h1”、“h2”、"h3"



|    key    |   哈希值   | 标记符 |
| :-------: | :--------: | :----: |
| jiansheng | 1294502165 |   h0   |
|  feifei   | 869165070  |   h1   |
|  meilin   | 1069572320 |   h2   |
|  manggo   | 1066011775 |   h3   |

将这些哈希值在哈希环上表示就是:

![](D:\doc\博客\新技术学习\一致性hash\me\一致性hash的key所对应的.png)

### 3.1 将服务器加入到哈希环

使用同样的哈希函数，我们将服务器放置到哈希环上，可以用服务器的IP作为哈希的键，这样每台服务器在哈希环上都有了一个唯一的位置。假如这里存在着4台服务器，分别为"S0"、"S1"、"S2"、"S3",哈希后的值记作"T0"、"T1"、"T2"、"T3"。

![](D:\doc\博客\新技术学习\一致性hash\me\服务器的一致性hash的key所对应的.png)

### 3.2 为对象选择服务器

将对象和服务器放置到同一个哈希环后，在哈希环上顺时针查找距离这个对象的Hash值最近的机器，即这个对象所属的机器。h0顺时针会找到T0，则h0的请求会被发送到T0这台机器上，H1顺序时针会找到T1，则h0的请求会被发送到T1这台机器上，其他的请求会类似的进行顺时针的找到对应的机器

![](D:\doc\博客\新技术学习\一致性hash\me\服务器的与IP的对应关系.png)

### 3.3 当新加服务器后

假如随着业务的增长，现在增加了一台服务器。经过hash运算后，服务器节点落在了T1与T2之间。可参看

![](D:\doc\博客\新技术学习\一致性hash\me\服务器的一致性hash的-新增加服务器的情况.png)

这里只有T1与T2之彰间的数据需要重新分配，其他节点的数据不受影响，依然可以对外提供服务。相比于传统的hash算法，这已经有较大的改善了，因为只有部分数据受到影响，其他依然可以正常的提供服务。



### 3.4 当服务器宕机后

这个操作就比较常见了，比如T1这台服务器宕机了，那会发生什么现象呢？

![](D:\doc\博客\新技术学习\一致性hash\me\服务器的一致性hash的-服务器宕机的情况.png)

因为T1已经宕机，这时原来请求到T1的节点，则会顺时针查找下一个节点，会转移到T2节点上，这里只需要将原来T1上的数据转移到T2节点上即可。并不会影响到其他正在运行的节点。



### 3.5 虚拟节点

一致性hash算法的基本操作已经完成了。但这个还存在问题，那就是分布不均的。在新增加一台服务器后。新增加的服务器只能分担相邻节点的负载。其他服务器并没有因为服务器的增加而减少负载。这种情况不是我们所希望的。

这个一致性hash当然也有解决办法。那就是通过虚拟节点来解决的。

具体来说，虚拟节点是这样子的：将每台服务器虚拟为一组虚拟服务器。将虚拟服务器放置到hash环上。如果要确定对象所在的服务器，需先确定虚拟服务器，再由虚拟服务器定位至物理服务器。

![](D:\doc\博客\新技术学习\一致性hash\me\服务器的一致性hash的-带虚拟节点.png)

o1和o2表示对象，v1~v8表示虚拟节点，S1和S2表示服务器节点，V1~V4的虚拟节点对应S1,V4~V8的虚拟节点对应S2。







## 四. 一致性哈希优势和劣势

### 4.1 优势：

扩展性：一致性哈希算法保证了在增加和减少服务器节点时，数据存储的改变最少，相比传统的哈希算法，节省了大量节点数据移动的开销。

容错性：当一个节点宕机后，一致性哈希会顺序的查找下一个服务器节点，不会出现类似“缓存雪崩”的问题。仅影响部分数据。

平衡性：当业务的快速增长，造成了数据的增长，当加入节点后，数据出现负载不均的情况，不需要对全量的数据进行重新的哈希计算和划分，只需调整数据的存储分布，这样就可以随着数据的增长而扩展物理服务器的数量。代价比普通的hash算法的重新分布的代价要小很多。



### 4,2 劣势：

影射关系不灵活：由于都是通过键计算得到的服务器IP，就必须导致数据关联关系比较固定。

负载不均：数据存储较均衡，不代表流量均衡。还是存在热点数据的问题，导致机器间的负载不均。

增加节点后，手动迁移数据较麻烦：我想这个可能是分布式系统上的一大痛点了，这个数据的分布与移迁，都是相当麻烦的操作。



## 五. 一致性哈希算法与哈希算法的关系

一致性哈希算法是在哈希算法的基础上提出来的。

在动态变化的分布式环境中，相比于传统的哈希算法，需要满足几个条件：

平衡性： 哈希的结果应该平均分配到各个节点，这样就从算法上解决了数据分配不均的问题。

单调性：在集群中新增和删除节点时，不影响系统的正常运行。

分散性：数据应该分散地存放在分布式系统中的各个节点（节点数据可以备份），不必每个都点都存储所有数据。



## 六. 技术适用的场景
### 6.1 负载均衡

负载均衡的技术有很多，比如轮训、随机、加权轮训等算法。那如何实现一个带会话粘连的负载均衡? 可以这么理解，一个用户请求过来的所有会话都发送到同一台机器，而不是飘忽不定的，在集群的所有节点都保存.

这个最简单的办法就是维护一张会话与服务器的关系表，一方面，当面对大量的用户会话时，数据量检索就会出现问题，另一方式，用户的上线，下线，服务器的宕机与新加都需要对这个关系表进行维护。这个复杂度可想而知。

一致性哈希算法为这个场景提供了解决方案。将所有的服务器的IP都维护到一致性哈希算法中。服务器的宕机与新加将对应的IP增加与删除即可。以每个会话的id作为一致性哈希的key进行计算，就可以得到一个唯一的节点。这样就可以将一个会话的所有请求都路由到同一台机器上。



### 6.2 分布式存储

现在的互联网面对的是海量的数据，海量的用户。为了提高数据的读取和写入能力，我们都使用优先使用分布式的存储系统，还是以分布式缓存为例吧，那如何决定数据缓存在哪台机器上呢？

借助于分片的思想，可以借助于哈希算法对数据求哈希值，然后再与机器的个数取模，就时候就可以得到应对的存储机器的编号了，但是如果数据的增加，原来的机器已经无法承受，需要向集群的节点中加入机器，这时候问题就来了，这个在传统哈希算法的局限性已经说明了。

一致性哈希为这种场景提供了解决方案。假如我们有N个机器，数据范围为[0,MAX],我们将数据划分为M个小区别(M>N)，每个机器就负载N/M个区间的数据，这样当有新机器加入的时候，我们仅需要将部分数据搬移动到新机器中，这样就不用全部重新计算哈希，同时也保持了，各机器数据均衡。



## 七. 代码实现

### 7.1 不带虚拟节点:

```java
public class ConsistentHashCycle {

  /** 用于记录当前一致性hasp的节点信息 */
  private static final SortedMap<Integer, String> SORT_MAP = new TreeMap<>();

  /**
   * 添加节点
   *
   * @param dataServerIp
   */
  public void addNode(String dataServerIp) {
    int hashCode = Hashing.murmur3_32().hashString(dataServerIp, StandardCharsets.UTF_8).asInt();
    SORT_MAP.put(hashCode, dataServerIp);
  }

  /**
   * 执行节点下线操作
   *
   * @param serviceIp
   */
  public void dataDown(String serviceIp) {
    int hashCode = Hashing.murmur3_32().hashString(serviceIp, StandardCharsets.UTF_8).asInt();
    // 下线节点
    SORT_MAP.remove(hashCode);
  }

  /**
   * 获取客户端的节点信息
   *
   * @param key key的信息
   * @return
   */
  public String getNode(String key) {
    int hashCode = Hashing.murmur3_32().hashString(key, StandardCharsets.UTF_8).asInt();
    SortedMap<Integer, String> dataNode = SORT_MAP.tailMap(hashCode);

    // 当节点获取不到时，获取首个节点作为默认节点
    if (dataNode.isEmpty()) {
      return SORT_MAP.get(SORT_MAP.firstKey());
    }

    // 如果能获取到，则返回对应的首个节点
    return dataNode.get(dataNode.firstKey());
  }
}
```

### 7.2 带虚拟节点的实现

```java
public class ConsistentHashVirtualNodeCycle {

  /** 用于记录当前一致性hasp的节点信息 */
  private static final SortedMap<Integer, String> SORT_MAP = new TreeMap<>();

  /** 虚拟节点的个数 */
  private static final int VIRTUAL_NODE = 100;

  /** 添加的字符 */
  private static final String RAND_VALUE = "DATA_";

  /**
   * 添加节点
   *
   * @param dataServerIp
   */
  public void addNode(String dataServerIp) {
    for (int i = 0; i < VIRTUAL_NODE; i++) {
      String dataKey = dataServerIp + RAND_VALUE + i;
      int hashCode = Hashing.murmur3_32().hashString(dataKey, StandardCharsets.UTF_8).asInt();
      SORT_MAP.put(hashCode, dataServerIp);
    }
  }

  /**
   * 执行节点下线操作
   *
   * @param serviceIp
   */
  public void dataDown(String serviceIp) {
    for (int i = 0; i < VIRTUAL_NODE; i++) {
      String key = serviceIp + RAND_VALUE + i;
      int hashCode = Hashing.murmur3_32().hashString(key, StandardCharsets.UTF_8).asInt();
      // 下线节点
      SORT_MAP.remove(hashCode);
    }
  }

  /**
   * 获取客户端的节点信息
   *
   * @param key key的信息
   * @return
   */
  public String getNode(String key) {
    int hashCode = Hashing.murmur3_32().hashString(key, StandardCharsets.UTF_8).asInt();
    SortedMap<Integer, String> dataNode = SORT_MAP.tailMap(hashCode);
    // 如果获取不到节点，则返回第一个节点
    if (dataNode.isEmpty()) {
      return SORT_MAP.get(SORT_MAP.firstKey());
    }
    return dataNode.get(dataNode.firstKey());
  }
}
```

