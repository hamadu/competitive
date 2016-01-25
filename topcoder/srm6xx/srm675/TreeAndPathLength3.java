package topcoder.srm6xx.srm675;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hama_du on 2015/12/10.
 */
public class TreeAndPathLength3 {

    List<Integer> d = new ArrayList<>();
    public void add(int u, int v) {
        d.add(u);
        d.add(v);
    }

    public int[] construct(int s) {
        d.clear();
        if (s >= 3) {
            add(0, 1);
            add(1, 2);
            add(0, 3);
            add(3, 4);

            int cur = 5;
            int cnt = 2;
            int k = 2;
            while (s - cnt > 0) {
                if (s-k*(k+1) >= 0) {
                    k ++;
                    add(0, cur);
                    add(cur,2*k);
                    cur+=2;
                    cnt = k*(k-1);
                } else {
                    if (s-cnt >= k) {
                        add(0, cur);
                        cur++;
                        cnt+=k;
                    }
                    for (int i = 1;i<=k&&s-cnt>0;i++) {
                        add(2*k,cur++);
                        cnt++;
                    }
                    break;
                }
            }
        }

        int[] res = new int[d.size()];
        for (int i = 0 ; i < d.size();i++) {
            res[i] = d.get(i);
        }
        return res;
    }

    public int[] construct2(int s) {
        for (int one = 1 ; one <= 200 ; one++) {
            for (int two = one ; two <= 200 ; two++) {
                int num = (one - 1) * two;
                for (int level = 0 ; level <= 100; level++) {
                    if (level == 1) {
                        num += two;
                    } else if (level == 2) {
                        num += one;
                    } else if (level >= 3) {
                        num++;
                    }
                    if (one + two + 1 + level > 500) {
                        break;
                    }
                    if (num == s) {
                        return buildIt(one, two, level);
                    }
                }
            }
        }
        throw new RuntimeException("not found");
    }

    private int[] buildIt(int one, int two, int level) {
        debug(one,two,level);

        int n = one + two + 1 + level;
        int[] ret = new int[2*n-2];
        int idx = 0;
        for (int i = 1 ; i <= one ; i++) {
            ret[idx++] = 0;
            ret[idx++] = i;
        }
        for (int i = one+1 ; i <= one+two ; i++) {
            ret[idx++] = 1;
            ret[idx++] = i;
        }
        int last = 0;
        for (int i = one+two+1 ; i <= one+two+level ; i++) {
            ret[idx++] = last;
            ret[idx++] = i;
            last = i;
        }
        return ret;
    }

    public static void check(int[] edges, int s) {
        int n = (edges.length+2)/2;

        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0 ; i < edges.length ; i += 2) {
            graph[edges[i]].add(edges[i+1]);
            graph[edges[i+1]].add(edges[i]);
        }

        int sum = 0;
        for (int i = 0 ; i < n; i++) {
            sum += dfs(graph, i, -1, 0);
        }
        sum /= 2;

        if (sum != s) {
            throw new RuntimeException("whoa:" + s);
        }
    }

    private static int dfs(List<Integer>[] graph, int now, int par, int depth) {
        if (depth == 3) {
            return 1;
        }
        int ret = 0;
        for (int to : graph[now]) {
            if (to != par) {
                ret += dfs(graph, to, now, depth+1);
            }
        }
        return ret;
    }


    public static void main(String[] args) {
        TreeAndPathLength3 solution = new TreeAndPathLength3();
        // debug(solution.construct(1));

        for (int s = 3 ; s <= 10000 ; s++) {
            debug(s);
            int[] ret = solution.construct(s);
            check(ret, s);
        }
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
