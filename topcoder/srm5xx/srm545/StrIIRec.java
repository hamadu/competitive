package topcoder.srm5xx.srm545;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/27.
 */
public class StrIIRec {
    public String recovstr(int n, int minInv, String minStr) {
        int m = minStr.length();
        int[] min = new int[m];
        for (int i = 0; i < m ; i++) {
            min[i] = minStr.charAt(i) - 'a';
        }

        boolean[] used = new boolean[n];
        int[] res = new int[n];
        Arrays.fill(res, -1);
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                if (!used[j]) {
                    res[i] = j;
                    if (canMake(res, minInv, min)) {
                        used[j] = true;
                        break;
                    }
                    res[i] = -1;
                }
            }
            if (res[i] == -1) {
                return "";
            }
        }

        String ret = "";
        for (int i = 0; i < n ; i++) {
            ret += (char)('a' + res[i]);
        }
        return ret;
    }

    public boolean canMake(int[] ord, int minInv, int[] minStr) {
        int n = ord.length;
        int mask = 0;
        int used = 0;
        for (int i = 0; i < n ; i++) {
            if (ord[i] != -1) {
                used++;
                mask |= 1<<ord[i];
            } else {
                break;
            }
        }

        int[] tord = ord.clone();
        for (int i = used ; i < n ; i++) {
            for (int j = n-1 ; j >= 0 ; j--) {
                if ((mask & (1<<j)) == 0) {
                    tord[i] = j;
                    mask |= 1<<j;
                    break;
                }
            }
        }

        for (int i = 0; i < minStr.length ; i++) {
            if (tord[i] < minStr[i]) {
                return false;
            } else if (tord[i] > minStr[i]) {
                break;
            }
        }

        int invCnt = 0;
        for (int i = 0; i < n ; i++) {
            for (int j = i+1 ; j < n; j++) {
                if (tord[i] > tord[j]) {
                    invCnt++;
                }
            }
        }
        return invCnt >= minInv;
    }
}
