package topcoder.srm5xx.srm578;

/**
 * Created by hama_du on 15/09/13.
 */
public class GooseInZooDivOne {
    private static final int MOD = 1000000007;

    public int count(String[] field, int dist) {
        int n = field.length;
        int m = field[0].length();
        char[][] map = new char[n][];
        for (int i = 0; i < n ; i++) {
            map[i] = field[i].toCharArray();
        }
        UnionFind uf = new UnionFind(n*m);
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] != 'v') {
                    continue;
                }
                for (int k = 0; k < n ; k++) {
                    for (int l = 0; l < m ; l++) {
                        if (map[k][l] == 'v' && Math.abs(i-k)+Math.abs(j-l) <= dist) {
                            uf.unite(i*m+j, k*m+l);
                        }
                    }
                }
            }
        }

        boolean[] visited = new boolean[n*m];
        int[] groups = new int[n*m];
        int gn = 0;
        for (int i = 0; i < n*m ; i++) {
            if (map[i/m][i%m] != 'v' || visited[i]) {
                continue;
            }
            int cnt = 0;
            for (int j = 0; j < n*m; j++) {
                if (uf.issame(i, j)) {
                    cnt++;
                    visited[j] = true;
                }
            }
            groups[gn++] = cnt;
        }


        int[][] dp = new int[gn+1][2];
        dp[0][0] = 1;
        for (int i = 0; i < gn ; i++) {
            for (int j = 0; j <= 1; j++) {
                int tj = (j + groups[i] % 2) % 2;
                dp[i+1][tj] += dp[i][j];
                dp[i+1][tj] %= MOD;

                dp[i+1][j] += dp[i][j];
                dp[i+1][j] %= MOD;
            }
        }
        return (dp[gn][0] + MOD - 1) % MOD;
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
