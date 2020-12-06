import com.google.common.collect.ImmutableMap;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class ValidParentheses {
    private static final Map<Character, Character> closeToOpenBrac = ImmutableMap.of( // uses guava
            ')', '(',
            '}', '{',
            ']', '['
    );

    public boolean isValid(String s) {
        if (s.isEmpty()) {
            return true;
        }

        Deque<Character> openBracStack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); ++i) {
            char brac = s.charAt(i);
            if (closeToOpenBrac.containsKey(brac)) {
                if (openBracStack.isEmpty()) {
                    return false;
                }
                char top = openBracStack.pop();
                if (closeToOpenBrac.get(brac) != top) {
                    return false;
                }
            } else {
                openBracStack.push(brac);
            }
        }
        return openBracStack.isEmpty();
    }
}
