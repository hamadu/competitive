package topcoder.srm5xx.srm563;

/**
 * Created by hama_du on 15/09/03.
 */
public class FoxAndHandle {
    public String lexSmallestName(String S) {
        int n = S.length();
        char[] c = S.toCharArray();
        int[] deg = new int[26];
        for (int i = 0; i < n ; i++) {
            deg[c[i]-'a']++;
        }
        int sum = 0;
        for (int i = 0; i < 26 ; i++) {
            deg[i] /= 2;
            sum += deg[i];
        }
        int[] fullDeg = deg.clone();
        char[] ret = new char[sum];
        for (int i = 0; i < sum ; i++) {
            for (int k = 0; k < 26 ; k++) {
                if (deg[k] >= 1) {
                    ret[i] = (char)(k + 'a');
                    deg[k]--;
                    if (isOK(ret, deg.clone(), fullDeg.clone(), c)) {
                        break;
                    }
                    deg[k]++;
                }
            }
        }
        return String.valueOf(ret);
    }

    private boolean isOK(char[] ret, int[] deg1, int[] deg2, char[] c) {
        int n = ret.length;
        int idx1 = 0;
        int idx2 = 0;
        for (int i = 0; i < c.length ; i++) {
            int ci = c[i] - 'a';
            if (idx1 < n && ret[idx1] == c[i]) {
                // ok
                idx1++;
            } else if (idx1 < n && ret[idx1] == 0 && deg1[ci] >= 1) {
                deg1[ci]--;
                idx1++;
            } else if (idx2 < n && deg2[ci] >= 1) {
                deg2[ci]--;
                idx2++;
            } else {
                return false;
            }
        }
        return true;
    }
}
