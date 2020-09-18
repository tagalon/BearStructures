import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testIsPalindromeCC() {
        Boolean nullVal = offByOne.equalChars('a', 'c');
        Boolean trueVal = offByOne.equalChars('a', 'b');
        Boolean falseVal = offByOne.equalChars('a', 'a');

    }
}