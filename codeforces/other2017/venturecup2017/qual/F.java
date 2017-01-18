package codeforces.other2017.venturecup2017.qual;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class F {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();
        int[] perm = new int[n];
        for (int i = 0; i < n ; i++) {
            perm[i] = in.nextInt()-1;
        }

        List<Integer> cycles = new ArrayList<>();
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n ; i++) {
            if (visited[i]) {
                continue;
            }
            int now = i;
            int cnt = 0;
            while (!visited[now]) {
                visited[now] = true;
                cnt++;
                now = perm[now];
            }
            cycles.add(cnt);
        }

        int min = solveMin(cycles, k);
        int max = solveMax(cycles, k);
        if (k == 0) {
            min = max = 0;
        }
        out.println(String.format("%d %d", min, max));
        out.flush();
    }

    private static int solveMin(List<Integer> cycles, int k) {
        Map<Integer,Integer> sets = new HashMap<>();
        for (int c : cycles) {
            sets.put(c, sets.getOrDefault(c, 0)+1);
        }

        List<Integer> vs = new ArrayList<>();
        for (int x : sets.keySet()) {
            int v = sets.get(x);
            int l = 1;
            while (l <= v) {
                vs.add(x * l);
                v -= l;
                l *= 2;
            }
            if (v >= 1) {
                vs.add(x * v);
            }
        }
        Collections.sort(vs);

        BitVector bv = new BitVector(1);
        bv.set(0);
        for (int c : vs) {
            bv = bv.or(bv.shiftLeft(c));
        }
        return bv.get(k) ? k : k+1;
    }

    static class BitVector {
        public int n;
        public int m;
        public long[] bits;

        public BitVector(int length) {
            n = length;
            bits = new long[(n+63)>>>6];
            m = bits.length;
        }

        public void set(int at) {
            bits[at>>>6] |= 1L<<(at&63);
        }

        public boolean get(int at) {
            return ((bits[at>>>6] >>> (at&63)) & 1) == 1;
        }

        public BitVector shiftLeft(int l) {
            BitVector ret = new BitVector(n+l);
            int big = l >>> 6;
            int small = l & 63;
            for (int i = 0; i < m ; i++) {
                ret.bits[i+big] |= bits[i] << small;
            }
            if (small >= 1) {
                for (int i = 0; i+big+1 < ret.m; i++) {
                    ret.bits[i+big+1] |= (bits[i] >>> (64-small));
                }
            }
            return ret;
        }

        public BitVector or(BitVector o) {
            BitVector ans = new BitVector(Math.max(n, o.n));
            for (int i = 0; i < ans.m ; i++) {
                if (i < m) {
                    ans.bits[i] = bits[i];
                }
                if (i < o.m) {
                    ans.bits[i] |= o.bits[i];
                }
            }
            return ans;
        }
    }

    private static int solveMax(List<Integer> cycles, int k) {
        int cnt = 0;
        for (int c : cycles) {
            int x = c / 2;
            int dec = Math.min(k, x);
            cnt += dec * 2;
            k -= dec;
        }
        for (int c : cycles) {
            if (c % 2 == 1 && k >= 1) {
                k--;
                cnt++;
            }
        }
        return cnt;
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
