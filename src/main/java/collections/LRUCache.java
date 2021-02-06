package collections;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private static class Entry<K, V> {
        private K key;
        private V value;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        private K getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        private void setValue(V value) {
            this.value = value;
        }
    }

    private static class DLinkedList<T> {
        private static class ListNode<T>  {
            private T data;
            private ListNode<T> prev;
            private ListNode<T> next;

            private ListNode(T data) {
                this.data = data;
            }

            private T getData() {
                return data;
            }

            private void setData(T data) {
                this.data = data;
            }

            private ListNode<T> getPrev() {
                return prev;
            }

            private void setPrev(ListNode<T> prev) {
                this.prev = prev;
            }

            private ListNode<T> getNext() {
                return next;
            }

            private void setNext(ListNode<T> next) {
                this.next = next;
            }
        }

        private ListNode<T> head;
        private ListNode<T> tail;

        private DLinkedList() {}

        private void add(ListNode<T> node) {
            if (head == null) {
                head = node;
                tail = head;
                assert head == tail;
            } else {
                tail.setNext(node);
                node.setPrev(tail);
                tail = node;
            }
        }

        // remove first
        public void remove() {
            if (head == null) {
                return;
            }

            ListNode newHead = head.getNext();
            if (newHead == null) {
                head = null;
                tail = null;
                assert head == null && tail == null;
                return;
            }
            newHead.setPrev(null);
            head.setNext(null);
            head = newHead;
        }

        private void remove(ListNode<T> node) {
            if (node == null || head == null) {
                return;
            }

            if (node == head) {
                remove();
                return;
            } else if (node == tail) {
                ListNode newTail = tail.getPrev();
                newTail.setNext(null);
                tail.setNext(null);
                tail = newTail;
            } else {
                node.getPrev().setNext(node.getNext());
                node.getNext().setPrev(node.getPrev());
                node.setNext(null);
                node.setPrev(null);
            }
        }

        private void moveToEnd(ListNode<T> node) {
            if (node == null || head == null || node == tail) {
                return;
            }

            remove(node);
            add(node);
        }

        private final ListNode<T> getHead() {
            return head;
        }

        private final ListNode<T> getTail() {
            return tail;
        }
    }

    private final Map<K, DLinkedList.ListNode> cache = new HashMap<>();
    private final DLinkedList<Entry<K, V>> queue = new DLinkedList<>();
    private final int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public V get(K key) {
        if (cache.containsKey(key)) {
            DLinkedList.ListNode node = cache.get(key);
            queue.moveToEnd(node);
            return ((Entry<K, V>)node.getData()).getValue();
        }
        return null;
    }

    public void put(K key, V value) {
        if (cache.containsKey(key)) {
            DLinkedList.ListNode node = cache.get(key);
            ((Entry<K, V>)node.getData()).setValue(value);
            queue.moveToEnd(node);
        } else {
            DLinkedList.ListNode node = new DLinkedList.ListNode(new Entry<>(key, value));
            if (cache.size() == capacity) {
                K lruKey = queue.getHead().getData().getKey();
                queue.remove();
                cache.remove(lruKey);
            }
            cache.put(key, node);
            queue.add(node);
        }
//        if (cache.size() < capacity) {
//            if (cache.containsKey(key)) {
//                DLinkedList.ListNode<Entry<K, V>> node = cache.get(key);
//                node.getData().setValue(value);
//                queue.moveToEnd(node);
//            } else {
//                DLinkedList.ListNode<Entry<K, V>> node = new DLinkedList.ListNode<>(new Entry<>(key, value));
//                cache.put(key, node);
//                queue.add(node);
//            }
//        } else {
//            if (cache.containsKey(key)) {
//                DLinkedList.ListNode<Entry<K, V>> node = cache.get(key);
//                node.getData().setValue(value);
//                queue.moveToEnd(node);
//            } else {
//                DLinkedList.ListNode<Entry<K, V>> node = new DLinkedList.ListNode<>(new Entry<>(key, value));
//                queue.remove();
//                queue.add(node);
//            }
//        }
    }

    public static void main(String[] args) {
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);
        cache.put(1, 1); // [1, ]
        cache.put(2, 2); // [1, 2]
        assert (cache.get(1) == 1); // [2, 1]
        cache.put(3, 3); // [1, 3]
        assert (cache.get(2) == null);
        cache.put(4, 4); // [3, 4]
        assert (cache.get(1) == null);
        assert (cache.get(3) == 3); // [4, 3]
        assert (cache.get(4) == 4); // [3, 4]
    }
}
