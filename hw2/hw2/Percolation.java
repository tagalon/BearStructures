package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public WeightedQuickUnionUF tracker;
    public boolean[][] grid;
    public int openSites;
    public WeightedQuickUnionUF gridFull;

    public Percolation(int N) {
        tracker = new WeightedQuickUnionUF(N * N + 2);
        grid = new boolean[N][N];
        gridFull = new WeightedQuickUnionUF(N * N + 2);
        openSites = 0;
        for (int row = 0; row < N; row++) {
            for(int col = 0; col < N; col++) {
                grid[row][col] = false;
            }
        }
    }

    private void validate(int v1, int v2) {
        if (v1 < 0 || v1 >= grid.length || v2 < 0 || v2 >= grid.length) {
            throw new IndexOutOfBoundsException(
                    v1 + ", " + v2 + " is not a valid index inside the grid where indexes are between (0, 0) and (" + (grid.length - 1) + ", " + (grid.length - 1) + ")"
            );
        }
    }

    private boolean indexedGrid(int row, int col) {
        return grid[row][col];
    }

    public void open(int row, int col) {
        validate(row, col);
        grid[row][col] = true;
        openSites += 1;
        if (row == 0) {
            tracker.union(row * grid.length + col, grid.length * grid.length);
            gridFull.union(row * grid.length + col, grid.length * grid.length);
        }
        if (row == grid.length - 1) {
            tracker.union( row * grid.length + col, grid.length * grid.length + 1);
        }
        for (int index = -1; index < 3; index += 2) {
            unionRowHelperGrid(row, col, index);
            unionColHelperGrid(row, col, index);
        }
    }

    private void unionRowHelperGrid(int row, int col, int index) {
        if ((row + index >= 0 && (row + index < grid.length)) && isOpen(row + index, col)) {
            tracker.union((row + index) * grid.length + col, row * grid.length + col);
            gridFull.union(row * grid.length + col, (row + index) * grid.length + col);
            if (isFull(row, col) && isOpen(row + index, col)) {
                gridFull.union((row + index) * grid.length + col, row * grid.length + col);
            }
        }
    }

    private void unionColHelperGrid(int row, int col, int index) {
        if ((col + index < grid.length && col + index >= 0) && isOpen(row, col + index)) {
            tracker.union(row * grid.length + (col + index), row * grid.length + col);
            gridFull.union(row * grid.length + col, row * grid.length + (col + index));
            if (isFull(row, col) && isOpen(row, col + index)) {
                gridFull.union(row * grid.length + (col + index), row * grid.length + col);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        if ((indexedGrid(row, col))) {
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        if (isOpen(row, col) && gridFull.connected(row * grid.length + col, grid.length * grid.length)) {
            return true;
        }
        return false;
    }

    public boolean percolates() {
        if (tracker.connected(grid.length * grid.length, grid.length * grid.length + 1)) {
            return true;
        }
        return false;
    }
    public int numberOfOpenSites () {
        return openSites;
    }
}
