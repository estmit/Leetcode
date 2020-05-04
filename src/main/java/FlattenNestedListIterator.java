import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Lists;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FlattenNestedListIterator {
    public static class NestedInteger {
        private Integer integer = null;
        private List<NestedInteger> list = null;

        public NestedInteger() {}

        public NestedInteger(Integer integer) {
            if (this.list != null) {
                throw new RuntimeException("NestedInteger is already a nested list");
            }
            this.integer = integer;
        }

        public NestedInteger(List<NestedInteger> list) {
            if (this.integer != null) {
                throw new RuntimeException("NestedInteger is already an integer");
            }
            this.list = list;
        }

        public NestedInteger list(NestedInteger integer) {
            if (this.integer != null) {
                throw new RuntimeException("NestedInteger is already a nested list");
            }
            if (this.list == null) {
                this.list = new ArrayList<>();
            }
            this.list.add(integer);
            return this;
        }

        public boolean isInteger() {
            return integer != null;
        }

        public Integer getInteger() {
            return integer;
        }

        public List<NestedInteger> getList() {
            return list;
        }
    }

    /**
     *  why this is a bad solution:
     *   (1) there's a bug in the program (input: [[]])
     *   (2) a simpler solution exists with less complexity
     *
     */
    public static class NestedIteratorV1 implements Iterator<Integer> {
        private final Iterator<NestedInteger> list;
        private final Deque<ListIterator<NestedInteger>> toVisit = new ArrayDeque<>();

        public NestedIteratorV1(List<NestedInteger> nestedList) {
            this.list = nestedList.iterator();
        }

        @Override
        public Integer next() {
            if (list.hasNext() && toVisit.isEmpty()) {
                NestedInteger curr = list.next();
                if (curr.isInteger()) {
                    return curr.getInteger();
                } else {
                    toVisit.addFirst(curr.getList().listIterator());
                }
            }

            while (!toVisit.isEmpty()) {
                ListIterator<NestedInteger> curr = toVisit.removeFirst();
                if (curr.hasNext()) {
                    NestedInteger i = curr.next();
                    if (i.isInteger()) {
                        if (curr.hasNext()) toVisit.addFirst(curr);
                        return i.getInteger();
                    } else {
                        toVisit.addFirst(i.getList().listIterator());
                    }
                }
            }

            return null;
        }

        @Override
        public boolean hasNext() { // FIXME: this fails if the input is [[]]
            return list.hasNext() || !toVisit.isEmpty();
        }
    }

    public static class NestedIteratorV2 implements Iterator<Integer> {
        private final Deque<NestedInteger> stack = new ArrayDeque<>();

        public NestedIteratorV2(List<NestedInteger> nestedList) {
            pushToStack(nestedList);
        }

        public void pushToStack(List<NestedInteger> list) {
            for (int i = list.size() - 1; i >= 0; --i) {
                stack.addFirst(list.get(i));
            }
        }

        @Override
        public boolean hasNext() {
            while (!stack.isEmpty() && !stack.getFirst().isInteger()) {
                List<NestedInteger> nested = stack.removeFirst().getList();
                pushToStack(nested);
            }
            // because the while loop only stops running if top of stack is an integer,
            // checking for !stack.isEmpty() is sufficient
            return !stack.isEmpty();
        }

        @Override
        public Integer next() {
            return stack.removeFirst().getInteger();
        }
    }

    public static void main(String[] args) {
        NestedInteger i = new NestedInteger(1);
        NestedInteger ii = new NestedInteger(2);
        NestedInteger li = new NestedInteger().list(i).list(ii);
        NestedInteger iii = new NestedInteger(3);
        NestedInteger iv = new NestedInteger(4);
        NestedInteger v = new NestedInteger(5);
        NestedInteger lii = new NestedInteger().list(iv).list(v);
        List<NestedInteger> input = Lists.newArrayList(li, iii, lii); // [[1, 2], 3, [4, 5]]

        NestedIteratorV1 iterator = new NestedIteratorV1(input);
        List<Integer> output = Lists.newArrayList();
        while (iterator.hasNext()) {
            output.add(iterator.next());
        }
        assertThat(output).isEqualTo(Lists.newArrayList(1, 2, 3, 4, 5));

        input = Lists.newArrayList(new NestedInteger(Collections.emptyList()));
        NestedIteratorV2 itr = new NestedIteratorV2(input);
        output = Lists.newArrayList();
        while (itr.hasNext()) {
            output.add(itr.next());
        }
        assertThat(output).isEmpty();
    }
}
