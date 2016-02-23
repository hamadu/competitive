package codeforces.aimtech2016.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/02/08.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long deleteCost = in.nextInt();
        long changeCost = in.nextInt();
        long[] a = new long[n];
        for (int i = 0 ; i < n ; i++) {
            a[i] = in.nextLong();
        }
        out.println(solve(a, deleteCost, changeCost));
        out.flush();
    }

    private static long solve(long[] a, long deleteCost, long changeCost) {
        int n = a.length;
        if (n == 1) {
            return 0;
        }
        Set<Long>[] divisorLeft = new Set[3];
        Set<Long>[] divisorRight = new Set[3];
        for (int i = 0 ; i <= 2 ; i++) {
            divisorLeft[i] = divisors(a[0] + i - 1);
            divisorRight[i] = divisors(a[n-1] + i - 1);
        }
        long left = solveHalf(a, divisorLeft, deleteCost, changeCost);
        long[] b = new long[n];
        for (int i = 0; i < n ; i++) {
            b[i] = a[n-1-i];
        }
        long right = solveHalf(b, divisorRight, deleteCost, changeCost);


        long min = Math.min(left, right);
        for (int dl = 0; dl <= 2; dl++) {
            for (int dr = 0; dr <= 2; dr++) {
                for (long l : divisorLeft[dl]) {
                    if (!divisorRight[dr].contains(l)) {
                        continue;
                    }
                    long delete = (n-2)*deleteCost;
                    long change = (Math.abs(1-dl)+Math.abs(1-dr))*changeCost;
                    min = Math.min(min, delete+change);
                    int li = 1;
                    int ri = n-2;
                    while (li <= ri) {
                        for (int d = -1 ; d <= 1 ; d++) {
                            if ((a[li]+d) % l == 0) {

                            }
                        }
                        li++;
                    }
                }
            }
        }
        return min;
    }


    static long solveHalf(long[] a, Set<Long>[] divisors, long deleteCost, long changeCost) {
        int n = a.length;
        long min = Long.MAX_VALUE;
        for (int d = 0; d <= 2; d++) {
            long change = (d == 1) ? 0 : changeCost;
            long delete = deleteCost * (n - 1);
            min = Math.min(min, change + delete);
            for (long l : divisors[d]) {
                for (int i = 1; i < n; i++) {
                    if (a[i] % l == 0) {
                    } else if ((a[i]+1) % l == 0 || (a[i]-1) % l == 0) {
                        change += changeCost;
                    } else {
                        break;
                    }
                    delete -= deleteCost;
                    min = Math.min(min, change + delete);
                }
            }
        }
        return min;
    }

    static Set<Long> divisors(long l) {
        Set<Long> set = new HashSet<>();
        for (long i = 1 ; i * i <= l ; i++) {
            if (l % i == 0) {
                set.add(i);
                set.add(l / i);
            }
        }
        set.remove(1L);
        return set;
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
