package atcoder.other2016.dwango2016.qual;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 2016/01/23.
 */
public class E {
    static final long INF = (long)1e18;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int l = in.nextInt();

        Set<Integer> timeset = new HashSet<>();
        int[][] fire = new int[n][2];
        for (int i = 0; i < n ; i++) {
            fire[i][0] = in.nextInt();
            fire[i][1] = in.nextInt();
            timeset.add(fire[i][0]);
        }
        List<Integer> times = new ArrayList<>();
        for (int t : timeset) {
            times.add(t);
        }
        Collections.sort(times);
        Map<Integer,Integer> timeMap = new HashMap<>();
        for (int i = 0 ; i < times.size() ; i++) {
            timeMap.put(times.get(i), i);
        }
        int tn = timeMap.size();

        List<Integer>[] timeFires = new List[tn];
        for (int i = 0 ; i < tn ; i++) {
            timeFires[i] = new ArrayList<>();
        }
        for (int i = 0 ; i < n ; i++) {
            int t = timeMap.get(fire[i][0]);
            timeFires[t].add(fire[i][1]);
        }
        long[][] timeFireImos = new long[tn][];
        for (int i = 0 ; i < tn ; i++) {
            int firenum = timeFires[i].size();
            timeFireImos[i] = new long[firenum+1];
            for (int f = 0 ; f < firenum ; f++) {
                timeFireImos[i][f+1] = timeFireImos[i][f] + timeFires[i].get(f);
            }
        }

        long[][] dp = new long[tn+1][l+1];
        for (int i = 0; i <= tn; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = 0;

        long[] prevTable = new long[l+2];
        for (int i = 1 ; i <= tn ; i++) {
            Arrays.fill(prevTable, INF);
            prevTable[0] = dp[i-1][0];
            for (int j = 1 ; j <= l ; j++) {
                prevTable[j] = Math.min(prevTable[j-1], dp[i-1][j]);
            }
            for (int j = 0; j <= l ; j++) {
                dp[i][j] = prevTable[j] + computeThat(j, timeFires[i-1], timeFireImos[i-1]); // fire?
            }
        }

        long best = INF;
        for (int i = 0; i <= l ; i++) {
            best = Math.min(best, dp[tn][i]);
        }
        out.println(best);
        out.flush();
    }

    private static long computeThat(long pos, List<Integer> timeFire, long[] timeFireImos) {
        int idx = Collections.binarySearch(timeFire, (int)pos);
        if (idx < 0) {
            idx = -idx-1;
        }
        long left = timeFireImos[idx];
        long right = timeFireImos[timeFireImos.length-1] - left;
        long ln = idx;
        long rn = timeFire.size()-ln;
        return (pos * ln - left) + (right - pos * rn);
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
