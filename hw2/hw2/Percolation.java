package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF tracker;
    private boolean[][] grid;
    private int openSites;
    private WeightedQuickUnionUF gridFull;

    // Creates N x N grid
    public Percolation(int N) {
        if (N <= 0)  {
            throw new IllegalArgumentException(
                    "N cannot be 0 or less than 0 itself"
            );
        }
        tracker = new WeightedQuickUnionUF(N * N + 2);
        grid = new boolean[N][N];
        gridFull = new WeightedQuickUnionUF(N * N + 2);
        openSites = 0;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                grid[row][col] = false;
            }
        }
    }

    //Checks if arguments are valid reference in grid
    private void validate(int v1, int v2) {
        if (v1 < 0 || v1 >= grid.length || v2 < 0 || v2 >= grid.length) {
            throw new IndexOutOfBoundsException(v1 + ", " + v2 + " is not a valid index");
        }
    }

    //Abstracted implementation for referencing grid
    private boolean indexedGrid(int row, int col) {
        return grid[row][col];
    }

    //Opens a site and connects surrounding sites if need be
    public void open(int row, int col) {
        validate(row, col);
        if (grid[row][col]) {
            openSites += 0;
        } else {
            openSites += 1;
        }
        grid[row][col] = true;
        if (row == 0) {
            tracker.union(row * grid.length + col, grid.length * grid.length);
            gridFull.union(row * grid.length + col, grid.length * grid.length);
        }
        if (row == grid.length - 1) {
            tracker.union(row * grid.length + col, grid.length * grid.length + 1);
        }
        for (int index = -1; index < 3; index += 2) {
            unionRowHelperGrid(row, col, index);
            unionColHelperGrid(row, col, index);
        }
    }

    // Unifies cells from left to right
    private void unionRowHelperGrid(int row, int col, int index) {
        if ((row + index >= 0 && (row + index < grid.length)) && isOpen(row + index, col)) {
            tracker.union((row + index) * grid.length + col, row * grid.length + col);
            gridFull.union(row * grid.length + col, (row + index) * grid.length + col);
            if (isFull(row, col) && isOpen(row + index, col)) {
                gridFull.union((row + index) * grid.length + col, row * grid.length + col);
            }
        }
    }

    // Unifies cells from top to bottom
    private void unionColHelperGrid(int row, int col, int index) {
        if ((col + index < grid.length && col + index >= 0) && isOpen(row, col + index)) {
            tracker.union(row * grid.length + (col + index), row * grid.length + col);
            gridFull.union(row * grid.length + col, row * grid.length + (col + index));
            if (isFull(row, col) && isOpen(row, col + index)) {
                gridFull.union(row * grid.length + (col + index), row * grid.length + col);
            }
        }
    }

    // Checks if cell is open or not
    public boolean isOpen(int row, int col) {
        validate(row, col);
        if ((indexedGrid(row, col))) {
            return true;
        }
        return false;
    }

    // Checks if cell is full or not
    public boolean isFull(int row, int col) {
        int gridLen = grid.length;
        if (isOpen(row, col) && gridFull.connected(row * grid.length + col, gridLen * gridLen)) {
            return true;
        }
        return false;
    }

    //Checks whether the grid percolates
    public boolean percolates() {
        if (tracker.connected(grid.length * grid.length, grid.length * grid.length + 1)) {
            return true;
        }
        return false;
    }

    // Returns number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // Used to test functionality of the class
    public static void main(String[] args) { }
}
