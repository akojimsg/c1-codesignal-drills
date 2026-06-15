/*
 * #18 | Gas Station
 * https://leetcode.com/problems/gas-station/
 * Difficulty: Medium
 * Pattern: Greedy (reset start when tank goes negative)
 *
 * Given gas[] and cost[] for n stations on a circular route, find the
 * starting station index that allows completing the full circuit.
 * Return -1 if impossible. Guaranteed at most one solution exists.
 *
 * Example 1:
 * Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
 * Output: 3
 *
 * Example 2:
 * Input: gas = [2,3,4], cost = [3,4,3]
 * Output: -1
 *
 * Constraints:
 * n == gas.length == cost.length
 * 1 <= n <= 10^5
 * 0 <= gas[i], cost[i] <= 10^4
 */

/*
 * INSIGHT:
 * Two observations collapse this into one pass:
 * 1. Feasibility: if total net gas >= 0, a solution must exist.
 * 2. Start candidate: if the running tank goes negative at station i, no starting
 *    point in [current_start..i] can work — each would arrive at i with even less fuel.
 *    Reset start = i+1 and tank = 0. The last candidate that doesn't fail is the answer.
 */

class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0, tank = 0, start = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            tank += diff;
            if (tank < 0) { start = i + 1; tank = 0; }
        }
        return total >= 0 ? start : -1;
    }
}
