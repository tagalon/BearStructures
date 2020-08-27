public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int count = 1;
        while (count < 11) {
            System.out.print(x + " ");
            x = x + count;
            count += 1;
        }
    }
}