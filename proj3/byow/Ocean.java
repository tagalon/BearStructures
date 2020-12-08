package byow;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class World {
//    private static TETile randomTile() {
//        int tileNum = RANDOM.nextInt(3);
//        switch (tileNum) {
//            case 0: return Tileset.WALL;
//            case 1: return Tileset.FLOWER;
//            case 2: return Tileset.NOTHING;
//            default: return Tileset.NOTHING;
//        }
//    }

//    private void addRoom(List<Room> rooms) {}
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        int WIDTH = 40;
        int HEIGHT = 40;
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // fills in a block 14 tiles wide by 4 tiles tall
        for (int x = 20; x < 35; x += 1) {
            for (int y = 5; y < 10; y += 1) {
                world[x][y] = Tileset.WALL;
            }
        }

        // draws the world to the screen
        ter.renderFrame(world);
    }
}
