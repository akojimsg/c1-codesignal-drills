/*
 * #31 | Sudoku Solver
 * https://leetcode.com/problems/sudoku-solver/
 * Difficulty: Hard
 * Pattern: Backtracking (try candidates, backtrack on conflict)
 *
 * Fill a 9x9 Sudoku board so every row, column, and 3x3 box contains
 * digits 1-9 exactly once. The given board has a unique solution.
 *
 * Example:
 * Input: partially filled 9x9 board with '.' for empty cells
 * Output: the same board filled with the solution
 *
 * Constraints:
 * board.length == 9, board[i].length == 9
 * board[i][j] is a digit or '.'
 * The input board has exactly one solution
 */

class Solution {
    boolean[][] row=new boolean[9][10], col=new boolean[9][10], box=new boolean[9][10];
    public void solveSudoku(char[][] board) {
        for (int r=0;r<9;r++) for (int c=0;c<9;c++)
            if (board[r][c]!='.') mark(r,c,board[r][c]-'0',true);
        solve(board,0,0);
    }
    private boolean solve(char[][] b, int r, int c) {
        if (r==9) return true; if (c==9) return solve(b,r+1,0);
        if (b[r][c]!='.') return solve(b,r,c+1);
        for (int d=1; d<=9; d++) if (!row[r][d]&&!col[c][d]&&!box[id(r,c)][d]) {
            b[r][c]=(char)('0'+d); mark(r,c,d,true);
            if (solve(b,r,c+1)) return true;
            mark(r,c,d,false); b[r][c]='.';
        }
        return false;
    }
    private int id(int r,int c){ return (r/3)*3+c/3; }
    private void mark(int r,int c,int d,boolean v){ row[r][d]=col[c][d]=box[id(r,c)][d]=v; }
}
