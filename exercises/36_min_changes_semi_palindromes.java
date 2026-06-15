/*
 * #36 | Minimum Changes to Make K Semi-Palindromes
 * https://leetcode.com/problems/minimum-changes-to-make-k-semi-palindromes/
 * Difficulty: Hard
 * Pattern: DP + Precomputed Costs (partition DP)
 *
 * Partition string s into exactly k non-empty substrings. Find the
 * minimum number of character changes to make each substring a
 * semi-palindrome (has a divisor d < len where every d-th subsequence
 * is a palindrome).
 *
 * Example 1:
 * Input: s = "abcac", k = 2
 * Output: 1
 *
 * Example 2:
 * Input: s = "abcdef", k = 2
 * Output: 2
 *
 * Constraints:
 * 2 <= k <= s.length <= 200
 * s consists of lowercase English letters
 */

class Solution {
    public int minimumChanges(String s, int k) {
        int n=s.length(), INF=1_000_000_000; int[][] cost=new int[n][n];
        for(int l=0;l<n;l++) for(int r=l;r<n;r++) cost[l][r]=semiCost(s,l,r);
        int[][] dp=new int[k+1][n+1]; for(int[] row:dp) Arrays.fill(row, INF); dp[0][0]=0;
        for(int parts=1;parts<=k;parts++) for(int end=1;end<=n;end++)
            for(int start=parts-1;start<end;start++)
                dp[parts][end]=Math.min(dp[parts][end], dp[parts-1][start]+cost[start][end-1]);
        return dp[k][n];
    }
    private int semiCost(String s,int l,int r){
        int len=r-l+1, best=1_000_000_000;
        for(int d=1;d<len;d++) if(len%d==0){
            int cur=0;
            for(int off=0;off<d;off++){
                List<Character> arr=new ArrayList<>();
                for(int p=l+off;p<=r;p+=d) arr.add(s.charAt(p));
                for(int i=0,j=arr.size()-1;i<j;i++,j--)
                    if(!arr.get(i).equals(arr.get(j))) cur++;
            }
            best=Math.min(best,cur);
        }
        return best;
    }
}
