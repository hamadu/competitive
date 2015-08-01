package aoj.vol25;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/08/01.
 */
@SuppressWarnings("unchecked")
public class P2549 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int s = in.nextInt()-1;
        int[][] lr = new int[n][3];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 2 ; j++) {
                lr[i][j] = in.nextInt();
            }
            lr[i][2] = i;
        }
        Arrays.sort(lr, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });

        int[][] graph = new int[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                graph[i][j] = in.nextInt();
            }
        }
        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }

        int[] ptoi = new int[n];
        int[] itop = new int[n];
        for (int i = 0; i < n ; i++) {
            ptoi[i] = lr[i][2];
            itop[lr[i][2]] = i;
        }

        Map<Long,Long>[] dp = new Map[n];
        for (int i = 0; i < n ; i++) {
            dp[i] = new HashMap<Long,Long>();
        }
        for (int i = 0; i < n ; i++) {
            int tp = ptoi[i];
            dp[i].put(graph[s][tp] * 1L, 0L);
        }
        long best = 0;
        for (int i = 0; i < n ; i++) {
            int pid = ptoi[i];
            for (long time : dp[i].keySet()) {
                long done = dp[i].get(time);
                best = Math.max(best, done);

                // case1
                {
                    long tv = done + Math.max(0, lr[i][1] - Math.max(lr[i][0], time));
                    best = Math.max(best, tv);
                    for (int j = i+1 ; j < n; j++) {
                        int pid2 = ptoi[j];
                        long tt = Math.max(lr[i][1], time) + graph[pid][pid2];
                        if (lr[j][1] <= tt) {
                            continue;
                        }
                        tt = Math.max(tt, lr[j][0]);
                        if (!dp[j].containsKey(tt)) {
                            dp[j].put(tt, tv);
                        } else {
                            if (dp[j].get(tt) < tv) {
                                dp[j].put(tt, tv);
                            }
                        }
                    }
                }

                // case2
                {
                    for (int j = i+1 ; j < n ; j++) {
                        int pid2 = ptoi[j];
                        long leaveTime = lr[j][0] - graph[pid][pid2];
                        if (leaveTime >= time) {
                            long tt = lr[j][0];
                            long tv = done + Math.max(0, Math.min(leaveTime, lr[i][1]) - Math.max(lr[i][0], time));
                            best = Math.max(best, tv);

                            if (!dp[j].containsKey(tt)) {
                                dp[j].put(tt, tv);
                            } else {
                                if (dp[j].get(tt) < tv) {
                                    dp[j].put(tt, tv);
                                }
                            }
                        }
                    }
                }
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
