public class ProductOfArrayExceptSelf {
    // we wouldn't reuse the input array like this in production...
    // but this does satisfy Leetcode's "constant space" requirement
    // (answer array allocation doesn't count)
    public static int[] productExceptSelf(int[] nums) {
        final int[] prefixes = new int[nums.length];
        prefixes[0] = nums[0];
        for (int i = 1; i < nums.length; ++i) {
            prefixes[i] = nums[i] * prefixes[i - 1];
        }

        // instead of allocating a new array to store suffixes,
        // just re-use the input array!
        for (int i = nums.length - 2; i >= 0; --i) {
            nums[i] = nums[i] * nums[i + 1];
        }

        // input / suffixes array will also be used as the answer array!
        // note that answer[i] = prefix[i - 1] * suffix[i + 1];
        // that means we need to fill in the answer array from left to right
        // in order to not overwrite suffixes
        for (int i = 0; i < nums.length; ++i) {
            int prefix = i == 0 ? 1 : prefixes[i - 1];
            int suffix = i == nums.length - 1 ? 1 : nums[i + 1];
            nums[i] = prefix * suffix;
        }
        return nums;
    }

    // look ma no division!
    public static int[] linear_time_space(int[] nums) {
        final int[] prefixes = new int[nums.length];
        prefixes[0] = nums[0];
        for (int i = 1; i < nums.length; ++i) {
            prefixes[i] = nums[i] * prefixes[i - 1];
        }

        final int[] suffixes = new int[nums.length];
        suffixes[nums.length - 1] = nums[nums.length - 1];
        for (int i = nums.length - 2; i >= 0; --i) {
            suffixes[i] = nums[i] * suffixes[i + 1];
        }

        final int[] answer = new int[nums.length];
        for (int i = 0; i < nums.length; ++i) {
            int prefix = i == 0 ? 1 : prefixes[i - 1];
            int suffix = i == nums.length - 1 ? 1 : suffixes[i + 1];
            answer[i] = prefix * suffix;
        }
        return answer;
    }

    // ugly brute-force:
    // scan for zeroes - if more than 1, fill array with zeroes
    //                   if exactly 1, multiply everything except for zero
    // else (no zeroes) - use rolling prefix/suffix
    public static int[] bruteForce(int[] nums) {
        int product = 1;
        int zeroIdx = -1;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] == 0) {
                if (zeroIdx == -1) {
                    zeroIdx = i;
                    continue;
                } else { // more than 1 zero detected
                    return new int[nums.length];
                }
            }
            product *= nums[i];
        }

        int[] answer = new int[nums.length];
        if (zeroIdx != -1) {
            answer[zeroIdx] = product;
            return answer;
        }

        int prefix = 1;
        int suffix = 1;
        for (int i = 1; i < nums.length; ++i) {
            suffix *= nums[i];
        }

        answer[0] = suffix;
        prefix *= nums[0];

        for (int i = 1; i < nums.length; ++i) {
            suffix /= nums[i];
            answer[i] = suffix * prefix;
            prefix *= nums[i];
        }

        return answer;
    }

    public static void main(String[] args) {
        int[] input = { 1, 2, 3, 4 };
        int[] output = productExceptSelf(input);
    }
}
