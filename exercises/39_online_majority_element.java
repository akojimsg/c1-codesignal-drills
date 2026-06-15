/*
 * #39 | Online Majority Element in Subarray
 * https://leetcode.com/problems/online-majority-element-in-subarray/
 * Difficulty: Hard
 * Pattern: Randomized Candidate + Binary Search on positions
 *
 * Design a structure for array arr supporting query(left, right, threshold):
 * return any element that appears at least threshold times in
 * arr[left..right], or -1 if none exists.
 *
 * Example:
 * Input: arr = [1,1,2,1,1], queries = [[0,4,5],[0,3,4],[2,3,2]]
 * Output: [1,1,-1]
 *
 * Constraints:
 * 1 <= arr.length <= 2 * 10^4
 * 1 <= threshold <= right - left + 1
 * 2 * threshold > right - left + 1 (majority guaranteed if exists)
 */

class MajorityChecker {
    int[] arr; Map<Integer,List<Integer>> pos=new HashMap<>(); Random rand=new Random(1);
    public MajorityChecker(int[] arr) {
        this.arr=arr;
        for(int i=0;i<arr.length;i++) pos.computeIfAbsent(arr[i],x->new ArrayList<>()).add(i);
    }
    public int query(int left, int right, int threshold) {
        for(int t=0;t<20;t++){
            int v=arr[left+rand.nextInt(right-left+1)]; List<Integer> p=pos.get(v);
            int cnt=upper(p,right)-lower(p,left); if(cnt>=threshold) return v;
        }
        return -1;
    }
    private int lower(List<Integer> a,int x){
        int l=0,r=a.size();
        while(l<r){int m=(l+r)/2;if(a.get(m)<x)l=m+1;else r=m;}return l;
    }
    private int upper(List<Integer> a,int x){
        int l=0,r=a.size();
        while(l<r){int m=(l+r)/2;if(a.get(m)<=x)l=m+1;else r=m;}return l;}
}
