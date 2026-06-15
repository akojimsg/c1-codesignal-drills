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

/*
 * INSIGHT:
 * Water at position i = min(maxLeft, maxRight) - height[i].
 * With two pointers we avoid precomputing both prefix arrays. The trick:
 * if height[l] < height[r], we know the water at l is bounded by lm — because
 * rm >= height[r] > height[l] >= lm, so min(lm, rm) = lm. We can safely compute
 * and advance l. The symmetric argument applies for the right side.
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
