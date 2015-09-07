package topcoder.srm5xx.srm573;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hama_du on 15/09/05.
 */
public class TeamContest {
    public int worstRank(int[] s) {
        int my = Math.max(s[0], Math.max(s[1], s[2])) + Math.min(s[0], Math.min(s[1], s[2]));
        List<Integer> other = new ArrayList<>();
        for (int i = 3 ; i < s.length; i++) {
            other.add(s[i]);
        }
        Collections.sort(other);
        int rank = 1;
        int n = other.size();
        int left = 0;
        int right = n-1;
        while (left <= right) {
            int rc = other.get(right);
            while (left+1 < right && other.get(left) + rc <= my) {
                left++;
            }
            if (left+1 >= right) {
                break;
            }
            right--;
            left += 2;
            rank++;
        }
        return rank;
    }
}
