package codeforces.cf3xx.cr305.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.TreeSet;

/**
 * Created by dhamada on 15/05/28.
 */
public class B {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] a = new int[n][2];
        for (int i = 0; i < n ; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = i;
        }

        Arrays.sort(a, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });

        TreeSet<Segment> segments = new TreeSet<>();
        segments.add(new Segment(-10, -10));
        segments.add(new Segment(n+10, n+10));


        int[] ans = new int[n];
        int currentMax = 0;
        for (int i = 0 ; i < n ;) {
            int to = i;
            while (to < n && a[to][0] == a[i][0]) {
                to++;
            }

            int toMax = currentMax;
            for (int j = i ; j < to ; j++) {
                int pos = a[j][1];
                Segment seg = new Segment(pos, pos);
                Segment left = segments.lower(seg);
                Segment right = segments.higher(seg);
                if (left.to + 1 == pos) {
                    // merge left
                    segments.remove(left);
                    seg.fr = left.fr;
                }
                if (pos + 1 == right.fr) {
                    // merge right
                    segments.remove(right);
                    seg.to = right.to;
                }
                segments.add(seg);
                toMax = Math.max(toMax, seg.to - seg.fr + 1);
            }

            for (int c = currentMax ; c < toMax ; c++) {
                ans[c] = a[i][0];
            }
            i = to;
            currentMax = toMax;
        }

        StringBuilder line = new StringBuilder();
        for (int i = 0; i < n ; i++) {
            line.append(' ').append(ans[i]);
        }
        out.println(line.substring(1));
        out.flush();
    }

    static class Segment implements Comparable<Segment> {
        int fr;
        int to;

        public Segment(int fr, int to) {
            this.fr = fr;
            this.to = to;
        }

        @Override
        public int compareTo(Segment o) {
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

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
