package codeforces.cf3xx.cf350.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/08/05.
 */
public class F {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] c = in.nextToken().toCharArray();
        char[] part = in.nextToken().toCharArray();

        int n = c.length;
        int[] digits = new int[10];
        for (int i = 0 ; i < n ; i++) {
            digits[c[i]-'0']++;
        }
        for (int i = 0; i < part.length ; i++) {
            digits[part[i]-'0']--;
        }

        for (int k10 = 1 ; k10 <= 6 ; k10++) {
            int min = (int)Math.pow(10, k10-1);
            int max = (int)Math.pow(10, k10);
            int left = n - k10;
            if (min <= left && left < max) {
                int[] d10 = digits.clone();
                for (char ci : String.valueOf(left).toCharArray()) {
                    d10[ci-'0']--;
                }
                int sum = 0;
                for (int i = 0; i < 10 ; i++) {
                    sum += d10[i];
                }

                // debug(left, d10);
                String best = "~";
                if (sum == 0) {
                    best = String.valueOf(part);
                } else {
                    if (part[0] != '0') {
                        StringBuilder z = new StringBuilder();
                        z.append(part);
                        for (int d = 0; d <= 9; d++) {
                            for (int i = 0; i < d10[d]; i++) {
                                z.append((char) ('0'+d));
                            }
                        }
                        String zz = z.toString();
                        if (zz.compareTo(best) < 0) {
                            best = zz;
                        }
                    }

                    // non-part-first
                    {
                        int fd = -1;
                        if (d10[0] >= 0) {
                            for (fd = 1; fd <= 9; fd++) {
                                if (d10[fd] >= 1) {
                                    d10[fd]--;
                                    break;
                                }
                            }
                        }
                        for (int du = 0; du <= 10; du++) {
                            StringBuilder z = new StringBuilder();
                            if (fd != -1) {
                                z.append((char) (fd+'0'));
                            }
                            for (int d = 0; d <= 10; d++) {
                                if (d == du) {
                                    z.append(part);
                                }
                                if (d <= 9) {
                                    for (int i = 0; i < d10[d]; i++) {
                                        z.append((char) (d+'0'));
                                    }
                                }
                            }

                            String zz = z.toString();
                            if (zz.charAt(0) != '0') {
                                if (zz.compareTo(best) < 0) {
                                    best = zz;
                                }
                            }
                        }
                    }
                }

                if (!best.equals("~")) {
                    out.println(best);
                    break;
                }
            }
        }
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
