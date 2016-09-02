package codeforces.cf3xx.cf337.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/08/30.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] seg = in.nextIntTable(n, 4);

        List<int[]> vertical = new ArrayList<>();
        List<int[]> horizontal = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            if (seg[i][0] == seg[i][2]) {
                vertical.add(seg[i]);
            } else {
                horizontal.add(seg[i]);
            }
        }
        int vn = vertical.size();
        int hn = horizontal.size();

        Collections.sort(vertical, (v0, v1) -> v0[0] - v1[0]);
        Collections.sort(horizontal, (h0, h1) -> h0[1] - h1[1]);

        List<MeldableSegment> vs = new ArrayList<>();
        List<Integer> vx = new ArrayList<>();
        for (int i = 0; i < vn ; ) {
            int j = i;
            while (j < vn && vertical.get(i)[0] == vertical.get(j)[0]) {
                j++;
            }
            MeldableSegment segment = new MeldableSegment();
            for (int k = i ; k < j ; k++) {
                int[] s = vertical.get(k);
                segment.meld(new MeldableSegment.Segment(s[1], s[3]));
            }
            vs.add(segment);
            vx.add(vertical.get(i)[0]);
            i = j;
        }

        List<MeldableSegment> hs = new ArrayList<>();
        List<Integer> hy = new ArrayList<>();
        for (int i = 0; i < hn ; ) {
            int j = i;
            while (j < hn && horizontal.get(i)[1] == horizontal.get(j)[1]) {
                j++;
            }
            MeldableSegment segment = new MeldableSegment();
            for (int k = i ; k < j ; k++) {
                int[] s = horizontal.get(k);
                segment.meld(new MeldableSegment.Segment(s[0], s[2]));
            }
            hs.add(segment);
            hy.add(horizontal.get(i)[1]);
            i = j;
        }

        long total = 0;
        for (List<MeldableSegment> sl : new List[]{vs, hs}) {
            for (MeldableSegment s : sl) {
                for (MeldableSegment.Segment se : s.tree) {
                    total += se.to - se.fr + 1;
                }
            }
        }

        Set<Integer> yset = new HashSet<>();
        yset.add((int)(-1e9)-1);
        for (MeldableSegment ms : vs) {
            for (MeldableSegment.Segment s : ms.tree) {
                yset.add(s.fr);
                yset.add(s.to);
            }
        }
        yset.addAll(hy);
        List<Integer> yl = new ArrayList<>(yset);
        Collections.sort(yl);
        Map<Integer,Integer> ymap = new HashMap<>();
        for (int i = 0; i < yl.size() ; i++) {
            ymap.put(yl.get(i), i);
        }

        for (MeldableSegment ms : vs) {
            for (MeldableSegment.Segment s : ms.tree) {
                s.fr = ymap.get(s.fr);
                s.to = ymap.get(s.to);
            }
        }

        List<Event> event = new ArrayList<>();
        for (int i = 0 ; i < hy.size() ; i++) {
            int y0 = ymap.get(hy.get(i));
            for (MeldableSegment.Segment s : hs.get(i).tree) {
                event.add(new Event(1, s.fr, y0));
                event.add(new Event(0, s.to+1, y0));
            }
        }
        for (int i = 0; i < vx.size() ; i++) {
            event.add(new Event(2, vx.get(i), -1));
        }
        Collections.sort(event);


        long dupes = 0;
        int[] ydeg = new int[300000];
        BIT ybit = new BIT(300000);

        int qi = 0;
        for (Event e : event) {
            if (e.kind == 2) {
                // yay, query time!
                for (MeldableSegment.Segment s : vs.get(qi).tree) {
                    dupes += ybit.range(s.fr, s.to);
                }
                qi++;
            } else if (e.kind == 1) {
                // add
                ydeg[e.y]++;
                if (ydeg[e.y] == 1) {
                    ybit.add(e.y, 1);
                }
            } else {
                // remove
                ydeg[e.y]--;
                if (ydeg[e.y] == 0) {
                    ybit.add(e.y, -1);
                }
            }
        }

        out.println(total - dupes);
        out.flush();
    }

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

    static class Event implements Comparable<Event> {
        int kind;
        int time;
        int y;

        public Event(int k, int t, int i) {
            kind = k;
            time = t;
            y = i;
        }


        @Override
        public int compareTo(Event o) {
            if (time == o.time) {
                return kind - o.kind;
            }
            return time - o.time;
        }
    }


    static class MeldableSegment {
        TreeSet<Segment> tree;

        public MeldableSegment() {
            tree = new TreeSet<>();
        }

        /**
         * add and merge segment s.
         * segments are inclusive-inclusive: [fr,to]
         */
        void meld(Segment s) {
            Segment left = tree.lower(s);
            if (left != null) {
                if (left.to + 1 < s.fr) {
                    // ok.
                } else {
                    // merge
                    s.fr = left.fr;
                    s.to = Math.max(s.to, left.to);
                    tree.remove(left);
                }
            }
            while (true) {
                Segment right = tree.higher(s);
                if (right != null) {
                    if (s.to + 1 < right.fr) {
                        break;
                    } else {
                        // merge.
                        tree.remove(right);
                        if (s.to <= right.to) {
                            s.to = right.to;
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
            tree.add(s);
        }

        static class Segment implements Comparable<Segment> {
            static int __seq = 0;
            int seq;
            int fr;
            int to;
            public Segment(int a, int b) {
                seq = ++__seq;
                fr = Math.min(a, b);
                to = Math.max(a, b);
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
                return fr + "/" + to;
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