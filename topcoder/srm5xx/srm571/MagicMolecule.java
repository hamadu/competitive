package topcoder.srm5xx.srm571;

/**
 * Created by hama_du on 15/09/04.
 */
public class MagicMolecule {
    public int maxMagicPower(int[] magicPower, String[] magicBond) {
        n = magicBond.length;
        int[] ord = new int[n];
        for (int i = 0; i < n ; i++) {
            ord[i] = i;
        }
        for (int i = 1 ; i < n ; i++) {
            int tmp = (int)(Math.random() * i);
            int t = ord[tmp];
            ord[tmp] = ord[i];
            ord[i] = t;
        }

        revGraph = new boolean[n][n];
        revDeg = new int[n];
        for (int i = 0; i < n ; i++) {
            for (int j = i+1; j < n; j++) {
                if (magicBond[ord[i]].charAt(ord[j]) == 'N') {
                    revGraph[i][j] = revGraph[j][i] = true;
                    revDeg[i]++;
                    revDeg[j]++;
                }
            }
        }


        atLeast = 0;
        while (atLeast * 3 < n * 2) {
            atLeast++;
        }
        power = new int[n];
        for (int i = 0; i < n ; i++) {
            power[i] = magicPower[ord[i]];
        }

        int score = 0;
        for (int i = 0; i < n ; i++) {
            score += power[i];
        }

        dfs((1L << n)-1, 0, score);
        return best;
    }

    int n;
    int atLeast;
    boolean[][] revGraph;
    int[] power;
    int[] revDeg;
    int best = -1;

    public void dfs(long alive, int head, int score) {
        if (Long.bitCount(alive) < atLeast || score <= best) {
            return;
        }
        if (head == n) {
            best = Math.max(best, score);
            return;
        }

        boolean canPick = true;
        for (int i = 0; i < head ; i++) {
            if ((alive & (1L<<i)) >= 1 && revGraph[i][head]) {
                canPick = false;
                break;
            }
        }
        if (canPick) {
            dfs(alive, head+1, score);
        }
        if (revDeg[head] >= 1) {
            for (int i = head+1 ; i < n ; i++) {
                if (revGraph[head][i]) {
                    revDeg[i]--;
                }
            }
            dfs(alive^(1L<<head), head+1, score-power[head]);
            for (int i = head+1 ; i < n ; i++) {
                if (revGraph[head][i]) {
                    revDeg[i]++;
                }
            }
        }
    }
}
