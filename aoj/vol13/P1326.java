package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 15/07/17.
 */
public class P1326 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int P = in.nextInt();
            int Q = in.nextInt();
            if (P + Q == 0) {
                break;
            }
            char[][] p = new char[P][];
            char[][] q = new char[Q][];
            for (int i = 0; i < P ; i++) {
                p[i] = in.nextToken().toCharArray();
            }
            for (int i = 0; i < Q ; i++) {
                q[i] = in.nextToken().toCharArray();
            }

            int[] ret = solve(p, q);
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < ret.length ; i++) {
                line.append(' ').append(ret[i]);
            }
            out.println(line.substring(1));
        }
        out.flush();
    }

    private static int[][] indents(char[][] p) {
        int[][] ret = new int[p.length][3];
        for (int i = 0 ; i < p.length-1 ; i++) {
            for (int j = 0; j < 3 ; j++) {
                ret[i+1][j] += ret[i][j];
            }
            for (char c : p[i]) {
                if (c == '(') {
                    ret[i+1][0]++;
                } else if (c == ')') {
                    ret[i+1][0]--;
                } else if (c == '{') {
                    ret[i+1][1]++;
                } else if (c == '}') {
                    ret[i+1][1]--;
                } else if (c == '[') {
                    ret[i+1][2]++;
                } else if (c == ']') {
                    ret[i+1][2]--;
                }
            }
        }
        return ret;
    }

    private static int[] solve(char[][] p, char[][] q) {
        int[][] pid = indents(p);
        int[][] qid = indents(q);

        int[] pid2 = new int[p.length];
        for (int i = 0; i < p.length ; i++) {
            int first = 0;
            while (first < p[i].length && p[i][first] == '.') {
                first++;
            }
            pid2[i] = first;
        }

        List<int[]> okay = new ArrayList<int[]>();
        for (int r = 1 ; r <= 20 ; r++) {
            for (int c = 1 ; c <= 20 ; c++) {
                for (int s = 1 ; s <= 20 ; s++) {
                    int[] rcs = new int[]{r, c, s};
                    boolean isOK = true;
                    for (int i = 0 ; i < p.length ; i++) {
                        int indent = 0;
                        for (int j = 0; j < 3 ; j++) {
                            indent += pid[i][j] * rcs[j];
                        }
                        if (indent != pid2[i]) {
                            isOK = false;
                            break;
                        }
                    }
                    if (isOK) {
                        okay.add(rcs);
                    }
                }
            }
        }

        int[] ret = new int[q.length];
        Arrays.fill(ret, Integer.MAX_VALUE);
        for (int[] rcs : okay) {
            for (int i = 0 ; i < q.length ; i++) {
                int indent = 0;
                for (int j = 0; j < 3 ; j++) {
                    indent += qid[i][j] * rcs[j];
                }
                if (ret[i] == Integer.MAX_VALUE) {
                    ret[i] = indent;
                } else if (ret[i] != indent) {
                    ret[i] = -1;
                }
            }
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
