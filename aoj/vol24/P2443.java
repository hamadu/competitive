package aoj.vol24;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by hama_du on 15/07/29.
 */
@SuppressWarnings("unchecked")
public class P2443 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = in.nextInt();
        long[] a = new long[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt()-1;
        }
        for (int i = 0; i < 2 ; i++) {
            memo[i] = new HashMap<Long,Integer>();
        }

        long start = 0;
        long goal = 0;
        for (long i = 0; i < n ; i++) {
            start |= a[(int)i]<<(4L*i);
            goal |= i<<(4L*i);
        }

        bfs(start, 0);
        bfs(goal, 1);

        int min = 9;
        for (long s : memo[0].keySet()) {
            if (memo[1].containsKey(s)) {
                min = Math.min(min, memo[0].get(s) + memo[1].get(s));
            }
        }
        out.println(min);
        out.flush();
    }

    static int n;

    static Map<Long,Integer>[] memo = new Map[2];

    static void bfs(long start, int type) {
        int qh = 0;
        int qt = 0;
        _sque[qh] = start;
        _tque[qh++] = 0;

        memo[type].put(start, 0);
        while (qt < qh) {
            long stat = _sque[qt];
            int ti = _tque[qt++];
            if (ti >= 4) {
                return;
            }
            for (int i = 0; i < n ; i++) {
                for (int j = i+2; j <= n ; j++) {
                    // reverse [i,j)
                    int L = j-i;
                    long tstat = stat;
                    for (int k = 0; k < L / 2 ; k++) {
                        // swap (i+k,j-1-k)
                        long num1 = (stat>>(4L*(i+k))) & 15L;
                        long num2 = (stat>>(4L*(j-1-k))) & 15L;
                        tstat -= num1<<(4L*(i+k));
                        tstat -= num2<<(4L*(j-1-k));
                        tstat |= num2<<(4L*(i+k));
                        tstat |= num1<<(4L*(j-1-k));
                    }
                    if (memo[type].containsKey(tstat)) {
                        continue;
                    }
                    memo[type].put(tstat, ti+1);
                    _sque[qh] = tstat;
                    _tque[qh++] = ti+1;
                }
            }
        }

    }

    static long[] _sque = new long[5000000];
    static int[] _tque = new int[5000000];

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
