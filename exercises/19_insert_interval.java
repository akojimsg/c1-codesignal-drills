/*
 * #19 | Insert Interval
 * https://leetcode.com/problems/insert-interval/
 * Difficulty: Medium
 * Pattern: Linear Merge (before, merge overlaps, after)
 *
 * Given a sorted list of non-overlapping intervals and a new interval,
 * insert the new interval and merge any overlapping intervals.
 *
 * Example 1:
 * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * Output: [[1,5],[6,9]]
 *
 * Example 2:
 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * Output: [[1,2],[3,10],[12,16]]
 *
 * Constraints:
 * 0 <= intervals.length <= 10^4
 * intervals[i].length == 2
 * intervals is sorted by start time
 */

/*
 * INSIGHT:
 * Three clean phases:
 *   Before:   intervals that end before newInterval starts (no overlap possible).
 *   Overlap:  intervals whose start <= newInterval's end — absorb them into newInterval
 *             by expanding its bounds (min start, max end).
 *   After:    everything remaining.
 * Mutating newInterval directly avoids extra allocations.
 */

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();
        int i = 0, n = intervals.length;
        while (i < n && intervals[i][1] < newInterval[0]) res.add(intervals[i++]);
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        res.add(newInterval);
        while (i < n) res.add(intervals[i++]);
        return res.toArray(new int[res.size()][]);
    }
}
