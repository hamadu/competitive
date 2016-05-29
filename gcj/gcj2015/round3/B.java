package gcj.gcj2015.round3;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class B {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            int k = in.nextInt();
            int[] s = new int[n-k+1];
            for (int i = 0; i < n-k+1 ; i++) {
                s[i] = in.nextInt();
            }
            out.println(String.format("Case #%d: %s", cs, solve(n, k, s)));
        }
        out.flush();
    }

    private static long solve(int n, int k, int[] s) {
        long[] min = new long[k];
        long[] max = new long[k];
        for (int i = 0; i < k ; i++) {
            long val = 0;
            int head = i;
            while (head+1 < s.length) {
                val += s[head+1] - s[head];
                min[i] = Math.min(min[i], val);
                max[i] = Math.max(max[i], val);
                head += k;
            }
        }

        long want = s[0];
        long lw = -10000000;
        for (int i = 0; i < k ; i++) {
            want += min[i] - lw;
        }
        long mod = want % k;
        long maxDiff = 0;
        for (int i = 0; i < k ; i++) {
            maxDiff = Math.max(maxDiff, max[i] - min[i]);
        }
        long freedom = 0;
        for (int i = 0; i < k ; i++) {
            freedom += maxDiff - (max[i] - min[i]);
        }
        if (mod <= freedom) {
            return maxDiff;
        }
        return maxDiff+1;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



