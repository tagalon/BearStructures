import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testIsPalindromeCC() {
        Boolean nullVal = offByOne.equalChars('a', 'c');
        Boolean nullVal2 = offByOne.equalChars('c', 'a');
        Boolean trueVal = offByOne.equalChars('a', 'b');
        Boolean falseVal = offByOne.equalChars('a', 'a');
        Boolean capVal = offByOne.equalChars('A', 'B');
        Boolean capLowVal = offByOne.equalChars('A', 'b');
        Boolean numVal = offByOne.equalChars('1', '2');
        assertTrue(trueVal);
        assertTrue(numVal);
        assertTrue(capVal);
        assertFalse(nullVal2);
        assertFalse(capLowVal);
        assertFalse(falseVal);
        assertFalse(nullVal);
    }
}