/*
 * #12 | 4Sum
 * https://leetcode.com/problems/4sum/
 * Difficulty: Medium
 * Pattern: Two Pointers (sort + fix two + shrink)
 *
 * Given an integer array nums and a target, return all unique quadruplets
 * that sum to target. The solution set must not contain duplicates.
 *
 * Example 1:
 * Input: nums = [1,0,-1,0,-2,2], target = 0
 * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 *
 * Example 2:
 * Input: nums = [2,2,2,2,2], target = 8
 * Output: [[2,2,2,2]]
 *
 * Constraints:
 * 1 <= nums.length <= 200
 * -10^9 <= nums[i] <= 10^9
 * -10^9 <= target <= 10^9
 */

/*
 * INSIGHT:
 * 4Sum = 3Sum + one more fixed loop. The pattern scales: kSum is O(n^(k-1))
 * with k-2 fixed loops plus two shrinking pointers.
 * Two gotchas unique to 4Sum:
 *   1. Cast to long before summing — four ints at ±10^9 overflow int.
 *   2. Inner loop's duplicate check uses j > i+1 (not j > 0) because j
 *      restarts at i+1 each outer iteration.
 */

class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                int l = j + 1, r = n - 1;
                while (l < r) {
                    long sum = (long)nums[i] + nums[j] + nums[l] + nums[r];
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        l++; r--;
                        while (l < r && nums[l] == nums[l - 1]) l++;
                        while (l < r && nums[r] == nums[r + 1]) r--;
                    } else if (sum < target) l++; else r--;
                }
            }
        }
        return res;
    }
}
