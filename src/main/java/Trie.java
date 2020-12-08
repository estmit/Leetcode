public class Trie {
    private static final int ALPHABET_SIZE = 26;

    private static class Node {
        private boolean isTerminal = false;
        private final Node[] children = new Node[ALPHABET_SIZE];

        public boolean hasChild(char letter) {
            return children['z' - letter] != null;
        }

        public Node getChild(char letter) {
            return children['z' - letter];
        }

        public void setChild(char letter, Node child) {
            children['z' - letter] = child;
        }

        public boolean isTerminal() {
            return isTerminal;
        }

        public void setTerminal(boolean terminal) {
            isTerminal = terminal;
        }
    }

    private final Node root = new Node();

    /**
     * Initialize your data structure here.
     */
    public Trie() {

    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        Node curr = root;
        for (char c : word.toCharArray()) {
            if (!curr.hasChild(c)) {
                curr.setChild(c, new Node());
            }
            curr = curr.getChild(c);
        }
        curr.setTerminal(true);
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        return search(word, false);
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        return search(prefix, true);
    }

    private boolean search(String word, boolean prefix) {
        Node curr = root;
        for (char c : word.toCharArray()) {
            if (!curr.hasChild(c)) {
                return false;
            }
            curr = curr.getChild(c);
        }

        if (!prefix) {
            return curr.isTerminal();
        }
        return true;
    }
}
