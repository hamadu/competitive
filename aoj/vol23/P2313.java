package aoj.vol23;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 15/07/19.
 */
public class P2313 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = in.nextInt();
        int e = in.nextInt();
        int q = in.nextInt();

        graph = new int[n][n];
        flow = new int[n][n];
        visited = new boolean[n];
        for (int i = 0; i < e ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            graph[a][b] = 1;
            graph[b][a] = 1;
        }

        int ret = flow();
        while (--q >= 0) {
            int m = in.nextInt();
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            if (m == 1) {
                graph[a][b] = 1;
                graph[b][a] = 1;

                ret += flow();
            } else {
                if (flow[a][b] >= 1) {
                    ret += rev(a, b);
                } else if (flow[b][a] >= 1) {
                    ret += rev(b, a);
                }
                graph[a][b] = 0;
                graph[b][a] = 0;
                flow[a][b] = flow[b][a] = 0;
            }
            // debug(flow);
            out.println(ret);
        }
        out.flush();
    }

    static int n;
    static int[][] graph;
    static int[][] flow;
    static boolean[] visited;

    static int flow() {
        int flw = 0;
        while (dfs0(0, -1, n-1)) {
            flw++;
        }
        return flw;
    }

    static int rev(int a, int b) {
        flow[a][b] = 0;
        if (dfs0(a, b, b)) {
            return 0;
        }
        flow[a][b] = 1;
        dfs0(n - 1, -1, 0);

        dfs0(a, b, b);

        return -1;
    }

    static boolean dfs0(int now, int par, int goal) {
        Arrays.fill(visited, false);
        return dfs(now, par, goal);
    }


    static boolean dfs(int now, int par, int goal) {
        if (now == goal) {
            return true;
        }
        if (visited[now]) {
            return false;
        }
        visited[now] = true;
        for (int to = 0 ; to < n ; to++) {
            if (to == par || graph[now][to] == 0) {
                continue;
            }
            if (flow[now][to] == 0 && flow[to][now] == 0) {
                flow[now][to]++;
                if (dfs(to, now, goal)) {
                    return true;
                }
                flow[now][to]--;
            } else if (flow[to][now] == 1) {
                flow[to][now]--;
                if (dfs(to, now, goal)) {
                    return true;
                }
                flow[to][now]++;
            }
        }
        return false;
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
