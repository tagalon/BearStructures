package bearmaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;

public class KDTree implements PointSet{
    private int numCount;
    /* Private class storing node's information */
    private class PointNode {
        PointNode left;
        PointNode right;
        Point pointNode;
        boolean depth;
        public PointNode(Point p, boolean d){
            pointNode = p;
            depth = d;
        }
    }
    PointNode head;

    public int getNumCount() {
        return numCount;
    }

    /* KDTree Constructor which builds our tree */
    public KDTree(List<Point> points) {
        numCount = 0;
        for (int index = 0; index < points.size(); index ++) {
            insert(points.get(index));
            numCount += 1;
        }
    }

    /* Method for inserting a point into the tree */
    private void insert(Point p) {
        head = insert(head, p, true);
    }

    /* Recursive helper method storing point in the right place */
    private PointNode insert(PointNode h, Point p, boolean d) {
        if (h == null) {
            return new PointNode(p, d);
        } else if (h.pointNode.equals(p)) {
            numCount -= 1;
            return h;
        } else if (d && p.getX() >= h.pointNode.getX()) {
            h.right = insert(h.right, p, !d);
        } else if (d && p.getX() < h.pointNode.getX()) {
            h.left = insert(h.left, p, !d);
        } else if (!(d) && p.getY() >= h.pointNode.getY()) {
            h.right = insert(h.right, p, !d);
        } else if (!(d) && p.getY() < h.pointNode.getY()) {
            h.left = insert(h.left, p,!d);
        }
        return h;
    }

    /* Helper method for calculating distance */
    private double distance(PointNode p, Point g) {
        double x = p.pointNode.getX() - g.getX();
        double y = p.pointNode.getY() - g.getY();
        double distance = Math.sqrt(x * x + y * y);
        return distance;
    }

    /* Finds the nearest point to inputted arguments */
    @Override
    public Point nearest(double x, double y) {
        PointNode bestPoint = head;
        return nearestHelper(head, new Point (x, y), bestPoint).pointNode;
    }

    /* Recursive helper method for finding the nearest point */
    private PointNode nearestHelper(PointNode n, Point goal, PointNode best) {
        PointNode goodSide;
        PointNode badSide;
        if (n == null) {
            return best;
        }
        if (distance(n, goal) < distance(best, goal)) {
            best = n;
        }
        if (n.depth && goal.getX() < n.pointNode.getX()) {
            goodSide = n.left;
            badSide = n.right;
        } else if (!n.depth && goal.getY() < n.pointNode.getY()) {
            goodSide = n.left;
            badSide = n.right;
        } else if (!n.depth && goal.getY() >= n.pointNode.getY()) {
            goodSide = n.right;
            badSide = n.left;
        }
        else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearestHelper(goodSide, goal, best);
        if (badSide == null) {
            return best;
        }
        PointNode badY = new PointNode(new Point(goal.getX(), badSide.pointNode.getY()), false);
        PointNode badX = new PointNode(new Point(badSide.pointNode.getX(), goal.getY()), true);
        if (!n.depth && distance(badY, goal) < distance(best, goal)) {
            best = nearestHelper(badSide, goal, best);
        } else if (n.depth && distance(badX, goal) < distance(best, goal)) {
            best = nearestHelper(badSide, goal, best);
        }
        return best;
    }

    /* Tests how fast the KDTree Constructor is */
    public static void KDtimeEntries() {
        List<Integer> NList = new ArrayList<>();
        List<Double> timesList = new ArrayList<>();
        for (int n= 31250; n < 2000001; n *= 2) {
            List<Point> pointList = new ArrayList<Point>();
            Random randomNumGen = new Random();
            for (int index = 0; index < n; index ++);
                double x = randomNumGen.nextDouble() * 10 - 5;
                double y = randomNumGen.nextDouble() * 10 - 5;
                pointList.add(new Point(x, y));
            Stopwatch sw = new Stopwatch();
            PointSet testTree = new KDTree(pointList);
            double timeS = sw.elapsedTime();
            timesList.add(timeS);
            NList.add(n);
        }
        printTimingTable(NList, timesList, NList);
    }

    /* Tests how fast nearest method is from NaivePointSet */
    private static void NaiveNearest() {
        List<Integer> NList = new ArrayList<>();
        List<Double> timesList = new ArrayList<>();
        List<Integer> opsList = new ArrayList<>();
        for (int n= 125; n < 1001; n *= 2) {
            List<Point> pointList = new ArrayList<Point>();
            Random randomNumGen = new Random();
            for (int index = 0; index < n; index ++);
                double x = randomNumGen.nextDouble() * 1000 - 500;
                double y = randomNumGen.nextDouble() * 1000 - 500;
                pointList.add(new Point(x, y));
            NaivePointSet testSet = new NaivePointSet(pointList);
            Stopwatch sw = new Stopwatch();
            for (int count = 0; count < 1000000; count ++) {
                double testX = randomNumGen.nextDouble() * 10 - 5;
                double testY = randomNumGen.nextDouble() * 10 - 5;
                testSet.nearest(testX, testY);
            }
            double timeS = sw.elapsedTime();
            timesList.add(timeS);
            NList.add(n);
            opsList.add(1000000);
        }
        printTimingTable(NList, timesList, opsList);
    }

    /* Tests how fast nearest method is from KDTree */
    private static void KDTreeNearest() {
        List<Integer> NList = new ArrayList<>();
        List<Double> timesList = new ArrayList<>();
        List<Integer> opsList = new ArrayList<>();
        for (int n= 31250; n < 1000001; n *= 2) {
            List<Point> pointList = new ArrayList<Point>();
            Random randomNumGen = new Random();
            for (int index = 0; index < n; index ++);
                double x = randomNumGen.nextDouble() * 1000 - 500;
                double y = randomNumGen.nextDouble() * 1000 - 500;
                pointList.add(new Point(x, y));
            KDTree testSet = new KDTree(pointList);
            Stopwatch sw = new Stopwatch();
            for (int count = 0; count < 1000000; count ++) {
                double testX = randomNumGen.nextDouble() * 10 - 5;
                double testY = randomNumGen.nextDouble() * 10 - 5;
                testSet.nearest(testX, testY);
            }
            double timeS = sw.elapsedTime();
            timesList.add(timeS);
            NList.add(n);
            opsList.add(1000000);
        }
        printTimingTable(NList, timesList, opsList);
    }

    /* @source Lab 5 */
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

    /* Tests the efficieny of KDTree Constructor and nearest methods */
    public static void main(String[] args) {
        KDtimeEntries();
        NaiveNearest();
        KDTreeNearest();
    }
}
