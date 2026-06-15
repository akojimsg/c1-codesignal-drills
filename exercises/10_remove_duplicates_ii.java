/*
 * #10 | Remove Duplicates from Sorted Array II
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
 * Difficulty: Medium
 * Pattern: Two Pointers (write index)
 *
 * Given a sorted array nums, allow each element to appear at most twice
 * in-place. Return the new length k. The first k elements must contain
 * the result.
 *
 * Example 1:
 * Input: nums = [1,1,1,2,2,3]
 * Output: 5, nums = [1,1,2,2,3,_]
 *
 * Example 2:
 * Input: nums = [0,0,1,1,1,1,2,3,3]
 * Output: 7, nums = [0,0,1,1,2,3,3,_,_]
 *
 * Constraints:
 * 1 <= nums.length <= 3 * 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums is sorted in non-decreasing order
 */

/*
 * INSIGHT:
 * An element is safe to write if the write index w < 2 (not enough elements yet)
 * OR it differs from what was written two slots ago (nums[w-2]).
 * Checking w-2 (not w-1) is the key: if x == nums[w-2], then x already occupies
 * positions w-2 and w-1 — a third copy would exceed the limit.
 * Generalization: for "at most k duplicates", check x != nums[w-k].
 */

class Solution {
    public int removeDuplicates(int[] nums) {
        int w = 0;
        for (int x : nums) if (w < 2 || x != nums[w - 2]) nums[w++] = x;
        return w;
    }
}
