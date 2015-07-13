package aoj.vol11;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/13.
 */
public class P1195 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            char[] c = in.nextToken().toCharArray();
            if (c[0] == '#') {
                break;
            }

            String[] lines = solve(c);
            for (String l : lines) {
                out.println(l);
            }
        }

        out.flush();
    }

    private static String[] solve(char[] c) {
        answers = new ArrayList<String>();
        dfs('z', 'y', c);
        Collections.sort(answers);

        int ac = answers.size();
        int lc = 1 + Math.min(10, ac);
        String[] lines = new String[lc];
        lines[0] = ac + "";
        if (ac <= 10) {
            for (int i = 0 ; i < ac; i++) {
                lines[i+1] = answers.get(i);
            }
        } else {
            for (int i = 0 ; i < 5 ; i++) {
                lines[i+1] = answers.get(i);
            }
            for (int i = 0 ; i < 5 ; i++) {
                lines[10-i] = answers.get(ac-i-1);
            }
        }
        return lines;
    }

    static void dfs(char first, char change, char[] cl) {
        if (first == 'a') {
            answers.add(String.valueOf(cl));
            return;
        }
        for (int i = 0 ; i < cl.length ; i++) {
            if (cl[i] == first) {
                for (int j = 0; j < i ; j++) {
                    if (cl[j] == change) {
                        cl[j] = first;
                        dfs((char)(first-1), (char)(change-1), cl);
                        cl[j] = change;
                    }
                }
                return;
            }
        }
        for (int j = 0; j < cl.length ; j++) {
            if (cl[j] == change) {
                cl[j] = first;
                dfs((char)(first-1), (char)(change-1), cl);
                cl[j] = change;
            }
        }
        dfs((char)(first-1), (char)(change-1), cl);
    }

    static List<String> answers;

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
