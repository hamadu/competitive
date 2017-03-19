package atcoder.other2017.yahoo2017.qual;

import utils.Unsolved;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;


@Unsolved("WIP")
public class D {
    static final int INFDAY = 1000000010;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int q = in.nextInt();
        long k = in.nextInt();
        Set<Integer> importantDaySet = new HashSet<>();
        int[][] queries = new int[q][];
        for (int i = 0; i < q ; i++) {
            int t = in.nextInt();
            int day = in.nextInt()-1;
            importantDaySet.add(day);
            if (t == 1) {
                queries[i] = new int[]{t, day, in.nextInt()};
            } else {
                queries[i] = new int[]{t, day};
            }
        }

        List<Integer> pts = new ArrayList<>(importantDaySet);
        Collections.sort(pts);

        daymap = new HashMap<>();
        List<int[]> ranges = new ArrayList<>();
        int last = 0;
        for (int p : pts) {
            if (last < p) {
                ranges.add(new int[]{last, p-1});
            }
            daymap.put(p, ranges.size());
            ranges.add(new int[]{p, p});
            last = p+1;
        }
        ranges.add(new int[]{last, INFDAY});

        M = Integer.highestOneBit(Math.max(4, ranges.size()-1))<<1;
        N = M*2;

        left = new long[N];
        reqs = new long[N];
        sold = new long[N];
        days = new long[N];

        for (int i = M-1 ; i < M-1+M ; i++) {
            int idx = i-M+1;
            int[] rng = idx < ranges.size() ? ranges.get(idx) : new int[]{INFDAY+1, INFDAY};
            days[i] = rng[1] - rng[0] + 1;
            left[i] = days[i] * k;
        }
        for (int i = M-2 ; i >= 0 ; i--) {
            days[i] = days[i*2+1] + days[i*2+2];
            left[i] = left[i*2+1] + left[i*2+2];
        }



        out.flush();
    }

    static Map<Integer,Integer> daymap;

    static void request(int day, long num) {
        int idx = daymap.get(day) + M - 1;
        reqs[idx] += num;
        while (idx > 0) {
            idx = (idx - 1) / 2;
            merge(idx);
        }
    }

    static long query(int day) {
        int idx = daymap.get(day)+1;
        return query(0, idx, 0, 0, M)[2];
    }

    static long[] query(int ql, int qr, int idx, int fr, int to) {
        if (qr <= fr || to <= ql) {
            return new long[]{0, 0, 0};
        }
        if (ql <= fr && to <= qr) {
            return new long[]{ left[idx], reqs[idx], sold[idx] };
        }
        long[] a = query(ql, qr, idx*2+1, fr, (fr+to)/2);
        long[] b = query(ql, qr, idx*2+2, (fr+to)/2, to);
        return merge(a, b);
    }

    static void merge(int pos) {
        long[] f0 = get(pos*2+1);
        long[] f1 = get(pos*2+2);

        long[] ff = merge(f0, f1);
        put(ff, pos);
    }

    static void put(long[] ff, int pos) {
        left[pos] = ff[0];
        reqs[pos] = ff[1];
        sold[pos] = ff[2];
        days[pos] = ff[3];
    }

    static long[] get(int pos) {
        return new long[]{left[pos], reqs[pos], sold[pos], days[pos]};
    }

    static long[] merge(long[] a, long[] b) {
        long[] c = new long[]{0, 0, 0};
        return c;
    }

    static int M;
    static int N;

    static long[] left;
    static long[] reqs;
    static long[] sold;
    static long[] days;



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
