package topcoder.srm7xx.srm706.div1;

import java.io.*;
import java.util.*;

public class MappingABC {
    static final int K = 3000;

    static final long MOD = 1000000007;

    public int countStrings(int[] t) {
        int n = t.length;
        int[] deg = new int[K];
        for (int i = 0; i < n ; i++) {
            t[i]--;
            deg[t[i]]++;
        }
        int[][] pos = new int[K][];
        for (int i = 0; i < K ; i++) {
            pos[i] = new int[deg[i]];
        }
        Arrays.fill(deg, 0);

        boolean[] canLeft = new boolean[n];
        boolean[] canRight = new boolean[n];
        for (int i = 0; i < n ; i++) {
            if (deg[t[i]] == 0) {
                canLeft[i] = true;
            }
            pos[t[i]][deg[t[i]]++] = i;
            if (deg[t[i]] == pos[t[i]].length) {
                canRight[i] = true;
            }
        }
        int[] lpos = new int[n];
        int[] rpos = new int[n];
        {
            int lc = 0;
            int rc = 0;
            for (int i = 0; i < n; i++) {
                if (canLeft[i]) {
                    lpos[i] = lc;
                    lc++;
                }
            }
            for (int i = n-1 ; i >= 0 ; i--) {
                if (canRight[i]) {
                    rpos[i] = rc;
                    rc++;
                }
            }
        }

        debug(canLeft, canRight);


        long[] pow2 = new long[K+1];
        long[] pow3 = new long[K+1];
        long[] pow3m2 = new long[K+1];
        pow2[0] = pow3[0] = 1;
        for (int i = 1 ; i <= K; i++) {
            pow2[i] = (pow2[i-1] * 2) % MOD;
            pow3[i] = (pow3[i-1] * 3) % MOD;
        }
        for (int i = 0; i <= K ; i++) {
            pow3m2[i] = (pow3[i] - pow2[i] + MOD) % MOD;
        }


        long total = 0;
        boolean[] seen = new boolean[K];
        boolean[] seenL = new boolean[K];
        boolean[] seenR = new boolean[K];
        int RR = 0;
        for (int r = n-1 ; r >= 0 ; r--) {
            if (!canRight[r]) {
                seenR[t[r]] = true;
                continue;
            }
            Arrays.fill(seen, false);
            for (int l = 0; l < r-1; l++) {
                if (canLeft[l]) {
                    seenL[t[l]] = true;
                }
            }


            // how many unique left

            int[] flg = new int[K];
            int kind = 0;
            int kindL = 0;
            int kindR = 0;
            int kindBoth = 0;
            long sum = 0;
            for (int l = r-1 ; l >= 0 ; l--) {
                if (canLeft[l] && t[l] != t[r]) {
                    debug("==",sum);
                    int tlk = kindL;
//                    if (seen[t[l]]) {
//                        tlk--;
//                    }
                    int trk = kindR;
//                    if (seen[t[r]]) {
//                        trk--;
//                    }



                    if (kindBoth >= 1) {
                        sum += pow2[tlk+trk] * pow3[kind] % MOD;
                        sum %= MOD;
                    } else {
                        sum += (pow2[tlk+trk] * pow3[kind] % MOD + MOD - pow2[kind]) % MOD;
                        sum %= MOD;
                    }
                    debug(sum,l,r,kindL,kindR,kindBoth,kind);
                }


                if (!seen[t[l]]) {
                    seen[t[l]] = true;
                    if (seenL[t[l]]) {
                        if (seenR[t[l]]) {
                            flg[t[l]] = 3;
                            kindBoth++;
                        } else {
                            flg[t[l]] = 1;
                            kindL++;
                        }
                    } else if (seenR[t[l]]) {
                        flg[t[l]] = 2;
                        kindR++;
                    }

                }
                if (canLeft[l]) {
                    if (flg[t[l]] == 3) {
                        kindBoth--;
                    } else if (flg[t[l]] == 1){
                        kindL--;
                    }
                    kind++;
                }
            }
            seenR[t[r]] = true;
            total += sum * pow2[RR] % MOD;
            total %= MOD;
            RR++;
        }
        return (int)total;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    // CUT begin
        public static void main(String[] args){
        System.err.println("MappingABC (500 Points)");
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

            int[] t = new int[Integer.parseInt(Reader.nextLine())];
            for (int i = 0; i < t.length; ++i)
                t[i] = Integer.parseInt(Reader.nextLine());
            Reader.nextLine();
            int __answer = Integer.parseInt(Reader.nextLine());

            cases++;
            if (caseSet.size() > 0 && !caseSet.contains(cases - 1))
                continue;
            System.err.print(String.format("  Testcase #%d ... ", cases - 1));

            if (doTest(t, __answer))
                passed++;
        }
        if (caseSet.size() > 0) cases = caseSet.size();
        System.err.println(String.format("%nPassed : %d/%d cases", passed, cases));

        int T = (int)(System.currentTimeMillis() / 1000) - 1485016025;
        double PT = T / 60.0, TT = 75.0;
        System.err.println(String.format("Time   : %d minutes %d secs%nScore  : %.2f points", T / 60, T % 60, 500 * (0.3 + (0.7 * TT * TT) / (10.0 * PT * PT + TT * TT))));
    }

    static boolean doTest(int[] t, int __expected) {
        long startTime = System.currentTimeMillis();
        Throwable exception = null;
        MappingABC instance = new MappingABC();
        int __result = 0;
        try {
            __result = instance.countStrings(t);
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
        private static final String dataFileName = "topcoder/srm7xx/srm706/div1/MappingABC.sample";
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
