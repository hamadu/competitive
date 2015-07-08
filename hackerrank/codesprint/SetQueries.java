package hackerrank.codesprint;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

/**
 * Created by hama_du on 15/06/21.
 */
public class SetQueries {

    private static final long MOD = 1000000009;

    static final int BUCKET_SIZE = 512;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int s = in.nextInt();
        int q = in.nextInt();

        int[] largeSets = new int[s];
        int[] largeSetRev = new int[s];
        int ln = 0;

        int[][] setArr = new int[s][];
        boolean[] isLarge = new boolean[s];
        Set<Integer>[] sets = new Set[s];
        for (int i = 0; i < s ; i++) {
            sets[i] = new HashSet<>();
            int k = in.nextInt();
            setArr[i] = new int[k];
            for (int j = 0 ; j < k ; j++) {
                setArr[i][j] = in.nextInt()-1;
                sets[i].add(setArr[i][j]);
            }
            if (k >= BUCKET_SIZE) {
                largeSets[ln++] = i;
                largeSetRev[i] = ln-1;
                isLarge[i] = true;
            }
        }
        boolean[][] largeSetX = new boolean[ln][n];
        for (int li = 0 ; li < ln ; li++) {
            for (int i : setArr[largeSets[li]]) {
                largeSetX[li][i] = true;
            }
        }

        int[][] imos = new int[ln][n+1];
        for (int li = 0 ; li < ln ; li++) {
            for (int i = 0; i < n; i++) {
                imos[li][i+1] += imos[li][i];
                if (largeSetX[li][i]) {
                    imos[li][i+1]++;
                }
            }
        }

        int[][] crossMapSL = new int[s][ln];
        for (int i = 0 ; i < s ; i++) {
            if (isLarge[i]) {
                continue;
            }
            for (int idx : sets[i]) {
                for (int li = 0 ; li < ln ; li++) {
                    if (largeSetX[li][idx]) {
                        crossMapSL[i][li]++;
                    }
                }
            }
        }

        int[][] crossMapLL = new int[ln][ln];
        for (int l1 = 0 ; l1 < ln ; l1++) {
            for (int l2 = l1+1; l2 < ln; l2++) {
                int i1 = largeSets[l1];
                int i2 = largeSets[l2];
                int[] itr = setArr[i1].length < setArr[i2].length ? setArr[i1] : setArr[i2];
                boolean[] ctr = setArr[i1].length < setArr[i2].length ? largeSetX[l2] : largeSetX[l1];
                for (int i : itr) {
                    if (ctr[i]) {
                        crossMapLL[l1][l2]++;
                    }
                }
                crossMapLL[l2][l1] = crossMapLL[l1][l2];
            }
        }

        long[] added = new long[s];
        long[] partAdded = new long[s];

        SqrtCmp data = new SqrtCmp(n);
        while (--q >= 0) {
            int type = in.nextInt();
            if (type == 1) {
                int si = in.nextInt()-1;
                long v = in.nextInt();
                added[si] = (added[si] + v) % MOD;
                if (!isLarge[si]) {
                    data.add(setArr[si], v);
                    for (int li = 0 ; li < ln ; li++) {
                        long ct = crossMapSL[si][li];
                        partAdded[largeSets[li]] += (ct * v) % MOD;
                        partAdded[largeSets[li]] %= MOD;
                    }
                } else {
                    int fi = largeSetRev[si];
                    for (int li = 0 ; li < ln ; li++) {
                        long ct = crossMapLL[fi][li];
                        partAdded[largeSets[li]] += (ct * v) % MOD;
                        partAdded[largeSets[li]] %= MOD;
                    }
                }

            } else if (type == 3) {
                int l = in.nextInt()-1;
                int r = in.nextInt()-1;
                long v = in.nextInt();
                data.add(l, r, v);
                for (int li = 0 ; li < ln ; li++) {
                    int cnt = imos[li][r+1] - imos[li][l];
                    partAdded[largeSets[li]] += (1L * cnt * v) % MOD;
                    partAdded[largeSets[li]] %= MOD;
                }
            } else if (type == 2) {
                int si = in.nextInt()-1;
                long sum = 0;
                if (!isLarge[si]) {
                    for (int i : setArr[si]) {
                        int b = i / BUCKET_SIZE;
                        sum += data.all[b];
                        sum += data.a[i];
                    }
                    for (int li = 0 ; li < ln ; li++) {
                        sum += (1L * crossMapSL[si][li] * added[largeSets[li]]) % MOD;
                        sum %= MOD;
                    }
                } else {
                    sum += (1L * setArr[si].length * added[si]) % MOD;
                }
                sum += partAdded[si];

                sum %= MOD;
                out.println(sum);
            } else if (type == 4) {
                int l = in.nextInt()-1;
                int r = in.nextInt()-1;
                long ret = data.range(l, r);
                for (int li = 0 ; li < ln ; li++) {
                    int cnt = imos[li][r+1] - imos[li][l];
                    ret += 1L * cnt * added[largeSets[li]] % MOD;
                }
                ret %= MOD;
                out.println(ret);
            }
        }
        out.flush();
    }


    static class SqrtCmp {
        int n;
        int bn;
        long[] a;
        long[] sum;
        long[] all;

        SqrtCmp(int _n) {
            n = _n;
            bn = (n + BUCKET_SIZE - 1) / BUCKET_SIZE;

            a = new long[n];
            sum = new long[bn];
            all = new long[bn];
        }

        void add(int[] set, long v) {
            for (int i : set) {
                int b = i / BUCKET_SIZE;
                sum[b] += MOD - a[i];
                a[i] += v;
                a[i] %= MOD;
                sum[b] += a[i];
                sum[b] %= MOD;
            }
        }

        void add(int f, int t, long v) {
            int bi = f / BUCKET_SIZE;
            int bj = t / BUCKET_SIZE;
            for (int b = bi ; b <= bj ; b++) {
                if (b == bi || b == bj) {
                    for (int i = b * BUCKET_SIZE ; i < (b + 1) * BUCKET_SIZE ; i++) {
                        if (f <= i && i <= t) {
                            sum[b] += MOD - a[i];
                            a[i] += v;
                            a[i] %= MOD;
                            sum[b] += a[i];
                            sum[b] %= MOD;
                        }
                    }
                } else {
                    long bl = Math.min(n, (b+1) * BUCKET_SIZE) - b * BUCKET_SIZE;
                    sum[b] = (sum[b] + (v * bl % MOD)) % MOD;
                    all[b] = (all[b] + v) % MOD;
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
                    for (int i = b * BUCKET_SIZE ; i < (b + 1) * BUCKET_SIZE ; i++) {
                        if (f <= i && i <= t) {
                            ret += a[i];
                            ret += all[b];
                        }
                    }
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
