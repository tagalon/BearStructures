package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class KDTreeTest {
    private static Random randomNum = new Random();

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

    /* Tests Demo Slides Data */
    @Test
    public void testKDTree() {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(1, 5);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(4, 4);
        Point p7 = new Point(4, 2);
        NaivePointSet np = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6));
        KDTree nn = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        assertEquals(nn.nearest(0, 7), new Point(1, 5));
        assertEquals(np.nearest(0, 7), new Point(1, 5));
        assertEquals(nn.nearest(0, 7), np.nearest(0, 7));
        System.out.println(nn);
    }

    /* Tests 100,000 points and 20000 testQueries */
    @Test
    public void testRandomnessKDTree() {
        List<Point> pointList = randomPointsList(1000000);
        List<Point> testQueries = randomPointsList(20000);
        KDTree nn = new KDTree(pointList);
        NaivePointSet np = new NaivePointSet(pointList);
        for (Point p : testQueries) {
            Point kdtest = nn.nearest(p.getX(), p.getY());
            Point nptest = np.nearest(p.getX(), p.getY());
            assertEquals(kdtest, nptest);
        }
    }

    /* @source Joshua Hug */
    /* Tests the randomness of the nearest function from KDTree & NPS, searching for edge cases */
    private void testRandomness(int numPoints, int numQueries) {
        List<Point> pointList = randomPointsList(numPoints);
        List<Point> testQueries = randomPointsList(numQueries);
        KDTree nn = new KDTree(pointList);
        NaivePointSet np = new NaivePointSet(pointList);
        for (Point p : testQueries) {
            Point kdtest = nn.nearest(p.getX(), p.getY());
            Point nptest = np.nearest(p.getX(), p.getY());
            assertEquals(kdtest, nptest);
        }
    }

    /* Tests for duplicate entries */
    @Test
    public void testDuplicates() {
        Point p1 = new Point(10, 11);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(15, 0);
        Point p5 = new Point(4, 6);
        Point p6 = new Point(3, 2);
        KDTree nn = new KDTree(List.of(p1, p1, p2, p3, p4 ,p5, p6, p6));
        KDTree rightnn = new KDTree(List.of(p1, p2, p3, p4, p5, p6));
        assertEquals(nn.getNumCount(), rightnn.getNumCount());
    }
}
