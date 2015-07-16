package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Stack;

/**
 * Created by hama_du on 15/07/15.
 */
public class P2584 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            char[] c = in.nextToken().toCharArray();
            if (c[0] == '.' && c.length == 1) {
                break;
            }
            out.println(solve(c));
        }

        out.flush();
    }

    static String solve(char[] c) {
        best = "~";
        dfs(c, 0);
        return best;
    }

    static String best;

    static void dfs(char[] c, int idx) {
        if (idx == c.length) {
            String ret = solveSub(c);
            if (ret.compareTo(best) < 0) {
                best = ret;
            }
            return;
        }
        if (c[idx] == '?') {
            for (char ci = 'A' ; ci <= 'Z' ; ci++) {
                c[idx] = ci;
                dfs(c, idx+1);
                c[idx] = '?';
            }
        } else {
            dfs(c, idx+1);
        }
    }


    static class State {
        StringBuilder str;
        int plus;
        int minus;

        State() {
            str = new StringBuilder();
            plus = minus = 0;
        }

        void plus() {
            plus += 1;
        }

        void minus() {
            minus += 1;
        }

        void append(char c) {
            int d = c - 'A';
            d = (d + plus - minus + 26 * 100) % 26;
            str.append((char)('A' + d));
            plus = minus = 0;
        }

        void append(State state) {
            str.append(state.str);
        }

        void reverse() {
            str.reverse();
        }
    }

    static String solveSub(char[] c) {
        int n = c.length;
        Stack<State> stk = new Stack<State>();
        stk.push(new State());
        for (int i = 0 ; i < n ; i++) {
            if (c[i] == '[') {
                stk.push(new State());
            } else if (c[i] == ']') {
                State rev = stk.pop();
                rev.reverse();
                State base = stk.pop();
                base.append(rev);
                stk.push(base);
            } else if (c[i] == '+') {
                stk.peek().plus();
            } else if (c[i] == '-') {
                stk.peek().minus();
            } else {
                stk.peek().append(c[i]);
            }
        }
        return stk.peek().str.toString();
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
