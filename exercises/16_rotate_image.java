/*
 * #16 | Rotate Image
 * https://leetcode.com/problems/rotate-image/
 * Difficulty: Medium
 * Pattern: Matrix (transpose + reverse rows)
 *
 * Given an n x n 2D matrix, rotate it 90 degrees clockwise in-place.
 *
 * Example 1:
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [[7,4,1],[8,5,2],[9,6,3]]
 *
 * Example 2:
 * Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
 * Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
 *
 * Constraints:
 * n == matrix.length == matrix[i].length
 * 1 <= n <= 20
 */

/*
 * INSIGHT:
 * 90° CW = transpose then reverse each row.
 * Proof: transpose maps (i,j)→(j,i); reversing row j maps (j,i)→(j,n-1-i).
 * The CW rule is original (i,j) → new (j, n-1-i). ✓
 * Transpose only needs j starting at i+1 to avoid double-swapping the diagonal.
 * Variants: CCW = reverse rows first then transpose. 180° = reverse entire matrix.
 */

class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++) {
                int t = matrix[i][j]; matrix[i][j] = matrix[j][i]; matrix[j][i] = t;
            }
        for (int[] row : matrix) {
            int l = 0, r = n - 1;
            while (l < r) { int t = row[l]; row[l++] = row[r]; row[r--] = t; }
        }
    }
}
