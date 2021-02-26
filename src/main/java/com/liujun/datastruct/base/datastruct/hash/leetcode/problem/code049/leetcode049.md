# leetcode 49 字母异位词分组 求解思路

## 1. 题目

>#### [49. 字母异位词分组](https://leetcode-cn.com/problems/group-anagrams/)
>
>给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
>
>**示例:**
>
>```
>输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
>输出:
>[
>  ["ate","eat","tea"],
>  ["nat","tan"],
>  ["bat"]
>]
>```
>
>**说明：**
>
>- 所有输入均为小写字母。
>- 不考虑答案输出的顺序。



## 2. 解决方案1：字符排序

这个就相对来说比较容易了，对每个字符进行排序，这样所有字符就变成了相同顺序，相同顺序的字符就可以做比较了，再使用一个Map将相同顺序的记录下来，就可以进行异位词分组了。

```java
public class Solution  {

  /**
   * 字母异位词分级
   *
   * @param strs
   * @return
   */
  public List<List<String>> groupAnagrams(String[] strs) {
    if (null == strs || strs.length == 0) {
      return Collections.emptyList();
    }

    // 以排序过的字母为key，展位字符集为值
    Map<String, List<String>> dataMap = new HashMap<>(strs.length, 1);
    for (int i = 0; i < strs.length; i++) {
      char[] dataItem = strs[i].toCharArray();
      // 执行排序操作
      Arrays.sort(dataItem);

      String key = new String(dataItem);
      List<String> dataSameList = dataMap.get(key);
      if (dataSameList == null) {
        dataSameList = new ArrayList<>();
        dataMap.put(key, dataSameList);
      }
      dataSameList.add(strs[i]);
    }

    // 转换结果输出
    List<List<String>> dataList = new ArrayList<>(dataMap.size());
    for (Map.Entry<String, List<String>> dataItem : dataMap.entrySet()) {
      dataList.add(dataItem.getValue());
    }

    return dataList;
  }
}
```

执行结果:

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\problem\code049\img\使用排序字符的方案.png)



## 3.失败解决方案：使用字符的值做乘法.

这个解决方案是将26个英文字母影射到一个数字中，然后对这个数字做乘法操作。

```java
	  long sumKey = 1;
      for (int j = 0; j < strs[i].length(); j++) {
        sumKey *= (strs[i].charAt(j));
      }
```

然后再以这个乘法的结果为key，记录到结果集中,完整的代码实现出来就是这样子的

```java
  public List<List<String>> groupAnagrams(String[] strs) {
    if (null == strs || strs.length == 0) {
      return Collections.emptyList();
    }

    // 以计算的乘法结果为key，异位词集为值
    Map<Long, List<String>> dataMap = new HashMap<>(strs.length, 1);
    for (int i = 0; i < strs.length; i++) {
      char[] dataItem = strs[i].toCharArray();

      // 乘积计算
      long sumKey = 1;
      for (int j = 0; j < strs[i].length(); j++) {
        sumKey *= (strs[i].charAt(j));
      }

      // 记录保存至map中
      String key = new String(dataItem);
      List<String> dataSameList = dataMap.get(key);
      if (dataSameList == null) {
        dataSameList = new ArrayList<>();
        dataMap.put(sumKey, dataSameList);
      }
      dataSameList.add(strs[i]);
    }

    // 转换结果输出
    List<List<String>> dataList = new ArrayList<>(dataMap.size());
    for (Map.Entry<Long, List<String>> dataItem : dataMap.entrySet()) {
      dataList.add(dataItem.getValue());
    }

    return dataList;
  }
```



自以为这是OK的，但失败的方案就是失败的方案。这个是不对的。

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\problem\code049\img\错误的key的计算方案.png)

当反过头来检查这个方案的时候，会发现一个致命的问题，那就是不同的数字会得到相同的乘积结果。从而导致不同的字符，却得到了相同的乘积。



## 4 解决方案2：啥希值计算做key方案

上面失败的解决方案，没有通过的原因是不同的字符相乘得到了相同的结果。那既然知道了原因也给出解决方案。既然直接用字符可能会得到相同的乘法结果。那如果采用其他随机分布的哈希值是不是就没有这个问题了呢。带着这个思考，我实现了这个方案，那就是采用Objects.hashCode的计算方式重新计算每个字符的的值。光说还是太抽象，直接上代码:

```java
      // 计算乘积
      long sumKey = 1;
      for (int j = 0; j < strs[i].length(); j++) {
        sumKey = sumKey * Objects.hashCode(HASH_PREFIX_DATA + strs[i].charAt(j));
      }

```

再来看下完整的代码:

