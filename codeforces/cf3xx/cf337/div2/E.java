package codeforces.cf3xx.cf337.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/09/02.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        char[] S = in.nextToken().toCharArray();
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = S[i]-'a';
        }
        int[][] table = new int[k][k];
        int[] ord = new int[k];

        CuttableSegment segment = new CuttableSegment();
        segment.tree.put(-1, -1);
        segment.tree.put(n, -1);
        for (int i = 0; i < n ; i++) {
            segment.tree.put(i, a[i]);
        }
        for (int i = 0; i < n-1; i++) {
            if (a[i] != a[i+1]) {
                table[a[i]][a[i+1]]++;
            }
        }
        while (--m >= 0) {
            int type = in.nextInt();
            if (type == 1) {
                int l = in.nextInt()-1;
                int r = in.nextInt()-1;
                int c = in.nextToken().toCharArray()[0]-'a';
                segment.doit(l, r, c, table);
            } else {
                char[] s = in.nextToken().toCharArray();
                for (int i = 0; i < s.length ; i++) {
                    ord[s[i]-'a'] = i;
                }

                int ans = n;
                for (int i = 0; i < k ; i++) {
                    for (int j = 0; j < k ; j++) {
                        if (ord[i] < ord[j]) {
                            ans -= table[i][j];
                        }
                    }
                }
                out.println(ans);
                out.flush();
            }
        }
        out.flush();
    }

    static int n;

    static class CuttableSegment {
        TreeMap<Integer, Integer> tree;

        int[] tormv;
        int ti;

        public CuttableSegment() {
            tree = new TreeMap<>();
            tormv = new int[200010];
        }

        public void doit(int a, int b, int c, int[][] tbl) {
            int left = tree.lowerKey(a);
            int right = tree.floorKey(b);
            int right2 = tree.higherKey(right);
            if (right2 == b+1) {
                right = right2;
            }

            int last = -1;
            ti = 0;
            for (int key : tree.subMap(left, true, right, true).keySet()) {
                if (key == -1 || key == n) {
                    continue;
                }
                if (last != -1) {
                    tbl[tree.get(last)][tree.get(key)]--;
                }
                if (a <= key && key <= b) {
                    tormv[ti++] = key;
                }
                last = key;
            }

            int newchar = tree.get(right);
            int rr = tree.higherKey(b);
            for (int i = 0; i < ti ; i++) {
                tree.remove(tormv[i]);
            }
            tree.put(a, c);
            if (b+1 < rr) {
                tree.put(b+1, newchar);
            }

            int newleft = tree.lowerKey(a);
            if (newleft >= 0) {
                tbl[tree.get(newleft)][c]++;
            }
            int newright = tree.higherKey(b);
            if (newright < n) {
                tbl[c][tree.get(newright)]++;
            }
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
