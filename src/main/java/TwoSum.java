import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class TwoSum {
    /**
     * Given an array of integers nums and an integer target,
     * return indices of the two numbers such that they add up to target.
     *
     * You may assume that each input would have exactly one solution,
     * and you may not use the same element twice.
     *
     * You can return the answer in any order.
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] compute(int[] nums, int target) {
        if (nums.length == 0) {
            throw new IllegalArgumentException("input array cannot be empty");
        }

        final Map<Integer, Integer> locMap = new HashMap<>();
        for (int i = 0; i < nums.length; ++i) {
            locMap.put(nums[i], i);
        }

        final int[] output = new int[2];
        Arrays.fill(output, -1);

        for (int i = 0; i < nums.length; ++i) {
            int match = locMap.getOrDefault(target - nums[i], -1);
            if (match != -1 && match != i) {
                output[0] = i;
                output[1] = match;
                return output;
            }
        }
        if (output[0] == -1 && output[1] == -1) {
            throw new IllegalArgumentException("no two elements in nums sum to target");
        }
        return output;
    }

    public static int[] compute2(int[] nums, int target) {
        if (nums.length == 0) {
            throw new IllegalArgumentException("input array cannot be empty");
        }

        final Map<Integer, Integer> locMap = new HashMap<>();
        for (int i = 0; i < nums.length; ++i) {
            int match = locMap.getOrDefault(target - nums[i], -1);
            if (match != -1) {
                return new int[] { i, match };
            } else {
                locMap.put(nums[i], i);
            }
        }
        throw new IllegalArgumentException("no two elements in nums sum to target");
    }

    public static void main(String[] args) {
        final int[] nums = new int[] { 1, 2, 3 };
        assertThatIllegalArgumentException().isThrownBy(() -> compute2(nums, 6));

        int[] input = new int[] { 1, 2, 3 };
        int target = 3;
        assertThat(compute2(input, target)).containsExactlyInAnyOrder(0, 1);

        input = new int[] { 1, 2, 3, 4 };
        target = 5;
        int[] output = compute2(input, target);
        assertThat(input[output[0]] + input[output[1]]).isEqualTo(target);

        // cannot re-use same index
        input = new int[] { 2, 2, 2 };
        target = 4;
        assertThat(Arrays.stream(compute2(input, target)).distinct().count()).isEqualTo(2);
    }
}
