package codeforces.cf3xx.cf337.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/09/02.
 */
public class E_TLE {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
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
        for (int i = 0; i < n ;) {
            int j = i;
            while (j < n && a[i] == a[j]) {
                j++;
            }
            segment.tree.add(new CuttableSegment.Segment(i, j-1, a[i]));
            i = j;
        }
        for (int i = 0; i < n-1; i++) {
            table[a[i]][a[i+1]]++;
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

                int ans = 1;
                for (int i = 0; i < k ; i++) {
                    for (int j = 0; j < k ; j++) {
                        if (ord[i] >= ord[j]) {
                            ans += table[i][j];
                        }
                    }
                }
                out.println(ans);
            }
        }
        out.flush();
    }

    static class CuttableSegment {
        TreeSet<Segment> tree;

        public CuttableSegment() {
            tree = new TreeSet<>();
        }

        public List<Segment> findCover(Segment s) {
            List<Segment> ret = new ArrayList<>();

            Segment left = tree.lower(s);
            if (left != null && left.to >= s.fr) {
                ret.add(left);
            }
            Segment ptr = s;
            while (true) {
                Segment next = tree.higher(ptr);
                if (next == null || s.to < next.fr) {
                    break;
                }
                ret.add(next);
                ptr = next;
            }
            return ret;
        }

        public List<Segment> findSwapWith(Segment s, List<Segment> with) {
            List<Segment> newSegments = new ArrayList<>();
            if (with.size() >= 1) {
                Segment ll = with.get(0);
                Segment rr = with.get(with.size()-1);
                if (ll.fr < s.fr) {
                    newSegments.add(new Segment(ll.fr, s.fr-1, ll.val));
                }
                newSegments.add(s);
                if (s.to < rr.to) {
                    newSegments.add(new Segment(s.to+1, rr.to, rr.val));
                }
            } else {
                newSegments.add(s);
            }
            return newSegments;
        }

        public void doit(int l, int r, int c, int[][] tbl) {
            Segment seg = new Segment(l, r, c);
            List<Segment> oldseg = findCover(seg);
            List<Segment> newseg = findSwapWith(seg, oldseg);

            doit2(tbl, oldseg, -1);
            tree.removeAll(oldseg);
            tree.addAll(newseg);
            doit2(tbl, newseg, 1);
        }

        private void doit2(int[][] tbl, List<Segment> seg, int diff) {
            // edges
            int sn = seg.size();
            if (sn >= 1) {
                Segment ll = seg.get(0);
                Segment left = tree.lower(seg.get(0));
                if (left != null) {
                    tbl[left.val][ll.val] += diff;
                }

                Segment rr = seg.get(sn-1);
                Segment right = tree.higher(rr);
                if (right != null) {
                    tbl[rr.val][right.val] += diff;
                }
            }

            // between
            for (int i = 0; i+1 < sn; i++) {
                Segment l0 = seg.get(i);
                Segment l1 = seg.get(i+1);
                tbl[l0.val][l1.val] += diff;
            }

            // center
            for (Segment s : seg) {
                tbl[s.val][s.val] += diff * (s.to - s.fr);
            }
        }

        static class Segment implements Comparable<Segment> {
            static int __seq = 0;
            int seq;
            int fr;
            int to;
            int val;

            public Segment(int a, int b, int v) {
                seq = ++__seq;
                fr = Math.min(a, b);
                to = Math.max(a, b);
                val = v;
            }

            @Override
            public int compareTo(Segment o) {
                if (fr == o.fr) {
                    return seq - o.seq;
                }
                return fr - o.fr;
            }

            @Override
            public String toString() {
                return fr + "/" + to + "=" + (char)('a'+val);
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