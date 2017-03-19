package atcoder.arc.arc070;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int k = in.nextInt();

        int[] a = in.nextInts(n);
        Arrays.sort(a);

        BitVector[] leftDP = new BitVector[n+1];
        leftDP[0] = new BitVector(5010);
        leftDP[0].set(0);
        for (int i = 0; i < n ; i++) {
            leftDP[i+1] = leftDP[i].shiftLeft(Math.min(5005, a[i])).or(leftDP[i]);
        }

        BitVector[] rightDP = new BitVector[n+1];
        rightDP[n] = new BitVector(5010);
        rightDP[n].set(0);
        for (int i = n-1 ; i >= 0 ; i--) {
            rightDP[i] = rightDP[i+1].shiftLeft(Math.min(5005, a[i])).or(rightDP[i+1]);
        }

        int[] imos = new int[5010];
        int ans = 0;
        for (int i = 0 ; i < n ; i++) {
            if (a[i] >= k) {
                continue;
            }

            for (int j = 0 ; j <= 5005 ; j++) {
                imos[j+1] = imos[j] + (rightDP[i+1].get(j) ? 1 : 0);
            }

            boolean found = false;
            int wantFrom = k - a[i];
            int wantTo = k - 1;
            for (int sum = 0 ; sum <= k - 1 ; sum++) {
                if (leftDP[i].get(sum)) {
                    int wf = Math.max(0, wantFrom - sum);
                    int wt = Math.max(0, wantTo - sum) + 1;
                    if (imos[wt] - imos[wf] >= 1) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                ans++;
            }
        }

        out.println(ans);
        out.flush();
    }

    public static class BitVector {
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

        public void set(int at, boolean s) {
            if (s) {
                bits[at>>>6] |= 1L<<(at&63);
            } else {
                bits[at>>>6] &= ~(1L<<(at&63));
            }
        }

        public boolean get(int at) {
            int big = at >>> 6 ;
            if (big >= bits.length) {
                return false;
            }
            return ((bits[big] >>> (at&63)) & 1) == 1;
        }

        public BitVector shiftLeft(int l) {
            BitVector ret = new BitVector(n);
            int big = l >>> 6;
            int small = l & 63;
            for (int i = 0; i < m ; i++) {
                if (i+big < ret.bits.length) {
                    ret.bits[i+big] |= bits[i] << small;
                }
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
