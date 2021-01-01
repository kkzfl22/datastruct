## 跳表

针对链表这种数据数据结构，捎加改造，可以支持类似“二分”的查找算法，这就是跳表。



### 如何理解跳表

对于单链表来说，即使存储的数据是有序的，如果在链表中查找某个数据，也只能从头到尾依次遍历，时间复杂度会很高,是O(n)。
![跳表原始之原始链表](D:\doc\博客\数据结构与算法\跳表\跳表原始之原始链表.png)
对于单链表如何提高查找效率呢？可以类似图中如下做一级“索引”，这样子查询起来就会快很多，每两个节点抽取一个结点到上一级，可以把抽取出来的叫作索引。图中down表示down指针，指向下一级。

![跳表之一级索引](D:\doc\博客\数据结构与算法\跳表\跳表之一级索引.png)

以查找6为例，首先可以遍历一级索引，当遍历到节点为5时，索引的下一个节点为7，大于需查找到6，则通过down指针向下一级，在原始链表中查找，现在再通过以down指针指向的5为起点，向后遍历，但遍历一次，就找到了我们需要查找的6。相比于原始链表的遍历，通过一级索引的方式，我们将原始需要6次的遍历，减少到了5次。

那如果再加一级索引呢？

![跳表之二级索引](D:\doc\博客\数据结构与算法\跳表\跳表之二级索引.png)

以查找8为例，首先在二级索引遍历，当遍历到节点为5时，二级索引的下一个节点为9，大于查找的8，则通过二级索引的5的down指针向下一级，在一级索引中查找。一级索引以5为起来，向后遍务，找到7时，一级索引的7的下一个节点为9，大于查找的8，通过一级索引的7的down指针向下一级，原始链表则以7为起点，向后遍历，即可得到8，相比原始链接，通过增加两级索引，将链表中的遍历次数从8次减少到6次。

在例子中，数据量不大，仅几个数据，所以即便是增加两级索引，效果也不明显。那来个数据大点的直观显示吧！

这里我画了7个顶点，建立了5级索引。



![72顶点数据顶点-图示](D:\doc\博客\数据结构与算法\跳表\72顶点数据顶点-图示.png)

图示为数据为1至72，查找70，仅需9次，即可找到70，效率提升是非常明显的。



### 跳表的时间复杂度？

单链的时间复杂度是O(n)，那跳表的时间复杂度呢？是什么呢？

假设一个链表中有n个节点，每两个节点向上抽出一个节点作为上一级的索引节点，那上一级节点个数大约为n/2,那第二级的节点个数大约就是n/4,第三级节点的个数大约就是n/8,依次类推至，第K级索引结点个数就是K-1级索引结点个数的二分之一，那K级结点的个数就是n/(2的k次方)

假设有s级，最高级的索引有2个结点，通过公式 ，可得到n/(2的s次方)=2，那s=log2n-1,那跳表的高度就是log2n,在跳表中查询时，如果每一层都要遍历m个节点，那在跳表中查询一个数据的时间复杂度就是O(m*logn)



### 跳表的空间复杂度

比起单纯的单链表，跳表需要存储多级索引，肯定要消耗更多的存储空间。那需要消耗多少呢？

假设原始链表大小为n，第一级大约有n/2个结点,第二级大约有n/4个顶点，第三级大约有n/8个顶点，依次类推，每上升一级就减少一半，直到剩下2个结点，如果把每一层写出不就是一个等比数列：

n/2,n/4,n/8,....8,4,2

这几级索引节点和就是n/2+n/4+n/8...+8+4+2=n-2

所以跳表的空间复杂度为O(n)





### 跳表的代码实现

跳表是一种动态数据结构，不仅支持查找操作，还支持动态的插入、删除操作，而且插入、删除操作的时间复杂度为O(logn)

跳表作为一种动态数据结构，需要一种手段来维护索引与原始链表大小之间的平衡，也就是说，当链表中的结构多，索引中的节点就应该增加一些，避免复杂度退化，以及查找、插入、删除性能的下降。跳表是通过随机函数来维护这一“平衡性”。通过一个随机函数，来决定将这个结点插入到哪几级索引中，比如索引值K，那就将这个结点插入到第一级至第K级索引中。

随机函数的选择很有讲究，从概率上来讲，能够保证跳表的索引大小和数据大小平衡性，不至于性能过度退化。

