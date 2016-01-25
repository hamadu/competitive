package atcoder.ttpc2015;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by hama_du on 15/09/20.
 */
public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        char[] s = in.nextToken().toCharArray();

        int[] idx = new int[26];
        Arrays.fill(idx, -1);
        int kind = 0;
        for (int i = 0; i < s.length ; i++) {
            int x = idx[s[i]-'a'];
            if (x == -1) {
                idx[s[i]-'a'] = kind++;
            }
        }
        if (kind <= 5) {
            int[] a = new int[s.length];
            for (int i = 0; i < s.length; i++) {
                a[i] = idx[s[i]-'a'];
            }
            int[] table = new int[kind];
            Arrays.fill(table, -1);
            dfs(0, 0, new boolean[10], table, a);
        }
        out.println(ans);
        out.flush();
    }

    static long ans = -1;

    static boolean isPrime(long num) {
        if (num == 1) {
            return false;
        }
        for (long f = 2 ; f * f <= num ; f++) {
            if (num % f == 0) {
                return false;
            }
        }
        return true;
    }

    static void dfs(int now, long num, boolean[] used, int[] table, int[] a) {
        if (now == a.length) {
            if (ans == -1 && isPrime(num)) {
                ans = num;
            }
            return;
        }
        if (table[a[now]] == -1) {
            for (int k = 1 ; k <= 9 ; k += 2) {
                if (!used[k]) {
                    table[a[now]] = k;
                    used[k] = true;

                    dfs(now+1, num*10+table[a[now]], used, table, a);

                    table[a[now]] = -1;
                    used[k] = false;
                }
            }
        } else {
            dfs(now+1, num*10+table[a[now]], used, table, a);
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
