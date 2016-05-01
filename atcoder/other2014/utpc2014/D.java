package atcoder.other2014.utpc2014;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class D {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);


        int m = in.nextInt();
        int[][] finger = new int[m][2];
        for (int i = 0; i < m ; i++) {
            finger[i][0] = in.nextInt();
            finger[i][1] = in.nextInt();
        }

        int n = in.nextInt();
        int[][] circle = new int[n][4];
        for (int i = 0 ; i < n ; i++) {
            circle[i][0] = in.nextInt();
            circle[i][1] = in.nextInt();
            circle[i][2] = in.nextInt();
            circle[i][3] = in.nextInt();
        }

        int[][] g = new int[n][n];
        for (int i = 0 ; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                long between = circle[j][2] - circle[i][3];
                long dx = Math.abs(circle[i][0] - circle[j][0]);
                long dy = Math.abs(circle[i][1] - circle[j][1]);
                if (dx*dx+dy*dy <= between*between) {
                    g[i][j] = 1;
                }
            }
        }
        int[][] g2 = new int[m][n];
        for (int i = 0 ; i < m ; i++) {
            for (int j = 0 ; j < n ; j++) {
                long between = circle[j][2];
                long dx = Math.abs(finger[i][0] - circle[j][0]);
                long dy = Math.abs(finger[i][1] - circle[j][1]);
                if (dx*dx+dy*dy <= between*between) {
                    g2[i][j] = 1;
                }
            }
        }

        init((n+m)*2+2);

        int source = (n+m)*2;
        int sink = (n+m)*2+1;
        for (int i = 0 ; i < n+m ; i++) {
            edge(source, i, 1);
            edge(n+m+i, sink, 1);
        }
        for (int i = 0 ; i < m ; i++) {
            for (int j = 0; j < n ; j++) {
                if (g2[i][j] >= 1) {
                    edge(i, n + m + m + j, 1);
                }
            }
        }
        for (int i = 0 ; i < n ; i++) {
            for (int j = i + 1; j < n; j++) {
                if (g[i][j] >= 1) {
                    edge(m+i, n+m+m+j, m);
                }
            }
        }
        int flow = max_flow(source, sink);
        int path = n+m - flow;
        out.println(path <= m ? "YES" : "NO");
        out.flush();
    }

    public static class Edge {
        int to;
        int cap;
        int rev;
        public Edge(int _to, int _cap, int _rev) {
            to = _to;
            cap = _cap;
            rev = _rev;
        }
    }
    public static Map<Integer, List<Edge>> graph = new HashMap<Integer, List<Edge>>();
    public static int[] level;
    public static int[] itr;
    public static void init(int size) {
        for (int i = 0 ; i < size ; i++) {
            graph.put(i, new ArrayList<Edge>());
        }
        level = new int[size];
        itr = new int[size];
    }
    public static void edge(int from, int to, int cap) {
        graph.get(from).add(new Edge(to, cap, graph.get(to).size()));
        graph.get(to).add(new Edge(from, 0, graph.get(from).size() - 1));
    }
    public static int dfs(int v, int t, int f) {
        if (v == t) return f;
        for (int i = itr[v] ; i < graph.get(v).size() ; i++) {
            itr[v] = i;
            Edge e = graph.get(v).get(i);
            if (e.cap > 0 && level[v] < level[e.to]) {
                int d = dfs(e.to, t, Math.min(f, e.cap));
                if (d > 0) {
                    e.cap -= d;
                    graph.get(e.to).get(e.rev).cap += d;
                    return d;
                }
            }
        }
        return 0;
    }

    public static void bfs(int s) {
        Arrays.fill(level, -1);
        Queue<Integer> q = new ArrayBlockingQueue<Integer>(graph.size()+10);
        level[s] = 0;
        q.add(s);
        while (q.size() >= 1) {
            int v = q.poll();
            for (int i = 0; i < graph.get(v).size() ; i++) {
                Edge e = graph.get(v).get(i);
                if (e.cap > 0 && level[e.to] < 0) {
                    level[e.to] = level[v] + 1;
                    q.add(e.to);
                }
            }
        }
    }

    public static int max_flow(int s, int t) {
        int flow = 0;
        while (true) {
            bfs(s);
            if (level[t] < 0) {
                return flow;
            }
            Arrays.fill(itr, 0);
            while (true) {
                int f = dfs(s, t, Integer.MAX_VALUE);
                if (f <= 0) {
                    break;
                }
                flow += f;
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



