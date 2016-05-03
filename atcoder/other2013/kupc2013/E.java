package atcoder.other2013.kupc2013;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/08/01.
 */
public class E {
    private static final int INF = 1145141919;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = new int[6];
        for (int i = 0; i < 6 ; i++) {
            a[i] = in.nextInt();
        }
        int s = in.nextInt()-1;
        int g = in.nextInt()-1;
        int[] v = new int[n];
        for (int i = 0; i < n; i++) {
            v[i] = in.nextInt();
        }

        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[g] = 0;
        for (int cur = 0; cur < 2*n; cur++) {
            for (int i = 0; i < n ; i++) {
                int min = INF;
                for (int k = 0; k < 6 ; k++) {
                    for (int d = -1 ; d <= 1; d += 2) {
                        int to = (i + a[k] * d);
                        if (0 <= to && to < n) {
                            to += v[to];
                            min = Math.min(min, dist[to]+1);
                        }
                    }
                }
                if (min < dist[i]) {
                    dist[i] = min;
                }
            }
        }

        int now = s;
        while (true) {
            int dice = in.nextInt() - 1;
            int prev = now - a[dice];
            int next = now + a[dice];
            if (0 <= prev && prev < n && dist[prev + v[prev]] < dist[now]) {
                out.println(-1);
                now = prev + v[prev];
            } else if (0 <= next && next < n && dist[next + v[next]] < dist[now]) {
                out.println(1);
                now = next + v[next];
            } else {
                out.println(0);
            }
            out.flush();
            if (now == g) {
                break;
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
