package topcoder.srm5xx.srm550;

/**
 * Created by hama_du on 15/08/30.
 */
public class ConversionMachine {
    public int countAll(String word1, String word2, int[] costs, int maxCost) {
        long[] ct = new long[3];
        for (int i = 0; i < 3 ; i++) {
            ct[i] = costs[i];
        }
        long[][] table = new long[256][256];
        table['a']['b'] = ct[0];
        table['a']['c'] = ct[0] + ct[1];
        table['b']['c'] = ct[1];
        table['b']['a'] = ct[1] + ct[2];
        table['c']['a'] = ct[2];
        table['c']['b'] = ct[2] + ct[0];
        long all = ct[0] + ct[1] + ct[2];


        int n = word1.length();
        long minCost = 0;
        for (int i = 0; i < n ; i++) {
            minCost += table[word1.charAt(i)][word2.charAt(i)];
        }
        if (minCost > maxCost) {
            return 0;
        }
        long cur = (maxCost - minCost) / all;


        return 0;
    }
}
