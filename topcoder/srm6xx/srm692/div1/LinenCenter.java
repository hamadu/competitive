package topcoder.srm6xx.srm692.div1;

import java.io.*;
import java.util.*;

public class LinenCenter {
    private static final int MOD = 1000000009;

    public int countStrings(String S, int N, int K) {
        int L = S.length();
        int[][] pam = new int[L+1][26];
        Map<String, Integer> prefixToInt = new HashMap<>();
        prefixToInt.put("", 0);
        for (int i = 0 ; i < L ; i++) {
            prefixToInt.put(S.substring(0, i+1), i+1);
        }
        for (int i = 0; i <= L ; i++) {
            for (char c = 'a' ; c <= 'z' ; c++) {
                String prefix = S.substring(0, i) + c;
                for (int l = 0 ; l <= prefix.length() ; l++) {
                    String to = prefix.substring(l);
                    if (prefixToInt.containsKey(to)) {
                        pam[i][c-'a'] = prefixToInt.get(to);
                        break;
                    }
                }
            }
        }

        boolean[] isSafe = new boolean[L];
        for (int i = 0; i < L ; i++) {
            int head = i;
            boolean touched = false;
            for (int j = 0 ; j < S.length()-1 ; j++) {
                int c = S.charAt(j)-'a';
                head = pam[head][c];
                if (head == L) {
                    touched = true;
                }
            }
            isSafe[i] = !touched;
        }

        int[][][] tbl = new int[L][L][2];
        for (int i = 0; i < L ; i++) {
            for (int j = 0; j < L ; j++) {
                tbl[i][j][0] = j;
            }
            for (int c = 0; c < 26 ; c++) {
                if (pam[i][c] < L) {
                    tbl[i][pam[i][c]][1] += 1;
                }
            }
            Arrays.sort(tbl[i], (a, b) -> b[1] - a[1]);
        }
        int[] tbln = new int[L];
        for (int i = 0; i < L ; i++) {
            tbln[i] = L;
            for (int j = 0; j < L ; j++) {
                if (tbl[i][j][1] == 0) {
                    tbln[i] = j;
                    break;
                }
            }
        }

        int[][] dp = new int[N+1][L];
        dp[0][0] = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < L ; j++) {
                for (int c = 0 ; c < 26 ; c++) {
                    int to = pam[j][c];
                    if (to < L) {
                        dp[i+1][to] += dp[i][j];
                        dp[i+1][to] -= dp[i+1][to] >= MOD ? MOD : 0;
                    }
                }
            }
        }

        long[] last = new long[N+1];
        long[] nonLast = new long[N+1];
        for (int i = 0; i <= N ; i++) {
            for (int j = 0; j < L; j++) {
                if (isSafe[j]) {
                    nonLast[i] += dp[i][j];
                    nonLast[i] %= MOD;
                }
                last[i] += dp[i][j];
                last[i] %= MOD;
            }
        }

        long[] kk = nonLast.clone();
        long[] ans = new long[N+1];
        ans[0] = 1;

        for (int k = 0 ; k < 30 ; k++) {
            if ((K & (1<<k)) >= 1) {
                ans = dpdp(ans, kk);
            }
            kk = dpdp(kk, kk);
        }


        ans = dpdp(ans, last);
        long ret = 0;
        for (int i = 0; i <= N; i++) {
            ret += ans[i];
        }
        ret %= MOD;
        return (int)ret;
    }

    static long[] dpdp(long[] a, long[] b) {
        int n = a.length;
        long[] c = new long[n];
        for (int i = 0; i < n ; i++) {
            long sum = 0;
            for (int j = 0; j <= i ; j++) {
                sum += a[j] * b[i-j] % MOD;
            }
            c[i] = sum % MOD;
        }
        return c;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
