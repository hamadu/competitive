package atcoder.other2016.tenka1.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by hama_du on 2016/09/15.
 */
public class C {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] s = in.nextToken().toCharArray();
        int m = in.nextInt();
        char[][] part = new char[m][];
        for (int i = 0; i < m ; i++) {
            part[i] = in.nextToken().toCharArray();
        }
        int[] weight = new int[m];
        for (int i = 0; i < m ; i++) {
            weight[i] = in.nextInt();
        }

        List<char[]>[] group = new List[200];
        List<Integer>[] scores = new List[200];
        for (int i = 0; i < 200; i++) {
            group[i] = new ArrayList<>();
            scores[i] = new ArrayList<>();
        }
        for (int i = 0 ; i < m; i++) {
            int len = part[i].length-1;
            group[len].add(part[i]);
            scores[len].add(weight[i]);
        }

        PrefixAutomaton[] trie = new PrefixAutomaton[200];
        for (int i = 0; i < 200; i++) {
            trie[i] = new PrefixAutomaton(group[i], scores[i]);
        }
        int[] heads = new int[200];

        int n = s.length;
        long[] dp = new long[n+1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 1 ; i <= n ; i++) {
            int c = s[i-1]-'a';
            dp[i] = Math.max(dp[i], dp[i-1]);
            for (int j = 0; j < 200; j++) {
                heads[j] = trie[j].next[heads[j]][c];
                if (i-j-1 < 0) {
                    continue;
                }
                dp[i] = Math.max(dp[i], dp[i-j-1] + trie[j].score[heads[j]]);
            }
        }
        out.println(dp[n]);
        out.flush();
    }

    static class PrefixAutomaton {
        int n;
        List<char[]> words;
        int[][] next;
        int[] score;
        int[] parentId;
        int[] lastCharacter;
        int[] failure;
        int ni;

        public PrefixAutomaton(List<char[]> words, List<Integer> weights) {
            n = 1;
            this.words = words;
            for (char[] w : words) {
                n += w.length;
            }
            next = new int[n+1][26];
            parentId = new int[n+1];
            lastCharacter = new int[n+1];
            score = new int[n+1];
            ni = 1;

            int wn = words.size();
            for (int i = 0; i < wn ; i++) {
                add(words.get(i), weights.get(i));
            }
            buildFailureLink();
        }

        private void add(char[] c, int s) {
            int head = 0;
            for (int i = 0; i < c.length ; i++) {
                int ci = c[i]-'a';
                if (next[head][ci] == 0) {
                    next[head][ci] = ni++;
                }
                parentId[next[head][ci]] = head;
                head = next[head][ci];
                lastCharacter[head] = ci;
            }
            score[head] += s;
        }

        private void buildFailureLink() {
            int[] que = new int[n];
            int qh = 0;
            int qt = 0;
            que[qh++] = 0;
            failure = new int[n];
            while (qt < qh) {
                int now = que[qt++];
                if (parentId[now] >= 1) {
                    int parFail = failure[parentId[now]];
                    failure[now] = next[parFail][lastCharacter[now]];
                }
                for (int j = 0; j < 26 ; j++) {
                    if (next[now][j] == 0) {
                        next[now][j] = next[failure[now]][j];
                    } else {
                        que[qh++] = next[now][j];
                    }
                }
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

        private int[] nextInts(int n) {
            int[] ret = new int[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextInt();
            }
            return ret;
        }


        private int[][] nextIntTable(int n, int m) {
            int[][] ret = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextInt();
                }
            }
            return ret;
        }

        private long[] nextLongs(int n) {
            long[] ret = new long[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextLong();
            }
            return ret;
        }

        private long[][] nextLongTable(int n, int m) {
            long[][] ret = new long[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ret[i][j] = nextLong();
                }
            }
            return ret;
        }

        private double[] nextDoubles(int n) {
            double[] ret = new double[n];
            for (int i = 0; i < n; i++) {
                ret[i] = nextDouble();
            }
            return ret;
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
            return res*sgn;
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
            return res*sgn;
        }

        public double nextDouble() {
            return Double.valueOf(nextToken());
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
