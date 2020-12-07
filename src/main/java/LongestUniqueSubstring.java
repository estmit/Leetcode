import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class LongestUniqueSubstring {
    public int lengthOfLongestSubstring(String s) {
        return lus(s).length();
    }

    public static String lus(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        // remember that a substring is contiguous - if the next character is already in the
        // substring, then chars before the next character (including the next character itself)
        // must be evicted
        // e.g. abcdef c
        //             ^
        // abcdef is the longest unique substring (LUS) so far, c is the next character
        // c already appears in the LUS, and so all characters appearing before c (including
        // c itself) must be evicted
        // the new LUS is defc

        // edge cases
        // a
        // aa
        // aaaaaaaa
        // aaaaaaab
        // baaaaaaa
        // aaaabaaa

        String longestSoFar = s.substring(0, 1);

        int start = 0, end = 1;
        Set<Character> charsEndingHere = new HashSet<>();
        // can also use int[] charsEndingHere = new int[256];
        // but charsEndingHere.empty() will incur a O(256) cost
        charsEndingHere.add(s.charAt(0));

        // loop invariants
        // #1 charsEndingHere should not be empty
        // #2 end must be at least +1 from start
        while (end < s.length() && start < end) {
            // if s[end] not in LUS, end++
            // else
            //    find first occurrence of s[end] in LUS and update
            char next = s.charAt(end);
            if (!charsEndingHere.contains(next)) {
                charsEndingHere.add(next);
                end++;
            } else {
                for (int i = start; i < end; ++i) {
                    charsEndingHere.remove(s.charAt(i));
                    if (s.charAt(i) == next) {
                        start = i + 1;
                        // we've updated start and charsEnding therefore we need to make sure
                        // loop invariant #1 and #2 still holds
                        if (start == end) { // invariant #2
                            end = start + 1;
                            if (charsEndingHere.isEmpty()) { // invariant #1
                                charsEndingHere.add(s.charAt(start));
                            }
                        }
                        end = (start == end) ? start + 1 : end;
                        break;
                    }
                }
            }
            if ((end - start > longestSoFar.length())) {
                longestSoFar = s.substring(start, end);
            }
        }

        return longestSoFar;
    }

    public static void main(String[] args) {
        String input = "a";
        assertThat(lus(input)).isEqualTo("a");

        input = "aa";
        assertThat(lus(input)).isEqualTo("a");

        input = "aaaaaaaa";
        assertThat(lus(input)).isEqualTo("a");

        input = "aaaaaaaab";
        assertThat(lus(input)).isEqualTo("ab");

        input = "baaaaaaaa";
        assertThat(lus(input)).isEqualTo("ba");

        input = "aaaabaaaa";
        assertThat(lus(input)).isEqualTo("ab");

        input = "ab";
        assertThat(lus(input)).isEqualTo("ab");

        input = "abcdefc";
        assertThat(lus(input)).isEqualTo("abcdef");

        input = "abcdefcghi";
        assertThat(lus(input)).isEqualTo("defcghi");

        input = "abcdefghia";
        assertThat(lus(input)).isEqualTo("abcdefghi");

        input = "abcdefghiaj";
        assertThat(lus(input)).isEqualTo("bcdefghiaj");

        input = "abcdeffghijklm";
        assertThat(lus(input)).isEqualTo("fghijklm");
    }
}
