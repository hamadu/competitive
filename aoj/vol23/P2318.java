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
public class P2318 {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int e = in.nextInt();
        int t = in.nextInt() - 1;
        boolean[] w = new boolean[n];
        for (int i = 0; i < n ; i++) {
            w[i] = in.nextInt() == 1;
        }
        W = w;

        graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<int[]>();
        }
        for (int i = 0; i < e ; i++) {
            int g = in.nextInt()-1;
            int c = in.nextInt();
            int[] materials = new int[c];
            for (int j = 0; j < c ; j++) {
                materials[j] = in.nextInt()-1;
            }
            graph[g].add(materials);
        }

        int[] dp = new int[n];
        for (int i = 0; i < n ; i++) {
            dp[i] = W[i] ? 1 : INF;
        }
        boolean upd = true;
        while (upd) {
            upd = false;
            for (int i = 0; i < n ; i++) {
                for (int[] mat : graph[i]) {
                    int[] mv = new int[mat.length];
                    for (int j = 0; j < mat.length ; j++) {
                        mv[j] = dp[mat[j]];
                    }
                    Arrays.sort(mv);
                    int free = 0;
                    int cnt = 0;
                    for (int j = mat.length-1 ; j >= 0 ; j--) {
                        if (free < mv[j]) {
                            cnt += mv[j] - free;
                            free = mv[j] - 1;
                        } else {
                            free -= 1;
                        }
                    }
                    if (cnt < INF) {
                        if (dp[i] > cnt) {
                            dp[i] = cnt;
                            upd = true;
                        }
                    }
                }
            }
        }

        out.println(dp[t] >= INF ? -1 : dp[t]);
        out.flush();
    }

    static boolean[] W;
    static List<int[]>[] graph;
    static int INF = 1000000;

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
