package codeforces.educational.ecf010;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class F {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long m = in.nextInt();
        long t = in.nextLong();

        Ant[] ants = new Ant[n];
        for (int i = 0; i < n ; i++) {
            ants[i] = new Ant(i, in.nextInt() % m, in.nextToken().toCharArray()[0] == 'R' ? 1 : -1);
        }
        Arrays.sort(ants, (a, b) -> Long.compare(a.pos, b.pos));

        long collision = 0;
        long cur = t / m;
        long mod = (t % m) * 2;
        long per = 0;
        for (int i = 1 ; i < n ; i++) {
            if (ants[0].dir != ants[i].dir) {
                per++;
                long diff2 = (ants[0].dir == 1) ? ants[i].pos - ants[0].pos : ants[0].pos + m - ants[i].pos;
                if (diff2 < mod) {
                    collision++;
                    if (diff2 + m < mod) {
                        collision++;
                    }
                }
            }
        }

        collision += ((per % n) * (cur % n) * 2) % n;
        collision %= n;

        if (ants[0].dir == -1) {
            collision = (n - collision) % n;
        }

        long[][] p3 = new long[n][3];
        for (int i = 0; i < n ; i++) {
            p3[i][0] = ants[i].pos + ants[i].dir * t;
            if (p3[i][0] < 0) {
                p3[i][0] *= -1;
                p3[i][0] %= m;
                p3[i][0] = (m - p3[i][0]) % m;
            } else {
                p3[i][0] %= m;
            }
            p3[i][1] = i;
            p3[i][2] = ants[i].dir == 1 ? 0 : 1;
        }
        Arrays.sort(p3, (a, b) -> (a[0] != b[0]) ? Long.compare(a[0], b[0]) : Long.compare(a[2], b[2]));

        long[] ans = new long[n];
        for (int i = 0 ; i < n ; i++) {
            if (p3[i][1] == 0) {
                for (int j = 0 ; j < n ; j++) {
                    int idx = (int)((collision + j) % n);
                    ans[ants[idx].idx] = p3[(i+j)%n][0];
                    if (ans[ants[idx].idx] == 0) {
                        ans[ants[idx].idx] = m;
                    }
                }
                break;
            }
        }

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < n ; i++) {
            line.append(' ').append(ans[i]);
        }
        out.println(line.substring(1));
        out.flush();
    }

    static class Ant {
        int idx;
        long pos;
        int dir;

        public Ant(int i, long p, int d) {
            idx = i;
            pos = p;
            dir = d;
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
