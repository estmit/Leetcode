import java.util.Comparator;
import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamMedian {
    // max heap
    private final PriorityQueue<Integer> lo = new PriorityQueue<>(Comparator.reverseOrder());

    // min heap
    private final PriorityQueue<Integer> hi = new PriorityQueue<>();

    /** initialize your data structure here. */
    public StreamMedian() {

    }

    public void addNum(int num) {
        // invariants
        // 1. 0 <= | lo.size() - hi.size() | <= 1
        // 2. all elements of lo are smaller than all elements of hi

        // maintain invariant #2
        if (lo.isEmpty() || num <= lo.element()) {
            lo.add(num);
        } else {
            hi.add(num);
        }

        // maintain invariant #1
        // this will run until len(lo) == len(hi) or len(lo) = len(hi) + 1
        while (hi.size() - lo.size() > 0) {
            lo.add(hi.remove());
        }

        while (lo.size() - hi.size() > 1) {
            hi.add(lo.remove());
        }
    }

    public double findMedian() {
        if (lo.isEmpty() && hi.isEmpty()) {
            throw new IllegalStateException("stream is empty");
        }

        if (lo.size() > hi.size()) {
            return lo.element();
        } else {
            return lo.element() + (hi.element() - lo.element()) / 2.0;
        }
    }

    public static void main(String[] args) {
        StreamMedian sm = new StreamMedian();
        sm.addNum(1);
        sm.addNum(2);
        assertThat(sm.findMedian()).isEqualTo(1.5);
        sm.addNum(2);
        assertThat(sm.findMedian()).isEqualTo(2.0);
    }
}
