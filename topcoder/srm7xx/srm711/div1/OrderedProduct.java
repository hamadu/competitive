package topcoder.srm7xx.srm711.div1;

import java.io.*;
import java.util.*;

public class OrderedProduct {
    private static final long MOD = 1000000007;

    private static final int MAX = 2510;

    public int count(int[] a) {
        int n = a.length;
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }

        int[][] ncr = new int[MAX][MAX];
        for (int i = 0; i < ncr.length; i++) {
            ncr[i][0] = ncr[i][i] = 1;
            for (int j = 1; j < i ; j++) {
                ncr[i][j] = (ncr[i-1][j-1]+ncr[i-1][j]);
                ncr[i][j] -= ncr[i][j] >= MOD ? MOD : 0;
            }
        }

        long[][] dp = new long[2][sum+1];
        dp[0][0] = 1;

        for (int i = 0; i < n ; i++) {
            int fr = i % 2;
            int to = 1 - fr;

            Arrays.fill(dp[to], 0);
            for (int currentGroup = 0 ; currentGroup <= sum ; currentGroup++) {
                if (dp[fr][currentGroup] == 0) {
                    continue;
                }
                long base = dp[fr][currentGroup];
                if (i >= 1) {
                    for (int add = 0 ; add <= a[i] ; add++) {
                        int left = a[i] - add;
                        int toGroup = currentGroup + add;
                        long ptn = base * ncr[left+toGroup-1][toGroup-1] % MOD * ncr[toGroup][add] % MOD;
                        dp[to][toGroup] += ptn;
                        dp[to][toGroup] %= MOD;
                    }
                } else {
                    for (int partition = 1 ; partition <= a[i] ; partition++) {
                        int left = a[i] - partition;
                        long ptn = base * ncr[left+partition-1][partition-1] % MOD;
                        dp[to][partition] += ptn;
                        dp[to][partition] %= MOD;
                    }
                }
            }
        }

        long ret = 0;
        for (int i = 1 ; i < dp[0].length ; i++) {
            ret += dp[n%2][i];
        }
        return (int)(ret % MOD);
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
