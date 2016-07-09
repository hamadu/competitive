package topcoder.srm6xx.srm645.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;

public class JanuszTheBusinessman {
    private static final int INF = 1000;

    public int makeGuestsReturn(int[] arr, int[] dep) {
        int n = arr.length;
        int[][] g = new int[n][2];
        for (int i = 0; i < n ; i++) {
            g[i][0] = arr[i];
            g[i][1] = dep[i];
        }
        Arrays.sort(g, (g0, g1) -> g0[0] - g1[0]);

        int[] dp = new int[367];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        int ans = INF;
        for (int i = 0; i <= 365; i++) {
            if (dp[i] == INF) {
                continue;
            }
            for (int j = 0; j < n ; j++) {
                boolean isOK = true;
                boolean finish = true;
                for (int k = 0; k < n ; k++) {
                    if (dep[j] < arr[k]) {
                        finish = false;
                    }
                }
                for (int k = 0; k < n ; k++) {
                    if (i <= arr[k] && dep[k] < arr[j]) {
                        isOK = false;
                        break;
                    }
                }
                if (isOK) {
                    dp[dep[j]+1] = Math.min(dp[dep[j]+1], dp[i]+1);
                    if (finish) {
                        ans = Math.min(ans, dp[i]+1);
                    }
                }
            }
        }
        return ans;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // CUT begin
        public static void main(String[] args){
        System.err.println("JanuszTheBusinessman (250 Points)");
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

            int[] arrivals = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < arrivals.length; ++i)
                arrivals[i] = Integer.parseInt(Reader.nextLine());
            int[] departures = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < departures.length; ++i)
                departures[i] = Integer.parseInt(Reader.nextLine());
            Reader.nextLine();
            int __answer = Integer.parseInt(Reader.nextLine());

            cases++;
            if (caseSet.size() > 0 && !caseSet.contains(cases - 1))
                continue;
            System.err.print(String.format("  Testcase #%d ... ", cases - 1));

            if (doTest(arrivals, departures, __answer))
                passed++;
        }
        if (caseSet.size() > 0) cases = caseSet.size();
        System.err.println(String.format("%nPassed : %d/%d cases", passed, cases));

        int T = (int)(System.currentTimeMillis() / 1000) - 1468029380;
        double PT = T / 60.0, TT = 75.0;
        System.err.println(String.format("Time   : %d minutes %d secs%nScore  : %.2f points", T / 60, T % 60, 250 * (0.3 + (0.7 * TT * TT) / (10.0 * PT * PT + TT * TT))));
    }

    static boolean doTest(int[] arrivals, int[] departures, int __expected) {
        long startTime = System.currentTimeMillis();
        Throwable exception = null;
        JanuszTheBusinessman instance = new JanuszTheBusinessman();
        int __result = 0;
        try {
            __result = instance.makeGuestsReturn(arrivals, departures);
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
        private static final String dataFileName = "topcoder/srm6xx/srm645/div1/JanuszTheBusinessman.sample";
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
