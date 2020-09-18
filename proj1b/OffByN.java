public class OffByN implements CharacterComparator {
    private int N;
    /* Creates empty object with associated N */
    public OffByN(int num) {
        N = num;
    }
    @Override
    /* Checks if the given chars are right next to each other by N */
    public boolean equalChars(char x, char y) {
        if (Math.abs(x - y) == N) {
            return true;
        }
        return false;
    }
}
