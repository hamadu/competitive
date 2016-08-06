package codeforces.cf3xx.cf365.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by hama_du on 2016/08/06.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = in.nextInts(n);
        long[] imos = new long[n+1];
        for (int i = 0; i < n ; i++) {
            imos[i+1] = imos[i] ^ a[i];
        }

        int[] ra = new int[n];
        Map<Integer,Integer> lmap = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (lmap.containsKey(a[i])) {
                ra[i] = lmap.get(a[i]);
                continue;
            }
            ra[i] = lmap.size();
            lmap.put(a[i], lmap.size());
        }

        int m = in.nextInt();
        int[][] q = new int[m][3];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j < 2  ; j++) {
                q[i][j] = in.nextInt();
            }
            q[i][0]--;
            q[i][2] = i;
        }
        Arrays.sort(q, (o1, o2) -> o1[1] - o2[1]);

        BIT bit = new BIT(1000010);
        int[] last = new int[1000010];
        long[] ans = new long[m];
        Arrays.fill(last, -1);

        int head = 0;
        for (int i = 0; i < m ; i++) {
            while (head < q[i][1]) {
                int ni = ra[head];
                if (last[ni] != -1) {
                    bit.set(last[ni]+1, 0);
                }
                last[ni] = head;
                bit.set(last[ni]+1, a[head]);
                head++;
            }
            ans[q[i][2]] = bit.range(q[i][0]+1, q[i][1]) ^ imos[q[i][1]] ^ imos[q[i][0]];
        }

        for (int i = 0; i < m ; i++) {
            out.println(ans[i]);
        }
        out.flush();
    }

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
                s ^= data[i];
                i -= i & (-i);
            }
            return s;
        }

        long range(int i, int j) {
            return sum(j) ^ sum(i-1);
        }

        void set(int i, long x) {
            add(i, x^range(i, i));
        }

        void add(int i, long x) {
            while (i <= N) {
                data[i] ^= x;
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
