package atcoder.other2016.xmas2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] foe = new int[n][3];
        for (int i = 0; i < n ; i++) {
            foe[i][0] = i;
            foe[i][1] = in.nextInt();
        }
        Arrays.sort(foe, (a, b) -> a[1] - b[1]);
        for (int i = 0; i < n ; i++) {
            foe[i][2] = i;
        }
        Arrays.sort(foe, (a, b) -> a[0] - b[0]);



        int[] perm = new int[n];
        for (int i = 0; i < n ; i++) {
            perm[i] = foe[i][2];
        }



        boolean[] visited = new boolean[n];
        List<List<Integer>> cycles = new ArrayList<>();

        int max = 0;
        for (int i = 0; i < n ; i++) {
            if (!visited[i]) {
                List<Integer> idx = new ArrayList<>();
                int cnt = 0;
                int now = i;
                while (!visited[now]) {
                    cnt++;
                    visited[now] = true;
                    idx.add(now);
                    now = perm[now];
                }
                cycles.add(idx);
                max = Math.max(max, cnt);
            }
        }

        List<String> lines = new ArrayList<>();
        boolean has = true;
        int cur = 0;
        while (cur <= 1) {
            has = false;
            List<Integer> idx = new ArrayList<>();
            cur++;

            for (List<Integer> c : cycles) {
                int sz = c.size();
                if (sz == 1) {
                    continue;
                }
                int l = cur-1;
                int r = sz-1;
                while (l < r) {
                    has = true;
                    idx.add(c.get(l));
                    idx.add(c.get(r));
                    l++;
                    r--;
                }
            }

            if (has) {
                StringBuilder line = new StringBuilder();
                line.append(idx.size()/2);
                for (int di : idx) {
                    line.append(' ');
                    line.append(di+1);
                }
                lines.add(line.toString());
            }
        }

        out.println(lines.size());
        for (String l : lines) {
            out.println(l);
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