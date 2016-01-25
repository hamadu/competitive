package topcoder.srm5xx.srm579;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hama_du on 15/09/13.
 */
public class UndoHistory {
    public int minPresses(String[] lines) {
        Set<String> histories = new HashSet<>();
        histories.add("");
        int cnt = 0;

        String buffer = "";
        for (String l : lines) {
            String best = buffer;
            int want = (l.startsWith(buffer)) ? (l.length() - buffer.length()) : Integer.MAX_VALUE;
            for (String h : histories) {
                if (l.startsWith(h)) {
                    if (want > 2 + l.length() - h.length()) {
                        want = 2 + l.length() - h.length();
                        best = h;
                    }
                }
            }
            buffer = best;
            for (int k = buffer.length() ; k <= l.length() ; k++) {
                histories.add(l.substring(0, k));
            }
            cnt += want;
            cnt++;
            buffer = l;
        }
        return cnt;
    }
}
