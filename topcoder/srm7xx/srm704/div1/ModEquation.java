package topcoder.srm7xx.srm704.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class ModEquation {



    public int[] count(int n, int K, int[] query) {
        int[] p = generatePrimes(100000);


        int[][] comb = new int[50][3];
        int pn = 0;
        int kk = K;
        for (int pr : p) {
            if (kk % pr == 0) {
                int cnt = 0;
                int pow = 1;
                while (kk % pr == 0) {
                    pow *= pr;
                    kk /= pr;
                    cnt++;
                }
                comb[pn++] = new int[]{pr, cnt, pow};
            }
        }
        if (kk >= 2) {
            comb[pn++] = new int[]{kk, 1, kk};
        }



        long[][] ptnTbl = new long[pn][];
        for (int pi = 0 ; pi < pn ; pi++) {
            int cnt = comb[pi][1];
            ptnTbl[pi] = new long[cnt+1];
            ptnTbl[pi][0] = comb[pi][2]-(comb[pi][2]/comb[pi][0]);
            for (int a = 1; a < cnt; a++) {
                ptnTbl[pi][a] = (ptnTbl[pi][a-1]/comb[pi][0]);
            }
            ptnTbl[pi][cnt] = 1;
        }

        long[][][] dp = new long[pn][][];
        for (int pi = 0 ; pi < pn ; pi++) {
            int cnt = ptnTbl[pi].length-1;
            dp[pi] = new long[n+1][cnt+1];
            dp[pi][0][0] = 1;
            for (int i = 0; i < n; i++) {
                for (int ct = 0; ct <= cnt; ct++) {
                    if (dp[pi][i][ct] == 0) {
                        continue;
                    }
                    long base = dp[pi][i][ct];
                    for (int u = 0; u <= cnt; u++) {
                        int tct = Math.min(ct+u, cnt);
                        dp[pi][i+1][tct] += base*ptnTbl[pi][u]%MOD;
                        dp[pi][i+1][tct] %= MOD;
                    }
                }
            }
        }

        int qn = query.length;
        int[] ret = new int[qn];
        for (int qi = 0 ; qi < qn ; qi++) {
            long ans = 1;
            int qq = query[qi];
            for (int pi = 0 ; pi < pn ; pi++) {
                int wcnt = 0;
                if (qq >= 1) {
                    while (qq%comb[pi][0] == 0) {
                        qq /= comb[pi][0];
                        wcnt++;
                    }
                } else {
                    wcnt = ptnTbl[pi].length-1;
                }
                wcnt = Math.min(wcnt, ptnTbl[pi].length-1);

                ans *= dp[pi][n][wcnt];
                ans %= MOD;
                ans *= inv(ptnTbl[pi][wcnt]);
                ans %= MOD;
            }
            ret[qi] = (int)ans;
        }
        return ret;
    }

    static final int MOD = 1000000007;

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x%2 != 0) {
                res = (res*a)%MOD;
            }
            a = (a*a)%MOD;
            x /= 2;
        }
        return res;
    }

    static long inv(long a) {
        return pow(a, MOD-2)%MOD;
    }

    static int[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        int pi = 0;
        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                pi++;
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }

        int[] ret = new int[pi];
        int ri = 0;
        for (int i = 2 ; i < upto ; i++) {
            if (isp[i]) {
                ret[ri++] = i;
            }
        }
        return ret;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
