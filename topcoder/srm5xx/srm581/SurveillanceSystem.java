package topcoder.srm5xx.srm581;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hama_du on 15/09/13.
 */
public class SurveillanceSystem {
    public String getContainerInfo(String containers, int[] reports, int L) {
        char[] c = containers.toCharArray();
        int n = c.length;
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < n ; i++) {
            boolean canOut = out(i, c, reports, L);
            boolean canIn = in(i, c, reports, L);
            if (canOut && canIn) {
                line.append('?');
            } else if (canIn) {
                line.append('+');
            } else if (canOut) {
                line.append('-');
            } else {
                throw new RuntimeException("the input must be consistent");
            }
        }
        return line.toString();
    }

    private boolean out(int idx, char[] c, int[] reports, int L) {
        int n = c.length;
        int[] imos = new int[n+1];
        for (int i = 0; i < n ; i++) {
            imos[i+1] = imos[i] + (c[i] == 'X' ? 1 : 0);
        }
        int[] canMake = new int[L+1];
        for (int i = 0; i <= n-L; i++) {
            int fr = i;
            int to = i+L;
            if (fr <= idx && idx < to) {
                continue;
            }
            canMake[imos[to]-imos[fr]]++;
        }
        for (int r : reports) {
            canMake[r]--;
            if (canMake[r] < 0) {
                return false;
            }
        }
        return true;
    }
    private boolean in(int idx, char[] c, int[] reports, int L) {
        int n = c.length;
        int[] imos = new int[n+1];
        for (int i = 0; i < n ; i++) {
            imos[i+1] = imos[i] + (c[i] == 'X' ? 1 : 0);
        }
        int[] canMake = new int[L+1];
        List<Integer> need = new ArrayList<>();
        for (int i = 0; i <= n-L; i++) {
            int fr = i;
            int to = i+L;
            if (fr <= idx && idx < to) {
                need.add(imos[to]-imos[fr]);
            }
            canMake[imos[to]-imos[fr]]++;
        }

        int[] rp = new int[L+1];
        for (int r : reports) {
            rp[r]++;
        }

        for (int ne : need) {
            int[] wrp = rp.clone();
            int[] wcm = canMake.clone();
            if (wrp[ne] == 0) {
                continue;
            }
            wrp[ne]--;
            wcm[ne]--;
            boolean isOK = true;
            for (int i = 0; i <= L ; i++) {
                if (wrp[i] > canMake[i]) {
                    isOK = false;
                }
            }
            if (isOK) {
                return true;
            }
        }
        return false;
    }
}
