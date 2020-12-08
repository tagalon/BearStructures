package byow;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Random;
import java.util.HashMap;
public class Room {
    private int w;
    private int h;
    private int innerW;
    private int innerH;
    private HashMap<Position, Position> tracker;

    public Room(int width, int height) {
        w = width;
        h = height;
        innerW = w - 2;
        innerH = h - 2;
        //Random start points and end points must be generated based off of width and height
        //
        int x = Random.nextInt
        for (int startW = x; startW < w; startW ++) {
            for (int startH = y; startH < h; startH++) {
                Position filledBlock = new Position(startW, startH);
                tracker.put(filledBlock);
            }
        }
    }
}
