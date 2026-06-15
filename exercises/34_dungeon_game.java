/*
 * #34 | Dungeon Game
 * https://leetcode.com/problems/dungeon-game/
 * Difficulty: Hard
 * Pattern: Reverse DP (work backward from destination)
 *
 * A knight starts top-left and must reach the princess at bottom-right.
 * Each cell adds or removes health. Find the minimum initial health so
 * the knight survives (health never drops to 0 or below).
 *
 * Example 1:
 * Input: dungeon = [[-2,-3,3],[-5,-10,1],[10,30,-5]]
 * Output: 7
 *
 * Constraints:
 * m == dungeon.length, n == dungeon[0].length
 * 1 <= m, n <= 200
 * -1000 <= dungeon[i][j] <= 1000
 */

class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m=dungeon.length, n=dungeon[0].length, INF=1_000_000_000;
        int[][] dp=new int[m+1][n+1];
        for(int[] row:dp) Arrays.fill(row, INF);
        dp[m][n-1]=dp[m-1][n]=1;
        for(int i=m-1;i>=0;i--) for(int j=n-1;j>=0;j--) {
            int need = Math.min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j];
            dp[i][j] = Math.max(1, need);
        }
        return dp[0][0];
    }
}
