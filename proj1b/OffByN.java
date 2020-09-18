public class OffByN implements CharacterComparator {
    private int N;
    /* Creates empty object with associated N */
    public OffByN(int num) {
        N = num;
    }
    @Override
    /* Checks if the given chars are right next to each other by N */
    public boolean equalChars(char x, char y) {
//        if (a >= 173 | b >= 173 | a <= 96 | b <= 96) {
//            return false;
        if (Math.abs(x - y) == N) {
            return true;
        }
        return false;
    }
}
