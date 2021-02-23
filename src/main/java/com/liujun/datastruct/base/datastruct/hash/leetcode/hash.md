# 概览
哈希表 是一种使用 哈希函数 组织数据，以支持快速插入和搜索的数据结构。

有两种不同类型的哈希表：哈希集合和哈希映射。

哈希集合 是 集合 的实现方式之一，用于存储 非重复值。
哈希映射 是 映射 的实现之一，用于存储 (key, value) 键值对。
在 标准模板库 的帮助下，哈希表是 易于使用的。大多数常见语言（如 Java，C ++ 和 Python）都支持哈希集合和哈希映射。

通过选择合适的哈希函数，哈希表可以在插入和搜索方面展现出 出色的性能。

在本 LeetBook 中，我们将回答以下问题：

哈希表的 原理 是什么？
如何 设计 哈希表？如何解决 哈希冲突？
如何使用 哈希集合 解决与重复元素相关的问题？
如何使用 哈希映射 解决与滑动窗口相关的问题？
如何在使用哈希表时设计正确的键？
我们还将提供相关练习帮助你熟悉哈希表。

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/hash-table-plus/kar61/
来源：力扣（LeetCode）


## 1 设计哈希表
本章节中，我们将讨论哈希表的基本原理。

完成这一章后，您应该能够回答以下问题：

哈希表的 原理 是什么？
什么是 哈希冲突？哪些因素会影响 哈希函数？
了解 哈希集合 与 哈希映射 之间的区别。
如何正确使用 标准模板库 中的哈希集合及哈希映射？
在哈希表中，插入 和 查找 操作的复杂度是多少？

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/hash-table-plus/k29q7/




### 1.1 哈希表简介
哈希表简介
哈希表 是一种使用哈希函数组织数据的数据结构，它支持快速插入和搜索。

哈希表的原理
哈希表（又称散列表）的原理为：借助 哈希函数，将键映射到存储桶地址。更确切地说，

首先开辟一定长度的，具有连续物理地址的桶数组；

当我们插入一个新的键时，哈希函数将决定该键应该分配到哪个桶中，并将该键存储在相应的桶中；

当我们想要搜索一个键时，哈希表将使用哈希函数来找到对应的桶，并在该桶中进行搜索。

示例
![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\210cf5c195c2e0f624bb38accb0a543e6f5c184130c8267d20aad00500e65da2-1.png)

在示例中，我们使用 y = x ％ 5 作为哈希函数，来完成插入和搜索策略。

插入：我们通过哈希函数解析键，将它们映射到相应的桶中。

例如，根据哈希函数，1987 将分配给桶 2，而 24 分配给桶 4。
搜索：我们通过哈希函数解析键，得到桶地址，然后在该存储桶中搜索。

如果我们搜索 1987，我们将使用哈希函数将 1987 映射到 2。因此我们在桶 2 中搜索，我们在那个桶中成功找到了 1987。

例如，如果我们搜索 23，将映射 23 到 3，并在桶 3 中搜索。我们发现 23 不在桶 3 中，这意味着 23 不在哈希表中。

注意到键 1987 和 2 被映射到了同一个桶中，我们称之为 哈希冲突，哈希冲突与哈希函数有关，但又难以避免。有关处理哈希冲突的办法，我们将在下一节详细介绍。

负载因子
负载因子 又叫装填因子，是哈希表的一个重要参数，它反映了哈希表的装满程度。

在上面的例子中，我们发现有的桶处于空闲状态，而有的桶产生了哈希冲突，如何解决这个问题呢？

不难想到，哈希函数是导致冲突的原因之一，更进一步，如果我们增加桶的数量，再采用合适的哈希函数，可使发生冲突的可能性大大减小。

然而，桶的个数太多则会造成大量的空间浪费。比如一个公司的电话号码共有 10 个，每个电话号码是 00000000 到 99999999 内的任意八位数字，如果我们想到一种方案，将每个可能的电话号码作为一个桶，使得电话号码与桶一一对应，则一共需要创建 10^810 
8
  个桶。



实际利用桶的个数 与 桶的总数 的比值，称为负载因子。在这个实例中，负载因子太小甚至接近于 0，这样的方案显然是不现实的。

比较合理的负载因子是 0.7，如果数据量是 7，则会创建 10 个桶，以此类推。随着插入的数据量的增加，计算机会逐渐增加桶的个数，并选择合适的哈希函数，使得数据经过映射之后能均匀地分布在桶中。

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/hash-table-plus/kd67i/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。





### 1.2 设计哈希表时的问题

理解了什么是哈希表之后，我们再来讨论一下设计哈希表时应当注意的几个问题。

1. 哈希函数
哈希函数是哈希表中最重要的组件，用于将键映射到特定的桶。在上一篇文章中的示例中，我们使用 y = x % 5 作为散列函数，其中 x 是键值，y 是映射之后对应的桶的索引。

一个好的哈希函数，应当具备以下几个特点：

哈希函数的键与桶的对应关系具有确定性。也就是说，对于 key 所映射的桶地址，只由 key 键本身决定，而不由其他因素决定；

哈希函数不应太过复杂。太过于复杂的哈希函数将导致计算桶地址不能快速完成，从而无法快速定位桶；

映射结果的分布应具有均匀性。对于特定的桶空间，我们应尽量保证数据经过哈希函数映射之后，能够均匀地分布在桶的整个地址空间中。

下面是一些哈希函数的示例：

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\307ba4121a0a2f26651cc2214c7fd80d696f2aedaf7655f1b61dd9b2a7c2ed4c-1.png)

理想情况下，完美的哈希函数将是键和桶之间的一对一映射。然而，在大多数情况下，完美的哈希函数并不多见。一般来讲，结果分布越随机，越均匀的哈希函数，它的性能越好。一方面，如果分布过于集中在某些桶中，会加剧这些桶发生冲突的概率；另一方面，剩余的桶由于没有得到有效利用导致空间利用率较低。

