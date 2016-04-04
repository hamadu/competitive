package codeforces.cf2xx.cr295.div1;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class B {
    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        char[] c = in.next().toCharArray();
        int[] cnt = new int[255];
        for (int i = 0 ; i < n ; i++) {
            cnt[c[i]]++;
        }
        int max = Math.max(Math.max(cnt['A'], cnt['C']), Math.max(cnt['G'], cnt['T']));
        int k = 0;
        for (char ct : new char[]{'A', 'C', 'G', 'T'}) {
            if (max == cnt[ct]) {
                k++;
            }
        }
        long ptn = 1;
        for (int i = 0 ; i < n ; i++) {
            ptn *= k;
            ptn %= MOD;
        }
        out.println(ptn);
        out.flush();
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



