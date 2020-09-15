import java.lang.reflect.Array;

public class ArrayDeque<T> {
    private T[] elements;
    private int size;
    private int nextFirst;
    private int nextLast;

    /* The ArrayDeque function creates empty object for Array Deque. */
    public ArrayDeque() {
        elements = (T[]) new Object[8];
        nextFirst = 7;
        nextLast = 0;
        size = 0;
    }

    /* The upsize function copies original elements into a larger empty list, using int capacity as an argument. */
    private void upsize(int capacity) {
        T[] newElements = (T[]) new Object[capacity];
        for(int i = 0; i < elements.length; i ++) {
            newElements[i] = get(i);
        }
        nextFirst = newElements.length - 1;
        nextLast = size;
        elements = newElements;
    }

    /* The downsize function copies original elements into a list that\'s smaller than the original list, using no arguments other than elements itself. */
    private void downsize() {
        T[] newElements = (T[]) new Object[elements.length / 2];
        for(int i = 0; i < size - 1; i ++) {
            newElements[i] = get(i);
        }
        nextFirst = newElements.length - 1;
        nextLast = size;
        elements = newElements;
    }

    /* The checkFirst is a helper function for keeping track of nextFirst inside addFirst */
    private void checkFirst() {
        if (nextFirst == 0) {
            nextFirst = elements.length - 1;
        } else {
            nextFirst -= 1;
        }
    }

    /* The addFirst function adds the first T item to the ArrayDeque object array, elements. */
    public void addFirst(T item) {
        /* Special condition for looping backwards */
        if (elements.length == size && (size != 0)) {
            upsize(size * 2);
        }
        elements[nextFirst] = item;
        checkFirst();
        size += 1;
    }

    /* The checkFirst is a helper function for keeping track of nextLast inside addLast */
    private void checkLast() {
        if (nextLast == elements.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
    }

    /* The addLast function adds the last T item to the ArrayDeque object array, elements */
    public void addLast(T item) {
        if ((elements.length == size) && (size != 0)) {
            upsize( size * 2);
        }
        elements[nextLast] = item;
        checkLast();
        size += 1;
    }

    /* The isEmpty function returns a boolean value, indicating whether or not elements is an empty array. */
    public boolean isEmpty() {
        return (size == 0);
    }

    /* The size function returns the int size of the elements. */
    public int size() {
        return size;
    }

    /* The print function returns the elements of the elements array. */
    public void printDeque() {
        for (T element : elements) {
            System.out.print(element + " ");
        }
    }

    /* The get function returns a T value of the ArrayDeque object, elements, based on the index of the array. */
    public T get(int index) {
        return elements[(nextFirst + index + 1) % elements.length];
    }

    /* The checkRemoveLast is a helper function for keeping track of nextFirst inside removeFirst */
    public void checkRemoveFirst() {
        if (nextFirst == elements.length - 1) {
            nextFirst = 0;
        } else {
            nextFirst += 1;
        }
    }

    /* The checkRemoveLast is a helper function for keeping track of nextLast inside removeLast. */
    public void checkRemoveLast() {
        if (nextLast == 0) {
            nextLast = elements.length - 1;
        } else {
            nextLast -= 1;
        }
    }

    /* The removeFirst function removes the first T element in the array. */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if ((size - 1 < .25 * elements.length) && (elements.length >= 16)) {
            downsize();
        }
        T first = elements[nextFirst];
        checkFirstNull();
        checkRemoveFirst();
        size -= 1;
        return first;
    }

    /* The checkFirstNull is a helper function for removing first T element, when nextFirst is at the end of the list and deletes the element.*/
    private void checkFirstNull() {
        if (nextFirst + 1 == elements.length) {
            elements[0] = null;
        } else {
            elements[nextFirst] = null;
        }
    }

    /* The removeLast function removes the last T element within the array elements. */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size - 1 < .25 * elements.length && elements.length >= 16) {
            downsize();
        }
        T last = elements[size - 1];
        elements[size -1] = null;
        checkRemoveLast();
        size -=1;
        return last;
    }
}
