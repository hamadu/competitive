package csacademy.round013;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class IntervalExpectedMax {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int q = in.nextInt();
        int[][] a = new int[n][2];
        for (int i = 0; i < n ; i++) {
            a[i][0] = i;
            a[i][1] = in.nextInt();
        }
        Arrays.sort(a, (u, v) -> (u[1] != v[1]) ? u[1] - v[1] : u[0] - v[0]);

        int[][] queries = in.nextIntTable(q, 2);
        for (int i = 0; i < q ; i++) {
            queries[i][0]--;
        }

        long[] sum = new long[q];

        int[] smaller = new int[n];
        int[] smallerPartialSum = new int[n+1];
        int[] current = new int[n];
        int[] currentPartialSum = new int[n+1];
        long[] currentSum = new long[n];
        long[] currentSumPartialSum = new long[n+1];

        final int BUCKET_SIZE = 400;
        int[][] inners = new int[BUCKET_SIZE][2];
        int[][] smallerTable = new int[BUCKET_SIZE][BUCKET_SIZE];
        long[][] largerTable = new long[BUCKET_SIZE][BUCKET_SIZE];
        long[][] table = new long[BUCKET_SIZE+1][BUCKET_SIZE+1];

        for (int bidx = 0 ; ; bidx++) {
            int fr = bidx * BUCKET_SIZE;
            int to = Math.min(n, fr + BUCKET_SIZE);
            for (int j = Math.max(0, fr-BUCKET_SIZE) ; j < fr ; j++) {
                smaller[a[j][0]] = 1;
            }
            for (int j = 0; j < n ; j++) {
                smallerPartialSum[j+1] = smallerPartialSum[j] + smaller[j];
            }

            for (int j = fr ; j < to; j++) {
                current[a[j][0]] = 1;
                currentSum[a[j][0]] = a[j][1];
            }
            for (int j = 0; j < n ; j++) {
                currentPartialSum[j+1] = currentPartialSum[j] + current[j];
                currentSumPartialSum[j+1] = currentSumPartialSum[j] + currentSum[j];
            }


            // process inner current bucket
            int bi = 0;
            for (int j = fr ; j < to; j++) {
                inners[bi][0] = a[j][0];
                inners[bi][1] = a[j][1];
                bi++;
            }
            Arrays.sort(inners, 0, bi, (u, v) -> u[0] - v[0]);

            for (int r = 0 ; r < bi ; r++) {
                for (int l = r-1 ; l >= 0 ; l--) {
                    smallerTable[l][r] = smallerTable[l+1][r];
                    largerTable[l][r] = largerTable[l+1][r];
                    if (inners[l][1] < inners[r][1]) {
                        smallerTable[l][r]++;
                    } else {
                        largerTable[l][r] += inners[l][1];
                    }
                }
            }

            for (int l = 0 ; l < bi ; l++) {
                table[l][l+1] = inners[l][1];
                for (int r = l+1 ; r < bi ; r++) {
                    table[l][r+1] = table[l][r];
                    table[l][r+1] += (2L * smallerTable[l][r] + 1) * inners[r][1];
                    table[l][r+1] += 2L * largerTable[l][r];
                }
            }

            // answer the queries in current bucket.
            for (int j = 0; j < q ; j++) {
                // part One: smaller vs current
                sum[j] += 2L * (smallerPartialSum[queries[j][1]] - smallerPartialSum[queries[j][0]]) * (currentSumPartialSum[queries[j][1]] - currentSumPartialSum[queries[j][0]]);

                // part Two: inner current bucket
                int lidx = currentPartialSum[queries[j][0]];
                int ridx = currentPartialSum[queries[j][1]];
                sum[j] += table[lidx][ridx];
            }

            for (int j = fr ; j < to; j++) {
                current[a[j][0]] = 0;
                currentSum[a[j][0]] = 0;
            }

            if (to == n) {
                break;
            }
        }


        for (int i = 0; i < q ; i++) {
            double len = queries[i][1] - queries[i][0];
            double ans = sum[i] / len / len;
            out.println(String.format("%.9f", ans));
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
