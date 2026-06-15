/*
 * #35 | Maximum Number of Non-Overlapping Palindrome Substrings
 * https://leetcode.com/problems/maximum-number-of-non-overlapping-palindrome-substrings/
 * Difficulty: Hard
 * Pattern: Greedy (earliest-ending valid palindrome)
 *
 * Given a string s and integer k, return the maximum number of
 * non-overlapping palindromic substrings of length at least k.
 *
 * Example 1:
 * Input: s = "abaccdbbd", k = 3
 * Output: 2
 *
 * Example 2:
 * Input: s = "adbcda", k = 2
 * Output: 0
 *
 * Constraints:
 * 1 <= k <= s.length <= 2000
 * s consists of lowercase English letters
 */

class Solution {
    public int maxPalindromes(String s, int k) {
        int ans = 0, end = -1;
        for (int i = 0; i < s.length(); i++) {
            if (i <= end) continue;
            if (isPal(s, i, i + k - 1)) { ans++; end = i + k - 1; }
            else if (isPal(s, i, i + k)) { ans++; end = i + k; }
        }
        return ans;
    }
    private boolean isPal(String s, int l, int r) {
        if (r >= s.length()) return false;
        while (l < r) if (s.charAt(l++) != s.charAt(r--)) return false;
        return true;
    }
}
