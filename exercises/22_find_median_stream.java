/*
 * #22 | Find Median from Data Stream
 * https://leetcode.com/problems/find-median-from-data-stream/
 * Difficulty: Hard
 * Pattern: Two Heaps (max-heap lower half, min-heap upper half)
 *
 * Design a data structure supporting:
 * - addNum(int num): adds a number to the stream
 * - findMedian(): returns the median of all numbers so far
 *
 * Example:
 * addNum(1), addNum(2), findMedian() -> 1.5
 * addNum(3), findMedian() -> 2.0
 *
 * Constraints:
 * -10^5 <= num <= 10^5
 * At most 5 * 10^4 calls to addNum and findMedian
 */

class MedianFinder {
    PriorityQueue<Integer> lo = new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> hi = new PriorityQueue<>();
    public void addNum(int num) {
        if (lo.isEmpty() || num <= lo.peek()) lo.offer(num); else hi.offer(num);
        if (lo.size() > hi.size() + 1) hi.offer(lo.poll());
        if (hi.size() > lo.size()) lo.offer(hi.poll());
    }
    public double findMedian() {
        if (lo.size() == hi.size()) return ((double)lo.peek() + hi.peek()) / 2.0;
        return lo.peek();
    }
}
