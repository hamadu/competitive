package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/21.
 */
public class P1324 {
    static int INF = 100000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            if (n == 0 && m == 0) {
                break;
            }
            int[][] city = new int[n][];
            city[0] = new int[]{0, 0, 0};
            city[n-1] = new int[]{0, 1000, 0};
            for (int i = 1 ; i <= n-2 ; i++) {
                int d = in.nextInt();
                int h = in.nextInt();
                city[i] = new int[]{d, h, 0};
            }
            int[][] graph = new int[n][n];
            for (int i = 0; i < n ; i++) {
                Arrays.fill(graph[i], INF);
            }
            for (int i = 0; i < m ; i++) {
                int a = in.nextInt()-1;
                int b = in.nextInt()-1;
                graph[a][b] = in.nextInt();
            }
            out.println(solve(city, graph));
        }


        out.flush();
    }

    private static int solve(int[][] city, int[][] graph) {
        int n = city.length;
        int[] hid = new int[1002];
        for (int i = 0; i < n ; i++) {
            int h = city[i][1];
            city[i][2] = hid[h];
            hid[h]++;
        }

        int[][][] dp = new int[n][n][1<<10];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }

        dp[0][0][0] = 0;
        Queue<State> q = new PriorityQueue<State>();
        q.add(new State(0, 0, 0, 0));

        boolean[][][] passed = new boolean[n][n][1<<10];
        while (q.size() >= 1) {
            State st = q.poll();
            if (st.a == n-1 && st.b == n-1) {
                break;
            }
            if (passed[st.a][st.b][st.ptn]) {
                continue;
            }
            passed[st.a][st.b][st.ptn] = true;

            int ha = city[st.a][1];
            int hb = city[st.b][1];
            if (ha <= hb) {
                for (int i = 0; i < n ; i++) {
                    if (ha > city[i][1] || graph[st.a][i] == INF) {
                        continue;
                    }
                    int tt = st.time + graph[st.a][i];
                    int tptn = 0;
                    if (city[i][1] < hb) {
                        tt += city[i][0];
                        tptn = st.ptn;
                    } else if (city[i][1] == hb) {
                        tptn = st.ptn;
                        if ((tptn & (1<<city[i][2])) == 0) {
                            tptn |= 1<<city[i][2];
                            tt += city[i][0];
                        }
                    } else {
                        tt += city[i][0];
                        tptn = 1<<city[i][2];
                    }
                    if (dp[i][st.b][tptn] > tt) {
                        dp[i][st.b][tptn] = tt;
                        q.add(new State(i, st.b, tptn, tt));
                    }
                }
            }
            if (hb <= ha) {
                for (int i = 0; i < n ; i++) {
                    if (hb > city[i][1] || graph[i][st.b] == INF) {
                        continue;
                    }
                    int tt = st.time + graph[i][st.b];
                    int tptn = 0;
                    if (city[i][1] < ha) {
                        tt += city[i][0];
                        tptn = st.ptn;
                    } else if (city[i][1] == ha) {
                        tptn = st.ptn;
                        if ((tptn & (1<<city[i][2])) == 0) {
                            tptn |= 1<<city[i][2];
                            tt += city[i][0];
                        }
                    } else {
                        tt += city[i][0];
                        tptn |= 1<<city[i][2];
                    }
                    if (dp[st.a][i][tptn] > tt) {
                        dp[st.a][i][tptn] = tt;
                        q.add(new State(st.a, i, tptn, tt));
                    }
                }
            }
        }

        int min = INF;
        for (int i = 0; i < (1<<10) ; i++) {
            min = Math.min(min, dp[n-1][n-1][i]);
        }
        return min == INF ? -1 : min;
    }

    static int[][] city;

    static class State implements Comparable<State> {
        int a;
        int b;
        int ptn;
        int time;

        State(int i, int j, int p, int t) {
            a = i;
            b = j;
            ptn = p;
            time = t;
        }

        @Override
        public int compareTo(State o) {
            return time - o.time;
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
