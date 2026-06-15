/*
 * #32 | Text Justification
 * https://leetcode.com/problems/text-justification/
 * Difficulty: Hard
 * Pattern: Greedy Simulation (pack line, distribute spaces)
 *
 * Given an array of words and a maxWidth, format the text so each line
 * has exactly maxWidth characters and is fully justified. The last line
 * should be left-justified with single spaces between words.
 *
 * Example 1:
 * Input: words = ["This","is","an","example","of","text","justification."],
 *        maxWidth = 16
 * Output: ["This    is    an", "example  of text", "justification.  "]
 *
 * Constraints:
 * 1 <= words.length <= 300
 * 1 <= words[i].length <= 20
 * 1 <= maxWidth <= 100
 * words[i].length <= maxWidth
 */

class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>(); int i = 0;
        while (i < words.length) {
            int j = i, len = 0;
            while (j < words.length && len + words[j].length() + (j-i) <= maxWidth)
                len += words[j++].length();
            int gaps = j - i - 1; StringBuilder sb = new StringBuilder();
            if (j == words.length || gaps == 0) {
                for (int k=i;k<j;k++) { if(k>i) sb.append(' '); sb.append(words[k]); }
                while (sb.length() < maxWidth) sb.append(' ');
            } else {
                int spaces = maxWidth - len, each = spaces/gaps, extra = spaces%gaps;
                for (int k=i;k<j;k++) {
                    sb.append(words[k]);
                    if (k<j-1) for(int s=0;s<each+(k-i<extra?1:0);s++) sb.append(' ');
                }
            }
            res.add(sb.toString()); i = j;
        }
        return res;
    }
}
