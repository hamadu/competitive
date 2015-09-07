package topcoder.srm5xx.srm572;

/**
 * Created by hama_du on 15/09/05.
 */
public class NewArenaPassword {
    public int minChange(String oldPassword, int K) {
        int n = oldPassword.length();
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < K ; i++) {
            uf.unite(i, n-K+i);
        }
        int change = 0;
        for (int i = 0; i < n ; i++) {
            int[] deg = new int[26];
            int total = 0;
            for (int j = 0; j < n ; j++) {
                if (uf.find(j) == i) {
                    deg[oldPassword.charAt(j)-'a']++;
                    total++;
                }
            }
            int max = 0;
            for (int j = 0; j < 26 ; j++) {
                max = Math.max(max, deg[j]);
            }
            change += total - max;
        }
        return change;
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
