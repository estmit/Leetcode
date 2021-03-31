import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class LongestSubstringWithoutRepeat {
    // s = abcdef
    // s = aaaaaa
    // s = abcdeaa
    // s = abbbbbb
    public static String compute(String s) {
        Objects.requireNonNull(s);
        if (s.length() == 0) {
            return "";
        }

        final int[] cache = new int[128]; // ascii only
        Arrays.fill(cache, -1);

        String longest = "";
        int start = 0; // start and end of window
        for (int end = 0; end < s.length(); ++end) {
            // if current char is repeated (aka this is the 2nd time current char has appeared in the [start, end]
            // then its idx must be >= start
            if (cache[s.charAt(end)] >= start) {
                start = cache[s.charAt(end)] + 1;
            }
            cache[s.charAt(end)] = end;
            // can start > end?
            if (end - start + 1 >= longest.length()) {
                longest = s.substring(start, end + 1);
            }
        }
        return longest;
    }

    public static void main(String[] args) {
        assertThat(compute("").length()).isEqualTo(0);

        String input = "abcdef";
        assertThat(compute(input)).hasSize(6);

        input = "abcdeaa";
        assertThat(compute(input)).hasSize(5);

        input = "aaaaaaa";
        assertThat(compute(input)).hasSize(1);

        input = "aaaaaab";
        assertThat(compute(input)).hasSize(2);

        input = "abbbbbb";
        assertThat(compute(input)).hasSize(2);
    }
}
