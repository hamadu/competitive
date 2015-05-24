package bestcoder.br042;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by dhamada on 15/05/23.
 */
public class B {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            String l = in.readLine();
            if (l == null) {
                break;
            }
            String[] nm = l.split(" ");
            int n = Integer.valueOf(nm[0]);
            int m = Integer.valueOf(nm[1]);

            String[] hi = in.readLine().split(" ");
            String[] qi = in.readLine().split(" ");

            int[] h = new int[n];
            for (int i = 0; i < n ; i++) {
                h[i] = Integer.valueOf(hi[i]);
            }
            Map<Integer,Integer> comp = compress(h);
            int sz = comp.size();

            int[][] table = new int[sz][];
            int[] deg = new int[sz];
            for (int i = 0; i < n ; i++) {
                int th = comp.get(h[i]);
                deg[th]++;
                h[i] = th;
            }
            for (int i = 0; i < sz ; i++) {
                if (deg[i] >= 1) {
                    table[i] = new int[deg[i]];
                }
            }
            Arrays.fill(deg, 0);
            for (int i = 0; i < n ; i++) {
                table[h[i]][deg[h[i]]++] = i;
            }
            Arrays.fill(deg, 0);

            for (int i = 0; i < m ; i++) {
                int q = Integer.valueOf(qi[i]);
                if (comp.containsKey(q)) {
                    int id = comp.get(q);
                    if (deg[id] < table[id].length) {
                        out.println(table[id][deg[id]++] + 1);
                    } else {
                        out.println(-1);
                    }
                } else {
                    out.println(-1);
                }
            }
        }
        out.flush();
    }

    static Map<Integer, Integer> compress(int[] a) {
        int n = a.length;
        int[] b = a.clone();
        for (int i = 0 ; i < n ; i++) {
            int j = (int)(Math.random() * n);
            int tmp = b[j];
            b[j] = b[i];
            b[i] = tmp;
        }

        LinkedHashSet<Integer> set = new LinkedHashSet<Integer>();
        List<Integer> nadd = new ArrayList<Integer>();
        for (int i = 0 ; i < n ; i++) {
            if (!set.contains(b[i])) {
                set.add(b[i]);
                nadd.add(b[i]);
            }
        }
        Collections.shuffle(nadd);
        Collections.sort(nadd);

        Map<Integer, Integer> comp = new HashMap<Integer, Integer>();
        for (int na : nadd) {
            comp.put(na, comp.size());
        }
        return comp;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
