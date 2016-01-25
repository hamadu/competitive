package topcoder.srm5xx.srm587;

/**
 * Created by hama_du on 15/09/26.
 */
public class JumpFurther {
    public int furthest(int N, int badStep) {
        int sum = 0;
        boolean wrong = false;
        for (int i = 1; i <= N ; i++) {
            sum += i;
            if (sum == badStep) {
                wrong = true;
            }
        }
        return sum - (wrong ? 1 : 0);
    }
}
