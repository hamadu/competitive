package topcoder.srm6xx.srm645.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;

public class ArmyTeleportation {
    public String ifPossible(int[] x1, int[] y1, int[] x2, int[] y2, int[] xt, int[] yt) {
        int n = x1.length;
        for (int to = 0 ; to < n ; to++) {
            for (int c = -1 ; c <= 2; c++) {
                int[] fx = x1.clone();
                int[] fy = y1.clone();
                if (c != -1) {
                    for (int i = 0; i < n ; i++) {
                        fx[i] = xt[c] * 2 - fx[i];
                        fy[i] = yt[c] * 2 - fy[i];
                    }
                }
                int wantDX = x2[to] - fx[0];
                int wantDY = y2[to] - fy[0];
                if (canMake(wantDX, wantDY, xt, yt)) {
                    boolean isOK = true;
                    for (int i = 0; i < n ; i++) {
                        int tx = fx[i] + wantDX;
                        int ty = fy[i] + wantDY;
                        boolean found = false;
                        for (int j = 0; j < n ; j++) {
                            if (x2[j] == tx && y2[j] == ty) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            isOK = false;
                            break;
                        }
                    }
                    if (isOK) {
                        return "possible";
                    }
                }
            }
        }
        return "impossible";
    }

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a%b);
    }


    private boolean canMake(long wantDX, long wantDY, int[] xt, int[] yt) {
        return canMake(wantDX, wantDY, xt, yt, 0, 1, 2)
                || canMake(wantDX, wantDY, xt, yt, 1, 2, 0)
                || canMake(wantDX, wantDY, xt, yt, 2, 0, 1);
    }

    private boolean canMake(long wantDX, long wantDY, int[] xt, int[] yt, int a, int b, int c) {
        long dx0 = 2*(xt[b]-xt[a]);
        long dy0 = 2*(yt[b]-yt[a]);
        long dx1 = 2*(xt[c]-xt[a]);
        long dy1 = 2*(yt[c]-yt[a]);
        long det = (dx0*dy1-dy0*dx1);

        if (det == 0) {
            long baseX = gcd(Math.abs(dx0), Math.abs(dx1));
            long baseY = gcd(Math.abs(dy0), Math.abs(dy1));
            if (baseX == 0) {
                return wantDX == 0 && Math.abs(wantDY) % baseY == 0;
            } else if (baseY == 0) {
                return wantDY == 0 && Math.abs(wantDX) % baseX == 0;
            }
            baseX *= dx0 / Math.abs(dx0);
            baseY *= dy0 / Math.abs(dy0);
            long K = wantDX / baseX;
            return (baseX * K == wantDX && baseY * K == wantDY);
        } else {
            long A = dy1 * wantDX - dx1 * wantDY;
            long B = -dy0 * wantDX + dx0 * wantDY;
            if (A % det != 0 || B % det != 0) {
                return false;
            }
            A /= det;
            B /= det;
            return (dx0*A+dx1*B == wantDX && dy0*A+dy1*B == wantDY);
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // CUT begin
        public static void main(String[] args){
        System.err.println("ArmyTeleportation (500 Points)");
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

            int[] x1 = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < x1.length; ++i)
                x1[i] = Integer.parseInt(Reader.nextLine());
            int[] y1 = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < y1.length; ++i)
                y1[i] = Integer.parseInt(Reader.nextLine());
            int[] x2 = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < x2.length; ++i)
                x2[i] = Integer.parseInt(Reader.nextLine());
            int[] y2 = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < y2.length; ++i)
                y2[i] = Integer.parseInt(Reader.nextLine());
            int[] xt = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < xt.length; ++i)
                xt[i] = Integer.parseInt(Reader.nextLine());
            int[] yt = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < yt.length; ++i)
                yt[i] = Integer.parseInt(Reader.nextLine());
            Reader.nextLine();
            String __answer = Reader.nextLine();

            cases++;
            if (caseSet.size() > 0 && !caseSet.contains(cases - 1))
                continue;
            System.err.print(String.format("  Testcase #%d ... ", cases - 1));

            if (doTest(x1, y1, x2, y2, xt, yt, __answer))
                passed++;
        }
        if (caseSet.size() > 0) cases = caseSet.size();
        System.err.println(String.format("%nPassed : %d/%d cases", passed, cases));

        int T = (int)(System.currentTimeMillis() / 1000) - 1468030649;
        double PT = T / 60.0, TT = 75.0;
        System.err.println(String.format("Time   : %d minutes %d secs%nScore  : %.2f points", T / 60, T % 60, 500 * (0.3 + (0.7 * TT * TT) / (10.0 * PT * PT + TT * TT))));
    }

    static boolean doTest(int[] x1, int[] y1, int[] x2, int[] y2, int[] xt, int[] yt, String __expected) {
        long startTime = System.currentTimeMillis();
        Throwable exception = null;
        ArmyTeleportation instance = new ArmyTeleportation();
        String __result = "";
        try {
            __result = instance.ifPossible(x1, y1, x2, y2, xt, yt);
        }
        catch (Throwable e) { exception = e; }
        double elapsed = (System.currentTimeMillis() - startTime) / 1000.0;

        if (exception != null) {
            System.err.println("RUNTIME ERROR!");
            exception.printStackTrace();
            return false;
        }
        else if (__expected.equals(__result)) {
            System.err.println("PASSED! " + String.format("(%.2f seconds)", elapsed));
            return true;
        }
        else {
            System.err.println("FAILED! " + String.format("(%.2f seconds)", elapsed));
            System.err.println("           Expected: " + "\"" + __expected + "\"");
            System.err.println("           Received: " + "\"" + __result + "\"");
            return false;
        }
    }

    static class Reader {
        private static final String dataFileName = "topcoder/srm6xx/srm645/div1/ArmyTeleportation.sample";
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
