package codeforces.cf2xx.cr254.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/08/03.
 */
public class C {
    private static final int INF = 1145141919;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        Segment[] units = new Segment[n+1];
        for (int i = 1 ; i <= n; i++) {
            units[i] = new Segment(i, i, INF);
        }

        TreeSet<Segment> tree = new TreeSet<>();
        BITRange range = new BITRange(n+1);
        for (int i = 0; i < n ; i++) {
            tree.add(new Segment(i+1, i+1, i+1));
        }

        for (int i = 0; i < m ; i++) {
            int type = in.nextInt();
            if (type == 1) {
                int l = in.nextInt();
                int r = in.nextInt();
                int x = in.nextInt();

                // left edge
                Segment segL = tree.floor(units[l]);
                Segment segR = tree.floor(units[r]);

                // debug(l, r, segL, segR);
                if (segL == segR) {
                    range.addRange(l, r, Math.abs(x - segL.value));
                    tree.remove(segL);
                    if (l - segL.fr >= 1) {
                        tree.add(new Segment(segL.fr, l-1, segL.value));
                    }
                    if (segL.to - r >= 1) {
                        tree.add(new Segment(r+1, segL.to, segL.value));
                    }
                } else {
                    tree.remove(segL);
                    tree.remove(segR);

                    if (segL.fr <= l && l <= segL.to) {
                        range.addRange(l, segL.to, Math.abs(x - segL.value));
                        if (l - segL.fr >= 1) {
                            tree.add(new Segment(segL.fr, l-1, segL.value));
                        }
                    }

                    // right edge
                    if (segR.fr <= r && r <= segR.to) {
                        range.addRange(segR.fr, r, Math.abs(x - segR.value));
                        if (segR.to - r >= 1) {
                            tree.add(new Segment(r+1, segR.to, segR.value));
                        }
                    }
                }
                while (true) {
                    Segment edgeL = tree.ceiling(units[l]);
                    if (edgeL == null) {
                        break;
                    }
                    if (l <= edgeL.fr && edgeL.to <= r) {
                        long add = Math.abs(x - edgeL.value);
                        range.addRange(edgeL.fr, edgeL.to, add);
                        tree.remove(edgeL);
                    } else {
                        break;
                    }
                }
                tree.add(new Segment(l, r, x));

//                for (Segment seg : tree) {
//                    debug(seg.fr, seg.to, seg.value);
//                }
//                debug("---");

            } else {
                int l = in.nextInt();
                int r = in.nextInt();
                out.println(range.range(l, r));
            }
        }




        out.flush();
    }

    // 区間加算
    static class BITRange {
        BIT bit0;
        BIT bit1;
        BITRange(int n) {
            bit0 = new BIT(n);
            bit1 = new BIT(n);
        }

        void addRange(int l, int r, long x) {
            // debug("add",l,r,x);
            if (l > r) {
                return;
            }
            bit0.add(l, -x * (l-1));
            bit1.add(l, x);
            bit0.add(r+1, x * r);
            bit1.add(r+1, -x);
        }

        long range(int l, int r) {
            long right = bit0.sum(r) + bit1.sum(r) * r;
            long left = bit0.sum(l-1) + bit1.sum(l-1) * (l-1);
            return right - left;
        }

        long get(int i) {
            return range(i, i);
        }
    }

    // BIT, 1-indexed, range : [a,b]
    static class BIT {
        long N;
        long[] data;
        BIT(int n) {
            N = n;
            data = new long[n+1];
        }

        long sum(int i) {
            long s = 0;
            while (i > 0) {
                s += data[i];
                i -= i & (-i);
            }
            return s;
        }

        long range(int i, int j) {
            return sum(j) - sum(i-1);
        }

        void add(int i, long x) {
            while (i <= N) {
                data[i] += x;
                i += i & (-i);
            }
        }
    }

    static class Segment implements Comparable<Segment> {
        // [fr,to]
        int fr;
        int to;
        int value;

        Segment(int f, int t, int v) {
            fr = f;
            to = t;
            value = v;
        }

        @Override
        public int compareTo(Segment o) {
            if (fr == o.fr) {
                return value - o.value;
            }
            return fr - o.fr;
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
