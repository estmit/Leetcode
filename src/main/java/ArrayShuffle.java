import java.util.Random;

public class ArrayShuffle {
    public void shuffle(int[] nums) {
        // Fisher-Yates algorithm
        // JDK Collections.shuffle implementation:
        // for (int i=size; i>1; i--)
        //     swap(list, i-1, rnd.nextInt(i));
        int shuffledRange = nums.length - 1;
        final Random rng = new Random(0L);
        while (shuffledRange > 0) {
            int rand = rng.nextInt(shuffledRange + 1);
            int tmp = nums[shuffledRange];
            nums[shuffledRange] = nums[rand];
            nums[rand] = tmp;
            --shuffledRange;
        }
    }
}
