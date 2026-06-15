/*
 * #38 | Longest Common Prefix of K Strings After Removal
 * https://leetcode.com/problems/longest-common-prefix-of-k-strings-after-removal/
 * Difficulty: Hard
 * Pattern: Trie + Prefix Counts
 *
 * Given an array of strings words and integer k, for each word removed,
 * return the length of the longest common prefix shared by at least k
 * of the remaining strings.
 *
 * Example 1:
 * Input: words = ["jump","run","run","jump","run"], k = 2
 * Output: [3,4,4,3,4]
 *
 * Constraints:
 * 1 <= words.length <= 10^5
 * 1 <= k <= words.length
 * 1 <= words[i].length <= 10^4
 */

class Solution {
    static class Node { Node[] ch=new Node[26]; int cnt, depth; }
    public int[] longestCommonPrefix(String[] words, int k) {
        Node root=new Node(); List<Node> all=new ArrayList<>(); all.add(root);
        Map<String,List<Node>> paths=new HashMap<>();
        for(String w:words) paths.put(w, insert(root,w,all));
        TreeMap<Integer,Integer> good=new TreeMap<>();
        for(Node node:all) if(node.depth>0&&node.cnt>=k)
            good.put(node.depth, good.getOrDefault(node.depth,0)+1);
        int[] ans=new int[words.length];
        for(int i=0;i<words.length;i++){
            List<Node> path=paths.get(words[i]);
            for(Node node:path) adjust(good,node,-1,k);
            ans[i]=good.isEmpty()?0:good.lastKey();
            for(Node node:path) adjust(good,node,1,k);
        }
        return ans;
    }
    private List<Node> insert(Node root,String w,List<Node> all){
        List<Node> path=new ArrayList<>(); Node cur=root;
        for(char c:w.toCharArray()){int x=c-'a';
            if(cur.ch[x]==null){cur.ch[x]=new Node();cur.ch[x].depth=cur.depth+1;all.add(cur.ch[x]);}
            cur=cur.ch[x]; cur.cnt++; path.add(cur);} return path;
    }
    private void adjust(TreeMap<Integer,Integer> good,Node node,int delta,int k){
        if(node.cnt>=k) good.merge(node.depth,-1,Integer::sum);
        if(good.getOrDefault(node.depth,0)<=0) good.remove(node.depth);
        node.cnt+=delta;
        if(node.cnt>=k) good.merge(node.depth,1,Integer::sum);
    }
}
