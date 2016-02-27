package atcoder.other2015.ttpc2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/09/20.
 */
public class N {
    private static final double INF = 1e10;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        double[] val = new double[n+1];
        Arrays.fill(val, INF);
        val[n] = 0;
        for (int i = 0; i < k ; i++) {
            int idx = in.nextInt()-1;
            val[idx] = in.nextInt();
        }

        int[][] edge = new int[m][3];
        for (int i = 0; i < m ; i++) {
            for (int j = 0; j <= 1 ; j++) {
                edge[i][j] = in.nextInt()-1;
            }
            edge[i][2] = in.nextInt();
        }

        double left = -1e8;
        double right = 1e8;
        for (int i = 0 ; i < 80 ; i++) {
            double med = (left + right) / 2;
            if (isOK(edge, val.clone(), med)) {
                right = med;
            } else {
                left = med;
            }
        }
        if (left <= -1e7) {
            out.println("#");
        } else {
            if (Math.abs(left) < 1e-7) {
                out.println(0);
            } else {
                out.println(String.format("%.12f", left));
            }
        }
        out.flush();
    }

    private static boolean isOK(int[][] edge, double[] val, double med) {
        int n = val.length;
        List<Edge>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] ed : edge) {
            int a = ed[0];
            int b = ed[1];
            double c = ed[2];
            graph[b].add(new Edge(b, a, med - c));
        }
        for (int i = 0 ; i < n-1 ; i++) {
            if (val[i] != INF) {
                graph[n-1].add(new Edge(n-1, i, val[i]));
                graph[i].add(new Edge(i, n-1, -val[i]));
            }
        }
        Arrays.fill(val, INF);

        for (int cur = 0 ; cur <= n+5 ; cur++) {
            boolean upd = false;
            for (int i = 0 ; i < n ; i++) {
                for (Edge e : graph[i]) {
                    int j = e.to;
                    if (val[i] + e.cost < val[j]) {
                        upd = true;
                        val[j] = val[i] + e.cost;
                    }
                }
            }
            if (!upd) {
                return true;
            }
        }
        return false;
    }


    static class Edge {
        int fr;
        int to;
        double cost;

        Edge(int a, int b, double c) {
            fr = a;
            to = b;
            cost = c;
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
