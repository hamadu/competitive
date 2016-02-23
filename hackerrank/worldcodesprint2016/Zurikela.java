package hackerrank.worldcodesprint2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 2016/01/30.
 */
public class Zurikela {

    static int MAX = 100010;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        graph = new List[MAX];
        for (int i = 0; i < MAX ; i++) {
            graph[i] = new ArrayList<>();
        }

        int q = in.nextInt();
        value = new int[MAX];
        touched = new boolean[MAX];

        int k = 0;
        while (--q >= 0) {
            char c = in.nextChar();
            if (c == 'A') {
                int num = in.nextInt();
                value[k] = num;
                k++;
            } else if (c == 'B') {
                int a = in.nextInt()-1;
                int b = in.nextInt()-1;
                graph[a].add(b);
                graph[b].add(a);
            } else {
                int root = in.nextInt()-1;
                int[] num = dfs(root, -1);
                value[k] = Math.max(num[0], num[1]);
                k++;
            }
        }
        Arrays.fill(touched, false);

        int sum = 0;
        for (int i = 0 ; i < MAX ; i++) {
            if (!touched[i]) {
                int[] res = dfs(i, -1);
                sum += Math.max(res[0], res[1]);
            }
        }
        out.println(sum);
        out.flush();
    }

    static boolean[] touched = new boolean[MAX];
    static int[] value;
    static List<Integer>[] graph;

    static int[] dfs(int now, int par) {
        touched[now] = true;
        int take = value[now];
        int nontake = 0;
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            int[] res = dfs(to, now);
            take += res[1];
            nontake += Math.max(res[0], res[1]);
        }
        graph[now].clear();
        value[now] = 0;
        return new int[]{take, nontake};
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