2. 冲突解决
一般情况下，哈希函数会起到压缩键的地址空间的作用，设键的地址空间为 S，桶的地址空间为 T，则有 S \gg TS≫T。

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\89e7240c6c7aa9f1a5c3e514aeae560a9e17878ed67be8b3b439b2b486ff6096-2.png)

因此，经过映射之后，不同的数据会不可避免地分配到同一个桶中，这时便产生了冲突。例如，在我们之前的哈希函数 y = x ％ 5 中，1987 和 2 都分配给了桶 2，这是一个冲突。

为了避免哈希冲突，我们简要介绍几种解决冲突的办法。

线性试探法

线性试探法属于开放定址法的一种，除此之外，开放定址法还包括二次探测法、双重哈希法等。

所谓线性试探法，就是当插入键 key 时，如果发现桶单元 bucket[hash(key)] 已经被占用，则向下线性寻找，直到找到可以使用的空桶。具体说来，经过第 i 次试探之后，桶单元应为：

bucket[(hash(key) + i)\ mod\ M],\ \ i=1, 2, 3\dots
bucket[(hash(key)+i) mod M],  i=1,2,3…

例如对于键的集合为 {8, 9, 21, 17, 34, 21, 4}，哈希函数为 y = x % 8，集合中的前三个数 8, 9, 21，它们分别映射到 0, 1, 5 号桶中。

而对于第四个数 17，它本应映射到 1 号桶中，由于 1 号桶中已经保存了 9，因此将 17 保存在下一个空桶—— 2 号桶中。同理，34 余数为 2，会保存在 3 号桶中。

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\d5e6634e428261af42051ee1177cf97601f78749318cb934935987705dff9deb-3.png)

当 查找 某个键时，首先会通过哈希函数计算出桶的地址，然后比较该桶中保存的值是否为该键，如果不是，则继续向下寻找。如果查找到末尾，则会从头开始查找。

而 删除 某个键时，为了避免查找过程中出现信息丢失，会将删除位置标记为 deleted，这样当进行线性查找时，遇到 deleted 会继续向下查找而不会中断。

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\5348d0bdaca8ced199813335706677c023160d43e0dd0f4923766e20969a44c8-4.png)

链地址法

解决冲突的另一种办法是将桶内产生冲突的键串联成一个链表。仍然以前一个方法中的冲突为例，实现方式如下图所示。

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\9a91efc10a493b2e8c2aaac80da9b920ad89baba79278554bfe09c02bf9bdfc0-5.png)

与线性探测法类似，发生冲突时，由于都需要进行线性查找，因此会导致查找的成本增加。

再哈希法

再哈希法比较典型的应用是双重哈希法，即发生冲突时，通过使用另一个哈希函数来避免冲突。不难想到，另一个哈希函数在构造时，需要具备一些约束条件才能避免再次冲突。

然而，双重哈希法同样存在一些问题：

与线性试探法相比，双重哈希法会消耗较多的时间。

在双重哈希法中，删除会使问题变复杂，如果逻辑删除数量太多，则应重新构造哈希表。

公共溢出区法

顾名思义，公共溢出区法就是建立另一个哈希表 dict_overflow 作为公共溢出区，当发成冲突时则将该键保存在该哈希表中。

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\8af7fe012602ba3492b01dc563c08c81d5f9a3f54797d05b65d7a2af2965100c-6.png)

如图所示，若查找的键发生冲突，则在公共溢出区进行线性查找。

到目前为止，你应该对哈希表的概念有了基本的认识。接下来，我们将提供一些相关练习，来帮助你进一步认识哈希表。快来试试吧！

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/hash-table-plus/k080e/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。







### 1.3  705. 设计哈希集合

不使用任何内建的哈希表库设计一个哈希集合（HashSet）。

实现 MyHashSet 类：

void add(key) 向哈希集合中插入值 key 。
bool contains(key) 返回哈希集合中是否存在这个值 key 。
void remove(key) 将给定值 key 从哈希集合中删除。如果哈希集合中没有这个值，什么也不做。

示例：

输入：
["MyHashSet", "add", "add", "contains", "contains", "add", "contains", "remove", "contains"]
[[], [1], [2], [1], [3], [2], [2], [2], [2]]
输出：
[null, null, null, true, false, null, true, null, false]

解释：
MyHashSet myHashSet = new MyHashSet();
myHashSet.add(1);      // set = [1]
myHashSet.add(2);      // set = [1, 2]
myHashSet.contains(1); // 返回 True
myHashSet.contains(3); // 返回 False ，（未找到）
myHashSet.add(2);      // set = [1, 2]
myHashSet.contains(2); // 返回 True
myHashSet.remove(2);   // set = [1]
myHashSet.contains(2); // 返回 False ，（已移除）


提示：

0 <= key <= 106
最多调用 104 次 add、remove 和 contains 。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/design-hashset
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。





### 1.4 设计哈希映射

