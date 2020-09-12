import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import lombok.val;
import lombok.var;

public class TopKFrequentWords {
    /*
     * idea:
     *      build counter for words
     *      use min heap of size k to keep words with highest counts
     */
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> counter = new HashMap<>();
        for (String word : words) {
            counter.merge(word, 1, Integer::sum);
        }

        PriorityQueue<String> bheap = new PriorityQueue<>(k, (x, y) ->
                // y.compareTo(x) instead of x.compareTo(y) because we are reversing the minheap at the end
            counter.get(x).equals(counter.get(y)) ? y.compareTo(x) : counter.get(x) - counter.get(y)
        );
        for (String word : counter.keySet()) {
//            if (bheap.size() == k) {
//                bheap.poll();
//            }
//            bheap.add(word);
            // if you poll before you add, you won't be keeping the smallest k elements
            // but instead the smallest k - 1 elements - which is incorrect!
            bheap.add(word);
            if (bheap.size() > k) {
                bheap.poll();
            }
        }

        List<String> topk = new ArrayList<>();
        while (!bheap.isEmpty()) {
            topk.add(bheap.poll());
        }
        Collections.reverse(topk);
        return topk;
    }

    public static void main(String[] args) {
        String words[] = new String[] {
                "i",
                "love",
                "leetcode",
                "i",
                "love",
                "coding"
        };
        TopKFrequentWords sol = new TopKFrequentWords();
        var result = sol.topKFrequent(words, 2);
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly("i", "love");

        val input = Lists.newArrayList(
                "the",
                "day",
                "is",
                "sunny",
                "the",
                "the",
                "the",
                "sunny",
                "is",
                "is"
        );
        result = sol.topKFrequent(input.toArray(new String[0]), 4);
        assertThat(result).hasSize(4);
        assertThat(result).containsExactly("the", "is", "sunny", "day");
    }
}
