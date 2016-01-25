package topcoder.srm5xx.srm589;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/30.
 */
public class FlippingBitsDiv1 {
    private static final int INF = 114514;

    public int getmin(String[] S, int M) {
        int[] a = build(S);
        if (M <= 18) {
            return solveSmall(a, M);
        } else {
            return solveLarge(a, M);
        }
    }

    private int solveLarge(int[] a, int m) {
        int n = a.length;
        int cur = (n + m - 1) / m;
        int best = INF;
        for (int ptn = 0 ; ptn < 1<<(n/m) ; ptn++) {
            int[] flipped = new int[cur];
            int now = 0;
            for (int c = cur-1 ; c >= 0 ; c--) {
                if ((ptn & (1<<c)) >= 1) {
                    now ^= 1;
                }
                flipped[c] = now;
            }
            int total = Integer.bitCount(ptn);
            for (int i = 0; i < m ; i++) {
                int one = 0;
                int zero = 0;
                for (int k = i, ct = 0 ; k < n ; k += m, ct++) {
                    int val = a[k] ^ flipped[ct];
                    if (val == 1) {
                        one++;
                    } else {
                        zero++;
                    }
                }
                total += Math.min(one, zero);
            }
            best = Math.min(best, total);
        }
        return best;
    }

    private int solveSmall(int[] a, int m) {
        int n = a.length;
        int cur = (n + m - 1) / m;
        int[][] dp = new int[cur+1][2];


        int[] block = new int[cur];
        for (int i = 0; i < cur ; i++) {
            for (int j = i * m ; j < (i + 1) * m; j++) {
                block[i] <<= 1;
                if (j < n) {
                    block[i] += a[j];
                }
            }
        }

        int best = INF;
        for (int ptn = 0 ; ptn < (1<<m) ; ptn++) {
            int amari = (cur * m) - n;
            if (amari >= 1) {
                int lowMask = (1 << amari)-1;
                block[cur-1] |= lowMask;
                block[cur-1] -= lowMask;
                block[cur-1] += ptn & lowMask;
            }

            for (int i = 0; i <= cur ; i++) {
                Arrays.fill(dp[i], INF);
            }
            dp[cur][0] = 0;
            for (int i = cur ; i >= 1 ; i--) {
                int ij = (i-1)*m;
                int ijt = Math.min(n, ij+m);
                int length = ijt - ij;
                for (int f = 0 ; f <= 1 ; f++) {
                    if (dp[i][f] == INF) {
                        continue;
                    }
                    int base = dp[i][f];

                    // non-flip
                    {
                        int diff = Integer.bitCount(block[i-1] ^ ptn);
                        if (f == 1) {
                            diff = length - diff;
                        }
                        dp[i-1][f] = Math.min(dp[i-1][f], base+diff);
                    }

                    // flip
                    if (length == m) {
                        int diff = length - Integer.bitCount(block[i-1] ^ ptn);
                        if (f == 1) {
                            diff = length - diff;
                        }
                        dp[i-1][1-f] = Math.min(dp[i-1][1-f], base+diff+1);
                    }
                }
            }
            best = Math.min(best, Math.min(dp[0][0], dp[0][1]));
        }
        return best;
    }

    // ooooooo
    // -----
    //   -----

    private int[] build(String[] s) {
        String line = "";
        for (String i : s) {
            line += i;
        }
        char[] c = line.toCharArray();
        int n = c.length;
        int[] x = new int[n];
        for (int i = 0; i < n ; i++) {
            x[i] = c[i]-'0';
        }
        return x;
    }

    public static void main(String[] args) {
        FlippingBitsDiv1 bits = new FlippingBitsDiv1();
        debug(bits.getmin(new String[]{"00111000"}, 1));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