[706. 设计哈希映射](https://leetcode-cn.com/problems/design-hashmap/)

难度简单117收藏分享切换为英文接收动态反馈

不使用任何内建的哈希表库设计一个哈希映射

具体地说，你的设计应该包含以下的功能

- `put(key, value)`：向哈希映射中插入(键,值)的数值对。如果键对应的值已经存在，更新这个值。
- `get(key)`：返回给定的键所对应的值，如果映射中不包含这个键，返回-1。
- `remove(key)`：如果映射中存在这个键，删除这个数值对。


**示例：**

```
MyHashMap hashMap = new MyHashMap();
hashMap.put(1, 1);          
hashMap.put(2, 2);         
hashMap.get(1);            // 返回 1
hashMap.get(3);            // 返回 -1 (未找到)
hashMap.put(2, 1);         // 更新已有的值
hashMap.get(2);            // 返回 1 
hashMap.remove(2);         // 删除键为2的数据
hashMap.get(2);            // 返回 -1 (未找到) 
```


**注意：**

- 所有的值都在 `[0, 1000000]`的范围内。
- 操作的总数目在`[1, 10000]`范围内。
- 不要使用内建的哈希库。

通过次数21,677

提交次数36,929



[706. Design HashMap](https://leetcode-cn.com/problems/design-hashmap/)



Design a HashMap without using any built-in hash table libraries.

To be specific, your design should include these functions:

- `put(key, value)` : Insert a (key, value) pair into the HashMap. If the value already exists in the HashMap, update the value.
- `get(key)`: Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key.
- `remove(key)` : Remove the mapping for the value key if this map contains the mapping for the key.


**Example:**

```
MyHashMap hashMap = new MyHashMap();
hashMap.put(1, 1);          
hashMap.put(2, 2);         
hashMap.get(1);            // returns 1
hashMap.get(3);            // returns -1 (not found)
hashMap.put(2, 1);          // update the existing value
hashMap.get(2);            // returns 1 
hashMap.remove(2);          // remove the mapping for 2
hashMap.get(2);            // returns -1 (not found) 
```


**Note:**

- All keys and values will be in the range of `[0, 1000000]`.
- The number of operations will be in the range of `[1, 10000]`.
- Please do not use the built-in HashMap library.

通过次数21,677

提交次数36,929



```java
class MyHashMap {

    /** Initialize your data structure here. */
    public MyHashMap() {

    }
    
    /** value will always be non-negative. */
    public void put(int key, int value) {

    }
    
    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {

    }
    
    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {

    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
```



### 1.5 设计哈希表 - 解决方案

这里分别给出设计哈希集合和哈希映射的解决方案。为了解决哈希冲突，我们使用 二维数组 来实现哈希集合，数组中的每个元素都可以看作一个桶，而在每个桶中又包含一个一维数组，以便保存产生冲突的键。

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\bfdaf4d480214f39965605f02d144c0380e24d60d83800658cce754607c9909b-1.png)

哈希集合

```java
class MyHashSet {
    private final int MAX_LEN = 100000; // 初始化桶的数量
    private List<Integer>[] set;      // 使用数组实现哈希集合
    
    /** 返回对应的桶的索引 */
    private int getIndex(int key) {
        return key % MAX_LEN;
    }
    
    /** 在特定的桶中搜索键，如果该键不存在则返回 -1 */
    private int getPos(int key, int index) {
        // 每个桶中包含一个列表.
        List<Integer> temp = set[index];
        if (temp == null) {
            return -1;
        }
        // 遍历所有桶中的元素来寻找特定的键.
        for (int i = 0; i < temp.size(); ++i) {
            if (temp.get(i) == key) {
                return i;
            }
        }
        return -1;
    }
    
    public MyHashSet() {
        set = (List<Integer>[])new ArrayList[MAX_LEN];
    }
    
    public void add(int key) {
        int index = getIndex(key);
        int pos = getPos(key, index);
        if (pos < 0) {
            // 如果键不存在，则添加
            if (set[index] == null) {
                set[index] = new ArrayList<Integer>();
            }
            set[index].add(key);
        }
    }
    
    public void remove(int key) {
        int index = getIndex(key);
        int pos = getPos(key, index);
        if (pos >= 0) {
            // 如果键存在，则删除
            set[index].remove(pos);
        }
    }
    
    public boolean contains(int key) {
        int index = getIndex(key);
        int pos = getPos(key, index);
        return pos >= 0;
    }
}

```





哈希映射

```java
class MyHashMap {
    private final int MAX_LEN = 100000;             // 初始化桶的数量
    private List<Pair<Integer, Integer>>[] map;     // 使用数组实现哈希集合
    
    /** 返回指定桶的索引 */
    private int getIndex(int key) {
        return key % MAX_LEN;
    }
    
    /** 在特定的桶中寻找 key，如果不存在则返回 -1 */
    private int getPos(int key, int index) {
        // 每个桶中包含一个数组
        List<Pair<Integer, Integer>> temp = map[index];
        if (temp == null) {
            return -1;
        }
        // 遍历所有元素，在桶中寻找 key
        for (int i = 0; i < temp.size(); ++i) {
            if (temp.get(i).getKey() == key) {
                return i;
            }
        }
        return -1;
    }

    public MyHashMap() {
        map = (List<Pair<Integer, Integer>>[])new ArrayList[MAX_LEN];
    }
    
    public void put(int key, int value) {
        int index = getIndex(key);
        int pos = getPos(key, index);
        if (pos < 0) {
            // 如果不存在，则添加键值对 (key, value) 
            if (map[index] == null) {
                map[index] = new ArrayList<Pair<Integer, Integer>>();
            }
            map[index].add(new Pair(key, value));
        } else {
            // 如果键存在，更新 value
            map[index].set(pos, new Pair(key, value));
        }
    }
    
    /** 返回键的映射结果，如果不存在映射关系则返回 -1 */
    public int get(int key) {
        int index = getIndex(key);
        int pos = getPos(key, index);
        if (pos < 0) {
            return -1;
        } else {
            return map[index].get(pos).getValue();
        }
    }
    
    /** 如果存在映射关系，则移除键值对 */
    public void remove(int key) {
        int index = getIndex(key);
        int pos = getPos(key, index);
        if (pos >= 0) {
            map[index].remove(pos);
        }
    }
}

进一步探索
我们来看看 “删除” 操作，找到元素的位置之后，我们需要从数组中 “删除” 元素。

这是由数组本身的特点决定的。在探索卡片 数组的操作 中，我们对数组的 “删除” 操作进行了解释，即删除第 i 个元素，需要将后面的 n - i 个元素向前移动一个位置。其中，n 为数组的长度。

```

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\52b92b3ae1e369f23304ec1d46c1b25bc2077e8989c80be9fe257e8213b438f0-2.gif)

考虑 i 取不同值的情况：

