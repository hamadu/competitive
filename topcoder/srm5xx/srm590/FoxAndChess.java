package topcoder.srm5xx.srm590;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hama_du on 15/09/30.
 */
public class FoxAndChess {
    public String ableToMove(String begin, String target) {
        int n = begin.length();
        char[] fr = begin.toCharArray();
        char[] to = target.toCharArray();


        List<Integer> frL = new ArrayList<>();
        List<Integer> frR = new ArrayList<>();
        List<Integer> toL = new ArrayList<>();
        List<Integer> toR = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            if (fr[i] == 'L') {
                frL.add(i);
            } else if (fr[i] == 'R') {
                frR.add(i);
            }
        }
        for (int i = 0; i < n ; i++) {
            if (to[i] == 'L') {
                toL.add(i);
            } else if (to[i] == 'R') {
                toR.add(i);
            }
        }
        if (isOK(fr, to, frL, frR, toL, toR)) {
            return "Possible";
        } else {
            return "Impossible";
        }
    }

    private boolean isOK(char[] begin, char[] target, List<Integer> frL, List<Integer> frR, List<Integer> toL, List<Integer> toR) {
        if (frL.size() != toL.size() || frR.size() != toR.size()) {
            return false;
        }
        int ln = frL.size();
        int rn = frR.size();
        for (int i = 0 ; i < ln ; i++) {
            if (frL.get(i) < toL.get(i)) {
                return false;
            }
            int left = toL.get(i);
            int right = frL.get(i);
            for (int j = left ; j <= right ; j++) {
                if (begin[j] == 'R' || target[j] == 'R') {
                    return false;
                }
            }
        }
        for (int i = 0 ; i < rn ; i++) {
            if (frR.get(i) > toR.get(i)) {
                return false;
            }
            int left = frR.get(i);
            int right = toR.get(i);
            for (int j = left ; j <= right ; j++) {
                if (begin[j] == 'L' || target[j] == 'L') {
                    return false;
                }
            }
        }
        return true;
    }
}
