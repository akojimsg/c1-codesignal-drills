/*
 * #34 | Dungeon Game
 * https://leetcode.com/problems/dungeon-game/
 * Difficulty: Hard
 * Pattern: Dynamic Programming (reverse traversal)
 *
 * A knight starts at dungeon[0][0] and must reach dungeon[m-1][n-1].
 * Cells contain positive (health gain) or negative (health loss) values.
 * Knight dies if health drops to 0 at any point. Find the minimum initial
 * health required.
 *
 * Example:
 * Input: dungeon = [[-2,-3,3],[-5,-10,1],[10,30,-5]]
 * Output: 7
 *
 * Constraints:
 * m == dungeon.length, n == dungeon[i].length
 * 1 <= m, n <= 200
 * -1000 <= dungeon[i][j] <= 1000
 */

/*
 * INSIGHT:
 * Forward DP fails because the future path affects what health we need now.
 * Go backwards: dp[r][c] = minimum health needed ENTERING cell (r,c) to survive.
 * At exit: need max(1, 1 - dungeon[m-1][n-1]).
 * At any cell: need = max(1, min(dp[down], dp[right]) - dungeon[r][c]).
 * The max(1,...) enforces health never drops to 0 — even in a bonus cell,
 * we need at least 1 health.
 */

class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[][] dp = new int[m + 1][n + 1];
        for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);
        dp[m][n-1] = dp[m-1][n] = 1;
        for (int r = m - 1; r >= 0; r--) {
            for (int c = n - 1; c >= 0; c--) {
                int need = Math.min(dp[r+1][c], dp[r][c+1]) - dungeon[r][c];
                dp[r][c] = Math.max(1, need);
            }
        }
        return dp[0][0];
    }
}
