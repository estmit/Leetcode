import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EqualSubsetSumPartition {
    public static List<List<Integer>> partitionApproximate(List<Integer> nums, int k) {
        int subsetSums[] = new int[k];
        // min-heapify subsetSums but call .poll() / .offer() with subsetSums index
        // comparator: (a, b) -> subsetSums[a] - subsetSums[b]
        PriorityQueue<Integer> partitionToAdd = new PriorityQueue<>(Comparator.comparingInt(a -> subsetSums[a]));
        for (int i = 0; i < k; ++i) {
            partitionToAdd.add(i);
        }

        List<List<Integer>> partitions = Stream.generate(ArrayList<Integer>::new).limit(k).collect(Collectors.toList());
        ListIterator<Integer> it = nums.listIterator(nums.size());
        while (it.hasPrevious()) {
            int nextPartition = partitionToAdd.poll();
            int num = it.previous();
            subsetSums[nextPartition] += num;
            partitions.get(nextPartition).add(num);
            partitionToAdd.offer(nextPartition);
        }
        return partitions;
    }

    public static void main(String args[]) {
        List<List<Integer>> partitions = EqualSubsetSumPartition.partitionApproximate(
                Lists.newArrayList(1, 1, 2, 3, 9),
                3
        );
        partitions.forEach(
                partition -> System.out.println(partition.stream().map(String::valueOf).collect(Collectors.joining(",")))
        );
    }
}
