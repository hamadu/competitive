package codeforces.cf3xx.cf362.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 2016/07/15.
 */
public class D {
    static final long INF = (long)1e17;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        long l = in.nextLong();
        long[] happy = new long[n];
        for (int i = 0; i < n ; i++) {
            happy[i] = in.nextInt();
        }
        char[][] words = new char[n][];
        for (int i = 0; i < n ; i++) {
            words[i] = in.nextToken().toCharArray();
        }

        PrefixAutomaton p = new PrefixAutomaton(words);
        long[] score = p.distributeWordScore(words, happy);

        int wn = p.ni;
        long[][] mat = new long[wn][wn];
        for (int i = 0; i < wn ; i++) {
            Arrays.fill(mat[i], -INF);
        }
        for (int i = 0; i < wn ; i++) {
            for (int j = 0; j < 26 ; j++) {
                int to = p.allNext[i][j];
                mat[to][i] = score[to];
            }
        }

        long[][] res = pow(mat, l);
        long max = 0;
        for (int i = 0; i < wn ; i++) {
            max = Math.max(max, res[i][0]);
        }

        out.println(max);
        out.flush();
    }

    public static long[][] pow(long[][] a, long n) {
        long i = 1;
        long[][] res = E(a.length);
        long[][] ap = mul(E(a.length), a);
        while (i <= n) {
            if ((n & i) >= 1) {
                res = mul(res, ap);
            }
            i *= 2;
            ap = mul(ap, ap);
        }
        return res;
    }

    public static long[][] E(int n) {
        long[][] a = new long[n][n];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(a[i], -INF);
            a[i][i] = 0;
        }
        return a;
    }

    public static long[][] mul(long[][] a, long[][] b) {
        long[][] c = new long[a.length][b[0].length];
        if (a[0].length != b.length) {
            System.err.print("err");
        }
        for (int i = 0 ; i < a.length ; i++) {
            for (int j = 0 ; j < b[0].length ; j++) {
                long sum = -INF;
                for (int k = 0 ; k < a[0].length ; k++) {
                    sum = Math.max(sum, a[i][k]+b[k][j]);
                }
                c[i][j] = sum;
            }
        }
        return c;
    }


    static class PrefixAutomaton {
        int n;
        char[][] words;
        int[][] next;
        int[][] allNext;
        int[] parentId;
        int[] lastCharacter;
        int[] failure;
        int ni;

        public PrefixAutomaton(char[][] words) {
            n = 1;
            this.words = words;
            for (int i = 0; i < words.length ; i++) {
                n += words[i].length;
            }
            next = new int[n+1][26];
            allNext = new int[n+1][26];
            parentId = new int[n+1];
            lastCharacter = new int[n+1];
            ni = 1;
            for (char[] w : words) {
                add(w);
            }
            buildFailureLink();
        }

        private int go(String l) {
            return go(l.toCharArray());
        }

        private int go(char[] l) {
            int head = 0;
            for (int i = 0; i < l.length ; i++) {
                head = next[head][l[i]-'a'];
            }
            return head;
        }

        private void add(char[] c) {
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
                    failure[now] = allNext[parFail][lastCharacter[now]];
                }
                for (int j = 0; j < 26 ; j++) {
                    if (next[now][j] == 0) {
                        allNext[now][j] = allNext[failure[now]][j];
                    } else {
                        allNext[now][j] = next[now][j];
                        que[qh++] = next[now][j];
                    }
                }
            }
        }

        private long[] distributeWordScore(char[][] word, long[] s) {
            long[] score = new long[ni];
            for (int i = 0 ; i < word.length ; i++) {
                int head = go(word[i]);
                score[head] += s[i];
            }
            int[] que = new int[n];
            int qh = 0;
            int qt = 0;
            que[qh++] = 0;
            while (qt < qh) {
                int now = que[qt++];
                score[now] += score[failure[now]];
                for (int i = 0; i < 26 ; i++) {
                    if (next[now][i] != 0) {
                        que[qh++] = next[now][i];
                    }
                }
            }
            return score;
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
