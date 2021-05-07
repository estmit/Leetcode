import java.util.ArrayList;
import java.util.List;

public class CloneGraph {
    private static final int NODE_COUNT = 100;

    private class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, List<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        Node[] cloned = new Node[NODE_COUNT + 1];
        Node clone = cloneNode(node, cloned);
        return clone;
    }

    private Node cloneNode(Node node, Node[] cloned) {
        if (cloned[node.val] != null) {
            return cloned[node.val];
        }

        Node clone = new Node(node.val);
        cloned[clone.val] = clone;
        List<Node> clonedNeighbors = new ArrayList<>();
        for (Node neighbor : node.neighbors) {
            Node clonedNeighbor;
            if (cloned[neighbor.val] != null) {
                clonedNeighbor = cloned[neighbor.val];
            } else {
                clonedNeighbor = cloneNode(neighbor, cloned);
                cloned[clonedNeighbor.val] = clonedNeighbor;
            }
            clonedNeighbors.add(clonedNeighbor);

        }
        clone.neighbors = clonedNeighbors;
        return clone;
    }
}
