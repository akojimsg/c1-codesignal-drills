/*
 * #36 | Minimum Changes to Make K Semi-palindromes
 * https://leetcode.com/problems/minimum-changes-to-make-k-semi-palindromes/
 * Difficulty: Hard
 * Pattern: DP + Precomputed Cost Table
 *
 * A semi-palindrome of divisor d: split string into d equal parts; corresponding
 * characters in mirrored parts must match. Return minimum character changes to
 * split s into k non-empty semi-palindromes.
 *
 * Constraints:
 * 2 <= s.length <= 200
 * 1 <= k <= s.length / 2
 * s consists of lowercase English letters
 */

/*
 * INSIGHT:
 * Precompute cost[i][j] = min changes to make s[i..j] a semi-palindrome (try all valid
 * divisors d of (j-i+1) where d <= (j-i+1)/2).
 * Then DP: dp[p][q] = min changes to partition s[0..q] into p semi-palindromes.
 * dp[p][q] = min over all split points m of (dp[p-1][m] + cost[m+1][q]).
 * Base: dp[1][q] = cost[0][q].
 * This is the classic "partition into k pieces with cost" DP.
 */

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] cost = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int len = j - i + 1;
                int best = len;
                for (int d = 1; d * 2 <= len; d++) {
                    if (len % d != 0) continue;
                    int changes = 0;
                    for (int p = 0; p < d; p++) {
                        int lo = i + p, hi = j - p;
                        while (lo < hi) {
                            if (s.charAt(lo) != s.charAt(hi)) changes++;
                            lo += d; hi -= d;
                        }
                    }
                    best = Math.min(best, changes);
                }
                cost[i][j] = best;
            }
        }
        int[][] dp = new int[k + 1][n];
        for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE / 2);
        for (int q = 0; q < n; q++) dp[1][q] = cost[0][q];
        for (int p = 2; p <= k; p++) {
            for (int q = 2 * p - 1; q < n; q++) {
                for (int m = 2 * (p - 1) - 1; m < q; m++) {
                    if (dp[p-1][m] < Integer.MAX_VALUE / 2)
                        dp[p][q] = Math.min(dp[p][q], dp[p-1][m] + cost[m+1][q]);
                }
            }
        }
        return dp[k][n-1];
    }
}
