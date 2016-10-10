package atcoder.other2016.codefestival2016.qualb;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class E {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        char[][] s = new char[n][];
        for (int i = 0; i < n ; i++) {
            s[i] = in.nextToken().toCharArray();
        }

        PrefixAutomaton trie = new PrefixAutomaton(s);
        trie.compress();
        trie.goup();

        int[] ord = new int[26];
        int q = in.nextInt();
        while (--q >= 0) {
            int k = in.nextInt()-1;
            char[] target = s[k];
            char[] oc = in.nextToken().toCharArray();
            for (int i = 0; i < 26 ; i++) {
                ord[oc[i]-'a'] = i;
            }

            int head = 0;
            int cnt = 0;
            while (true) {
                int cmp = trie.cmp[head];
                cnt += trie.hereCnt[head];
                if (cmp >= target.length) {
                    break;
                }

                int myindex = ord[target[cmp]-'a'];
                // 26
                for (int i = 0 ; i < 26 ; i++) {
                    int nextID = trie.next[head][i];
                    if (nextID == 0) {
                        continue;
                    }
                    if (ord[i] < myindex) {
                        cnt += trie.cnt[nextID];
                    }
                }
                head = trie.next[head][target[cmp]-'a'];
            }
            out.println(cnt);
        }
        out.flush();
    }

    static class PrefixAutomaton {
        int n;
        char[][] words;
        int[][] next;
        int[][] allNext;
        int[] parentId;
        int[] lastCharacter;
        int[] cnt;
        int[] hereCnt;
        int[] cmp;
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
            cnt = new int[n+1];
            hereCnt = new int[n+1];
            cmp = new int[n+1];
            ni = 1;
            for (char[] w : words) {
                add(w);
            }
        }

        private int go(char[] l) {
            int head = 0;
            for (int i = 0; i < l.length ; i++) {
                int idx = cmp[head];
                if (idx >= l.length) {
                    break;
                }
                head = next[head][l[idx]-'a'];
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
                cmp[head] = i;
                head = next[head][ci];
                lastCharacter[head] = ci;
            }
            cmp[head] = c.length;
            cnt[head]++;
            hereCnt[head]++;
        }

        public void compress() {
            for (int i = 1 ; i < ni ; i++) {
                int has = 0;
                int onlyID = -1;
                for (int k = 0 ; k < 26 ; k++) {
                    if (next[i][k] != 0) {
                        onlyID = next[i][k];
                        has++;
                    }
                }
                if (has == 1 && hereCnt[i] == 0) {
                    int my = lastCharacter[i];
                    next[parentId[i]][my] = onlyID;
                    parentId[onlyID] = parentId[i];
                    lastCharacter[onlyID] = my;
                }
            }
        }

        public void goup() {
            for (int i = ni-1 ; i >= 1 ; i--) {
                cnt[parentId[i]] += cnt[i];
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
