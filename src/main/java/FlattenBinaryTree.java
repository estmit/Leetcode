import static util.TreeUtil.isLeafNode;

import lombok.var;
import model.TreeNode;

public class FlattenBinaryTree {
    public <T> void flatten(TreeNode<T> root) {
        // base cases
        if (root == null || isLeafNode(root)) {
            return;
        }

        // x
        //  \
        //   y
        if (root.left == null && root.right != null && isLeafNode(root.right)) {
            return;
        }

        //   x      x
        //  /   =>   \
        // y          y
        if (root.left != null && isLeafNode(root.left) && root.right == null) {
            root.right = root.left;
            root.left = null;
            return;
        }

        flatten(root.left);
        flatten(root.right);
        rotateRight(root);
    }

    private static <T> void rotateRight(TreeNode<T> node) {
        if (node.left == null) { // there's nothing to rotate
            return;
        }

        TreeNode<T> rst = node.right;
        node.right = node.left;
        node.left = null;

        TreeNode<T> cur = node.right;
        while (cur.right != null) {
            cur = cur.right;
        }
        cur.right = rst;
    }

    public static void main(String[] args) {
        var root = new TreeNode<>(1);
        root.left = new TreeNode<>(2);
        root.left.left = new TreeNode<>(3);
        root.left.left.left = new TreeNode<>(4);
        root.left.left.right = new TreeNode<>(5);
        root.left.right = new TreeNode<>(6);
        root.left.right.left = new TreeNode<>(7);
        root.left.right.right = new TreeNode<>(8);
        root.right = new TreeNode<>(9);
        root.right.right = new TreeNode<>(10);

        FlattenBinaryTree sol = new FlattenBinaryTree();
        sol.flatten(root);

        var curr = root;
        while (curr != null) {
            System.out.println(curr.val);
            curr = curr.right;
        }

        root = null;
        root = new TreeNode<>(1);
        root.left = new TreeNode<>(2);
        root.left.left = new TreeNode<>(3);
        root.left.left.left = new TreeNode<>(4);
        root.left.left.left.left = new TreeNode<>(5);
        root.left.left.left.left.left = new TreeNode<>(6);

        sol.flatten(root);

        curr = root;
        while (curr != null) {
            System.out.println(curr.val);
            curr = curr.right;
        }

        root = null;
        root = new TreeNode<>(1);
        root.right = new TreeNode<>(2);
        root.right.left = new TreeNode<>(3);

        sol.flatten(root);

        curr = root;
        while (curr != null) {
            System.out.println(curr.val);
            curr = curr.right;
        }
    }
}
