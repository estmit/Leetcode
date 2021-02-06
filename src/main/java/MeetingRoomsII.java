import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MeetingRoomsII {
    // given list of intervals, return the minimum number of meeting rooms
    // for all meetings to be scheduled
    //
    // this is more of a bin packing problem than a merge intervals problem
    // instead of being given the number of bins in advance, we are given the
    // intervals and then asked to minimize the number of bins needed them
    // in a non-overlapping way
    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null) {
            return 0;
        }

        if (intervals.length < 2) {
            return intervals.length;
        }

        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[0]));

        // brute-force:
        //  allocate a fixed size array of rooms with size=intervals.length
        //  for each interval
        //      iterate over the array and find the room with the earliest finishing time
        //      put current interval into that room (bin packing step)
        // return number of rooms that are actually in use
        //
        // optimization:
        //  instead of pre-allocating an array and iterating over it for every interval (O(N^2)),
        //  use a min-heap to store the numbers of rooms needed AND to quickly find the room with the earliest finishing time
        PriorityQueue<int[]> rooms = new PriorityQueue<>(Comparator.comparingInt(interval -> interval[1]));
        // it's possible to only store the finishing times
        rooms.add(intervals[0]);
        for (int i = 1; i < intervals.length; ++i) {
            // if the room with the earliest finishing time is free, update (pack the current interval into) that room
            // (by popping the earliest finishing time interval and then adding the current interval to the heap)
            // otherwise, NO OTHER ROOMS ARE FREE; create a new room by adding the current interval to the heap
            // think of rooms as bins
            if (intervals[i][0] >= rooms.peek()[1]) { // current interval can be fitted into room with earliest finishing time
                rooms.poll();
            }

            rooms.add(intervals[i]);
        }
        return rooms.size();
    }

    public static void main(String[] args) {
        int[][] intervals = {
                {0, 30},
                {0, 10},
                {10, 20},
                {20, 30}
        };
        assertThat(minMeetingRooms(intervals)).isEqualTo(2);

        intervals = new int[][] {
                {1, 2},
                {7, 9}
        };
        assertThat(minMeetingRooms(intervals)).isEqualTo(1);

        intervals = new int[][]{
                {1, 3},
                {2, 4},
                {3, 5},
                {4, 6},
                {5, 10},
                {9, 11}
        };
        assertThat(minMeetingRooms(intervals)).isEqualTo(2);

        // edge case - duplicates
        intervals = new int[][]{
                {1, 5},
                {8, 9},
                {8, 9}
        };
        assertThat(minMeetingRooms(intervals)).isEqualTo(2);

        intervals = new int[][]{
                {6, 10},
                {13, 14},
                {12, 14}
        };
        assertThat(minMeetingRooms(intervals)).isEqualTo(2);
    }
}
