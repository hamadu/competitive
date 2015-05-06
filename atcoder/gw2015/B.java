package atcoder.gw2015;

import java.util.*;

/**
 * Created by dhamada on 15/05/07.
 */
public class B {

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long p = in.nextLong();

        int SZ = 4000;
        int[][] field = new int[SZ][SZ];
        for (int i = 0 ; i < SZ ; i++) {
            Arrays.fill(field[i], 1);
        }
        int x = SZ / 2;
        int y = SZ / 2;
        int dir = 0;
        int ans = 0;

        List<Integer> cl = new ArrayList<>();
        for (long i = 1 ; i <= p ; i++) {
            dir = (dir + 4 + field[y][x]) % 4;
            field[y][x] *= -1;
            x += dx[dir];
            y += dy[dir];
            if (i >= 20000) {
                if (i % 104 == p % 104) {
                    ans = (field[y][x] == 1) ? 0 : 1;
                    break;
                }
            }
            if (i == p) {
                ans = (field[y][x] == 1) ? 0 : 1;
            }
        }
        System.out.println(ans);
    }
}
