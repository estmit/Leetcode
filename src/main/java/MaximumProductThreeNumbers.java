import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MaximumProductThreeNumbers {
    public int maximumProduct(int[] nums) {
        if (nums.length == 3) {
            return nums[0] * nums[1] * nums[2];
        }

        Arrays.sort(nums);
        int len = nums.length;
        if (nums[0] >= 0) {
            // strategy: take the right most 3 integers and multiply them
            // [..., +ve, +ve, +ve] // good
            return nums[len - 1] * nums[len - 2] * nums[len - 3];
        } else {
            // edge case [-2,-2,0,1,1]

            //  0,   1,   2,..., n - 2, n - 1, n
            // [-ve, -ve, -ve, ..., +ve, +ve, +ve]
            // what if A[0] * A[1] * A[n] > A[n] * A[n - 1] * A[n - 2]?

            // there has to be at least 2 negative numbers for this scenario to get triggered
            // [-2, 0, 1, 1] => 1*1*0
            // [-2,-2,0,1] => max(1*0*-2, -2*-2*1) = 4

            return Math.max(
                    nums[0] * nums[1] * nums[len - 1],
                    nums[len - 1] * nums[len - 2] * nums[len - 3]
            );
        }
    }

    public static void main(String[] args) {
        MaximumProductThreeNumbers sol = new MaximumProductThreeNumbers();

        assertThat(sol.maximumProduct(new int[]{-5, -4, -3, 0, 9, 10})).isEqualTo(200);
        assertThat(sol.maximumProduct(new int[]{-5, -4, -3, -2, -1})).isEqualTo(-6);
        assertThat(sol.maximumProduct(new int[]{-5, -4, -3, 0, 0, 1})).isEqualTo(20);
        assertThat(sol.maximumProduct(new int[]{-5, -4, -3, 0, 0, 0})).isEqualTo(0);
    }
}
