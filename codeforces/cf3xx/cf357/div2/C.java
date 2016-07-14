package codeforces.cf3xx.cf357.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/07/13.
 */
public class C {
    static class LoggablePriorityQueue {
        Queue<Integer> q;
        List<String> instLog;

        public LoggablePriorityQueue() {
            q = new PriorityQueue<>();
            instLog = new ArrayList<>();
        }

        public int size() {
            return q.size();
        }

        public void removeMin() {
            instLog.add("removeMin");
            q.poll();
        }

        public void insert(int x) {
            instLog.add("insert " + x);
            q.add(x);
        }

        public int getMin(int x) {
            int r = q.peek();
            if (x != r) {
                throw new RuntimeException("what");
            }
            instLog.add("getMin " + x);
            return r;
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        LoggablePriorityQueue q = new LoggablePriorityQueue();
        for (int i = 0; i < n ; i++) {
            char op = in.nextToken().toCharArray()[0];
            if (op == 'i') {
                int x = in.nextInt();
                q.insert(x);
            } else if (op == 'r') {
                if (q.size() == 0) {
                    q.insert(1);
                }
                q.removeMin();
            } else {
                int x = in.nextInt();
                while (q.size() >= 1 && q.q.peek() < x) {
                    q.removeMin();
                }
                if (q.size() == 0 || q.q.peek() > x) {
                    q.insert(x);
                }
                q.getMin(x);
            }
        }

        out.println(q.instLog.size());
        for (String i : q.instLog) {
            out.println(i);
        }
        out.flush();
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
