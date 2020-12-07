package collections;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * requirements
 * #1 - implements stack interface - LIFO, push, pop, top in O(1)
 * #2 - getMin() in O(1)
 *
 * idea = use 2 stacks
 * stack1 = the actual stack
 * stack2 = keep track of minimums
 *
 * insertion:
 * if x is to be pushed to the MinStack and x is leq to everything in stack1,
 * then push x to both stack1 and stack2
 * since stack2 already holds all the minimum, we don't need to check if x is
 * leq to everything in stack1, we just need to check x against the most recently
 * minimum element which is recorded in stack2.peek()
 *
 * removal:
 * if stack1.peek() is a min, then we also need to pop stack2
 */
public class MinStack {
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final Deque<Integer> min = new ArrayDeque<>();

    public MinStack() {

    }

    // 1, 1, 2, 3, 4, 0
    public void push(int x) {
        stack.push(x);
        if (min.isEmpty() || x <= min.peek()) {
            min.push(x);
        }
    }

    public void pop() {
        int top = stack.peek();
        stack.pop();
        if (min.peek() == top) {
            min.pop();
        }
    }

    public int top() { // peek
        return stack.peek();
    }

    public int getMin() {
        if (min.isEmpty()) {
            throw new UnsupportedOperationException("cannot call getMin() on empty stack");
        }
        return min.peek();
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        assertThat(minStack.getMin()).isEqualTo(-3); // return -3
        minStack.pop();
        assertThat(minStack.top()).isEqualTo(0);    // return 0
        assertThat(minStack.getMin()).isEqualTo(-2); // return -2
    }
}
