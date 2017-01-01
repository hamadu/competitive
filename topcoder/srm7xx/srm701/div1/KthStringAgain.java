package topcoder.srm7xx.srm701.div1;

import java.util.Arrays;

public class KthStringAgain {
    public String getKth(String s, long k) {
        n = s.length();
        S = s.toCharArray();

        k--;

        String current = "";
        for (int i = 0 ; i < n ; i++) {
            for (char c = 'a' ; c <= 'z' ; c++) {
                long cnt = count(current + c);
                if (k < cnt) {
                    current += c;
                    break;
                } else {
                    k -= cnt;
                }
            }
        }
        return current;
    }

    public char[] S;

    public int n;

    public int pn;

    public long count(String prefix) {
        int m = prefix.length();

        long[][] dp = new long[n][n];
        for (int i = 0; i < n ; i++) {
            dp[i][i] = (i < m && prefix.charAt(i) != S[n-1]) ? 0 : 2;
        }

        for (int d = 1 ; d < n; d++) {
            char c = S[n-d-1];
            for (int h = 0 ; h+d-1 < n ; h++) {
                int t = h+d-1;
                if (dp[h][t] == 0) {
                    continue;
                }
                long base = dp[h][t];
                if (h-1 >= 0) {
                    if (h-1 < m && prefix.charAt(h-1) != c) {
                    } else {
                        dp[h-1][t] += base;
                    }
                }
                if (t+1 < n) {
                    if (t+1 < m && prefix.charAt(t+1) != c) {
                    } else {
                        dp[h][t+1] += base;
                    }
                }
            }
        }

        return dp[0][n-1];
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
