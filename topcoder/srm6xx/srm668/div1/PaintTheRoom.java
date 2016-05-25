package topcoder.srm6xx.srm668.div1;

/**
 * Created by hama_du on 2016/05/25.
 */
public class PaintTheRoom {
    public String canPaintEvenly(int R, int C, int K) {
        return solve(R, C, K) ? "Paint" : "Cannot paint";
    }

    private boolean solve(int r, int c, int k) {
        if ((r * c) % 2 == 0) {
            return true;
        }
        return k == 1;
    }
}
