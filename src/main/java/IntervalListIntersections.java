import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IntervalListIntersections {
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        if (A == null || A.length == 0 || B == null || B.length == 0) {
            return new int[][]{};
        }

        List<int[]> output = new ArrayList<>();
        // two pointer approach
        int a = 0, b = 0;
        while (a < A.length && b < B.length) {
            int[] rangeA = A[a];
            int[] rangeB = B[b];

            int start = Math.max(rangeA[0], rangeB[0]);
            int end = Math.min(rangeA[1], rangeB[1]);

            if (start <= end) {
                output.add(new int[]{start, end});
            }

            if (rangeA[1] < rangeB[1]) {
                a++;
            } else {
                b++;
            }

            // in either case, we need to make the same calculation to determine whether
            // to increment a or b after the merge
            // therefore we can optimize by noting the following observations:
            // if there's an intersection, then the intersecting interval is
            //     [ max(rangeA.start, rangeB.start), min(rangeA.end, rangeB.end) ]
            // if the 2 ranges are disjoint then
            //      max(rangeA.start, rangeB.start) > min(rangeA.end, rangeB.end) which doesn't intersect
//            if (rangeA[1] < rangeB[0] || rangeB[1] < rangeA[0]) { // no overlap
//                if (rangeA[1] < rangeB[1]) {
//                    a++;
//                } else if (rangeB[1] < rangeA[1]) {
//                    b++;
//                }
//            } else {
//                output.add(new int[]{
//                        Math.max(rangeA[0], rangeB[0]),
//                        Math.min(rangeA[1], rangeB[1])
//                });
//                if (rangeA[1] < rangeB[1]) {
//                    a++;
//                } else {
//                    b++;
//                }
//            }
        }
        return output.stream().toArray(int[][]::new);
    }

    public static void main(String[] args) {
        //[[0,2],[5,10],[13,23],[24,25]]
        //[[1,5],[8,12],[15,24],[25,26]]
        IntervalListIntersections sol = new IntervalListIntersections();
        int[][] A = new int[][]{{0,2},{5,10},{13,23},{24,25}};
        int[][] B = new int[][]{{1,5},{8,12},{15,24},{25,26}};
        int[][] output = sol.intervalIntersection(A, B);

        output = sol.intervalIntersection(new int[][]{{0,1}}, new int[][]{{2,3}});
        assertThat(output).isEmpty();

        output = sol.intervalIntersection(new int[][]{{0,1}}, new int[][]{{1,2}});
        assertThat(output).isEqualTo(new int[][]{{1,1}});

        output = sol.intervalIntersection(new int[][]{{0,2}}, new int[][]{{1,3}});
        assertThat(output).isEqualTo(new int[][]{{1,2}});

        output = sol.intervalIntersection(new int[][]{{0,1}}, new int[][]{{0,2}});
        assertThat(output).isEqualTo(new int[][]{{0,1}});

        output = sol.intervalIntersection(new int[][]{{1,6}}, new int[][]{{1,2},{3,4},{5,6}});
        assertThat(output).isEqualTo(new int[][]{{1,2},{3,4},{5,6}});
    }
}
