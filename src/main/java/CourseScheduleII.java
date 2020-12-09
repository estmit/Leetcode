import java.util.*;
import java.util.stream.IntStream;

public class CourseScheduleII {
    private enum Status {
        NEW,
        ACTIVE,
        VISITED
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if (numCourses < 2 || prerequisites == null || prerequisites.length == 0) {
            return IntStream.range(0, numCourses).toArray();
        }

        final Map<Integer, Set<Integer>> adjList = new HashMap<>();
        for (int[] prereq : prerequisites) {
            // prereq[1] is a prerequisite of prereq[0]
            // we want a "reverse" topological ordering
            // so set prereq[0] -> prereq[1]
            adjList.computeIfAbsent(prereq[0], p -> new HashSet<>()).add(prereq[1]);
        }

        final Status[] statuses = new Status[numCourses];
        Arrays.fill(statuses, Status.NEW);

        List<Integer> ordering = new ArrayList<>();
        for (int course = 0; course < numCourses; course++) {
            if (statuses[course] == Status.NEW && postOrderDfs(adjList, statuses, course, ordering)) {
                return new int[0];
            }
        }
        return ordering.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Run post-order DFS
     * Return if cycle is found or not
     */
    public boolean postOrderDfs(Map<Integer, Set<Integer>> adjList,
                                Status[] statuses,
                                int vertex,
                                List<Integer> ordering) {
        if (statuses[vertex] == Status.ACTIVE) { // back edge!
            return true;
        }

        statuses[vertex] = Status.ACTIVE;
        Set<Integer> outgoingV = adjList.getOrDefault(vertex, Collections.emptySet());
        for (int v : outgoingV) {
            if (statuses[v] == Status.ACTIVE) { // back edge!
                return true;
            }

            // run DFS on outgoing vertex; if there's a cycle, stop!
            if (statuses[v] != Status.VISITED && postOrderDfs(adjList, statuses, v, ordering)) {
                    return true;
            }
        }
        statuses[vertex] = Status.VISITED;
        ordering.add(vertex);
        return false;
    }
}
