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
    /* This method returns random generated point. */
    private static Point randomPoint() {
        double x = randomNum.nextDouble() * 10000 - 5000;
        double y = randomNum.nextDouble() * 10000 - 5000;
        return new Point(x, y);
    }

    /* @source Joshua Hug */
    /* This method returns list of n random generated points. */
    private static List<Point> randomPointsList(int n) {
        List<Point> pointsList = new ArrayList<>();
        for (int index = 0; index < n; index++) {
            pointsList.add(randomPoint());
        }
        return pointsList;
    }

    /* This method tests the Project 2A demo slides data. */
    @Test
    public void testKDTree() {
        Point p1 = new Point(2, 3);
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

    /* This method runs multiple tests on testRandomness using values from 1 to 100000. */
    @Test
    public void testRandomnessKDTree() {
        testRandomness(100000, 20000);
        testRandomness(10000, 30);
        testRandomness(50000, 50000);
        testRandomness(1000, 30000);
        testRandomness(2, 1);
    }

    /* @source Joshua Hug */
    /* The method tests randomness of the nearest function from KDTree & NPS */
    /* This method takes in two int arguments: numPoints and numQueries */
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

    /* This method tests for duplicate entries. */
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