开始跳表的代码吧。就从添加开始吧。



```java
public class SkipList {

  /** 当前最大层级 */
  private final int maxLevel;

  /** 随机函数 */
  private Random rand = new Random();

  /** 根节点 */
  private Node root;

  /** 当前的层级数 */
  private int levelNum;

  public SkipList(int maxLevel) {
    this.maxLevel = maxLevel;
    this.root = new Node();
  }

  /**
   * 跳表插入节点
   *
   * @param value 数据
   */
  public void add(int value) {
    // 1,通过随机函数决定叶子节点数
    int level = this.randomLevel();
    // 节点信息
    Node tempNode = new Node();
    // 保存值
    tempNode.data = value;
    // 最大层级
    tempNode.nodeMaxLevel = level;
    // 跳表的前指针位置记录
    Node[] nodeForward = new Node[level];
    // 1,将跳表都指向头节点
    for (int i = 0; i < level; i++) {
      nodeForward[i] = root;
    }
    // 进行跳表的查找，找到每一层该插入的位置。记录到nodeForward中
    Node nodeTmp = root;
    for (int i = level - 1; i >= 0; i--) {
      // 从最顶层的节点开始
      while (nodeTmp.forward[i] != null && nodeTmp.forward[i].data < value) {
        nodeTmp = nodeTmp.forward[i];
      }
      // 设置当前跳表层的位置。
      nodeForward[i] = nodeTmp;
    }

    // 按索引层的插入点位置插入节点
    for (int i = 0; i < level; i++) {
      tempNode.forward[i] = nodeForward[i].forward[i];
      nodeForward[i].forward[i] = tempNode;
    }

    // 设置当前跳表的层级
    if (levelNum < level) {
      this.levelNum = level;
    }
  }
    
  /**
   * 随机生成跳表的层级
   *
   * <p>跳表使用随机函数，做到大致分布均匀。
   *
   * @return 跳表的层级
   */
  private int randomLevel() {
    int level = 1;
    for (int i = 1; i < maxLevel; i++) {
      if (rand.nextInt() % 2 == 0) {
        level++;
      }
    }
    return level;
  }   
    
 /**
   * 检查一个数是否存在于跳表中
   *
   * @param value 当前待查找的值
   * @return true 存在，false 不存在
   */
  public boolean find(int value) {
    Node node = root;
    // 按层级向下进行索引搜索
    for (int i = levelNum - 1; i >= 0; i--) {
      if (node.forward[i] != null && node.forward[i].data < value) {
        node = node.forward[i];
      }
    }

    if (node.forward[0] != null && node.forward[0].data == value) {
      return true;
    } else {
      return false;
    }
  }
    
    
    
  /**
   * 执行删除操作
   *
   * @param targetValue 目标值
   */
  public void delete(int targetValue) {
    // 1,找到每一层中的删除的前一个顶点
    Node[] levelNodes = new Node[maxLevel];
    Node tempNode = root;
    for (int i = maxLevel - 1; i >= 0; i--) {
      while (tempNode.forward[i] != null && tempNode.forward[i].data < targetValue) {
        tempNode = tempNode.forward[i];
      }
      levelNodes[i] = tempNode;
    }

    // 将每层中的结果进行移除操作
    if (tempNode.forward[0] != null && tempNode.forward[0].data == targetValue) {
      for (int i = maxLevel - 1; i >= 0; i--) {
        if (levelNodes[i].forward[i] != null && levelNodes[i].forward[i].data == targetValue) {
          levelNodes[i].forward[i] = levelNodes[i].forward[i].forward[i];
        }
      }
    }
  }    
    
    
    
    
  /** 跳表的存储节点信息 */
  class Node {
    /** 用于存储数据,默认为-1 */
    private int data = -1;
    /** 跳表的节点 */
    private Node[] forward = new Node[maxLevel];
    /** 当前节点的最大层级 */
    private int nodeMaxLevel;

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("Node{");
      sb.append("data=").append(data);
      sb.append(", forward=").append(Arrays.toString(forward));
      sb.append(", nodeMaxLevel=").append(nodeMaxLevel);
      sb.append('}');
      return sb.toString();
    }
  }
}

```



#### 插入操作

