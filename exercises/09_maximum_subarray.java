/*
 * #9 | Maximum Subarray
 * https://leetcode.com/problems/maximum-subarray/
 * Difficulty: Medium
 * Pattern: Kadane's DP
 *
 * Given an integer array nums, find the contiguous subarray with the
 * largest sum and return its sum.
 *
 * Example 1:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6 (subarray [4,-1,2,1])
 *
 * Example 2:
 * Input: nums = [1]
 * Output: 1
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 */

class Solution {
    public int maxSubArray(int[] nums) {
        int best = nums[0], cur = nums[0];
        for (int i = 1; i < nums.length; i++) {
            cur = Math.max(nums[i], cur + nums[i]);
            best = Math.max(best, cur);
        }
        return best;
    }
}
