package gcj.gcj2016.round1a;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 4/16/16.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int t = 1 ; t <= T ; t++) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n ; i++) {
                a[i] = in.nextInt()-1;
            }
            out.println(String.format("Case #%d: %d", t, solve(a)));
        }


        out.flush();
    }

    private static int solve(int[] a) {
        int pl = 0;
        int n = a.length;
        boolean[] isPair = new boolean[n];
        for (int i = 0; i < n ; i++) {
            if (i == a[a[i]]) {
                isPair[i] = isPair[a[i]] = true;
            }
        }
        int[] longest = new int[n];
        for (int h = 0 ; h < n ; h++) {
            if (isPair[h]) {
                continue;
            }
            int[] visited = new int[n];
            int now = h;
            int t = 0;
            while (visited[now] == 0 && !isPair[now]) {
                t++;
                visited[now] = t;
                now = a[now];
            }
            if (isPair[now]) {
                longest[now] = Math.max(longest[now], t);
            }
        }
        int pairChain = 0;
        for (int i = 0 ; i < n ; i++) {
            if (isPair[i] && isPair[a[i]] && i < a[i]) {
                pairChain += 2 + longest[i] + longest[a[i]];
            }
        }
        int cycle = computeCycle(a);
        return Math.max(pairChain, cycle);
    }

    private static int computeCycle(int[] a) {
        int n = a.length;
        int ret = 0;
        for (int h = 0; h < n ; h++) {
            int[] visited = new int[n];
            int now = h;
            int t = 0;
            while (visited[now] == 0) {
                t++;
                visited[now] = t;
                now = a[now];
            }
            ret = Math.max(ret, t - visited[now] + 1);
        }
        return ret;
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
                res += c-'0';
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
                res += c-'0';
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
