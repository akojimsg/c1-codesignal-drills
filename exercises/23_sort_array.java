/*
 * #23 | Sort an Array
 * https://leetcode.com/problems/sort-an-array/
 * Difficulty: Medium
 * Pattern: Merge Sort (avoids quicksort worst case)
 *
 * Given an array of integers, sort it in ascending order without using
 * any built-in sort functions.
 *
 * Example 1:
 * Input: nums = [5,2,3,1]
 * Output: [1,2,3,5]
 *
 * Example 2:
 * Input: nums = [5,1,1,2,0,0]
 * Output: [0,0,1,1,2,5]
 *
 * Constraints:
 * 1 <= nums.length <= 5 * 10^4
 * -5 * 10^4 <= nums[i] <= 5 * 10^4
 */

class Solution {
    public int[] sortArray(int[] nums) {
        mergeSort(nums, 0, nums.length - 1, new int[nums.length]); return nums;
    }
    private void mergeSort(int[] a, int l, int r, int[] tmp) {
        if (l >= r) return; int m = l + (r - l) / 2;
        mergeSort(a, l, m, tmp); mergeSort(a, m + 1, r, tmp); merge(a, l, m, r, tmp);
    }
    private void merge(int[] a, int l, int m, int r, int[] tmp) {
        int i = l, j = m + 1, k = l;
        while (i <= m && j <= r) tmp[k++] = a[i] <= a[j] ? a[i++] : a[j++];
        while (i <= m) tmp[k++] = a[i++]; while (j <= r) tmp[k++] = a[j++];
        for (i = l; i <= r; i++) a[i] = tmp[i];
    }
}
