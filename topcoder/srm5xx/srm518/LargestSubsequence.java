package topcoder.srm5xx.srm518;

/**
 * Created by hama_du on 15/08/14.
 */
public class LargestSubsequence {
    public String getLargest(String s) {
        String ret = "";
        int head = 0;
        char[] c = s.toCharArray();
        while (true) {
            int idx = -1;
            for (int i = head ; i < c.length; i++) {
                if (idx == -1 || c[idx] < c[i]) {
                    idx = i;
                }
            }
            if (idx == -1) {
                break;
            }
            ret += c[idx];
            head = idx+1;
        }
        return ret;
    }
}
