package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    private static Random randomNum = new Random();

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

    /* This function tests my personal implementation and the functionality of
    * ArrayMinHeapPQ */
    @Test
    public void testFunctionality() {
        ArrayHeapMinPQ test = new ArrayHeapMinPQ(8);
        test.add(1, 1.00);
        test.add(3, 3.00);
        test.add(2, 2.00);
        test.add(4, 4.00);
        test.add(5, 5.00);
        test.add(7, 7.00);
        test.add(6, 1.00);
        test.removeSmallest();
        test.changePriority(2, 3);
        assertEquals(test.removeSmallest(), 6);
        test.removeSmallest();
    }

    /* This function tests implementation of add, changePriority, and removeSmallest
     *  in sequential order. */
    @Test
    public void testRandomnessCorrectness() {
        for (int n = 10; n < 10000; n*=2) {
            List<double[]> testArgList = randomArgList(n);
            ArrayHeapMinPQ test = new ArrayHeapMinPQ(n);
            NaiveMinPQ<Double> nmtest = new NaiveMinPQ<>();
            for (double[] arg : testArgList) {
                test.add(arg[0], arg[1]);
                nmtest.add(arg[0], arg[1]);
            }
            for (int index = 1; index < n; index *= 2 + 1) {
                double[] args = testArgList.get(index);
                test.changePriority(args[0], -args[1]);
                nmtest.changePriority(args[0], -args[1]);
            }
            for (int index = 0; index < n - 5; index++) {
                assertEquals(test.removeSmallest(), nmtest.removeSmallest());
                assertEquals(test.size(), nmtest.size());
            }
        }
    }

    /* This function tests implementation of add, changePriority, add, and removeSmallest
     *  in sequential order. */
    @Test
    public void testRandomnessCorrectnessV2() {
        for (int n = 10; n < 10000; n*=2) {
            List<double[]> testArgList = randomArgList(n);
            List<double[]> testArgList2 = randomArgList(n);
            ArrayHeapMinPQ test = new ArrayHeapMinPQ(n);
            NaiveMinPQ<Double> nmtest = new NaiveMinPQ<>();
            for (double[] arg : testArgList) {
                test.add(arg[0], arg[1]);
                nmtest.add(arg[0], arg[1]);
            }
            for (int index = 1; index < n; index *= 2 + 1) {
                double[] args = testArgList.get(index);
                test.changePriority(args[0], -args[1]);
                nmtest.changePriority(args[0], -args[1]);
            }
            for (double[] arg : testArgList2) {
                test.add(arg[0], arg[1]);
                nmtest.add(arg[0], arg[1]);
            }
            for (int index = 0; index < n - 5; index++) {
                assertEquals(test.removeSmallest(), nmtest.removeSmallest());
                assertEquals(test.size(), nmtest.size());
            }
        }
    }
}
