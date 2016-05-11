package topcoder.srm6xx.srm685.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/05/11.
 */
public class FoxAirline2 {
    public static final String OK = "Possible";
    public static final String NG = "Impossible";

    public boolean isOK = false;

    public void dfs(int idx, int[] a, int[] b, UnionFind red, UnionFind blue) {
        if (idx >= a.length) {
            int n = red.cnt.length;
            isOK |= red.groupCount(0) == n && blue.groupCount(0) == n;
            return;
        }
        boolean redSame = red.issame(a[idx], b[idx]);
        boolean blueSame = blue.issame(a[idx], b[idx]);
        if (!redSame) {
            UnionFind nred = new UnionFind(red);
            nred.unite(a[idx], b[idx]);
            dfs(idx+1, a, b, nred, blue);
        }
        if (!blueSame) {
            UnionFind nblue = new UnionFind(blue);
            nblue.unite(a[idx], b[idx]);
            dfs(idx+1, a, b, red, nblue);
        }
        if (redSame && blueSame) {
            dfs(idx+1, a, b, red, blue);
        }
    }

    public String isPossible(int n, int[] a, int[] b) {
        dfs(0, a, b, new UnionFind(n), new UnionFind(n));
        return isOK ? OK : NG;
    }

    public static void main(String[] args) {
        FoxAirline2 foxAirline2 = new FoxAirline2();
        debug(foxAirline2.isPossible(4,
        new int[]{0,0,0,1,1,2},
        new int[]{1,2,3,2,3,3}
        ));

    }

    static class UnionFind {
        int[] rank;
        int[] parent;
        int[] cnt;

        public UnionFind(int n) {
            rank = new int[n];
            parent = new int[n];
            cnt = new int[n];
            for (int i = 0; i < n ; i++) {
                parent[i] = i;
                cnt[i] = 1;
            }
        }

        public UnionFind(UnionFind uf) {
            rank = uf.rank.clone();
            parent = uf.parent.clone();
            cnt = uf.cnt.clone();
        }

        public int find(int a) {
            if (parent[a] == a) {
                return a;
            }
            parent[a] = find(parent[a]);
            return parent[a];
        }

        public void unite(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) {
                return;
            }
            if (rank[a] < rank[b]) {
                parent[a] = b;
                cnt[b] += cnt[a];
                cnt[a] = cnt[b];
            } else {
                parent[b] = a;
                cnt[a] += cnt[b];
                cnt[b] = cnt[a];
                if (rank[a] == rank[b]) {
                    rank[a]++;
                }
            }
        }

        public int groupCount(int a) {
            return cnt[find(a)];
        }

        private boolean issame(int a, int b) {
            return find(a) == find(b);
        }
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
