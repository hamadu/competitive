package codeforces.cr60.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

/**
 * Created by hama_du on 15/08/03.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = in.nextInt();
        can = new boolean[4];
        String[] names = new String[]{"Gryffindor", "Hufflepuff", "Ravenclaw", "Slytherin"};
        choose = in.nextToken().toCharArray();
        arr = new char[]{'G', 'H', 'R', 'S'};
        dfs(0, 0);
        for (int i = 0; i < 4 ; i++) {
            if (can[i]) {
                out.println(names[i]);
            }
        }

        out.flush();
    }

    static char[] choose;
    static char[] arr;
    static int n;
    static boolean[] can;
    static Set<Long> reached = new HashSet<>();

    static void dfs(long stat, int idx) {
        if (reached.contains(stat)) {
            return;
        }
        reached.add(stat);

        long min = 1000000;
        for (int i = 0; i < 4 ; i++) {
            min = Math.min(min, (stat >> (i * 14L)) & ((1<<14L)-1));
        }
        if (idx == n) {
            for (int d = 0; d < 4 ; d++) {
                long num = (stat >> (d * 14L)) & ((1 << 14L) - 1);
                if (num == min) {
                    can[d] = true;
                }
            }
            return;
        }

        for (int d = 0; d < 4 ; d++) {
            long num = (stat >> (d * 14L)) & ((1<<14L)-1);
            if (arr[d] == choose[idx] || (choose[idx] == '?' && num == min)) {
                dfs(stat+(1L<<(14*d)), idx+1);
            }
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
