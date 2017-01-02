package topcoder.srm6xx.srm696.div1;

import java.io.*;
import java.util.*;

public class Gperm {
    private static final int INF = 1000000000;

    public int countfee(int[] x, int[] y) {
        int n = x.length;

        // removed
        int[] dp = new int[1<<n];
        int[] ptnV = new int[50];
        for (int i = 0; i < n ; i++) {
            ptnV[x[i]] |= 1<<i;
            ptnV[y[i]] |= 1<<i;
        }

        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int i = 0; i < (1<<n) ; i++) {
            for (int k = 0 ; k < n ; k++) {
                if ((i & (1<<k)) == 0) {
                    int ti1 = i | ptnV[x[k]];
                    int ti2 = i | ptnV[y[k]];

                    int tocost = dp[i] + (n - Integer.bitCount(i));
                    dp[ti1] = Math.min(dp[ti1], tocost);
                    dp[ti2] = Math.min(dp[ti2], tocost);
                }
            }
        }
        return dp[(1<<n)-1];
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // CUT begin
        public static void main(String[] args){
        System.err.println("Gperm (300 Points)");
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

            int[] x = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < x.length; ++i)
                x[i] = Integer.parseInt(Reader.nextLine());
            int[] y = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < y.length; ++i)
                y[i] = Integer.parseInt(Reader.nextLine());
            Reader.nextLine();
            int __answer = Integer.parseInt(Reader.nextLine());

            cases++;
            if (caseSet.size() > 0 && !caseSet.contains(cases - 1))
                continue;
            System.err.print(String.format("  Testcase #%d ... ", cases - 1));

            if (doTest(x, y, __answer))
                passed++;
        }
        if (caseSet.size() > 0) cases = caseSet.size();
        System.err.println(String.format("%nPassed : %d/%d cases", passed, cases));

        int T = (int)(System.currentTimeMillis() / 1000) - 1483272223;
        double PT = T / 60.0, TT = 75.0;
        System.err.println(String.format("Time   : %d minutes %d secs%nScore  : %.2f points", T / 60, T % 60, 300 * (0.3 + (0.7 * TT * TT) / (10.0 * PT * PT + TT * TT))));
    }

    static boolean doTest(int[] x, int[] y, int __expected) {
        long startTime = System.currentTimeMillis();
        Throwable exception = null;
        Gperm instance = new Gperm();
        int __result = 0;
        try {
            __result = instance.countfee(x, y);
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
        private static final String dataFileName = "topcoder/srm6xx/srm696/div1/Gperm.sample";
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
