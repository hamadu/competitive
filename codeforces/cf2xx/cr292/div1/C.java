package codeforces.cf2xx.cr292.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class C {
    static class SegmentTree {
        int N;
        long[] a;
        int[] maxIndex;

        SegmentTree(long[] data) {
            int n = data.length;
            N = 1;
            while (N <= n) {
                N *= 2;
            }
            a = new long[N*2+1];
            maxIndex = new int[N*2+1];
            for (int i = 0 ; i < n ; i++) {
                a[N-1+i] = data[i];
                maxIndex[N-1+i] = i;
            }
            for (int i = N-2 ; i >= 0 ; i--) {
                a[i] = Math.max(a[i*2+1], a[i*2+2]);
                if (a[i*2+1] < a[i*2+2]) {
                    maxIndex[i] = maxIndex[i*2+2];
                } else {
                    maxIndex[i] = maxIndex[i*2+1];
                }
            }
        }

        int rangeIndex;
        long rangeValue;
        void range(int fr, int to) {
            rangeIndex = -1;
            rangeValue = Long.MIN_VALUE;
            range(fr, to, 0, 0, N);
        }

        private void range(int fr, int to, int idx, int l, int r) {
            if (fr <= l && r <= to) {
                if (rangeValue < a[idx]) {
                    rangeIndex = maxIndex[idx];
                    rangeValue = a[idx];
                }
                return;
            } else if (to <= l || r <= fr) {
                return;
            }
            range(fr, to, idx*2+1, l, (l+r)/2);
            range(fr, to, idx*2+2, (l+r)/2, r);
        }

        void update(int idx, long v) {
            a[N-1+idx] = v;
            maxIndex[N-1+idx] = idx;
            int ptr = N-1+idx;
            while (ptr > 0) {
                int parent = (ptr-1)/2;
                a[parent] = Math.max(a[parent*2+1], a[parent*2+2]);
                if (a[parent*2+1] < a[parent*2+2]) {
                    maxIndex[parent] = maxIndex[parent*2+2];
                } else {
                    maxIndex[parent] = maxIndex[parent*2+1];
                }
                ptr = parent;
            }
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        long around = 0;
        long[] d = new long[2*n-1];
        for (int i = 0 ; i < n ; i++) {
            d[i] = in.nextInt();
            if (i != n-1) {
                d[n + i] = d[i];
            }
        }
        for (long di : d) {
            around += di;
        }
        long[] h = new long[2*n];
        for (int i = 0 ; i < n ; i++) {
            h[i] = in.nextInt();
            h[n+i] = h[i];
        }

        long[] left = new long[2*n];
        for (int i = 0 ; i < 2*n ; i++) {
            if (i != 2*n-1) {
                left[i + 1] += left[i];
                left[i + 1] += d[i];
            }
            left[i] += h[i] * 2;
        }
        long[] right = new long[2*n];
        for (int i = 2*n-1 ; i >= 0 ; i--) {
            if (i != 0) {
                right[i-1] += right[i];
                right[i-1] += d[i-1];
            }
            right[i] += h[i] * 2;
        }

        SegmentTree ll = new SegmentTree(left);
        SegmentTree rr = new SegmentTree(right);

        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            int[] rg = new int[2];
            if (a <= b) {
                rg = new int[]{ b+1, a+n };
            } else {
                rg = new int[]{b + 1, a};
            }
            long max = 0;
            ll.range(rg[0], rg[1]);
            rr.range(rg[0], rg[1]);

            if (ll.rangeIndex == rr.rangeIndex) {
                int tempRi = rr.rangeIndex;
                long tempRV = rr.a[rr.N-1+rr.rangeIndex];
                rr.update(rr.rangeIndex, -1);
                rr.range(rg[0], rg[1]);
                rr.update(tempRi, tempRV);
                max = Math.max(max, ll.rangeValue+rr.rangeValue-around);
                rr.range(rg[0], rg[1]);

                int tempLi = ll.rangeIndex;
                long tempLV = ll.a[ll.N-1+ll.rangeIndex];
                ll.update(rr.rangeIndex, -1);
                ll.range(rg[0], rg[1]);
                ll.update(tempLi, tempLV);
                max = Math.max(max, ll.rangeValue+rr.rangeValue-around);
                ll.range(rg[0], rg[1]);
            } else {
                max = Math.max(max, ll.rangeValue+rr.rangeValue-around);
            }
            out.println(max);
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
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



