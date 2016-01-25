package topcoder.srm5xx.srm589;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/30.
 */
public class GooseTattarrattatDiv1 {
    public int getmin(String S) {
        int n = S.length();
        UnionFind uf = new UnionFind(26);
        for (int i = 0; i < n / 2 ; i++) {
            int a = S.charAt(i)-'a';
            int b = S.charAt(n-i-1)-'a';
            uf.unite(a, b);
        }
        int[] deg = new int[26];
        for (int i = 0; i < n ; i++) {
            deg[S.charAt(i)-'a']++;
        }

        int ret = 0;
        boolean[] visited = new boolean[26];
        for (int i = 0; i < 26 ; i++) {
            if (!visited[i]) {
                int sum = 0;
                int best = 0;
                for (int j = 0; j < 26; j++) {
                    if (uf.issame(i, j)) {
                        sum += deg[j];
                        best = Math.max(best, deg[j]);
                        visited[j] = true;
                    }
                }
                ret += sum - best;
            }
        }
        return ret;
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

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
