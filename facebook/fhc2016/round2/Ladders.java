package facebook.fhc2016.round2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/01/10.
 */
public class Ladders {
    private static final long MOD = 1000000007;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int T = in.nextInt();
        for (int c = 1 ; c <= T ; c++) {
            int n = in.nextInt();
            int[][] lad = new int[n][2];
            for (int i = 0; i < n ; i++) {
                lad[i][0] = in.nextInt();
                lad[i][1] = in.nextInt();
            }
            Arrays.sort(lad, (l1, l2) -> l1[0] - l2[0]);

            out.println(String.format("Case #%d: %d", c, solve(lad)));
        }
        out.flush();
    }


    private static long solve(int[][] lad) {
        int n = lad.length;
        int[] height = new int[n];
        for (int i = 0; i < n ; i++) {
            height[i] = lad[i][1];
        }
        if (n == 1) {
            return 0;
        }
        SegmentTree seg = new SegmentTree(height);


        Map<Integer,List<Integer>> heightMap = new HashMap<>();
        for (int i = 0 ; i < n ; i++) {
            if (!heightMap.containsKey(lad[i][1])) {
                heightMap.put(lad[i][1], new ArrayList<>());
            }
            heightMap.get(lad[i][1]).add(i);
        }

        long sum = 0;
        for (int h : heightMap.keySet()) {
            List<Integer> pos = heightMap.get(h);
            int pn = pos.size();
            if (pn >= 2) {
                int left = 0;
                while (left < pn) {
                    int lpos = pos.get(left);
                    int right = left;
                    while (right < pn) {
                        int rpos = pos.get(right);
                        if (h < seg.max(lpos+1, rpos)) {
                            break;
                        }
                        right++;
                    }
                    int rl = right - left;
                    long[] parts = new long[rl-1];
                    for (int i = 0; i < rl-1 ; i++) {
                        parts[i] = lad[pos.get(left+i+1)][0] - lad[pos.get(left+i)][0];
                    }
                    sum += computeThat(parts);
                    sum %= MOD;
                    left = right;
                }
            }
        }
        return sum;
    }

    private static long computeThat(long[] parts) {
        long sum = 0;
        int n = parts.length;
        long subPart = 0;
        long addition = 0;
        for (int i = 0 ; i < n ; i++) {
            addition += ((parts[i] * parts[i]) % MOD) * (i+1) % MOD;
            addition += (parts[i] * subPart) % MOD;
            addition %= MOD;
            subPart += (((i+1) * 2) % MOD * parts[i]) % MOD;
            subPart %= MOD;
            sum += addition;
            sum %= MOD;
        }
        return sum;
    }


    public static class SegmentTree {
        int N;
        int M;
        int[] seg;

        public SegmentTree(int[] data) {
            N = Integer.highestOneBit(data.length-1)<<2;
            M = (N >> 1) - 1;

            seg = new int[N];
            Arrays.fill(seg, Integer.MIN_VALUE);
            for (int i = 0 ; i < data.length ; i++) {
                seg[M+i] = data[i];
            }
            for (int i = M-1 ; i >= 0 ; i--) {
                seg[i] = compute(i);
            }
        }

        public void update(int idx, int value) {
            seg[M+idx] = value;
            int i = M+idx;
            while (true) {
                i = (i-1) >> 1;
                seg[i] = compute(i);
                if (i == 0) {
                    break;
                }
            }
        }

        public int compute(int i) {
            return Math.max(seg[i*2+1], seg[i*2+2]);
        }

        public int max(int l, int r) {
            return max(l, r, 0, 0, M+1);
        }

        public int max(int l, int r, int idx, int fr, int to) {
            if (to <= l || r <= fr) {
                return Integer.MIN_VALUE;
            }
            if (l <= fr && to <= r) {
                return seg[idx];
            }

            int med = (fr+to) / 2;
            int ret = Integer.MIN_VALUE;
            ret = Math.max(ret, max(l, r, idx*2+1, fr, med));
            ret = Math.max(ret, max(l, r, idx*2+2, med, to));
            return ret;
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
