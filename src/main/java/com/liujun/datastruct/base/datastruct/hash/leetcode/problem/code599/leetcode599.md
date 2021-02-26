# leetcode 599. 两个列表的最小索引总和 求解思路

先来看下题目:
>#### [599. 两个列表的最小索引总和](https://leetcode-cn.com/problems/minimum-index-sum-of-two-lists/)
>
>假设Andy和Doris想在晚餐时选择一家餐厅，并且他们都有一个表示最喜爱餐厅的列表，每个餐厅的名字用字符串表示。
>
>你需要帮助他们用**最少的索引和**找出他们**共同喜爱的餐厅**。 如果答案不止一个，则输出所有答案并且不考虑顺序。 你可以假设总是存在一个答案。
>
>**示例 1:**
>
>```
>输入:
>["Shogun", "Tapioca Express", "Burger King", "KFC"]
>["Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"]
>输出: ["Shogun"]
>解释: 他们唯一共同喜爱的餐厅是“Shogun”。
>```
>
>**示例 2:**
>
>```
>输入:
>["Shogun", "Tapioca Express", "Burger King", "KFC"]
>["KFC", "Shogun", "Burger King"]
>输出: ["Shogun"]
>解释: 他们共同喜爱且具有最小索引和的餐厅是“Shogun”，它有最小的索引和1(0+1)。
>```
>
>**提示:**
>
>1. 两个列表的长度范围都在 [1, 1000]内。
>2. 两个列表中的字符串的长度将在[1，30]的范围内。
>3. 下标从0开始，到列表的长度减1。
>4. 两个列表都没有重复的元素。



这个题目在最开始还没有理解其真正的含义。导致我走了不少的弯路。我将我的求解思路写下来，记录下这一求解的过程。



## 方案1：直接用map记录返回

第一眼看这个题目的时候，我的第一反应这不就是找出最喜欢的餐厅列表么，那不就是求公共集么。我就首先想到了，使用map记录下输入1，然后再输入2中查找，如果能查找到第一个，就直接返回第一个。

```java
class Solution {
  public String[] findRestaurant(String[] list1, String[] list2) {

    if (list1 == null || list2 == null) {
      return new String[0];
    }

    Set<String> dataSet = new HashSet<>(list2.length);
    for (int i = 0; i < list2.length; i++) {
      dataSet.add(list2[i]);
    }

    // 查找共同的爱好
    for (int i = 0; i < list1.length; i++) {
      if (dataSet.contains(list1[i])) {
        return new String[] {list1[i]};
      }
    }

    return new String[0];
  }
}
```

但现实很快就打脸了，当我提交运行后，很快就提示解答错误：

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\problem\code599\img\方案1map解答错误.png)

我这边意部识到程序有问题。那问题在哪里呢？我没有想明白。这时候我又才回过头来将题目给再读一遍。

注意到这一行字

>你需要帮助他们用**最少的索引和**找出他们**共同喜爱的餐厅**

索引和，这一个原来没有注意到的词，却是加粗的提示。还是审题不清啊。那什么是索引和呢？数组的索引就是数组的下标，换句话说就是下标的和。而且是在两个数组中查找下标和最小的元素。经过我这再次的审题才发现在真正的求解问题。

题目下次读三遍吧！这么重要的提示，还是被遗漏了。



## 方案2： 使用有序map求解

经过两次的审题发现，这个题目要求解的就是最小的索引和，但相同的索引和可能会有多个，这也正是问题求解的复杂点之一。那如何求解呢？

### 2.1 填充Map的元素和索引号

还是使用一个Map记录，以元素为key，索引下标为值。这段程序表示就是:

```java
    // 使用map记录下索引号
    Map<String, Integer> srcMap = new HashMap<>(list1.length);
    // 填充下索引号
    for (int i = 0; i < list1.length; i++) {
      srcMap.put(list1[i], i);
    }
```

### 2.2  按元素和填充有序map

接下来就是求解的关键了。

我使用的是一个有序map来记录，以索引和为key，以元素为集合类型值。为什么要这么设计呢？我的一个想法是，既然是求解索引和最小的元素，那就以索引和为key，所有的元素为值，再加上索引和为Int类型，有序map默认从小到大排序。所有首个元素即为要求解的元素。

```java
TreeMap<Integer, List<String>> dataMap = new TreeMap<>();
```

数据结构想好了，那接下来就是将元素填充到这个Map中了。这个逻辑就是遍历输入2，检查元素必须要在元素1的map中，然后将相同的索引和相加，再存储到有序map中.

```java
    // 使用有序map来记录下，有序和相同的数据集
    TreeMap<Integer, List<String>> dataMap = new TreeMap<>();
    Integer minSum;
    for (int i = 0; i < list2.length; i++) {
      //首先元素必须在输入1中
      minSum = srcMap.get(list2[i]);
      if (null == minSum) {
        continue;
      }

      // 计算索引和
      int key = i + minSum;
      // 使用索引和作为key去查找
      List<String> continueList = dataMap.get(key);
      // 当数据不存在时，则加入
      if (null == continueList) {
        continueList = new ArrayList<>();
        dataMap.put(key, continueList);
      }
      continueList.add(list2[i]);
    }
```



### 2.3 返回求解的结果

