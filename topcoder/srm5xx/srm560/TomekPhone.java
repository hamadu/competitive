package topcoder.srm5xx.srm560;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hama_du on 15/09/02.
 */
public class TomekPhone {
    public int minKeystrokes(int[] frequencies, int[] keySizes) {
        int n = keySizes.length;
        List<Integer> types = new ArrayList<>();
        for (int i = 0; i < frequencies.length ; i++) {
            types.add(frequencies[i]);
        }
        List<Integer> keys = new ArrayList<>();
        for (int i = 0; i < keySizes.length ; i++) {
            for (int j = 1; j <= keySizes[i] ; j++) {
                keys.add(j);
            }
        }
        Collections.sort(types);
        Collections.reverse(types);
        Collections.sort(keys);
        if (types.size() > keys.size()) {
            return -1;
        }
        int ret = 0;
        for (int i = 0; i < types.size() ; i++) {
            ret += types.get(i) * keys.get(i);
        }
        return ret;
    }
}
