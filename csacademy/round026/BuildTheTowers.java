package csacademy.round026;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class BuildTheTowers {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] target = in.nextInts(n);

        try {
            List<Integer> operations = solve(target);
            int[] result = simulate(n, operations);
            for (int i = 0; i < n ; i++) {
                if (result[i] != target[i]) {
                    throw new RuntimeException("ng");
                }
            }

            StringBuilder line = new StringBuilder();
            for (Integer operation : operations) {
                line.append(' ').append(operation+1);
            }
            out.println(operations.size());
            if (operations.size() >= 1) {
                out.println(line.substring(1));
            } else {
                out.println();
            }
        } catch (Exception e) {
            out.println(-1);
        }
        out.flush();
    }

    private static int[] simulate(int n, List<Integer> op) {
        int[] result = new int[n];
        for (int pos : op) {
            if (result[pos] != 0) {
                throw new RuntimeException();
            }

            int[] ot = new int[4];
            if (pos-1 >= 0) {
                ot[result[pos-1]]++;
            }
            if (pos+1 < n) {
                ot[result[pos+1]]++;
            }
            if (ot[1] == 0) {
                result[pos] = 1;
            } else if (ot[1] == 1 && ot[2] == 1) {
                result[pos] = 3;
                if (pos-1 >= 0) {
                    result[pos-1] = 0;
                }
                if (pos+1 < n) {
                    result[pos+1] = 0;
                }
            } else {
                result[pos] = 2;
                if (pos-1 >= 0 && result[pos-1] == 1) {
                    result[pos-1] = 0;
                }
                if (pos+1 < n && result[pos+1] == 1) {
                    result[pos+1] = 0;
                }
            }
        }
        return result;
    }

    private static List<Integer> solve(int[] target) {
        int n = target.length;
        List<Integer> result = new ArrayList<>();
        for (int i = 0 ; i < n ; i++) {
            if (target[i] == 3) {
                result.add(i);    // 0 1 0
                result.add(i+1);  // 0 0 2
                result.add(i-1);  // 1 0 2
                result.add(i);    // 0 3 0
            }
        }

        for (int i = 0; i < n ; i++) {
            if (target[i] == 2) {
                if (i >= 1 && target[i-1] != 3) {
                    result.add(i-1); // 1 0 ?
                    result.add(i);   // 0 2 ?
                } else if (i < n-1 && target[i+1] != 3) {
                    result.add(i+1); // ? 0 1
                    result.add(i);   // ? 2 0
                }
            }
        }

        for (int i = 0; i < n ; i++) {
            if (target[i] == 1) {
                result.add(i);
            }
        }
        return result;
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
