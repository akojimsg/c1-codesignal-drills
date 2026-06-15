/*
 * #24 | Spiral Matrix III
 * https://leetcode.com/problems/spiral-matrix-iii/
 * Difficulty: Medium
 * Pattern: Matrix Simulation (expanding steps)
 *
 * Starting at (r, c) in an rows x cols grid, walk in a clockwise spiral
 * and return the coordinates of every cell in the order visited.
 *
 * Example 1:
 * Input: rows = 1, cols = 4, r = 0, c = 0
 * Output: [[0,0],[0,1],[0,2],[0,3]]
 *
 * Example 2:
 * Input: rows = 5, cols = 6, r = 1, c = 4
 * Output: [[1,4],[1,5],[2,5],[2,4],[2,3],[1,3],[0,3],...] (all 30 cells)
 *
 * Constraints:
 * 1 <= rows, cols <= 100
 * 0 <= r < rows
 * 0 <= c < cols
 */

class Solution {
    public int[][] spiralMatrixIII(int rows, int cols, int r, int c) {
        int[][] ans = new int[rows * cols][2]; int idx = 0;
        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}}; int step = 1;
        ans[idx++] = new int[]{r,c};
        while (idx < rows * cols) {
            for (int d = 0; d < 4; d++) {
                for (int s = 0; s < step; s++) {
                    r += dirs[d][0]; c += dirs[d][1];
                    if (0 <= r && r < rows && 0 <= c && c < cols) ans[idx++] = new int[]{r,c};
                }
                if (d == 1 || d == 3) step++;
            }
        }
        return ans;
    }
}
