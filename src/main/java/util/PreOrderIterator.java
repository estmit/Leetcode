package util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import lombok.val;
import model.TreeNode;

public class PreOrderIterator<T> implements Iterator<TreeNode<T>> {
    // why use deque instead of stack? performance
    // stack inherits from Vector - allows for element access -> breaks interface
    // cannot overload capacity - more frequent resizing
    // https://dzone.com/articles/why-future-generations-will
    private final Deque<TreeNode<T>> stack = new ArrayDeque<>();

    public PreOrderIterator(TreeNode<T> root) {
        if (root != null) {
            stack.push(root);
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public TreeNode<T> next() {
        if (hasNext()) {
            TreeNode<T> next = stack.pop();
            if (next.right != null) {
                stack.push(next.right);
            }
            if (next.left != null) {
                stack.push(next.left);
            }
            return next;
        } else {
            throw new NoSuchElementException();
        }
    }

    public static void main(String[] args) {
        TreeNode<Integer> root = new TreeNode<>(1);
        val l = new TreeNode<>(2);
        val r = new TreeNode<>(3);
        val lr = new TreeNode<>(4);
        val lrl = new TreeNode<>(5);
        l.right = lr;
        lr.left = lrl;
        root.left = l;
        root.right = r;


        val itr = new PreOrderIterator<Integer>(root);
        while (itr.hasNext()) {
            System.out.println(itr.next().val);
        }
    }
}
