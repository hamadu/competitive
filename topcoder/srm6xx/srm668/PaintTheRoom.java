package topcoder.srm6xx.srm668;

/**
 * Created by hama_du on 15/09/16.
 */
public class PaintTheRoom {
    static final String OK = "Paint";
    static final String NG = "Cannot paint";

    public String canPaintEvenly(int R, int C, int K) {
        if (K == 1) {
            return OK;
        }
        int mod = R * C % 2;
        return mod == 0 ? OK : NG;
    }
}
