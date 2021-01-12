import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmployeeImportance {
    class Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;
    }

    public int getImportance(List<Employee> employees, int id) {
        Map<Integer, Employee> idMap = employees.stream()
                .collect(Collectors.toMap(e -> e.id, Function.identity()));

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(id);
        int sum = 0;
        while (!queue.isEmpty()) {
            Employee next = idMap.get(queue.remove());
            sum += next.importance;
            queue.addAll(next.subordinates);
        }
        return sum;
    }
}
