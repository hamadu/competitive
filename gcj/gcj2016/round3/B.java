package gcj.gcj2016.round3;

import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by hama_du on 2016/06/10.
 */
public class B {
    static SecureRandom rnd = new SecureRandom();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = in.nextInt();
        for (int cs = 1 ; cs <= t ; cs++) {
            int n = in.nextInt();
            int[][] courses = new int[n][2];
            for (int i = 0; i < n ; i++) {
                courses[i][0] = in.nextInt()-1;
            }
            char[] w = in.next().toCharArray();
            for (int i = 0; i < n ; i++) {
                courses[i][1] = w[i];
            }
            int m = in.nextInt();
            char[][] cool = new char[m][];
            for (int i = 0; i < m ; i++) {
                cool[i] = in.next().toCharArray();
            }

            double[] ans = solve(courses, cool);
            StringBuilder line = new StringBuilder();
            for (double d : ans) {
                line.append(' ').append(String.format("%.9f", d));
            }
            out.println(String.format("Case #%d: %s", cs, line.substring(1)));
        }
        out.flush();
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }


    static boolean[] visited;
    static List<Integer>[] graph;
    static int[] cnt;

    static int dfs0(int now) {
        visited[now] = true;
        cnt[now] = 1;
        for (int to : graph[now]) {
            cnt[now] += dfs0(to);
        }
        return cnt[now];
    }

    private static double[] solve(int[][] course, char[][] cool) {
        int n = course.length;
        int m = cool.length;

        graph = new List[n];
        for (int i = 0; i < n ; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0 ; i < n ; i++) {
            if (course[i][0] != -1) {
                graph[course[i][0]].add(i);
            }
        }

        cnt = new int[n];
        visited = new boolean[n];
        for (int i = 0; i < n ; i++) {
            if (!visited[i]) {
                dfs0(i);
            }
        }

        String[] cs = new String[m];
        for (int i = 0; i < m ; i++) {
            cs[i] = String.valueOf(cool[i]);
        }

        int MAX = 5000;
        int[] stat = new int[m];
        for (int frac = 0 ; frac < MAX ; frac++) {
            char[] c = new char[n];
            int[] okay = new int[n];
            int oi = 0;
            Arrays.fill(okay, -1);
            for (int i = 0; i < n ; i++) {
                if (course[i][0] == -1) {
                    okay[oi++] = i;
                }
            }
            for (int i = 0; i < n ; i++) {
                int sum = 0;
                for (int j = 0; j < oi ; j++) {
                    if (okay[j] != -1) {
                        sum += cnt[okay[j]];
                    }
                }
                int pick = rnd.nextInt(sum);
                int pi = -1;
                int chosen = -1;
                for (int j = 0; j < oi ; j++) {
                    if (okay[j] != -1) {
                        if (pick < cnt[okay[j]]) {
                            pi = j;
                            chosen = okay[j];
                            break;
                        } else {
                            pick -= cnt[okay[j]];
                        }
                    }
                }
                okay[pi] = -1;
                c[i] = (char)(course[chosen][1]);
                for (int next : graph[chosen]) {
                    okay[oi++] = next;
                }
            }
            String sl = String.valueOf(c);
            for (int i = 0 ; i < m ; i++) {
                if (sl.contains(cs[i])) {
                    stat[i]++;
                }
            }
        }

        double[] ans = new double[m];
        for (int i = 0; i < m ; i++) {
            ans[i] = stat[i] * 1.0d / MAX;
        }
        return ans;
    }
}
