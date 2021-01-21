import model.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

public class Subtree {
    // idea:
    //  -use BFS to traverse the tree
    //  -if current node == root of other tree, compare recursively
    public boolean isSubtree(TreeNode s, TreeNode t) {
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(s);
        while (!(q.isEmpty())) {
            TreeNode front = q.remove();
            if (front.val == t.val && equals(front, t)) {
                return true;
            }
            if (front.left != null) {
                q.add(front.left);
            }
            if (front.right != null) {
                q.add(front.right);
            }
        }
        return false;
    }

    private boolean equals(TreeNode t1, TreeNode t2) {
        // base cases
        if (t1 == null && t2 == null) {
            return true;
        }

        if ((t1 == null && t2 != null) ||
                (t1 != null && t2 == null) ||
                (t1.val != t2.val)) {
            return false;
        }

        if (t1.val != t2.val) {
            return false;
        }

        return t1.val == t2.val && equals(t1.left, t2.left) && equals(t1.right, t2.right);
    }
}
