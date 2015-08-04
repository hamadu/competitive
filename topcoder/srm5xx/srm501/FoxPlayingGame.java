package topcoder.srm5xx.srm501;

/**
 * Created by hama_du on 15/08/04.
 */
public class FoxPlayingGame {
    public double theMax(int nA, int nB, int paramA, int paramB) {
        double pa = paramA / 1000.0d;
        double pb = paramB / 1000.0d;
        double[] add = new double[nA+1];
        for (int i = 0; i < nA; i++) {
            add[i+1] = add[i] + pa;
        }

        double[] mul = new double[nB+1];
        mul[0] = 1.0d;
        for (int i = 0; i < nB; i++) {
            mul[i+1] = mul[i] * pb;
        }


        double max = Double.MIN_EXPONENT;
        for (int i = 0; i <= nA ; i++) {
            for (int j = 0; i+j <= nA ; j++) {
                for (int k = 0; k <= nB; k++) {
                    for (int l = 0; k+l <= nB ; l++) {
                        double now = 0;
                        int fb = nB - (k+l);
                        now *= mul[fb];
                        int fa = nA - (i+j);
                        now += add[fa];
                        now *= mul[k];
                        now += add[i];
                        now *= mul[l];
                        now += add[j];
                        max = Math.max(max, now);
                    }
                }
            }
        }
        return max;
    }


}
