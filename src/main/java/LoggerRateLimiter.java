import java.util.HashMap;
import java.util.Map;

public class LoggerRateLimiter {
    private final Map<String, Integer> msgToTimeMap = new HashMap<>();

    public LoggerRateLimiter() {

    }


    // if foo is logged at t=1, it cannot be logged again at t=11
    //
    // data structure used = hashmap
    //
    // pseudo
    // if message is new / has not appeared in the last 10 seconds:
    //   update map
    //   return true
    // else
    //   return false
    public boolean shouldPrintMessage(int timestamp, String message) {
        // naive solution - O(1) time but O(N) space
        Integer lastSeenTime = msgToTimeMap.get(message);
        if (lastSeenTime == null || timestamp - lastSeenTime >= 10) {
            msgToTimeMap.put(message, timestamp);
            return true;
        } else {
            return false;
        }
    }
}
