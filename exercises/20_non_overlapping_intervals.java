/*
 * #20 | Non-Overlapping Intervals
 * https://leetcode.com/problems/non-overlapping-intervals/
 * Difficulty: Medium
 * Pattern: Greedy (sort by end time, count conflicts)
 *
 * Given an array of intervals, return the minimum number of intervals
 * you need to remove to make the rest non-overlapping.
 *
 * Example 1:
 * Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
 * Output: 1
 *
 * Example 2:
 * Input: intervals = [[1,2],[1,2],[1,2]]
 * Output: 2
 *
 * Constraints:
 * 1 <= intervals.length <= 10^5
 * intervals[i].length == 2
 * -5 * 10^4 <= start < end <= 5 * 10^4
 */

/*
 * INSIGHT:
 * Equivalent to finding the MAX number of non-overlapping intervals (Activity Selection),
 * then removals = total - max_kept.
 * Greedy rule: always keep the interval that ends earliest — it leaves the most room
 * for future intervals. Sort by end time, greedily accept non-overlapping ones,
 * count the rest as removals.
 */

class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
        int count = 0, end = Integer.MIN_VALUE;
        for (int[] in : intervals) {
            if (in[0] >= end) end = in[1];
            else count++;
        }
        return count;
    }
}
