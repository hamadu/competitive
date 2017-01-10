package facebook.fhc2017.qual;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Loading {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int n = in.nextInt();
            int [] w = new int[n];
            for (int i = 0; i < n ; i++) {
                w[i] = in.nextInt();
            }
            out.println(String.format("Case #%d: %d", c, solve(w)));
        }
        out.flush();
    }

    private static int solve(int[] w) {
        Arrays.sort(w);

        int n = w.length;
        int head = 0;
        int cur = 0;
        for (int i = n-1 ; i >= head ; i--) {
            int left = 50-w[i];
            if (left <= 0) {
                cur++;
                continue;
            }
            int needToTake = (left+w[i]-1)/w[i];
            head += needToTake;
            if (head > i) {
                break;
            }
            cur++;
        }
        return cur;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
