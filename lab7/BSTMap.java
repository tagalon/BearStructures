import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTIterator implements Iterator<K> {
        int i;
        List<Node> keys;
        private BSTIterator() {
            keys = new ArrayList<>(size);
            fillInOrder(keys, head);
            i = 0;
        }
        public boolean hasNext() { return i < keys.size();}
        public K next() { return keys.get(i++).key; }
    }

    public Iterator<K> iterator() {
        return new BSTIterator();
    }

    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        public Node(K argKey, V argValue) {
            key = argKey;
            value = argValue;
        }

    }

    private Node head;
    private int size;

    public BSTMap() {
        clear();
    }

    public BSTMap(K key, V value) {
        head = new Node(key, value);
        size = 1;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return getHelper(key, head);
    }

    public void printInOrder(Node h) {
        if (h == null) {
            return;
        }
        printInOrder(h.left);
        System.out.print("(" + head.key.toString() + ", " + head.value.toString() + ")");
        printInOrder(h.right);
    }

    private V getHelper(K key, Node h) {
        if (h == null) {
            return null;
        }
        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            return getHelper(key, h.left);
        } else if (cmp > 0) {
            return getHelper(key, h.right);
        } else {
            return h.value;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        head = putHelper(key, value, head);
    }

    private Node putHelper(K key, V value, Node h) {
        if (h == null) {
            size += 1;
            return new Node(key, value);
        }
        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left = putHelper(key, value, h.left);
        } else if (cmp > 0) {
            h.right = putHelper(key, value, h.right);
        } else {
            h.value = value;
        }
        return h;
    }


    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public V remove(K key) {
        V value = this.get(key);
        if (value != null) {
            head = removeHelper(head, key);
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if(this.get(key).equals(value)) {
            return this.remove(key);
        }
        return null;
    }

    private Node removeHelper(Node h, K key) {
        if (h == null) {
            return null;
        }
        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left = removeHelper(h.left, key);
        } else if (cmp > 0) {
            h.right = removeHelper(h.right, key);
        } else {
            return deleteNode(h);
        }
        return h;
    }

    private Node deleteNode(Node h) {
        size -= 1;
        if (h.left == null && h.right == null) {
            return null;
        } if (h.right == null) {
            return h.left;
        } if (h.left == null) {
            return h.right;
        }

        if (h.right.left == null) {
            h.right.left = h.left;
            return h.right;
        }

        Node t = h.right;
        Node s = h;
        while (t.left != null) {
            s = t;
            t = t.left;
        }

        t.left = h.left;
        s.left = t.right;
        t.right = h.right;
        return t;
    }

    public void fillInOrder(List<Node> list, Node h) {
        if (h == null) {
            return;
        }
        fillInOrder(list, h.left);
        list.add(h);
        fillInOrder(list, h.right);
    }

}
