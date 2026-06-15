/*
 * #4 | Rotate Array
 * https://leetcode.com/problems/rotate-array/
 * Difficulty: Medium
 * Pattern: Array Manipulation (triple reverse)
 *
 * Given an integer array nums, rotate the array to the right by k steps
 * in-place.
 *
 * Example 1:
 * Input: nums = [1,2,3,4,5,6,7], k = 3
 * Output: [5,6,7,1,2,3,4]
 *
 * Example 2:
 * Input: nums = [-1,-100,3,99], k = 2
 * Output: [3,99,-1,-100]
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 * 0 <= k <= 10^5
 */

class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length; k %= n;
        reverse(nums, 0, n - 1); reverse(nums, 0, k - 1); reverse(nums, k, n - 1);
    }
    private void reverse(int[] a, int l, int r) {
        while (l < r) { int t = a[l]; a[l++] = a[r]; a[r--] = t; }
    }
}
