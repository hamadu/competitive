package atcoder.arc.arc044;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by hama_du on 15/09/19.
 */
public class C {
    private static final int INF = 100000000;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int w = in.nextInt();
        int h = in.nextInt();
        int q = in.nextInt();
        int[][] beams = new int[q][3];
        int wcnt = 0;
        int hcnt = 0;
        for (int i = 0; i < q; i++) {
            beams[i][0] = in.nextInt();
            beams[i][1] = in.nextInt();
            beams[i][2] = in.nextInt()-1;
            if (beams[i][1] == 0) {
                wcnt++;
            } else {
                hcnt++;
            }
        }
        int[][] wbeam = new int[wcnt][2];
        int[][] hbeam = new int[hcnt][2];
        for (int i = 0; i < q ; i++) {
            if (beams[i][1] == 0) {
                --wcnt;
                wbeam[wcnt][0] = beams[i][0];
                wbeam[wcnt][1] = beams[i][2];
            } else {
                --hcnt;
                hbeam[hcnt][0] = beams[i][0];
                hbeam[hcnt][1] = beams[i][2];
            }
        }

        int cnt = solve(wbeam, w) + solve(hbeam, h);
        out.println(cnt >= INF ? -1 : cnt);
        out.flush();
    }

    private static int solve(int[][] beams, int w) {
        Arrays.sort(beams, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                }
                return o1[0] - o2[0];
            }
        });

        int n = beams.length;
        int[] beamDegree = new int[w];
        for (int i = 0; i < n ; i++) {
            beamDegree[beams[i][1]]++;
        }
        int[][] beamArray = new int[w][];
        for (int i = 0; i < w ; i++) {
            beamArray[i] = new int[beamDegree[i]];
        }
        Arrays.fill(beamDegree, 0);
        for (int i = 0; i < n ; i++) {
            int time = beams[i][0];
            int place = beams[i][1];
            beamArray[place][beamDegree[place]++] = time;
        }
        for (int i = 0; i < w ; i++) {
            if (beamArray[i].length == 0) {
                return 0;
            }
        }

        int[][] dp = new int[w][];
        for (int i = 0; i < w ; i++) {
            dp[i] = new int[beamArray[i].length+1];
            Arrays.fill(dp[i], INF);
            dp[i][0] = 0;
        }

        Queue<State> q = new PriorityQueue<State>();
        for (int i = 0; i < w ; i++) {
            int ne = next(beamArray[i], 0);
            q.add(new State(i, beamArray[i][ne], 0));
        }
        while (q.size() >= 1) {
            State st = q.poll();
            for (int d = -1 ; d <= 1; d += 2) {
                int tpos = st.pos+d;
                if (tpos < 0 || tpos >= w) {
                    continue;
                }
                int tt = next(beamArray[tpos], st.time);
                if (dp[tpos][tt] > st.move+1) {
                    dp[tpos][tt] = st.move+1;
                    if (tt < beamArray[tpos].length) {
                        q.add(new State(tpos, beamArray[tpos][tt], st.move+1));
                    }
                }
            }
        }

        int best = INF;
        for (int f = 0; f < w ; f++) {
            best = Math.min(best, dp[f][beamArray[f].length]);
        }
        return best;
    }

    static int next(int[] time, int current) {
        int idx = Arrays.binarySearch(time, current);
        if (idx >= 0) {
            return idx;
        } else {
            return -idx-1;
        }
    }

    static class State implements Comparable<State> {
        int pos;
        int time;
        int move;

        State(int a, int b, int c) {
            pos = a;
            time = b;
            move = c;
        }

        @Override
        public int compareTo(State o) {
            return move - o.move;
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
