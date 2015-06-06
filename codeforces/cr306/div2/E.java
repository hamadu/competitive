package codeforces.cr306.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/06/06.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt();
        }

        String l = solve(a);
        if (l.equals("")) {
            out.println("NO");
        } else {
            out.println("YES");
            out.println(l);
        }
        out.flush();
    }

    private static String solve(int[] a) {
        int n = a.length;
        if (a[n-1] == 1) {
            return "";
        }
        int zeroCnt = 0;
        int tail = n-1;
        while (tail >= 0) {
            if (a[tail] == 1) {
                break;
            }
            tail--;
            zeroCnt++;
        }

        int[] open = new int[n];
        int[] close = new int[n];
        if (zeroCnt >= 3) {
            int left = n-zeroCnt;
            int right = n-2;
            if (zeroCnt % 2 == 0) {
                left++;
            }
            open[left]++;
            close[right]++;
        } else if (zeroCnt == 2) {

            boolean found = false;
            for (int i = 0 ; i < n-2 ; i++) {
                if (a[i] == 0) {
                    found = true;
                    open[i]++;
                    open[i+1]++;
                    break;
                }
            }
            if (!found) {
                return "";
            }
            close[n-2] += 2;
        }

        StringBuilder b = new StringBuilder();
        for (int i = 0 ; i < n ; i++) {
            b.append("->");
            for (int j = 0 ; j < open[i] ; j++) {
                b.append('(');
            }
            b.append(a[i]);
            for (int j = 0 ; j < close[i] ; j++) {
                b.append(')');
            }
        }
        return b.substring(2);
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
