package topcoder.srm6xx.srm699.div1;

import java.io.*;
import java.util.*;

public class OthersXor {
    private static final long INF = 114514;

    public long minSum(int[] x) {
        int free = 0;
        for (int i = 0; i < x.length ; i++) {
            if (x[i] == -1) {
                free++;
            }
        }

        long must = 0;
        if (x.length % 2 == 0) {
            if (free >= 1) {
                must = -1;
            } else {
                for (int i = 0; i < x.length; i++) {
                    must ^= x[i];
                }
            }
        }

        long ans = 0;
        for (int idx = 29 ; idx >= 0 ; idx--) {
            int one = 0;
            for (int i = 0 ; i < x.length ; i++) {
                if (x[i] != -1) {
                    if ((x[i]&(1<<idx)) >= 1) {
                        one++;
                    }
                }
            }
            long req = INF;
            for (int ol = 0 ; ol <= free ; ol++) {
                int tone = one + ol;
                int tzero = x.length - tone;
                if (must != -1 && (must & (1<<idx)) >= 1 != (tone % 2 == 1)) {
                    continue;
                }
                if (x.length % 2 == 0) {
                    if (tone%2 == 0) {
                        req = Math.min(req, tone);
                    } else {
                        req = Math.min(req, tzero);
                    }
                } else {
                    req = Math.min(req, Math.min(tone, tzero));
                }
            }
            if (req == INF) {
                return -1;
            }
            ans += (1L<<idx)*req;
        }

        return ans;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // CUT begin
        public static void main(String[] args){
        System.err.println("OthersXor (250 Points)");
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
            Reader.nextLine();
            long __answer = Long.parseLong(Reader.nextLine());

            cases++;
            if (caseSet.size() > 0 && !caseSet.contains(cases - 1))
                continue;
            System.err.print(String.format("  Testcase #%d ... ", cases - 1));

            if (doTest(x, __answer))
                passed++;
        }
        if (caseSet.size() > 0) cases = caseSet.size();
        System.err.println(String.format("%nPassed : %d/%d cases", passed, cases));

        int T = (int)(System.currentTimeMillis() / 1000) - 1477222577;
        double PT = T / 60.0, TT = 75.0;
        System.err.println(String.format("Time   : %d minutes %d secs%nScore  : %.2f points", T / 60, T % 60, 250 * (0.3 + (0.7 * TT * TT) / (10.0 * PT * PT + TT * TT))));
    }

    static boolean doTest(int[] x, long __expected) {
        long startTime = System.currentTimeMillis();
        Throwable exception = null;
        OthersXor instance = new OthersXor();
        long __result = 0;
        try {
            __result = instance.minSum(x);
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
        private static final String dataFileName = "topcoder/srm6xx/srm699/div1/OthersXor.sample";
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
