package codeforces.cf3xx.cf395.div1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class D {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        graph = buildGraph(in, n, n-1);
        cnt = new int[n];
        map = new HashMap<>();
        value0 = new long[n][2];
        value = new long[n][2];

        mods = new long[2];
        muls = new long[2][214514];
        invmuls = new long[2][214514];

        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < 2 ; i++) {
            while (mods[i] <= muls[i][1] || muls[i][1] <= 512) {
                mods[i] = BigInteger.probablePrime(30, rand).longValue();
                muls[i][1] = BigInteger.probablePrime(12, rand).longValue();
            }
            muls[i][0] = 1;
            for (int j = 2 ; j < muls[i].length; j++) {
                muls[i][j] = muls[i][j-1] * muls[i][1];
                muls[i][j] %= mods[i];
            }
            invmuls[i][invmuls[i].length-1] = inv(muls[i][muls[i].length-1], mods[i]);
            for (int j = muls[i].length-2 ; j >= 0 ; j--) {
                invmuls[i][j] = (invmuls[i][j+1] * muls[i][1]) % mods[i];
            }
            assert invmuls[i][0] == 1;
        }
        for (int i = 0; i < muls[0].length ; i++) {
            assert muls[0][i] * invmuls[0][i] % mods[0] == 1;
        }


        ord = new int[n][];
        dfs(0, -1);

        // debug(value);
        // debug(ord);
        // debug(cnt);

        map = new HashMap<>();
        for (int i = 1 ; i < n ; i++) {
            addToMap(hash(value[i]), 1);
        }

        dfs2(0, -1);

        out.println(bestV+1);
        out.flush();
    }

    private static long embrace(long value, int k, int length) {
        value *= muls[k][1];
        value += ')';
        value %= mods[k];
        value += muls[k][length+1] * '(' % mods[k];
        value %= mods[k];
        return value;
    }

    private static long hash(long[] lr) {
        return hash(lr[0], lr[1]);
    }

    private static long hash(long l, long r) {
        return (l<<32L) | r;
    }

    static long pow(long a, long x, long MOD) {
        long res = 1;
        while (x > 0) {
            if (x%2 != 0) {
                res = (res*a)%MOD;
            }
            a = (a*a)%MOD;
            x /= 2;
        }
        return res;
    }

    static long inv(long a, long MOD) {
        return pow(a, MOD-2, MOD)%MOD;
    }

    static void buildHash(int now, List<long[]> children) {
        Collections.sort(children, (a, b) -> {
            int k = a[0] == b[0] ? 1 : 0;
            return Long.compare(a[k], b[k]);
        });
        int cn = children.size();

        ord[now] = new int[cn];


        int len = 0;
        for (int i = 0 ; i < children.size(); i++) {
            ord[now][i] = (int)children.get(i)[3];
            len += cnt[ord[now][i]];
        }

        long[] ans = new long[2];
        for (int k = 0 ; k <= 1 ; k++) {
            for (long[] child : children) {
                ans[k] *= muls[k][(int)child[2]*2];
                ans[k] %= mods[k];
                ans[k] += child[k];
                ans[k] %= mods[k];
            }

            value0[now][k] = ans[k];
            value[now][k] = embrace(value0[now][k], k, len*2);
        }
        cnt[now] = len + 1;
    }


    private static void addToMap(long hashValue, int inc) {
        int tv = map.getOrDefault(hashValue, 0) + inc;
        assert tv >= 0;

        if (tv == 0) {
            map.remove(hashValue);
        } else {
            map.put(hashValue, tv);
        }
    }

    private static void dfs2(int now, int par) {
        int size = map.size() + 1;
        if (best < size) {
            best = size;
            bestV = now;
        }
        // debug(now, size);
        // debug(now, map);
        // debug(now, ord[now], cnt[now]);

        int leftU = 0;
        int childLen = cnt[now] * 2 - 2;

        long[] leftHash = new long[2];
        long[] rightHash = value0[now].clone();
        long[] ha = new long[2];
        for (int to : ord[now]) {
            int po = childLen-leftU-cnt[to]*2;
            if (par != to) {
                addToMap(hash(value[to]), -1);

                for (int k = 0; k <= 1; k++) {
                    long newHash = rightHash[k];
                    newHash += mods[k];
                    newHash -= muls[k][po]*value[to][k]%mods[k];
                    newHash %= mods[k];

                    newHash += leftHash[k]*invmuls[k][cnt[to]*2]%mods[k];
                    newHash %= mods[k];

                    ha[k] = embrace(newHash, k, (cnt[now]-cnt[to]-1)*2);
                }

                addToMap(hash(ha), 1);

                int tmpcnt = cnt[to];
                cnt[now] -= cnt[to];
                long[] tmp = value[now].clone();
                long[] tmpto = value[to].clone();
                long[] tmpto0 = value0[to].clone();
                value[now][0] = ha[0];
                value[now][1] = ha[1];

                List<long[]> chash = new ArrayList<>();
                for (int tt : ord[to]) {
                    chash.add(new long[]{value[tt][0], value[tt][1], cnt[tt], tt});
                }
                chash.add(new long[]{value[now][0], value[now][1], cnt[now], now});


                buildHash(to, chash);

                dfs2(to, now);

                cnt[to] = tmpcnt;
                cnt[now] += cnt[to];
                value[now][0] = tmp[0];
                value[now][1] = tmp[1];
                value[to][0] = tmpto[0];
                value[to][1] = tmpto[1];
                value0[to][0] = tmpto0[0];
                value0[to][1] = tmpto0[1];

                addToMap(hash(ha), -1);

                addToMap(hash(value[to]), 1);
            }

            for (int k = 0 ; k <= 1 ; k++) {
                leftHash[k] += muls[k][po] * value[to][k] % mods[k];
                leftHash[k] %= mods[k];

                rightHash[k] += mods[k];
                rightHash[k] -= muls[k][po] * value[to][k] % mods[k];
                rightHash[k] %= mods[k];
            }
            leftU += cnt[to] * 2;
        }
        // debug(now, map);
        // debug(now, leftHash, rightHash, value0[now]);
    }

    static int best = -1;
    static int bestV = 0;

    static long[] mods;
    static long[][] muls;
    static long[][] invmuls;

    static int[][] graph;

    static int[] cnt;

    static long[][] value0;
    static long[][] value;

    static Map<Long,Integer> map;

    static int[][] ord;

    static void dfs(int now, int par) {
        int cn = (par == -1) ? graph[now].length : graph[now].length-1;

        List<long[]> children = new ArrayList<>();
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            dfs(to, now);
            children.add(new long[]{value[to][0], value[to][1], cnt[to], to});
        }
        buildHash(now, children);
    }

    static int[][] buildGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
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
