package bearmaps;

import java.util.ArrayList;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private class Node {
        Node right;
        T item;
        double priority;

        Node(T i, double p) {
            item = i;
            priority = p;
        }
    }

    private ArrayList<Node> items;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        items.add(new Node(null, -10000000));
    }


    public int size() {
        return items.size();
    }

    private int parent(int k) {
        return k - 1 / 2;
    }

    private void swap(int k1, int k2) {
        Node temp1 = items.get(k1);
        Node temp2 = items.get(k2);
        items.set(k1, temp2);
        items.set(k2, temp1);
    }

    @Override
    public void add(T item, double priority) {
        Node obj = new Node(item, priority);
        int k = items.indexOf(obj);
        Node par = items.get(parent(k));
        int parK = items.indexOf(par);
    }

    @Override
    public boolean contains(T item) {
        return true;
    }

    @Override
    public T getSmallest() {
        return null;
    }

    @Override
    public T removeSmallest() {
        return null;
    }

    @Override
    public void changePriority(T item, double p) {
    }
}
