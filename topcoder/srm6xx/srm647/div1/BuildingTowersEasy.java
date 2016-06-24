package topcoder.srm6xx.srm647.div1;

/**
 * Created by hama_du
 */
import java.io.*;
import java.util.*;

public class BuildingTowersEasy {
    public int maxHeight(int N, int[] x, int[] t) {
        int[] cap = new int[N+1];
        Arrays.fill(cap, Integer.MAX_VALUE);
        for (int i = 0; i < x.length ; i++) {
            cap[x[i]] = t[i];
        }
        cap[1] = 0;
        for (int f = 0 ; f <= 2 ; f++) {
            for (int i = 2; i <= N; i++) {
                cap[i] = Math.min(cap[i], cap[i-1]+1);
            }
            for (int i = N-1; i >= 1; i--) {
                cap[i] = Math.min(cap[i], cap[i+1]+1);
            }
        }
        int max = 0;
        for (int i = 1 ; i <= N; i++) {
            max = Math.max(max, cap[i]);
        }
        return max;
    }
}
