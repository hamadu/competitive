package codeforces.cf3xx.cf373.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class C {
    static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[] a = in.nextInts(n);

        baseMat = new long[100][4];
        baseMat[0] = new long[]{1,1,1,0};
        for (int k = 1 ; k < baseMat.length ; k++) {
            mul(baseMat[k-1], baseMat[k-1]);
            baseMat[k][0] = mulA;
            baseMat[k][1] = mulB;
            baseMat[k][2] = mulC;
            baseMat[k][3] = mulD;
        }

        long[][] w2 = new long[n][2];
        for (int i = 0; i < n ; i++) {
            long[] x = getmat(a[i]-1);
            getPair(x, 1, 0);
            w2[i][0] = pairU;
            w2[i][1] = pairD;
        }

        SegmentTreeRURSQ seg = new SegmentTreeRURSQ(w2);
        while (--m >= 0) {
            int type = in.nextInt();
            int l = in.nextInt()-1;
            int r = in.nextInt();
            if (type == 1) {
                long add = in.nextInt();
                seg.add(l, r, add);
            } else {
                out.println(seg.sum(l, r) % MOD);
            }
        }
        out.flush();
    }


    static long[][] baseMat;

    static long mulA;
    static long mulB;
    static long mulC;
    static long mulD;

    static void mul(long[] a, long[] b) {
        mulA = (a[0] * b[0] + a[1] * b[2]) % MOD;
        mulB = (a[0] * b[1] + a[1] * b[3]) % MOD;
        mulC = (a[2] * b[0] + a[3] * b[2]) % MOD;
        mulD = (a[2] * b[1] + a[3] * b[3]) % MOD;
    }

    static Map<Long,long[]> memo = new HashMap<>();

    static long[] getmat(long value) {
        if (memo.containsKey(value)) {
            return memo.get(value);
        }
        long[] ret = new long[]{1,0,0,1};
        for (int k = 0 ; k < 60 ; k++) {
            if (((value >>> k) & 1) == 1) {
                mul(ret, baseMat[k]);
                ret[0] = mulA;
                ret[1] = mulB;
                ret[2] = mulC;
                ret[3] = mulD;
            }
        }
        memo.put(value, ret);
        return ret;
    }

    static long pairU;
    static long pairD;

    static void getPair(long[] mat, long a, long b) {
        long U = (mat[0] * a) + (mat[1] * b);
        long D = (mat[2] * a) + (mat[3] * b);
        pairU = U % MOD;
        pairD = D % MOD;
    }

    /**
     * Segment tree (range update, range sum query).
     */
    public static class SegmentTreeRURSQ {
        int N;
        int M;
        long[][] segSum;
        long[] segAdd;

        public SegmentTreeRURSQ(long[][] data) {
            N = Integer.highestOneBit(data.length-1)<<2;
            N = Math.max(8, N);
            M = (N >> 1) - 1;

            segAdd = new long[N];

            segSum = new long[N][2];
            for (int i = 0 ; i < data.length ; i++) {
                segSum[M+i] = data[i];
            }
            for (int i = M-1 ; i >= 0 ; i--) {
                segSum[i] = compute(i);
            }
        }

        public long[] compute(int i) {
            long a = (segSum[i*2+1][0] + segSum[i*2+2][0]) % MOD;
            long b = (segSum[i*2+1][1] + segSum[i*2+2][1]) % MOD;
            return new long[]{a, b};
        }

        public void add(int l, int r, long k) {
            add(l, r, k, 0, 0, M+1);
        }

        public void propagate(int idx) {
            if (segAdd[idx] >= 1) {
                long[] mat = getmat(segAdd[idx]);
                long up = segSum[idx][0];
                long dw = segSum[idx][1];
                int l = idx*2+1;
                int r = idx*2+2;
                if (r < segAdd.length) {
                    segAdd[l] += segAdd[idx];
                    segAdd[r] += segAdd[idx];
                }
                getPair(mat, up, dw);
                segSum[idx][0] = pairU;
                segSum[idx][1] = pairD;
                segAdd[idx] = 0;
            }
        }

        public void add(int l, int r, long x, int idx, int fr, int to) {
            propagate(idx);

            if (to <= l || r <= fr) {
                return;
            }
            if (l <= fr && to <= r) {
                segAdd[idx] = x;
                propagate(idx);
                return;
            }

            int med = (fr + to) / 2;
            int L = idx*2+1;
            int R = idx*2+2;
            add(l, r, x, idx*2+1, fr, med);
            add(l, r, x, idx*2+2, med, to);

            // make sure that child nodes have been propagated.
            long u = segSum[L][0] + segSum[R][0];
            long d = segSum[L][1] + segSum[R][1];
            segSum[idx][0] = u >= MOD ? u-MOD : u;
            segSum[idx][1] = d >= MOD ? d-MOD : d;
        }

        public long sum(int l, int r) {
            return sum(l, r, 0, 0, M+1);
        }

        public long sum(int l, int r, int idx, int fr, int to) {
            propagate(idx);

            if (to <= l || r <= fr) {
                return 0;
            }

            if (l <= fr && to <= r) {
                return segSum[idx][0];
            }
            int med = (fr+to) / 2;
            int L = idx*2+1;
            int R = idx*2+2;
            long ret = sum(l, r, L, fr, med) + sum(l, r, R, med, to);

            // make sure that child nodes have been propagated.
            long u = segSum[L][0] + segSum[R][0];
            long d = segSum[L][1] + segSum[R][1];
            segSum[idx][0] = u >= MOD ? u-MOD : u;
            segSum[idx][1] = d >= MOD ? d-MOD : d;

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
