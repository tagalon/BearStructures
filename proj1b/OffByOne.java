public class OffByOne implements CharacterComparator {
    /* Checks if the given chars are right next to each other */
    @Override
    public boolean equalChars(char x, char y) {
        if (Math.abs(x - y) == 1) {
            return true;
        }
        return false;
    }
}
