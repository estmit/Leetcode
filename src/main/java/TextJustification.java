import com.google.common.collect.Lists;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TextJustification {
    /**
     * complexity of problem stems from the fact that there are many edge cases to account for;
     * it's a very imperative problem so as long as you think logically it is not actually hard
     *
     * pitfalls:
     *   - not grasping the req that although the last line doesn't need to be center justified,
     *     it still needs to be left justified (padRight with spaces)
     */
    public static List<String> fullJustify(String[] words, int maxWidth) {
        // words guaranteed to not be empty or null
        if (words.length == 1) {
            StringBuilder sb = new StringBuilder(maxWidth);
            sb.append(words[0]);
            return Collections.singletonList(padRight(sb));
        }

        // queue is not necessary; just use an int pointer to traverse the original array
//        Queue<String> input = new ArrayDeque<>(Arrays.asList(words));

        int i = 0;
        List<String> output = new ArrayList<>();
        while (i < words.length) {
            StringBuilder buf = new StringBuilder(maxWidth);
            while (buf.length() < buf.capacity()) {
                // word.length() is guaranteed to be <= maxWidth
                if (i >= words.length) break;
                String word = words[i];
                // "abc def ", next=gh
                // "abc def ", next=ghi => pop last space
                if (buf.length() + word.length() <= buf.capacity()) { // guaranteed to at least be true once
                    buf.append(word);
                    if (buf.length() < buf.capacity()) {
                        buf.append(' ');
                    }
                    ++i;
                } else {
                    if (buf.lastIndexOf(" ") == buf.length() - 1) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    break;
                }
            }
            if (buf.lastIndexOf(" ") == buf.length() - 1) {
                buf.deleteCharAt(buf.length() - 1);
            }
            String justified = (i < words.length)
                    ? padSpaces(buf)
                    : padRight(buf); // last line only needs to be left justified
            output.add(justified);
        }
        return output;
    }

    /**
     * pitfall - indexOf needs to account for the fact that length of space delimiter
     *           grows as you continue padding
     */
    private static String padSpaces(StringBuilder sb) {
        if (sb.length() == sb.capacity()) {
            return sb.toString();
        }

        if (sb.indexOf(" ") == -1) {
            return padRight(sb);
        }

        // while last character is a space
        // find the next place to pad, and then pad // next place shouldnt be from the end
        int start = 0;
        do {
            start = sb.indexOf(" ", start);
            if (start == -1) {
                start = sb.indexOf(" ", 0);
            }
            while (start < sb.length() && sb.charAt(start) == ' ') {
                start++;
            }
            sb.insert(start, ' ');
            ++start;
        } while (sb.length() < sb.capacity());
        return sb.toString();
    }

    private static String padRight(StringBuilder sb) {
        if (sb.length() == sb.capacity()) {
            return sb.toString();
        }

        while (sb.length() < sb.capacity()) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder(10);
        sb.append("abc def g");
        assertThat(padSpaces(sb)).hasSize(10).isEqualTo("abc  def g");

        sb = new StringBuilder(10);
        sb.append("abc def");
        assertThat(padSpaces(sb)).hasSize(10).isEqualTo("abc    def");

        sb = new StringBuilder(10);
        sb.append("abc d e");
        assertThat(padSpaces(sb)).hasSize(10).isEqualTo("abc   d  e");

        sb = new StringBuilder(10);
        sb.append("abcdefghij");
        assertThat(padSpaces(sb)).hasSize(10).isEqualTo("abcdefghij");

        sb = new StringBuilder(10);
        sb.append("abcde fghi");
        assertThat(padSpaces(sb)).hasSize(10).isEqualTo("abcde fghi");

        sb = new StringBuilder(10);
        sb.append("abc");
        assertThat(padSpaces(sb)).hasSize(10).isEqualTo("abc       ");

        String[] words = new String[] {"What","must","be","acknowledgment","shall","be"};
        int maxWidth = 16;

        List<String> output = fullJustify(words, maxWidth);
        assertThat(output).isEqualTo(Lists.newArrayList(
                "What   must   be",
                "acknowledgment  ",
                "shall be        "
        ));
    }
}
