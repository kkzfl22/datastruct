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

