package codeforces.cr325.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/10/12.
 */
public class D {
    private static final long INF = 1000000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);


        int n = in.nextInt();
        long[][] quests = new long[n][3];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 3 ; j++) {
                quests[i][j] = in.nextLong();
            }
        }

        left = new HashMap<>();
        leftHistory = new HashMap<>();

        dfsLEFT(0, n / 2, quests, 0, new long[3], 0);
        dfsRIGHT(n / 2, n, quests, n / 2, new long[3], 0);

        if (max == -INF) {
            out.println("Impossible");
        } else {
            // LMW
            char[][] ans = new char[n][2];
            for (int i = n-1 ; i >= n / 2 ; i--) {
                long mod = maxRight % 3;
                if (mod == 0) {
                    ans[i][0] = 'M';
                    ans[i][1] = 'W';
                } else if (mod == 1) {
                    ans[i][0] = 'L';
                    ans[i][1] = 'W';
                } else {
                    ans[i][0] = 'L';
                    ans[i][1] = 'M';
                }
                maxRight /= 3;
            }
            for (int i = n/2-1 ; i >= 0 ; i--) {
                long mod = maxLeft % 3;
                if (mod == 0) {
                    ans[i][0] = 'M';
                    ans[i][1] = 'W';
                } else if (mod == 1) {
                    ans[i][0] = 'L';
                    ans[i][1] = 'W';
                } else {
                    ans[i][0] = 'L';
                    ans[i][1] = 'M';
                }
                maxLeft /= 3;
            }
            for (int i = 0; i < n ; i++) {
                out.println(String.valueOf(ans[i]));
            }
        }
        out.flush();
    }

    static long max = -INF;
    static long maxLeft = -1;
    static long maxRight = -1;

    static Map<Long,Long> left;
    static Map<Long,Long> leftHistory;


    static void dfsLEFT(int fr, int to, long[][] quests, int idx, long[] attitude, long history) {
        if (idx == to) {
            long diff0 = (attitude[1] - attitude[0] + INF);
            long diff1 = (attitude[2] - attitude[0] + INF);
            long code = (diff0 << 32L) + diff1;
            long leftBefore = left.getOrDefault(code, -INF);
            if (leftBefore < attitude[0]) {
                left.put(code, attitude[0]);
                leftHistory.put(code, history);
            }
            return;
        }
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    continue;
                }
                attitude[j] += quests[idx][j];
            }
            dfsLEFT(fr, to, quests, idx+1, attitude, history*3+i);
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    continue;
                }
                attitude[j] -= quests[idx][j];
            }
        }
    }

    static void dfsRIGHT(int fr, int to, long[][] quests, int idx, long[] attitude, long history) {
        if (idx == to) {
            long diff0 = (attitude[0] - attitude[1] + INF);
            long diff1 = (attitude[0] - attitude[2] + INF);
            long code = (diff0 << 32L) + diff1;
            if (left.containsKey(code)) {
                long sum = attitude[0] + left.get(code);
                if (max < sum) {
                    max = sum;
                    maxLeft = leftHistory.get(code);
                    maxRight = history;
                }
            }
            return;
        }
        for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    continue;
                }
                attitude[j] += quests[idx][j];
            }
            dfsRIGHT(fr, to, quests, idx+1, attitude, history*3+i);
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    continue;
                }
                attitude[j] -= quests[idx][j];
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
