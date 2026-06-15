/*
 * #37 | Count Anagrams
 * https://leetcode.com/problems/count-anagrams/
 * Difficulty: Hard
 * Pattern: Combinatorics (Permutations with Repetitions) + Modular Arithmetic
 *
 * Given a space-separated string s, for each word compute the number of
 * distinct anagrams (arrangements of its characters), then return the
 * product of all those counts modulo 10^9 + 7.
 *
 * Example 1:
 * Input: s = "too hot"
 * Output: 18
 *
 * Example 2:
 * Input: s = "aa"
 * Output: 1
 *
 * Constraints:
 * 1 <= s.length <= 10^5
 * s consists of lowercase English letters and spaces
 * Adjacent words separated by single space, no leading/trailing spaces
 */

/*
 * INSIGHT:
 * Distinct arrangements of a word of length n = n! / (freq[a]! × freq[b]! × ...).
 * Compute using precomputed factorials and modular inverses (Fermat's little theorem:
 * inv(x) = x^(MOD-2) mod MOD). Multiply results for each word together.
 * Precompute all factorials once (up to max length) to avoid recomputation per word.
 */

class Solution {
    static final int MOD = 1_000_000_007;

    public long countAnagrams(String s) {
        int n = s.length();
        long[] fact = new long[n + 1];
        fact[0] = 1;
        for (int i = 1; i <= n; i++) fact[i] = fact[i-1] * i % MOD;
        long ans = 1;
        for (String word : s.split(" ")) {
            int[] cnt = new int[26];
            for (char c : word.toCharArray()) cnt[c - 'a']++;
            long ways = fact[word.length()];
            for (int x : cnt) ways = ways * modPow(fact[x], MOD - 2) % MOD;
            ans = ans * ways % MOD;
        }
        return ans;
    }
    private long modPow(long base, long exp) {
        long result = 1; base %= MOD;
        while (exp > 0) {
            if ((exp & 1) == 1) result = result * base % MOD;
            base = base * base % MOD;
            exp >>= 1;
        }
        return result;
    }
}
