package codechef.long201505;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/05/09.
 */
public class CHEFCK {
    static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int T = 1;
        while (--T >= 0) {
            int n = in.nextInt();
            int k = in.nextInt();
            int q = in.nextInt();

            int[] a = generateArray(n,
                    in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(),
                    in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(),
                    in.nextInt()
                    );
            long[] ans = solve(
                    a,
                    n, k, q,
                    in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(),
                    in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt()
            );
            out.println(ans[0] + " " + ans[1]);
        }
        out.flush();
    }

    private static long[] solve(int[] a, int n, int k, int q, long L1, long La, long Lc, long Lm, long D1, long Da, long Dc, long Dm) {
        long[] ret = new long[2];
        ret[1] = 1;

        Deque<Integer> deq = new ArrayDeque<>();
        int[] slideMin = new int[n-k+1];
        for (int i = 0 ; i < n ; i++) {
            while (deq.size() >= 1 && a[deq.peekLast()] >= a[i]) {
                deq.pollLast();
            }
            deq.add(i);

            if (i - k + 1 >= 0) {
                int top = deq.peekFirst();
                slideMin[i-k+1] = a[top];
                if (top == i - k + 1) {
                    deq.pollFirst();
                }
            }
        }

        for (int i = 0 ; i < q ; i++) {
            L1 = (La * L1 + Lc) % Lm;
            D1 = (Da * D1 + Dc) % Dm;
            int fr = (int) L1;
            int to = (int)Math.min(fr + 1 + k - 1 + D1, n);

            long ans = Math.min(slideMin[fr], slideMin[to-k]);

            ret[0] = ret[0] + ans;
            ret[1] = (ret[1] * ans) % MOD;
        }
        return ret;
    }

    private static int[] generateArray(int n, long a, long b, long c, long d, long e, long f, long r, long s, long t, long m, long a1) {
        long[] data = new long[n+1];
        data[1] = a1;

        long powt = t;
        for (int i = 2 ; i <= n ; i++) {
            powt = (powt * t) % s;


            long d1 = data[i-1];
            long d2 = (data[i-1] * data[i-1]) % m;
            if (powt <= r) {
                data[i] = (a * d2 + b * d1 + c) % m;
            } else {
                data[i] = (d * d2 + e * d1 + f) % m;
            }
        }

        int[] ret = new int[n];
        for (int i = 0 ; i < n ; i++) {
            ret[i] = (int)data[i+1];
        }
        return ret;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
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

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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

    public static class SegmentTree {
        int N;
        int M;
        int[] seg;

        public SegmentTree(int[] data) {
            N = Integer.highestOneBit(data.length-1)<<2;
            M = (N >> 1) - 1;

            seg = new int[N];
            Arrays.fill(seg, Integer.MAX_VALUE);
            for (int i = 0 ; i < data.length ; i++) {
                seg[M+i] = data[i];
            }
            for (int i = M-1 ; i >= 0 ; i--) {
                seg[i] = compute(i);
            }
        }

        public void update(int idx, int value) {
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


        public int compute(int i) {
            return Math.min(seg[i*2+1], seg[i*2+2]);
        }


        public int min(int l, int r) {
            return min(l, r, 0, 0, M+1);
        }

        public int min(int l, int r, int idx, int fr, int to) {
            if (to <= l || r <= fr) {
                return Integer.MAX_VALUE;
            }
            if (l <= fr && to <= r) {
                return seg[idx];
            }

            int med = (fr+to) / 2;
            int ret = Integer.MAX_VALUE;
            ret = Math.min(ret, min(l, r, idx*2+1, fr, med));
            ret = Math.min(ret, min(l, r, idx*2+2, med, to));
            return ret;
        }
    }
}
