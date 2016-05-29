package gcj.gcj2015.round3;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class D {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            long[] num = new long[n];
            long[] cnt = new long[n];
            for (int i = 0; i < n ; i++) {
                num[i] = in.nextLong();
            }
            for (int i = 0; i < n ; i++) {
                cnt[i] = in.nextLong();
            }
            out.println(String.format("Case #%d: %s", cs, solve(num, cnt)));
        }
        out.flush();
    }

    private static String solve(long[] nu, long[] cn) {
        p = nu.length;
        num = nu;
        cnt = cn;
        ln.clear();
        long total = Arrays.stream(cnt).sum();
        int n = Long.numberOfTrailingZeros(total);
        long[] ans = new long[n];
        for (int i = 0; i < p ; i++) {
            ln.put(nu[i], i);
        }

        int ai = 0;
        for (int j = 0; j < p ; j++) {
            while (cnt[j] >= 1 && ai < n) {
                if (isOK(j)) {
                    ans[ai] = num[j];
                    ai++;
                } else {
                    break;
                }
            }
        }

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < n ; i++) {
            line.append(' ').append(ans[i]);
        }
        return line.substring(1);
    }

    private static boolean isOK(int j) {
        long[] rev = cnt.clone();
        long n = num[j];
        boolean ok = true;
        if (n < 0) {
            for (int i = p-1 ; i >= 0 ; i--) {
                if (cnt[i] == 0) {
                    continue;
                }
                long to = num[i] + n;
                int tidx = ln.getOrDefault(to, -1);
                if (tidx < 0 || cnt[tidx] < cnt[i]) {
                    ok = false;
                    break;
                }
                cnt[tidx] -= cnt[i];
            }
        } else if (n > 0) {
            for (int i = 0; i < p ; i++) {
                if (cnt[i] == 0) {
                    continue;
                }
                long to = num[i] + n;
                int tidx = ln.getOrDefault(to, -1);
                if (tidx < 0 || cnt[tidx] < cnt[i]) {
                    ok = false;
                    break;
                }
                cnt[tidx] -= cnt[i];
            }
        } else {
            for (int i = 0; i < p ; i++) {
                if (cnt[i] % 2 == 0) {
                    cnt[i] /= 2;
                } else {
                    ok = false;
                    break;
                }
            }
        }
        if (!ok) {
            cnt = rev;
        }
        return ok;
    }

    static int p;
    static long[] num;
    static long[] cnt;
    static Map<Long,Integer> ln = new HashMap<>();

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



