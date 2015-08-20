package topcoder.srm5xx.srm525;

/**
 * Created by hama_du on 15/08/17.
 */
public class Rumor {
    public int getMinimum(String knowledge, String[] rel) {
        int n = knowledge.length();
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                graph[i][j] = rel[i].charAt(j) == 'Y';
            }
        }

        long want = ((1L<<(2L*n))-1);
        int min = Integer.MAX_VALUE;
        for (int l = 0; l < (1<<n) ; l++) {
            long[][] ord = new long[n][2];
            for (int i = 0; i < n ; i++) {
                ord[i][0] = (l & (1<<i)) == 0 ? 1 : 2;
                ord[i][1] = 3 - ord[i][0];
            }

            long flg = 0;
            for (int i = 0; i < n ; i++) {
                if (knowledge.charAt(i) == 'Y') {
                    flg |= 3L<<(i*2);
                }
            }
            int day = 0;
            long done = 0;
            boolean upd = true;
            while (upd && flg != want) {
                upd = false;
                long toFlg = flg;
                long toDone = done;
                for (int i = 0; i < n ; i++) {
                    long dn = (done >> (i*2)) & 3;
                    long nw = (flg >> (i*2)) & 3;
                    for (int f = 0; f <= 1; f++) {
                        if ((ord[i][f] & nw) >= 1 && (ord[i][f] & dn) == 0) {
                            toDone |= ord[i][f]<<(2*i);
                            for (int j = 0; j < n ; j++) {
                                if (graph[i][j]) {
                                    toFlg |= ord[i][f]<<(2*j);
                                }
                            }
                            upd = true;
                            break;
                        }
                    }
                }
                if (!upd) {
                    break;
                }
                flg = toFlg;
                done = toDone;
                day++;
            }
            if (flg == want) {
                min = Math.min(min, day);
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }
}
