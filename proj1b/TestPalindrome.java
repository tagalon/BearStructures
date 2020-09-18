import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    
    @Test
    public void testIsPalindrome() {
        Boolean truePalindrome = palindrome.isPalindrome("poop");
        Boolean nullPalindrome = palindrome.isPalindrome(null);
        Boolean falsePalindrome = palindrome.isPalindrome("cat");
        Boolean emptyPalindrome = palindrome.isPalindrome("");
        Boolean charPalindrome = palindrome.isPalindrome("a");
        assertEquals(true, truePalindrome);
        assertFalse(nullPalindrome);
        assertEquals(false, falsePalindrome);
        assertTrue(emptyPalindrome);
        assertTrue(charPalindrome);
    }

    @Test
    public void testIsPalindromeCC() {
        OffByOne ob = new OffByOne();
        Boolean truePal = palindrome.isPalindrome("cab", ob);
        Boolean falsePal = palindrome.isPalindrome("cat", ob);
        Boolean emptyPal = palindrome.isPalindrome("", ob);
        Boolean emptyOb = palindrome.isPalindrome("tattarrattat", null);
        assertTrue(truePal);
        assertFalse(falsePal);
        assertTrue(emptyPal);
        assertTrue(emptyOb);
    }
}
