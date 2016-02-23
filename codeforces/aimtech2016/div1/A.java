package codeforces.aimtech2016.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 2016/02/08.
 */
public class A {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] graph = new int[n][n];
        for (int i = 0; i < m ; i++) {
            int a = in.nextInt() - 1;
            int b = in.nextInt() - 1;
            graph[a][b] = graph[b][a] = 1;
        }
        String ans = solve(graph);
        if (ans == null) {
            out.println("No");
        } else {
            out.println("Yes");
            out.println(ans);
        }
        out.flush();
    }


    static String solve(int[][] graph) {
        int n = graph.length;
        List<Integer>[] cog = new List[n];
        for (int i = 0 ; i < n ; i++) {
            cog[i] = new ArrayList<>();
        }
        for (int i = 0 ; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                if (graph[i][j] == 0) {
                    cog[i].add(j);
                    cog[j].add(i);
                }
            }
        }

        int[] color = new int[n];
        for (int i = 0 ; i < n ; i++) {
            if (color[i] != 0) {
                continue;
            }
            if (cog[i].size() == 0) {
                continue;
            }
            if (!dfs(i, 1, cog, color)) {
                return null;
            }
        }

        for (int i = 0 ; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (color[i] == 1 && color[j] == -1 && graph[i][j] == 1) {
                    return null;
                }
            }
        }
        char[] abc = {'c','b','a'};
        StringBuilder line = new StringBuilder();
        for (int i = 0 ; i < n ; i++) {
            line.append(abc[color[i]+1]);
        }
        return line.toString();
    }


    private static boolean dfs(int now, int col, List<Integer>[] cog, int[] color) {
        if (color[now] != 0) {
            return color[now] == col;
        }
        color[now] = col;
        for (int to : cog[now]) {
            if (!dfs(to, -col, cog, color)) {
                return false;
            }
        }
        return true;
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
