package codeforces.cf4xx.cf409.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        if (m == 1) {
            out.println(1);
            out.println(0);
            out.flush();
            return;
        }

        boolean[] ng = new boolean[m];
        for (int i = 0; i < n ; i++) {
            ng[in.nextInt()] = true;
        }

        int[] primes = generatePrimes(m+10);

        List<int[]> deco = new ArrayList<>();
        int mm = m;
        for (int p : primes) {
            int cnt = 0;
            while (mm % p == 0) {
                mm /= p;
                cnt++;
            }
            if (cnt >= 1) {
                deco.add(new int[]{p, cnt});
            }
        }

        int[] divId = new int[m];
        int[] didToNum = new int[m];
        int did = 0;
        Arrays.fill(divId, -1);


        int[] gcdTable = new int[m+1];
        for (int x = 1 ; x <= m ; x++) {
            gcdTable[x%m] = gcd(m, x) % m;
            if (divId[gcdTable[x]] == -1) {
                divId[gcdTable[x]] = did;
                didToNum[did] = gcdTable[x];
                did++;
            }
        }

        List<Integer>[] didToSequence = new List[did];
        for (int i = 0; i < did; i++) {
            didToSequence[i] = new ArrayList<>();
        }
        for (int i = 0; i < m ; i++) {
            didToSequence[divId[gcdTable[i]]].add(i);
        }

        int[] available = new int[did];
        for (int i = 0 ; i < m ; i++) {
            if (!ng[i%m]) {
                available[divId[gcdTable[i]]]++;
            }
        }

        int[] dp = new int[did];
        int[] bestFrom = new int[did];
        Arrays.fill(dp, -1);
        dp[0] = available[1];
        bestFrom[0] = -1;
        for (int fid = 0 ; fid < did ; fid++) {
            int base = dp[fid];
            if (base == -1) {
                continue;
            }
            long num = didToNum[fid];
            if (num == 0) {
                continue;
            }

            for (int[] de : deco) {
                long toNum = num * de[0];
                if (toNum > m) {
                    continue;
                }
                int tid = divId[(int)toNum%m];
                if (tid >= 0) {
                    int tv = base + available[tid];
                    if (dp[tid] < tv) {
                        dp[tid] = tv;
                        bestFrom[tid] = fid;
                    }
                }
            }
        }

        List<Integer> bestSequence = new ArrayList<>();
        int now = did-1;
        while (now >= 0) {
            bestSequence.add(now);
            now = bestFrom[now];
        }
        Collections.sort(bestSequence);



        List<Integer> answer = new ArrayList<>();
        int lastPrefix = -1;
        long lastMul = 1;
        for (int seqDid : bestSequence) {
            int mul = didToNum[seqDid];
            if (mul == 0) {
                if (!ng[0]) {
                    answer.add(0);
                }
                break;
            }

            boolean isFirst = true;
            for (int origin : didToSequence[seqDid]) {
                int base = origin / mul;
                if (ng[origin]) {
                    continue;
                }
                if (lastPrefix == -1) {
                    answer.add(origin);
                } else {
                    int mu = (int)(modInv(lastPrefix, m) * base % m);
                    if (isFirst) {
                        mu = (int)((long)mu * (mul / lastMul));
                    }
                    answer.add(mu % m);
                }
                isFirst = false;
                lastPrefix = origin;
                lastMul = mul;
            }
        }


        StringBuilder ln = new StringBuilder();
        for (int a : answer) {
            ln.append(' ').append(a);
        }
        out.println(answer.size());
        out.println(ln.substring(1));

        out.flush();
    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a%b);
    }


    /**
     * Generates primes less than upto.
     *
     * O(nlog(logn))
     *
     * @param upto limit
     * @return array of primes
     */
    static int[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        int pi = 0;
        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                pi++;
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }

        int[] ret = new int[pi];
        int ri = 0;
        for (int i = 2 ; i < upto ; i++) {
            if (isp[i]) {
                ret[ri++] = i;
            }
        }
        return ret;
    }


    /**
     * computes inverse of x on mod M.
     *
     * @param x
     * @param M
     * @return
     */
    public static long modInv(long x, long M) {
        long y = M, a = 1, b = 0, t;
        while (y != 0) {
            long d = x / y;
            t = x - d * y; x = y; y = t;
            t = a - d * b; a = b; b = t;
        }
        return a >= 0 ? a % M : a % M + M;
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