当元素所有都被填充到这个有序Map中后，有序和的最小的也已经找到了，那就是这个有序map的首个元素。接下来就是将这个元素转换返回即可了。

```java
    List<String> dataList = dataMap.get(dataMap.firstKey());
    String[] dataRsp = new String[dataList.size()];
    for (int i = 0; i < dataList.size(); i++) {
      dataRsp[i] = dataList.get(i);
    }
```



### 2.4 完整的代码

最后再来看下完整的代码:

```java
public class Solution {

  public String[] findRestaurant(String[] list1, String[] list2) {

    if (list1 == null || list2 == null) {
      return new String[0];
    }

    // 使用map记录下索引号
    Map<String, Integer> srcMap = new HashMap<>(list1.length);
    // 填充下索引号
    for (int i = 0; i < list1.length; i++) {
      srcMap.put(list1[i], i);
    }

    // 使用有序map来记录下，有序和相同的数据集
    TreeMap<Integer, List<String>> dataMap = new TreeMap<>();
    Integer minSum;
    for (int i = 0; i < list2.length; i++) {
      //首先元素必须在输入1中
      minSum = srcMap.get(list2[i]);
      if (null == minSum) {
        continue;
      }

      // 计算索引和
      int key = i + minSum;
      // 使用索引和作为key去查找
      List<String> continueList = dataMap.get(key);
      // 当数据不存在时，则加入
      if (null == continueList) {
        continueList = new ArrayList<>();
        dataMap.put(key, continueList);
      }
      continueList.add(list2[i]);
    }

    List<String> dataList = dataMap.get(dataMap.firstKey());
    String[] dataRsp = new String[dataList.size()];
    for (int i = 0; i < dataList.size(); i++) {
      dataRsp[i] = dataList.get(i);
    }

    return dataRsp;
  }
}
```



### 2.5 提交结果 

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\problem\code599\img\有序map的求解方案的结果.png)



## 方案3： 优化有序map的求解方案

### 3.1 只保留最小索引和

当使用有序方案运行出结果后。我就发现一个问题，那就是既然我们求解的是最小的一组元素，我们可以跳过那些非最小元素的记录，中间那些非最小元素的，就可以直接抛弃了。不用记录了。

```java
public class Solution {

  public String[] findRestaurant(String[] list1, String[] list2) {

    if (list1 == null || list2 == null) {
      return new String[0];
    }

    // 使用map来求解，list1的索引和记录
    Map<String, Integer> srcMap = new HashMap<>(list1.length);

    for (int i = 0; i < list1.length; i++) {
      srcMap.put(list1[i], i);
    }

    Integer sumMin = null;
    List<String> continueList = new ArrayList<>();
    int minSum;

    for (int i = 0; i < list2.length; i++) {
      if (srcMap.containsKey(list2[i])) {
        minSum = srcMap.get(list2[i]);
        int key = i + minSum;
        if (sumMin == null) {
          sumMin = key;
        }
        // 当数据比已知的最小数据大时，则跳过处理
        else if (key > sumMin) {
          continue;
        }
        // 如果找到比当前小的，则清空数据集，重新加入
        else if (key < sumMin) {
          sumMin = key;
          continueList.clear();
        }

        if (key == sumMin) {
          continueList.add(list2[i]);
        }
      }
    }

    String[] dataRsp = new String[continueList.size()];
    for (int i = 0; i < continueList.size(); i++) {
      dataRsp[i] = continueList.get(i);
    }
    return dataRsp;
  }
}
```





在运行上有了4毫秒的一个提升

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\problem\code599\img\运行优先方案3.1.png)



### 3.2 优先存储map避免扩容

```java
// 使用map来求解，list1的索引和记录,阈值设置为1，避免扩容
Map<String, Integer> srcMap = new HashMap<>(list1.length, 1);
```





### 3.3 最终优化的代码

```java
public class Solution {

  public String[] findRestaurant(String[] list1, String[] list2) {

    if (list1 == null || list2 == null) {
      return new String[0];
    }

    // 使用map来求解，list1的索引和记录,阈值设置为1，避免扩容
    Map<String, Integer> srcMap = new HashMap<>(list1.length, 1);
    for (int i = 0; i < list1.length; i++) {
      srcMap.put(list1[i], i);
    }

    Integer sumMin = null;
    List<String> continueList = new ArrayList<>();
    int minSum;

    for (int i = 0; i < list2.length; i++) {
      if (srcMap.containsKey(list2[i])) {
        minSum = srcMap.get(list2[i]);
        int key = i + minSum;
        if (sumMin == null) {
          sumMin = key;
        }
        // 当数据比已知的最小数据大时，则跳过处理
        else if (key > sumMin) {
          continue;
        }
        // 如果找到比当前小的，则清空数据集，重新加入
        else if (key < sumMin) {
          sumMin = key;
          continueList.clear();
        }

        if (key == sumMin) {
          continueList.add(list2[i]);
        }

        // 如果当前最小的数，小于或者等于索引，则可以返回
        if (!continueList.isEmpty() && sumMin <= i) {
          break;
        }
      }
    }

    String[] dataRsp = new String[continueList.size()];
    for (int i = 0; i < continueList.size(); i++) {
      dataRsp[i] = continueList.get(i);
    }
    return dataRsp;
  }
}
```

