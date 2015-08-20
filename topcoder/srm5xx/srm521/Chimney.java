package topcoder.srm5xx.srm521;

import java.util.*;

/**
 * Created by hama_du on 15/08/17.
 */
public class Chimney {
    private static final long MOD = 1000000007;

    public int countWays(long n) {
        int[] idmap = new int[1<<16];
        int _idx = 0;
        Arrays.fill(idmap, -1);

        int[][][] id = new int[16][16][16];
        for (int i = 0; i < 16 ; i++) {
            for (int j = 0; j < 16 ; j++) {
                for (int k = 0; k < 16 ; k++) {
                    int z = normalize(i, j, k);
                    if (z == -1) {
                        id[i][j][k] = -1;
                        continue;
                    }
                    if (idmap[z] == -1) {
                        idmap[z] = _idx++;
                    }
                    id[i][j][k] = idmap[z];
                }
            }
        }


        boolean[] done = new boolean[_idx];
        long[][] mat = new long[_idx][_idx];
        for (int i = 0; i < 16 ; i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    int from = id[i][j][k];
                    if (from == -1) {
                        continue;
                    }
                    if (done[from]) {
                        continue;
                    }
                    done[from] = true;
                    for (int l = 0; l < 4 ; l++) {
                        if ((i & (1<<l)) == 0) {
                            int to = id[i|(1<<l)][j][k];
                            if (to != -1) {
                                mat[from][to]++;
                            }
                        }
                        if ((j & (1<<l)) == 0) {
                            int to = id[i][j|(1<<l)][k];
                            if (to != -1) {
                                mat[from][to]++;
                            }
                        }
                        if ((k & (1<<l)) == 0) {
                            int to = id[i][j][k|(1<<l)];
                            if (to != -1) {
                                mat[from][to]++;
                            }
                        }
                    }
                }
            }
        }

        long[][] A = pow(mat, n*4, MOD);

        return (int)(A[0][0] % MOD);
    }

    private int normalize(int r1, int r2, int r3) {
        if (r1 == 15) {
            return normalize(r2, r3, 0);
        }
        if (r1 == 0) {
            if (r2 == 0 && r3 == 0) {
                return 0;
            }
            return -1;
        }
        for (int k = 0; k < 4 ; k++) {
            if ((r2 & (1<<k)) >= 1) {
                if ((r1 & (1<<k)) == 0 || (r1 & (1<<((k+1)%4))) == 0) {
                    return -1;
                }
            }
            if ((r3 & (1<<k)) >= 1) {
                if ((r2 & (1<<k)) == 0 || (r2 & (1<<((k+1)%4))) == 0) {
                    return -1;
                }
            }
        }
        return r1 | (r2<<4) | (r3<<8);
    }

    private int left(int r1) {
        int last = (r1 & 1);
        return (r1 >> 1) | (last << 3);
    }

    public static long[][] pow(long[][] a, long n, long mod) {
        long i = 1;
        long[][] res = E(a.length);
        long[][] ap = mul(E(a.length), a, mod);
        while (i <= n) {
            if ((n & i) >= 1) {
                res = mul(res, ap, mod);
            }
            i *= 2;
            ap = mul(ap, ap, mod);
        }
        return res;
    }

    public static long[][] E(int n) {
        long[][] a = new long[n][n];
        for (int i = 0 ; i < n ; i++) {
            a[i][i] = 1;
        }
        return a;
    }

    public static long[][] mul(long[][] a, long[][] b, long mod) {
        long[][] c = new long[a.length][b[0].length];
        if (a[0].length != b.length) {
            System.err.print("err");
        }
        for (int i = 0 ; i < a.length ; i++) {
            for (int j = 0 ; j < b[0].length ; j++) {
                long sum = 0;
                for (int k = 0 ; k < a[0].length ; k++) {
                    sum = (sum + a[i][k] * b[k][j]) % mod;
                }
                c[i][j] = sum;
            }
        }
        return c;
    }

    public static void main(String[] args) {
        Chimney chimney = new Chimney();
        for (int i = 1; i <= 10 ; i++) {
            debug(chimney.countWays(i));
        }
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
