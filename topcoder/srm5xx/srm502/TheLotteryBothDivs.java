package topcoder.srm5xx.srm502;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hama_du on 15/08/04.
 */
public class TheLotteryBothDivs {
    public double find(String[] goodSuffixes) {
        Map<String,Integer> partMap = new HashMap<String,Integer>();
        partMap.put("", 0);
        partMap.put("WRONG", 1);

        int n = goodSuffixes.length;

        for (int i = 0; i < n ; i++) {
            String sf = goodSuffixes[i];
            for (int j = 0; j <= sf.length(); j++) {
                String part = sf.substring(j);
                if (!partMap.containsKey(part)) {
                    partMap.put(part, partMap.size());
                }
            }
        }


        int ps = partMap.size();
        boolean[] isJackpot = new boolean[ps];
        for (String good : goodSuffixes) {
            isJackpot[partMap.get(good)] = true;
        }

        int[][] graph = new int[ps][10];
        for (String part : partMap.keySet()) {
            int idx = partMap.get(part);
            for (char c = '0' ; c <= '9' ; c++) {
                String to = c+part;
                if (partMap.containsKey(to)) {
                    graph[idx][c-'0'] = partMap.get(to);
                } else {
                    graph[idx][c-'0'] = 1;
                }
            }
        }


        long[][][] dp = new long[10][2][ps];
        dp[0][0][0] = 1;
        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j <= 1; j++) {
                for (int now = 0; now < ps ; now++) {
                    if (dp[i][j][now] == 0) {
                        continue;
                    }
                    long base = dp[i][j][now];

                    for (int d = 0; d <= 9; d++) {
                        int to = graph[now][d];
                        int tj = isJackpot[to] ? 1 : j;
                        dp[i+1][tj][to] += base;
                    }
                }
            }
        }

        long total = 0;
        for (int i = 0 ; i < ps ; i++) {
            total += dp[9][1][i];
        }
        return total / 1000000000.0;
    }

    public static void main(String[] args) {
        TheLotteryBothDivs l = new TheLotteryBothDivs();
        debug(l.find(new String[]{"8542861", "1954", "6", "523", "000000000", "5426", "8"}));
    }


    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}