```java
  /** 用做哈希计算的字符 */
  private static final String HASH_PREFIX_DATA = "S";

  /**
   * 字母异位词分组
   *
   * @param strs
   * @return
   */
  public List<List<String>> groupAnagrams(String[] strs) {
    if (null == strs || strs.length == 0) {
      return Collections.emptyList();
    }

    // 以乘积做为key，以异位字符集做值
    Map<Long, List<String>> dataMap = new HashMap<>(strs.length, 1);
    for (int i = 0; i < strs.length; i++) {

      // 计算乘积
      long sumKey = 1;
      for (int j = 0; j < strs[i].length(); j++) {
        sumKey = sumKey * Objects.hashCode(HASH_PREFIX_DATA + strs[i].charAt(j));
      }

      // 保存结果
      List<String> dataSameList = dataMap.get(sumKey);
      if (dataSameList == null) {
        dataSameList = new ArrayList<>();
        dataMap.put(sumKey, dataSameList);
      }
      dataSameList.add(strs[i]);
    }

    // 转换结果输出
    List<List<String>> dataList = new ArrayList<>(dataMap.size());
    for (Map.Entry<Long, List<String>> dataItem : dataMap.entrySet()) {
      List<String> value = dataItem.getValue();
      dataList.add(value);
    }

    return dataList;
  }
```

这个是可以通过的

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\problem\code049\img\使用Objects的hash方案.png)



### 4.1 优化

刚刚在代码中还有没有可以优化的地方呢？

这个是有的，那就是Map的存储，与结果实现上我们存储了一遍，然后再构建结果，那我们能不能直接就构建结果呢？

带着这个问题我思考了下，发现是可以的。我们最终要返回的是一个List<List<String>>，这个一个动态数组，我们是可以以下标访问集合元素的。既然可以以下标访问，我们为什么何不直接在Map中存储下标，当找到相同的数据，直接向这个结果中添加。

下面这是我实现出来的代码:

```java
  /** 用做哈希计算的字符 */
  private static final String HASH_PREFIX_DATA = "S";

  /**
   * 字母异位词分级
   *
   * @param strs
   * @return
   */
  public List<List<String>> groupAnagrams(String[] strs) {
    if (null == strs || strs.length == 0) {
      return Collections.emptyList();
    }

    // 以排序过的字母为key，展位字符集为值
    int listIndex = 0;
    Map<Long, Integer> dataMap = new HashMap<>(strs.length, 1);
    List<List<String>> dataResult = new ArrayList<>(strs.length);
    for (int i = 0; i < strs.length; i++) {

      // 执行排序操作
      long sumKey = 1;
      for (int j = 0; j < strs[i].length(); j++) {
        sumKey = sumKey * Objects.hashCode(HASH_PREFIX_DATA + strs[i].charAt(j));
      }

      Integer dataIndex = dataMap.get(sumKey);
      if (null == dataIndex) {
        dataMap.put(sumKey, listIndex++);
        List<String> subList = new ArrayList<>();
        subList.add(strs[i]);
        dataResult.add(subList);
      } else {
        dataResult.get(dataIndex).add(strs[i]);
      }
    }

    return dataResult;
  }
```

## 5.解决方案3：使用质数做哈希计算

老实说这个方案不是我想出来的，是参数了leetcode其他大神的解法，这个的核心思想是：将26个英文字母影射到一个质数的数组中，再对质数进行乘法求解就可以得到一个值，以这个值做key来计算，先来看下核心做哈希key的计算吧:

```java
    // 数据影射
    int[] dataHash = {
      2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89,
      97, 101
    };
    
   	 // 进行哈希计算
      long hashKey = 1;
      for (int j = 0; j < strs[i].length(); j++) {
        char item = strs[i].charAt(j);
        hashKey *= dataHash[item - 97];
      }
```

通过这样一个影射的计算就可以大幅减少计算，

再来看下完整的代码：

```java
  public List<List<String>> groupAnagrams(String[] strs) {

    if (null == strs || strs.length < 1) {
      return Collections.emptyList();
    }

    // 数据影射
    int[] dataHash = {
      2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89,
      97, 101
    };

    // 集合记录下返回结果
    List<List<String>> resultList = new ArrayList<>(strs.length);
    // map以计算的索引值为key，以返回的结果集的索引的下标为值,避免扩容引起的开销
    Map<Long, Integer> dataMap = new HashMap<>(strs.length, 1);

    int putIndex = 0;
    for (int i = 0; i < strs.length; i++) {
      // 进行哈希计算
      long hashKey = 1;
      for (int j = 0; j < strs[i].length(); j++) {
        char item = strs[i].charAt(j);
        hashKey *= dataHash[item - 97];
      }

      // 通过哈希计算得到索引
      Integer index = dataMap.get(hashKey);
      if (index != null) {
        resultList.get(index).add(strs[i]);
      }
      // 当此键不存在时,保存到索引中
      else {
        dataMap.put(hashKey, putIndex++);
        // 在结果集中加入一个
        List<String> subList = new ArrayList<>();
        subList.add(strs[i]);
        resultList.add(subList);
      }
    }

    return resultList;
  }
```



看下结果:

![](D:\java\workspace\dataStruct\git\datastruct\src\main\java\com\liujun\datastruct\base\datastruct\hash\leetcode\problem\code049\img\使用质数做key的计算方案.png)

这个可以有几倍的性能提升，由原来的16毫秒提升到了4毫秒。



## 总结：

这就是我对这个字母异位词分组的一个求解的多种解决方案。我来总结下吧，这个字符排序方案，最先想到一个方案，简单，速度也不弱。其他的方案都是同一个类型的方案，都是使用个数字结果做为哈希的key，异位词为值，使用Objects.hashCode的计算较通用，但性能较弱。使用质数做影射射的方案相对于本题来说最好。