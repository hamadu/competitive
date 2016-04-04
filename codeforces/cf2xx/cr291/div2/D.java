package codeforces.cf2xx.cr291.div2;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.PriorityQueue;

public class D {
    public static void main(String[] args) throws IOException {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();

        PriorityQueue<Integer> pq[] = new PriorityQueue[m];
        for (int i = 0 ; i < m ; i++) {
            pq[i] = new PriorityQueue<>();
        }

        int[][] droids = new int[m][n];
        for (int j = 0; j < n ; j++) {
            for (int i = 0; i < m ; i++) {
                droids[i][j] = in.nextInt();
            }
        }

        int fr = 0;
        int to = 0;

        int ml = 0;
        int[] best = new int[m];

        int[] max = new int[m];
        while (fr < n) {
            while (to < n) {
                int maxSum = 0;
                for (int i = 0 ; i < m ; i++) {
                    max[i] = Math.max(max[i], droids[i][to]);
                    pq[i].add(-droids[i][to]);
                    maxSum += max[i];
                }
                to++;
                if (maxSum > k) {
                    break;
                }
                if (to - fr > ml) {
                    ml = to - fr;
                    best = max.clone();
                    // debug(fr, to, best);
                }
            }
            while (fr < to) {
                int maxSum = 0;
                for (int i = 0 ; i < m ; i++) {
                    pq[i].remove(-droids[i][fr]);
                    if (pq[i].size() >= 1) {
                        max[i] = -pq[i].peek();
                    } else {
                        max[i] = 0;
                    }
                    maxSum += max[i];
                }
                fr++;
                if (maxSum <= k) {
                    break;
                }
            }
        }


        StringBuilder ans = new StringBuilder();
        for (int i = 0 ; i < m ; i++) {
            ans.append(' ').append(best[i]);
        }
        out.println(ans.substring(1));
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



