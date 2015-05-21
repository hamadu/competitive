package codeforces.cr281.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dhamada on 15/05/21.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int[] a = arr(in, in.nextInt());
        int[] b = arr(in, in.nextInt());

        int n = a.length;
        int m = b.length;
        int[][] th = new int[n+m][2];
        int tid = 0;
        for (int i : a) {
            th[tid++] = new int[]{i, 0};
        }
        for (int i : b) {
            th[tid++] = new int[]{i, 1};
        }
        Arrays.sort(th, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });

        int bestDiff = n * 3 - m * 3;
        int bestScoreA = n * 3;
        int bestScoreB =  m * 3;

        int scoreA = bestScoreA;
        int scoreB = bestScoreB;
        for (int i = 0 ; i < n+m ; ) {
            int j = i;
            int athrow = 0;
            int bthrow = 0;
            while (j < n+m && th[i][0] == th[j][0]) {
                if (th[j][1] == 0) {
                    athrow++;
                } else {
                    bthrow++;
                }
                j++;
            }
            scoreA -= athrow;
            scoreB -= bthrow;
            int diff = scoreA - scoreB;
            if (diff > bestDiff || (diff == bestDiff && scoreA > bestScoreA)) {
                bestDiff = diff;
                bestScoreA = scoreA;
                bestScoreB = scoreB;
            }
            i = j;
        }

        out.println(String.format("%d:%d", bestScoreA, bestScoreB));
        out.flush();
    }

    private static int[] arr(InputReader in, int n) {
        int[] r = new int[n];
        for (int i = 0; i < n ; i++) {
            r[i] = in.nextInt();
        }
        return r;
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
