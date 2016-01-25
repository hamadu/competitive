package topcoder.srm5xx.srm587;

/**
 * Created by hama_du on 15/09/26.
 */
public class TriangleXor {
    public int theArea(int W) {
        double[] height = new double[W+1];
        for (int a = 0; a <= W ; a++) {
            height[a] = (1.0d * a) / (1.0d * W + a);
        }
        double top = (W % 2 == 0) ? 1.0d * W / 4 : 0.0d;
        double bottom = 0;
        for (int s = W-W%2 ; s >= 2 ; s -= 2) {
            int num = W-s+1;
            double add = s * height[s] - 2 * (s - 1) * height[s-1] + (s - 2) * height[s-2];
            add *= num;
            add /= 2;
            bottom += add;
        }
        double left = 0;
        for (int s = 1 ; s <= W ; s += 2) {
            double add = 0.5;
            add -= (s * height[s] / 2) - ((s-1) * height[s-1] / 2);
            left += add;
        }
        return (int)(top + bottom + left * 2);
    }
}
