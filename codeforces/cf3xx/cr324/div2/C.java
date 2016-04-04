package codeforces.cf3xx.cr324.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/10/10.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int t = in.nextInt();
        char[] a = in.nextToken().toCharArray();
        char[] b = in.nextToken().toCharArray();

        int same = 0;
        int diff = 0;
        for (int i = 0; i < n ; i++) {
            if (a[i] == b[i]) {
                same++;
            } else {
                diff++;
            }
        }

        int usame = -1;
        int udiffA = -1;
        int udiffB = -1;
        for (int i = 0; i <= diff ; i += 2) {
            int doe = (i / 2) + (diff - i);
            if (doe <= t && doe + same >= t) {
                udiffA = i / 2;
                udiffB = i / 2;
                usame = t - doe;
                break;
            }
        }

        if (usame >= 0) {
            for (int i = 0; i < n; i++) {
                if (a[i] == b[i]) {
                    int d = a[i]-'a';
                    if (usame >= 1) {
                        usame--;
                        out.print((char) ('a'+(d+1) % 26));
                    } else {
                        out.print(a[i]);
                    }
                } else {
                    if (udiffA >= 1) {
                        udiffA--;
                        out.print(a[i]);
                    } else if (udiffB >= 1) {
                        udiffB--;
                        out.print(b[i]);
                    } else {
                        int ai = a[i]-'a';
                        int bi = b[i]-'a';
                        for (int j = 0; j < 26; j++) {
                            if (j != ai && j != bi) {
                                out.print((char)('a' + j));
                                break;
                            }
                        }
                    }
                }
            }
            out.println();
        } else {
            out.println(-1);
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
