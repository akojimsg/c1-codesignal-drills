/*
 * #30 | Word Search II
 * https://leetcode.com/problems/word-search-ii/
 * Difficulty: Hard
 * Pattern: Trie + DFS Backtracking
 *
 * Given an m x n board of characters and a list of words, return all
 * words that can be found in the board. Words are formed by adjacent
 * cells (horizontal/vertical), each cell used at most once per word.
 *
 * Example 1:
 * Input: board = [["o","a","a","n"],["e","t","a","e"],
 *                 ["i","h","k","r"],["i","f","l","v"]],
 *        words = ["oath","pea","eat","rain"]
 * Output: ["eat","oath"]
 *
 * Constraints:
 * m == board.length, n == board[i].length
 * 1 <= m, n <= 12
 * 1 <= words.length <= 3 * 10^4
 */

/*
 * INSIGHT:
 * Build a Trie of all words so one DFS pass simultaneously matches ALL words at once —
 * shared prefixes ("app" and "apple") are traversed only once. Without the Trie we'd
 * re-DFS the entire board per word.
 * Mark '# ' during DFS to prevent reusing a cell within the same path (backtrack by restoring).
 * Set next.word = null after a hit to prevent duplicate results for the same word.
 */

class Solution {
    static class Node { Node[] ch = new Node[26]; String word; }

    public List<String> findWords(char[][] board, String[] words) {
        Node root = new Node();
        for (String w : words) insert(root, w);
        List<String> res = new ArrayList<>();
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                dfs(board, i, j, root, res);
        return res;
    }
    private void insert(Node root, String w) {
        for (char c : w.toCharArray()) {
            int k = c - 'a';
            if (root.ch[k] == null) root.ch[k] = new Node();
            root = root.ch[k];
        }
        root.word = w;
    }
    private void dfs(char[][] b, int r, int c, Node node, List<String> res) {
        if (r < 0 || c < 0 || r == b.length || c == b[0].length || b[r][c] == '#') return;
        char ch = b[r][c];
        Node next = node.ch[ch - 'a'];
        if (next == null) return;
        if (next.word != null) { res.add(next.word); next.word = null; }
        b[r][c] = '#';
        dfs(b, r+1, c, next, res); dfs(b, r-1, c, next, res);
        dfs(b, r, c+1, next, res); dfs(b, r, c-1, next, res);
        b[r][c] = ch;
    }
}
