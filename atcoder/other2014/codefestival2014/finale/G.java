package atcoder.other2014.codefestival2014.finale;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by hama_du on 4/29/16.
 */
public class G {

    static long ans = 0;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        long n = in.nextLong();
        List<long[]> f = factor(n);
        int[] cnt = new int[f.size()];
        for (int i = 0; i < f.size() ; i++) {
            cnt[i] = (int)f.get(i)[1];
        }
        for (int i = 1 ; i < 50 ; i++) {
            wmap[i] = genMap(i);
        }
        memo = new Map[50];
        for (int i = 0; i < 50 ; i++) {
            memo[i] = new TreeMap<>();
        }
        long ans = dfs(0, cnt, new int[9], 0);

        out.println(ans / 8);
        out.flush();
    }

    private static List<int[]> genMap(int n) {
        List<int[]> f = new ArrayList<>();
        for (int i = 0; i <= 2*n ; i++) {
            for (int j = 0; j <= 2*n ; j++) {
                int[] m = check(i, j, n);
                if (m != null) {
                    f.add(m);
                }
            }
        }
        return f;
    }


    static Map<Long,Long>[] memo;

    static long dfs(int idx, int[] cnt, int[] id, long code) {
        if (memo[idx].containsKey(code)) {
            return memo[idx].get(code);
        }
        if (isUniq(id)) {
            long ptn = 1;
            for (int i = idx ; i < cnt.length ; i++) {
                ptn *= wmap[cnt[i]].size();
            }
            memo[idx].put(code, ptn);
            return ptn;
        }
        if (idx >= cnt.length) {
            memo[idx].put(code, 0L);
            return 0;
        }
        long ret = 0;
        for (int[] field : wmap[cnt[idx]]) {
            int[] id2 = id.clone();
            Map<Integer,Integer> map = new HashMap<>();
            for (int i = 0 ; i < 9 ; i++) {
                id2[i] = (id2[i]<<10)+field[i];
                if (!map.containsKey(id2[i])) {
                    map.put(id2[i], map.size());
                }
            }
            long tcode = 0;
            for (int i = 0; i < 9 ; i++) {
                tcode *= 10;
                id2[i] = map.get(id2[i]);
                tcode += id2[i];
            }
            ret += dfs(idx+1, cnt, id2, tcode);
        }
        memo[idx].put(code, ret);
        return ret;
    }

    private static boolean isUniq(int[] id) {
        int ct = 0;
        for (int i = 0; i < id.length ; i++) {
            ct |= 1<<id[i];
        }
        return Integer.bitCount(ct) == 9;
    }

    static List<int[]>[] wmap = new List[50];

    private static int[] check(int a, int b, int n) {
        int sum = n*3;
        int c = sum - a - b;
        int[][] map = new int[][]{
                {a,         c,          b},
                {b+n-a,     n,          a+n-b},
                {sum-b-n,   sum-c-n,    sum-a-n}
        };
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3 ; j++) {
                if (map[i][j] < 0) {
                    return null;
                }
            }
        }
        int[] ml = new int[9];
        for (int i = 0; i < 9 ; i++) {
            ml[i] = map[i/3][i%3];
        }
        return ml;
    }

    static int[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        int pi = 0;
        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                pi++;
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }

        int[] ret = new int[pi];
        int ri = 0;
        for (int i = 2 ; i < upto ; i++) {
            if (isp[i]) {
                ret[ri++] = i;
            }
        }
        return ret;
    }

    static List<long[]> factor(long n) {
        int[] p = generatePrimes(1000000);
        List<long[]> f = new ArrayList<>();
        for (int pi : p) {
            if (n == 1) {
                break;
            }
            long cnt = 0;
            while (n % pi == 0) {
                cnt++;
                n /= pi;
            }
            if (cnt >= 1) {
                f.add(new long[]{pi, cnt});
            }
        }
        if (n >= 2) {
            f.add(new long[]{n, 1});
        }
        return f;
    }

    static long[] divisor(long n) {
        long[] d = new long[10000];
        int di = 0;
        for (long i = 1 ; i * i <= n ; i++) {
            if (n % i == 0) {
                d[di++] = i;
                if (i * i != n) {
                    d[di++] = n / i;
                }
            }
        }
        d = Arrays.copyOf(d, di);
        Arrays.sort(d);
        return d;
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
