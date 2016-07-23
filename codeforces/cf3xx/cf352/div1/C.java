package codeforces.cf3xx.cf352.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/07/22.
 */
public class C {
    static final int MAX = 200000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = in.nextInts(n);
        int[] invA = new int[MAX+1];
        Arrays.fill(invA, -1);
        for (int i = 0; i < a.length ; i++) {
            invA[a[i]] = i;
        }

        long[] pa = new long[MAX+1];


        long[] fo = new long[n];
        for (int i = 0; i < n ; i++) {
            fo[i] = i;
        }
        SegmentTree tree = new SegmentTree(fo);

        int[] tmp = new int[n+1];
        for (int l = MAX+1 ; l >= 1 ; l--) {
            int ti = 0;
            for (int x = l ; x <= MAX ; x += l) {
                if (invA[x] != -1) {
                    tmp[ti++] = invA[x];
                }
            }
            if (ti >= 2) {
                Arrays.sort(tmp, 0, ti);

                int lastOne = tmp[ti-1];
                int lastTwo = tmp[ti-2];

                int firstOne = tmp[0];
                int firstTwo = tmp[1];

                tree.update(0, firstOne+1, lastTwo);
                tree.update(firstOne+1, firstTwo+1, lastOne);
                tree.update(firstTwo+1, n, n);
            }
            pa[l-1] = (long)n*n - tree.sum(0, n);
        }

        long sum = 0;
        for (int i = MAX ; i >= 1 ; i--) {
            long lw = pa[i] - pa[i-1];
            sum += lw * i;
        }
        out.println(sum);
        out.flush();
    }

    public static class SegmentTree {
        int N;
        int M;
        long[] segUpd;
        long[] segSum;
        long[] segMin;
        long[] segMax;

        public SegmentTree(long[] data) {
            N = Integer.highestOneBit(data.length-1)<<2;
            M = (N >> 1) - 1;

            segSum = new long[N];
            segUpd = new long[N];
            segMin = new long[N];
            segMax = new long[N];
            Arrays.fill(segUpd, -1);

            for (int i = 0 ; i < data.length ; i++) {
                segSum[M+i] = segMin[M+i] = segMax[M+i] = data[i];
            }
            for (int i = M-1 ; i >= 0 ; i--) {
                compute(i);
            }
        }

        public void compute(int i) {
            segSum[i] = segSum[i*2+1] +  segSum[i*2+2];
            segMin[i] = Math.min(segMin[i*2+1], segMin[i*2+2]);
            segMax[i] = Math.max(segMax[i*2+1], segMax[i*2+2]);
        }

        public void update(int l, int r, long k) {
            update(l, r, k, 0, 0, M+1);
        }

        public void propagate(int idx, int fr, int to) {
            if (segUpd[idx] != -1) {
                int l = idx*2+1;
                int r = idx*2+2;
                if (r < segUpd.length) {
                    segUpd[l] = segMin[l] = segMax[l] = segUpd[idx];
                    segUpd[r] = segMin[r] = segMax[r] = segUpd[idx];
                }
                segSum[idx] = (to-fr)*segUpd[idx];
                segUpd[idx] = -1;
            }
        }

        public void update(int l, int r, long x, int idx, int fr, int to) {
            propagate(idx, fr, to);

            if (to <= l || r <= fr) {
                return;
            }
            if (l <= fr && to <= r && segMax[idx] <= x) {
                segUpd[idx] = segMin[idx] = segMax[idx] = x;
                propagate(idx, fr, to);
                return;
            }
            int med = (fr + to) / 2;
            if (segMin[idx*2+1] < x) {
                update(l, r, x, idx*2+1, fr, med);
            }
            if (segMin[idx*2+2] < x) {
                update(l, r, x, idx*2+2, med, to);
            }

            // make sure that child nodes have been propagated.
            compute(idx);
        }

        public long sum(int l, int r) {
            return sum(l, r, 0, 0, M+1);
        }

        public long sum(int l, int r, int idx, int fr, int to) {
            propagate(idx, fr, to);

            if (to <= l || r <= fr) {
                return 0;
            }

            if (l <= fr && to <= r) {
                return segSum[idx];
            }
            int med = (fr+to) / 2;
            long ret = sum(l, r, idx*2+1, fr, med) + sum(l, r, idx*2+2, med, to);

            // make sure that child nodes have been propagated.
            compute(idx);

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
