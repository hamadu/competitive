package topcoder.srm5xx.srm509;

import java.util.Arrays;

/**
 * Created by hama_du on 15/08/05.
 */
public class PalindromizationDiv1 {
    private static final long INF = (long)1e15;

    public int getMinimumCost(String word, String[] operations) {
        wd = word.toCharArray();
        n = word.length();
        memo = new long[n][n+1];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(memo[i], INF);
        }
        visited = new boolean[n][n+1];

        add = new long[26];
        erase = new long[26];
        change = new long[26][26];
        Arrays.fill(add, INF);
        Arrays.fill(erase, INF);
        for (int i = 0; i < 26; i++) {
            Arrays.fill(change[i], INF);
            change[i][i] = 0;
        }
        for (String op : operations) {
            String[] part = op.split(" ");
            if ("add".equals(part[0])) {
                int idx = part[1].charAt(0) - 'a';
                add[idx] = Math.min(add[idx], Integer.valueOf(part[2]));
            } else if ("erase".equals(part[0])) {
                int idx = part[1].charAt(0) - 'a';
                erase[idx] = Math.min(erase[idx], Integer.valueOf(part[2]));
            } else {
                int fr = part[1].charAt(0) - 'a';
                int to = part[2].charAt(0) - 'a';
                change[fr][to] = Math.min(change[fr][to], Integer.valueOf(part[3]));
            }
        }
        for (int k = 0; k < 26; k++) {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    change[i][j] = Math.min(change[i][j], change[i][k] + change[k][j]);
                }
            }
        }
        long co = dfs(0, n-1);
        return co >= INF ? -1 : (int)co;
    }

    long dfs(int fr, int to) {
        if (to - fr <= 0) {
            return 0;
        }
        if (visited[fr][to]) {
            return memo[fr][to];
        }
        int left = wd[fr]-'a';
        int right = wd[to]-'a';
        long cost = INF;

        // they are same
        if (left == right) {
            cost = Math.min(cost, dfs(fr+1, to-1));
        }

        // delete left or right
        for (int k = 0; k < 26 ; k++) {
            cost = Math.min(cost, dfs(fr+1, to) + change[left][k] + erase[k]);
            cost = Math.min(cost, dfs(fr, to-1) + change[right][k] + erase[k]);
        }

        // add left and change right
        // add right and change left
        for (int a = 0; a < 26 ; a++) {
            for (int k = 0; k < 26 ; k++) {
                cost = Math.min(cost, dfs(fr, to-1) + add[a] + change[a][k] + change[right][k]);
                cost = Math.min(cost, dfs(fr+1, to) + add[a] + change[a][k] + change[left][k]);
            }
        }

        // change both
        for (int k = 0; k < 26 ; k++) {
            cost = Math.min(cost, dfs(fr+1, to-1) + change[left][k] + change[right][k]);
        }

        visited[fr][to] = true;
        memo[fr][to] = cost;
        return cost;
    }

    int n;
    char[] wd;
    long[] add;
    long[] erase;
    long[][] change;
    boolean[][] visited;

    long[][] memo;
}
