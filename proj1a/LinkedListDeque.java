public class LinkedListDeque<T> {
    /* Helper class for instantiating the nodes for the LinkedListDeque */
    private class Node {
        private Node prev;
        private T item;
        private Node next;

        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    /* The LinkedListDeque creates the empty object LinkedListDeque and assigns it to sentinel. */
    LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /* The addFirst function adds the first T item to the sentinel. */
    public void addFirst(T item) {
        Node temp = sentinel.next;
        sentinel.next = new Node(null, item, null);
        sentinel.next.prev = sentinel;
        sentinel.next.next = temp;
        temp.prev = sentinel.next;
        size += 1;
    }

    /* The addLast function adds the last T item to the sentinel and takes in the input, T item. */
    public void addLast(T item) {
        Node temp = sentinel.prev;
        sentinel.prev = new Node(null, item, null);
        sentinel.prev.next = sentinel;
        sentinel.prev.prev = temp;
        temp.next = sentinel.prev;
        size += 1;
    }

    /* The removeFirst function removes the first item from the sentinel and returns the T item. */
    public T removeFirst() {
        Node temp = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return temp.item;
    }

    /* The removeLast function removes the last item from the sentinel and returns the T item. */
    public T removeLast() {
        Node temp = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return temp.item;
    }

    /* The printDeque function prints all the items from the sentinel and returns nothing. */
    public void printDeque() {
        Node temp = sentinel.next;
        while (temp != sentinel) {
            System.out.print(temp.item + " ");
            temp = temp.next;
        }
        System.out.println("");
    }

    /* The get function is a helper function used to get the indexed value T */
    public T get(int index, Node nodeDeque) {
        if (index == 0) {
            return nodeDeque.item;
        } else {
            return get(index - 1, nodeDeque.next);
        }
    }

    /* The getRecursive function returns given T indexed item in sentinel. */
    public T getRecursive(int index) {
        if (index == 0) {
            return get(0, sentinel.next);
        } else {
            return get(index - 1, sentinel.next.next);
        }
    }

    /* The isEmpty function checks returns a boolean value indicating if the sentinel is empty. */
    public boolean isEmpty() {
        return (size == 0);
    }

    /* The size function returns the int size of the sentinel. */
    public int size() {
        return size;
    }
}