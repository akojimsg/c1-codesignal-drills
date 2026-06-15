/*
 * #13 | Happy Number
 * https://leetcode.com/problems/happy-number/
 * Difficulty: Easy
 * Pattern: HashSet (cycle detection)
 *
 * Repeatedly replace a number with the sum of squares of its digits.
 * Return true if the process eventually reaches 1, false if it loops forever.
 *
 * Example 1:
 * Input: n = 19
 * Output: true (19 -> 82 -> 68 -> 100 -> 1)
 *
 * Example 2:
 * Input: n = 2
 * Output: false
 *
 * Constraints:
 * 1 <= n <= 2^31 - 1
 */

class Solution {
    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        while (n != 1 && seen.add(n)) n = next(n);
        return n == 1;
    }
    private int next(int n) {
        int s = 0;
        while (n > 0) { int d = n % 10; s += d * d; n /= 10; }
        return s;
    }
}
