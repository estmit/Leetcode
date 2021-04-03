import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class LongestSubstringKDistinct {
    /**
     * Given a string s and an integer k, return the longest substring of s
     * that contains at most k distinct characters.
     *
     * Input: s = "eceba", k = 2
     * Output: 3
     * Explanation: The substring is "ece" with length 3.
     *
     * Input: s = "aa", k = 1
     * Output: 2
     * Explanation: The substring is "aa" with length 2.
     *
     * brute-force
     * 1. generate all substrings O(N^2)
     * 2. get distinct integer count for each substring O(N)
     * 3. run quick-select to get top k substring with k distinct chars
     *
     * sliding window
     * 1. 2 pointers, start and end
     * 2. slide end to the right as long as window contains <= k distinct chars
     * 3. if window has > k distinct chars, move start forward until window contains <= k distinct chars
     *
     * @param s     1 <= s.length <= 5 * 104
     * @param k     0 <= k <= 50
     * @return
     */
    public static String compute(String s, int k) {
        Objects.requireNonNull(s);
        if (s.length() == 0 || k == 0) {
            return "";
        }

        // ideally we can use a multiset
        final Map<Character, Integer> counter = new HashMap<>();
        int substrStart = -1;
        int substrEnd = -1;
        for (int start = 0, end = 0; end < s.length(); ++end) {
            char right = s.charAt(end);
            // we don't need to check counter.size() before incrementing counter[right]
            // if counter.size() > k after the merge, the while loop in L60 will execute
            // and bring counter.size() back to k
            counter.merge(right, 1, Integer::sum);

            // this if block is redundant - if counter.size() == k, the if block in
            // L68 will do the check
//            if (counter.size() == k) {
//                if ((substrStart == -1 && substrEnd == -1)
//                        || (end - start > substrEnd - substrStart)) {
//                    substrStart = start;
//                    substrEnd = end;
//                }
//            }

            // since we are only removing at most one character
            // counter.size() must be equal to k when the loop terminates
            while (start <= end && counter.size() > k) {
                char left = s.charAt(start);
                counter.merge(left, -1, Integer::sum);
                if (counter.get(left) <= 0) {
                    counter.remove(left);
                }
                ++start;
            }

            if ((counter.size() == k) &&
                    ((substrStart == -1 && substrEnd == -1) || (end - start > substrEnd - substrStart))) {
                substrStart = start;
                substrEnd = end;
            }

        }
        // (substrStart == -1 && substrEnd == -1) will never be true
        return (substrStart == -1 && substrEnd == -1)
                ? s
                : s.substring(substrStart, substrEnd + 1);
    }

    public static void main(String[] args) {
        String output = compute("eceba", 2);
        assertThat(output).hasSize(3);

        output = compute("aa", 1);
        assertThat(output).hasSize(2);

        output = compute("aa", 2);
        assertThat(output).hasSize(2);

        output = compute("ab", 1);
        assertThat(output).hasSize(1);

        output = compute("abee", 1);
        assertThat(output).hasSize(2);
    }
}
