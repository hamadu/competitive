package hackerrank.countercode;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/08/02.
 */
public class Subset {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] degree = new int[1<<16];
        int[] answer = new int[1<<16];

        int[][] queries = new int[n][2];
        for (int i = 0; i < n ; i++) {
            String op = in.nextToken();
            queries[i][0] = (op.equals("add") ? 1 : (op.equals("del") ? -1 : 0));
            queries[i][1] = in.nextInt();
        }

        int all = 0;
        int qn = 500;

        int fr = -1;
        int to = -1;

        int[] diff = new int[n];
        for (int q = 0; q < n ; q++) {
            if (q % qn == 0) {
                fr = q;
                to = Math.min(n, q + qn);
                for (int x = 0 ; x < (1<<16) ; x++) {
                    answer[x] = degree[x];
                }
                for (int i = 0 ; i < 16 ; i++) {
                    for (int x = 0 ; x < (1<<16) ; x++) {
                        if ((x & (1<<i)) == 0) {
                            answer[x|(1<<i)] += answer[x];
                        }
                    }
                }
            }

            if (queries[q][0] == 0) {
                out.println(answer[queries[q][1]] + diff[q]);
            } else {
                degree[queries[q][1]] += queries[q][0];
                for (int i = q+1 ; i < to; i++) {
                    if (queries[i][0] == 0) {
                        if ((queries[i][1] & queries[q][1]) == queries[q][1]) {
                            diff[i] += queries[q][0];
                        }
                    }
                }
            }
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
