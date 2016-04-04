package codeforces.cf0xx.cr86.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/05/31.
 */
public class B {
    static final int MOD0 = 10007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] _line = in.nextToken().toCharArray();
        char[] _head = in.nextToken().toCharArray();
        char[] _tail = in.nextToken().toCharArray();

        int[] line = convert(_line);
        int[] head = convert(_head);
        int[] tail = convert(_tail);

        int head1 = computeHash(head, MOD0);
        int tail1 = computeHash(tail, MOD0);

        int n = line.length;
        long[] modPow = new long[n+1];
        modPow[0] = 1;
        for (int i = 1; i <= n ; i++) {
            modPow[i] = modPow[i-1] * MOD0;
        }

        int[] heads = new int[n];
        int hi = 0;
        int headHash = 0;
        for (int i = 0 ; i < n ; i++) {
            headHash *= MOD0;
            headHash += line[i];
            if (i >= head.length) {
                headHash -= modPow[head.length] * line[i-head.length];
            }
            if (headHash == head1 && i - head.length + 1 >= 0) {
                heads[hi++] = i-head.length+1;
            }
        }

        int[] tails = new int[n];
        int ti = 0;
        int tailHash = 0;
        for (int i = 0 ; i < n ; i++) {
            tailHash *= MOD0;
            tailHash += line[i];
            if (i >= tail.length) {
                tailHash -= modPow[tail.length] * line[i-tail.length];
            }
            if (tailHash == tail1 && i >= tail.length-1) {
                tails[ti++] = i;
            }
        }

        boolean[][] isOK = new boolean[n][n];
        for (int i = 0 ; i < hi ; i++) {
            for (int j = 0 ; j < ti ; j++) {
                int d = tails[j] - heads[i] + 1;
                if (d >= Math.max(head.length, tail.length)) {
                    isOK[heads[i]][tails[j]] = true;
                }
            }
        }

        int n2 = n;
        int[][] lhashes = new int[n2][n2];
        int[] hidx = new int[n2];
        for (int hii = 0 ; hii < hi ; hii++) {
            int i = heads[hii];
            int hash1 = 0;
            for (int j = i ; j < n ; j++) {
                int d = line[j];
                hash1 *= MOD0;
                hash1 += d;
                if (!isOK[i][j]) {
                    continue;
                }
                int l = j-i;
                lhashes[l][hidx[l]++] = hash1;
            }
        }

        int cnt = 0;
        for (int i = 0 ; i < n ; i++) {
            Arrays.sort(lhashes[i], 0, hidx[i]);
            for (int j = 0; j < hidx[i] ; j++) {
                if (j == 0 || lhashes[i][j-1] != lhashes[i][j]) {
                    cnt++;
                }
            }
        }

        out.println(cnt);
        out.flush();
    }

    private static int[] convert(char[] cl) {
        int[] r = new int[cl.length];
        for (int i = 0; i < cl.length ; i++) {
            r[i] = cl[i] - 'a' + 1;
        }
        return r;
    }

    static int computeHash(int[] x, long d) {
        int val = 0;
        for (int c : x) {
            val *= d;
            val += c;
        }
        return val;
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
