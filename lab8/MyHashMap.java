import java.util.Iterator;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private class Node {
        K key;
        V value;
        Node(K k, V v) {
            key = k;
            value = v;
        }

    }
    private ArrayList<Node>[] buckets;
    private int size;
    private double loadFactor;
    private HashSet<K> keys;

    private static final int DEFAULT_INIT_SIZE = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    public MyHashMap() {
        buckets = (ArrayList<Node>[]) new ArrayList[DEFAULT_INIT_SIZE];
        size = 0;
        loadFactor = DEFAULT_LOAD_FACTOR;
        keys = new HashSet<K>();
    }

    @Override
    public void clear() {
        buckets = (ArrayList<Node>[]) new ArrayList[DEFAULT_INIT_SIZE];
        size = 0;
        keys = new HashSet<K>();
    }
    @Override
    public Set<K> keySet() { return keys;}

    @Override
    public boolean containsKey(K key) {
        return keySet().contains(key);
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }

    private int findBucket(K key, int numBuckets) {
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    private int findBucket(K key) {
        return findBucket(key, buckets.length);
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        if ( node == null) {
            return null;
        }
        return node.value;
    }

    private void rebucket(int targetSize) {
        ArrayList<Node>[] newBuckets = (ArrayList<Node>[]) new ArrayList[targetSize];
        for (K key: keys) {
            int idx = findBucket(key, newBuckets.length);
            if (newBuckets[idx] == null) {
                newBuckets[idx] = new ArrayList<>();
            }
            newBuckets[idx].add(getNode(key));
        }
        buckets = newBuckets;
    }

    private Node getNode(K key) {
        int idx = findBucket(key);
        ArrayList<Node> bucketList = buckets[idx];
        if (bucketList != null) {
            for (Node node : bucketList) {
                if (node.key.equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }
    @Override
    public V remove(K key) {
        return remove(key, null);
    }
    @Override
    public V remove (K key, V value) {
        Node n = getNode(key);
        if (n == null) {
            return null;
        }
        keys.remove(key);
        size -= 1;
        int idx = findBucket(key);
        ArrayList<Node> bucket = buckets[idx];
        bucket.remove(n);
        return n.value;

    }

    @Override
    public void put(K key, V value) {
        Node n = getNode(key);
        if (n != null) {
            n.value = value;
            return;
        }
        n = new Node(key, value);
        int idx = findBucket(key);
        ArrayList<Node> bucket = buckets[idx];
        if (bucket == null) {
            bucket = new ArrayList<Node>();
            buckets[idx] = bucket;
        }
        bucket.add(n);
        size += 1;
        keys.add(key);
        if ( (double) size / buckets.length > loadFactor) {
            rebucket(size * 2);
        }
    }
    public MyHashMap(int initialSize) {
        buckets = (ArrayList<Node>[]) new ArrayList[initialSize];
        size = 0;
        loadFactor = DEFAULT_LOAD_FACTOR;
        keys = new HashSet<K>();
    }
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = (ArrayList<Node>[]) new ArrayList[initialSize];
        size = 0;
        loadFactor = maxLoad;
        keys = new HashSet<K>();
    }
}
