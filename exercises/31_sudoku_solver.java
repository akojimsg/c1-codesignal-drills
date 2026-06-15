/*
 * #31 | Sudoku Solver
 * https://leetcode.com/problems/sudoku-solver/
 * Difficulty: Hard
 * Pattern: Backtracking
 *
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 * A sudoku solution must satisfy all rules: each row, column, and 3x3
 * box must contain digits 1-9 without repetition.
 *
 * Example: Given a partially filled 9×9 board, fill it in place.
 *
 * Constraints:
 * board.length == 9, board[i].length == 9
 * board[i][j] is a digit or '.'
 * Guaranteed exactly one solution
 */

/*
 * INSIGHT:
 * Three boolean[9][9] arrays (rows, cols, boxes) make O(1) validity checks.
 * boxes index = (r/3)*3 + c/3 — maps any cell to its 3×3 box.
 * Place a digit, recurse; if recursion fails, undo and try next digit.
 * The function returns boolean so a successful placement propagates back up
 * and stops all further exploration immediately.
 */

class Solution {
    boolean[][] rows = new boolean[9][9];
    boolean[][] cols = new boolean[9][9];
    boolean[][] boxes = new boolean[9][9];

    public void solveSudoku(char[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] != '.') {
                    int d = board[r][c] - '1';
                    rows[r][d] = cols[c][d] = boxes[(r/3)*3 + c/3][d] = true;
                }
            }
        }
        solve(board, 0, 0);
    }
    private boolean solve(char[][] board, int r, int c) {
        if (r == 9) return true;
        int nr = (c == 8) ? r + 1 : r;
        int nc = (c == 8) ? 0 : c + 1;
        if (board[r][c] != '.') return solve(board, nr, nc);
        int box = (r/3)*3 + c/3;
        for (int d = 0; d < 9; d++) {
            if (rows[r][d] || cols[c][d] || boxes[box][d]) continue;
            board[r][c] = (char)('1' + d);
            rows[r][d] = cols[c][d] = boxes[box][d] = true;
            if (solve(board, nr, nc)) return true;
            board[r][c] = '.';
            rows[r][d] = cols[c][d] = boxes[box][d] = false;
        }
        return false;
    }
}
