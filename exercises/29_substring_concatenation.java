/*
 * #29 | Substring with Concatenation of All Words
 * https://leetcode.com/problems/substring-with-concatenation-of-all-words/
 * Difficulty: Hard
 * Pattern: Sliding Window by word chunks
 *
 * Given string s and array words of equal-length strings, return all
 * starting indices of substrings that are a concatenation of all words
 * in any order.
 *
 * Example 1:
 * Input: s = "barfoothefoobarman", words = ["foo","bar"]
 * Output: [0,9]
 *
 * Example 2:
 * Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
 * Output: []
 *
 * Constraints:
 * 1 <= s.length <= 10^4
 * 1 <= words.length <= 5000
 * 1 <= words[i].length <= 30
 */

/*
 * INSIGHT:
 * Since all words have equal length w, run w independent sliding windows — one per
 * alignment offset (0..w-1). Within each window, slide by entire words (not characters).
 * Unknown word → hard reset. Over-counted word → shrink from left until balanced.
 * This reduces the naive O(n × W × L) to O(n × L) by sharing prefix work across
 * overlapping windows at the same alignment.
 */

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        if (words.length == 0) return res;
        int w = words[0].length();
        Map<String, Integer> need = new HashMap<>();
        for (String x : words) need.put(x, need.getOrDefault(x, 0) + 1);
        for (int off = 0; off < w; off++) {
            Map<String, Integer> have = new HashMap<>();
            int l = off, count = 0;
            for (int r = off; r + w <= s.length(); r += w) {
                String cur = s.substring(r, r + w);
                if (!need.containsKey(cur)) { have.clear(); count = 0; l = r + w; continue; }
                have.put(cur, have.getOrDefault(cur, 0) + 1);
                count++;
                while (have.get(cur) > need.get(cur)) {
                    String left = s.substring(l, l + w);
                    have.put(left, have.get(left) - 1);
                    l += w; count--;
                }
                if (count == words.length) res.add(l);
            }
        }
        return res;
    }
}
