import java.util.Arrays;
import java.util.List;

public class ValidParenthesisString {
    public static boolean isValid(String s) {
        if (s.isEmpty()) return true;

        if (s.startsWith(")")) return false;

        return isValidSubstring(s, 0, 0);
//        int leftParens = 0;
//        for (char ch : s.toCharArray()) {
//            if (ch == '(') leftParens++;
//            if (ch == ')') leftParens--;
//        }
//        return leftParens == 0;
    }

    public static boolean isValidSubstring(String s, int pos, int leftParens) {
        if (leftParens < 0) return false;

        for (int i = pos; i < s.length(); ++i) {
            if (leftParens < 0) return false;
            char ch = s.charAt(i);
            if (ch == '(') leftParens++;
            if (ch == ')') leftParens--;
            if (ch == '*') {
                // treat * as (
                boolean asLeftParen = isValidSubstring(s, i + 1, leftParens + 1);
                // treat * as )
                boolean asRightParen = isValidSubstring(s, i + 1, leftParens - 1);
                // treat * as '' -- skip over
                boolean asEmpty = isValidSubstring(s, i + 1, leftParens);

                return asLeftParen || asRightParen || asEmpty;
            }
        }
        return leftParens == 0;
    }

    public static void main(String args[]) {
        List<String> valids = Arrays.asList(
                "()",
                "(())",
                "()()",
                "(()())",
                "((())())",
                "()(())((())())",
                "(*",
                "*",
                "***",
                "(*)"
        );
        valids.stream().forEach(s ->
            System.out.println(s + " " + ValidParenthesisString.isValid(s))
        );

        List<String> invalids = Arrays.asList(
                ")(",
                "(()",
                "())",
                "(()))",
                "(()(()",
                "*("
        );
        invalids.stream().forEach(s ->
                System.out.println(s + " " + ValidParenthesisString.isValid(s))
        );
    }
}
