package codeforces.cf3xx.cr318.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/08/30.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] h = new int[n];
        for (int i = 0; i < n ; i++) {
            h[i] = in.nextInt();
        }
        out.println(solve(h));
        out.flush();
    }

    static int solve(int[] h) {
        int n = h.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);

        Queue<State> q = new PriorityQueue<>();
        for (int i = 0; i < n ; i++) {
            if (i == 0 || i == n-1 || h[i] == 1) {
                q.add(new State(i, 1));
                dp[i] = 1;
            } else {
                q.add(new State(i, h[i]));
                dp[i] = h[i];
            }
        }

        while (q.size() >= 1) {
            State st = q.poll();
            for (int d = -1 ; d <= 1 ; d++) {
                int ti = st.idx + d;
                if (ti < 0 || ti >= n || dp[ti] <= st.time+1) {
                    continue;
                }
                dp[ti] = st.time+1;
                q.add(new State(ti, st.time+1));
            }
        }

        int max = 0;
        for (int i = 0; i < n ; i++) {
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    static class State implements Comparable<State> {
        int idx;
        int time;

        public State(int i, int t) {
            idx = i;
            time = t;
        }

        @Override
        public int compareTo(State o) {
            return time - o.time;
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
