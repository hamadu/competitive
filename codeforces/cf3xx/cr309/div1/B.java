package codeforces.cf3xx.cr309.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/06/25.
 */
public class B {

    private static final long MOD = 1_000_000_007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = in.nextInt();
        long L = in.nextLong();

        fib = new int[n+1];
        fib[0] = fib[1] = 1;
        for (int i = 2; i <= n ; i++) {
            fib[i] = fib[i-2] + fib[i-1];
        }
        arr = new ArrayList<>();

        dfs(n-1, L);

        debug(arr);

        ex(n, (int)L);
    }

    static int n;
    static int[] fib;
    static List<Integer> arr;

    static void dfs(int level, long idx) {
        if (level == 0) {
            arr.add(0);
            return;
        }
        long F = fib[level];
        if (idx > F) {
            // upper ptn
            dfs(level-1, idx-F);

            arr.add(1, 0);
            for (int i = 0 ; i < arr.size() ; i++) {
                if (i != 1) {
                    arr.set(i, arr.get(i)+1);
                }
            }
        } else {
            // lower ptn
            dfs(level-1, idx);
            arr.add(0, 0);
            for (int i = 1; i < arr.size() ; i++) {
                arr.set(i, arr.get(i)+1);
            }
        }
    }


    public static void ex(int n, int ct) {
        int[] perm = new int[n];
        for (int i = 0; i < n ; i++) {
            perm[i] = i;
        }
        do {
            List<List<Integer>> x = new ArrayList<>();
            boolean[] done = new boolean[n];
            for (int i = 0; i < n; i++) {
                if (done[i]) {
                    continue;
                }
                List<Integer> ll = new ArrayList<>();
                ll.add(i);
                done[i] = true;
                int backed = i;
                int now = perm[i];
                int max = i;
                int maxc = 0;
                while (now != backed) {
                    if (max < now) {
                        max = now;
                        maxc = ll.size();
                    }
                    ll.add(now);
                    done[now] = true;
                    now = perm[now];
                }

                List<Integer> f = new ArrayList<>();
                for (int j = maxc; j < maxc + ll.size()  ; j++) {
                    f.add(ll.get(j%ll.size()));
                }
                x.add(f);
            }

            Collections.sort(x, new Comparator<List<Integer>>() {
                @Override
                public int compare(List<Integer> o1, List<Integer> o2) {
                    return o1.get(0) - o2.get(0);
                }
            });

            debug(x);

            int[] pl = new int[n];
            int ci = 0;
            for (List<Integer> xi : x) {
                for (int ii : xi) {
                    pl[ci++] = ii;
                }
            }


            boolean same = true;
            for (int i = 0; i < n ; i++) {
                if (pl[i] != perm[i]) {
                    same = false;
                    break;
                }
            }
            if (same) {
                if (--ct == 0) {
                    debug(perm);
                    return;
                }
            }
        } while (next_permutation(perm));
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
