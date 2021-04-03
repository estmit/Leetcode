import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MinimumWindowSubstring {
    //Input: s = "ADOBECODEBANC", t = "ABC"
    //Output: "BANC"
    //what if there are duplicates in t?
    public static String compute(String s, String t) {
        Objects.requireNonNull(s);
        Objects.requireNonNull(t);
        if (s.isEmpty() || t.isEmpty() || s.length() < t.length()) {
            return "";
        } else if (t.length() == 1) {
            return s.contains(t) ? t : "";
        }

        // brute-force:
        // come up with ALL substrings -- O(N^2)
        // trim substrings where length < target.length
        // trim substrings where substring[0] and substring[-1] are NOT in target
        // test target against each substring (subset test) and keep track of shortest substring -- linear
        // subset test -- convert substring to hashset and iterate over t

        // sliding window approach
        // 2 pointers: start and end
        // slide end pointer to the right until substring contains entire target
        // contract window by sliding start pointer to the right
        //
        // why sliding window works:
        // a substring is a candidate for minimum window substring if
        //   1. s[start..end] covers every single character in target string
        //   2. s[start] and s[end] are both in the target string
        // keeping 1. and 2. let's reconsider the algorithm:
        //   we begin by sliding end to the right and stop when s[0..end] covers the entire target
        //   at this point, we know s[end] must be one of the characters in target string
        //   but s[0] may not necessarily be one of the characters in the target string
        //   therefore we need to slide start to the right until s[start..end] no longer covers the target string
        //   the moment we no longer cover the target string, there's no point advancing the start pointer
        //   because 1. is no longer satisfied, in which case we have to start sliding end to the right again
        //
        // will the following ever happen?
        //    [         [ ]           ]
        //    ^                       ^
        //    start                   end
        // where target string = []
        // NO! according to our algorithm, here's how things would have unfolded
        //
        // 1. init
        //    [         [ ]           ]
        //    ^^
        //    start==end
        // 2. covered! slide left to the right
        //    [         [ ]           ]
        //    ^           ^
        //    start       end
        // still covered...
        //    [         [ ]           ]
        //              ^ ^
        //             /   \
        //        start     end
        // 3. no longer covered
        //    [         [ ]           ]
        //               ^^
        //              /  \
        //         start    end

        final Map<Character, Long> counter = t.chars()
                .mapToObj(i -> (char)i)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//                .collect(Collectors.toMap(Function.identity(), c -> 1, Integer::sum));

        int charsToCover = t.length();
        int start = 0;
        int end = 0;
        int substrStart = -1;
        int substrEnd = -1;
        while (end < s.length()) {
            char right = s.charAt(end);
            // if s=aaab, target=aab and current window=[aaa]b
            // we do NOT want to decrement $charsToCover 3 times
            // (the third 'a' should be counted by $counter but not by $charsToCover)
            //
            // if counter.keys() <= 0 then all chars are covered
            if (counter.containsKey(right) && counter.merge(right, -1L, Long::sum) >= 0) {
                --charsToCover;
            }

            while (start <= end && charsToCover == 0) {
                // s[start..end] is a candidate window substring
                // if it is smaller than all other candidates seen so far, then do update
                if ((substrStart == -1 && substrEnd == -1) ||
                        (end - start < substrEnd - substrStart)) {
                    substrStart = start;
                    substrEnd = end;
                }

                char left = s.charAt(start);
                // if s=aaab, target=aab and current window=[aaab]
                // charsToCover==0
                // counter=={'a':-1, 'b':0}
                // a[aab] should not result in charsToCover>0 (counter['a']==0)
                // aa[ab] should     result in charsToCover>0 (counter['a']==1)
                //
                // counter.keys() should be <= 0 === all chars are covered
                // if counter[key] > 0 then not all chars are covered
                if (counter.containsKey(left) && counter.merge(left, 1L, Long::sum) > 0) {
                    ++charsToCover;
                }
                ++start; // contract window
            }
            ++end;
        }
        return (substrStart == -1 && substrEnd == -1)
                ? ""
                : s.substring(substrStart, substrEnd + 1);
    }

    public static void main(String[] args) {
        String minWindow = compute("ADOBECODEBANC", "ABC");
        assertThat(minWindow).isEqualTo("BANC");

        minWindow = compute("a000a0bb", "ab");
        assertThat(minWindow).isEqualTo("a0b");
    }
}
