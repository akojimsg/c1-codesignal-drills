/*
 * #6 | Move Zeroes
 * https://leetcode.com/problems/move-zeroes/
 * Difficulty: Easy
 * Pattern: Two Pointers (stable compaction)
 *
 * Given an integer array nums, move all zeroes to the end while
 * maintaining the relative order of non-zero elements. Do it in-place.
 *
 * Example 1:
 * Input: nums = [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 *
 * Example 2:
 * Input: nums = [0]
 * Output: [0]
 *
 * Constraints:
 * 1 <= nums.length <= 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 */

class Solution {
    public void moveZeroes(int[] nums) {
        int w = 0;
        for (int x : nums) if (x != 0) nums[w++] = x;
        while (w < nums.length) nums[w++] = 0;
    }
}
