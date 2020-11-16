# Build Your Own World Design Document

**Partner 1: Rahul Ravella  **

**Partner 2: Vivek Dutta **

## Classes and Data Structures


-Positions.java = This is the class where we dictate the x and y coordinates of each tile that we build.

-Rectangle.java = 

- (a single tile, has four cardinal directions)
- This class generates these rectangular spaces where we generate interactive and noninteractive elements in the space
- Choice of shape for all tiles in world (tentative, simplifies calculations)
- getRow and getColumn
- Orientation (to increase Randomness of the world)
- Generating random rectangles must include floor tiles anywhere across the border of the shape in order to have hallways connecting to other rectangles
- getNeighbors()
- The size of the rectangle itself doesn’t exceed the world size. (The biggest rectangle you can have in the world must be at least .25 of the size. ( casting int alongside Std.Random and Math.round to build these rectangles)
Class variables for each of four cardinal directions (this.left, this.right, this.top, this.bottom)
- Each class variable initially = Tile.NOTHING
- As build world, tiles get occupied.

-Tessellation.java = (tesselation = group of rectangles)
- Build out from center tile based off the inputted width and height of the world
- Each new spawn connected (side-to-side) the previous tile
- (recursive?) spawning using getNeighbors() to check open spaces in all four cardinal directions
- Contains all the different tiles that exist in our world and must have their own unique identifiers (HashMap implementation)
What can possibly exist in each of 4 cardinal directions (Tile.FLOWER, Tile. BUTTERFLY, etc. OR Tile.NOTHING)

## Algorithms

## Persistence
