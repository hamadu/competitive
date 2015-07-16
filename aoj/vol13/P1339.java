package aoj.vol13;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/16.
 */
public class P1339 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        prec();

        while (true) {
            int ch = in.nextInt();
            int cv = in.nextInt();
            if (ch + cv == 0) {
                break;
            }
            int[][] board = new int[2][9];
            for (int d = 0; d <= 1; d++) {
                for (int i = 0; i < 9; i++) {
                    board[d][i] = in.nextInt();
                }
            }
            out.println(solve(board[0], board[1], ch, cv));
        }
        out.flush();
    }

    static int[] dp = new int[362880];

    private static int solve(int[] from, int[] to, int ch, int cv) {
        int fromID = encode(from);
        int toID = encode(to);

        Arrays.fill(dp, Integer.MAX_VALUE);

        Queue<State> q = new PriorityQueue<State>();
        q.add(new State(fromID, 0));
        dp[fromID] = 0;
        while (q.size() >= 1) {
            State st = q.poll();
            int id = st.id;
            for (int d = 0 ; d < 4 ; d++) {
                int tid = graph[id][d];
                int tcost = st.time + ((d <= 1) ? ch : cv);
                if (dp[tid] > tcost) {
                    dp[tid] = tcost;
                    q.add(new State(tid, tcost));
                }
            }
        }
        return dp[toID];
    }

    static class State implements Comparable<State> {
        int id;
        int time;

        State(int i, int t) {
            id = i;
            time = t;
        }

        @Override
        public int compareTo(State o) {
            return time - o.time;
        }
    }

    static Map<Integer,Integer> codeToID = new HashMap<Integer,Integer>();

    static int[][] move = {
            {1, 8, 6, 3},
            {2, 0, 7, 4},
            {3, 1, 8, 5},
            {4, 2, 0, 6},
            {5, 3, 1, 7},
            {6, 4, 2, 8},
            {7, 5, 3, 0},
            {8, 6, 4, 1},
            {0, 7, 5, 2}
    };

    static int[][] graph = new int[362880][4];

    static void prec() {
        int[] perm = new int[9];
        for (int j = 0; j < 9 ; j++) {
            perm[j] = j;
        }

        int id = 0;
        do {
            int code = 0;
            int nine = 1;
            for (int i = 0; i < 9 ; i++) {
                code += nine * perm[i];
                nine *= 9;
            }
            codeToID.put(code, id);
            id++;
        } while (next_permutation(perm));

        Arrays.sort(perm);
        do {
            int myID = encode(perm);

            int sp = -1;
            for (int i = 0 ; i < 9 ; i++) {
                if (perm[i] == 0) {
                    sp = i;
                    break;
                }
            }

            int tmp = -1;
            for (int d = 0 ; d < 4 ; d++) {
                int ps = move[sp][d];
                tmp = perm[sp];
                perm[sp] = perm[ps];
                perm[ps] = tmp;
                graph[myID][d] = encode(perm);
                tmp = perm[sp];
                perm[sp] = perm[ps];
                perm[ps] = tmp;
            }
        } while (next_permutation(perm));
    }



    static int code(int[] perm) {
        int code = 0;
        int nine = 1;
        for (int i = 0; i < 9 ; i++) {
            code += nine * perm[i];
            nine *= 9;
        }
        if (code < 0) {
            debug("whoa!");
        }
        return code;
    }

    static int encode(int[] perm) {
        return codeToID.get(code(perm));
    }

    public static boolean next_permutation(int[] num) {
        int len = num.length;
        int x = len - 2;
        while (x >= 0 && num[x] >= num[x+1]) {
            x--;
        }
        if (x == -1) return false;

        int y = len - 1;
        while (y > x && num[y] <= num[x]) {
            y--;
        }
        int tmp = num[x];
        num[x] = num[y];
        num[y] = tmp;
        java.util.Arrays.sort(num, x+1, len);
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
