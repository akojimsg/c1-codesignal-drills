/*
 * #28 | Minimum Window Substring
 * https://leetcode.com/problems/minimum-window-substring/
 * Difficulty: Hard
 * Pattern: Sliding Window (expand right until valid, shrink left)
 *
 * Given strings s and t, return the minimum window substring of s that
 * contains all characters of t. Return "" if no such window exists.
 *
 * Example 1:
 * Input: s = "ADOBECODEBANC", t = "ABC"
 * Output: "BANC"
 *
 * Example 2:
 * Input: s = "a", t = "a"
 * Output: "a"
 *
 * Constraints:
 * 1 <= s.length, t.length <= 10^5
 * s and t consist of uppercase and lowercase English letters
 */

class Solution {
    public String minWindow(String s, String t) {
        int[] need = new int[128]; for (char c : t.toCharArray()) need[c]++;
        int missing = t.length(), l = 0, bestL = 0, best = Integer.MAX_VALUE;
        for (int r = 0; r < s.length(); r++) {
            if (need[s.charAt(r)]-- > 0) missing--;
            while (missing == 0) {
                if (r - l + 1 < best) { best = r - l + 1; bestL = l; }
                if (++need[s.charAt(l++)] > 0) missing++;
            }
        }
        return best == Integer.MAX_VALUE ? "" : s.substring(bestL, bestL + best);
    }
}
