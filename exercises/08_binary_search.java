/*
 * #8 | Binary Search
 * https://leetcode.com/problems/binary-search/
 * Difficulty: Easy
 * Pattern: Binary Search
 *
 * Given an array of integers nums sorted in ascending order and a target,
 * return the index of target. Return -1 if not found.
 *
 * Example 1:
 * Input: nums = [-1,0,3,5,9,12], target = 9
 * Output: 4
 *
 * Example 2:
 * Input: nums = [-1,0,3,5,9,12], target = 2
 * Output: -1
 *
 * Constraints:
 * 1 <= nums.length <= 10^4
 * All integers in nums are unique
 * nums is sorted in ascending order
 */

/*
 * INSIGHT:
 * Eliminate half the search space each step → O(log n).
 * Template to memorize: l <= r (inclusive both ends), mid = l + (r-l)/2 (avoids
 * overflow), on miss move l = m+1 or r = m-1 (never leave m in range or you loop).
 * The loop exits when l > r, meaning the space is exhausted.
 */

class Solution {
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (nums[m] == target) return m;
            if (nums[m] < target) l = m + 1; else r = m - 1;
        }
        return -1;
    }
}
