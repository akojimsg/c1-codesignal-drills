/*
 * #5 | Reverse String II
 * https://leetcode.com/problems/reverse-string-ii/
 * Difficulty: Medium
 * Pattern: Two Pointers (block simulation)
 *
 * Given a string s and integer k, reverse the first k characters for
 * every 2k characters. If fewer than k characters remain, reverse all
 * of them. If between k and 2k remain, reverse only the first k.
 *
 * Example 1:
 * Input: s = "abcdefg", k = 2
 * Output: "bacdfeg"
 *
 * Example 2:
 * Input: s = "abcd", k = 2
 * Output: "bacd"
 *
 * Constraints:
 * 1 <= s.length <= 10^4
 * s consists of only lowercase English letters
 * 1 <= k <= 10^4
 */

class Solution {
    public String reverseStr(String s, int k) {
        char[] a = s.toCharArray();
        for (int i = 0; i < a.length; i += 2 * k) {
            int l = i, r = Math.min(i + k - 1, a.length - 1);
            while (l < r) { char t = a[l]; a[l++] = a[r]; a[r--] = t; }
        }
        return new String(a);
    }
}
