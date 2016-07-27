package codeforces.cf3xx.cf352.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.TreeMap;

/**
 * Created by hama_du on 2016/07/26.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long k = in.nextInt();
        long[] a = in.nextLongs(n);

        shuffleAndSort(a);
        long[] imos = new long[n+1];
        for (int i = 0; i < n ; i++) {
            imos[i+1] = imos[i] + a[i];
        }

        long left = -1;
        {
            long sum = 0;
            int cnt = 0;
            for (int i = 0; i < n; i++) {
                cnt++;
                sum += a[i];
                long needToFill = a[i]*cnt-sum;
                long leftOP = k-needToFill;
                long canUp = a[i]+leftOP/cnt+((leftOP%cnt != 0) ? 1 : 0);
                if (i == n-1 || canUp <= a[i+1]) {
                    left = canUp - ((leftOP % cnt != 0) ? 1 : 0);
                    break;
                }
            }
        }

        long right = -1;
        {
            long sum = 0;
            int cnt = 0;
            for (int i = n-1 ; i >= 0; i--) {
                cnt++;
                sum += a[i];
                long needToFill = sum-a[i]*cnt;
                long leftOP = k-needToFill;
                long canDown = a[i]-leftOP/cnt-((leftOP%cnt != 0) ? 1 : 0);
                if (i == 0 || canDown >= a[i-1]) {
                    right = canDown + ((leftOP % cnt != 0) ? 1 : 0);
                    break;
                }
            }
        }

        if (left < right) {
            out.println(right-left);
        } else {
            long sum = 0;
            for (int i = 0; i < n ; i++) {
                sum += a[i];
            }
            if (sum % n == 0) {
                out.println(0);
            } else {
                out.println(1);
            }
        }
        out.flush();
    }


    // against for quick-sort killer
    static void shuffleAndSort(long[] a) {
        int n = a.length;
        for (int i = 0; i < n ; i++) {
            int idx = (int)(Math.random() * i);
            long tmp = a[idx];
            a[idx] = a[i];
            a[i] = tmp;
        }
        Arrays.sort(a);
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

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
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
