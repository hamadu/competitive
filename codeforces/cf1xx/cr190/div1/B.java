package codeforces.cf1xx.cr190.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/05/21.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();

        List<Integer> at = new ArrayList<>();
        List<Integer> df = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            if (in.nextToken().equals("ATK")) {
                at.add(in.nextInt());
            } else {
                df.add(in.nextInt());
            }
        }

        Collections.sort(at);
        Collections.sort(df);

        int[] attack = new int[m];
        int total = 0;
        for (int i = 0; i < m ; i++) {
            attack[i] = in.nextInt();
            total += attack[i];
        }
        Arrays.sort(attack);

        int best = 0;
        {
            int case1 = 0;
            int ji = 0;
            for (int i = m-1 ; i >= 0 && ji < at.size() ; i--) {
                if (at.get(ji) < attack[i]) {
                    case1 += attack[i] - at.get(ji);
                    ji++;
                } else {
                    break;
                }
            }
            best = Math.max(best, case1);
        }

        {
            int case2 = total;
            boolean[] done = new boolean[m];
            int ok = 0;
            for (int di : df) {
                for (int i = 0 ; i < m ; i++) {
                    if (done[i]) {
                        continue;
                    }
                    if (attack[i] > di) {
                        total -= attack[i];
                        done[i] = true;
                        ok++;
                        break;
                    }
                }
            }

            for (int di : at) {
                for (int i = 0 ; i < m ; i++) {
                    if (done[i]) {
                        continue;
                    }
                    if (attack[i] >= di) {
                        total -= di;
                        done[i] = true;
                        ok++;
                        break;
                    }
                }
            }
            if (ok == n) {
                best = Math.max(best, total);
            }
        }

        out.println(best);
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
