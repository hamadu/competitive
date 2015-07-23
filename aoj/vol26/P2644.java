package aoj.vol26;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by hama_du on 15/07/24.
 */
public class P2644 {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        PMA leftRoot = new PMA();
        PMA rightRoot = new PMA();

        char[] s = in.nextToken().toCharArray();
        char[] rs = s.clone();
        for (int i = 0; i < s.length / 2; i++) {
            char tmp = rs[i];
            rs[i] = rs[s.length-1-i];
            rs[s.length-1-i] = tmp;
        }

        int m = in.nextInt();
        char[][] left = new char[m][];
        char[][] right = new char[m][];
        for (int i = 0; i < m ; i++) {
            left[i] = in.nextToken().toCharArray();
            right[i] = in.nextToken().toCharArray();
            for (int r = 0; r < right[i].length / 2 ; r++) {
                char tmp = right[i][r];
                right[i][r] = right[i][right[i].length-1-r];
                right[i][right[i].length-1-r] = tmp;
            }
        }
        leftRoot.digWords(left);
        leftRoot.buildFailureLink();

        rightRoot.digWords(right);
        rightRoot.buildFailureLink();

        int n = s.length;
        int[] L = leftRoot.find(s, left);
        int[] R = rightRoot.find(rs, right);


        for (int i = 0; i < m ; i++) {
            if (L[i] == Integer.MAX_VALUE || R[i] == Integer.MAX_VALUE) {
                out.println(0);
            } else {
                int len = (n - R[i]) - L[i];
                if (len < Math.max(left[i].length, right[i].length)) {
                    out.println(0);
                } else {
                    out.println((n - R[i]) - L[i]);
                }
            }
        }
        out.flush();
    }

    static class PMA {
        List<Integer> accepted;
        PMA[] next;
        PMA failure;
        boolean flag;

        PMA() {
            flag = false;
            next = new PMA[26];
            failure = null;
            accepted = new ArrayList<Integer>();
        }

        void digWords(char[][] str) {
            int n = str.length;
            for (int id = 0; id < n ; id++) {
                PMA now = this;
                for (int idx = 0; idx < str[id].length ; idx++) {
                    int ci = str[id][idx]-'a';
                    if (now.next[ci] == null) {
                        now.next[ci] = new PMA();
                    }
                    now = now.next[ci];
                }
                now.accepted.add(id);
            }
        }

        int[] find(char[] haystack, char[][] words) {
            int r = words.length;
            int[] found = new int[r];
            Arrays.fill(found, Integer.MAX_VALUE);
            int n = haystack.length;
            PMA now = this;
            for (int i = 0; i < n ; i++) {
                now = now.next[haystack[i]-'a'];
                PMA tracking = now;
                while (!tracking.flag) {
                    for (int id : tracking.accepted) {
                        found[id] = Math.min(found[id], i - words[id].length + 1);
                    }
                    tracking.flag = true;
                    tracking = tracking.failure;
                }
            }
            return found;
        }

        void buildFailureLink() {
            flag = true;
            Queue<PMA> q = new ArrayBlockingQueue<PMA>(200010);
            for (int i = 0; i < 26 ; i++) {
                if (next[i] != null) {
                    next[i].failure = this;
                    q.add(next[i]);
                } else {
                    next[i] = this;
                }
            }
            while (q.size() >= 1) {
                PMA node = q.poll();
                for (int i = 0; i < 26; i++) {
                    if (node.next[i] != null) {
                        q.add(node.next[i]);
                    }
                    PMA fail = node.failure;
                    while (fail.next[i] == null) {
                        fail = fail.failure;
                    }
                    if (node.next[i] != null) {
                        node.next[i].failure = fail.next[i];
                    } else {
                        node.next[i] = fail.next[i];
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
