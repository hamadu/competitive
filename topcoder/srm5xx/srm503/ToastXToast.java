package topcoder.srm5xx.srm503;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/04.
 */
public class ToastXToast {
    public int bake(int[] undertoasted, int[] overtoasted) {
        Arrays.sort(undertoasted);
        Arrays.sort(overtoasted);

        int ul = undertoasted.length;
        int ol = overtoasted.length;
        if (undertoasted[ul-1] < overtoasted[0]) {
            return 1;
        }
        if (overtoasted[0] < undertoasted[0]) {
            return -1;
        }
        if (overtoasted[ol-1] < undertoasted[ul-1]) {
            return -1;
        }
        return 2;
    }
}
