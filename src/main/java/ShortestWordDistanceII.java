import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortestWordDistanceII {
    private Map<String, List<Integer>> wordToIndices = new HashMap<>();

    public ShortestWordDistanceII(String words[]) {
        if (words == null || words.length == 0) {
            return;
        }

        for (int i = 0; i < words.length; ++i) {
            wordToIndices.computeIfAbsent(words[i], word -> new ArrayList<>()).add(i);
        }
    }

    // preconditions: word1 != word2; word1, word2 in words
    public int shortest(String word1, String word2) {
        List<Integer> word1Ind = wordToIndices.get(word1);
        List<Integer> word2Ind = wordToIndices.get(word2);

        int shortest = Integer.MAX_VALUE;
        for (int i = 0, j = 0; i < word1Ind.size() && j < word2Ind.size(); ) {
            int dist = Math.abs(word1Ind.get(i) - word2Ind.get(j));
            shortest = Integer.min(shortest, dist);

            // given 2 sorted lists of ints (no duplicates in each list), we want to find
            // the int in each list that would yield the smallest difference
            // A = [1, 3, 5]
            // B = [0, 7]
            // smallest difference is 1 (1 - 0 = 1)
            // brute-force = O(N^2) nested for loop
            // optimal = O(N) using two pointers, a for A and b for B
            // upshot: minimize A[a] - B[b]
            // if A[a] < B[b], we want to advance a since A[a + 1] > A[a] and that in theory,
            // abs(B[b] - A[a + 1]) should be less than abs(B[b] - A[a])
            // (similar to binary search)
            if (word1Ind.get(i) < word2Ind.get(j)) {
                ++i;
            } else {
                ++j;
            }
        }
        return shortest;
    }

    public static void main(String[] args) {
        String words[] = new String[] {
                "practice",
                "makes",
                "perfect",
                "coding",
                "makes"
        };

        ShortestWordDistanceII sol = new ShortestWordDistanceII(words);
        assertThat(sol.shortest("coding", "practice")).isEqualTo(3);
        assertThat(sol.shortest("makes", "coding")).isEqualTo(1);
    }
}
