import sun.jvm.hotspot.utilities.Assert;

public class FruitIntoBaskets {
    /*
     *  Given array of ints, identify the longest subarray where there are only 2 distinct ints
     */
    public static int totalFruits(int tree[]) {
        if (tree.length == 0) {
            return 0;
        }

        if (tree.length == 1) {
            return 1;
        }

        int baskets[] = new int[2];
        int left = 0;
        int right = 1;
        baskets[0] = tree[left];

        // loop invariant --> right is same as left
        while (right < tree.length && tree[right] == baskets[0]) {
            ++right;
        }
        // right is pointing at the first element from the left that is NOT equal to tree[0]

        if (right == tree.length) {
            return tree.length;
        }
        baskets[1] = tree[right];
        int len = right -  left + 1;
        ++right;

        // tree.length must be at least 3
        while (right < tree.length && left < right) {
            if (!isElementInArray(tree[right], baskets)) {
                left = right - 1;
                int lastSeen = tree[left];
                // left = right - 1; alone doesn't handle the following edge case:
                //  0  1  2  3  4
                //  5  6  6  6  7
                //           ^  ^
                //           L  R
                //  L should be 1, not simply right - 1 = 3
                while (left > 0 && left - 1 >= 0 && tree[left - 1] == lastSeen) {
                    --left;
                }
                baskets[0] = tree[left];
                baskets[1] = tree[right];
            }
            len = Math.max(len, right - left + 1);
            ++right;
        }
        return len;
    }

    private static boolean isElementInArray(int element, int array[]) {
        for (int i : array) {
            if (i == element) {
                return true;
            }
        }
        return false;
    }

    public static void main(String args[]) {
        Assert.that(3 == FruitIntoBaskets.totalFruits(new int[] {1, 2, 1}), "");
        Assert.that(3 == FruitIntoBaskets.totalFruits(new int[] {0, 1, 2, 2}), "");
        Assert.that(4 == FruitIntoBaskets.totalFruits(new int[] {1, 2, 3, 2, 2}), "");
        Assert.that(5 == FruitIntoBaskets.totalFruits(new int[] {3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4}), "");
        Assert.that(5 == FruitIntoBaskets.totalFruits(new int[] {0, 1, 6, 6, 4, 4, 6}), "");
    }
}
