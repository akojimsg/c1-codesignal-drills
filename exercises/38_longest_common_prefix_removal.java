/*
 * #38 | Longest Common Prefix
 * https://leetcode.com/problems/longest-common-prefix/
 * Difficulty: Easy
 * Pattern: Horizontal Scanning / Trie concept
 *
 * Write a function to find the longest common prefix string among an
 * array of strings. Return "" if there is no common prefix.
 *
 * Example 1:
 * Input: strs = ["flower","flow","flight"]
 * Output: "fl"
 *
 * Example 2:
 * Input: strs = ["dog","racecar","car"]
 * Output: ""
 *
 * Constraints:
 * 1 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] consists of lowercase English letters
 */

/*
 * INSIGHT:
 * Start with strs[0] as the running prefix. For each subsequent string,
 * shrink the prefix from the right until the string starts with it.
 * String.indexOf(prefix)==0 is the fastest "starts with" check that also
 * handles the empty-prefix base case cleanly.
 * Order doesn't matter — the shortest common prefix is determined by all strings equally.
 */

class Solution {
    public String longestCommonPrefix(String[] strs) {
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++)
            while (strs[i].indexOf(prefix) != 0)
                prefix = prefix.substring(0, prefix.length() - 1);
        return prefix;
    }
}
