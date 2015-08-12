package topcoder.srm5xx.srm564;

/**
 * Created by hama_du on 15/08/11.
 */
public class KnightCircuit2 {
    int[] kx = {-2, -1, 1, 2, 2, 1, -1, -2};
    int[] ky = {-1, -2, -2, -1, 1, 2, 2, 1};

    public int maxSize(int w, int h) {
        if (Math.min(w, h) >= 4) {
            return w * h;
        }

        UnionFind uf = new UnionFind(w*h);
        for (int i = 0; i < h ; i++) {
            for (int j = 0; j < w ; j++) {
                for (int d = 0; d < 8 ; d++) {
                    int ti = i + ky[d];
                    int tj = j + kx[d];
                    if (ti < 0 || tj < 0 || ti >= h || tj >= w) {
                        continue;
                    }
                    uf.unite(i*w+j, ti*w+tj);
                }
            }
        }

        int max = 0;
        int[] cnt = new int[w*h];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int id = uf.find(i*w+j);
                cnt[id]++;
                max = Math.max(max, cnt[id]);
            }
        }
        return max;
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
