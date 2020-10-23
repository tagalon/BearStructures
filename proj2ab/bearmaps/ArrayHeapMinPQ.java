package bearmaps;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private class Node {
        T item;
        double priority;
        Node(T i, double p) {
            item = i;
            priority = p;
        }
    }
    private Node[] items;
    private int size;
    private static Random randomNum = new Random();

    /* My tracker array keeps track of the item placed into the PQ */
    /* It's assigned value is the index and priority queue itself. */
    private HashMap<T, Integer> tracker = new HashMap<>();

    /* This is my constructor which declares the ArrayHeapMinPQ as an object */
    public ArrayHeapMinPQ() {
        items = new ArrayHeapMinPQ.Node[16];
        items[0] = ((new Node(null, -1.01)));
        size = 0;
    }

    /* This function returns the size as an integer. */
    public int size() {
        return size;
    }

    /* This function returns parent index based on given k index. */
    private int parent(int k) {
        return k / 2;
    }

    /* This helper function takes in two int indices and swaps their items. */
    private void swap(int k1, int k2) {
        tracker.put(items[k1].item, k2);
        tracker.put(items[k2].item, k1);
        Node temp = items[k1];
        items[k1] = items[k2];
        items[k2] = temp;
    }

    /* This helper function returns the right child index for int k. */
    private int rightChild(int k) {
        return k * 2;
    }

    /* This helper function returns the left child index for int k. */
    private int leftChild(int k) {
        return k * 2 + 1;
    }

    /* This helper function reassigns an array with int capacity. */
    private void resize(int capacity) {
        Node[] temp = new ArrayHeapMinPQ.Node[capacity];
        for (int i = 1; i <= size; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    /* This is a helper function used to determine which input is greater,
    * less than, or equal to and returns an int. */
    private int compare(double k1, double k2) {
        if (k1 > k2) {
            return 1;
        } else if (k1 < k2) {
            return -1;
        } else {
            return 0;
        }
    }

    /* This function compares object's priority values at their given indices
    * and returns a boolean value based on which is bigger. */
    private boolean greater(int k1, int k2) {
        if (items[k1] == null || items[k2] == null) {
            return false;
        }
        return compare(items[k1].priority, items[k2].priority) > 0;
    }

    /* @source Joshua Hug */
    /* This helper method is used in order to traverse up the tree at given int index (or k). */
    /* It also ensures that the items in the priority queue are in their appropriate positions. */
    private void swim(int k) {
        while (k > 1 && greater(parent(k), k)) {
            swap(k, parent(k));
            k = parent(k);
        }
    }

    /* @source Joshua Hug */
    /* This helper method used in order to traverse down the tree at given int index (or k). */
    /* It also ensures that the items in the priority queue are in their appropriate positions. */
    private void sink(int k) {
        if (leftChild(k) > size && rightChild(k) > size) {
            return;
        }
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && greater(j, j + 1)) {
                j += 1;
            }
            if (!greater(k, j)) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    /* This method adds an item to the priority queue in its appropriate place based off
    * its priority value (double) and its T item acts as a placeholder. */
    @Override
    public void add(T item, double priority) {
        //use hashing to
        if (tracker.containsKey(item)) {
            throw new IllegalArgumentException("You have already added " + item + " in PQ");
        }
        if (size == items.length - 1) {
            resize(size * 2);
        }
        Node obj = new Node(item, priority);
        items[size + 1] = obj;
        tracker.put(item, size + 1);
        size += 1;
        swim(size);
    }

    /* This method checks whether or not the input item exists within the PQ. */
    @Override
    public boolean contains(T item) {
        return tracker.containsKey(item);
    }

    /* This method simply returns the head of PQ, which has the smallest priority. */
    @Override
    public T getSmallest() {
        return items[1].item;
    }

    /* This method returns the indice which has a smaller priority value. */
    private int minChild(int k1, int k2) {
        if (items[k1].priority < items[k2].priority) {
            return k1;
        } else {
            return k2;
        }
    }

    /* This method removes the head and restructures the PQ in accordance
    * with the other items' priority values. */
    @Override
    public T removeSmallest() {
        if (((double) size) / items.length < .33) {
            resize(items.length / 2);
        }
        Node temp = items[1];
        swap(1, size);
        tracker.remove(items[size].item);
        items[size] = null;
        sink(1);
        size -= 1;
        return temp.item;
    }

    /* This method changes the priority value of the specified item in the PQ
    * and restructures it in respect to the other items' priority values. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            return;
        }
        int index = tracker.get(item);
        items[index] = new Node(item, priority);
        sink(index);
        swim(index);
    }

    /* This method returns a random double[] array for T and priority. */
    private static double[] randomArg() {
        double T = randomNum.nextDouble() * 10000 - 5000;
        double p = randomNum.nextDouble() * 10000 - 5000;
        double[] arg = new double[2];
        arg[0] = T;
        arg[1] = p;
        return arg;
    }

    /* This method returns list of n random double[] arrays for T and priority. */
    private static List<double[]> randomArgList(int n) {
        List<double[]> pointsList = new ArrayList<>();
        for (int index = 0; index < n; index++) {
            pointsList.add(randomArg());
        }
        return pointsList;
    }

    /* @source Lab 5 */
    /* This method prints a timing table for the efficiency of a method. */
    /* This method takes in three lists, telling the number of calls (nList),
     * time for each number of calls (t), and the number of operations (op). */
    private static void printTimingTable(List<Integer> nList, List<Double> t, List<Integer> op) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("---------------------------------------------------------\n");
        for (int i = 0; i < nList.size(); i += 1) {
            int N = nList.get(i);
            double time = t.get(i);
            int opCount = op.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    /* This method tests how fast the add method is from ArrayHeapMinPQ. */
    private static void arrayHeapAddEntries() {
        List<Integer> nList = new ArrayList<>();
        List<Double> timesList = new ArrayList<>();
        for (int n = 31250; n < 2000001; n *= 2) {
            List<double[]> testArgList = randomArgList(n);
            ArrayHeapMinPQ test = new ArrayHeapMinPQ();
            Stopwatch sw = new Stopwatch();
            for (double[] arg : testArgList) {
                test.add(arg[0], arg[1]);
            }
            double timeS = sw.elapsedTime();
            timesList.add(timeS);
            nList.add(n);
        }
        printTimingTable(nList, timesList, nList);
    }

    /* This method tests how fast the removeSmallest method is from ArrayHeapMinPQ. */
    private static void arrayHeapRemoveSmallest() {
        List<Integer> nList = new ArrayList<>();
        List<Double> timesList = new ArrayList<>();
        for (int n = 31250; n < 2000001; n *= 2) {
            List<double[]> testArgList = randomArgList(n * 2);
            ArrayHeapMinPQ test = new ArrayHeapMinPQ();
            for (double[] arg : testArgList) {
                test.add(arg[0], arg[1]);
            }
            Stopwatch sw = new Stopwatch();
            for (int index = 0; index < n; index++) {
                test.removeSmallest();
            }
            double timeS = sw.elapsedTime();
            timesList.add(timeS);
            nList.add(n);
        }
        printTimingTable(nList, timesList, nList);
    }

    /* This method tests how fast the changePriority method is from ArrayHeapMinPQ. */
    private static void arrayHeapChangePriority() {
        List<Integer> nList = new ArrayList<>();
        List<Double> timesList = new ArrayList<>();
        for (int n = 31250; n < 2000001; n *= 2) {
            List<double[]> testArgList = randomArgList(n * 2);
            ArrayHeapMinPQ test = new ArrayHeapMinPQ();
            for (double[] arg : testArgList) {
                test.add(arg[0], arg[1]);
            }
            Stopwatch sw = new Stopwatch();
            for (double[] arg : testArgList) {
                test.changePriority(arg[0], -arg[0]);
            }
            double timeS = sw.elapsedTime();
            timesList.add(timeS);
            nList.add(n);
        }
        printTimingTable(nList, timesList, nList);
    }
}
