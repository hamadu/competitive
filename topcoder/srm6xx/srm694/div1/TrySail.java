package topcoder.srm6xx.srm694.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;

public class TrySail {
    public int get(int[] strength) {
        int n = strength.length;

        int[][][][] dp = new int[n+1][256][256][8];
        dp[0][0][0][0] = 1;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < 256 ; j++) {
                for (int k = 0; k < 256 ; k++) {
                    for (int l = 0; l < 8 ; l++) {
                        if (dp[i][j][k][l] == 0) {
                            continue;
                        }
                        // first
                        dp[i+1][j^strength[i]][k][l|1] = 1;

                        // second
                        dp[i+1][j][k^strength[i]][l|2] = 1;

                        // third
                        dp[i+1][j][k][l|4] = 1;
                    }
                }
            }
        }

        int all = 0;
        for (int i = 0; i < strength.length; i++) {
            all ^= strength[i];
        }
        int max = 0;
        for (int i = 0; i < 256 ; i++) {
            for (int j = 0; j < 256 ; j++) {
                if (dp[n][i][j][7] == 1) {
                    max = Math.max(max, i + j + (all ^ i ^ j));
                }
            }
        }
        return max;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // CUT begin
        public static void main(String[] args){
        System.err.println("TrySail (250 Points)");
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

            int[] strength = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < strength.length; ++i)
                strength[i] = Integer.parseInt(Reader.nextLine());
            Reader.nextLine();
            int __answer = Integer.parseInt(Reader.nextLine());

            cases++;
            if (caseSet.size() > 0 && !caseSet.contains(cases - 1))
                continue;
            System.err.print(String.format("  Testcase #%d ... ", cases - 1));

            if (doTest(strength, __answer))
                passed++;
        }
        if (caseSet.size() > 0) cases = caseSet.size();
        System.err.println(String.format("%nPassed : %d/%d cases", passed, cases));

        int T = (int)(System.currentTimeMillis() / 1000) - 1468080039;
        double PT = T / 60.0, TT = 75.0;
        System.err.println(String.format("Time   : %d minutes %d secs%nScore  : %.2f points", T / 60, T % 60, 250 * (0.3 + (0.7 * TT * TT) / (10.0 * PT * PT + TT * TT))));
    }

    static boolean doTest(int[] strength, int __expected) {
        long startTime = System.currentTimeMillis();
        Throwable exception = null;
        TrySail instance = new TrySail();
        int __result = 0;
        try {
            __result = instance.get(strength);
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
        private static final String dataFileName = "topcoder/srm6xx/srm694/div1/TrySail.sample";
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
