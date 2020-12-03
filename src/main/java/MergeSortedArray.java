public class MergeSortedArray {
    public static int[] merge(int[] arrLeft, int[] arrRight) {
        if (arrLeft == null || arrLeft.length == 0) {
            return arrRight;
        } else if (arrRight == null || arrRight.length == 0) {
            return arrLeft;
        }

        int merged[] = new int[arrLeft.length + arrRight.length];
        int left = 0;
        int right = 0;
        int i = 0;
        while (left < arrLeft.length && right < arrRight.length) {
            if (arrLeft[left] <= arrRight[right]) {
                merged[i] = arrLeft[left];
                ++left;
            } else {
                merged[i] = arrRight[right];
                ++right;
            }
            ++i;
        }
        while (left < arrLeft.length) {
            merged[i] = arrLeft[left];
            ++i;
            ++left;
        }

        while (right < arrRight.length) {
            merged[i] = arrRight[right];
            ++i;
            ++right;
        }

        return merged;
    }

    public static void main(String[] args) {
        int a[] = new int[]{2, 5, 7, 8};
        int b[] = new int[]{2, 4, 7, 12, 32};

        int merged[] = merge(a, b);
    }
}
