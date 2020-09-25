import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about SLList getLast method.
 */
public class TimeSLList {
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        List<Integer> NList = new ArrayList<>();
        List<Double> timesList = new ArrayList<>();
        for(int n = 1000; n < 100000; n *= 2) {
            SLList<Integer> test = new SLList<>();
            for (int countOps = 0; countOps < n; countOps++) {
                test.addLast(1);
            }
            Stopwatch sw = new Stopwatch();
            for (int countOps = 0; countOps < n; countOps++) {
                test.getLast();
            }
            double timeS = sw.elapsedTime();
            timesList.add(timeS);
            NList.add(n);
        }
        printTimingTable(NList, timesList, NList);
    }

}
