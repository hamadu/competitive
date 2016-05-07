package atcoder.arc.arc015;

/**
 * Created by hama_du on 2016/05/07.
 */
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class C {
    static class Conv {
        String l;
        double m;
        String s;

        Conv(String _, double __, String ___) {
            l = _;
            m = __;
            s = ___;
            if (m < 1) {
                l = ___;
                s = _;
                m = 1/__;
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = in.nextInt();
        Conv[] cv = new Conv[N];
        Map<String, Integer> umap = new HashMap<String, Integer>();
        String[] units = new String[N*2];
        for (int i = 0; i<N; i++) {
            cv[i] = new Conv(in.next(), in.nextDouble(), in.next());
            if (!umap.containsKey(cv[i].l)) {
                units[umap.size()] = cv[i].l;
                umap.put(cv[i].l, umap.size());
            }
            if (!umap.containsKey(cv[i].s)) {
                units[umap.size()] = cv[i].s;
                umap.put(cv[i].s, umap.size());
            }
        }

        int n = umap.size();
        double[][] graph = new double[n][n];
        for (int i = 0; i<N; i++) {
            int f = umap.get(cv[i].s);
            int t = umap.get(cv[i].l);
            graph[f][t] = cv[i].m;
        }

        for (int cur = 0 ; cur < 6 ; cur++) {
            for (int k = 0; k<n; k++) {
                for (int i = 0; i<n; i++) {
                    for (int j = 0; j<n; j++) {
                        if (graph[i][k]>=1&&graph[k][j]>=1) {
                            graph[i][j] = graph[i][k]*graph[k][j];
                        } else if (graph[i][j]>=1&&(graph[i][k]>=1||graph[k][j]>=1)) {
                            if (graph[i][k]>=1) {
                                graph[k][j] = graph[i][j]/graph[i][k];
                            } else {
                                graph[i][k] = graph[i][j]/graph[k][j];
                            }
                        }
                    }
                }
            }
        }

        double ans = -1;
        int mosti = -1;
        int mostj = -1;
        for (int i = 0; i<n; i++) {
            for (int j = 0; j<n; j++) {
                if (ans < graph[i][j]) {
                    mosti = i;
                    mostj = j;
                    ans = graph[i][j];
                }
            }
        }
        out.println("1" + units[mostj] + "=" + (long)(ans+0.5) + units[mosti]);
        out.flush();
    }

    public static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}