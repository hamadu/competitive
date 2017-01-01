package topcoder.srm7xx.srm704.div1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeDistanceConstruction {

    static int[] NG = new int[]{};

    public int[] construct(int[] d) {
        int n = d.length;
        if (n == 2) {
            return new int[]{0, 1};
        }
        List<Integer>[] categorize = new List[n];
        for (int i = 0; i < n ; i++) {
            categorize[i] = new ArrayList<>();
        }
        for (int i = 0; i < n ; i++) {
            categorize[d[i]].add(i);
        }

        int max = 0;
        for (int i = n-1 ; i >= 0 ; i--) {
            if (categorize[i].size() >= 1) {
                max = i;
                break;
            }
        }

        int min = 0;
        for (int i = 1 ; i <= n ; i++) {
            if (categorize[i].size() >= 1) {
                min = i;
                break;
            }
        }
        if (categorize[max].size() <= 1) {
            return NG;
        }
        if (categorize[min].size() > 2) {
            return NG;
        }

        int[][] ltwo = new int[n][2];
        ltwo[max][0] = categorize[max].get(0);
        ltwo[max][1] = categorize[max].get(1);

        int current = max;
        boolean odd = categorize[min].size() == 1;
        while (current > min) {
            current--;
            if (current == min && odd) {
                if (categorize[current].size() != 1) {
                    return NG;
                }
                ltwo[current][0] = categorize[current].get(0);
                ltwo[current][1] = -1;
            } else {
                if (categorize[current].size() <= 1) {
                    return NG;
                }
                ltwo[current][0] = categorize[current].get(0);
                ltwo[current][1] = categorize[current].get(1);
            }
        }

        int ei = 0;
        ans = new int[(n-1)*2];
        ai = 0;
        for (int i = current ; i+1 <= max ; i++) {
            if (i == current && odd) {
                addEdge(ltwo[i][0], ltwo[i+1][0]);
                addEdge(ltwo[i][0], ltwo[i+1][1]);
            } else {
                if (i == current) {
                    addEdge(ltwo[i][0], ltwo[i][1]);
                }
                addEdge(ltwo[i][0], ltwo[i+1][0]);
                addEdge(ltwo[i][1], ltwo[i+1][1]);
            }
        }

        for (int i = current+1 ; i <= max ; i++) {
            for (int k = 2 ; k < categorize[i].size() ; k++) {
                addEdge(categorize[i].get(k), ltwo[i-1][0]);
            }
        }

        if (ai != ans.length) {
            return NG;
        }

        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < ans.length / 2 ; i++) {
            int a = ans[i*2];
            int b = ans[i*2+1];
            graph[a].add(b);
            graph[b].add(a);
        }

        for (int i = 0 ; i < n ; i++) {
            if (d[i] != farthest(graph, i, -1)) {
                return NG;
            }
        }
        return ans;
    }



    private int farthest(List<Integer>[] graph, int now, int par) {
        int max = 0;
        for (int to : graph[now]) {
            if (to == par) {
                continue;
            }
            max = Math.max(max, farthest(graph, to, now)+1);
        }
        return max;
    }

    public int[] ans;
    public int ai;

    public void addEdge(int a, int b) {
        ans[ai++] = a;
        ans[ai++] = b;
    }

    public static void main(String[] args) {
        TreeDistanceConstruction ho = new TreeDistanceConstruction();
        debug(ho.construct(new int[]{4,4,4,3,3,3,2}));

    }

    public static void debug(Object... obj) {
        System.err.println(Arrays.deepToString(obj));
    }
}
