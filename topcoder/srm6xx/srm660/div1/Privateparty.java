package topcoder.srm6xx.srm660.div1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hama_du on 2016/06/15.
 */
public class Privateparty {
    public double rate(int idx, double d, List<Integer>[] graph) {
        if (idx == a[idx]) {
            return 1.0d;
        }
        if (toposort(graph) == null) {
            return 0;
        }
        graph[a[idx]].add(idx);
        return d / (d + 1) + (1 / (d + 1)) * (1 - rate(a[idx], d+1, graph));
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


    public double getexp(int[] a) {
        int n = a.length;
        this.a = a;
        double as = 0;
        for (int i = 0 ; i < a.length ; i++) {
            List<Integer>[] condGraph = new List[n];
            for (int j = 0; j < n ; j++) {
                condGraph[j] = new ArrayList<>();
            }
            double po = rate(i, 1, condGraph);
            as += po;
        }
        return as;
    }

    int[] a;
}
