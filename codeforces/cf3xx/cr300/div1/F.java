package codeforces.cf3xx.cr300.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class F {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] r = new int[n];
        for (int i = 0 ; i < n ; i++) {
            r[i] = in.nextInt();
        }

        int[] imos = new int[n+2];
        for (int i = 1 ; i < n ; i++) {
            int last_ary = -1;
            int prev_parent = -1;
            for (int ary = 1 ; ary < n ; ary++) {
                int parent = (i - 1) / ary;
                if (prev_parent == parent) {
                    break;
                }
                prev_parent = parent;
                last_ary = ary+1;
                if (r[parent] > r[i]) {
                    imos[ary]++;
                    imos[ary+1]--;
                }
            }

            for (int p = prev_parent ; p >= 0 ; p--) {
                int to_ary = -1;
                if (p == 0) {
                    to_ary = n;
                } else {
                    to_ary = (i - 1) / p;
                }
                if (r[p] > r[i]) {
                    imos[last_ary]++;
                    imos[to_ary+1]--;
                }
                last_ary = to_ary+1;
            }
        }


        for (int i = 2 ; i <= n ; i++) {
            imos[i] += imos[i-1];
        }

        StringBuilder b = new StringBuilder();
        for (int i = 1 ; i < n ; i++) {
            b.append(' ').append(imos[i]);
        }
        out.println(b.substring(1));
        out.flush();
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
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
}



