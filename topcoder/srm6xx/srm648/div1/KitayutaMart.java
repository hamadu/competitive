package topcoder.srm6xx.srm648.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;

public class KitayutaMart {
    public int lastPrice(int N, int K) {
        return (int)solve(N, K);
    }

    private long solve(long n, long k) {
        long base = countThat(k);
        long ho = (base < n) ? (n - base) / k : 0;
        long need = n - ho * k;

        long min = 0;
        long max = k+1;
        while (max - min > 1) {
            long med = (max + min) / 2;
            long cnt = countThat(med);
            if (cnt >= need) {
                max = med;
            } else {
                min = med;
            }
        }
        return pow(2, ho) * max % MOD;
    }

    static final int MOD = 1000000007;

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x % 2 != 0) {
            res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            x /= 2;
        }
        return res;
    }

    private long countThat(long k) {
        long ret = 0;
        while (k >= 1) {
            ret += k;
            k /= 2;
        }
        return ret;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // CUT begin
        public static void main(String[] args){
        System.err.println("KitayutaMart (550 Points)");
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

            int N = Integer.parseInt(Reader.nextLine());
            int K = Integer.parseInt(Reader.nextLine());
            Reader.nextLine();
            int __answer = Integer.parseInt(Reader.nextLine());

            cases++;
            if (caseSet.size() > 0 && !caseSet.contains(cases - 1))
                continue;
            System.err.print(String.format("  Testcase #%d ... ", cases - 1));

            if (doTest(N, K, __answer))
                passed++;
        }
        if (caseSet.size() > 0) cases = caseSet.size();
        System.err.println(String.format("%nPassed : %d/%d cases", passed, cases));

        int T = (int)(System.currentTimeMillis() / 1000) - 1466779095;
        double PT = T / 60.0, TT = 75.0;
        System.err.println(String.format("Time   : %d minutes %d secs%nScore  : %.2f points", T / 60, T % 60, 550 * (0.3 + (0.7 * TT * TT) / (10.0 * PT * PT + TT * TT))));
    }

    static boolean doTest(int N, int K, int __expected) {
        long startTime = System.currentTimeMillis();
        Throwable exception = null;
        KitayutaMart instance = new KitayutaMart();
        int __result = 0;
        try {
            __result = instance.lastPrice(N, K);
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
        private static final String dataFileName = "topcoder/srm6xx/srm648/div1/KitayutaMart.sample";
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
