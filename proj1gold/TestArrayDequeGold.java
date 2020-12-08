import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testGoldAD() {
        ArrayDequeSolution sol = new ArrayDequeSolution<Integer>();
        StudentArrayDeque test = new StudentArrayDeque<Integer>();
        for (int a = 0; a < 3; a++) {
            String message = "\n";
            //@source StudentArrayDequeLauncher.java
            for (int i = 0; i < 10; i += 1) {
                double randomNum = StdRandom.uniform();

                if (randomNum < 0.5) {
                    sol.addLast(i);
                    test.addLast(i);
                    message += "addLast("+i+")\n";
                } else {
                    sol.addFirst(i);
                    test.addFirst(i);
                    message += "addFirst("+i+")\n";
                }
            }
            test.printDeque();
            for (int i = 0; i < 10; i++) {
                Integer solRemove = null;
                Integer testRemove = null;
                double randomNum = StdRandom.uniform();
                if (randomNum < 0.5) {
                    if (!sol.isEmpty()) {
                        solRemove = (Integer) sol.removeFirst();
                    }
                    if (!test.isEmpty()) {
                        testRemove = (Integer) test.removeFirst();
                    }
                    if (solRemove != null && testRemove != null) {
                        Integer actual = testRemove;
                        Integer expected = solRemove;
                        assertEquals(message+"removeFirst()", actual, expected);
                    }
                } else {
                    if (!sol.isEmpty()) {
                        solRemove = (Integer) sol.removeLast();
                    }
                    if (!test.isEmpty()) {
                        testRemove = (Integer) test.removeLast();
                    }
                    if (solRemove != null && testRemove != null) {
                        Integer actual = testRemove;
                        Integer expected = solRemove;
                        assertEquals(message+"removeFirst()", actual, expected);
                    }
                }
            }
        }
    }
}
