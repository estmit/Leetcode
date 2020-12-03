import java.util.*;

public class CourseSchedule {
    private enum Status {
        UNVISITED,
        VISITED_ON_STACK,
        VISITED
    };

    // cycle detection using DFS
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses == 1 || prerequisites == null || prerequisites.length == 0) {
            return true;
        }

        // convert to adjList
        Map<Integer, Set<Integer>> courseToPrereqs = new HashMap<>();
        for (int[] prerequisite : prerequisites) {
            int course = prerequisite[0];
            int prereq = prerequisite[1];
            courseToPrereqs.computeIfAbsent(course, c -> new HashSet<>()).add(prereq);
        }

        Status[] visitStatus = new Status[numCourses];
        Arrays.fill(visitStatus, Status.UNVISITED);

        for (int course = 0; course < numCourses; ++course) {
            if (courseToPrereqs.containsKey(course) &&
                    visitStatus[course] == Status.UNVISITED &&
                    prereqCycleDetected(course, courseToPrereqs, visitStatus)) {
                return false;
            }
        }
        return true;
    }

    private boolean prereqCycleDetected(int course, Map<Integer, Set<Integer>> courseToPrereqs, Status[] visitStatus) {
        if (visitStatus[course] == Status.VISITED_ON_STACK) {
            return true;
        }

        visitStatus[course] = Status.VISITED_ON_STACK;
        Set<Integer> prereqs = courseToPrereqs.getOrDefault(course, Collections.emptySet());
        for (Integer prereq : prereqs) {
            if (visitStatus[prereq] == Status.VISITED_ON_STACK ||
                    prereqCycleDetected(prereq, courseToPrereqs, visitStatus)) {
                return true;
            }
        }
        visitStatus[course] = Status.VISITED;
        return false;
    }
}
