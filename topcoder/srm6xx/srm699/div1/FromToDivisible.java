package topcoder.srm6xx.srm699.div1;

import java.io.*;
import java.util.*;

public class FromToDivisible {
    private static final int INF = 114514;

    public int shortest(int N, int S, int T, int[] a, int[] b) {
        List<int[]> edges = new ArrayList<>();

        // S -> a[i] ?
        int n = a.length;
        for (int i = 0; i < n; i++) {
            if (S % a[i] == 0) {
                edges.add(new int[]{n, i});
            }
            if (T % b[i] == 0) {
                edges.add(new int[]{i, n+1});
            }
        }

        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                long ab = lcm(b[i], a[j]);
                if (ab <= N && i != j) {
                    edges.add(new int[]{i, j});
                }
            }
        }
        int[][] graph = buildGraph(n+2, edges);
        int ret = cost(n, n+1, graph);

        return ret >= INF ? -1 : ret-1;
    }

    static int cost(int start, int goal, int[][] graph) {
        int n = graph.length;
        int[] dp = new int[n];
        Arrays.fill(dp, INF);
        dp[start] = 0;
        int[] que = new int[2*n];
        int qh = 0;
        int qt = 0;
        que[qh++] = start;
        que[qh++] = 0;
        while (qt < qh) {
            int now = que[qt++];
            int tim = que[qt++]+1;
            for (int to : graph[now]) {
                if (dp[to] > tim) {
                    dp[to] = tim;
                    que[qh++] = to;
                    que[qh++] = tim;
                }
            }
        }
        return dp[goal];
    }

    static int[][] buildGraph(int n, List<int[]> edges) {
        int m = edges.size();
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges.get(i)[0];
            deg[a]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges.get(i)[0];
            int b = edges.get(i)[1];
            graph[a][--deg[a]] = b;
        }
        return graph;
    }


    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a%b);
    }

    public static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // CUT begin
        public static void main(String[] args){
        System.err.println("FromToDivisible (500 Points)");
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
            int S = Integer.parseInt(Reader.nextLine());
            int T = Integer.parseInt(Reader.nextLine());
            int[] a = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < a.length; ++i)
                a[i] = Integer.parseInt(Reader.nextLine());
            int[] b = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < b.length; ++i)
                b[i] = Integer.parseInt(Reader.nextLine());
            Reader.nextLine();
            int __answer = Integer.parseInt(Reader.nextLine());

            cases++;
            if (caseSet.size() > 0 && !caseSet.contains(cases - 1))
                continue;
            System.err.print(String.format("  Testcase #%d ... ", cases - 1));

            if (doTest(N, S, T, a, b, __answer))
                passed++;
        }
        if (caseSet.size() > 0) cases = caseSet.size();
        System.err.println(String.format("%nPassed : %d/%d cases", passed, cases));

        int T = (int)(System.currentTimeMillis() / 1000) - 1477226535;
        double PT = T / 60.0, TT = 75.0;
        System.err.println(String.format("Time   : %d minutes %d secs%nScore  : %.2f points", T / 60, T % 60, 500 * (0.3 + (0.7 * TT * TT) / (10.0 * PT * PT + TT * TT))));
    }

    static boolean doTest(int N, int S, int T, int[] a, int[] b, int __expected) {
        long startTime = System.currentTimeMillis();
        Throwable exception = null;
        FromToDivisible instance = new FromToDivisible();
        int __result = 0;
        try {
            __result = instance.shortest(N, S, T, a, b);
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
        private static final String dataFileName = "topcoder/srm6xx/srm699/div1/FromToDivisible.sample";
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
