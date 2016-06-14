package gcj.gcj2016.round3;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by hama_du on 2016/06/10.
 */
public class D {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            int l = in.nextInt();
            char[][] good = new char[n][];
            for (int i = 0; i < n ; i++) {
                good[i] = in.next().toCharArray();
            }
            char[] bad = in.next().toCharArray();
            char[][] res = solve(good, bad);
            if (res == null) {
                out.println(String.format("Case #%d: IMPOSSIBLE", cs));
            } else {
                out.println(String.format("Case #%d: %s %s", cs, String.valueOf(res[0]), String.valueOf(res[1])));
            }

        }
        out.flush();
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    private static char[][] solve(char[][] good, char[] bad) {
        int l = bad.length;
        for (char[] g : good) {
            if (String.valueOf(g).equals(String.valueOf(bad))) {
                return null;
            }
        }
        if (l == 1) {
            if (bad[0] == '1') {
                return new char[][]{{'?'}, {'0'}};
            } else {
                return new char[][]{{'1', '?'}, {'1'}};
            }
        }

        char[] a = new char[l*2-2];
        char[] b = new char[l*2];
        for (int i = 0; i < l-1 ; i++) {
            if (bad[i] == '0') {
                a[i*2] = '1';
                a[i*2+1] = '0';
            } else {
                a[i*2] = '0';
                a[i*2+1] = '1';
            }
        }
        for (int i = 0 ; i < l ; i++) {
            if (bad[i] == '0') {
                b[i*2] = '1';
            } else {
                b[i*2] = '0';
            }
            b[i*2+1] = '?';
        }
        return new char[][]{a, b};
    }
}
