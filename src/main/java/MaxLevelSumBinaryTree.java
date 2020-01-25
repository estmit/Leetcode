import model.TreeNode;
import util.LevelOrderIterator;

import java.util.Iterator;

public class MaxLevelSumBinaryTree {
    public static int maxLevelSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int maxSumSoFar = (int)root.val;
        int maxLevelSoFar = 1;

        LevelOrderIterator levels = new LevelOrderIterator(root);
        for (int level = 1; levels.hasNextLevel(); ++level) {
            int sum = 0;
            Iterator<TreeNode> nodes = levels.nextLevel();
            while (nodes.hasNext()) {
                sum += (int)nodes.next().val;
            }

            if (sum > maxSumSoFar) {
                maxSumSoFar = sum;
                maxLevelSoFar = level;
            }
        }
        return maxLevelSoFar;
    }
}
