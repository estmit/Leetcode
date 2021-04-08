import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class MergeIntervals {
    public static int[][] compute(int[][] intervals) {
        Objects.requireNonNull(intervals);
        if (intervals.length == 0) {
            return new int[][]{};
        }

        Arrays.sort(intervals, Comparator.<int[]>comparingInt(i -> i[0]).thenComparingInt(i -> i[1]));
        List<int[]> merged = new ArrayList<>(intervals.length);
        for (int[] interval : intervals) {
            if (merged.isEmpty()) {
                merged.add(interval);
            } else {
                int[] last = merged.get(merged.size() - 1);
                if (merge(last, interval)) {
                    merged.set(merged.size() - 1, last);
                } else {
                    merged.add(interval);
                }
            }
        }
        return merged.toArray(new int[0][]);
    }

    // no intersection
    // a0 a1 b0 b1
    // b0 b1 a0 a1
    //
    // yes intersection
    // a0 b0 b1 a1
    // a0 b0 a1 b1
    // b0 a0 a1 b1
    // b0 a0 b1 a1
    /**
     * if a and b intersect: merge b into a and return true
     * else: return false
     *
     * @param a is mutable
     * @param b is immutable
     * @return
     */
    private static boolean merge(int[] a, int[] b) {
        if (b[1] < a[0] || a[1] < b[0]) {
            return false;
        }

        a[0] = Math.min(a[0], b[0]);
        a[1] = Math.max(a[1], b[1]);
        return true;
    }

    public static void test_merge() {
        // a0 a1 b0 b1
        // b0 b1 a0 a1
        assertThat(merge(new int[] {0, 1}, new int[] {2, 3})).isFalse();
        assertThat(merge(new int[] {2, 3}, new int[] {0, 1})).isFalse();

        // a0 b0 b1 a1
        // a0 b0 a1 b1
        assertThat(merge(new int[] {0, 1}, new int[] {0, 1})).isTrue();
        assertThat(merge(new int[] {0, 1}, new int[] {0, 2})).isTrue();
        assertThat(merge(new int[] {0, 1}, new int[] {1, 2})).isTrue();
        assertThat(merge(new int[] {0, 2}, new int[] {1, 3})).isTrue();
        assertThat(merge(new int[] {0, 3}, new int[] {1, 2})).isTrue();
        assertThat(merge(new int[] {0, 3}, new int[] {1, 3})).isTrue();

        // b0 a0 a1 b1
        // b0 a0 b1 a1
        assertThat(merge(new int[] {0, 3}, new int[] {0, 2})).isTrue();
        assertThat(merge(new int[] {0, 3}, new int[] {1, 2})).isTrue();
        assertThat(merge(new int[] {0, 3}, new int[] {1, 3})).isTrue();
        assertThat(merge(new int[] {1, 2}, new int[] {0, 3})).isTrue();
    }

    public static void main(String[] args) {
        test_merge();
    }
}
