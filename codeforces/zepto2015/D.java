package codeforces.zepto2015;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class D {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        char[] c = in.next().toCharArray();

        debug(errorFunction(c));

        out.flush();
    }


    public static int[] errorFunction(char[] carr) {
        int len = carr.length;
        int[] err = new int[len+1];
        err[0] = -1;
        for (int i = 2 ; i <= len ; i++) {
            int now = err[i-1];
            while (now > 0 && carr[i-1] != carr[now]) {
                now = err[now];
            }
            if (carr[i-1] == carr[now]) {
                now++;
            } else {
                now = 0;
            }
            err[i] = now;
        }
        return err;
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



