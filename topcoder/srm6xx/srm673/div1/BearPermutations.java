package topcoder.srm6xx.srm673.div1;

import java.util.Arrays;

/**
 * Created by hamada on 2016/05/20.
 */
public class BearPermutations {

    public long[][] C;

    public long[][][] memo;

    public int S;

    public int MOD;

    public long[] dfs(int length, int pos) {
        if (memo[length][pos][0] >= 0) {
            return memo[length][pos];
        }
        Arrays.fill(memo[length][pos], 0);
        if (length == 1) {
            memo[length][pos][0] = 1;
            return memo[length][pos];
        }

        if (pos == 0 || pos == length-1) {
            for (int c = 0 ; c < length-1 ; c++) {
                long[] sub = dfs(length-1, c);
                for (int i = 0; i < sub.length ; i++) {
                    memo[length][pos][i] += sub[i];
                    memo[length][pos][i] -= (memo[length][pos][i] >= MOD) ? MOD : 0;
                }
            }
        } else {
            int ln = pos;
            int rn = length-ln-1;
            long[] left = new long[S+ln*2];
            long[] right = new long[S+rn];
            for (int i = 0; i < ln ; i++) {
                long[] sub = dfs(ln, i);
                for (int j = 0; j < sub.length; j++) {
                    int tj = j+ln-i;
                    left[tj] += sub[j];
                    left[tj] -= (left[tj] >= MOD) ? MOD : 0;
                }
            }
            for (int i = 0; i < rn ; i++) {
                long[] sub = dfs(rn, i);
                for (int j = 0; j < sub.length; j++) {
                    int tj = j+i;
                    right[tj] += sub[j];
                    right[tj] -= (right[tj] >= MOD) ? MOD : 0;
                }
            }
            for (int li = 0 ; li < left.length ; li++) {
                if (left[li] == 0) {
                    continue;
                }
                for (int ri = 0; ri < right.length ; ri++) {
                    int tj = li + (pos + ri + 1) - ln;
                    if (tj < 0) {
                        continue;
                    }
                    if (tj > S) {
                        break;
                    }
                    long prd = left[li] * right[ri] % MOD;
                    memo[length][pos][tj] += prd;
                    memo[length][pos][tj] -= (memo[length][pos][tj] >= MOD) ? MOD : 0;
                }
            }

            long base = C[length-1][ln];
            for (int i = 0; i <= S; i++) {
                memo[length][pos][i] *= base;
                memo[length][pos][i] %= MOD;
            }
        }
        return memo[length][pos];
    }

    public int countPermutations(int N, int s, int mo) {
        MOD = mo;
        S = s;
        C = new long[201][201];
        for (int i = 0; i < C.length; i++) {
            C[i][0] = C[i][i] = 1;
            for (int j = 1 ; j < i ; j++) {
                C[i][j] = C[i-1][j-1] + C[i-1][j];
                C[i][j] %= MOD;
            }
        }
        memo = new long[N+1][N][S+1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j < N ; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }

        long sum = 0;
        for (int p = 0; p < N ; p++) {
            long[] ans = dfs(N, p);
            for (int i = 0; i < ans.length; i++) {
                sum += ans[i];
            }
        }
        return (int)(sum % MOD);
    }

    public static void main(String[] args) {
        BearPermutations p = new BearPermutations();
        debug(p.countPermutations(100, 100, 1000000007));
    }

    public static void debug(Object... ob) {
        System.err.println(Arrays.deepToString(ob));
    }
}