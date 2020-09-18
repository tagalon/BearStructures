public class OffByOne implements CharacterComparator {
    /* Checks if the given chars are right next to each other */
    @Override
    public boolean equalChars(char x, char y) {
        int a = Character.toLowerCase(x);
        int b = Character.toLowerCase(y);
        if (a >= 173 | b >= 173 | a <= 96 | b <= 96) {
            return false;
        } else if (a == 0 | b == 0) {
            return false;
        } else if (Math.abs(a - b) == 1) {
            return true;
        }
        return false;
    }
}
