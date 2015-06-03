package codeforces.cr131.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by dhamada on 15/06/03.
 */
public class A {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n ; i++) {
            a[i] = in.nextInt() - 1;
        }
        
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < n ; i++) {
            int k = in.nextInt();
            for (int j = 0; j < k ; j++) {
                int before = in.nextInt() - 1;
                graph[before].add(i);
            }
        }

        int c1 = solve(0, a, graph);
        int c2 = solve(1, a, graph);
        int c3 = solve(2, a, graph);

        out.println(Math.min(c1, Math.min(c2, c3)) + n);
        out.flush();
    }

    private static int solve(int start, int[] a, List<Integer>[] graph) {
        List<Integer>[] games = new List[3];
        int n = a.length;
        for (int i = 0; i < 3 ; i++) {
            games[i] = new ArrayList<>();
        }
        for (int i = 0 ; i < n ; i++) {
            games[a[i]].add(i);
        }

        int[] indeg = new int[n];
        for (int i = 0 ; i < n ; i++) {
            for (int t : graph[i]) {
                indeg[t]++;
            }
        }
        boolean[] finished = new boolean[n];
        int now = start;
        int completed = 0;
        int time = 0;
        while (true) {
            boolean updated = true;
            while (updated) {
                updated = false;
                for (int gi : games[now]) {
                    if (indeg[gi] == 0 && !finished[gi]) {
                        finished[gi] = true;
                        completed++;
                        for (int to : graph[gi]) {
                            indeg[to]--;
                        }
                        updated = true;
                    }
                }
            }

            if (completed == n) {
                break;
            }
            time++;
            now = (now + 1) % 3;
        }
        return time;
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
