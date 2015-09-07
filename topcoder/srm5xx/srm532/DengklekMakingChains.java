package topcoder.srm5xx.srm532;

/**
 * Created by hama_du on 15/08/23.
 */
public class DengklekMakingChains {
    public int maxBeauty(String[] chains) {
        int all = 0;
        int left = 0;
        int right = 0;
        int center = 0;

        int n = chains.length;
        boolean[] used = new boolean[n+1];
        for (int ci = 0; ci  < n ; ci++) {
            String line = chains[ci];
            char[] c = line.toCharArray();
            if (!line.contains(".")) {
                for (int i = 0; i < 3 ; i++) {
                    all += c[i] - '0';
                }
                used[ci] = true;
            }
        }

        int ret = all;
        for (int i = 0; i <= n ; i++) {
            for (int j = 0; j <= n ; j++) {
                if (used[i] || used[j] || i == j) {
                    continue;
                }
                int score = 0;
                if (i < n) {
                    for (int k = 2; k >= 0; k--) {
                        if (chains[i].charAt(k) == '.') {
                            break;
                        }
                        score += chains[i].charAt(k)-'0';
                    }
                }
                if (j < n) {
                    for (int k = 0; k <= 2; k++) {
                        if (chains[j].charAt(k) == '.') {
                            break;
                        }
                        score += chains[j].charAt(k)-'0';
                    }
                }
                ret = Math.max(ret, all+score);
            }
        }

        for (int i = 0; i < n ; i++) {
            int sc = 0;
            for (int j = 0; j < 3 ; j++) {
                if (chains[i].charAt(j) == '.') {
                    sc = 0;
                } else {
                    sc += chains[i].charAt(j) - '0';
                    ret = Math.max(ret, sc);
                }
            }
        }
        return ret;
    }
}
