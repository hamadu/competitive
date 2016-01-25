package codeforces.cr325.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 15/10/12.
 */
public class A {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long[][] children = new long[n][3];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 3 ; j++) {
                children[i][j] = in.nextInt();
            }
        }



        List<Integer> answers = new ArrayList<>();

        boolean[] gone = new boolean[n];
        for (int i = 0; i < n ; i++) {
            if (gone[i]) {
                continue;
            }
            answers.add(i);

            long volume = children[i][0];
            for (int j = i+1 ; j < n ; j++) {
                if (gone[j]) {
                    continue;
                }
                if (children[j][2] >= 0) {
                    children[j][2] -= volume;
                    volume--;
                }
                if (volume == 0) {
                    break;
                }
            }
            long[] dec = new long[n];
            while (true) {
                long decAll = 0;
                Arrays.fill(dec, 0);
                for (int j = i+1; j < n; j++) {
                    if (gone[j]) {
                        continue;
                    }
                    dec[j] = decAll;
                    if (children[j][2] < 0) {
                        gone[j] = true;
                        decAll += children[j][1];
                    }
                }
                if (decAll == 0) {
                    break;
                }
                for (int j = i+1 ; j < n ; j++) {
                    if (gone[j]) {
                        continue;
                    }
                    children[j][2] -= dec[j];
                }
            }
        }

        out.println(answers.size());
        for (int i = 0 ; i < answers.size() ; i++) {
            if (i >= 1) {
                out.print(' ');
            }
            out.print(answers.get(i)+1);
        }
        out.println();
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
