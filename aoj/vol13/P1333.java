package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/17.
 */
public class P1333 {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int w = in.nextInt();
            int n = in.nextInt();
            if (w + n == 0) {
                break;
            }
            int[] a = new int[n];
            for (int i = 0; i < n ; i++) {
                a[i] = in.nextInt();
            }
            out.println(solve(w, a));
        }
        out.flush();
    }

    private static int solve(int W, int[] a) {
        int n = a.length;
        int[] imos = new int[n+2];
        for (int i = 0; i < n ; i++) {
            imos[i+1] = imos[i] + a[i];
        }
        Arrays.sort(a);

        imos[n+1] = W+1;
        int[] tidx = new int[n];
        for (int i = 0; i < n; i++) {
            int min = i+1;
            int max = n+1;
            while (max - min > 1) {
                int med = (max + min) / 2;
                int spaces = W - (imos[med] - imos[i]);
                if (spaces >= (med - i) - 1) {
                    min = med;
                } else {
                    max = med;
                }
            }
            tidx[i] = min;
        }

        boolean[] isLast = new boolean[n+1];
        isLast[n] = true;
        int lastSum = 0;
        for (int i = n-1 ; i >= 0 ; i--) {
            lastSum += a[i];
            if (lastSum <= W) {
                isLast[i] = true;
            }
            lastSum++;
        }
        if (isLast[0]) {
            return 1;
        }

        int[] visited = new int[n];
        int vi = 1;

        Queue<Integer> q = new PriorityQueue<Integer>();
        boolean[] done = new boolean[n+1];
        done[0] = true;
        int uf = 0;

        Set<Integer> start = new HashSet<>();
        start.add(0);

        long time = 0;
        long cur = System.currentTimeMillis();
        for (int s = 1 ; s <= W ; s++) {
            for (Integer si : start) {
                q.add(si);
            }
            while (q.size() >= 1) {
                Integer now = q.poll();
                while (true) {
                    int to = tidx[now];
                    int fw = to - now;
                    if (done[to]) {
                        break;
                    }
                    int space = W - (imos[to] - imos[now]);
                    int sw = (space + fw - 2) / (fw - 1);
                    if (sw <= s && !done[to]) {
                        done[to] = true;
                        visited[vi++] = to;
                        q.add(to);
                        tidx[now]--;
                        start.add(to);
                        if (isLast[to]) {
                            done[n] = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (done[n]) {
                    break;
                }
            }
            if (done[n]) {
                return s;
            }
        }
        return -1;
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
