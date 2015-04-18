package gcj2015.qual;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class C {

    // 1, i, j, k, -1, -i, -j, -k
    final static int[][] table = {
        {0, 1, 2, 3, 4, 5, 6, 7},
        {1, 4, 3, 6, 5, 0, 7, 2},
        {2, 7, 4, 1, 6, 3, 0, 5},
        {3, 2, 5, 4, 7, 6, 1, 0},

        {4, 5, 6, 7, 0, 1, 2, 3},
        {5, 0, 7, 2, 1, 4, 3, 6},
        {6, 3, 0, 5, 2, 7, 4, 1},
        {7, 6, 1, 0, 3, 2, 5, 4},
    };

    static char[] ijk = new char[256];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        ijk['i'] = 1;
        ijk['j'] = 2;
        ijk['k'] = 3;

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int l = in.nextInt();
            long x = in.nextLong();
            char[] c = in.next().toCharArray();
            int[] nu = new int[c.length];
            for (int i = 0; i < l ; i++) {
                nu[i] = ijk[c[i]];
            }
            out.println(String.format("Case #%d: %s", cs, solve(nu, x)));
        }
        out.flush();
    }

    private static int pow(int a, long x) {
        int res = 0;
        while (x > 0) {
            if (x % 2 != 0) {
                res = table[res][a];
            }
            a = table[a][a];
            x /= 2;
        }
        return res;
    }

    private static String solve(int[] nu, long x) {
        int n = nu.length;
        int[] left = new int[n+1];
        int[] right = new int[n+1];
        for (int i = 0 ; i < n ; i++) {
            left[i+1] = table[left[i]][nu[i]];
        }
        for (int i = n-1 ; i >= 0 ; i--) {
            right[i] = table[nu[i]][right[i+1]];
        }
        int all = pow(left[n], x);
        if (all != 4) {
            return "NO";
        }

        long lx = x * n;

        long lp = -1;
        int lf = 0;
        for (int i = 0 ; i < 4*n ; i++) {
            lf = table[lf][nu[i%n]];
            if (lf == 1) {
                lp = i+1;
                break;
            }
        }

        long rp = -1;
        int rf = 0;
        for (int i = 0 ; i < 4*n ; i++) {
            int ti = 4*n-1-i;
            rf = table[nu[ti%n]][rf];
            if (rf == 3) {
                rp = i+1;
                break;
            }
        }
        if (lp == -1 || rp == -1 || lp + rp >= lx) {
            return "NO";
        }
        return "YES";
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



