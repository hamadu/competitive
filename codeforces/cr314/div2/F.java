package codeforces.cr314.div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 15/08/10.
 */
public class F {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = in.nextInt();
        int k = in.nextInt();
        cds = new List[2*n];
        for (int i = 0; i < 2*n ; i++) {
            cds[i] = new ArrayList<>();
        }
        for (int i = 0; i < k ; i++) {
            int a = in.nextInt()-1;
            String tp = in.nextToken();
            int b = in.nextInt()-1;
            Condition cd = new Condition(a, tp, b);
            Condition cdRev = cd.rev();
            cds[cd.a].add(cd);
            cds[cdRev.a].add(cdRev);
        }
        memo = new long[n][2*n][2*n];
        for (int i = 0 ; i < n; i++) {
            for (int j = 0; j < 2*n; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }

        out.println(dfs(0, 0, 2*n-1));
        out.flush();
    }

    // 123456
    //

    static int n;
    static List<Condition>[] cds;
    static long[][][] memo;

    static long dfs(int idx, int fr, int to) {
        if (idx == n) {
            return 1;
        }
        if (memo[idx][fr][to] != -1) {
            return memo[idx][fr][to];
        }

        int[][] next = new int[3][];
        int[][] zero = new int[3][];
        int[][] lessEqualMore = new int[3][2*n];
        for (int i = 0; i < 3 ; i++) {
            Arrays.fill(lessEqualMore[i], -1);
        }
        for (int i = 0; i < 3; i++) {
            for (int j = fr ; j <= to; j++) {
                lessEqualMore[i][j] = 1;
            }
        }

        // 1-1-[]
        next[0] = new int[]{fr+2, to};
        zero[0] = new int[]{fr, fr+1};

        // []-1-1
        next[1] = new int[]{fr, to-2};
        zero[1] = new int[]{to, to-1};

        // 1-[]-1
        next[2] = new int[]{fr+1, to-1};
        zero[2] = new int[]{fr, to};

        int upk = (fr+1 == to) ? 1 : 3;
        long ret = 0;
        for (int k = 0; k < upk ; k++) {
            boolean can = true;
            List<Condition> cond = new ArrayList<>();
            for (int i = 0; i <= 1; i++) {
                cond.addAll(cds[zero[k][i]]);
                lessEqualMore[k][zero[k][i]] = 0;
            }
            for (Condition cd : cond) {
                if (!cd.match(lessEqualMore[k][cd.b])) {
                    can = false;
                    break;
                }
            }
            if (can) {
                ret += dfs(idx+1, next[k][0], next[k][1]);
            }
        }
        memo[idx][fr][to] = ret;
        return ret;
    }

    // [fr,to]
    static class Condition  {
        int a;
        String type;
        int b;

        Condition(int a, String t, int b) {
            this.a = a;
            this.type = t;
            this.b = b;
        }

        Condition rev() {
            return new Condition(b, revType(type), a);
        }


        // less : -1
        // eq : 0
        // more : 1
        boolean match(int lessEqMore) {
            if (type.equals("=")) {
                return lessEqMore == 0;
            } else if (type.equals("<")) {
                return lessEqMore == 1;
            } else if (type.equals(">")) {
                return lessEqMore == -1;
            } else if (type.equals("<=")) {
                return lessEqMore != -1;
            } else if (type.equals(">=")) {
                return lessEqMore != 1;
            } else {
                throw new RuntimeException();
            }
        }

        static String revType(String type) {
            if (type.equals("=")) {
                return type;
            } else if (type.equals("<")) {
                return ">";
            } else if (type.equals(">")) {
                return "<";
            } else if (type.equals("<=")) {
                return ">=";
            } else if (type.equals(">=")) {
                return "<=";
            } else {
                throw new RuntimeException();
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