删除末尾元素，移动 0 次；

删除开头元素，移动 n - 1 次。

平均而言，我们将要移动的次数为：

\frac{((n-1)+(n-2)+\dots+1+0)}{n}=\frac{n-1}{2}
n
((n−1)+(n−2)+⋯+1+0)
	
 = 
2
n−1
	


因此，找到待删除元素后，从数组中删除元素的时间复杂度将为 O(n)O(n)。

以下两种方案可以将时间复杂度从 O(n)O(n) 降低到 O(1)O(1)。

1. 交换

我们可以使用一种巧妙的策略。首先，用存储桶中的最后一个元素交换要移除的元素。然后删除最后一个元素。通过这种方法，我们成功地在 O(1)O(1) 的时间复杂度中去除了元素。

2. 链表

实现此目标的另一种方法是使用链表而不是数组列表。通过这种方式，我们可以在不修改数组中元素顺序的情况下删除元素。该策略时间复杂度为 O(1)O(1)。

然而，以上两种策略只改进了 找到元素之后 进行删除的时间复杂度，而对于整个删除操作，由于先要进行线性查找，所以时间复杂度仍为 O(n)O(n)，nn 为桶的大小。

要进一步改进此问题，我们可以使用 平衡二叉搜索树 作为桶，当进行删除操作时，则可以使用二分查找来快速定位被删除元素，使得删除操作的时间复杂度由 O(n)O(n) 减小为 O(log \ n)O(log n)，nn 为桶的大小。



### 1.6 复杂度分析-哈希表

在本文中，我们将讨论哈希表的性能。

复杂度分析
对于哈希表，插入和搜索的平均时间复杂度为 O(1)O(1)，空间复杂度为 O(n)O(n)。下面将进一步分析。

我们知道哈希表在设计的过程中总是要考虑冲突问题，这是因为不发生冲突几乎是不可能的。

例如我们经常谈到的——“在 5050 人中，至少有 22 人同一天生日的概率是 97\%97%”，这就意味着，如果我们将 365365 天看作 365365 个桶，每个桶保存一个人的生日，那么发生冲突概率则高达 97\%97%，而此时的装填因子仅为

\frac{50}{365}=13.7\%
365
50
	
 =13.7%

也就是说，为了使插入和查询的时间复杂度达到 O(1)O(1)，我们可以选择牺牲空间复杂度，增加桶的个数，使其不发生冲突。然而经过分析，我们发现即使装填因子很低的情况下，冲突仍然难以避免。因此就有了前面文章中关于冲突解决的各种办法，本质上是为了达到时间与空间的平衡。

既然如此，插入和搜索的时间复杂度就不会总是 O(1)O(1)，它将取决于桶内部存储发生冲突的键的方式。

如果桶内使用 链表 或者 数组，则最坏情况下，插入和搜索的时间复杂度为 O(n)O(n)；

如果桶内使用 高度平衡的二叉树 来保存键，则最坏情况下，插入和搜索的时间复杂度为 O(log \ n)O(log n)。

其中，nn 为桶的大小。且无论使用以上那种方式，平均时间复杂度 均为 O(1)O(1)。

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\64a5bde5a559c6557928419ee0c8cc81b2eea06dbde819706c88e44170d6f052-3.jpg)



对于空间复杂度，如果总共有 MM 个键，那么至少需要分配 MM 个桶，在实际的编程语言中，有各自的动态扩容机制，但总的来说，桶的个数与键呈线性关系，空间复杂度为 O(M)O(M)。




## 2 实际应用 - 哈希集合

哈希集合的操作
哈希集合是集合的实现方式之一，它是一种存储 不重复值 的数据结构。

我们分别提供了在 Java，C++ 和 Python 中使用哈希集的代码实例。你可以通过下面的实例对相关操作进行练习。

```java
public class Main {
    public static void main(String[] args) {
        // 1. 初始化哈希集合
        Set<Integer> hashSet = new HashSet<>();     
        // 2. 新增键
        hashSet.add(3);
        hashSet.add(2);
        hashSet.add(1);
        // 3. 删除键
        hashSet.remove(2);        
        // 4. 查询键是否包含在哈希集合中
        if (!hashSet.contains(2)) {
            System.out.println("键 2 不在哈希集合中");
        }
        // 5. 哈希集合的大小
        System.out.println("哈希集合的大小为: " + hashSet.size());     
        // 6. 遍历哈希集合
        for (Integer i : hashSet) {
            System.out.print(i + " ");
        }
        System.out.println("在哈希集合中");
        // 7. 清空哈希集合
        hashSet.clear();
        // 8. 查看哈希集合是否为空
        if (hashSet.isEmpty()) {
            System.out.println("哈希集合为空！");
        }
    }
}

```



### 2.1 使用哈希集合查重

我们知道，由于哈希集合中的元素是 不重复的，因此可以使用哈希集合来判断是否包含重复元素。

实例
让我们来看一个例子：

给定一个整数数组，判断该数组中是否包含重复元素。

这是一个典型的问题，用来判断某个数据结构中是否包含重复元素，或者对某个数据结构进行 去重 的操作。

解决这类问题的简单思路是遍历该数据结构，并将值插入到哈希集合中。如果该值已经存在于哈希集合中，表明发生了重复。

```java
/*
 * 使用哈希集合寻找重复元素的模板
 */
boolean findDuplicates(List<Type>& keys) {
    // 将 type 替换为 keys 的实际类型
    Set<Type> hashset = new HashSet<>();
    for (Type key : keys) {
        if (hashset.contains(key)) {
            return true;
        }
        hashset.insert(key);
    }
    return false;
}

```





### 2.2 [217. 存在重复元素](https://leetcode-cn.com/problems/contains-duplicate/)

给定一个整数数组，判断是否存在重复元素。

如果存在一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。

 

示例 1:

输入: [1,2,3,1]
输出: true
示例 2:

