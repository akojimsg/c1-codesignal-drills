/*
 * #15 | Search in Rotated Sorted Array II
 * https://leetcode.com/problems/search-in-rotated-sorted-array-ii/
 * Difficulty: Medium
 * Pattern: Modified Binary Search
 *
 * Given a rotated sorted array that may contain duplicates, return true
 * if target exists, false otherwise.
 *
 * Example 1:
 * Input: nums = [2,5,6,0,0,1,2], target = 0
 * Output: true
 *
 * Example 2:
 * Input: nums = [2,5,6,0,0,1,2], target = 3
 * Output: false
 *
 * Constraints:
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * nums is sorted and rotated between 1 and nums.length times
 */

class Solution {
    public boolean search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (nums[m] == target) return true;
            if (nums[l] == nums[m] && nums[m] == nums[r]) { l++; r--; }
            else if (nums[l] <= nums[m]) {
                if (nums[l] <= target && target < nums[m]) r = m - 1; else l = m + 1;
            } else {
                if (nums[m] < target && target <= nums[r]) l = m + 1; else r = m - 1;
            }
        }
        return false;
    }
}
