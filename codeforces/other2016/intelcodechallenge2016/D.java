package codeforces.other2016.intelcodechallenge2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int m = in.nextInt();
        char[] c = in.nextToken().toCharArray();

        int n = c.length;
        int[][] codes = new int[n][2];
        for (int i = 0; i < n ; i++) {
            codes[i][0] = c[i]-'a';
            codes[i][1] = i;
        }
        Arrays.sort(codes, (a, b) -> (a[0] == b[0]) ? a[1] - b[1] : a[0] - b[0]);

        int[] imos = new int[n+1];
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < n ; ) {
            int j = i;
            int[] prevImos = imos.clone();
            while (j < n && codes[i][0] == codes[j][0]) {
                int fr = Math.max(0, codes[j][1]-m+1);
                int to = codes[j][1]+1;
                imos[fr]++;
                imos[to]--;
                j++;
            }

            int[] csum = imos.clone();
            for (int k = 0; k < n; k++) {
                csum[k+1] += csum[k];
            }

            boolean filledAll = true;
            for (int k = 0; k < n-m+1 ; k++) {
                if (csum[k] == 0) {
                    filledAll = false;
                    break;
                }
            }

            if (filledAll) {
                // ok. find the answer carefully.
                csum = prevImos.clone();
                for (int k = 0; k < n-m+1 ; k++) {
                    csum[k+1] += csum[k];
                }

                int L = i;
                for (int k = 0; k < n-m+1 ; k++) {
                    if (csum[k] == 0) {
                        ans.append((char)('a' + codes[i][0]));
                        while (L+1 <= j) {
                            int fr0 = Math.max(0, codes[L][1]-m+1);
                            int to0 = codes[L][1]+1;
                            int fr1 = L+1 < j ? Math.max(0, codes[L+1][1]-m+1) : n;
                            if (fr0 <= k && k <= to0 && k < fr1) {
                                for (int l = fr0 ; l < to0 ; l++) {
                                    csum[l]++;
                                }
                                break;
                            }
                            L++;
                        }
                    }
                }
                break;
            } else {
                // ng. go to next character
                for (int k = i ; k < j ; k++) {
                    ans.append((char)('a' + codes[k][0]));
                }
            }
            i = j;
        }


        out.println(ans.toString());
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