以插入来讲解吧，如果明白了插入，其他都是操作都是类似的。

还是来举个例子吧，假设已经存在如下图所示的跳表，插入16，这个数据。

![跳表之动态插入原始内容](D:\doc\博客\数据结构与算法\跳表\跳表之动态插入原始内容.png)





这个节点的插入分为这样几步：

1. 构建16这个数据项的结点，生成随机的索引层数。示例生成为3层

   ![跳表之动态插入-构建的结点](D:\doc\博客\数据结构与算法\跳表\跳表之动态插入-构建的结点.png)

   

   









2. 根据随机函数生成的结果3，找到这3层中16这个结点的插入位置。将位置的结果记录到nodeForward中。

   ```java
    // 跳表的前指针位置记录
       Node[] nodeForward = new Node[level];
    // 1,将跳表都指向头节点
       for (int i = 0; i < level; i++) {
      nodeForward[i] = root;
       }
    // 进行跳表的查找，找到每一层该插入的位置。记录到nodeForward中
       Node nodeTmp = root;
    for (int i = level - 1; i >= 0; i--) {
         // 从最顶层的节点开始
      while (nodeTmp.forward[i] != null && nodeTmp.forward[i].data < value) {
           nodeTmp = nodeTmp.forward[i];
      }
         // 设置当前跳表层的位置。
      nodeForward[i] = nodeTmp;
       }
```
   
   
   
   ![跳表之动态插入-找前插入点](D:\doc\博客\数据结构与算法\跳表\跳表之动态插入-找前插入点.png)
   
   按层搜索的起点定为root，从root节点开始，将索引层找到的离插入点最近的结点记录到nodeForward中。
   
   
   
   3. 将构建的16这个数据及索引插入到跳表中，通过之前找到的位置nodeForward，将索引项的每层都插入指定层中，插入完成后，nodeForward变成了如下所示：
   
      ```java
   	// 按索引层的插入点位置插入节点
          for (int i = 0; i < level; i++) {
         tempNode.forward[i] = nodeForward[i].forward[i];
            nodeForward[i].forward[i] = tempNode;
       }
      ```
   
      
      
      ![跳表之动态插入-插入点 插入后的变化](D:\doc\博客\数据结构与算法\跳表\跳表之动态插入-插入点 插入后的变化.png)
      
      由于nodeForward数组是跳到中的一部分节点，当数据插入后，整个跳表就变成了如下所示：
      
      ![跳表之动态插入- 整个跳表的变化](D:\doc\博客\数据结构与算法\跳表\跳表之动态插入- 整个跳表的变化.png)
      
      就这样将数据插入到了跳表中。
      
      
   
   #### 查询操作
   
   还是以刚刚的跳表为例，来查找28这个数据吧，搜索相对于添加来说就简单多了，直接使用一个指针按层向下搜索即可
   
   1. 使用一个指针搜索向下搜索。
   
      ```java
          Node node = root;
          // 按层级向下进行索引搜索
          for (int i = levelNum - 1; i >= 0; i--) {
            if (node.forward[i] != null && node.forward[i].data < value) {
              node = node.forward[i];
            }
          }
      ```
   
      每一个层找仅查找比当前目标数小的数据，通过“i”（可以理解为down指针）即向下搜索。当搜索完毕，即找到了离28最近的值，26这个顶点。
   
      ![跳表之动态插入-查找操作-索引搜索](D:\doc\博客\数据结构与算法\跳表\跳表之动态插入-查找操作-索引搜索.png)
   
      2. 在原始链表那层向后检查下是否为目标值，即可确认是否为搜索目标值。
   
         ```
             if (node.forward[0] != null && node.forward[0].data == value) {
               return true;
             } else {
               return false;
             }
         ```
   
         
   
      搜索的原理也解释完毕了。
   
   
   
   ### 总结
   
   跳表使用空间换时间的设计思路，通过构建多级索引来提高查询效率。实现了基于链表的“二分查找”，跳表是一种动态数据结构，可以快速的插入、删除、查询，而且时间复杂度都为O(logn),跳表的空间复杂度为O(N).跳表相比对其他动态数据结构，链表支持区间搜索的时间复杂度也是O(logn)，这也可能是redis使用跳表来实现有序集合的一个原因，另一个原因，可能就是跳表实现起来更容易一点吧。
   
   

