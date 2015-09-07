package topcoder.srm5xx.srm571;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hama_du on 15/09/04.
 */
public class FoxAndMp3 {
    public String[] playList(int n) {
        build(0, n);
        String[] ret = new String[list.size()];
        for (int i = 0; i < ret.length ; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    List<String> list = new ArrayList<>();

    public void build(int now, int limit) {
        if (now > limit || list.size() >= 50) {
            return;
        }
        if (now >= 1) {
            list.add(String.format("%d.mp3", now));
        }
        for (int d = (now == 0) ? 1 : 0 ; d <= 9; d++) {
           build(now*10+d, limit);
        }
    }
}
