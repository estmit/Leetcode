import java.util.Comparator;
import java.util.PriorityQueue;

public class KClosestPointToOrigin {
    private static class Point {
        private final int[] point;
        private final int dist;
        public Point(int[] point) {
            this.point = point;
            this.dist = point[0] * point[0] + point[1] * point[1];
        }

        public int[] getPoint() {
            return this.point;
        }

        public int getDist() {
            return this.dist;
        }
    }

    public int[][] kClosest(int[][] points, int K) {
        PriorityQueue<Point> maxHeap = new PriorityQueue<>(Comparator.comparing(Point::getDist).reversed());
        for (int[] point : points) {
            Point p = new Point(point);
            maxHeap.add(p);

            if (maxHeap.size() > K) { // keep heap capped at size = K
                maxHeap.poll();
            }
        }

        return maxHeap.stream().map(Point::getPoint).toArray(int[][]::new);
    }
}
