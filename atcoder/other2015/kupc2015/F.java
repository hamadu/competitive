package atcoder.other2015.kupc2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Stack;

/**
 * Created by hama_du on 15/10/24.
 */
public class F {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] s = in.nextToken().toCharArray();

        int n = s.length;
        parent = new int[n];
        children = new int[n][2];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(children[i], -1);
        }
        Arrays.fill(parent, -1);

        Stack<Integer> stk = new Stack<>();
        for (int i = 0; i < n ; i++) {
            if (s[i] == '-' || s[i] == '+' || s[i] == '*') {
                int r = stk.pop();
                int l = stk.pop();
                children[i][0] = l;
                children[i][1] = r;
                parent[l] = parent[r] = i;
                stk.push(i);
            } else {
                stk.push(i);
            }
        }
        vs = new V[n];
        dfs(stk.pop(), 0, 0);

        Arrays.sort(vs);
        char[] ord = new char[n];
        for (int i = 0; i < n ; i++) {
            ord[i] = s[vs[i].idx];
        }
        out.println(String.valueOf(ord));
        out.flush();
    }

    static int[][] children;
    static int[] parent;
    static V[] vs;

    static class V implements Comparable<V> {
        int idx;
        int d;
        long s;

        public V(int i, int di, long si) {
            idx = i;
            d = di;
            s = si;
        }

        @Override
        public int compareTo(V o) {
            if (d != o.d) {
                return o.d-d;
            }
            return Long.compare(o.s, s);
        }
    }

    public static void dfs(int now, int depth, long score) {
        vs[now] = new V(now, depth, score);
        if (children[now][0] != -1) {
            dfs(children[now][1], depth+1, score*2+1);
            dfs(children[now][0], depth+1, score*2);
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
