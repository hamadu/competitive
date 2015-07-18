package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by hama_du on 15/07/17.
 */
public class P1328 {
    static final double EPS = 1e-9;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int d = in.nextInt();
            if (d == 0) {
                break;
            }
            double[] v = new double[d+3];
            for (int i = 0; i < d+3 ; i++) {
                v[i] = in.nextDouble();
            }
            out.println(solve(d, v));
        }
        out.flush();
    }

    private static int solve(int d, double[] values) {
        int n = values.length;
        for (int rm1 = 0 ; rm1 < n ; rm1++) {
            for (int rm2 = rm1+1; rm2 < n ; rm2++) {
                double[][] table = new double[d+1][d+1];
                double[] ans = new double[d+1];
                int ki = 0;
                for (int x = 0; x < n ; x++) {
                    if (x != rm1 && x != rm2) {
                        ans[ki] = values[x];
                        int k = 1;
                        for (int di = 0; di <= d; di++) {
                            table[ki][di] = k;
                            k *= x;
                        }
                        ki++;
                    }
                }

                double[] vec = hakidasi(table, ans);
                if (vec.length == 0) {
                    continue;
                }

                int correct = 0;
                int wrong = -1;
                for (int i = 0; i < n ; i++) {
                    if (diff(vec, i, values[i]) < 1e-5) {
                        correct++;
                    } else {
                        wrong = i;
                    }
                }

                if (correct == n-1) {
                    if (wrong == rm1) {
                        return rm1;
                    }
                    if (wrong == rm2) {
                        return rm2;
                    }
                }
            }
        }
        return -1;
    }



    private static double compute(double[] vec, int x) {
        double v = 0;
        int k = 1;
        for (int i = 0; i < vec.length ; i++) {
            v += vec[i] * k;
            k *= x;
        }
        return v;
    }

    private static double diff(double[] vec, int x, double value) {
        double v = compute(vec, x);
        return Math.abs(value - v);
    }

    public static double[] hakidasi(double[][] r1, double[] r2) {
        int len = r1.length;
        double[][] B = new double[len][len+1];
        for (int i = 0 ; i < len ; i++) {
            for (int j = 0 ; j < len ; j++) {
                B[i][j] = r1[i][j];
            }
        }
        for (int i = 0 ; i < len ; i++) {
            B[i][len] = r2[i];
        }

        for (int i = 0 ; i < len ; i++) {
            int pv = i;
            for (int j = i ; j < len ; j++) {
                if (Math.abs(B[j][i]) > Math.abs(B[pv][i])) {
                    pv = j;
                }
            }
            double[] tmp = B[i].clone();
            B[i] = B[pv].clone();
            B[pv] = tmp;
            if (Math.abs(B[i][i]) < EPS) {
                return new double[]{};
            }

            for (int j = i + 1 ; j <= len ; j++) {
                B[i][j] /= B[i][i];
            }
            for (int j = 0 ; j < len ; j++) {
                if (i != j) {
                    for (int k = i + 1 ; k <= len ; k++) {
                        B[j][k] -= B[j][i] * B[i][k];
                    }
                }
            }
        }

        double[] ret = new double[len];
        for (int i = 0 ; i < len ; i++) {
            ret[i] = B[i][len];
        }
        return ret;
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
