/** If you project is set up properly, this file should execute. 
* One thing you might consider is to try printing out the sequence of
* operations */
public class StudentArrayDequeLauncher {
    public static void main(String[] args) {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> sad2 = new ArrayDequeSolution<Integer>();
        sad1.addLast(0);
        sad2.addLast(0);
        sad1.addFirst(1);
        sad2.addFirst(1);
        sad1.addLast(2);
        sad2.addLast(2);
        sad1.addFirst(3);
        sad2.addFirst(3);
        sad1.addFirst(4);
        sad2.addFirst(4);
        sad1.addLast(5);
        sad2.addLast(5);
        sad1.addLast(6);
        sad2.addLast(6);
        sad1.addFirst(7);
        sad2.addFirst(7);
        sad1.addLast(8);
        sad2.addLast(8);
        sad1.addLast(9);
        sad2.addLast(9);
        System.out.println(sad1.removeLast());
        System.out.println(sad2.removeLast());
//        for (int i = 0; i < 10; i += 1) {
//            double numberBetweenZeroAndOne = StdRandom.uniform();
//
//            if (numberBetweenZeroAndOne < 0.5) {
//                sad1.addLast(i);
//                sad2.addLast(i);
//            } else {
//                sad1.addFirst(i);
//                sad2.addFirst(i);
//            }
//        }
//
//        sad1.printDeque();
//        for (int i = 0; i < 10; i++) {
//            double randomNum = StdRandom.uniform();
//            if (randomNum < 0.5) {
//                sad1.removeFirst();
//            } else {
//                sad1.removeLast();
//            }
//        }
//    }
    }
} 
