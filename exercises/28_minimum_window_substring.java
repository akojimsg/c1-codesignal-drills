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

/*
 * INSIGHT:
 * Expand right until the window is valid (missing == 0), then shrink left to minimize.
 * need[] doubles as frequency map and over-supply detector: need[c] > 0 means we still
 * need c; need[c] <= 0 means we have surplus. This lets us track "missing" with a single
 * counter instead of re-scanning the entire window each time.
 * On add: decrement need[c]; if it was > 0 before, we satisfied a requirement → missing--.
 * On remove: increment need[c]; if it goes > 0, we lost a requirement → missing++.
 */

class Solution {
    public String minWindow(String s, String t) {
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;
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
