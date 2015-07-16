package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/16.
 */

@SuppressWarnings("unchecked")
public class P1341 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        for (int i = 0 ; i < dp.length ; i++) {
            dp[i] = new TreeSet<Entry>();
        }

        while (true) {
            int m = in.nextInt();
            int n = in.nextInt();
            int A = in.nextInt();
            int B = in.nextInt();
            if (m + n + A + B == 0) {
                break;
            }
            int[][] pt = new int[m+n][3];
            for (int i = 0; i < m ; i++) {
                for (int j = 0; j < 3 ; j++) {
                    pt[i][j] = in.nextInt();
                }
            }
            Generator gen = new Generator(A, B);
            for (int i = m; i < m+n; i++) {
                for (int j = 0; j < 3; j++) {
                    pt[i][j] = gen.r();
                }
            }
            out.println(solve(pt));
        }
        out.flush();
    }

    private static int solve(int[][] pt) {
        int n = pt.length;
        Arrays.sort(pt, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] != o2[0]) {
                    return o1[0] - o2[0];
                }
                if (o1[1] != o2[1]) {
                    return o2[1] - o1[1];
                }
                return o2[2] - o1[2];
            }
        });
        for (int i = 0 ; i <= n ; i++) {
            dp[i].clear();
        }
        dp[0].add(new Entry(-1, new int[]{-1, -1, -1}));

        int head = 1;
        for (int i = 0 ; i < n ; i++) {
            Entry e = new Entry(i, pt[i]);
            int max = head+1;
            int min = 0;
            while (max - min > 1) {
                int med = (max + min) / 2;

                boolean hasLower = false;
                Entry e1 = dp[med].lower(e);
                if (e1 != null && e1.z < e.z && e1.y < e.y) {
                    hasLower = true;
                }
                if (hasLower) {
                    min = med;
                } else {
                    max = med;
                }
            }
            while (true) {
                Entry ei = dp[max].higher(e);
                if (ei == null || e.z > ei.z) {
                    break;
                }
                dp[max].remove(ei);
            }
            dp[max].add(e);
            head = Math.max(head, max+1);
        }

        return head-1;
    }

    static TreeSet<Entry>[] dp = new TreeSet[300010];

    static class Entry implements Comparable<Entry> {
        int idx;
        int x;
        int y;
        int z;
        Entry(int id, int[] v) {
            idx = id;
            x = v[0];
            y = v[1];
            z = v[2];
        }

        @Override
        public String toString() {
            return String.format("%d-%d-%d", x, y, z);
        }

        @Override
        public int compareTo(Entry o) {
            if (y != o.y) {
                return y - o.y;
            }
            if (z != o.z) {
                return o.z - z;
            }
            return idx - o.idx;
        }
    }

    static class Generator {
        int a;
        int b;
        int C = ~(1<<31);
        int M = (1<<16)-1;

        Generator(int A, int B) {
            a = A;
            b = B;
        }

        int r() {
            a = 36969 * (a & M) + (a >> 16);
            b = 18000 * (b & M) + (b >> 16);
            return (int)((C & ((a << 16L) + b)) % 1000000);
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
