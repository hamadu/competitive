package topcoder.srm5xx.srm507;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/05.
 */
public class CubePacking {
    public int getMinimumVolume(int Ns, int Nb, int L) {
        long need = Ns + Nb * L * L * L;
        long best = Long.MAX_VALUE;

        long xlimit = (long)(Math.pow(2 * need, 1.0d / 3) + L);
        for (long x = L ; x <= xlimit  ; x++) {
            long ylimit = (long)(Math.pow(2 * need / x, 0.5) + L);
            for (long y = x ; y <= ylimit; y++) {
                long perXY = (x / L) * (y / L);
                long needToStack = (Nb + perXY - 1) / perXY;
                long z = needToStack * L;
                long volume = x * y * z;

                if (volume >= need) {
                    best = Math.min(best, volume);
                } else {
                    long dz = (need - volume + x * y - 1) / (x * y);
                    best = Math.min(best, volume + dz * x * y);
                }
            }
        }
        return (int)best;
    }

    public static void main(String[] args) {
        CubePacking pack = new CubePacking();
        debug(pack.getMinimumVolume(144296168, 978414, 7));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
