package codeforces.cf3xx.cf347.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String token = in.nextLine();
        String ans = solve(token);
        if (ans == null) {
            out.println("Impossible");
        } else {
            out.println("Possible");
            out.println(ans);
        }
        out.flush();
    }

    private static String solve(String token) {
        String[] part = token.split("=");
        int n = Integer.valueOf(part[1].trim());
        int right = n;
        int plus = 1;
        int minus = 0;
        for (int i = 0; i < part[0].length() ; i++) {
            if (part[0].charAt(i) == '+') {
                plus++;
            } else if (part[0].charAt(i) == '-') {
                minus++;
            }
        }

        int pmax = (right + minus + plus - 1) / plus;
        if (pmax > n) {
            return null;
        }

        int pmaxCnt = (right + minus) % plus == 0 ? plus : (right + minus) % plus;
        int qmax = 1;
        int qmaxCnt = minus;
        if (pmax == 1 && pmaxCnt < plus) {
            if (minus == 0) {
                return null;
            }
            // can make it?
            int need = plus - pmaxCnt;
            qmax = 1 + (need + minus - 1) / minus;
            qmaxCnt = need % minus == 0 ? minus : need % minus;
            if (qmax > n) {
                return null;
            }
            // yattaze.
            pmaxCnt = plus;
        }

        StringBuilder line = new StringBuilder();

        boolean pl = true;
        for (int i = 0; i < token.length() ; i++) {
            if (token.charAt(i) == '?') {
                if (pl) {
                    if (pmaxCnt >= 1) {
                        pmaxCnt--;
                        line.append(pmax);
                    } else {
                        line.append(pmax-1);
                    }
                } else {
                    if (qmaxCnt >= 1) {
                        qmaxCnt--;
                        line.append(qmax);
                    } else {
                        line.append(qmax-1);
                    }
                }
            } else {
                if (token.charAt(i) == '+') {
                    pl = true;
                } else if (token.charAt(i) == '-') {
                    pl = false;
                }
                line.append(token.charAt(i));
            }
        }
        return line.toString();
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
