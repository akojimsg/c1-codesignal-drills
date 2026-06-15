/*
 * #14 | Longest Consecutive Sequence
 * https://leetcode.com/problems/longest-consecutive-sequence/
 * Difficulty: Medium
 * Pattern: HashSet (start only where x-1 is absent)
 *
 * Given an unsorted array of integers, return the length of the longest
 * consecutive elements sequence. Must run in O(n).
 *
 * Example 1:
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4 (sequence: [1,2,3,4])
 *
 * Example 2:
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 *
 * Constraints:
 * 0 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 */

/*
 * INSIGHT:
 * Only START counting a sequence at its true beginning — where x-1 is absent.
 * If we started from every element we'd recount each sequence O(length) times → O(n²).
 * By restricting to sequence starts, each element is "walked" at most once → O(n) total.
 * The O(1) HashSet lookup makes both the start-check and the walk free.
 */

class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums) set.add(x);
        int best = 0;
        for (int x : set) {
            if (!set.contains(x - 1)) {
                int y = x;
                while (set.contains(y)) y++;
                best = Math.max(best, y - x);
            }
        }
        return best;
    }
}