输入: [1,2,3,4]
输出: false
示例 3:

输入: [1,1,1,3,3,4,3,2,4,2]
输出: true
通过次数234,282提交次数425,210



217. Contains Duplicate

Given an array of integers, find if the array contains any duplicates.

Your function should return true if any value appears at least twice in the array, and it should return false if every element is distinct.

Example 1:

Input: [1,2,3,1]
Output: true
Example 2:

Input: [1,2,3,4]
Output: false
Example 3:

Input: [1,1,1,3,3,4,3,2,4,2]
Output: true

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/contains-duplicate
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

```java
class Solution {
    public boolean containsDuplicate(int[] nums) {

    }
}
```





### 2.3 [136. 只出现一次的数字](https://leetcode-cn.com/problems/single-number/)

给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。

说明：

你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？

示例 1:

输入: [2,2,1]
输出: 1
示例 2:

输入: [4,1,2,1,2]
输出: 4





[136. Single Number](https://leetcode-cn.com/problems/single-number/)

Given a **non-empty** array of integers `nums`, every element appears *twice* except for one. Find that single one.

**Follow up:** Could you implement a solution with a linear runtime complexity and without using extra memory?

 

**Example 1:**

```
Input: nums = [2,2,1]
Output: 1
```

**Example 2:**

```
Input: nums = [4,1,2,1,2]
Output: 4
```

**Example 3:**

```
Input: nums = [1]
Output: 1
```

 

**Constraints:**

- `1 <= nums.length <= 3 * 104`
- `-3 * 104 <= nums[i] <= 3 * 104`
- Each element in the array appears twice except for one element which appears only once.



```java
class Solution {
    public int singleNumber(int[] nums) {

    }
}
```





### 2.4 [349. 两个数组的交集](https://leetcode-cn.com/problems/intersection-of-two-arrays/)

给定两个数组，编写一个函数来计算它们的交集。

 

示例 1：

输入：nums1 = [1,2,2,1], nums2 = [2,2]
输出：[2]
示例 2：

输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
输出：[9,4]


说明：

输出结果中的每个元素一定是唯一的。
我们可以不考虑输出结果的顺序。





349. Intersection of Two Arrays

Given two arrays, write a function to compute their intersection.

**Example 1:**

```
Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2]
```

**Example 2:**

```
Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [9,4]
```

**Note:**

- Each element in the result must be unique.
- The result can be in any order.



```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {

    }
}
```



### 2.5 [202. 快乐数](https://leetcode-cn.com/problems/happy-number/)

难度简单536收藏分享切换为英文接收动态反馈

编写一个算法来判断一个数 `n` 是不是快乐数。

「快乐数」定义为：

- 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
- 然后重复这个过程直到这个数变为 1，也可能是 **无限循环** 但始终变不到 1。
- 如果 **可以变为** 1，那么这个数就是快乐数。

如果 `n` 是快乐数就返回 `true` ；不是，则返回 `false` 。

 

**示例 1：**

```
输入：19
输出：true
解释：
1^2 + 9^2 = 82
8^2 + 2^2 = 68
6^2 + 8^2 = 100
1^2 + 0^2 + 0^2 = 1
```

**示例 2：**

```
输入：n = 2
输出：false
```

 

**提示：**

- `1 <= n <= 231 - 1`



[202. Happy Number](https://leetcode-cn.com/problems/happy-number/)



Write an algorithm to determine if a number `n` is happy.

A **happy number** is a number defined by the following process:

- Starting with any positive integer, replace the number by the sum of the squares of its digits.
- Repeat the process until the number equals 1 (where it will stay), or it **loops endlessly in a cycle** which does not include 1.
- Those numbers for which this process **ends in 1** are happy.

Return `true` *if* `n` *is a happy number, and* `false` *if not*.

 

**Example 1:**

```
Input: n = 19
Output: true
Explanation:
12 + 92 = 82
82 + 22 = 68
62 + 82 = 100
12 + 02 + 02 = 1
```

**Example 2:**

```
Input: n = 2
Output: false
```

 

**Constraints:**

- `1 <= n <= 231 - 1`

```java
class Solution {
    public boolean isHappy(int n) {

    }
}
```





## 3 实际应用 - 哈希映射



我们知道，哈希集合主要用来存储 不重复元素，而 哈希映射 可以使用 (key, value) 键值对。

本章节中，我们将讨论哈希映射的相关问题，主要有以下两个方面：

根据值的结构不同设计不同的 (key, value) 键值对；
利用哈希表解决与 滑动窗口 相关的问题。

哈希表 是用来存储 (key, value) 键值对的一种实现。

下面的实例中，我们提供了 C++、Java 和 Python3 中有关哈希表的一些操作，如果你对这些操作不熟悉，可以浏览下方代码进行学习。

```java
public class Main {
    public static void main(String[] args) {
        // 1. 初始化哈希表
        Map<Integer, Integer> hashmap = new HashMap<>();
        // 2. 插入一个新的（键，值）对
        hashmap.putIfAbsent(0, 0);
        hashmap.putIfAbsent(2, 3);
        // 3. 插入一个新的（键，值）对，或者更新值
        hashmap.put(1, 1);
        hashmap.put(1, 2);
        // 4. 获得特定键对应的值
        System.out.println("键 1 对应的值为: " + hashmap.get(1));
        // 5. 删除键
        hashmap.remove(2);
        // 6. 检查键是否存在于哈希表中
        if (!hashmap.containsKey(2)) {
            System.out.println("键 2 不在哈希表中");
        }
        // 7. 哈希表的大小
        System.out.println("哈希表的大小为: " + hashmap.size()); 
        // 8. 遍历哈希表
        for (Map.Entry<Integer, Integer> entry : hashmap.entrySet()) {
            System.out.print("(" + entry.getKey() + "," + entry.getValue() + ") ");
        }
        System.out.println("在哈希表中");
        // 9. 清空哈希表
        hashmap.clear();
        // 10. 检查哈希表是否为空
        if (hashmap.isEmpty()) {
            System.out.println("哈希表为空！");
        }
    }
}


```



### 3.1 情景 Ⅰ - 构造哈希表

什么情况下会想到使用哈希表呢？当我们需要 同时得到关联信息 时，可以使用哈希表建立 key 与 value 的映射关系。

例如对于一个数组 A，我们想知道每个元素最后一次出现的位置，这时，元素和最后一次出现的位置信息相关联，则可以使用 {A[i]:A[i] 最后出现的下标} 这样的映射关系来构造哈希表。

学会构造哈希表，将是我们熟练应用哈希表的一个前提。构造哈希表前，我们需要仔细分析题目中相关因素之间的关系，然后为键和值选择合适的数据类型。

示例
这是一个经典问题：

两数之和：给定一个整数数组，返回两个元素的 索引，使这两个元素之和为 target。

如果我们枚举每一个下标 i 和 j，如果他们指向的元素等于 target，这时便找到了答案，但是时间复杂度将达到 O(N^2)O(N 
2
 )。

使用哈希表可以将时间复杂度降为 O(N)O(N)。首先，返回的是元素的 下标，所以可以考虑使用 {元素:下标} 的结构构造哈希表；其次，遍历数组的过程中，如果当前元素为 x， target - x 在之前已经遍历过，则表明 [下标(x), 下标(target - x)] 就是答案，否则，将 x:下标(x) 添加到哈希表中。

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\06424589b261472e89126c8c72b3625e0b3e4055950eab4a4c01f69e3531262a-1.png)



如图，当前 x 位于下标 3 且 x = 15，发现 target - x = 7 在哈希表中，所以直接返回 [1, 3]。

更多
注意到上面的例子中，我们使用了 {键:下标} 的形式构建哈希表，除此之外，根据值类别的不同，常用的构造方式还有以下几种：

{键:频次}：使用频率最高，将元素出现的次数作为值；

{键:数组}：如果一个键对应的信息是一组元素，可使用数组或链表存储。

{键:平面坐标}：某些矩阵类习题可能会存储坐标；

{键:其他}：一般出现在模拟题中，根据实际需要设计哈希表。

因此，当遇到某个问题可使用哈希表解决时，可以从以上几种构造方法中进行筛选。



### 3.2 [1. 两数之和](https://leetcode-cn.com/problems/two-sum/)

给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。

你可以按任意顺序返回答案。

 

示例 1：

输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
示例 2：

输入：nums = [3,2,4], target = 6
输出：[1,2]
示例 3：

输入：nums = [3,3], target = 6
输出：[0,1]


提示：

2 <= nums.length <= 103
-109 <= nums[i] <= 109
-109 <= target <= 109
只会存在一个有效答案



[1. Two Sum](https://leetcode-cn.com/problems/two-sum/)

Given an array of integers `nums` and an integer `target`, return *indices of the two numbers such that they add up to `target`*.

You may assume that each input would have ***exactly\* one solution**, and you may not use the *same* element twice.

You can return the answer in any order.

 

**Example 1:**

```
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Output: Because nums[0] + nums[1] == 9, we return [0, 1].
```

**Example 2:**

```
Input: nums = [3,2,4], target = 6
Output: [1,2]
```

**Example 3:**

```
Input: nums = [3,3], target = 6
Output: [0,1]
```

 

**Constraints:**

- `2 <= nums.length <= 103`
- `-109 <= nums[i] <= 109`
- `-109 <= target <= 109`
- **Only one valid answer exists.**

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {

    }
}
```



### 3.3 205 同构字符串

给定两个字符串 s 和 t，判断它们是否是同构的。

如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。

每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。

 

示例 1:

输入：s = "egg", t = "add"
输出：true
示例 2：

输入：s = "foo", t = "bar"
输出：false
示例 3：

输入：s = "paper", t = "title"
输出：true


提示：

可以假设 s 和 t 长度相同。



[205. Isomorphic Strings](https://leetcode-cn.com/problems/isomorphic-strings/)

Given two strings s and t, determine if they are isomorphic.

Two strings s and t are isomorphic if the characters in s can be replaced to get t.

All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character, but a character may map to itself.

 

Example 1:

Input: s = "egg", t = "add"
Output: true
Example 2:

Input: s = "foo", t = "bar"
Output: false
Example 3:

Input: s = "paper", t = "title"
Output: true


Constraints:

1 <= s.length <= 5 * 104
t.length == s.length
s and t consist of any valid ascii character.



```java
class Solution {
    public boolean isIsomorphic(String s, String t) {

    }
}
```





### 3.4 两个列表的最小索引总和

[599. 两个列表的最小索引总和](https://leetcode-cn.com/problems/minimum-index-sum-of-two-lists/)

假设Andy和Doris想在晚餐时选择一家餐厅，并且他们都有一个表示最喜爱餐厅的列表，每个餐厅的名字用字符串表示。

你需要帮助他们用最少的索引和找出他们共同喜爱的餐厅。 如果答案不止一个，则输出所有答案并且不考虑顺序。 你可以假设总是存在一个答案。

示例 1:

输入:
["Shogun", "Tapioca Express", "Burger King", "KFC"]
["Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"]
输出: ["Shogun"]
解释: 他们唯一共同喜爱的餐厅是“Shogun”。
示例 2:

输入:
["Shogun", "Tapioca Express", "Burger King", "KFC"]
["KFC", "Shogun", "Burger King"]
输出: ["Shogun"]
解释: 他们共同喜爱且具有最小索引和的餐厅是“Shogun”，它有最小的索引和1(0+1)。
提示:

两个列表的长度范围都在 [1, 1000]内。
两个列表中的字符串的长度将在[1，30]的范围内。
下标从0开始，到列表的长度减1。
两个列表都没有重复的元素。



[599. Minimum Index Sum of Two Lists](https://leetcode-cn.com/problems/minimum-index-sum-of-two-lists/)



假设Andy和Doris想在晚餐时选择一家餐厅，并且他们都有一个表示最喜爱餐厅的列表，每个餐厅的名字用字符串表示。

你需要帮助他们用最少的索引和找出他们共同喜爱的餐厅。 如果答案不止一个，则输出所有答案并且不考虑顺序。 你可以假设总是存在一个答案。

示例 1:

输入:
["Shogun", "Tapioca Express", "Burger King", "KFC"]
["Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"]
输出: ["Shogun"]
解释: 他们唯一共同喜爱的餐厅是“Shogun”。
示例 2:

输入:
["Shogun", "Tapioca Express", "Burger King", "KFC"]
["KFC", "Shogun", "Burger King"]
输出: ["Shogun"]
解释: 他们共同喜爱且具有最小索引和的餐厅是“Shogun”，它有最小的索引和1(0+1)。
提示:

两个列表的长度范围都在 [1, 1000]内。
两个列表中的字符串的长度将在[1，30]的范围内。
下标从0开始，到列表的长度减1。
两个列表都没有重复的元素。

```java
class Solution {
    public String[] findRestaurant(String[] list1, String[] list2) {

    }
}
```





## 4 实际应用 - 设计键

设计键
在前面的问题中，我们主要考虑了根据值的不同形式构造哈希表，而键的设计相对简单。然而在某些题目中，我们可能已经想到了哈希表的解法，但受限于 无法设计出合适的键，本章将对键的设计做出总结。

示例
我们来看一个例子：

给定一组字符串，将字母异位词组合在一起。

字母异位词指字母相同，但排列不同的字符串，比如 "ate" 与 "eat" 是一组字母异位词。如果单纯地把每个字符串作为键，显然没有起到任何作用。

解决方案
经过分析发现，同一组字母异位词中，如果按照字典序排列，得到的字符串相同，且长度相等。这时我们自然想到以 按照字典序排列后的字符串 作为键，这样就能合理区分出字母异位词了。例如对于 "ate" 和 "eat" 构造的哈希表为 {"aet":["ate", "eat"]}。

设计键的策略可能是非常 棘手的，接下来我们将提供一些习题，并对键的设计技巧做出总结。



### 4.1 字母异位词分组

[49. 字母异位词分组](https://leetcode-cn.com/problems/group-anagrams/)

给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。

示例:

输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
输出:
[
  ["ate","eat","tea"],
  ["nat","tan"],
  ["bat"]
]
说明：

所有输入均为小写字母。
不考虑答案输出的顺序。

[49. Group Anagrams](https://leetcode-cn.com/problems/group-anagrams/)

Given an array of strings strs, group the anagrams together. You can return the answer in any order.

An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

 

Example 1:

Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
Example 2:

Input: strs = [""]
Output: [[""]]
Example 3:

Input: strs = ["a"]
Output: [["a"]]


Constraints:

1 <= strs.length <= 104
0 <= strs[i].length <= 100
strs[i] consists of lower-case English letters.



```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {

    }
}
```



### 4.2 移位字符串分组

[249. 移位字符串分组](https://leetcode-cn.com/problems/group-shifted-strings/)

难度中等39收藏分享切换为英文接收动态反馈

给定一个字符串，对该字符串可以进行 “移位” 的操作，也就是将字符串中每个字母都变为其在字母表中后续的字母，比如：`"abc" -> "bcd"`。这样，我们可以持续进行 “移位” 操作，从而生成如下移位序列：

```
"abc" -> "bcd" -> ... -> "xyz"
```

给定一个包含仅小写字母字符串的列表，将该列表中所有满足 “移位” 操作规律的组合进行分组并返回。

 

**示例：**

```
输入：["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"]
输出：
[
  ["abc","bcd","xyz"],
  ["az","ba"],
  ["acef"],
  ["a","z"]
]
解释：可以认为字母表首尾相接，所以 'z' 的后续为 'a'，所以 ["az","ba"] 也满足 “移位” 操作规律。
```





[249. Group Shifted Strings](https://leetcode-cn.com/problems/group-shifted-strings/)

Given a string, we can "shift" each of its letter to its successive letter, for example: `"abc" -> "bcd"`. We can keep "shifting" which forms the sequence:

```
"abc" -> "bcd" -> ... -> "xyz"
```

Given a list of **non-empty** strings which contains only lowercase alphabets, group all strings that belong to the same shifting sequence.

**Example:**

```
Input: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
Output: 
[
  ["abc","bcd","xyz"],
  ["az","ba"],
  ["acef"],
  ["a","z"]
]
```



```java
class Solution {
    public List<List<String>> groupStrings(String[] strings) {

    }
}
```



### 4.3 有效的数独

[36. 有效的数独](https://leetcode-cn.com/problems/valid-sudoku/)

难度中等472收藏分享切换为英文接收动态反馈

判断一个 9x9 的数独是否有效。只需要**根据以下规则**，验证已经填入的数字是否有效即可。

1. 数字 `1-9` 在每一行只能出现一次。
2. 数字 `1-9` 在每一列只能出现一次。
3. 数字 `1-9` 在每一个以粗实线分隔的 `3x3` 宫内只能出现一次。

![img](https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/250px-Sudoku-by-L2G-20050714.svg.png)

上图是一个部分填充的有效的数独。

数独部分空格内已填入了数字，空白格用 `'.'` 表示。

**示例 1:**

```
输入:
[
  ["5","3",".",".","7",".",".",".","."],
  ["6",".",".","1","9","5",".",".","."],
  [".","9","8",".",".",".",".","6","."],
  ["8",".",".",".","6",".",".",".","3"],
  ["4",".",".","8",".","3",".",".","1"],
  ["7",".",".",".","2",".",".",".","6"],
  [".","6",".",".",".",".","2","8","."],
  [".",".",".","4","1","9",".",".","5"],
  [".",".",".",".","8",".",".","7","9"]
]
输出: true
```

**示例 2:**

```
输入:
[
  ["8","3",".",".","7",".",".",".","."],
  ["6",".",".","1","9","5",".",".","."],
  [".","9","8",".",".",".",".","6","."],
  ["8",".",".",".","6",".",".",".","3"],
  ["4",".",".","8",".","3",".",".","1"],
  ["7",".",".",".","2",".",".",".","6"],
  [".","6",".",".",".",".","2","8","."],
  [".",".",".","4","1","9",".",".","5"],
  [".",".",".",".","8",".",".","7","9"]
]
输出: false
解释: 除了第一行的第一个数字从 5 改为 8 以外，空格内其他数字均与 示例1 相同。
     但由于位于左上角的 3x3 宫内有两个 8 存在, 因此这个数独是无效的。
