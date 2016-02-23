package hackerrank.worldcodesprint2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 2016/01/30.
 */
public class Cryptography {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        field[1] |= 2L;
        for (long p : primes) {
            int limit = (int)(field.length / p) + 1;
            for (int i = limit; i >= 1; i--) {
                if (p * i > field.length) {
                    continue;
                }
                if (field[i] >= 1) {
                    long to = i;
                    int mul = 1;
                    while (to < field.length) {
                        if (mul >= 2) {
                            for (int l = 1 ; l * mul <= 40 ; l++) {
                                if ((field[i] & (1L<<l)) >= 1) {
                                    field[(int)to] |= 1L<<(l * mul);
                                }
                            }
                        }
                        to *= p;
                        mul++;
                    }
                }
            }
        }

        list = new List[41];
        for (int i = 0; i <= 40; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 1 ; i < field.length ; i++) {
            for (int k = 1 ; k <= 40 ; k++) {
                if ((field[i] & (1L<<k)) >= 1) {
                    list[k].add(i * 1L);
                }
            }
        }
//        for (int i = 1 ; i <= 40; i++) {
//            if (list[i].size() >=1 ) {
//                debug(i, list[i].get(0));
//            }
//        }

        int t = in.nextInt();
        while (--t >= 0) {
            long n = in.nextLong();
            int k = in.nextInt();
            out.println(solve(n, k));
        }

        out.flush();
    }

    static int[] primes = generatePrimes(1000010);
    static long[] field = new long[1000010];
    static List<Long>[] list;


    private static long solve(long N, int k) {
        if (list[k].size() >= 1) {
            long last = list[k].get(list[k].size()-1);
            if (N < last) {
                int idx = Collections.binarySearch(list[k], N);
                if (idx < 0) {
                    idx = -idx-2;
                }
                if (idx >= 0) {
                    return list[k].get(idx);
                }
                return -1;
            }
        }
        if (k == 2) {
            return findPrime(N);
        }
        max = -1;
        for (long p : primes) {
            long m = 1;
            int kk = 1;
            while (m <= N) {
                if (kk == k) {
                    max = Math.max(max, m);
                }
                m *= p;
                kk++;
            }
        }

        for (int kk = 1 ; kk < k ; kk++) {
            if (k % kk == 0) {
                int di = k / kk;
                if (di == 2) {
                    for (long candidate : list[kk]) {
                        long upto = N / candidate;
                        long loe = findPrime(upto);
                        max = Math.max(candidate * loe, max);
                    }
                }
            }
        }


        return max;
    }

    static long max = 0;

    static void dfs(long current, long N, int idx, int mk, int K) {
        if (current > N || idx >= primes.length) {
            return;
        }
        if (K % mk != 0) {
            return;
        }
        if (mk == K) {
            max = Math.max(max, current);
            return;
        }
        if (current * primes[idx] > N) {
            return;
        }
        debug(current, N, idx, mk, K);


        int i = 0;
        while (current <= N) {
            if (K % (mk * (i+1)) == 0) {
                dfs(current, N, idx+1, mk * (i+1), K);
            }
            current *= primes[idx];
            i++;
        }
    }

    static long findPrime(long n) {
        while (true) {
            boolean isP = true;
            for (long f = 2 ; f * f <= n ; f++) {
                if (n % f == 0) {
                    isP = false;
                    break;
                }
            }
            if (isP) {
                return n;
            }
            n--;
        }
    }

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
                res += c-'0';
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
