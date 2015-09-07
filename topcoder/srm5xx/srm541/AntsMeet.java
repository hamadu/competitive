package topcoder.srm5xx.srm541;

/**
 * Created by hama_du on 15/08/24.
 */
public class AntsMeet {
    int[] dx = new int[256];
    int[] dy = new int[256];

    public int countAnts(int[] x, int[] y, String direction) {
        int n = x.length;
        for (int i = 0; i < n ; i++) {
            x[i] *= 2;
            y[i] *= 2;
        }
        char[] c = direction.toCharArray();
        dx['W'] = -1;
        dx['E'] = 1;
        dy['S'] = -1;
        dy['N'] = 1;
        boolean[] gone = new boolean[n];
        for (int cur = 0; cur < 6000 ; cur++) {
            boolean[] tgone = gone.clone();
            for (int i = 0; i < n ; i++) {
                for (int j = i+1 ; j < n ; j++) {
                    if (gone[i] || gone[j]) {
                        continue;
                    }
                    if (x[i] == x[j] && y[i] == y[j]) {
                        tgone[i] = tgone[j] = true;
                    }
                }
            }
            gone = tgone;
            for (int i = 0; i < n ; i++) {
                x[i] += dx[c[i]];
                y[i] += dy[c[i]];
            }
        }

        int ct = 0;
        for (int i = 0; i < n ; i++) {
            if (!gone[i]) {
                ct++;
            }
        }
        return ct;
    }
}
