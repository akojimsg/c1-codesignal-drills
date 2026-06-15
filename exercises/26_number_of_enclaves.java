/*
 * #26 | Number of Enclaves
 * https://leetcode.com/problems/number-of-enclaves/
 * Difficulty: Medium
 * Pattern: DFS Boundary Flood Fill
 *
 * Given a binary grid, return the number of land cells (1s) from which
 * you cannot walk off the boundary of the grid in any number of moves.
 *
 * Example 1:
 * Input: grid = [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
 * Output: 3
 *
 * Example 2:
 * Input: grid = [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
 * Output: 0
 *
 * Constraints:
 * m == grid.length, n == grid[i].length
 * 1 <= m, n <= 500
 * grid[i][j] is 0 or 1
 */

/*
 * INSIGHT:
 * Invert the problem: instead of finding enclosed land, remove land that CAN reach
 * the boundary. DFS from every border cell and sink all reachable '1's. Whatever
 * land remains after that is completely surrounded — count it.
 * This is cleaner than trying to detect enclosures from the inside.
 */

class Solution {
    public int numEnclaves(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) { dfs(grid, i, 0); dfs(grid, i, n - 1); }
        for (int j = 0; j < n; j++) { dfs(grid, 0, j); dfs(grid, m - 1, j); }
        int ans = 0;
        for (int[] row : grid) for (int x : row) if (x == 1) ans++;
        return ans;
    }
    private void dfs(int[][] g, int r, int c) {
        if (r < 0 || c < 0 || r == g.length || c == g[0].length || g[r][c] == 0) return;
        g[r][c] = 0;
        dfs(g, r+1, c); dfs(g, r-1, c); dfs(g, r, c+1); dfs(g, r, c-1);
    }
}
