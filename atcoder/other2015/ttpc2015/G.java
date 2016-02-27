package atcoder.other2015.ttpc2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/20.
 */
public class G {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] s = in.nextToken().toCharArray();
        boolean isOK = solve(s);
        out.println(isOK ? "Yes" : "No");
        out.flush();
    }

    private static boolean solve(char[] s) {
        int n = s.length;
        if (n % 6 != 0) {
            return false;
        }

        int[] findVal = new int[255];
        Arrays.fill(findVal, -1);
        findVal['h'] = 0;
        findVal['c'] = 1;
        findVal['e'] = 2;
        findVal['t'] = 3;
        findVal['i'] = 4;
        int dv = n / 6;
        int[] state = new int[dv];
        for (int i = n-1 ; i >= 0 ; i--) {
            char dg = s[i];
            int pos = -1;
            for (int j = 0; j < dv; j++) {
                if (state[j] == findVal[dg]) {
                    pos = j;
                    break;
                }
            }
            if (pos == -1) {
                if (dg != 't') {
                    return false;
                }
                for (int j = 0; j < dv; j++) {
                    if (state[j] == 5) {
                        pos = j;
                        break;
                    }
                }
                if (pos == -1) {
                    return false;
                }
            }
            state[pos]++;
        }
        for (int i = 0; i < dv ; i++) {
            if (state[i] != 6) {
                return false;
            }
        }
        return true;
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