```

**说明:**

- 一个有效的数独（部分已被填充）不一定是可解的。
- 只需要根据以上规则，验证已经填入的数字是否有效即可。
- 给定数独序列只包含数字 `1-9` 和字符 `'.'` 。
- 给定数独永远是 `9x9` 形式的。

通过次数119,275

提交次数192,150



[36. Valid Sudoku](https://leetcode-cn.com/problems/valid-sudoku/)

难度中等472收藏分享切换为中文接收动态反馈

Determine if a `9 x 9` Sudoku board is valid. Only the filled cells need to be validated **according to the following rules**:

1. Each row must contain the digits `1-9` without repetition.
2. Each column must contain the digits `1-9` without repetition.
3. Each of the nine `3 x 3` sub-boxes of the grid must contain the digits `1-9` without repetition.

**Note:**

- A Sudoku board (partially filled) could be valid but is not necessarily solvable.
- Only the filled cells need to be validated according to the mentioned rules.

 

**Example 1:**

![img](https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/250px-Sudoku-by-L2G-20050714.svg.png)

```
Input: board = 
[["5","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
Output: true
```

**Example 2:**

```
Input: board = 
[["8","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
Output: false
Explanation: Same as Example 1, except with the 5 in the top left corner being modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
```

 

**Constraints:**

- `board.length == 9`
- `board[i].length == 9`
- `board[i][j]` is a digit or `'.'`.

```java
class Solution {
    public boolean isValidSudoku(char[][] board) {

    }
}
```





### 4.4 寻找重复的子树

[652. 寻找重复的子树](https://leetcode-cn.com/problems/find-duplicate-subtrees/)

难度中等231收藏分享切换为英文接收动态反馈

给定一棵二叉树，返回所有重复的子树。对于同一类的重复子树，你只需要返回其中任意**一棵**的根结点即可。

两棵树重复是指它们具有相同的结构以及相同的结点值。

**示例 1：**

```
        1
       / \
      2   3
     /   / \
    4   2   4
       /
      4
```

下面是两个重复的子树：

```
      2
     /
    4
```

和

```
    4
```

因此，你需要以列表的形式返回上述重复子树的根结点。

通过次数18,450

提交次数33,122



[652. Find Duplicate Subtrees](https://leetcode-cn.com/problems/find-duplicate-subtrees/)

难度中等231收藏分享切换为中文接收动态反馈

Given the `root` of a binary tree, return all **duplicate subtrees**.

For each kind of duplicate subtrees, you only need to return the root node of any **one** of them.

Two trees are **duplicate** if they have the **same structure** with the **same node values**.

 

**Example 1:**

![img](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\e1.jpg)

```
Input: root = [1,2,3,4,null,2,4,null,null,4]
Output: [[2,4],[4]]
```

**Example 2:**

![img](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\e2.jpg)

```
Input: root = [2,1,1]
Output: [[1]]
```

**Example 3:**

![img](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\img\e33.jpg)

```
Input: root = [2,2,2,3,null,3,null]
Output: [[2,3],[3]]
```

 

**Constraints:**

- The number of the nodes in the tree will be in the range `[1, 10^4]`
- `-200 <= Node.val <= 200`

通过次数18,450

提交次数33,122



```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        
    }
}
```





### 4.5 稀疏矩阵的乘法

[311. 稀疏矩阵的乘法](https://leetcode-cn.com/problems/sparse-matrix-multiplication/)

难度中等41收藏分享切换为英文接收动态反馈

给你两个 [稀疏矩阵](https://baike.baidu.com/item/稀疏矩阵) **A** 和 **B**，请你返回 **AB** 的结果。你可以默认 **A** 的列数等于 **B** 的行数。

请仔细阅读下面的示例。

 

**示例：**

```
输入：

A = [
  [ 1, 0, 0],
  [-1, 0, 3]
]

B = [
  [ 7, 0, 0 ],
  [ 0, 0, 0 ],
  [ 0, 0, 1 ]
]

输出：

     |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
                  | 0 0 1 |
```

通过次数2,372

提交次数3,111



[311. Sparse Matrix Multiplication](https://leetcode-cn.com/problems/sparse-matrix-multiplication/)

难度中等41收藏分享切换为中文接收动态反馈

Given two [sparse matrices](https://en.wikipedia.org/wiki/Sparse_matrix) **A** and **B**, return the result of **AB**.

You may assume that **A**'s column number is equal to **B**'s row number.

**Example:**

```
Input:

A = [
  [ 1, 0, 0],
  [-1, 0, 3]
]

B = [
  [ 7, 0, 0 ],
  [ 0, 0, 0 ],
  [ 0, 0, 1 ]
]

Output:

     |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
                  | 0 0 1 |
```

 

**Constraints:**

- `1 <= A.length, B.length <= 100`
- `1 <= A[i].length, B[i].length <= 100`
- `-100 <= A[i][j], B[i][j] <= 100`



```java
class Solution {
    public int[][] multiply(int[][] A, int[][] B) {

    }
}
```





