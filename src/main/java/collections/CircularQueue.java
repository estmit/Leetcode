package collections;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * pretty straightforward problem
 *
 * time complexity: O(1) for all ops
 *
 * discussion
 * -how to make this threadsafe? lock free?
 * -array vs linked list approach
 */
public class CircularQueue<T> {
    private Object elems[];
    private int length;
    private int head;
    private int tail;

    public CircularQueue(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be greater than 0");
        }
        elems = new Object[size];
        length = size;
        head = -1;
        tail = -1;
    }

    public boolean enqueue(T item) {
        if (isEmpty()) { // init / empty state
            head = 0;
            tail = head;
            elems[head] = item;
        } else {
            int next = (tail + 1) % length;
            if (next != head) {
                elems[next] = item;
                tail = next;
            } else { // queue is full!
                return false;
            }
        }
        return true;
    }

    public boolean dequeue() {
        if (isEmpty()) {
            return false;
        }

        if (head == tail) { // size = 1
            reset();
            return true;
        }

        int next = (head + 1) % length;
        head = next;
        return true;
    }

    public T front() {
        return isEmpty() ? null : (T) elems[head];
    }

    public T back() {
        return isEmpty() ? null : (T) elems[tail];
    }

    public boolean isEmpty() {
        return head == -1 && tail == -1;
    }

    public boolean isFull() {
        return !isEmpty() && (tail + 1) % length == head;
    }

    protected void reset() {
        head = -1;
        tail = -1;
    }

    public static void main(String[] args) {
        // [,,]
        CircularQueue<Integer> cq = new CircularQueue<>(3);
        assertThat(cq.isEmpty()).isTrue();
        assertThat(cq.isFull()).isFalse();
        assertThat(cq.front()).isEqualTo(null);
        assertThat(cq.back()).isEqualTo(null);

        // [1,,]
        cq.enqueue(1);
        assertThat(cq.isEmpty()).isFalse();
        assertThat(cq.isFull()).isFalse();
        assertThat(cq.front()).isEqualTo(1);
        assertThat(cq.back()).isEqualTo(1);

        // [1,2,]
        cq.enqueue(2);
        assertThat(cq.isEmpty()).isFalse();
        assertThat(cq.isFull()).isFalse();
        assertThat(cq.front()).isEqualTo(1);
        assertThat(cq.back()).isEqualTo(2);

        // [1,2,3]
        cq.enqueue(3);
        assertThat(cq.isEmpty()).isFalse();
        assertThat(cq.isFull()).isTrue();
        assertThat(cq.front()).isEqualTo(1);
        assertThat(cq.back()).isEqualTo(3);

        // [1,2,3]
        boolean status = cq.enqueue(4);
        assertThat(status).isFalse();
        assertThat(cq.isEmpty()).isFalse();
        assertThat(cq.isFull()).isTrue();
        assertThat(cq.front()).isEqualTo(1);
        assertThat(cq.back()).isEqualTo(3);

        // [,2,3]
        status = cq.dequeue();
        assertThat(status).isTrue();
        assertThat(cq.isEmpty()).isFalse();
        assertThat(cq.isFull()).isFalse();
        assertThat(cq.front()).isEqualTo(2);
        assertThat(cq.back()).isEqualTo(3);

        // [4,2,3]
        status = cq.enqueue(4);
        assertThat(status).isTrue();
        assertThat(cq.isEmpty()).isFalse();
        assertThat(cq.isFull()).isTrue();
        assertThat(cq.front()).isEqualTo(2);
        assertThat(cq.back()).isEqualTo(4);

        // [4,,3]
        status = cq.dequeue();
        assertThat(status).isTrue();
        assertThat(cq.isEmpty()).isFalse();
        assertThat(cq.isFull()).isFalse();
        assertThat(cq.front()).isEqualTo(3);
        assertThat(cq.back()).isEqualTo(4);

        // [4,,]
        status = cq.dequeue();
        assertThat(status).isTrue();
        assertThat(cq.isEmpty()).isFalse();
        assertThat(cq.isFull()).isFalse();
        assertThat(cq.front()).isEqualTo(4);
        assertThat(cq.back()).isEqualTo(4);

        // [,,]
        status = cq.dequeue();
        assertThat(status).isTrue();
        assertThat(cq.isEmpty()).isTrue();
        assertThat(cq.isFull()).isFalse();
        assertThat(cq.front()).isEqualTo(null);
        assertThat(cq.back()).isEqualTo(null);

        cq = null;
        cq = new CircularQueue<>(1);
        assertThat(cq.isEmpty()).isTrue();
        assertThat(cq.isFull()).isFalse();
        assertThat(cq.front()).isEqualTo(null);
        assertThat(cq.back()).isEqualTo(null);

        cq.enqueue(1);
        assertThat(cq.isEmpty()).isFalse();
        assertThat(cq.isFull()).isTrue();
        assertThat(cq.front()).isEqualTo(1);
        assertThat(cq.back()).isEqualTo(1);

        status = cq.enqueue(2);
        assertThat(status).isFalse();
        assertThat(cq.isEmpty()).isFalse();
        assertThat(cq.isFull()).isTrue();
        assertThat(cq.front()).isEqualTo(1);
        assertThat(cq.back()).isEqualTo(1);

        status = cq.dequeue();
        assertThat(status).isTrue();
        assertThat(cq.isEmpty()).isTrue();
        assertThat(cq.isFull()).isFalse();
        assertThat(cq.front()).isEqualTo(null);
        assertThat(cq.back()).isEqualTo(null);
    }
}
