/*
 * #7 | Product of Array Except Self
 * https://leetcode.com/problems/product-of-array-except-self/
 * Difficulty: Medium
 * Pattern: Prefix / Suffix Products
 *
 * Given an integer array nums, return an array answer such that
 * answer[i] is the product of all elements except nums[i].
 * Must run in O(n) without using division.
 *
 * Example 1:
 * Input: nums = [1,2,3,4]
 * Output: [24,12,8,6]
 *
 * Example 2:
 * Input: nums = [-1,1,0,-3,3]
 * Output: [0,0,9,0,0]
 *
 * Constraints:
 * 2 <= nums.length <= 10^5
 * -30 <= nums[i] <= 30
 * The product of any prefix or suffix fits in a 32-bit integer.
 */

/*
 * INSIGHT:
 * product_except[i] = (product of everything LEFT of i) × (product of everything RIGHT of i).
 * Two passes, one running accumulator each — no extra array needed beyond the output.
 * Pass 1 (→): ans[i] = left product so far, then extend left product.
 * Pass 2 (←): ans[i] *= right product so far, then extend right product.
 * This avoids division and handles zeros naturally.
 */

class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        int left = 1;
        for (int i = 0; i < n; i++) { ans[i] = left; left *= nums[i]; }
        int right = 1;
        for (int i = n - 1; i >= 0; i--) { ans[i] *= right; right *= nums[i]; }
        return ans;
    }
}
