import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class StringPermutationInclusion {
    // check if any of s1's permutation is in s2
    //
    // brute-force:
    //   - generate all substrings of s1 (prune substrings where length != s2.length())
    //   - check if substring and s2 have the same hash
    //
    // optimization:
    //   - this is similar to minimum window substring except that window is fixed in length (s2.length)
    //
    // s1, s2 will NOT be empty
    public static boolean compute(String s1, String s2) {
        Objects.requireNonNull(s1);
        Objects.requireNonNull(s2);

        if (s1.length() > s2.length()) {
            return false;
        }

        final Map<Character, Long> counter = s1.chars().mapToObj(c -> (char)c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        int charsToCover = s1.length();
        for (int start = 0, end = 0; end < s2.length(); ++end) {
            char right = s2.charAt(end);
            if (counter.containsKey(right) && counter.merge(right, -1l, Long::sum) >= 0) {
                --charsToCover;
            }

            // grow window to size s1.length()
            if (end - start + 1 == s1.length()) {
                if (charsToCover == 0) {
                    return true; // if we want list of all inclusions, then push s2.substring(start, end + 1) to a list
                }

                // what we are trying to do here is move the start of the window forward by 1 and
                // therefore evicting that first character
                // if $left is in $counter and $counter[$left]++ > 0, then evicting $left means that you're losing one of the
                // covered chars (++$charsToCover)
                char left = s2.charAt(start);
                if (counter.containsKey(left) && counter.merge(left, 1L, Long::sum) > 0) {
                    ++charsToCover;
                }
                ++start;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // corner case #1 - s1 > s2
        assertThat(compute("abcde", "abcd")).isFalse();

        // LC examples
        assertThat(compute("ab", "eidbaooo")).isTrue();
        assertThat(compute("ab", "eidboaoo")).isFalse();

        assertThat(compute("abc", "xyzabdcxy")).isFalse();

        assertThat(compute("abc", "xyzcbacxy")).isTrue(); // overlap shouldn't matter

        // boundary tests
        assertThat(compute("abc", "abccbacxy")).isTrue();
        assertThat(compute("abc", "xyztuvcab")).isTrue();
    }
}
