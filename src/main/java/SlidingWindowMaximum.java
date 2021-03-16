import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class SlidingWindowMaximum {
    /**
     * idea behind using a bounded priority queue
     * -priority queue takes care of max/min management
     * -if the current element = curr is smaller than pqueue.getMax():
     *  -don't curr add to pqueue
     *  -sliding window max of [curr-2:curr] = pqueue.getMax()
     * -else:
     *  -add to pqueue
     *  -sliding window max of [curr-2:curr] = curr
     *
     * idea of using deque instead of pqueue
     * https://www.nayuki.io/page/sliding-window-minimum-maximum-algorithm - intuitive writeup
     * -window does not need to be at size k at all times;
     * -you only need to know if curr will become the new max at some point!
     * -as you slide the window to the right, we evict elements from the right until
     * we find an element that's greater than curr or the deque becomes empty
     * -do not confuse deque with the window; window = subarray but deque = subsequence
     * -while( curr > deque.tail() ) deque.popTail()
     * -by always evicting elements to the right, those elements rotate over and eventually become new maxes
     * -that means deque.head() is the max!
     *
     * more polished writeup
     * -invariant 1: deque maintains a monotonically decreasing sequence
     * -^ that means deque.head = max
     * -invariant 2: deque size cannot grow > k
     *
     * Window position                Max       Deque
     * ---------------               -----      -------
     * [1  3  -1] -3  5  3  6  7       3        [3, -1]  // init
     *  1 [3  -1  -3] 5  3  6  7       3        [3, -1]  // as long as 3 is in the deque, -3 will never become the new max
     *  1  3 [-1  -3  5] 3  6  7       5        [5]      // 5 is greater than everything to its left
     *  1  3  -1 [-3  5  3] 6  7       5        [5, 3]   // as long as 5 is in the queue, 3 will never become the new max
     *  1  3  -1  -3 [5  3  6] 7       6        [6]
     *  1  3  -1  -3  5 [3  6  7]      7        [7]
     *
     *
     * [10  9  8] 7  6  7  8  9       10       [10, 9, 8]
     *  10 [9  8  7] 6  7  8  9        9       [9, 8, 7]
     *  10  9 [8  7  6] 7  8  9        8       [8, 7, 6]
     *  10  9  8 [7  6  7] 8  9        7       [7, 7]
     *  10  9  8  7 [6  7  8] 9        8       [8]
     *  10  9  8  7  6 [7  8  9]       9       [9]
     *
     *
     * finding the sliding window minimum is trivial (invert everything)
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] compute(int[] nums, int k) {
        if (k == 1) {
            return nums;
        } else if (k == nums.length) {
            int max = Arrays.stream(nums).max().getAsInt();
            return new int[]{ max };
        }

        Deque<Integer> window = new ArrayDeque<>();
        List<Integer> maxs = new ArrayList<>();
        for(int i = 0; i < nums.length; i++) {
            enforceMonotonicity(window, nums, i);
            window.addLast(i); // store indices instead of elements
            if (i >= k - 1) { // window has reached length k and is sliding towards the right
                int maxIdx = window.getFirst();
                maxs.add(nums[maxIdx]);
                if (maxIdx <= i - k + 1) { // evicting indices that become out-of-range
                    window.removeFirst();
                }
            }
        }

        return maxs.stream().mapToInt(Integer::valueOf).toArray();
    }

    // enforce invariant 1 (deque should be monotonically decreasing)
    private static void enforceMonotonicity(Deque<Integer> window, int[] nums, int curr) {
        while (!(window.isEmpty()) && nums[curr] > nums[window.getLast()]) {
            window.removeLast();
        }
    }

    public static void main(String[] args) {
        int[] input = { 1, 3, -1, -3, 5, 3, 6, 7 };
        int[] output = compute(input, 3);
        System.out.println(Arrays.toString(output));

        input = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
        output = compute(input, 3);
        System.out.println(Arrays.toString(output));
        assertThat(output).isSorted();

        input = new int[] { 8, 7, 6, 5, 4, 3, 2, 1 };
        output = compute(input, 3);
        System.out.println(Arrays.toString(output));
        assertThat(output).isSortedAccordingTo(Comparator.reverseOrder());
    }
}
