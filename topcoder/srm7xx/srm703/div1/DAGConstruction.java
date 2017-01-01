package topcoder.srm7xx.srm703.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;

public class DAGConstruction {
    public int[] construct(int[] x) {
        int n = x.length;
        List<int[]> edges = new ArrayList<>();

        int[][] vinfo = new int[n][2];
        for (int i = 0; i < n ; i++) {
            vinfo[i][0] = x[i];
            vinfo[i][1] = i;
        }
        Arrays.sort(vinfo, (a, b) -> a[0] - b[0]);

        for (int i = 0 ; i < n ; i++) {
            int my = vinfo[i][1];
            int needConn = vinfo[i][0]-1;
            if (needConn > i) {
                return new int[]{-1};
            }
            for (int f = 0 ; f < needConn ; f++) {
                edges.add(new int[]{ my, vinfo[f][1] });
            }
        }

        int[] ret = new int[edges.size()*2];
        for (int i = 0 ; i < edges.size() ; i++) {
            ret[i*2] = edges.get(i)[0];
            ret[i*2+1] = edges.get(i)[1];
        }
        return ret;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // CUT begin
        public static void main(String[] args){
        System.err.println("DAGConstruction (250 Points)");
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
            int[] __answer = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < __answer.length; ++i)
                __answer[i] = Integer.parseInt(Reader.nextLine());

            cases++;
            if (caseSet.size() > 0 && !caseSet.contains(cases - 1))
                continue;
            System.err.print(String.format("  Testcase #%d ... ", cases - 1));

            if (doTest(x, __answer))
                passed++;
        }
        if (caseSet.size() > 0) cases = caseSet.size();
        System.err.println(String.format("%nPassed : %d/%d cases", passed, cases));

        int T = (int)(System.currentTimeMillis() / 1000) - 1483177217;
        double PT = T / 60.0, TT = 75.0;
        System.err.println(String.format("Time   : %d minutes %d secs%nScore  : %.2f points", T / 60, T % 60, 250 * (0.3 + (0.7 * TT * TT) / (10.0 * PT * PT + TT * TT))));
    }

    static boolean doTest(int[] x, int[] __expected) {
        long startTime = System.currentTimeMillis();
        Throwable exception = null;
        DAGConstruction instance = new DAGConstruction();
        int[] __result = new int[0];
        try {
            __result = instance.construct(x);
        }
        catch (Throwable e) { exception = e; }
        double elapsed = (System.currentTimeMillis() - startTime) / 1000.0;

        if (exception != null) {
            System.err.println("RUNTIME ERROR!");
            exception.printStackTrace();
            return false;
        }
        else if (equals(__result, __expected)) {
            System.err.println("PASSED! " + String.format("(%.2f seconds)", elapsed));
            return true;
        }
        else {
            System.err.println("FAILED! " + String.format("(%.2f seconds)", elapsed));
            System.err.println("           Expected: " + toString(__expected));
            System.err.println("           Received: " + toString(__result));
            return false;
        }
    }

    static boolean equals(int[] a, int[] b) {
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; ++i) if (a[i] != b[i]) return false;
        return true;
    }

    static String toString(int[] arr) {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        for (int i = 0; i < arr.length; ++i) {
            if (i > 0) sb.append(", ");
            sb.append(arr[i]);
        }
        return sb.toString() + " ]";
    }

    static class Reader {
        private static final String dataFileName = "topcoder/srm7xx/srm703/div1/DAGConstruction.sample";
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
