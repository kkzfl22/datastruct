## 658. 找到 K 个最接近的元素


>
    给定一个排序好的数组 arr ，两个整数 k 和 x ，从数组中找到最靠近 x（两数之差最小）的 k 个数。
    返回的结果必须要是按升序排好的。
    
    整数 a 比整数 b 更接近 x 需要满足：
    
    |a - x| < |b - x| 或者
    |a - x| == |b - x| 且 a < b
     
    
    示例 1：
    
    输入：arr = [1 ,2,3,4,5], k = 4, x = 3
    输出：[1,2,3,4]
    示例 2：
    
    输入：arr = [1,2,3,4,5], k = 4, x = -1
    输出：[1,2,3,4]
     
    
    提示：
    
    1 <= k <= arr.length
    1 <= arr.length <= 104
    数组里的每个元素与 x 的绝对值不超过 104
    
    来源：力扣（LeetCode）
    链接：https://leetcode-cn.com/problems/find-k-closest-elements
    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。    
>


>
    Given a sorted integer array arr, two integers k and x, 
    return the k closest integers to x in the array. 
    The result should also be sorted in ascending order.
    
    An integer a is closer to x than an integer b if:
    
    |a - x| < |b - x|, or
    |a - x| == |b - x| and a < b
     
    
    Example 1:
    
    Input: arr = [1,2,3,4,5], k = 4, x = 3
    Output: [1,2,3,4]
    Example 2:
    
    Input: arr = [1,2,3,4,5], k = 4, x = -1
    Output: [1,2,3,4]
     
    
    Constraints:
    
    1 <= k <= arr.length
    1 <= arr.length <= 104
    Absolute value of elements in the array and x will not exceed 104
    
    来源：力扣（LeetCode）
    链接：https://leetcode-cn.com/problems/find-k-closest-elements
    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
>


class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {

    }
}