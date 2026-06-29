/*
 * #3 | Merge Sorted Array
 * https://leetcode.com/problems/merge-sorted-array/
 * Difficulty: Easy
 * Pattern: Two Pointers (from the back)
 *
 * Given two sorted arrays nums1 and nums2, merge nums2 into nums1
 * in-place. nums1 has length m+n where the last n elements are 0
 * placeholders. nums2 has length n.
 *
 * Example 1:
 * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * Output: [1,2,2,3,5,6]
 *
 * Example 2:
 * Input: nums1 = [1], m = 1, nums2 = [], n = 0
 * Output: [1]
 *
 * Constraints:
 * 0 <= m, n <= 200
 * nums1.length == m + n
 * nums2.length == n
 */

/*
 * INSIGHT:
 * Merge from the BACK. Front-to-back merging would overwrite nums1 elements we still need.
 * From the back, the trailing zeros are free space — every write lands on a slot that's
 * already been consumed or is a placeholder. When i goes negative, the remaining nums2
 * elements fall in correctly. Remaining nums1 elements need no movement.
 */
import java.util.*;

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) nums1[k--] = nums1[i--];
            else nums1[k--] = nums2[j--];
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums1 = {1,2,3,0,0,0}, nums2 = {2,5,6};
        s.merge(nums1, 3, nums2, 3);
        //SOT Expected: [1, 2, 2, 3, 5, 6]
        System.out.println("Expected: [1, 2, 2, 3, 5, 6]");
        System.out.println("Actual:   " + Arrays.toString(nums1));
    }
}
