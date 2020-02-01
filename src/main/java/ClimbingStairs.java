import sun.jvm.hotspot.utilities.Assert;

public class ClimbingStairs {
    // can we do better? yes - for each i you only need dp[i - 1] and dp[i - 2]
    public static int climb(int n) {
        int dp[] = new int[n + 1];
        dp[0] = 1;
        for (int i = 0; i <= n; ++i) {
            if (dp[i] > 0) {
                if (i + 1 <= n) dp[i + 1] += dp[i];
                if (i + 2 <= n) dp[i + 2] += dp[i];
            }
        }
        return dp[n];
    }

    // constant space
    public static int climbV2(int n) {
        if (n == 1)  return 1;
        else if (n == 2) return 2;

        int dp[] = new int[3];
        dp[0] = 3;
        dp[1] = 2;
        for (int i = 3; i < n; ++i) {
            int idx = i % 3;
            dp[(idx + 1) % 3] += dp[idx];
            dp[(idx + 2) % 3] += dp[idx];
            dp[idx] = 0;
        }
        return dp[n % 3];
    }

    public static void main(String args[]) {
        Assert.that(ClimbingStairs.climbV2(6) == 13, "");
    }
}
