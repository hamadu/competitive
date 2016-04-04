package codeforces.cf2xx.cr290.div1;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        char[][] authors = new char[n][];
        for (int i = 0; i < n; i++) {
            authors[i] = in.next().toCharArray();
        }

        List<Integer>[] graph = new List[26];
        for (int i = 0; i < 26 ; i++) {
            graph[i] = new ArrayList<>();
        }
        boolean[][] edge = new boolean[26][26];

        for (int i = 0 ; i < n ; i++) {
            for (int j = i+1 ; j < n ; j++) {
                for (int k = 0 ; k < Math.min(authors[i].length, authors[j].length) ; k++) {
                    if (authors[i][k] != authors[j][k]) {
                        int c1 = authors[i][k] - 'a';
                        int c2 = authors[j][k] - 'a';
                        if (!edge[c1][c2]) {
                            edge[c1][c2] = true;
                            graph[c1].add(c2);
                        }
                        break;
                    }
                }
            }
        }

        int[] topo = toposort(graph);
        for (int i = 0 ; i < n ; i++) {
            for (int j = i + 1; j < n; j++) {
                String ai = String.valueOf(authors[i]);
                String aj = String.valueOf(authors[j]);
                if (ai.startsWith(aj) && !aj.equals(ai)) {
                    topo = null;
                }
            }
        }

        if (topo == null) {
            out.println("Impossible");
        } else {
            StringBuilder b = new StringBuilder();
            for (int t: topo) {
                b.append((char)('a' + t));
            }
            out.println(b.toString());
        }
        out.flush();
    }

    static int[] toposort(List<Integer>[] graph) {
        int n = graph.length;
        int[] in = new int[n];
        for (int i = 0; i < n; i++) {
            for (int t : graph[i]) {
                in[t]++;
            }
        }

        int[] res = new int[n];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            if (in[i] == 0) {
                res[idx++] = i;
            }
        }
        for (int i = 0; i < idx; i++) {
            for (int t : graph[res[i]]) {
                in[t]--;
                if (in[t] == 0) {
                    res[idx++] = t;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (in[i] >= 1) {
                return null;
            }
        }
        return res;
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}



