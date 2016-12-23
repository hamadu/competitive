package codeforces.cf3xx.cf386.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] a = new int[n][2];
        for (int i = 0; i < n ; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = i;
        }
        Arrays.sort(a, (u, v) -> u[0] - v[0]);
        m = Math.min(m, 400000);

        int[][] available = new int[2][800000];
        int[] an = new int[2];
        int li = 1;
        for (int i = 0 ; i < n ; i++) {
            int A = a[i][0];
            while (li <= m && li < A) {
                int eo = li%2;
                available[eo][an[eo]++] = li;
                li++;
            }
            if (li <= m && li == A) {
                li++;
            }
        }
        while (li <= m) {
            int eo = li%2;
            available[eo][an[eo]++] = li;
            li++;
        }

        int[] eocnt = new int[2];
        for (int i = 0; i < n; i++) {
            eocnt[a[i][0]%2]++;
        }


        boolean possible = true;
        int changed = 0;
        int[] ai = new int[2];
        for (int i = 0 ; i < n ; ) {
            int j = i;
            while (j < n && a[i][0] == a[j][0]) {
                j++;
            }
            int my = a[i][0]%2;
            int ot = 1-my;

            int ef = i+1;
            while (ef < j) {
                int want = eocnt[my] <= eocnt[ot] ? my : ot;

                if (ai[want] < an[want]) {
                    changed++;
                    eocnt[my]--;
                    eocnt[want]++;
                    a[ef][0] = available[want][ai[want]++];
                } else {
                    possible = false;
                }
                ef++;
            }
            i = j;
        }

        if (eocnt[0] != eocnt[1]) {
            int want = eocnt[0] < eocnt[1] ? 0 : 1;
            int my = 1-want;
            for (int i = 0; i < n ; i++) {
                if (a[i][0] % 2 == want) {
                    continue;
                }
                if (ai[want] < an[want]) {
                    changed++;
                    eocnt[my]--;
                    eocnt[want]++;
                    a[i][0] = available[want][ai[want]++];
                } else {
                    possible = false;
                }
                if (eocnt[0] == eocnt[1]) {
                    break;
                }
            }
        }


        if (!possible) {
            out.println(-1);
        } else {
            Arrays.sort(a, (u, v) -> u[1] - v[1]);
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < n ; i++) {
                line.append(' ').append(a[i][0]);
            }
            out.println(changed);
            out.println(line.substring(1));
        }



        out.flush();
    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }

        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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
                res += c-'0';
                c = next();
            } while (!isSpaceChar(c));
            return res*sgn;
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
                res += c-'0';
                c = next();
            } while (!isSpaceChar(c));
            return res*sgn;
        }

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
