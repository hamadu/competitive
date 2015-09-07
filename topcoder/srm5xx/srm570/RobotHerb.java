package topcoder.srm5xx.srm570;

/**
 * Created by hama_du on 15/09/04.
 */
public class RobotHerb {
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};

    public long getdist(int T, int[] a) {
        long nx = 0;
        long ny = 0;
        int dir = 0;
        for (int i = 0; i < 4 ; i++) {
            for (int j = 0; j < a.length ; j++) {
                nx += a[j] * dx[dir];
                ny += a[j] * dy[dir];
                dir = (dir + a[j]) % 4;
            }
        }
        long cur = T / 4;
        long fx = nx * cur;
        long fy = ny * cur;
        dir = 0;
        for (int i = 0; i < T % 4; i++) {
            for (int j = 0; j < a.length ; j++) {
                fx += a[j] * dx[dir];
                fy += a[j] * dy[dir];
                dir = (dir + a[j]) % 4;
            }
        }
        return Math.abs(fx) + Math.abs(fy);
    }

}
