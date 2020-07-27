import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

class PeekingIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private boolean hasPeeked = false;
    private Integer peeked = null;

    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        this.iterator = iterator;
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        if (!this.hasPeeked) {
            this.hasPeeked = true;
            this.peeked = this.iterator.next();
        }
        return peeked;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        if (!this.hasPeeked) {
            return this.iterator.next();
        } else {
            this.hasPeeked = false;
            return this.peeked;
        }
    }

    @Override
    public boolean hasNext() {
        if (this.hasPeeked && this.peeked != null) {
            return true;
        } else {
            return this.iterator.hasNext();
        }
    }

    public static void main(String[] args) {
        List<Integer> input = Lists.newArrayList(1, 2, 3, 4);
        PeekingIterator iterator = new PeekingIterator(input.iterator());
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.peek()).isEqualTo(1);
        assertThat(iterator.peek()).isEqualTo(1);
        assertThat(iterator.next()).isEqualTo(1);

        assertThat(iterator.next()).isEqualTo(2);

        assertThat(iterator.peek()).isEqualTo(3);
        assertThat(iterator.peek()).isEqualTo(3);
        assertThat(iterator.next()).isEqualTo(3);

        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.peek()).isEqualTo(4);
        assertThat(iterator.next()).isEqualTo(4);
        assertThat(iterator.hasNext()).isFalse();
    }
}