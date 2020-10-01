import java.util.ArrayList;

public class UnionFind {
    int[] parent;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];
        // set all the parents to be -1 to symbolize that they are disjoint
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid vertex. */
    private void validate(int v1) {
        if (v1 < 0 || v1 >= parent.length ) {
            throw new IllegalArgumentException(
                    v1 + " is not a valid vertex between 0 and " + parent.length
            );
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        int root = find(v1);
        return -1 * parent[root];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean isConnected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Connecting a
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void connect(int v1, int v2) {
        // TODO
        int rootVOne = find(v1);
        int rootVTwo = find(v2);
        if (rootVOne == rootVTwo) {
            return;
        } else if (rootVOne != rootVTwo) {
            if (parent[rootVOne] < parent[rootVTwo]) {
                parent[rootVOne] += parent[rootVTwo];
                parent[rootVOne] = rootVTwo;
            }
        } else {
            parent[rootVTwo] += parent[rootVOne];
            parent[rootVOne] = rootVTwo;
        }
    }

    /* Returns the root of the set v1 belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int v1) {
        validate(v1);
        if (parent[v1] < 0) {
            return v1;
        } else {
            ArrayList<Integer> fastSearch = new ArrayList<>();
            int root = v1;
            while (parent[root] >= 0) {
                fastSearch.add(root);
                root = parent[root];
            }
            for(int i: fastSearch) {
                parent[i] = root;
            }
            return root;
        }
    }

}
