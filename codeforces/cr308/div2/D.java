package codeforces.cr308.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/06/20.
 */
public class D {
    static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a%b);
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] pt = new int[n][2];
        for (int i = 0; i < n ; i++) {
            pt[i][0] = in.nextInt();
            pt[i][1] = in.nextInt();
        }


        long all = 1L * n * (n - 1) * (n - 2) / 6;
        long dis = 0;
        for (int i = 0 ; i < n ; i++) {
            int[] dx = new int[n-i-1];
            int[] dy = new int[n-i-1];

            Map<Integer,Integer> ctDeg = new HashMap<>();
            for (int j = i+1 ; j < n ; j++) {
                int ii = j-i-1;
                dx[ii] = pt[j][0] - pt[i][0];
                dy[ii] = pt[j][1] - pt[i][1];
                if (dx[ii] == 0) {
                    dy[ii] = 1;
                } else {
                    if (dx[ii] < 0) {
                        dx[ii] *= -1;
                        dy[ii] *= -1;
                    }
                    int g = gcd(Math.abs(dx[ii]), Math.abs(dy[ii]));
                    dx[ii] /= g;
                    dy[ii] /= g;
                }
                int did = dx[ii] * 512 + dy[ii];
                if (ctDeg.containsKey(did)) {
                    ctDeg.put(did, ctDeg.get(did)+1);
                } else {
                    ctDeg.put(did, 1);
                }
            }
            for (int qty : ctDeg.values()) {
                dis += qty * (qty - 1) / 2;
            }
        }
        out.println(all - dis);
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
