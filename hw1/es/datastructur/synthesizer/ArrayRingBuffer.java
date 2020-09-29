package es.datastructur.synthesizer;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */

    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }
    private class ArrayRingBufferIterator implements Iterator<T> {
        private int index;

        public ArrayRingBufferIterator() {
            index = 0;
        }

        public boolean hasNext() {
            return index < capacity();
        }

        public T next() {
            T returnItem = rb[index];
            index += 1;
            return returnItem;
        }
    }

    @Override
    public int capacity() {
        return rb.length;
    }
    @Override
    public int fillCount() {
        return fillCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (this.getClass() != o.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> castO = (ArrayRingBuffer<T>) o;
        if (castO.capacity() != this.capacity()) {
            return false;
        }
        for (int count = 0; count < capacity(); count ++) {
            if (this.rb[count] != castO.rb[count]) {
                return false;
            }
        }
        return true;
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last. Don't worry about throwing the RuntimeException until you
        //       get to task 4.
        if (fillCount == capacity()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        last += 1;
        fillCount += 1;
        if (last == capacity()) {
            last = 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first. Don't worry about throwing the RuntimeException until you
        //       get to task 4.
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T itemFirst = rb[first];
        rb[first] = null;
        first += 1;
        fillCount -=1;
        if (first == capacity()) {
            first = 0;
        }
        return itemFirst;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (first == capacity()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        return rb[first];
    }

}
