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

/*
 * INSIGHT:
 * Two-phase compaction. Write pointer w advances only on non-zero elements,
 * packing them to the front in their original order (phase 1). Then fill
 * everything from w onward with zeros (phase 2). This is simpler and safer
 * than swapping — swapping can disturb relative order of zeros.
 */

class Solution {
    public void moveZeroes(int[] nums) {
        int w = 0;
        for (int x : nums) if (x != 0) nums[w++] = x;
        while (w < nums.length) nums[w++] = 0;
    }
}
