import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestRedBlackFloorSet {
    @Test
    public void randomizedTest() {
        AListFloorSet listA = new AListFloorSet();
        RedBlackFloorSet listB = new RedBlackFloorSet();
        for (int n = 0; n < 1000000; n++) {
            double random = StdRandom.uniform(-5000.00, 5000.00);
            listA.add(random);
            listB.add(random);
        }
        for (int n = 0; n < 100000; n++) {
            double random = StdRandom.uniform(-5000.00, 5000.00);
            double randomA = listA.floor(random);
            double randomB = listB.floor(random);
            assertEquals(randomA, randomB, .000001);
        }
    }
}
