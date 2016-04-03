package atcoder.arc.arc048;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2015/12/13.
 */
public class B {
    static class Contestants implements Comparable<Contestants> {
        int idx;
        int rate;
        int hand;

        public Contestants(int i, int a, int b) {
            idx = i;
            rate = a;
            hand = b;
        }

        @Override
        public int compareTo(Contestants o) {
            return o.rate - rate;
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        Contestants[] cs = new Contestants[n];
        for (int i = 0 ; i < n ; i++) {
            cs[i] = new Contestants(i, in.nextInt(), in.nextInt()-1);
        }
        Arrays.sort(cs);

        int[][] answer = new int[3][n];
        int[] rsp = new int[4];
        for (int i = 0 ; i < n ;) {
            int fr = i;
            int to = i;
            while (to < n && cs[fr].rate == cs[to].rate) {
                to++;
            }
            Arrays.fill(rsp, 0);
            int allWin = n - to;
            int allLose = fr;
            for (int k = fr ; k < to ; k++) {
                rsp[cs[k].hand]++;
            }
            for (int k = fr ; k < to ; k++) {
                int idx = cs[k].idx;
                int myHand = cs[k].hand;
                int winHand = (myHand + 1) % 3;
                int loseHand = (myHand + 2) % 3;
                answer[0][idx] = allWin + rsp[winHand];
                answer[1][idx] = allLose + rsp[loseHand];
                answer[2][idx] = rsp[myHand] - 1;
            }
            i = to;
        }

        for (int i = 0 ; i < n ; i++) {
            out.println(String.format("%d %d %d", answer[0][i], answer[1][i], answer[2][i]));
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
