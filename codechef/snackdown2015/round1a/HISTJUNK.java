package codechef.snackdown2015.round1a;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by dhamada on 15/06/12.
 */
public class HISTJUNK {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        while (--T >= 0) {
            int n = in.nextInt();
            int m = in.nextInt();
            int[][] edges = new int[m][2];
            for (int i = 0 ; i < m ; i++) {
                for (int j = 0; j < 2 ; j++) {
                    edges[i][j] = in.nextInt()-1;
                }
            }
            List<String> ans = solve(n, m, edges);
            for (String a : ans) {
                out.println(a);
            }
        }
        out.flush();
    }

    private static List<String> solve(int n, int m, int[][] edges) {
        List<String> answers = new ArrayList<>();
        if (n <= 2 || (n == 3 && m == 3)) {
            answers.add("0 0");
            return answers;
        }
        int[] deg = new int[n];
        for (int[] e : edges) {
            deg[e[0]]++;
            deg[e[1]]++;
        }

        if (n == 3) {
            int c = -1;
            for (int i = 0 ; i < 3 ; i++) {
                if (deg[i] == 2) {
                    c = i+1;
                    break;
                }
            }
            answers.add("2 3");
            for (int k = 1 ; k <= 3 ; k++) {
                if (k == c) {
                    answers.add(k + " 5");
                } else {
                    answers.add(k + " 4");
                }
            }
            return answers;
        }

        answers.add("4 " + (2*n+2));
        for (int i = 1 ; i <= n ; i++) {
            answers.add(i + " " + (n+1));
            answers.add(i + " " + (n+2));
        }
        answers.add((n+1) + " " + (n+3));
        answers.add((n+2) + " " + (n+4));
        return answers;
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
                return (char)c;
            }
            if ('A' <= c && c <= 'Z') {
                return (char)c;
            }
            throw new InputMismatchException();
        }

        public String nextToken() {
            int c = next();
            while (isSpaceChar(c))
                c = next();
            StringBuilder res = new StringBuilder();
            do {
                res.append((char)c);
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
