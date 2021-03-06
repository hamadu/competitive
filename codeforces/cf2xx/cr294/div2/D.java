package codeforces.cf2xx.cr294.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by dhamada on 15/05/17.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long[] table = new long[26];
        for (int i = 0; i < 26; i++) {
            table[i] = in.nextInt();
        }

        char[] c = in.nextToken().toCharArray();
        int n = c.length;
        long[][] imos = new long[n+1][2];
        for (int i = 0 ; i < n ; i++) {
            imos[i+1][0] = imos[i][0] + table[c[i]-'a'];
            imos[i+1][1] = i+1;
        }

        Arrays.sort(imos, new Comparator<long[]>() {
            @Override
            public int compare(long[] o1, long[] o2) {
                if (o1[0] == o2[0]) {
                    return Long.compare(o1[1], o2[1]);
                }
                return Long.compare(o1[0], o2[0]);
            }
        });


        long sum = 0;
        for (int i = 0 ; i <= n ; ) {
            int j = i;
            while (j <= n && imos[j][0] == imos[i][0]) {
                j++;
            }

            long[] deg  = new long[26];
            for (int k = j-1 ; k >= i ; k--) {
                int ep = (int) imos[k][1];
                int sp = ep - 1;
                if (ep < n) {
                    deg[c[ep] - 'a']++;
                }
                if (sp >= 0) {
                    sum += deg[c[sp] - 'a'];
                }
            }
            i = j;
        }
        out.println(sum);
        out.flush();
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
