package topcoder.srm6xx.srm663;

/**
 * Created by hama_du on 15/08/12.
 */
public class ABBADiv1 {
    public String canObtain(String initial, String target) {
        memo = new int[100][100][2];
        I = initial;
        T = target;
        return dfs(0, target.length()-1, 0) ? "Possible" : "Impossible";
    }

    int[][][] memo;
    String I;
    String T;

    public boolean dfs(int fr, int to, int rev) {
        if (memo[fr][to][rev] != 0) {
            return memo[fr][to][rev] == 1;
        }
        String sub = T.substring(fr, to+1);
        if (rev == 1) {
            sub = new StringBuilder(sub).reverse().toString();
        }
        if (I.equals(sub)) {
            memo[fr][to][rev] = 1;
            return true;
        }
        if (sub.length() < I.length()) {
            memo[fr][to][rev] = -1;
            return false;
        }

        boolean ok = false;
        if (sub.charAt(sub.length()-1) == 'A') {
            if (rev == 0) {
                ok |= dfs(fr, to-1, rev);
            } else {
                ok |= dfs(fr+1, to, rev);
            }
        }
        if (sub.charAt(0) == 'B') {
            if (rev == 0) {
                ok |= dfs(fr+1, to, 1-rev);
            } else {
                ok |= dfs(fr, to-1, 1-rev);
            }
        }
        memo[fr][to][rev] = ok ? 1 : -1;
        return ok;
    }
}
