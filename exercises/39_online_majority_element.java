/*
 * #39 | Online Majority Element In Subarray
 * https://leetcode.com/problems/online-majority-element-in-subarray/
 * Difficulty: Hard
 * Pattern: Boyer-Moore Majority Vote + Binary Search
 *
 * Design a data structure that answers queries: for a given [left, right, threshold],
 * return an element that appears at least threshold times in the subarray, or -1.
 *
 * Example:
 * Input: ["MajorityChecker","query","query","query"]
 *        [[arr = [1,1,2,2,1,1]], [0,5,4], [0,3,3], [2,3,2]]
 * Output: [null,1,-1,2]
 *
 * Constraints:
 * 1 <= arr.length <= 2 * 10^4
 * 1 <= query threshold <= right - left + 1
 * At most 10^4 queries
 */

/*
 * INSIGHT:
 * Boyer-Moore on a random sample: with high probability, if a majority element exists
 * (≥threshold occurrences in range of length L), sampling ~20 random positions will
 * hit it. For each candidate from sampling, verify with binary search on a precomputed
 * index list (positions where that value appears) — use lower_bound/upper_bound to
 * count occurrences in [left,right] in O(log n). No candidate after sampling → return -1.
 */

class Solution {
    private int[] arr;
    private Map<Integer, List<Integer>> positions;
    private Random rand;

    public Solution(int[] arr) {
        this.arr = arr;
        positions = new HashMap<>();
        rand = new Random();
        for (int i = 0; i < arr.length; i++)
            positions.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
    }

    public int query(int left, int right, int threshold) {
        int len = right - left + 1;
        for (int t = 0; t < 20; t++) {
            int candidate = arr[left + rand.nextInt(len)];
            List<Integer> idx = positions.getOrDefault(candidate, List.of());
            int lo = Collections.binarySearch(idx, left);
            if (lo < 0) lo = ~lo;
            int hi = Collections.binarySearch(idx, right + 1);
            if (hi < 0) hi = ~hi;
            if (hi - lo >= threshold) return candidate;
        }
        return -1;
    }
}
