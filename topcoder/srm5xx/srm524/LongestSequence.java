package topcoder.srm5xx.srm524;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 15/08/17.
 */
public class LongestSequence {
    public int maxLength(int[] C) {
        int n = C.length;
        boolean hasPositive = false;
        boolean hasNegative = false;
        for (int i = 0; i < n ; i++) {
            if (C[i] < 0) {
                hasNegative = true;
            } else {
                hasPositive = true;
            }
        }
        if (!hasNegative || !hasPositive) {
            return -1;
        }

        int minABS = 10000;
        for (int i = 0; i < n ; i++) {
            minABS = Math.min(minABS, Math.abs(C[i]));
        }

        List<Integer>[] graph = new List[4000];
        for (int i = 0; i < 4000 ; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int l = minABS ; l <= graph.length-1 ; l++) {
            for (int i = 0; i < n ; i++) {
                int z = l - Math.abs(C[i]);
                if (z >= 0) {
                    if (C[i] < 0) {
                        graph[l].add(z);
                    } else {
                        graph[z].add(l);
                    }
                }
            }
            if (toposort(graph) == null) {
                return l-1;
            }
        }
        return -1;
    }

    static int[] toposort(List<Integer>[] graph) {
        int n = graph.length;
        int[] in = new int[n];
        for (int i = 0 ; i < n ; i++) {
            for (int t : graph[i]) {
                in[t]++;
            }
        }

        int[] res = new int[n];
        int idx = 0;
        for (int i = 0 ; i < n ; i++) {
            if (in[i] == 0) {
                res[idx++] = i;
            }
        }
        for (int i = 0 ; i < idx ; i++) {
            for (int t : graph[res[i]]) {
                in[t]--;
                if (in[t] == 0) {
                    res[idx++] = t;
                }
            }
        }
        for (int i = 0 ; i < n ; i++) {
            if (in[i] >= 1) {
                return null;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        LongestSequence sequence = new LongestSequence();
        debug(sequence.maxLength(new int[]{114,-114}));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
