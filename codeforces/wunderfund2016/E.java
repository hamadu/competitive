package codeforces.wunderfund2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/01/30.
 */
public class E {
    static double[] sin = new double[361];
    static double[] cos = new double[361];

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        for (int i = 0; i <= 360; i++) {
            double rad = i * Math.PI / 180.0d;
            sin[i] = Math.signum(rad);
            cos[i] = Math.signum(rad);
        }

        int n = in.nextInt();
        int m = in.nextInt();


        SegmentTree segmentTree = new SegmentTree(new int[n+2], n);
        for (int i = 0; i < m ; i++) {
            int t = in.nextInt();
            int seg = in.nextInt()-1;
            if (t == 1) {
                segmentTree.extend(seg, seg+1, in.nextInt());
            } else {
                long theta = 360 - in.nextInt();
                double rad = theta * Math.PI / 180.0;
                segmentTree.rotate(seg, seg+1, rad);
            }
            double[] xy = segmentTree.compute2(0);
            out.println(String.format("%.9f %.9f", xy[0], xy[1]));
        }
        out.flush();
    }



    public static class SegmentTree {
        int N;
        int M;
        double[] len;
        double[] rot;

        public SegmentTree(int[] data, int n) {
            N = Integer.highestOneBit(data.length-1)<<2;
            M = (N >> 1) - 1;

            len = new double[N];
            rot = new double[N];
            for (int i = 0 ; i < n ; i++) {
                len[M+i] = 1.0d;
            }
            for (int i = M-1 ; i >= 0 ; i--) {
                compute(i);
            }
        }

        public void compute(int i) {
            double dx1 = len[i*2+1] * Math.cos(rot[i*2+1]);
            double dy1 = len[i*2+1] * Math.sin(rot[i*2+1]);
            double dx2 = len[i*2+2] * Math.cos(rot[i*2+2]);
            double dy2 = len[i*2+2] * Math.sin(rot[i*2+2]);


            double dx = dx1+dx2;
            double dy = dy1+dy2;
            len[i] = Math.sqrt(dx*dx+dy*dy);
            rot[i] = Math.atan2(dy, dx);
        }


        public double[] compute2(int i) {
            double dx1 = len[i*2+1] * Math.cos(rot[i*2+1]);
            double dy1 = len[i*2+1] * Math.sin(rot[i*2+1]);
            double dx2 = len[i*2+2] * Math.cos(rot[i*2+2]);
            double dy2 = len[i*2+2] * Math.sin(rot[i*2+2]);

            double dx = dx1+dx2;
            double dy = dy1+dy2;
            return new double[]{dx, dy};
        }

        public void extend(int l, int r, int exLen) {
            extend(l, r, 0, 0, M+1, exLen);
        }

        public void extend(int l, int r, int idx, int fr, int to, int exLen) {
            if (to <= l || r <= fr) {
                return;
            }
            if (l <= fr && to <= r) {
                len[idx] += exLen;
                return;
            }
            int med = (fr+to) / 2;
            extend(l, r, idx*2+1, fr, med, exLen);
            extend(l, r, idx*2+2, med, to, exLen);
            compute(idx);
        }

        public void rotate(int l, int r, double theta) {
            rotate(l, r, 0, 0, M+1, theta);
        }

        public void rotate(int l, int r, int idx, int fr, int to, double theta) {
            if (to <= l || r <= fr) {
                return;
            }
            if (l <= fr && to <= r) {
                rot[idx] += theta;
                return;
            }
            int med = (fr+to) / 2;
            rotate(l, r, idx*2+1, fr, med, theta);
            rotate(l, r, idx*2+2, med, to, theta);
            compute(idx);
        }

    }

    // 区間加算
    static class BITRange {
        BIT bit0;
        BIT bit1;
        BITRange(int n) {
            bit0 = new BIT(n);
            bit1 = new BIT(n);
        }

        void addRange(int l, int r, long x) {
            bit0.add(l, -x * (l-1));
            bit1.add(l, x);
            bit0.add(r+1, x * r);
            bit1.add(r+1, -x);
        }

        long range(int l, int r) {
            long right = bit0.sum(r) + bit1.sum(r) * r;
            long left = bit0.sum(l-1) + bit1.sum(l-1) * (l-1);
            return right - left;
        }

        long get(int i) {
            return range(i, i);
        }
    }

    // BIT, 1-indexed, range : [a,b]
    static class BIT {
        long N;
        long[] data;
        BIT(int n) {
            N = n;
            data = new long[n+1];
        }

        long sum(int i) {
            long s = 0;
            while (i > 0) {
                s += data[i];
                i -= i & (-i);
            }
            return s;
        }

        long range(int i, int j) {
            return sum(j) - sum(i-1);
        }

        void add(int i, long x) {
            while (i <= N) {
                data[i] += x;
                i += i & (-i);
            }
        }

        // find rightmost non-zero position less or equal x
        int beforeNonZeroPosition(int x) {
            long u = sum(x);
            return findMostLeftPositionValueOf(u);
        }

        // find leftmost non-zero position more or equal x
        int afterNonZeroPosition(int x) {
            long u = sum(x);
            if (u > sum(x-1)) {
                return x;
            }
            return findMostLeftPositionValueOf(u+1);
        }

        // find position　at most right that have a[i] == value
        int findMostRightPositionValueOf(long value) {
            int i = 0;
            int n = data.length;
            for (int b = Integer.highestOneBit(n) ; b != 0 && i < n ; b >>= 1) {
                if (i + b < n) {
                    int t = i + b;
                    if (value >= data[t]) {
                        i = t;
                        value -= data[t];
                    }
                }
            }
            return i;
        }

        // find position at most left that have a[i] == value
        int findMostLeftPositionValueOf(long value) {
            if (value <= 0) {
                return 0;
            }
            // <most left> = <most right at previous value> + 1
            return findMostRightPositionValueOf(value-1)+1;
        }

        static void verify() {
            // input:
            //   i : 1 2 3 4 5 6 7 8 9
            //   a : 0 1 1 0 1 0 0 1 0
            //   s : 0 1 2 2 3 3 3 4 4
            // expected:
            //   < : X 2 3 3 5 5 5 8 8
            //   > : 2 2 3 5 5 8 8 8 X
            BIT bit = new BIT(9);
            bit.add(2, 1L);
            bit.add(3, 1L);
            bit.add(5, 1L);
            bit.add(8, 1L);
            for (int i = 1 ; i <= 9 ; i++) {
                debug(i, bit.beforeNonZeroPosition(i), bit.afterNonZeroPosition(i));
            }
        }
    }

    static class BIT2 {
        long N;
        long M;
        long[][] data;
        BIT2(int n, int m) {
            N = n;
            M = m;
            data = new long[n+1][m+1];
        }

        long sum(int i, int j) {
            long s = 0;
            while (i > 0) {
                int ij = j;
                while (ij > 0) {
                    s += data[i][ij];
                    ij -= ij & (-ij);
                }
                i -= i & (-i);
            }
            return s;
        }

        long range(int x0, int y0, int x1, int y1) {
            return sum(x1, y1) - sum(x0-1, y1) - sum(x1, y0-1) + sum(x0-1, y0-1);
        }

        void add(int i, int j, long x) {
            while (i <= N) {
                int ij = j;
                while (ij <= M) {
                    data[i][ij] += x;
                    ij += ij & (-ij);
                }
                i += i & (-i);
            }
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
