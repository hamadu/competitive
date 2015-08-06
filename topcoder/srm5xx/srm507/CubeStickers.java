package topcoder.srm5xx.srm507;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hama_du on 15/08/05.
 */
public class CubeStickers {
    public String isPossible(String[] sticker) {
        Map<String,Integer> deg = new HashMap<>();
        for (String s : sticker) {
            if (!deg.containsKey(s)) {
                deg.put(s, 1);
            } else {
                deg.put(s, deg.get(s)+1);
            }
        }

        int two = 0;
        int one = 0;
        for (String col : deg.keySet()) {
            int v = deg.get(col);
            if (v == 1) {
                one++;
            } else {
                two++;
            }
        }
        int men = two * 2 + one;
        return men >= 6 ? "YES" : "NO";
    }
}
