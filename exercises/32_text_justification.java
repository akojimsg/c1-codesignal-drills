/*
 * #32 | Text Justification
 * https://leetcode.com/problems/text-justification/
 * Difficulty: Hard
 * Pattern: Greedy Line Packing + String Formatting
 *
 * Given an array of words and a width maxWidth, format the text so each
 * line has exactly maxWidth characters and is fully left-justified.
 * Pack as many words as possible per line. Distribute extra spaces evenly
 * (left gaps get the extra space). Last line is left-justified.
 *
 * Example:
 * Input: words = ["This","is","an","example","of","text","justification."], maxWidth = 16
 * Output: ["This    is    an", "example  of text", "justification.  "]
 *
 * Constraints:
 * 1 <= words.length <= 300
 * 1 <= words[i].length <= 20
 * 1 <= maxWidth <= 100
 */

/*
 * INSIGHT:
 * Greedy: pack words until the next word won't fit (word count × 1 min-space + lengths > max).
 * Then distribute spaces: gaps = words-1; each gets totalSpaces/gaps; first (totalSpaces%gaps)
 * gaps get one extra. Edge cases: single word on line → left-pad with spaces; last line →
 * single spaces between words, pad right. Separate the "build a line" logic cleanly.
 */

class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < words.length) {
            int lineLen = words[i].length(), j = i + 1;
            while (j < words.length && lineLen + 1 + words[j].length() <= maxWidth)
                lineLen += 1 + words[j++].length();
            result.add(buildLine(words, i, j, lineLen, maxWidth, j == words.length));
            i = j;
        }
        return result;
    }
    private String buildLine(String[] words, int lo, int hi, int lineLen, int max, boolean last) {
        int gaps = hi - lo - 1;
        StringBuilder sb = new StringBuilder(words[lo]);
        if (gaps == 0 || last) {
            for (int k = lo + 1; k < hi; k++) sb.append(' ').append(words[k]);
            while (sb.length() < max) sb.append(' ');
        } else {
            int totalSpaces = max - lineLen + gaps;
            int each = totalSpaces / gaps, extra = totalSpaces % gaps;
            for (int k = lo + 1; k < hi; k++) {
                int sp = each + (k - lo <= extra ? 1 : 0);
                sb.append(" ".repeat(sp)).append(words[k]);
            }
        }
        return sb.toString();
    }
}
