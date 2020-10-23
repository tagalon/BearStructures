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

    public static void kdTreeTimeEntries() {
        List<Integer> nList = new ArrayList<>();
        List<Double> timesList = new ArrayList<>();
        for (int n = 31250; n < 2000001; n *= 2) {
            List<double[]> testArgList = randomArgList(n);
            ArrayHeapMinPQ test = new ArrayHeapMinPQ(n);
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
        assertEquals(test.removeSmallest(), 1);
        test.removeSmallest();
    }
}
