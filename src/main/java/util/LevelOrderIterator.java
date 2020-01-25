package util;

import model.TreeNode;

import java.util.*;

public class LevelOrderIterator {
    private TreeNode curr;
    private Queue<TreeNode> queue = new ArrayDeque<>();
    private int currLvlNodeCount;

    public LevelOrderIterator(TreeNode root) {
        curr = root;
        if (curr != null) {
            queue.offer(curr);
        }
    }

    public boolean hasNextLevel() {
        return !queue.isEmpty();
    }

    public Iterator<TreeNode> nextLevel() {
        currLvlNodeCount = queue.size();
        return new Iterator<TreeNode>() {
            @Override
            public boolean hasNext() {
                return currLvlNodeCount > 0;
            }

            @Override
            public TreeNode next() {
                TreeNode front = queue.poll();
                if (front.left != null) {
                    queue.offer(front.left);
                }
                if (front.right != null) {
                    queue.offer(front.right);
                }
                --currLvlNodeCount;

                return front;
            }
        };
    }

    public List<TreeNode> nextLevelAsList() {
        List<TreeNode> level = new ArrayList();
        int nextLvlNodeCount = 0;
        for (int i = 0; i < currLvlNodeCount; ++i) {
            TreeNode front = queue.poll();
            if (front.left != null) {
                queue.offer(front.left);
                ++nextLvlNodeCount;
            }
            if (front.right != null) {
                queue.offer(front.right);
                ++nextLvlNodeCount;
            }

            level.add(front);
        }
        currLvlNodeCount = nextLvlNodeCount;
        return level;
    }

    public static void main(String args[]) {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        TreeNode ll = new TreeNode(4);
        TreeNode rl = new TreeNode(5);

        root.left = left;
        root.right = right;

        left.left = ll;
        right.left = rl;

        LevelOrderIterator levels = new LevelOrderIterator(root);
        while (levels.hasNextLevel()) {
            Iterator<TreeNode> nodes = levels.nextLevel();
            while (nodes.hasNext()) {
                int val = (int) nodes.next().val;
                System.out.println(val);
            }
        }
    }
}
