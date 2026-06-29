/*
 * #4 | Rotate Array
 * https://leetcode.com/problems/rotate-array/
 * Difficulty: Medium
 * Pattern: Array Manipulation (triple reverse)
 *
 * Given an integer array nums, rotate the array to the right by k steps
 * in-place.
 *
 * Example 1:
 * Input: nums = [1,2,3,4,5,6,7], k = 3
 * Output: [5,6,7,1,2,3,4]
 *
 * Example 2:
 * Input: nums = [-1,-100,3,99], k = 2
 * Output: [3,99,-1,-100]
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 * 0 <= k <= 10^5
 */


/* NOTES:
*
* Idea I: shift every item to the right by 1, replace the last item with the first. 
* Do this k times. O(nk) time, O(1) space.
*
*    public void bruteForce(int[] nums, int k) {
*        int lastIndex = nums.length-1;
*        for(int i=0; i < k; i++) {
*            int lastNumber = nums[lastIndex];
*            //rotate from last to first
*            for(int j=lastIndex; j > 0; j--) {
*                nums[j] = nums[j-1];
*            }
*            nums[0] = lastNumber; 
*        }
*    }
*
* Idea II: copy the original array, then for each index i, write the value from (i-k) % n. O(n) time, O(n) space.
*    public void permutationApplication(int[] nums, int k) {
*        int n = nums.length;
*        int[] copy = Arrays.copyOf(nums, n);
*        for(int i=0; i < n; i++) {
*            nums[i] = copy[(i-k+n) % n];
*        }
*    }
*
* Idea III: Cyclic Replacement. O(n) time, O(1) space.
*    public void cyclicReplacement(int[] nums, int k) {
*        int n = nums.length;
*        k = k % n;
*        int count = 0; // number of elements moved
*        for (int start = 0; count < n; start++) {
*            int current = start;
*            int prev = nums[start];
*            do {
*                int next = (current + k) % n;
*                int temp = nums[next];
*                nums[next] = prev;
*                prev = temp;
*                current = next;
*                count++;
*            } while (start != current);
*        }
*    }
*/


/*
 * INSIGHT:
 * Most common solution is triple reverse. Rotating right by k equals three reversals:
 * Rotating right by k equals three reversals:
 *   [1,2,3,4,5,6,7], k=3
 *   → reverse all     → [7,6,5,4,3,2,1]
 *   → reverse [0,k)   → [5,6,7,4,3,2,1]
 *   → reverse [k,n)   → [5,6,7,1,2,3,4]  ✓
 * Reversing all places the two halves in the right slots; reversing each half
 * un-flips their internal order. Use k %= n first — rotating by n is a no-op.
 */

class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }
    private void reverse(int[] a, int l, int r) {
        while (l < r) { int t = a[l]; a[l++] = a[r]; a[r--] = t; }
    }

    //Test case with array of one item, rotate by 1
}
