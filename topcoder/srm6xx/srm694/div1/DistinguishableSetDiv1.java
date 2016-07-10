package topcoder.srm6xx.srm694.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;

public class DistinguishableSetDiv1 {
    public int count(String[] answer) {
        n = answer.length;
        int m = answer[0].length();
        map = new char[n][m];
        for (int i = 0; i < n ; i++) {
            map[i] = answer[i].toCharArray();
        }

        boolean[] ng = new boolean[1<<m];
        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                int ngptn = 0;
                for (int k = 0; k < m ; k++) {
                    if (map[i][k] == map[j][k]) {
                        ngptn |= 1<<k;
                    }
                }
                ng[ngptn] = true;
            }
        }


        int ngcnt = 0;
        for (int i = (1<<m)-1 ; i >= 0 ; i--) {
            if (ng[i]) {
                ngcnt++;
                for (int j = 0; j < m ; j++) {
                    if ((i & (1<<j)) >= 1) {
                        ng[i^(1<<j)] = true;
                    }
                }
            }
        }
        return (1<<m)-ngcnt;
    }


    int n;
    char[][] map;

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // CUT begin
        public static void main(String[] args){
        System.err.println("DistinguishableSetDiv1 (500 Points)");
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

            String[] answer = new String[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < answer.length; ++i)
                answer[i] = Reader.nextLine();
            Reader.nextLine();
            int __answer = Integer.parseInt(Reader.nextLine());

            cases++;
            if (caseSet.size() > 0 && !caseSet.contains(cases - 1))
                continue;
            System.err.print(String.format("  Testcase #%d ... ", cases - 1));

            if (doTest(answer, __answer))
                passed++;
        }
        if (caseSet.size() > 0) cases = caseSet.size();
        System.err.println(String.format("%nPassed : %d/%d cases", passed, cases));

        int T = (int)(System.currentTimeMillis() / 1000) - 1468080416;
        double PT = T / 60.0, TT = 75.0;
        System.err.println(String.format("Time   : %d minutes %d secs%nScore  : %.2f points", T / 60, T % 60, 500 * (0.3 + (0.7 * TT * TT) / (10.0 * PT * PT + TT * TT))));
    }

    static boolean doTest(String[] answer, int __expected) {
        for (int i = 0; i < answer.length; i++) {
            answer[i] = new String(answer[i]);
        }
        long startTime = System.currentTimeMillis();
        Throwable exception = null;
        DistinguishableSetDiv1 instance = new DistinguishableSetDiv1();
        int __result = 0;
        try {
            __result = instance.count(answer);
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
        private static final String dataFileName = "topcoder/srm6xx/srm694/div1/DistinguishableSetDiv1.sample";
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
