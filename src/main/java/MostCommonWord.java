import com.google.common.collect.ImmutableSet;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MostCommonWord {
    // Java 13 allows Set.of()
    private static final Set<Character> wordbreaks = ImmutableSet.of(
            '!',
            '?',
            '\'',
            ',',
            ';',
            '.',
            ' '
    );

    public String mostCommonWord(String paragraph, String[] banned) {
        Set<String> blacklist = Arrays.stream(banned)
//                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        Map<String, Integer> counter = new HashMap<>();
        // we could use regex to split the paragraph and then feed into a stream that lower cases
        // each word and then collect them into a string-to-int map
        // e.g. paragraph.split("(\\s)+|(!|\\?|'|,|;|\\.)(\\s)*")
        // ^ above regex does not split on " , "
        int ws = 0;
        int we = 0;
        while (ws <= we && we < paragraph.length()) {
             while (we < paragraph.length() && !wordbreaks.contains(paragraph.charAt(we))) we++;
             String word = paragraph.substring(ws, we).toLowerCase();
             if (!blacklist.contains(word)) {
                 counter.merge(word, 1, Integer::sum);
             }
            while (we < paragraph.length() && wordbreaks.contains(paragraph.charAt(we))) we++;
            ws = we;
        }
        return counter.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry<String, Integer>::getValue).reversed())
                .limit(1)
                .findFirst()
                .get().getKey();
    }

    public static void main(String[] args) {
        String[] banned = new String[]{ "hit" };
        String paragraph = "Bob hit a ball, the hit BALL flew far after it was hit.";

        MostCommonWord sol = new MostCommonWord();
        String mostCommonWord = sol.mostCommonWord(paragraph, banned);
        assertThat(mostCommonWord).isEqualTo("ball");
    }
}
