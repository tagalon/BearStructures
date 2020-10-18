package bearmaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;

public class KDTree implements PointSet {
    private static Random randomNum = new Random();

    /* Variable keeping track of unique points added */
    private int numCount;

    public int getNumCount() {
        return numCount;
    }

    /* Private class storing node's information */
    private class PointNode {
        PointNode left;
        PointNode right;
        Point pointNode;
        boolean depth;
        PointNode(Point p, boolean d) {
            pointNode = p;
            depth = d;
        }
    }
    PointNode head;

    /* KDTree Constructor which builds our tree */
    public KDTree(List<Point> points) {
        numCount = 0;
        for (int index = 0; index < points.size(); index++) {
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
            h.left = insert(h.left, p, !d);
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
        return nearestHelper(head, new Point(x, y), bestPoint).pointNode;
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
        } else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearestHelper(goodSide, goal, best);
        PointNode badX = new PointNode(new Point(n.pointNode.getX(), goal.getY()), false);
        PointNode badY = new PointNode(new Point(goal.getX(), n.pointNode.getY()), true);
        if (distance(badY, goal) < distance(best, goal)) {
            best = nearestHelper(badSide, goal, best);
        } else if (distance(badX, goal) < distance(best, goal)) {
            best = nearestHelper(badSide, goal, best);
        }
        return best;
    }

    /* @source Joshua Hug */
    /* Returns random generated point */
    private static Point randomPoint() {
        double x = randomNum.nextDouble() * 10000 - 5000;
        double y = randomNum.nextDouble() * 10000 - 5000;
        return new Point(x, y);
    }

    /* @source Joshua Hug */
    /* Returns list of random generated points */
    private static List<Point> randomPointsList(int n) {
        List<Point> pointsList = new ArrayList<>();
        for (int index = 0; index < n; index++) {
            pointsList.add(randomPoint());
        }
        return pointsList;
    }

    /* Tests how fast the KDTree Constructor is */
    public static void kdTreeTimeEntries() {
        List<Integer> nList = new ArrayList<>();
        List<Double> timesList = new ArrayList<>();
        for (int n = 31250; n < 2000001; n *= 2) {
            List<Point> pointList = randomPointsList(n);
            Stopwatch sw = new Stopwatch();
            PointSet testTree = new KDTree(pointList);
            double timeS = sw.elapsedTime();
            timesList.add(timeS);
            nList.add(n);
        }
        printTimingTable(nList, timesList, nList);
    }

    /* Tests how fast nearest method is from NaivePointSet */
    private static void naiveNearest() {
        List<Integer> nList = new ArrayList<>();
        List<Double> timesList = new ArrayList<>();
        List<Integer> opsList = new ArrayList<>();
        for (int n = 125; n < 1001; n *= 2) {
            List<Point> pointList = randomPointsList(n);
            NaivePointSet testSet = new NaivePointSet(pointList);
            List<Point> testQueries = randomPointsList(1000000);
            Stopwatch sw = new Stopwatch();
            for (Point p : testQueries) {
                testSet.nearest(p.getX(), p.getY());
            }
            double timeS = sw.elapsedTime();
            timesList.add(timeS);
            nList.add(n);
            opsList.add(1000000);
        }
        printTimingTable(nList, timesList, opsList);
    }

    /* Tests how fast nearest method is from KDTree */
    private static void kdTreeNearest() {
        List<Integer> nList = new ArrayList<>();
        List<Double> timesList = new ArrayList<>();
        List<Integer> opsList = new ArrayList<>();
        for (int n = 31250; n < 1000001; n *= 2) {
            List<Point> pointList = randomPointsList(n);
            KDTree testSet = new KDTree(pointList);
            List<Point> testQueries = randomPointsList(1000000);
            Stopwatch sw = new Stopwatch();
            for (Point p : testQueries) {
                testSet.nearest(p.getX(), p.getY());
            }
            double timeS = sw.elapsedTime();
            timesList.add(timeS);
            nList.add(n);
            opsList.add(1000000);
        }
        printTimingTable(nList, timesList, opsList);
    }

    /* @source Lab 5 */
    /* Constructs a timing table for the efficiency of a method */
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

    /* Tests the efficiency of KDTree Constructor and nearest methods */
    public static void main(String[] args) {
        kdTreeTimeEntries();
        naiveNearest();
        kdTreeNearest();
    }
}
