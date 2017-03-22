package csacademy.round020;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        char[][] s = new char[n][];
        for (int i = 0; i < n ; i++) {
            s[i] = in.nextToken().toCharArray();
        }
        Arrays.sort(s, (a, b) -> a.length - b.length);

        boolean[][] hasPalindromePrefix = new boolean[n][];
        boolean[][] hasPalindromeSuffix = new boolean[n][];
        for (int i = 0; i < n ; i++) {
            char[] S = s[i];
            int L = S.length;
            hasPalindromePrefix[i] = new boolean[L];
            hasPalindromeSuffix[i] = new boolean[L];
            int[][] pal =  manachar(S);

            for (int j = 0; j < L ; j++) {
                int head = j - pal[0][j] + 1;
                int tail = j + pal[0][j] - 1;
                if (head == 0) {
                    hasPalindromePrefix[i][tail] = true;
                }
                if (tail == L-1) {
                    hasPalindromeSuffix[i][head] = true;
                }
            }
            for (int j = 0; j < L-1 ; j++) {
                int head = j - pal[1][j] + 1;
                int tail = j + pal[1][j];
                if (head == 0) {
                    hasPalindromePrefix[i][tail] = true;
                }
                if (tail == L-1) {
                    hasPalindromeSuffix[i][head] = true;
                }
            }
        }

        long ans = 0;

        RollingHash roller = new RollingHash(1145141919);

        // |shorter| + |longer(me)|
        {
            Map<Long, Integer> palindromeHalf = new HashMap<>();
            for (int i = 0; i < n; i++) {
                char[] S = s[i];
                int L = S.length;
                long suffix = 0;
                for (int k = L-1; k >= 0; k--) {
                    suffix = roller.next(suffix, S[k]);
                    if (k == 0 || hasPalindromePrefix[i][k-1]) {
                        ans += palindromeHalf.getOrDefault(suffix, 0);
                    }
                }
                long prefix = hashAsc(s[i], roller);
                palindromeHalf.put(prefix, palindromeHalf.getOrDefault(prefix, 0)+1);
            }
        }

        // |longer(me)| + |shorter|
        {
            Map<Long, Integer> palindromeHalf = new HashMap<>();
            for (int i = 0; i < n; i++) {
                char[] S = s[i];
                int L = S.length;
                long prefix = 0;
                for (int k = 0 ; k < L ; k++) {
                    prefix = roller.next(prefix, S[k]);
                    if (k == L-1 || hasPalindromeSuffix[i][k+1]) {
                        ans += palindromeHalf.getOrDefault(prefix, 0);
                    }
                }
                long suffix = hashDesc(s[i], roller);
                palindromeHalf.put(suffix, palindromeHalf.getOrDefault(suffix, 0)+1);
            }
        }

        out.println(ans);
        out.flush();
    }

    public static class RollingHash {
        static final long MASK = (1L<<32)-1;

        long[] mul;
        long[] mod;

        public RollingHash(long seed) {
            mul = new long[2];
            mod = new long[2];
            Random rand = new Random(seed);
            for (int i = 0; i < 2 ; i++) {
                mul[i] = BigInteger.probablePrime(30, rand).longValue();
                mod[i] = BigInteger.probablePrime(30, rand).longValue();
            }
        }

        public long next(long have, char c) {
            long[] x = decompose(have);
            for (int i = 0; i < 2 ; i++) {
                x[i] *= mul[i];
                x[i] += c;
                x[i] %= mod[i];
            }
            return makeValue(x);
        }

        public long[] decompose(long val) {
            return new long[]{ val>>>32, val&MASK };
        }

        public long makeValue(long[] a) {
            return a[0]<<32L|a[1];
        }
    }

    public static long hashAsc(char[] S, RollingHash roller) {
        int L = S.length;
        long[] prefix = new long[2];
        for (int j = 0; j < L ; j++) {
            for (int i = 0; i < 2 ; i++) {
                prefix[i] *= roller.mul[i];
                prefix[i] += S[j];
                prefix[i] %= roller.mod[i];
            }
        }
        return roller.makeValue(prefix);
    }

    public static long hashDesc(char[] S, RollingHash roller) {
        int L = S.length;
        long[] suffix = new long[2];
        for (int j = L-1 ; j >= 0; j--) {
            for (int i = 0; i < 2 ; i++) {
                suffix[i] *= roller.mul[i];
                suffix[i] += S[j];
                suffix[i] %= roller.mod[i];
            }
        }
        return roller.makeValue(suffix);
    }

    public static int[][] manachar(char[] c) {
        int[] odd = manacharSub(c);
        int n = c.length;
        char[] c2 = new char[2*n-1];
        for (int i = 0; i < n ; i++) {
            c2[2*i] = c[i];
        }
        for (int i = 0; i < n-1 ; i++) {
            c2[2*i+1] = '$';
        }
        int[] e = manacharSub(c2);
        int[] even = new int[n-1];
        for (int i = 0; i < n-1 ; i++) {
            even[i] = e[i*2+1]/2;
        }
        return new int[][]{odd, even};
    }

    public static int[] manacharSub(char[] c) {
        int n = c.length;
        int[] ret = new int[n];
        int i = 0;
        int j = 0;
        while (i < n) {
            while (i-j >= 0 && i+j < n && c[i-j] == c[i+j]) {
                j++;
            }
            ret[i] = j;
            int k = 1;
            while (i - k >= 0 && i+k < n && k+ret[i-k] < j) {
                ret[i+k] = ret[i-k];
                k++;
            }
            i += k;
            j -= k;
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
