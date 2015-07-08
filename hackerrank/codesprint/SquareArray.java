package hackerrank.codesprint;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/06/21.
 */
public class SquareArray {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();

        SqrtCmp data = new SqrtCmp(n);
        while (--q >= 0) {
            int type = in.nextInt();
            int l = in.nextInt()-1;
            int r = in.nextInt()-1;
            if (type == 1) {
                data.add(l, r);
            } else {
                out.println(data.range(l, r));
            }
        }
        out.flush();
    }


    static class SqrtCmp {
        static final int BUCKET_SIZE = 512;
        private static final long MOD = 1000000007;

        int n;
        int bn;
        long[] a;
        long[] sum;

        long[] top;
        long[] head;
        long[] cnt;

        SqrtCmp(int _n) {
            n = _n;
            bn = (n + BUCKET_SIZE - 1) / BUCKET_SIZE;

            a = new long[n];
            sum = new long[bn];

            top = new long[bn];
            head = new long[bn];
            cnt = new long[bn];

        }

        void add(int f, int t) {
            int bi = f / BUCKET_SIZE;
            int bj = t / BUCKET_SIZE;
            for (int b = bi ; b <= bj ; b++) {
                if (b == bi || b == bj) {
                    for (int i = b * BUCKET_SIZE ; i < (b + 1) * BUCKET_SIZE ; i++) {
                        if (f <= i && i <= t) {
                            long idx = i - f + 1;
                            sum[b] += MOD - a[i];
                            a[i] += idx * (idx + 1) % MOD;
                            a[i] %= MOD;
                            sum[b] += a[i];
                            sum[b] %= MOD;
                        }
                    }
                } else {
                    cnt[b]++;
                    long idx = b * BUCKET_SIZE - f + 1;
                    top[b] += idx * (idx + 1) % MOD;
                    top[b] %= MOD;
                    head[b] += (idx + 1) * 2;
                    head[b] %= MOD;

                    long len = Math.min(n, (b+1)*BUCKET_SIZE) - b * BUCKET_SIZE;
                    long first = (idx * (idx + 1)) % MOD;
                    long next = ((idx + 1) * (idx + 2) - first) % MOD;

                    sum[b] += val(first, next, 2, len);
                    sum[b] %= MOD;
                }
            }
        }


        long val(long a, long d, long c, long n) {
            long ai = (a * n) % MOD;
            long di = ((n * (n - 1) / 2) % MOD) * d % MOD;
            long ci = (((n - 2) * (n - 1) * n / 6) % MOD) * c % MOD;
            return (ai + di + ci) % MOD;
        }

        // [f,t]
        long range(int f, int t) {
            long ret = 0;
            int bi = f / BUCKET_SIZE;
            int bj = t / BUCKET_SIZE;
            for (int b = bi ; b <= bj ; b++) {
                if (b == bi || b == bj) {
                    int min = BUCKET_SIZE+1;
                    int max = -1;
                    for (int i = b * BUCKET_SIZE ; i < (b + 1) * BUCKET_SIZE ; i++) {
                        if (f <= i && i <= t) {
                            ret += a[i];
                            min = Math.min(min, i - b * BUCKET_SIZE);
                            max = Math.max(max, i - b * BUCKET_SIZE + 1);
                        }
                    }
                    ret += MOD - val(top[b], head[b], cnt[b] * 2, min);
                    ret += val(top[b], head[b], cnt[b] * 2, max);
                    ret %= MOD;
                } else {
                    ret += sum[b];
                }
            }
            return ret % MOD;
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

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
