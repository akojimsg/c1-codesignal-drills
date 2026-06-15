/*
 * #33 | Best Time to Buy and Sell Stock IV
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
 * Difficulty: Hard
 * Pattern: Dynamic Programming (buy/sell arrays per transaction)
 *
 * Given prices[] and integer k, find the maximum profit using at most
 * k transactions. You must sell before buying again.
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
 * 0 <= prices[i] <= 1000
 */

class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length; if (n == 0) return 0;
        if (k >= n/2) {
            int p=0; for(int i=1;i<n;i++) if(prices[i]>prices[i-1]) p+=prices[i]-prices[i-1];
            return p;
        }
        int[] buy = new int[k+1], sell = new int[k+1];
        Arrays.fill(buy, Integer.MIN_VALUE/2);
        for (int price : prices) for (int t=1; t<=k; t++) {
            buy[t] = Math.max(buy[t], sell[t-1] - price);
            sell[t] = Math.max(sell[t], buy[t] + price);
        }
        return sell[k];
    }
}
