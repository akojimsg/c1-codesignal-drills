/*
 * #21 | Jump Game II
 * https://leetcode.com/problems/jump-game-ii/
 * Difficulty: Medium
 * Pattern: Greedy BFS (farthest reach, expand layers)
 *
 * Given an array where nums[i] is the max jump from index i, return the
 * minimum number of jumps to reach the last index. Always reachable.
 *
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: 2
 *
 * Example 2:
 * Input: nums = [2,3,0,1,4]
 * Output: 2
 *
 * Constraints:
 * 1 <= nums.length <= 10^4
 * 0 <= nums[i] <= 1000
 * Always possible to reach last index
 */

class Solution {
    public int jump(int[] nums) {
        int jumps = 0, end = 0, far = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            far = Math.max(far, i + nums[i]);
            if (i == end) { jumps++; end = far; }
        }
        return jumps;
    }
}
