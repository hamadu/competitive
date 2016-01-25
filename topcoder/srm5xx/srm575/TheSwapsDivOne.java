package topcoder.srm5xx.srm575;

import java.util.Arrays;

/**
 * Created by hama_du on 15/09/13.
 */
public class TheSwapsDivOne {
    public double find(String[] sequence, int k) {
        int[] num = build(sequence);
        int[] deg = new int[10];
        for (int i = 0; i < num.length ; i++) {
            deg[num[i]]++;
        }
        double n = num.length;
        double pair = n*(n-1)/2;
        double[][] conv = new double[10][10];
        for (int to = 0; to < 10 ; to++) {
            for (int fr = 0; fr < 10 ; fr++) {
                if (to == fr) {
                    conv[to][fr] = (deg[to]-1) / pair + (n-1) * (n-2) / 2 / pair;
                } else {
                    conv[to][fr] = deg[to] / pair;
                }
            }
        }
        double[][] mat = pow(conv, k);
        double[] ex = new double[10];
        for (int fr = 0; fr < 10 ; fr++) {
            for (int to = 0; to < 10 ; to++) {
                ex[fr] += to * mat[to][fr];
            }
        }
        double[] imos = new double[num.length+1];
        for (int i = 0; i < num.length ; i++) {
            imos[i+1] = imos[i] + ex[num[i]];
        }
        double all = 0;
        int cnt = 0;
        for (int i = 0; i < num.length ; i++) {
            for (int j = i+1 ; j <= num.length ; j++) {
                all += imos[j] - imos[i];
                cnt++;
            }
        }
        return all / cnt;
    }

    public static double[][] pow(double[][] a, long n) {
        long i = 1;
        double[][] res = E(a.length);
        double[][] ap = mul(E(a.length), a);
        while (i <= n) {
            if ((n & i) >= 1) {
                res = mul(res, ap);
            }
            i *= 2;
            ap = mul(ap, ap);
        }
        return res;
    }

    public static double[][] E(int n) {
        double[][] a = new double[n][n];
        for (int i = 0 ; i < n ; i++) {
            a[i][i] = 1;
        }
        return a;
    }

    public static double[][] mul(double[][] a, double[][] b) {
        double[][] c = new double[a.length][b[0].length];
        if (a[0].length != b.length) {
            System.err.print("err");
        }
        for (int i = 0 ; i < a.length ; i++) {
            for (int j = 0 ; j < b[0].length ; j++) {
                double sum = 0;
                for (int k = 0 ; k < a[0].length ; k++) {
                    sum = (sum + a[i][k] * b[k][j]);
                }
                c[i][j] = sum;
            }
        }
        return c;
    }

    private int[] build(String[] sequence) {
        StringBuilder line = new StringBuilder();
        for (String s : sequence) {
            line.append(s);
        }
        char[] c = line.toString().toCharArray();
        int L = c.length;
        int[] x = new int[L];
        for (int i = 0; i < L ; i++) {
            x[i] = c[i]-'0';
        }
        return x;
    }
}
