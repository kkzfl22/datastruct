## leetcode 153. 寻找旋转排序数组中的最小值

>
    假设按照升序排序的数组在预先未知的某个点上进行了旋转。例如，数组 [0,1,2,4,5,6,7] 
    可能变为 [4,5,6,7,0,1,2] 。
    
    请找出其中最小的元素。
    
     
    
    示例 1：
    
    输入：nums = [3,4,5,1,2]
    输出：1
    示例 2：
    
    输入：nums = [4,5,6,7,0,1,2]
    输出：0
    示例 3：
    
    输入：nums = [1]
    输出：1
     
    
    提示：
    
    1 <= nums.length <= 5000
    -5000 <= nums[i] <= 5000
    nums 中的所有整数都是 唯一 的
    nums 原来是一个升序排序的数组，但在预先未知的某个点上进行了旋转
    
    来源：力扣（LeetCode）
    链接：https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array
    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
>


```java
public class Solution {

  public int findMin(int[] nums) {

    if (nums == null || nums.length == 0) {
      return -1;
    }
    if (nums.length <= 1) {
      return nums[0];
    }

    int low = 0, mid, high = nums.length - 1;
    int result = nums[0];

    while (low <= high) {
      mid = low + (high - low) / 2;

      // 检查是否为左边右区间有序的情况
      if (nums[low] < nums[mid]) {
        if (nums[low] < nums[high]) {
          return nums[low];
        } else {
          low = mid + 1;
        }
      }
      // 检查是否为右区间有序的情况
      else {
        if (mid != 0 && nums[mid - 1] > nums[mid]) {
          return nums[mid];
        } else {
          if (nums[mid] > nums[high]) {
            result = nums[high];
          }
          high = mid - 1;
        }
      }
    }

    return result;
  }
}

```