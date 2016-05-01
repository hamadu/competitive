package gcj.gcj2016.round1b;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 5/1/16.
 */
public class A {
    static final String[] NUMBERS = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};

    static final int[][] magic = {
            {0, 'Z'},
            {2, 'W'},
            {4, 'U'},
            {1, 'O'},
            {3, 'R'},
            {5, 'F'},
            {6, 'X'},
            {7, 'S'},
            {8, 'G'},
            {9, 'I'},
    };

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int t = 1 ; t <= T ; t++) {
            char[] s = in.nextToken().toCharArray();
            out.println(String.format("Case #%d: %s", t, solve(s)));
        }
        out.flush();
    }

    private static String solve(char[] s) {
        int[] cnt = new int[10];
        int[] deg = new int[255];
        for (int i = 0; i < s.length ; i++) {
            deg[s[i]]++;
        }
        for (int di = 0 ; di <= 9 ; di++) {
            int digit = magic[di][0];
            char ch = (char)magic[di][1];
            cnt[digit] = deg[ch];
            for (char c : NUMBERS[digit].toCharArray()) {
                deg[c] -= cnt[digit];
            }
        }

        StringBuilder line = new StringBuilder();
        for (int d = 0; d <= 9; d++) {
            for (int i = 0; i < cnt[d] ; i++) {
                line.append((char)('0' + d));
            }
        }
        return line.toString();
    }

    private static void prec() {
        boolean[] done = new boolean[10];
        while (true) {
            Map<Character, Integer> dmap = new HashMap<>();

            int picked = -1;
            char key = '~';
            for (int d = 0; d <= 9 ; d++) {
                if (done[d]) {
                    continue;
                }
                for (char ci : NUMBERS[d].toCharArray()) {
                    dmap.put(ci, dmap.getOrDefault(ci, 0)+1);
                }
            }
            sch: for (int d = 0; d <= 9 ; d++) {
                if (done[d]) {
                    continue;
                }
                for (char ci : NUMBERS[d].toCharArray()) {
                    if (dmap.get(ci) == 1) {
                        picked = d;
                        key = ci;
                        break sch;
                    }
                }
            }
            if (picked == -1) {
                break;
            }
            done[picked] = true;
            debug(picked,key);
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
