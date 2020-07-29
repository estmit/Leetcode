package util;

import model.TreeNode;

public class TreeUtil {
    public static <T> boolean isLeafNode(TreeNode<T> node) {
        return node.left == null && node.right == null;
    }
}
