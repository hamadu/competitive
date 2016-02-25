package codeforces.cr343.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/02/24.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[][] cake = new long[n][2];
        for (int i = 0; i < n ; i++) {
            long r = in.nextInt();
            long h = in.nextInt();
            cake[i][0] = r * r * h;
            cake[i][1] = i;
        }
        Arrays.sort(cake, (o1, o2) -> Long.compare(o2[0], o1[0]));

        SegmentTree dp = new SegmentTree(n+10);

        long[] upd = new long[n];
        for (int i = 0 ; i < n ;) {
            int fr = i;
            int to = i;
            while (to < n && cake[to][0] == cake[fr][0]) {
                to++;
            }
            for (int f = fr ; f < to ; f++) {
                int idx = (int) cake[f][1];
                upd[f] = cake[f][0] + dp.range(idx+1, n);
            }
            for (int f = fr ; f < to ; f++) {
                int idx = (int) cake[f][1];
                dp.update(idx, upd[f]);
            }
            i = to;
        }
        out.println(String.format("%.9f", dp.range(0, n) * Math.PI));
        out.flush();
    }

    static class SegmentTree {
        long[] data;
        int N;
        int M;

        public SegmentTree(int n) {
            N = Integer.highestOneBit(n)<<2;
            M = N / 2 - 1;
            data = new long[N];
        }

        public void update(int idx, long v) {
            int i = M + idx;
            data[i] = v;
            while (i >= 1) {
                int pi = (i - 1) / 2;
                data[pi] = Math.max(data[pi*2+1], data[pi*2+2]);
                i = pi;
            }
        }

        public long range(int fr, int to) {
            return range(fr, to, 0, 0, N / 2);
        }

        private long range(int fr, int to, int idx, int l, int r) {
            if (to <= l || r <= fr) {
                return 0;
            } else if (fr <= l && r <= to) {
                return data[idx];
            }
            int med = (l + r) / 2;
            long left = range(fr, to, idx*2+1, l, med);
            long right = range(fr, to, idx*2+2, med, r);
            return Math.max(left, right);
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
                res += c-'0';
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
                res += c-'0';
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
