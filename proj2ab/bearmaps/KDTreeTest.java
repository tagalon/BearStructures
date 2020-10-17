package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class KDTreeTest {

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
        assertEquals(nn.getNumCount(), 6);
        System.out.println(nn);
    }

    @Test
    public void testRandomnessKDTree() {
        List<Point> pointList = new ArrayList<Point>();
        Random randomNumGen = new Random();
        for (int index = 0; index < 100; index += 1) {
            double x = randomNumGen.nextDouble() * 10 - 5;
            double y = randomNumGen.nextDouble() * 10 - 5;
            pointList.add(new Point(x, y));
        }
        NaivePointSet np = new NaivePointSet((pointList));
        KDTree nn = new KDTree(pointList);
        double testX = randomNumGen.nextDouble() * 10 - 5;
        double testY = randomNumGen.nextDouble() * 10 - 5;
        assertEquals(nn.nearest(testX, testY), np.nearest(testX, testY));
    }
}
