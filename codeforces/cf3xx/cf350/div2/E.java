package codeforces.cf3xx.cf350.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Stack;

/**
 * Created by hama_du on 2016/08/05.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        in.nextInt();
        in.nextInt();
        int p = in.nextInt()-1;
        char[] bra = in.nextToken().toCharArray();
        char[] ope = in.nextToken().toCharArray();
        int n = bra.length;
        int m = ope.length;


        int[] prev = new int[n];
        int[] next = new int[n];
        int[] pair = new int[n];
        for (int i = 0 ; i < n ; i++) {
            prev[i] = i-1;
            next[i] = i+1;
        }
        {
            Stack<Integer> stk = new Stack<>();
            for (int i = 0; i < n; i++) {
                if (bra[i] == '(') {
                    stk.push(i);
                } else {
                    int j = stk.pop();
                    pair[i] = j;
                    pair[j] = i;
                }
            }
        }

        int[] rem = new int[n+1];
        for (int i = 0 ; i < m ; i++) {
            if (ope[i] == 'L') {
                p = prev[p];
            } else if (ope[i] == 'R') {
                p = next[p];
            } else {
                int top = pair[p];
                p = Math.min(p, top);
                int q = pair[p];
                rem[p]++;
                rem[q+1]--;
                int pp = prev[p];
                int qq = next[q];
                // debug("remo",pp,p,q,qq);

                if (pp != -1) {
                    next[pp] = qq;
                }
                if (qq != n) {
                    prev[qq] = pp;
                }
                if (qq < n) {
                    p = qq;
                } else {
                    p = pp;
                }
            }
        }
        for (int i = 0; i < n ; i++) {
            rem[i+1] += rem[i];
        }
        for (int i = 0 ; i < n ; i++) {
            if (rem[i] == 0) {
                out.print(bra[i]);
            }
        }
        out.println();
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
