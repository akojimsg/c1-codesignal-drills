/*
 * #2 | Running Sum of 1D Array
 * https://leetcode.com/problems/running-sum-of-1d-array/
 * Difficulty: Easy
 * Pattern: Prefix Sum
 *
 * Given an array nums, return a new array where each element is the
 * cumulative sum of all elements up to and including that index.
 * runningSum[i] = sum(nums[0] + nums[1] + ... + nums[i])
 *
 * Example 1:
 * Input: nums = [1,2,3,4]
 * Output: [1,3,6,10]
 *
 * Example 2:
 * Input: nums = [1,1,1,1,1]
 * Output: [1,2,3,4,5]
 *
 * Constraints:
 * 1 <= nums.length <= 1000
 * -10^6 <= nums[i] <= 10^6
 */

/*
 * INSIGHT:
 * Each element's running sum is simply the previous running sum plus the current value.
 * We can overwrite in-place because by the time we process index i, nums[i-1] already
 * holds the cumulative sum up to i-1 — no scratch space needed.
 */

class Solution {
    public int[] runningSum(int[] nums) {
        for (int i = 1; i < nums.length; i++) nums[i] += nums[i - 1];
        return nums;
    }
}
