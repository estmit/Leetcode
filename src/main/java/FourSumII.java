import java.util.HashMap;
import java.util.Map;

public class FourSumII {
    public static int count(int[] A, int[] B, int[] C, int[] D) {
        int counts = 0;

        Map<Integer, Integer> CDSums = new HashMap<>();
        for (int i = 0; i < C.length; ++i) {
            for (int j = 0; j < D.length; ++j) {
                int sum = C[i] + D[j];
                // if sum not in map, set value to 1;
                // else, apply remap bifunction (Integer::sum) to 1 and CDSums.get(sum)
                CDSums.merge(sum, 1, Integer::sum);
            }
        }

        for (int i = 0; i < A.length; ++i) {
            for (int j = 0; j < B.length; ++j) {
                int sum = -A[i] - B[j];
                if (CDSums.containsKey(sum)) {
                    // ++counts would have been wrong!
                    counts = counts + CDSums.get(sum);
                }
            }
        }

        return counts;
    }
}
