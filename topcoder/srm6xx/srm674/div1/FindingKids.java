package topcoder.srm6xx.srm674.div1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hama_du on 2016/05/19.
 */
public class FindingKids {
    long MOD = (long)1e9+7;

    public long getSum(int n, int q, int A, int B, int C) {
        build(n, q, A, B, C);

        long[] lefts = new long[n];
        long[] rights = new long[n];
        int rn = 0;
        int ln = 0;

        Arrays.sort(pos, (o1, o2) -> Integer.compare(o1[0], o2[0]));
        for (int i = 0; i < n ; i++) {
            if (pos[i][1] == -1) {
                rights[rn++] = pos[i][0];
            }
        }
        for (int i = n-1 ; i >= 0 ; i--) {
            if (pos[i][1] == 1) {
                lefts[ln++] = pos[i][0];
            }
        }

        int[] deg = new int[n];
        for (int i = 0; i < q ; i++) {
            deg[(int)queries[i][0]]++;
        }
        long[][] map = new long[n][];
        for (int i = 0; i < n ; i++) {
            map[i] = new long[deg[i]];
        }
        for (int i = 0; i < q ; i++) {
            int id = (int)queries[i][0];
            map[id][--deg[id]] = queries[i][1];
        }

        long ans = 0;
        int lzero = ln;
        int rzero = 0;
        for (int posID = 0; posID < n ; posID++) {
            int id = pos[posID][2];
            if (pos[posID][1] == 1) {
                lzero--;
            }
            int cnt = Math.min(ln - lzero, rn - rzero);
            for (long time : map[id]) {
                //debug("q",time,pos[posID]);
                int l = -1;
                int r = cnt*2-1;
                if (pos[posID][1] == 1) {
                    if (ln - lzero > rn - rzero) {
                        r++;
                    }
                } else {
                    if (ln - lzero < rn - rzero) {
                        r++;
                    }
                }

                while (r - l > 1) {
                    int med = (r + l) / 2;
                    long dist = dist(med, pos[posID][1], lzero, rzero, lefts, rights, -1);
                    if (dist > time * 2) {
                        r = med;
                    } else {
                        l = med;
                    }
                }
                long thePos = 0;
                if (l == -1) {
                    thePos = (long)pos[posID][0] + pos[posID][1] * time;
                } else {
                    long dist = dist(l, pos[posID][1], lzero, rzero, lefts, rights, -1);
                    long center = dist(l, pos[posID][1], lzero, rzero, lefts, rights, 1);
                    long ltime = time * 2 - dist;
                    int ldir = (l + 1) % 2 == 1 ? -1 : 1;
                    thePos = (center + (pos[posID][1] * ldir * ltime)) / 2;
                }
                ans += Math.abs(thePos);
            }
            if (pos[posID][1] == -1) {
                rzero++;
            }
        }
        return ans;







    }

    public void boi(int l, int dir, int lzero, int rzero, long[] lefts, long[] rights) {
        int base = l / 2;
        int nl = (l % 2) * (dir == 1 ? 1 : 0);
        int nr = (l % 2) * (dir == 1 ? 0 : 1);
        debug("r",rights[rzero+base+nr] ,"l",lefts[lzero+base+nl]);
    }

    public long dist(int l, int dir, int lzero, int rzero, long[] lefts, long[] rights, int op) {
        int base = l / 2;
        int nl = (l % 2) * (dir == 1 ? 1 : 0);
        int nr = (l % 2) * (dir == 1 ? 0 : 1);
        return rights[rzero+base+nr] + lefts[lzero+base+nl] * op;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    int[][] pos;
    long[][] queries;

    void build(int n, int q, long a, long b, long c) {
        pos = new int[n][3];
        queries = new long[q][2];
        Set<Long> done = new HashSet<>();
        for (int i = 0; i < n ; i++) {
            a = (a * b + c) % MOD;
            long p = a % (MOD - n + i + 1);
            if (done.contains(p)) {
                p = MOD - n + i;
            }
            done.add(p);
            pos[i][0] = (int)p;
            pos[i][1] = p % 2 == 0 ? 1 : -1;
            pos[i][2] = i;
        }
        for (int i = 0; i < q ; i++) {
            a = (a * b + c) % MOD;
            queries[i][0] = (int)a % n;
            a = (a * b + c) % MOD;
            queries[i][1] = a;
        }
    }

    public static void main(String[] args) {
        FindingKids kids = new FindingKids();
//        debug(kids.getSum(5,4,3,2,1));

        debug(kids.getSum(200000,
                200000,
                12345,
                67890,
                111213141));
        
    }
}
