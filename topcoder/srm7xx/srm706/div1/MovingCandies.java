package topcoder.srm7xx.srm706.div1;

import java.io.*;
import java.util.*;

public class MovingCandies {
    private static final int INF = 10000000;

    public int minMoved(String[] t) {
        int n = t.length;
        int m = t[0].length();

        int[][] map = new int[n][m];
        int total = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                map[i][j] = t[i].charAt(j) == '#' ? 1 : 0;
                total += map[i][j];
            }
        }

        int[][][] dp = new int[n][m][n*m+1];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        Queue<State> q = new PriorityQueue<>();
        q.add(new State(0, 0, map[0][0], 1-map[0][0]));
        while (q.size() >= 1) {
            State st = q.poll();
            for (int d = 0 ; d < 4; d++) {
                int ty = st.y + dy[d];
                int tx = st.x + dx[d];
                if (ty < 0 || tx < 0 || ty >= n || tx >= m) {
                    continue;
                }
                int tp = st.pick + map[ty][tx];
                if (tp > n*m) {
                    continue;
                }
                int tt = st.time + (1 - map[ty][tx]);
                if (dp[ty][tx][tp] > tt) {
                    dp[ty][tx][tp] = tt;
                    q.add(new State(ty, tx, tp, tt));
                }
            }
        }

        int min = INF;
        for (int i = 0; i <= total; i++) {
            int move = dp[n-1][m-1][i];
            int left = total - i;
            if (left >= move) {
                min = Math.min(min, move);
            }
        }
        return min == INF ? -1 : min;
    }

    private int[] dx = new int[]{0, -1, 0, 1};
    private int[] dy = new int[]{-1, 0, 1, 0};


    static class State implements Comparable<State> {
        int y;
        int x;
        int pick;
        int time;

        State(int a, int b, int c, int d) {
            y = a;
            x = b;
            pick = c;
            time = d;
        }

        @Override
        public int compareTo(State o) {
            return time - o.time;
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // CUT begin
        public static void main(String[] args){
        System.err.println("MovingCandies (250 Points)");
        System.err.println();
        HashSet<Integer> cases = new HashSet<Integer>();
        for (int i = 0; i < args.length; ++i) cases.add(Integer.parseInt(args[i]));
        runTest(cases);
    }

    static void runTest(HashSet<Integer> caseSet) {
        int cases = 0, passed = 0;
        while (true) {
            String label = Reader.nextLine();
            if (label == null || !label.startsWith("--"))
                break;

            String[] t = new String[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < t.length; ++i)
                t[i] = Reader.nextLine();
            Reader.nextLine();
            int __answer = Integer.parseInt(Reader.nextLine());

            cases++;
            if (caseSet.size() > 0 && !caseSet.contains(cases - 1))
                continue;
            System.err.print(String.format("  Testcase #%d ... ", cases - 1));

            if (doTest(t, __answer))
                passed++;
        }
        if (caseSet.size() > 0) cases = caseSet.size();
        System.err.println(String.format("%nPassed : %d/%d cases", passed, cases));

        int T = (int)(System.currentTimeMillis() / 1000) - 1485014408;
        double PT = T / 60.0, TT = 75.0;
        System.err.println(String.format("Time   : %d minutes %d secs%nScore  : %.2f points", T / 60, T % 60, 250 * (0.3 + (0.7 * TT * TT) / (10.0 * PT * PT + TT * TT))));
    }

    static boolean doTest(String[] t, int __expected) {
        for (int i = 0; i < t.length; i++) {
            t[i] = new String(t[i]);
        }
        long startTime = System.currentTimeMillis();
        Throwable exception = null;
        MovingCandies instance = new MovingCandies();
        int __result = 0;
        try {
            __result = instance.minMoved(t);
        }
        catch (Throwable e) { exception = e; }
        double elapsed = (System.currentTimeMillis() - startTime) / 1000.0;

        if (exception != null) {
            System.err.println("RUNTIME ERROR!");
            exception.printStackTrace();
            return false;
        }
        else if (__result == __expected) {
            System.err.println("PASSED! " + String.format("(%.2f seconds)", elapsed));
            return true;
        }
        else {
            System.err.println("FAILED! " + String.format("(%.2f seconds)", elapsed));
            System.err.println("           Expected: " + __expected);
            System.err.println("           Received: " + __result);
            return false;
        }
    }

    static class Reader {
        private static final String dataFileName = "topcoder/srm7xx/srm706/div1/MovingCandies.sample";
        private static BufferedReader reader;

        public static String nextLine() {
            try {
                if (reader == null) {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFileName)));
                }
                return reader.readLine();
            }
            catch (IOException e) {
                System.err.println("FATAL!! IOException");
                e.printStackTrace();
                System.exit(1);
            }
            return "";
        }
    }
    // CUT end
}
