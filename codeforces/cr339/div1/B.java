package codeforces.cr339.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/01/21.
 */
public class B {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long upto = in.nextLong();
        long perfectV = in.nextLong();
        long minimumV = in.nextLong();
        long money = in.nextLong();
        long[][] skills = new long[n][2];
        for (int i = 0; i < n ; i++) {
            skills[i][0] = i;
            skills[i][1] = in.nextLong();
        }
        Arrays.sort(skills, (o1, o2) -> Long.compare(o1[1], o2[1]));

        long[] s = new long[n];
        for (int i = 0 ; i < n ; i++) {
            s[i] = skills[i][1];
        }

        long[] imos = new long[n];
        for (int i = 0 ; i < n ; i++) {
            imos[i] = ((i >= 1) ? imos[i-1] : 0) + s[i];
        }

        long best = 0;
        int bestPerfectIdx = n;
        long bestMinimum = 0;

        long makePerfect = 0;
        for (int i = n ; i >= 0 ; i--) {
            if (i < n) {
                makePerfect += upto-s[i];
            }
            if (makePerfect > money) {
                break;
            }
            long score = (n-i)*perfectV;
            long min = upto;
            if (i >= 1) {
                min = solveMinimum(s, imos, i, money-makePerfect, upto);
                score += min * minimumV;
            } else {
                score += upto * minimumV;
            }
            if (score > best) {
                best = score;
                bestPerfectIdx = i;
                bestMinimum = min;
            }
        }

        for (int i = n-1 ; i >= bestPerfectIdx ; i--) {
            skills[i][1] = upto;
        }
        for (int i = 0 ; i < n ; i++) {
            if (skills[i][1] < bestMinimum) {
                skills[i][1] = bestMinimum;
            }
        }
        Arrays.sort(skills, (o1, o2) -> Long.compare(o1[0], o2[0]));

        out.println(best);
        StringBuilder line = new StringBuilder();
        for (int i = 0 ; i < n ; i++) {
            line.append(' ').append(skills[i][1]);
        }
        out.println(line.substring(1));
        out.flush();
    }

    private static long solveMinimum(long[] s, long[] lsum, int to, long money, long limit) {
        long max = limit+1;
        long min = s[0];
        while (max - min > 1) {
            long med = (max+min)/2;

            int left = 0;
            int right = to;
            while (right - left > 1) {
                int m = (right + left) / 2;
                if (s[m] > med) {
                    right = m;
                } else {
                    left = m;
                }
            }
            long need = med * (left+1);
            need -= lsum[left];
            if (need > money) {
                max = med;
            } else {
                min = med;
            }
        }
        return min;
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
