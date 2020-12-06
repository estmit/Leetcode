import model.TreeNode;

public class LowestCommonAncestor {
    public TreeNode<Integer> lca(TreeNode<Integer> root, TreeNode<Integer> p, TreeNode<Integer> q) {
        return lcaHelper(root, p, q).getLca();
    }

    protected static class NodeStatus<T>{
        private int numOfNodesFound;
        private TreeNode<T> lca;

        public NodeStatus(int numOfNodesFound, TreeNode<T> lca) {
            this.numOfNodesFound = numOfNodesFound;
            this.lca = lca;
        }

        public int getNumOfNodesFound() {
            return numOfNodesFound;
        }

        public TreeNode<T> getLca() {
            return lca;
        }
    }

    // time O(N)
    // space O(Height)
    public <T> NodeStatus<T> lcaHelper(TreeNode<T> root, TreeNode<T> p, TreeNode<T> q) {
        if (root == null) {
            return new NodeStatus(0, null);
        }

        NodeStatus leftSubtree = lcaHelper(root.left, p, q);
        if (leftSubtree.getNumOfNodesFound() == 2) {
            return new NodeStatus(2, root.left);
        }

        NodeStatus rightSubtree = lcaHelper(root.right, p, q);
        if (rightSubtree.getNumOfNodesFound() == 2) {
            return new NodeStatus(2, root.right);
        }

        int numOfNodesFound = leftSubtree.getNumOfNodesFound()
                + rightSubtree.getNumOfNodesFound()
                + (root.val == p.val ? 1 : 0)
                + (root.val == q.val ? 1 : 0);
        return new NodeStatus(numOfNodesFound, numOfNodesFound == 2 ? root : null);
    }

    // brute-force -> recursive post-order traversal
    public TreeNode<Integer> naive(TreeNode<Integer> root, TreeNode<Integer> p, TreeNode<Integer> q) {
        if (root == null) {
            return null;
        }

        if (root.val == p.val || root.val == q.val) {
            return root;
        }

        TreeNode<Integer> leftSubtree = naive(root.left, p, q);
        // if leftSubtree is already the LCA, LCA#naive would still explore the rightSubtree
        // we need to return the number of nodes found so that we can return early
        TreeNode<Integer> rightSubtree = naive(root.right, p, q);

        if (leftSubtree != null && rightSubtree != null) {
            return root;
        }

        if (leftSubtree != null) {
            return leftSubtree;
        } else {
            return rightSubtree;
        }
    }
}
