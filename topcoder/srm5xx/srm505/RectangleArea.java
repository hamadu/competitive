package topcoder.srm5xx.srm505;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hama_du on 15/08/05.
 */
public class RectangleArea {
    public int minimumQueries(String[] known) {
        int n = known.length;
        int m = known[0].length();


        UnionFind ufH = new UnionFind(n);
        UnionFind ufW = new UnionFind(m);

        char[][] map = new char[n][];
        for (int i = 0; i < n ; i++) {
            map[i] = known[i].toCharArray();
        }
        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                for (int k = 0; k < m ; k++) {
                    if (map[i][k] == 'Y' && map[j][k] == 'Y') {
                        ufH.unite(i, j);
                    }
                }
            }
        }
        for (int i = 0; i < m ; i++) {
            for (int j = i+1 ; j < m ; j++) {
                for (int k = 0; k < n ; k++) {
                    if (map[k][i] == 'Y' && map[k][j] == 'Y') {
                        ufW.unite(i, j);
                    }
                }
            }
        }

        Set<Integer> gh = new HashSet<>();
        for (int i = 0; i < n ; i++) {
            gh.add(ufH.find(i));
        }

        Set<Integer> gw = new HashSet<>();
        for (int i = 0; i < m ; i++) {
            gw.add(ufW.find(i));
        }

        int doH = gh.size() - 1;
        int doW = gw.size() - 1;
        for (int i = 0; i < n ; i++) {
            boolean one = false;
            for (int j = 0; j < m ; j++) {
                if (map[i][j] == 'Y') {
                    one = true;
                }
            }
            if (!one) {
                doW++;
            }
        }
        for (int i = 0; i < m; i++) {
            boolean one = false;
            for (int j = 0; j < n ; j++) {
                if (map[j][i] == 'Y') {
                    one = true;
                }
            }
            if (!one) {
                doH++;
            }
        }
        return Math.min(doW, doH);
    }

    static class UnionFind {
        int[] parent, rank;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0 ; i < n ; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            parent[x] = find(parent[x]);
            return parent[x];
        }

        void unite(int x, int y) {
            x = find(x);
            y = find(y);
            if (x == y) {
                return;
            }
            if (rank[x] < rank[y]) {
                parent[x] = y;
            } else {
                parent[y] = x;
                if (rank[x] == rank[y]) {
                    rank[x]++;
                }
            }
        }
        boolean issame(int x, int y) {
            return (find(x) == find(y));
        }
    }
}
