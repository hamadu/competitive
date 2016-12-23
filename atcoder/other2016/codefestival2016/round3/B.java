package atcoder.other2016.codefestival2016.round3;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class B {
    private static final int INF = 10000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = in.nextInts(n);
        char[] s = in.nextToken().toCharArray();

        int[] base = new int[n];
        int ok = 1;
        int ng = n+1;
        while (ng - ok > 1) {
            int med = (ng+ok)/2;
            for (int i = 0; i < n ; i++) {
                base[i] = a[i] >= med ? 1 : 0;
            }
            if (solve(base, s) == 1) {
                ok = med;
            } else {
                ng = med;
            }
        }

        out.println(ok);
        out.flush();
    }

    private static int solve(int[] base, char[] s) {
        int n = base.length;
        int sum = 0;
        for (int i = 0; i < n ; i++) {
            sum += base[i];
        }
        if (sum == 0 || sum == n) {
            return sum == 0 ? 0 : 1;
        }



        Queue<Segment>[] onezero = new Queue[2];
        for (int i = 0; i < 2 ; i++) {
            onezero[i] = new PriorityQueue<>();
        }
        TreeSet<Segment> tree = new TreeSet<>((a, b) -> a.left - b.left);

        Segment head = null;
        Segment tail = null;
        for (int i = 0; i < n ; ) {
            int j = i;
            while (j < n && base[i] == base[j]) {
                j++;
            }
            int fr = i == 0 ? -INF : i;
            int to = j == n ? INF : j-1;
            Segment seg = new Segment(fr, to, base[i]);
            onezero[base[i]].add(seg);
            tree.add(seg);
            if (fr == -INF) {
                head = seg;
            } else if (to == INF) {
                tail = seg;
            }
            i = j;
        }

        int plus = 0;
        int minus = 0;

        int diff = 0;
        for (int i = 0; i < s.length ; i++) {
            int my, ot;
            if (s[i] == 'M') {
                diff++;
                plus++;
                my = 0;
                ot = 1;
            } else {
                diff--;
                minus++;
                my = 1;
                ot = 0;
            }

            while (onezero[my].size() >= 1) {
                Segment seg = onezero[my].peek();
                if (!seg.available) {
                    onezero[my].poll();
                    continue;
                }
                int[] tl = seg.eval(plus, minus);
                if (tl[0] > tl[1]) {
                    onezero[my].poll();
                    Segment L = tree.lower(seg);
                    Segment R = tree.higher(seg);
                    seg.available = false;
                    L.available = false;
                    R.available = false;
                    tree.remove(seg);
                    tree.remove(L);
                    tree.remove(R);

                    Segment newSeg = new Segment(L.left, R.right, L.value);
                    newSeg.cnt = L.cnt + R.cnt;
                    if (newSeg.value == 1) {
                        newSeg.cnt += diff;
                    } else {
                        newSeg.cnt -= diff;
                    }

                    tree.add(newSeg);
                    onezero[ot].add(newSeg);
                } else {
                    break;
                }
            }
        }

        for (Segment t : tree) {
            int[] fin = t.eval(plus, minus);
            if (fin[0] <= 0 && 0 <= fin[1]) {
                return t.value;
            }
        }
        throw new RuntimeException("arien");
    }

    static class Segment implements Comparable<Segment> {
        int left;
        int right;
        int value;
        int cnt;
        boolean available;

        public Segment(int fr, int to, int v) {
            left = fr;
            right = to;
            value = v;
            cnt = to-fr+1;
            available = true;
        }

        @Override
        public int compareTo(Segment o) {
            return cnt - o.cnt;
        }

        public int[] eval(int plus, int minus) {
            int tl = left;
            int tr = right;
            if (value == 0) {
                tl -= minus;
                tr -= plus;
            } else {
                tl -= plus;
                tr -= minus;
            }
            return new int[]{tl, tr};
        }
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
