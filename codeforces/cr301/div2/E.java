package codeforces.cr301.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class E {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();

        int[][] pair = new int[n][2];
        for (int i = 0 ; i < n ; i++) {
            pair[i][0] = in.nextInt()-1;
            pair[i][1] = in.nextInt()-1;
        }

        compress(pair);

        for (int i = 0 ; i < n ; i++) {
            int i1 = pair[i][0];
            int i2 = pair[i][1];
            int p1 = posMap[i1];
            int p2 = posMap[i2];

            Segment seg1 = segments[p1];
            Segment seg2 = segments[p2];
            Segment.swap(seg1, seg2);
            int tmp = posMap[i1];
            posMap[i1] = posMap[i2];
            posMap[i2] = tmp;
        }


        int sn = segments.length;
        BIT bit = new BIT(sn+10);
        long sum = 0;
        for (int i = sn-1 ; i >= 0 ; i--) {
            sum += bit.range(1, segments[i].id) * segments[i].length;
            bit.add(segments[i].id+1, segments[i].length);
        }


        out.println(sum);
        out.flush();
    }

    // 1 2 3 4
    // 1 4 3 2
    // 4 1 3 2


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

    static class Segment {
        int id;
        int value;
        int length;

        public Segment(int id, int val, int len) {
            this.id = id;
            this.value = val;
            this.length = len;
        }

        public static void swap(Segment seg1, Segment seg2) {
            int tmp = seg1.id;
            seg1.id = seg2.id;
            seg2.id = tmp;

            tmp = seg1.value;
            seg1.value = seg2.value;
            seg2.value = tmp;

            tmp = seg1.length;
            seg1.length = seg2.length;
            seg2.length = tmp;
        }

        public String toString() {
            return String.format("id=%d v=%d(%d)", id, value, length);
        }
    }

    static Segment[] segments;

    static int[] posMap;

    static void compress(int[][] pair) {
        int n = pair.length;

        Set<Integer> pos = new HashSet<>();
        for (int i = 0 ; i < n ; i++) {
            pos.add(pair[i][0]);
            pos.add(pair[i][1]);
        }
        int[] x = new int[pos.size()];
        int xi = 0;
        for (int p : pos) {
            x[xi++] = p;
        }
        Arrays.sort(x);

        List<Segment> seg = new ArrayList<>();
        int now = 1;
        int segId = 0;
        for (int i = 0 ; i < x.length ; i++) {
            int to = x[i];
            if (now < to) {
                seg.add(new Segment(segId++, now, to-now));
            }
            Segment p = new Segment(segId++, to, 1);
            seg.add(p);
            now = to+1;
        }

        Map<Integer,Integer> valueIdMap = new HashMap<>();
        for (Segment s : seg) {
            valueIdMap.put(s.value, s.id);
        }

        int sn = seg.size();
        segments = new Segment[sn];
        posMap = new int[sn];
        for (int i = 0 ; i < sn ; i++) {
            segments[i] = seg.get(i);
            posMap[segments[i].id] = i;
        }

        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j <= 1 ; j++) {
                pair[i][j] = valueIdMap.get(pair[i][j]);
            }
        }
    }


    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
}



