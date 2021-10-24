package cache;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class LruCache<K, V> {
    private final Map<K, Node<K, V>> cache = new HashMap<>();
    private Node<K, V> head;
    private Node<K, V> tail;
    private final int capacity;

    LruCache(int capacity) {
        assert capacity > 0;
        this.capacity = capacity;
        this.head = null;
        this.tail = null;
    }

    public V find(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        var foundNode = cache.get(key);
        remove(foundNode);
        makeHead(foundNode);
        return foundNode.getValue();
    }

    public void put(K key, V value) {
        final Node<K, V> node = new Node<>(key, value);
        if (cache.containsKey(key)) {
            var oldNode = cache.get(key);
            remove(oldNode);
            assert oldNode.getNext() == null && oldNode.getPrevious() == null;
        } else if (cache.size() == capacity && tail != null) {
            var tailNode = tail;
            remove(tailNode);
            assert tailNode.getNext() == null && tailNode.getPrevious() == null;
            cache.remove(tailNode.getKey());
            assert cache.size() < capacity;
        }
        cache.put(key, node);
        makeHead(node);
        assert cache.size() <= capacity;
    }

    private void makeHead(Node<K, V> node) {
        node.setNext(head);
        if (head != null) head.setPrevious(node);
        head = node;
        if (cache.size() == 1) {
            tail = node;
        }
        assert tail != null;
    }

    private void remove(Node<K, V> node) {
        if (node.getNext() == null) {
            tail = node.getPrevious();
        }
        if (node.getPrevious() == null) {
            head = node.getNext();
        }

        if (node.getPrevious() != null)
            node.getPrevious().setNext(node.getNext());
        if (node.getNext() != null)
            node.getNext().setPrevious(node.getPrevious());

        assert node.getPrevious() == null || node.getPrevious().getNext() != node;
        assert node.getNext() == null || node.getNext().getPrevious() != node;

        node.setNext(null);
        node.setPrevious(null);
    }
}
