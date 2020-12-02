import java.util.HashMap;
import java.util.Map;

public class LongestPalindrome {
    public int longestPalindrome(String s) {
        int counter[] = new int[128];

        for (int i = 0; i < s.length(); ++i) {
            ++counter[s.charAt(i)];
        }

        int longest = 0;
        for (int count : counter) {
            if (count % 2 == 0) {
                longest += count;
            } else {
                longest += (count - 1);
            }
        }

        return (longest == s.length()) ? longest : longest + 1;
    }

    public int longestPalindrome_slow(String s) {
        // length of palindrome is either even (e.g xxyy) or odd (e.g. xyx)

        // either input chars all have even count,
        // or there's a mix of chars with even count and odd count
        //
        // note that odd - 1 = even
        // we can greedily swallow up chars with odd count by counting their counts - 1
        // at the end, if palindrome.length == input.length, then we know there were no chars
        // with odd count (even case)
        // otherwise, we need to pad the palindrome with 1 more character

        // we don't really care about the key, only the value
        // can use int[] since s is only composed of ascii chars
        Map<Character, Integer> charCounter = new HashMap<>();
        for (int i = 0; i < s.length(); ++i) {
            charCounter.merge(s.charAt(i), 1, Integer::sum);
        }

        // 2 separate stream operations can be avoided if we sweep map.values() once using a loop
        int evens = charCounter.values().stream()
                .filter(i -> i % 2 == 0)
                .reduce(0, Integer::sum);

        int odds = charCounter.values().stream()
                .filter(i -> i % 2 != 0)
                .map(i -> i - 1)
                .reduce(0, Integer::sum);

        if (evens != s.length()) { // there are chars with odd count
            return evens + odds + 1;
        } else {
            return evens;
        }
    }

    public static void main(String[] args) {
        String input = "abccccdd";
        LongestPalindrome sol = new LongestPalindrome();
        System.out.println(sol.longestPalindrome(input));
    }
}
