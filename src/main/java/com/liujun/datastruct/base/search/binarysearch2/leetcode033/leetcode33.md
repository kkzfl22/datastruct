# leetcode 33. 搜索旋转排序数组

## 题目
给你一个整数数组 nums ，和一个整数 target 。 

该整数数组原本是按升序排列，但输入时在预先未知的某个点上进行了旋转。
（例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] ）。 
请你在数组中搜索 target ，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。 
示例 1： 
输入：nums = [4,5,6,7,0,1,2], target = 0
输出：4
示例 2： 
输入：nums = [4,5,6,7,0,1,2], target = 3
输出：-1 
 示例 3： 
输入：nums = [1], target = 0
输出：-1

 提示： 
 1 <= nums.length <= 5000 , 
 -10^4 <= nums[i] <= 10^4 ,
 nums 中的每个值都 独一无二 , 
 nums 肯定会在某个点上旋转 ， 
 -10^4 <= target <= 10^4 ，

 
 ## 解题思路
 这是一个二分查找的变种应用
 三个指针，
 low，区间开始的位置
 mid, 区间中间的位置
 high，区间结束的位置 
 
 ![二分过程](file:///D:\doc\博客\数据结构与算法\二分查找/leetcode-33-情况说明.png)
 
### 情况1：nums[mid] > nums[low]
> 
    左区间有序，右区间无序。 
    1. 当区间中间值小于目标值时，即数据在mid与4之间位置，low=mid+1，下次右区间搜索。
    2. 当区间中间值大于目标值时，数据可能在1与2之间，或者在3与4之间，这如何区分？
        这里可使用nums[low]加以判断
            当nums[low] > target，说明在1与2之间，则low=mid+1，下次右区间搜索。
            当nums[low] < target, 说明在3与mid之间，则hig=mid-1,下次左区间搜索。
    3. 当区间中间值等于目标值时，这就是我们要找的数据。   
>


### 情况2：nums[mid] <= nums[low]
>
    左区间无序，右区间有序。
    1. 当区间中间值小于目标值时，数据可能存在1与2之间，或者3与4之彰，这如何区分
        这里可使用nums[high]加以判断
            当nums[high] < target,数据在3与4之间，则high=mid-1,下次左区间搜索。
            当nums[high] > target,数据在mid与2之间，则low=mid+1,下次右区间搜索。
    2. 当区间中间值大于目标值时，数据只能在1与mid之间，则high=mid-1,下次左区间搜索。
    3. 当区间中间值等于目标值时，这就是我们要找的数据。   
>


代码实现
```java
public class Solution {

  public int search(int[] nums, int target) {

    if (nums == null || nums.length <= 0) {
      return -1;
    }

    int low = 0, mid, high = nums.length - 1;

    while (low <= high) {
      mid = low + (high - low) / 2;

      // 最左侧匹配的情况
      if (nums[low] == target) {
        return low;
      }
      // 中间匹配的情况
      else if (nums[mid] == target) {
        return mid;
      }
      // 右侧匹配的情况
      else if (nums[high] == target) {
        return high;
      }

      // 如果num[mid]>num[low]，说明左区间有序
      if (nums[mid] > nums[low]) {
        // 如果num[mid] < target,说明在右区间
        if (nums[mid] < target) {
          low = mid + 1;
        }
        // 如果num[mid] > target,说明在左区间
        else {
          // 如果nums[low] < target，则在左区间中
          if (nums[low] < target) {
            high = mid - 1;
          }
          // 如果nums[low] > target，则在右区间中
          else {
            low = mid + 1;
          }
        }
      }
      // 如果num[mid]<=num[low]，说明右区间有序
      else {
        // 右区间有序，并且目标值大于中间值,存在两种情况
        if (nums[mid] < target) {
          // 如果当前查找值比结束的值还要大，说明在左区间
          if (nums[high] < target) {
            high = mid - 1;
          }
          // 如果当前查找值比结束的值要小，说明在右区间
          else {
            low = mid + 1;
          }
        }
        // 右区间有序，并且nums[mid] > target，说明仅存在于左区间的可能
        else {
          high = mid - 1;
        }
      }
    }

    return -1;
  }
}
```

![二分过程](file:///D:\doc\博客\数据结构与算法\二分查找/leetcode-33-提交结果.png)