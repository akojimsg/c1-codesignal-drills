/*
 * #33 | Best Time to Buy and Sell Stock IV
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
 * Difficulty: Hard
 * Pattern: Dynamic Programming (k transactions)
 *
 * Given prices[] and k, find max profit with at most k transactions.
 * One transaction = one buy + one sell.
 *
 * Example 1:
 * Input: k = 2, prices = [2,4,1]
 * Output: 2
 *
 * Example 2:
 * Input: k = 2, prices = [3,2,6,5,0,3]
 * Output: 7
 *
 * Constraints:
 * 1 <= k <= 100
 * 1 <= prices.length <= 1000
 */

/*
 * INSIGHT:
 * If k >= n/2, unlimited transactions — just sum all positive diffs (greedy).
 * Otherwise, DP with two arrays: buy[j] = best "just bought using j transactions so far",
 * sell[j] = best profit after j completed transactions.
 * buy[j] = max(buy[j], sell[j-1] - price)  ← buy today using j-th transaction
 * sell[j] = max(sell[j], buy[j] + price)    ← sell today completing j-th transaction
 * Initialize buy[] to -INF (haven't bought yet) and sell[] to 0.
 */

class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (k >= n / 2) {
            int profit = 0;
            for (int i = 1; i < n; i++) profit += Math.max(0, prices[i] - prices[i-1]);
            return profit;
        }
        int[] buy = new int[k + 1], sell = new int[k + 1];
        Arrays.fill(buy, Integer.MIN_VALUE);
        for (int price : prices) {
            for (int j = k; j >= 1; j--) {
                sell[j] = Math.max(sell[j], buy[j] + price);
                buy[j] = Math.max(buy[j], sell[j-1] - price);
            }
        }
        return sell[k];
    }
}
