package aoj.vol12;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/30.
 */
public class P1252 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        while (true) {
            int n = in.nextInt();
            if (n == 0) {
                break;
            }
            int d = in.nextInt();
            char[][] words = new char[n][];
            for (int i = 0; i < n ; i++) {
                words[i] = in.nextToken().toCharArray();
            }
            solve(out, words, d);
        }
        out.flush();
    }

    private static void solve(PrintWriter out, char[][] words, int d) {
        int n = words.length;
        String[] str = new String[n];
        for (int i = 0; i < n ; i++) {
            str[i] = String.valueOf(words[i]);
        }
        Arrays.sort(str);

        char[][] rw = new char[n][];
        for (int i = 0; i < n ; i++) {
            rw[i] = str[i].toCharArray();
        }

        int cnt = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = i+1; j < n ; j++) {
                if (isSimilar(str[i].toCharArray(), str[j].toCharArray(), d)) {
                    out.println(str[i] + "," + str[j]);
                    cnt++;
                }
            }
        }
        out.println(cnt);
    }

    static final int INF = 114514;
    static int[][] dp = new int[32][32];

    private static boolean isSimilar(char[] w1, char[] w2, int d) {
        int n1 = w1.length;
        int n2 = w2.length;
        for (int i = 0; i <= n1; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = 0;
        for (int i = 0; i <= n1; i++) {
            for (int j = 0; j <= n2 ; j++) {
                if (dp[i][j] > d) {
                    continue;
                }
                int base = dp[i][j];
                if (i < n1) {
                    dp[i+1][j] = Math.min(dp[i+1][j], base+1);
                }
                if (j < n2) {
                    dp[i][j+1] = Math.min(dp[i][j+1], base+1);
                }
                if (i < n1 && j < n2) {
                    int co = (w1[i] == w2[j]) ? 0 : 1;
                    dp[i+1][j+1] = Math.min(dp[i+1][j+1], base+co);
                }
            }
        }
        if (dp[n1][n2] <= d) {
            return true;
        }
        if (Math.abs(w1.length - w2.length) >= 2) {
            return false;
        }
        return dfs(w1, w2, d);
    }

    static void swap(char[] w, int idx) {
        char tmp = w[idx];
        w[idx] = w[idx+1];
        w[idx+1] = tmp;
    }

    static boolean dfs(char[] w1, char[] w2, int d) {
        if (d == 0) {
            if (w1.length != w2.length) {
                return false;
            }
            for (int i = 0; i < w1.length ; i++) {
                if (w1[i] != w2[i]) {
                    return false;
                }
            }
            return true;
        }
        if (d == 1) {
            int wc = 0;
            if (w1.length == w2.length) {
                for (int i = 0; i < w1.length ; i++) {
                    if (w1[i] != w2[i]) {
                        wc++;
                    }
                }
            } else if (w1.length < w2.length) {
                for (int i = 0; i < w1.length ; i++) {
                    if (w1[i] != w2[i+wc]) {
                        wc++;
                        i--;
                        if (wc >= 2) {
                            break;
                        }
                    }
                }
            } else {
                for (int i = 0; i < w2.length ; i++) {
                    if (w1[i+wc] != w2[i]) {
                        wc++;
                        i--;
                        if (wc >= 2) {
                            break;
                        }
                    }
                }
            }
            if (wc <= 1) {
                return true;
            }
        }
        boolean isOK = false;
        for (int i = 0; i < w1.length-1 ; i++) {
            swap(w1, i);
            isOK |= dfs(w1, w2, d-1);
            swap(w1, i);
        }
        for (int i = 0; i < w2.length-1 ; i++) {
            swap(w2, i);
            isOK |= dfs(w1, w2, d-1);
            swap(w2, i);
        }
        return isOK;
    }

    static class Pair implements Comparable<Pair> {
        String a;
        String b;

        Pair(String c, String d) {
            if (c.compareTo(d) < 0) {
                a = c;
                b = d;
            } else {
                a = d;
                b = c;
            }
        }

        public String toString() {
            return String.format("%s,%s", a, b);
        }

        @Override
        public int compareTo(Pair o) {
            if (a.equals(o.a)) {
                return b.compareTo(o.b);
            }
            return a.compareTo(o.a);
        }
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int next() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public char nextChar() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            if ('a' <= c && c <= 'z') {
                return (char) c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char) c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char) c);
                c = next();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public int nextInt() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = next();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = next();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public long nextLong() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            long sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = next();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = next();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
