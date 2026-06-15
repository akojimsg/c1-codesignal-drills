/*
 * #17 | Trapping Rain Water
 * https://leetcode.com/problems/trapping-rain-water/
 * Difficulty: Medium
 * Pattern: Two Pointers (min of left/right max minus height)
 *
 * Given n non-negative integers representing bar heights, compute how
 * much water can be trapped after rain.
 *
 * Example 1:
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 *
 * Example 2:
 * Input: height = [4,2,0,3,2,5]
 * Output: 9
 *
 * Constraints:
 * n == height.length
 * 1 <= n <= 2 * 10^4
 * 0 <= height[i] <= 10^5
 */

class Solution {
    public int trap(int[] height) {
        int l = 0, r = height.length - 1, lm = 0, rm = 0, ans = 0;
        while (l < r) {
            if (height[l] < height[r]) {
                lm = Math.max(lm, height[l]); ans += lm - height[l++];
            } else {
                rm = Math.max(rm, height[r]); ans += rm - height[r--];
            }
        }
        return ans;
    }
}
