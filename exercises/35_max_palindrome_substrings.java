/*
 * #35 | Palindromic Substrings
 * https://leetcode.com/problems/palindromic-substrings/
 * Difficulty: Medium
 * Pattern: Expand Around Center
 *
 * Given a string s, return the number of palindromic substrings.
 * A string is a palindrome if it reads the same forward and backward.
 *
 * Example 1:
 * Input: s = "abc"
 * Output: 3
 *
 * Example 2:
 * Input: s = "aaa"
 * Output: 6
 *
 * Constraints:
 * 1 <= s.length <= 1000
 * s consists of lowercase English letters
 */

/*
 * INSIGHT:
 * Expand-around-center: every palindrome has a center (single char or gap between chars).
 * For a string of length n there are 2n-1 centers. Expand outward while s[l]==s[r].
 * Each successful expansion is a distinct palindrome — increment count each time.
 * Simpler than DP (O(n²) both ways) and avoids building a 2D table.
 */

class Solution {
    public int countSubstrings(String s) {
        int count = 0;
        for (int center = 0; center < 2 * s.length() - 1; center++) {
            int l = center / 2, r = l + center % 2;
            while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
                count++;
                l--; r++;
            }
        }
        return count;
    }
}
