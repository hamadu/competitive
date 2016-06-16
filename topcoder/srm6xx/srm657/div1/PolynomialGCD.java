package topcoder.srm6xx.srm657.div1;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/06/16.
 */
public class PolynomialGCD {
    int n;
    int[] d;

    public int gcd(String s) {
        n = s.length();
        d = new int[n];
        for (int i = 0; i < n ; i++) {
            d[i] = s.charAt(i)-'0';
        }
        int[] pr = generatePrimes(10000);

        long ret = 1;
        for (int p : pr) {
            if (p > n) {
                break;
            }
            long cnt = p >= 150 ? dfsLarge(p) : dfs(0, 0, p, 1);
            ret *= pow(p, cnt);
            ret %= MOD;
        }
        return (int)ret;
    }

    private long dfsLarge(int p) {
        long best = MOD;
        for (int i = 0 ; i < p ; i++) {
            long nu = 0;
            for (int j = i ; j < n ; j += p) {
                nu += d[j];
            }
            best = Math.min(best, nu);
        }
        return best;
    }

    private long dfs(int level, int idx, int p, int pp) {
        int cnt = (n - idx + pp - 1) / pp;
        long[] arr = new long[p];
        for (int i = 0; i < cnt ; i++) {
            arr[i%p] += d[idx + pp * i];
        }
        long sum = Arrays.stream(arr).sum();
        if (cnt < p) {
            return sum * level;
        }
        long best = MOD;
        for (int i = 0 ; i < p ; i++) {
            best = Math.min(best, (sum - arr[i]) * level + dfs(level+1, idx+pp*i, p, pp*p));
        }
        return best;
    }

    static final long MOD = 1000000007;

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
}
