package topcoder.srm6xx.srm654.div1;

/**
 * Created by hama_du on 2016/06/20.
 */
public class SquareScores {
    public double calcexpectation(int[] p, String s) {
        char[] c = s.toCharArray();
        int n = c.length;

        double[] sameRate = new double[n+1];
        sameRate[0] = 1.0d;
        for (int i = 1 ; i <= n ; i++) {
            for (int j = 0; j < p.length; j++) {
                sameRate[i] += Math.pow(p[j] / 100.0, i);
            }
        }
        
        double ans = 0;
        for (int i = 0; i < n ; i++) {
            int occur = -1;
            int hatena = 0;
            for (int j = i ; j < n ; j++) {
                if (c[j] != '?') {
                    if (occur == -1 || occur == c[j]-'a') {
                        occur = c[j]-'a';
                    } else {
                        break;
                    }
                } else {
                    hatena++;
                }
                if (occur == -1) {
                    ans += sameRate[hatena];
                } else {
                    ans += Math.pow(p[occur] / 100.0d, hatena);
                }
            }
        }
        return ans;
    }
}
