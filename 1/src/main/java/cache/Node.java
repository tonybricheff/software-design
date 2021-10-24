package cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(exclude = {"next", "previous"})
@Builder
public class Node<K, V> {
    private K key;
    private V value;
    private Node<K, V> next;
    private Node<K, V> previous;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
