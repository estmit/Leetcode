import org.junit.Assert;

import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {
    static int length(int nums[]) {
        if (nums.length <= 1) {
            return nums.length;
        }

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longest_so_far = 0;
        // iterate over set instead of nums to skip duplicates
        for (int num : set) {
            // optimization: we want to always start the lowest number of a sequence
            //               so that we don't need to sweep bidirectionally
            if (set.contains(num - 1)) continue;

            int longest_run = 0;
            while (set.contains(num)) {
                ++num;
                ++longest_run;
            }

//            while (set.contains(num)) {
//                --num;
//                ++longest_run;
//            }
            longest_so_far = Math.max(longest_run, longest_so_far);
        }
        return longest_so_far;
    }

    public static void main(String args[]) {
        int nums[] = {100, 4, 200, 1, 3, 2};
        Assert.assertEquals(LongestConsecutiveSequence.length(nums), 4);
    }
}
