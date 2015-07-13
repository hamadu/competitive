package aoj.vol22;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/07/12.
 */
public class P2257 {
    static int MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int n = in.nextInt();
            int m = in.nextInt();
            int k = in.nextInt();
            if (n + m + k == 0) {
                break;
            }

            Map<String,Integer> wordMap = new HashMap<String,Integer>();
            int[][] con = new int[n][2];
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < 2 ; j++) {
                    String x = in.nextToken();
                    if (!wordMap.containsKey(x)) {
                        wordMap.put(x, wordMap.size());
                    }
                    con[i][j] = wordMap.get(x);
                }
            }

            int[][] graph = new int[wordMap.size()][];
            int[] deg = new int[wordMap.size()];
            for (int i = 0 ; i < n ; i++) {
                deg[con[i][0]]++;
            }
            for (int i = 0; i < wordMap.size() ; i++) {
                graph[i] = new int[deg[i]];
            }
            for (int i = 0 ; i < n ; i++) {
                int a = con[i][0];
                int b = con[i][1];
                graph[a][--deg[a]] = b;
            }

            char[][] keywords = new char[wordMap.size()][];
            for (String key : wordMap.keySet()) {
                keywords[wordMap.get(key)] = key.toCharArray();
            }

            char[][] seasonWords = new char[k][];
            for (int i = 0; i < k ; i++) {
                seasonWords[i] = in.nextToken().toCharArray();
            }
            out.println(solve(keywords, graph, seasonWords, m));
        }
        out.flush();
    }

    private static long solve(char[][] keywords, int[][] graph, char[][] seasonWords, int M) {
        PMA root = new PMA();
        for (int i = 0 ; i < seasonWords.length ; i++) {
            root.dig(seasonWords[i], 0);
        }
        root.buildFailureLink();

        int W = keywords.length;
        int N = PMA.__seq;

        int[][][] find = new int[N][W][2];
        for (int i = 0 ; i < N ; i++) {
            for (int j = 0; j < W ; j++) {
                find[i][j] = PMA.nodes.get(i).find(keywords[j]);
            }
        }

        int[][][][] dp = new int[22][2][N][W];
        dp[0][0][0][0] = 1;

        for (int i = 0 ; i < M ; i++) {
            int ci = i % 22;
            int pi = (ci - 1 + 22) % 22;
            for (int s = 0 ; s <= 1 ; s++) {
                for (int ni = 0; ni < N; ni++) {
                    Arrays.fill(dp[pi][s][ni], 0);
                }
            }

            for (int s = 0 ; s <= 1 ; s++) {
                for (int ni = 0 ; ni < N ; ni++) {
                    for (int wi = 0 ; wi < W ; wi++) {
                        if (dp[ci][s][ni][wi] == 0) {
                            continue;
                        }
                        int base = dp[ci][s][ni][wi];
                        if (i == 0) {
                            for (int tw = 0 ; tw < W ; tw++) {
                                int tc = (i + keywords[tw].length) % 22;
                                int ts = s + find[ni][tw][1];
                                int tn = find[ni][tw][0];
                                if (ts <= 1) {
                                    dp[tc][ts][tn][tw] += base;
                                    if (dp[tc][ts][tn][tw] >= MOD) {
                                        dp[tc][ts][tn][tw] -= MOD;
                                    }
                                }
                            }
                        } else {
                            for (int tw : graph[wi]) {
                                int tc = (i + keywords[tw].length) % 22;
                                int ts = s + find[ni][tw][1];
                                int tn = find[ni][tw][0];
                                if (ts <= 1) {
                                    dp[tc][ts][tn][tw] += base;
                                    if (dp[tc][ts][tn][tw] >= MOD) {
                                        dp[tc][ts][tn][tw] -= MOD;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        long all = 0;
        for (int ni = 0 ; ni < N ; ni++) {
            for (int wi = 0; wi < W; wi++) {
                all += dp[M%22][1][ni][wi];
            }
        }
        return all % MOD;
    }

    static class PMA {
        static int __accepted;
        static List<PMA> nodes = new ArrayList<PMA>();
        static Map<String, PMA> nodeMap = new HashMap<String, PMA>();
        static PMA __root;
        static int __seq;

        int id;
        int accepted;
        int[] next;
        int failure;
        String all = "";

        PMA() {
            this('$', null);
        }

        PMA(char c, PMA par) {
            if (par != null) {
                all = par.all + c;
            } else {
                __seq = 0;
                __root = this;
                nodes.clear();
                nodeMap.clear();
            }

            id = __seq++;
            next = new int[26];
            Arrays.fill(next, -1);
            failure = 0;
            accepted = 0;
            nodes.add(this);
            nodeMap.put(all, this);
        }

        void dig(char[] str, int idx) {
            if (str.length == idx) {
                accepted += 1;
                return;
            }
            int ci = str[idx]-'a';
            if (next[ci] == -1) {
                next[ci] = (new PMA(str[idx], this)).id;
            }
            nodes.get(next[ci]).dig(str, idx+1);
        }

        int[] find(char[] str) {
            __accepted = 0;
            PMA pma = find(str, 0);
            return new int[]{pma.id, __accepted};
        }

        PMA find(char[] str, int idx) {
            if (idx >= 1) {
                __accepted += accepted;
            }
            if (str.length == idx) {
                return this;
            }
            int ci = str[idx]-'a';
            if (next[ci] == -1) {
                if (id == 0) {
                    return find(str, idx+1);
                } else {
                    return nodes.get(failure).find(str, idx);
                }
            } else {
                return nodes.get(next[ci]).find(str, idx+1);
            }
        }

        void buildFailureLink() {
            for (int i = 1 ; i < all.length() ; i++) {
                String sub = all.substring(i);
                if (nodeMap.containsKey(sub)) {
                    failure = nodeMap.get(sub).id;
                    accepted += nodes.get(failure).accepted;
                    break;
                }
            }
            for (int i = 0 ; i < 26 ; i++) {
                if (next[i] != -1) {
                    nodes.get(next[i]).buildFailureLink();
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
