package aoj.vol22;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/07/31.
 */
public class P2265 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[] edge = new long[n];
        for (int i = 0; i < n ; i++) {
            edge[i] = in.nextLong();
        }
        Arrays.sort(edge);

        long max = 0;
        for (int i = n-1 ; i >= 5 ; i--) {
            long A = edge[i];
            long B = edge[i-1];
            long C = edge[i-2];
            if (A >= B + C) {
                continue;
            }
            boolean isFirst = true;
            for (int j = i-3 ; j >= 2 ; j--) {
                long D = edge[j];
                long E = edge[j-1];
                long F = edge[j-2];
                if (D >= E + F) {
                    if (isFirst) {
                        boolean canMake = false;
                        long[] edge6 = new long[]{A, B, C, D, E, F};
                        Arrays.sort(edge6);
                        do {
                            long max1 = Math.max(edge6[0], Math.max(edge6[1], edge6[2]));
                            long max2 = Math.max(edge6[3], Math.max(edge6[4], edge6[5]));
                            if (max1 < edge6[0] + edge6[1] + edge6[2] - max1 && max2 < edge6[3] + edge6[4] + edge6[5] - max2) {
                                canMake = true;
                                break;
                            }

                        } while (next_permutation(edge6));
                        if (!canMake) {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
                max = Math.max(max, A + B + C + D + E + F);
                break;
            }
            break;
        }
        out.println(max);
        out.flush();
    }

    public static boolean next_permutation(long[] num) {
        int len = num.length;
        int x = len - 2;
        while (x >= 0 && num[x] >= num[x+1]) {
            x--;
        }
        if (x == -1) return false;

        int y = len - 1;
        while (y > x && num[y] <= num[x]) {
            y--;
        }
        long tmp = num[x];
        num[x] = num[y];
        num[y] = tmp;
        java.util.Arrays.sort(num, x+1, len);
        return true;
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
