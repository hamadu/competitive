package atcoder.other2016.xmas2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        for (int k = 0 ; k < 200 ; k++) {
            solve(in, out);
        }

        out.flush();
    }

    private static void solve(InputReader in, PrintWriter out) {
        int[][] unagi = new int[30][50];
        for (int i = 0; i < unagi.length ; i++) {
            for (int j = 0; j < unagi[0].length ; j++) {
                unagi[i][j] = in.nextInt();
            }
        }

        int[][] score = new int[30][2];
        for (int i = 0; i < 30 ; i++) {
            score[i][0] = i;
        }

        for (int i = 0; i < 50 ; i++) {
            int[] a = new int[4];
            for (int j = 0; j < 30 ; j++) {
                a[unagi[j][i]]++;
            }
            int max = Math.max(Math.max(a[0], a[1]), Math.max(a[2], a[3]));
            for (int j = 0; j < 30 ; j++) {
                score[j][1] += (a[unagi[j][i]] == max ? 1 : 0);
            }
        }

        Arrays.sort(score, (a, b) -> b[1] - a[1]);
        
        int[] ans = new int[50];
        for (int r = 0 ; r < 50 ; r++) {
            int[] a = new int[4];
            for (int i = 0; i < 10; i++) {
                int id = score[i][0];
                a[unagi[id][r]] += (i < 10) ? 2 : 1;
            }
            int max = Math.max(Math.max(a[0], a[1]), Math.max(a[2], a[3]));
            List<Integer> dd = new ArrayList<>();
            for (int i = 0; i < 4 ; i++) {
                if (a[i] == max) {
                    dd.add(i);
                }
            }
            Collections.shuffle(dd);
            ans[r] = dd.get(0);
        }

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < ans.length; i++) {
            line.append(' ').append(ans[i]);
        }
        out.println(line.substring(1));
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