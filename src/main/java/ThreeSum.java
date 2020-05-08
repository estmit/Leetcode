import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.val;

public class ThreeSum {
    public static List<List<Integer>> triplets(int[] nums) {
        if (nums == null || nums.length < 3) {
            return Collections.emptyList();
        }

        Arrays.sort(nums);
        List<List<Integer>> triplets = new ArrayList<>();
        // treat each element as the target, and use binary search to find two elements
        // that sum up to -(target)
        for (int i = 0; i < nums.length; ++i) {
            // dedup
            while (i - 1 >= 0 && i < nums.length && nums[i] == nums[i - 1]) {
                // i should always be pointing to the first element of a duplicate range
                // 0 0 0 1 1 1 1 1 2 3 3
                // ^     ^         ^ ^
                ++i;
            }

            if (i >= nums.length) {
                break;
            }

            int target = -(nums[i]);
            int left = i + 1;
            int right = nums.length - 1;
            while (left < nums.length && left < right) {
                if (target == nums[left] + nums[right]) {
                    triplets.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left + 1 < nums.length && left < right && nums[left] == nums[left + 1]) {
                        ++left;
                    }
                    ++left;
                    while (right > left && nums[right] == nums[right - 1]) {
                        // if right - 1 = left, while loop will break on the  next iteration
                        //  0 1 1 1 1
                        //  ^ ^ ^ ^ ^
                        //  L R R R R
                        --right;
                    }
                    --right;
                } else if (target > nums[left] + nums[right]) {
                    ++left;
                } else if (target < nums[left] + nums[right]) {
                    --right;
                }
            }
        }
        return triplets;
    }

    public static void main(String[] args) {
        int input[] = new int[]{ 0, 0, 0, 0 };
        val output = ThreeSum.triplets(input);
        assertThat(output).hasSize(1);
        assertThat(output.get(0)).isEqualTo(Lists.newArrayList(0, 0, 0));
    }
}
