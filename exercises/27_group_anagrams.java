/*
 * #27 | Group Anagrams
 * https://leetcode.com/problems/group-anagrams/
 * Difficulty: Medium
 * Pattern: HashMap (canonical key from character frequency)
 *
 * Given an array of strings, group the anagrams together.
 * Return the groups in any order.
 *
 * Example 1:
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 *
 * Example 2:
 * Input: strs = [""]
 * Output: [[""]]
 *
 * Constraints:
 * 1 <= strs.length <= 10^4
 * 0 <= strs[i].length <= 100
 * strs[i] consists of lowercase English letters
 */

/*
 * INSIGHT:
 * Anagrams share the same character frequencies, so encode frequencies as a canonical key.
 * Frequency encoding (O(L) per word) beats sorting (O(L log L)) for long strings.
 * The '#' separator between counts is essential: without it, counts [1,10] and [11,0]
 * would produce the same string "110", causing false groupings.
 */

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            int[] cnt = new int[26];
            for (char ch : s.toCharArray()) cnt[ch - 'a']++;
            StringBuilder key = new StringBuilder();
            for (int x : cnt) key.append('#').append(x);
            map.computeIfAbsent(key.toString(), k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(map.values());
    }
}
