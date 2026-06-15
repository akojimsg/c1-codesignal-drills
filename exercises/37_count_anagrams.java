/*
 * #37 | Count Anagrams
 * https://leetcode.com/problems/count-anagrams/
 * Difficulty: Hard
 * Pattern: Math / Combinatorics (factorial with modular inverse)
 *
 * Given a string s of words separated by spaces, for each word count
 * the number of distinct anagram arrangements. Return the product of
 * all words' counts modulo 10^9 + 7.
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
 * s contains at least one word, no leading/trailing spaces, single spaces between words
 */

class Solution {
    static final long MOD = 1_000_000_007L;
    public int countAnagrams(String s) {
        String[] words = s.split(" "); int max = 0;
        for(String w:words) max=Math.max(max,w.length());
        long[] fact=new long[max+1], invFact=new long[max+1]; fact[0]=1;
        for(int i=1;i<=max;i++) fact[i]=fact[i-1]*i%MOD;
        invFact[max]=pow(fact[max],MOD-2);
        for(int i=max;i>0;i--) invFact[i-1]=invFact[i]*i%MOD;
        long ans=1;
        for(String w:words){
            int[] cnt=new int[26]; for(char c:w.toCharArray()) cnt[c-'a']++;
            long ways=fact[w.length()]; for(int c:cnt) ways=ways*invFact[c]%MOD;
            ans=ans*ways%MOD;
        }
        return (int)ans;
    }
    private long pow(long a,long e){
        long r=1; while(e>0){if((e&1)==1) r=r*a%MOD; a=a*a%MOD; e>>=1;} return r;
    }
}
