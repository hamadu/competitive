package codeforces.cf3xx.cf353.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/07/18.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] tt = in.nextInts(n-1);
        int[] t0 = Arrays.copyOf(tt, n);
        t0[n-1] = n;

        SegmentTree tree = new SegmentTree(t0);

        long sum = 0;
        long[] part = new long[n];
        for (int k = n-2 ; k >= 0 ; k--) {
            int fr = k+1;
            int to = tt[k];

            int[] best = tree.max(fr, to);
            int x = best[1];

            part[k] += to - fr;
            part[k] += part[x] + (n - to);
            part[k] -= to - x - 1;
            sum += part[k];
        }

        out.println(sum);
        out.flush();
    }

    public static class SegmentTree {
        int N;
        int M;
        int[][] seg;

        public SegmentTree(int[] data) {
            N = Integer.highestOneBit(data.length-1)<<2;
            M = (N >> 1) - 1;

            seg = new int[N][2];
            for (int i = 0; i < N ; i++) {
                seg[i][0] = seg[i][1] = Integer.MIN_VALUE;
            }
            for (int i = 0 ; i < data.length ; i++) {
                seg[M+i][0] = data[i];
                seg[M+i][1] = i;
            }
            for (int i = M-1 ; i >= 0 ; i--) {
                seg[i] = compute(i);
            }
        }

        public void update(int idx, int[] value) {
            seg[M+idx] = value;
            int i = M+idx;
            while (true) {
                i = (i-1) >> 1;
                seg[i] = compute(i);
                if (i == 0) {
                    break;
                }
            }
        }

        public int[] compare(int[] a, int[] b) {
            if (a[0] > b[0] || (a[0] == b[0] && a[1] > b[1])) {
                return a;
            }
            return b;
        }

        public int[] compute(int i) {
            int l = i*2+1;
            int r = i*2+2;
            return compare(seg[l], seg[r]).clone();
        }

        public int[] max(int l, int r) {
            return max(l, r, 0, 0, M+1);
        }

        public int[] max(int l, int r, int idx, int fr, int to) {
            int[] ret = {Integer.MIN_VALUE, Integer.MIN_VALUE};
            if (to <= l || r <= fr) {
                return ret;
            }
            if (l <= fr && to <= r) {
                return seg[idx];
            }

            int med = (fr+to) / 2;
            ret = compare(ret, max(l, r, idx*2+1, fr, med));
            ret = compare(ret, max(l, r, idx*2+2, med, to));
            return ret;
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

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
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
