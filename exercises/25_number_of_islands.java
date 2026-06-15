/*
 * #25 | Number of Islands
 * https://leetcode.com/problems/number-of-islands/
 * Difficulty: Medium
 * Pattern: DFS Flood Fill
 *
 * Given an m x n grid of '1' (land) and '0' (water), count the number
 * of islands. An island is surrounded by water and formed by connecting
 * adjacent land cells horizontally or vertically.
 *
 * Example 1:
 * Input: grid = [["1","1","1","1","0"],["1","1","0","1","0"],
 *                ["1","1","0","0","0"],["0","0","0","0","0"]]
 * Output: 1
 *
 * Example 2:
 * Input: grid = [["1","1","0","0","0"],["1","1","0","0","0"],
 *                ["0","0","1","0","0"],["0","0","0","1","1"]]
 * Output: 3
 *
 * Constraints:
 * m == grid.length, n == grid[i].length
 * 1 <= m, n <= 300
 */

/*
 * INSIGHT:
 * Flood fill. Every time we find unvisited land, it's a new island — increment counter
 * and DFS to "sink" (mark '0') all connected land so it's never counted again.
 * Sinking the input avoids a separate visited array. If mutation is not allowed,
 * use a boolean[][] visited instead.
 */

class Solution {
    public int numIslands(char[][] grid) {
        int m = grid.length, n = grid[0].length, count = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (grid[i][j] == '1') { count++; dfs(grid, i, j); }
        return count;
    }
    private void dfs(char[][] g, int r, int c) {
        if (r < 0 || c < 0 || r == g.length || c == g[0].length || g[r][c] != '1') return;
        g[r][c] = '0';
        dfs(g, r+1, c); dfs(g, r-1, c); dfs(g, r, c+1); dfs(g, r, c-1);
    }
}
