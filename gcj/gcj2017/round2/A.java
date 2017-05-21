package gcj.gcj2017.round2;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();

            int p = in.nextInt();
            int[] g = new int[n];
            for (int i = 0; i < n ; i++) {
                g[i] = in.nextInt();
            }
            out.println(String.format("Case #%d: %d", cs, solve(p, g)));
        }
        out.flush();
    }

    private static int solve(int p, int[] g) {
        int n = g.length;
        int[] modP = new int[p];
        for (int i = 0 ; i < n ; i++) {
            modP[g[i]%p]++;
        }

        switch (p) {
            case 2:
                return solve2(modP);
            case 3:
                return solve3(modP);
            case 4:
                return solve4Sub(modP);
        }
        throw new RuntimeException("arien");
    }
    private static int solve4Sub(int[] modP) {
        int max = 0;
        for (int m23to1 = 0 ; m23to1 <= Math.min(modP[2], modP[3]) ; m23to1++) {
            int[] tmo = modP.clone();
            tmo[1] += m23to1;
            tmo[2] -= m23to1;
            tmo[3] -= m23to1;
            max = Math.max(max, solve4(tmo));
        }
        return max;
    }

    private static int solve4(int[] modP) {
        int ret = modP[0];
        int m13 = Math.min(modP[1], modP[3]);
        ret += m13;
        modP[1] -= m13;
        modP[3] -= m13;

        if (modP[3] >= 4) {
            ret += modP[3] / 4;
            modP[3] %= 4;
        }

        int otherMax = 0;
        for (int m1111 = 0 ; m1111 <= modP[1] ; m1111++) {
            for (int m112 = 0 ; m112 <= modP[2] ; m112++) {
                for (int m22 = 0 ; m22 <= modP[2] ; m22++) {
                    int one = m1111 * 4 + m112 * 2;
                    int two = m112 + m22 * 2;
                    if (one <= modP[1] && two <= modP[2]) {
                        int leftOne = modP[1] - one;
                        int leftTwo = modP[2] - two;
                        int leftThree = modP[3];
                        int cnt = m1111 + m112 + m22;
                        if (leftOne >= 1 || leftTwo >= 1 || leftThree >= 1) {
                            cnt++;
                        }
                        otherMax = Math.max(otherMax, cnt);
                    }
                }
            }
        }
        return ret + otherMax;
    }

    private static int solve3(int[] modP) {
        int ret = modP[0];
        int m12 = Math.min(modP[1], modP[2]);
        ret += m12;
        modP[1] -= m12;
        modP[2] -= m12;

        if (modP[1] >= 1) {
            ret += (modP[1] + 2) / 3;
        }
        if (modP[2] >= 1) {
            ret += (modP[2] + 2) / 3;
        }
        return ret;
    }

    private static int solve2(int[] modP) {
        return modP[0] + (modP[1] + 1) / 2;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



