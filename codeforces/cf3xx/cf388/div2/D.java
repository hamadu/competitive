package codeforces.cf3xx.cf388.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[][] a = in.nextIntTable(n, 2);
        Participant[] part = new Participant[n];
        for (int i = 0; i < n ; i++) {
            part[i] = new Participant(i);
        }
        for (int i = 0; i < n ; i++) {
            int id = a[i][0]-1;
            part[id].max = Math.max(part[id].max, a[i][1]);
            part[id].min = Math.min(part[id].min, a[i][1]);
            part[id].times.add(i);
        }

        Queue<Participant> que = new PriorityQueue<>();
        for (int i = 0; i < n ; i++) {
            if (part[i].max >= 1) {
                que.add(part[i]);
            }
        }

        boolean[] available = new boolean[n];
        Arrays.fill(available, true);

        int q = in.nextInt();
        while (--q >= 0) {
            int k = in.nextInt();
            int[] except = in.nextInts(k);
            for (int i = 0; i < k ; i++) {
                available[except[i]-1] = false;
            }
            List<Participant> removed = new ArrayList<>();

            Participant best = null;
            while (que.size() >= 1) {
                Participant p = que.poll();
                removed.add(p);
                if (available[p.idx]) {
                    best = p;
                    break;
                }
            }
            if (best == null) {
                out.println("0 0");
            } else {
                int left = -1;
                while (que.size() >= 1) {
                    Participant p = que.poll();
                    if (available[p.idx]) {
                        left = p.maxIndex();
                        que.add(p);
                        break;
                    }
                    removed.add(p);
                }

                int l = -1;
                int r = best.times.size();
                while (r - l > 1) {
                    int med = (r+l)/2;
                    if (left < best.times.get(med)) {
                        r = med;
                    } else {
                        l = med;
                    }
                }
                int time = best.times.get(r);
                out.println(String.format("%d %d", a[time][0], a[time][1]));
            }

            for (int i = 0; i < k ; i++) {
                available[except[i]-1] = true;
            }
            que.addAll(removed);
        }

        out.flush();
    }


    static class Participant implements Comparable<Participant> {
        int idx;
        int min;
        int max;
        List<Integer> times;

        public Participant(int i) {
            idx = i;
            min = Integer.MAX_VALUE;
            max = -1;
            times = new ArrayList<>();
        }

        public int maxIndex() {
            return times.get(times.size()-1);
        }

        @Override
        public int compareTo(Participant o) {
            return o.max - max;
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
