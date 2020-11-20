import model.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *  strategy: serialize tree as a complete tree a la binary heap
 *
 *  serialize: level order traversal
 *
 *  deserialize:
 *
 *         0
 *        _|_
 *       /   \
 *      1    2
 *     / \  / \
 * NULL  4 5  NULL
 *
 *  |0|1|2|NULL|4|5|NULL|
 *  left child index = 2n + 1
 *  rightchild index = 2n + 2
 *
 *  this strategy is correct but not space-efficient especially in cases where there are a lot of null leaf nodes
 *  e.g. a tree that only branches left/right
 *
 *  if a leaf node is null, its children should not be present in the serialized form
 *  e.g.
 *        0
 *       /
 *      1
 *     /
 *    2
 *   /
 *  3
 *  is stored as "0,1,,2,,,3" when it could have been stored as "0,1,,2,,3"
 */
public class DeSerializeBinaryTree {
    private static final char DELIMITER = ',';

    public String serialize(TreeNode<Integer> root) {
        /*
        if (root == null) {
            return "";
        }
        List<TreeNode> levelOrder = new ArrayList<>();
        levelOrder.add(root);
        int reader = 0;
        while (reader < levelOrder.size()) {
            if (reader > 0 && isPowerOfTwo(reader + 1)) {
                // check if remaining array is all nulls
                boolean allNull = true;
                int runner = reader;
                while (runner < levelOrder.size()) {
                    if (levelOrder.get(runner) != null) {
                        allNull = false;
                        break;
                    }
                    runner++;
                }
                if (allNull == true) {
                    break;
                }
            }

            TreeNode node = levelOrder.get(reader);
            if (node != null) {
                levelOrder.add(node.left);
                levelOrder.add(node.right);
            } else {
                levelOrder.add(null);
                levelOrder.add(null);
            }
            reader++;
        }

        int limit = levelOrder.size() - 1;
        while (levelOrder.get(limit) == null) {
            limit--;
        }
        return levelOrder.stream()
                .map(i -> {
                    if (i == null) {
                        return "";
                    } else {
                        return i.val.toString();
                    }
                })
                .limit(limit + 1)
                .collect(Collectors.joining(","));
         */

        if (root == null) {
            return "";
        }

        Deque<TreeNode> queue = new LinkedList<>(); // ArrayDeque does not permit nulls
        StringBuilder ser = new StringBuilder();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode front = queue.remove();
            if (front == null) {
                ser.append(DELIMITER);
            } else {
                ser.append(front.val).append(DELIMITER);
                queue.add(front.left);
                queue.add(front.right);
            }
        }
        int trailingCommas = ser.length() - 1;
        while (ser.charAt(trailingCommas) == DELIMITER) {
            trailingCommas--;
        }
        return ser.substring(0, trailingCommas + 1);
    }

    /**
     *        0
     *       / \
     *      1  NULL
     *     / \
     *    2  NULL
     *   / \
     *  3 NULL
     *
     *  [0,1,2,   3,4,   5,   6,   7
     *    ____      _______________
     *   / \  \    /               \
     *  [0,1,NULL,2,NULL,NULL,NULL,3]
     *      \____/__/
     *
     *       ______________________
     *      /     \/\              \   <= i=5,6 are not stored b/c they are the children of i=2 (which was null)
     *  [0,1,NULL,2,NULL,          3]
     *   \/__/
     *
     *  by "compacting" the NULLs, we only need to iterate pairwise instead of calculating 2i + 1 for left child
     *  and 2i + 2 for right child
     *
     *  (1,2) -> (3,4) -> (5,6) -> etc
     */
    public TreeNode<Integer> deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        List<TreeNode<Integer>> nodes = Arrays.stream(data.split(String.valueOf(DELIMITER)))
                .map(str -> {
                    if (str.isEmpty()) {
                        return null;
                    } else {
                        return new TreeNode<>(Integer.valueOf(str));
                    }
                })
                .collect(Collectors.toList());
//        int reader = 0;
//        while (reader < nodes.size()) {
//            while (reader < nodes.size() && nodes.get(reader) == null) {
//                reader++;
//            }
//            if (reader >= nodes.size()) {
//                break;
//            }
//            int left = 2 * reader + 1;
//            if (left < nodes.size() && nodes.get(left) != null) {
//                nodes.get(reader).left = nodes.get(left);
//            }
//            int right = 2 * reader + 2;
//            if (right < nodes.size() && nodes.get(right) != null) {
//                nodes.get(reader).right = nodes.get(right);
//            }
//            reader++;
//        }

        // [0,1,2,   3,4,   5
        // [0,1,NULL,2,NULL,3]

        // 0.left = 1
        // 0.right= 2
        // 1.left=3
        // 1.right=4
        // 2.left=5


        // TODO too difficult to understand...
        int curr = 0;
        int child = curr + 1;
        while (curr <= child && child < nodes.size()) {
            // NULL nodes don't have children, so skip them
            while (curr < nodes.size() && nodes.get(curr) == null) {
                curr++;
            }

            // since we've mutated curr we have to re-check while condition
            if (curr > child) {
                break;
            }

            int left = child;
            int right = child + 1;
            if (left < nodes.size()) {
                nodes.get(curr).left = nodes.get(left);
            }
            if (right < nodes.size()) {
                nodes.get(curr).right = nodes.get(right);
            }
            child+=2;
            curr++;
        }
        return nodes.get(0);
    }

    protected boolean isPowerOfTwo(int i) {
        return (i & i - 1) == 0;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode<>(1);
        root.left = new TreeNode<>(3);
        root.right = null;
        root.left.left = null;
        root.left.right = new TreeNode<>(4);

        DeSerializeBinaryTree solution = new DeSerializeBinaryTree();
        String ser = solution.serialize(root);
        System.out.println(ser);

        TreeNode deser = solution.deserialize("1,2,3,,,4,5");
        assertThat(deser).isNotNull();
    }
}
