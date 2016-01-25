package codeforces.cr167.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by hama_du on 15/09/09.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] a = new int[2*n][2];
        for (int d = 0; d < 2; d++) {
            for (int i = 0; i < n ; i++) {
                a[i+d*n][0] = in.nextInt();
                a[i+d*n][1] = i+1;
            }
        }
        long MOD = in.nextLong();

        Arrays.sort(a, (u, v) -> u[0] - v[0]);

        int[] mark = new int[n+1];
        Arrays.fill(mark, -1);

        long ans = 1;
        for (int i = 0; i < 2*n ;) {
            int j = i;
            while (j < 2*n && a[j][0] == a[i][0]) {
                j++;
            }
            long two = 0;
            long one = 0;
            for (int k = i ; k < j ; k++) {
                if (mark[a[k][1]] != i) {
                    mark[a[k][1]] = i;
                    one++;
                } else {
                    one--;
                    two++;
                }
            }
            long left = j - i;
            long sub = 1;
            for (int k = 0; k < one ; k++) {
                sub *= left;
                sub %= MOD;
                left--;
            }
            for (int k = 0; k < two ; k++) {
                sub *= (left*(left-1)/2) % MOD;
                sub %= MOD;
                left -= 2;
            }
            ans *= sub;
            ans %= MOD;
            i = j;
        }
        out.println(ans % MOD);
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
