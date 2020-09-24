import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReorderLogFiles {
    public String[] reorder(String logs[]) {
        // brute force:
        // two lists, store letter logs in one list and digit logs in another
        // sort letter logs list
        // concat lists
        // logs.length <= 100 - this will probably be good enough?
        List<String> letterLogs = new ArrayList<>();
        List<String> digitLogs = new ArrayList<>();
        for (String log : logs) {
            if (Character.isDigit(log.charAt(log.indexOf(' ') + 1))) {
                digitLogs.add(log);
            } else {
                letterLogs.add(log);
            }
        }

        letterLogs.sort((s1, s2) -> {
            String sub1 = s1.substring(s1.indexOf(' ') + 1);
            String sub2 = s2.substring(s2.indexOf(' ') + 1);
            return sub1.equals(sub2) ? s1.compareTo(s2) : sub1.compareTo(sub2);
        });

        return Stream.concat(letterLogs.stream(), digitLogs.stream())
                .toArray(String[]::new);
    }
}
