package atcoder.dwango2016.qual;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/01/23.
 */
public class C {
    private static final long INF = (long) 1e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int start = in.nextInt();
        int goal = in.nextInt();

        List<Edge>[] graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<>();
        }

        int[][] finish = new int[m][2];
        int[][][] timeToFinish = new int[m][][];
        for (int i = 0; i < m ; i++) {
            int l = in.nextInt();
            int[] stations = new int[l];
            int[] time = new int[l-1];
            for (int j = 0; j < l ; j++) {
                stations[j] = in.nextInt();
            }
            for (int j = 0; j < l-1 ; j++) {
                time[j] = in.nextInt();
            }
            for (int j = 0; j < l-1 ; j++) {
                int a = stations[j];
                int b = stations[j+1];
                graph[a].add(new Edge(i, j, b, 1, time[j]));
            }
            for (int j = l-1 ; j >= 1 ; j--) {
                int a = stations[j];
                int b = stations[j-1];
                graph[a].add(new Edge(i, j, b, -1, time[j-1]));
            }
            finish[i][0] = stations[0];
            finish[i][1] = stations[l-1];

            timeToFinish[i] = new int[2][l];
            // go
            for (int j = l-2 ; j >= 0 ; j--) {
                timeToFinish[i][0][j] = timeToFinish[i][0][j+1] + time[j];
            }

            // rev
            for (int j = 1 ; j < l ; j++) {
                timeToFinish[i][1][j] = timeToFinish[i][1][j-1] + time[j-1];
            }
        }


        long[] toGoal = new long[n];
        Arrays.fill(toGoal, INF);
        toGoal[goal] = 0;
        Queue<State> q = new PriorityQueue<>();
        q.add(new State(goal, 0L));
        while (q.size() >= 1) {
            State st = q.poll();
            int now = st.now;
            long time = st.time;
            if (toGoal[now] < time) {
                continue;
            }

            for (Edge e : graph[now]) {
                int to = e.to;
                long ttime = time + e.w;
                if (toGoal[to] > ttime) {
                    toGoal[to] = ttime;
                    q.add(new State(to, ttime));
                }
            }
        }

        long max = INF/100000;
        long min = 0;
        while (max - min > 1) {
            long time = (max + min) / 2;
            if (isOK(time, toGoal, graph, finish, timeToFinish, start, goal)) {
                max = time;
            } else {
                min = time;
            }
        }
        out.println(max);
        out.flush();
    }

    private static boolean isOK(long limit, long[] toGoal, List<Edge>[] graph, int[][] finish, int[][][] timeToFinish, int start, int goal) {
        int n = graph.length;
        long[] best = new long[n];
        Arrays.fill(best, INF);
        best[start] = 0;

        Queue<State> q = new PriorityQueue<>();
        q.add(new State(start, 0L));
        while (q.size() >= 1) {
            State st = q.poll();
            int now = st.now;
            long time = st.time;
            if (best[now] < time) {
                continue;
            }

            for (Edge e : graph[now]) {
                int to = e.to;
                long ttime = time + e.w;
                int expStation = finish[e.line][e.dir == 1 ? 1 : 0];
                if (time + timeToFinish[e.line][e.dir == 1 ? 0 : 1][e.num] + toGoal[expStation] > limit) {
                    continue;
                }
                if (best[to] > ttime) {
                    best[to] = ttime;
                    q.add(new State(to, ttime));
                }
            }
        }
        return best[goal] <= limit;
    }

    static class State implements Comparable<State> {
        int now;
        long time;

        public State(int a, long b) {
            now = a;
            time = b;
        }

        @Override
        public int compareTo(State o) {
            return Long.compare(time, o.time);
        }
    }

    static class Edge {
        int line;
        int num;
        int to;
        int dir;
        int w;

        public Edge(int z, int n, int a, int b, int c) {
            line = z;
            num = n;
            to = a;
            dir = b;
            w = c;
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
