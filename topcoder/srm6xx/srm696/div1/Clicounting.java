package topcoder.srm6xx.srm696.div1;

import java.io.*;
import java.util.*;

public class Clicounting {
    public int count(String[] g) {
        n = g.length;
        vs = new V[n];

        int[][] qs = new int[10][2];
        int qn = 0;
        for (int i = 0; i < n ; i++) {
            vs[i] = new V();
            vs[i].idx = i;
            vs[i].filter = ((1L<<n)-1)^(1L<<i);
            vs[i].edges |= 1L<<i;
            for (int j = 0 ; j < n ; j++) {
                if (g[i].charAt(j) == '1') {
                    vs[i].deg++;
                    vs[i].edges |= 1L<<j;
                } else if (g[i].charAt(j) == '?') {
                    if (i < j) {
                        qs[qn] = new int[]{i, j};
                        qn++;
                    }
                }
            }
        }

        int ret = 0;
        for (int ptn = 0 ; ptn < (1<<qn) ; ptn++) {
            for (int q = 0; q < qn; q++) {
                if ((ptn & (1<<q)) >= 1) {
                    int u = qs[q][0];
                    int v = qs[q][1];
                    vs[u].edges ^= 1L<<v;
                    vs[v].edges ^= 1L<<u;
                    vs[u].deg++;
                    vs[v].deg++;
                }
            }
            Arrays.sort(vs, (u, v) -> u.deg - v.deg);

            max = 0;
            dfs0(0, 0, (1L<<n)-1);
            ret += max;

            Arrays.sort(vs, (u, v) -> u.idx - v.idx);
            for (int q = 0; q < qn; q++) {
                if ((ptn & (1<<q)) >= 1) {
                    int u = qs[q][0];
                    int v = qs[q][1];
                    vs[u].edges ^= 1L<<v;
                    vs[v].edges ^= 1L<<u;
                    vs[u].deg--;
                    vs[v].deg--;
                }
            }
        }

        return ret;
    }

    private void dfs0(int idx, long selected, long exists) {
        if (idx == n) {
            int cnt = Long.bitCount(exists);
            if (max < cnt) {
                max = cnt;
            }
            return;
        }
        if (max >= Long.bitCount(exists)) {
            return;
        }
        dfs0(idx+1, selected, exists & vs[idx].filter);

        long ts = selected | 1L<<vs[idx].idx;
        long te = exists & vs[idx].edges;
        if ((ts & te) == ts) {
            dfs0(idx+1, ts, te);
        }
    }

    private int n;
    private int max;

    private V[] vs;

    public class V {
        int idx;
        int deg;
        long edges;
        long filter;
        long qs;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // CUT begin
        public static void main(String[] args){
        System.err.println("Clicounting (550 Points)");
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

            String[] g = new String[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < g.length; ++i)
                g[i] = Reader.nextLine();
            Reader.nextLine();
            int __answer = Integer.parseInt(Reader.nextLine());

            cases++;
            if (caseSet.size() > 0 && !caseSet.contains(cases - 1))
                continue;
            System.err.print(String.format("  Testcase #%d ... ", cases - 1));

            if (doTest(g, __answer))
                passed++;
        }
        if (caseSet.size() > 0) cases = caseSet.size();
        System.err.println(String.format("%nPassed : %d/%d cases", passed, cases));

        int T = (int)(System.currentTimeMillis() / 1000) - 1483256684;
        double PT = T / 60.0, TT = 75.0;
        System.err.println(String.format("Time   : %d minutes %d secs%nScore  : %.2f points", T / 60, T % 60, 550 * (0.3 + (0.7 * TT * TT) / (10.0 * PT * PT + TT * TT))));
    }

    static boolean doTest(String[] g, int __expected) {
        for (int i = 0; i < g.length; i++) {
            g[i] = new String(g[i]);
        }
        long startTime = System.currentTimeMillis();
        Throwable exception = null;
        Clicounting instance = new Clicounting();
        int __result = 0;
        try {
            __result = instance.count(g);
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
        private static final String dataFileName = "topcoder/srm6xx/srm696/div1/Clicounting.sample";
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
