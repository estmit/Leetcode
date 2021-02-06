import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class MeetingRooms {
    // given a list of intervals, check if two intervals conflict with each other
    //
    // definition of a conflict:
    // (1) given [a,b] and [c,d], c < b or d < a
    //     a----b
    //        c----d
    // (2) given [a,b] and [c,d], c < a < b < d or a < c < d < b
    //     a------b
    //       c--d
    //
    // two approaches:
    // (1) sort and then run merge intervals - two intervals can be merged then
    // immediately return false
    // (2) add intervals iteratively to a TreeMap and use ceilingEntry/floorEntry
    // to flag conflicts
    public static boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return true;
        }

        TreeMap<Integer, Integer> intervalTree = new TreeMap<>();
        for (int[] interval : intervals) {
            Map.Entry<Integer, Integer> floorEntry = intervalTree.floorEntry(interval[0]);
            if (floorEntry != null && intersects(floorEntry, interval)) {
                return false;
            }
            Map.Entry<Integer, Integer> ceilingEntry = intervalTree.ceilingEntry(interval[0]);
            if (ceilingEntry != null && intersects(interval, ceilingEntry)) {
                return false;
            }
            intervalTree.put(interval[0], interval[1]);
        }
        return true;
    }

    // precondition: i1[0] <= i2.getKey()
    private static boolean intersects(int[] i1, Map.Entry<Integer, Integer> i2) {
        return i2.getKey() < i1[1];
    }

    // precondition: i1.getKey() <= i2[0]
    private static boolean intersects(Map.Entry<Integer, Integer> i1, int[] i2) {
        return i2[0] < i1.getValue();
    }

    public static void main(String[] args) {
        int[][] intervals = {
                {1, 2}
        };
        assertThat(canAttendMeetings(intervals)).isTrue();

        intervals = new int[][]{
                {1, 2},
                {3, 4}
        };
        assertThat(canAttendMeetings(intervals)).isTrue();

        intervals = new int[][]{
                {1, 2},
                {2, 3}
        };
        assertThat(canAttendMeetings(intervals)).isTrue();

        intervals = new int[][]{
                {1, 3},
                {2, 4}
        };
        assertThat(canAttendMeetings(intervals)).isFalse();

        intervals = new int[][]{
                {1, 4},
                {2, 3}
        };
        assertThat(canAttendMeetings(intervals)).isFalse();

        intervals = new int[][]{
                {1, 2},
                {1, 2}
        };
        assertThat(canAttendMeetings(intervals)).isFalse();
    }
}
