public class Palindrome {
    /* Converts the String literal word to the Deque data structure */
    public Deque<Character> wordToDeque(String word) {
        if (word == null) {
            return null;
        }
        Deque<Character> reverseWord = new LinkedListDeque<>();
        for (int index = word.length() - 1; index > -1; index -= 1) {
            reverseWord.addFirst(word.charAt(index));
        }
        return reverseWord;
    }

    /* Helper function which returns boolean value on the word being a palindrome */
    private boolean isPalindromeHelper(Deque<Character> word) {
        if (word == null) {
            return false;
        } else if (word.size() == 1 | (word.isEmpty())) {
            return true;
        } else if (word.removeFirst() == word.removeLast()) {
            return isPalindromeHelper(word);
        } else {
            return false;
        }
    }

    /* Returns returns boolean value on the word being a palindrome using isPalindromeHelper */
    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        } else {
            Deque<Character> wordDeque = wordToDeque(word);
            return isPalindromeHelper(wordDeque);
        }
    }

    /* Returns boolean value indicating if the word is a palindrome using OffByOne */
    private boolean ccPalindromeHelper(Deque<Character> word, CharacterComparator cc) {
        if (word == null) {
            return false;
        } else if (word.size() == 1 | word.isEmpty()) {
            return true;
        } else if (cc.equalChars(word.removeFirst(), word.removeLast())) {
            return isPalindromeHelper(word);
        } else {
            return false;
        }
    }

    /* Returns boolean value indicating the word is a palindrome using CharacterComparator */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        if (wordDeque == null) {
            return false;
        } else if (cc == null) {
            return isPalindrome(word);
        }
        return ccPalindromeHelper(wordDeque, cc);
    }
}
