package codeforces.cr307.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/06/13.
 */
public class B {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] a = in.nextToken().toCharArray();
        int n = a.length;
        int[] deg = new int[26];
        for (int i = 0; i < a.length ; i++) {
            deg[a[i]-'a']++;
        }

        char[][] str = new char[2][];
        int[][] req = new int[2][26];
        for (int i = 0 ; i <= 1 ; i++) {
            char[] z = in.nextToken().toCharArray();
            str[i] = z;
            for (int j = 0 ; j < z.length ; j++) {
                req[i][z[j]-'a']++;
            }
        }

        int max = 0;
        int maxU1 = 0;
        int maxU2 = 0;
        for (int u1 = 0 ; u1 <= n ; u1++) {
            boolean canPlace = true;
            int u2 = Integer.MAX_VALUE;
            for (int al = 0 ; al < 26 ; al++) {
                int left = deg[al] - req[0][al] * u1;
                if (left < 0) {
                    canPlace = false;
                    break;
                }
                if (req[1][al] >= 1) {
                    u2 = Math.min(u2, left / req[1][al]);
                }
            }
            if (!canPlace) {
                break;
            }
            if (max < u1 + u2) {
                max = u1 + u2;
                maxU1 = u1;
                maxU2 = u2;
            }
        }


        out.println(build(deg, new int[]{maxU1, maxU2}, str));



        out.flush();
    }

    private static String build(int[] deg, int[] use, char[][] str) {
        StringBuilder line = new StringBuilder();
        for (int i = 0 ; i <= 1 ; i++) {
            for (int c = 0 ; c < use[i] ; c++) {
                for (char ci : str[i]) {
                    line.append(ci);
                    deg[ci-'a']--;
                }
            }
        }
        for (int al = 0 ; al < 26 ; al++) {
            for (int d = 0 ; d < deg[al] ; d++) {
                line.append((char)('a' + al));
            }
        }
        return line.toString();
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
