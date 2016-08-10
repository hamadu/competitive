package csacademy.round010;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/08/10.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt()-1;
        }

        int[] has = new int[n];
        Arrays.fill(has, 1);

        int[] gprev = new int[n];
        int[] gnext = new int[n];
        for (int i = 0; i < n ; i++) {
            gprev[i] = i-1;
            gnext[i] = i+1;
        }

        long ans = 0;
        int ghead = 0;
        int gtail = n-1;
        for (int i = 0 ; i < n ; i++) {
            int[] prev = gprev.clone();
            int[] next = gnext.clone();
            int head = ghead;
            int med = head;
            for (int k = 0 ; k < (n-i)/2 ; k++) {
                med = next[med];
            }
            for (int j = n-1 ; j >= i+2 ; j--) {
                int rem = a[j];
                cut(rem, prev, next);

                if ((j - i) % 2 == 0) {
                    ans += (long)(i+1)*(j+1)*(med+1);
                    if (med >= rem) {
                        med = next[med];
                    }
                } else {
                    if (med <= rem) {
                        med = prev[med];
                    }
                }
            }
            has[a[i]] = 0;
            while (ghead < n && has[ghead] == 0) {
                ghead++;
            }
            while (gtail >= 0 && has[gtail] == 0) {
                gtail--;
            }
            cut(a[i], gprev, gnext);
        }

        for (int i = 0 ; i < n ; i++) {
            ans += (long)(i+1)*(i+1)*(a[i]+1);
        }
        out.println(ans);
        out.flush();
    }

    private static void cut(int val, int[] gprev, int[] gnext) {
        int vnext = gnext[val];
        int vprev = gprev[val];
        if (vnext != gnext.length) {
            gprev[vnext] = vprev;
        }
        if (vprev != -1) {
            gnext[vprev] = vnext;
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
