package topcoder.srm5xx.srm509;

import java.util.*;

/**
 * Created by hama_du on 15/08/06.
 */
public class NumberLabyrinthDiv1 {

    int[] dx = {0, 1, 0};
    int[] dy = {0, 0, 1};

    public int getNumberOfPaths(int[] X, int[] Y, int[] val, int xFinish, int yFinish, int K) {
        prec(2001000);

        Set<Long> codeList = new HashSet<>();
        pt = new ArrayList<>();
        ptMap = new HashMap<>();
        for (int d = 0; d < 3 ; d++) {
            for (int i = 0; i < X.length; i++) {
                Point f = new Point(X[i]+dx[d]*val[i], Y[i]+dy[d]*val[i], d == 0 ? val[i] : 0);
                if (codeList.contains(f.code())) {
                    continue;
                }
                codeList.add(f.code());
                pt.add(f);
            }
        }
        pt.add(new Point(0, 0, 0));
        Point goal = new Point(xFinish, yFinish, 0);
        if (!codeList.contains(goal.code())) {
            pt.add(goal);
        }
        n = pt.size();
        Collections.sort(pt);
        int pid = 0;
        for (Point p : pt) {
            p.id = pid;
            if (p.x == xFinish && p.y == yFinish) {
                gid = pid;
                debug(gid);
            }
            ptMap.put(p.code(), p);
            pid++;
        }

        prec = new long[n][n][K+1];
        for (int i = 0; i < n; i++) {
            for (int j = 0 ; j < n ; j++) {
                Point p1 = pt.get(i);
                Point p2 = pt.get(j);
                int dx = p2.x - p1.x;
                int dy = p2.y - p1.y;
                if (dx < 0 || dy < 0) {
                    continue;
                }

                long[] xk = new long[K+1];
                long[] yk = new long[K+1];
                for (int useK = 0; useK <= K ; useK++) {
                    xk[useK] = sub(dx, useK) % MOD;
                    yk[useK] = sub(dy, useK) % MOD;
                }
                for (int k = 0; k <= K; k++) {
                    for (int x = 0; x <= k; x++) {
                        long ptn = (((comb(k, x) * xk[x]) % MOD) * yk[k-x]) % MOD;
                        prec[i][j][k] += ptn;
                    }
                    prec[i][j][k] %= MOD;
                }
            }
        }

        memo0 = new long[200][200][11];
        memo1 = new long[200][200][11];
        for (int i = 0; i < 200 ; i++) {
            for (int j = 0; j < 200 ; j++) {
                Arrays.fill(memo0[i][j], -1);
                Arrays.fill(memo1[i][j], -1);
            }
        }

        long[][] dp = new long[200][11];
        dp[0][0] = 1;
        for (int i = 1; i < n ; i++) {
            for (int k = 0; k <= K; k++) {
                dp[i][k] = dfs1(0, i, k);
                for (int j = 1 ; j < i; j++) {
                    if (j == gid || (pt.get(j).val >= 1) ) {
                        for (int jk = 0; jk <= k; jk++) {
                            long p = dp[j][jk];
                            p = (p * dfs1(j, i, k - jk)) % MOD;
                            dp[i][k] += p;
                            dp[i][k] %= MOD;
                        }
                    }
                }
                dp[i][k] %= MOD;
            }
        }

        long ans = 0;
        for (int i = 0; i <= K; i++) {
            ans += dp[gid][i];
        }
        ans %= MOD;

        return (int)ans;
    }

    List<Point> pt;
    Map<Long,Point> ptMap;
    int n;


    long[][][] prec;

    public class Point implements Comparable<Point> {
        int id;
        int x;
        int y;
        int val;

        Point(int x, int y, int v) {
            this.x = x;
            this.y = y;
            this.val = v;
        }

        @Override
        public int compareTo(Point o) {
            return (x != o.x) ? x - o.x : y - o.y;
        }

        public long code() {
            return ((1L*x)<<24L)+y;
        }

        public String toString() {
            return String.format("id:%d x:%d y:%d v:%d", id, x, y, val);
        }
    }

    long[][][] memo0;
    public long dfs0(int fr, int to, int k) {
        if (to == fr) {
            return (k == 0) ? 1 : 0;
        }
        if (memo0[fr][to][k] != -1) {
            return memo0[fr][to][k];
        }
        long ptn = prec[fr][to][k];
        long sub = 0;
        for (int subK = 0; subK <= k; subK++) {
            for (int med = fr + 1; med < to; med++) {
                if (pt.get(med).val >= 1) {
                    long d1 = prec[fr][med][subK];
                    long d2 = dfs0(med, to, k - subK);
                    sub += (d1 * d2) % MOD;
                    sub %= MOD;
                }
            }
        }
        sub %= MOD;
        ptn = (ptn + MOD - sub) % MOD;
        memo0[fr][to][k] = ptn;
        return ptn;
    }

    long[][][] memo1;
    public long dfs1(int fr, int to, int k) {
        if (to == fr) {
            return (k == 0) ? 1 : 0;
        }
        if (memo1[fr][to][k] != -1) {
            return memo1[fr][to][k];
        }
        long ptn = 0;
        Point p = pt.get(fr);
        if (p.val == 0) {
            ptn = dfs0(fr, to, k);
        } else {
            for (int d = 1 ; d <= 2; d++) {
                long tx = p.x + dx[d] * p.val;
                long ty = p.y + dy[d] * p.val;
                long tcode = (tx<<24L)+ty;
                Point ptt = ptMap.get(tcode);
                if (ptt.val >= 1) {
                    if (k == 0 && to == ptt.id) {
                        ptn++;
                    }
                    continue;
                }
                ptn += dfs0(ptt.id, to, k);
                ptn %= MOD;
            }
        }
        ptn %= MOD;
        memo1[fr][to][k] = ptn;
        return ptn;
    }

    int gid;

    // dxだけk回かけて移動
    public long sub(int dx, int k) {
        if (k > dx) {
            return 0;
        }
        if (dx == 0) {
            return k == 0 ? 1 : 0;
        }
        return comb(dx-1, k-1);
    }

    static final long MOD = 1000000009;

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x % 2 != 0) {
                res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            x /= 2;
        }
        return res;
    }

    static long inv(long a) {
        return pow(a, MOD - 2) % MOD;
    }

    static long[] _fact;
    static long[] _invfact;
    static long comb(long ln, long lr) {
        int n = (int)ln;
        int r = (int)lr;
        if (n < 0 || r < 0 || r > n) {
            return 0;
        }
        if (r > n / 2) {
            r = n - r;
        }
        return (((_fact[n] * _invfact[n - r]) % MOD) * _invfact[r]) % MOD;
    }

    static void prec(int n) {
        _fact = new long[n + 1];
        _invfact = new long[n + 1];
        _fact[0] = 1;
        _invfact[0] = 1;
        for (int i = 1; i <= n; i++) {
            _fact[i] = _fact[i - 1] * i % MOD;
            _invfact[i] = inv(_fact[i]);
        }
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    public static void main(String[] args) {
        NumberLabyrinthDiv1 nl = new NumberLabyrinthDiv1();
        debug(nl.getNumberOfPaths(
        new int[]{13, 224, 77, 509, 1451, 43, 379, 142, 477},
        new int[]{1974, 375, 38, 783, 3, 1974, 1790, 1008, 2710},
        new int[]{30, 1845, 360, 11, 837, 84, 210, 4, 6704},
        509,
        2011,
        10
        ));
    }
}
