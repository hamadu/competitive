package codeforces.cf3xx.cf377.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by hama_du on 2016/10/20.
 */
public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] computers = new int[n][2];
        for (int i = 0; i < n ; i++) {
            computers[i][0] = i;
            computers[i][1] = in.nextInt();
        }
        Arrays.sort(computers, (a, b) -> b[1] - a[1]);

        int[] adaptors = new int[m];
        int[] sockets = new int[n];
        Arrays.fill(sockets, -1);

        MinHeap q = new MinHeap(m+10);

        long POWMASK = (1L<<30)-1;
        for (int i = 0; i < m ; i++) {
            long v = in.nextInt();
            q.push(((POWMASK-v)<<26)|i);
        }


        long CNTMASK = (1L<<6)-1;
        long IDXMASK = (1L<<20)-1;
        int idx = 0;
        while (q.size() >= 1 && idx < n) {
            long so = q.poll();
            long power = POWMASK-((so>>>26)&POWMASK);
            int adaptor = (int)((so>>>20)&CNTMASK);
            int sidx = (int)(so&IDXMASK);

            while (idx < n && power < computers[idx][1]) {
                idx++;
            }
            if (idx == n) {
                break;
            }
            if (power == computers[idx][1]) {
                sockets[computers[idx][0]] = sidx;
                adaptors[sidx] = adaptor;
                idx++;
            } else {
                power = (power+1)/2;
                adaptor++;
                q.push(((POWMASK-power)<<26)|(adaptor<<20)|sidx);
            }
        }

        int c = 0;
        int u = 0;
        for (int i = 0; i < sockets.length; i++) {
            if (sockets[i] >= 0) {
                c++;
                u += adaptors[sockets[i]];
            }
        }

        out.println(String.format("%d %d", c, u));
        for (int i = 0; i < m ; i++) {
            if (i >= 1) {
                out.print(' ');
            }
            out.print(adaptors[i]);
        }
        out.println();
        for (int i = 0; i < n ; i++) {
            if (i >= 1) {
                out.print(' ');
            }
            out.print(sockets[i]+1);
        }
        out.println();
        out.flush();
    }

    static class MinHeap {
        private static final long INF = Long.MAX_VALUE;
        int pos;
        long[] data;

        public MinHeap(int capacity) {
            data = new long[capacity];
            pos = 1;
            Arrays.fill(data, INF);

        }

        public void push(long x) {
            int p = pos;
            data[pos++] = x;
            while (p != 1) {
                int pp = p>>>1;
                if (data[pp] <= data[p]) {
                    break;
                }
                long tmp = data[pp];
                data[pp] = data[p];
                data[p] = tmp;
                p = pp;
            }
        }

        public long peek() {
            return data[1];
        }

        public long poll() {
            if (size() == 0) {
                throw new RuntimeException("queue is empty");
            }
            pos--;
            long ret = data[1];
            data[1] = data[pos];
            data[pos] = INF;

            for (int p = 1 ; p * 2 < pos ; ){
                int l = p<<1;
                int r = l+1;
                int to = data[l] < data[r] ? l : r;
                if (data[to] < data[p]) {
                    long tmp = data[to];
                    data[to] = data[p];
                    data[p] = tmp;
                    p = to;
                } else {
                    break;
                }
            }
            return ret;
        }

        public int size() {
            return pos-1;
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