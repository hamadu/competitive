package aoj.vol12;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hama_du on 15/07/31.
 */
public class P1281 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        while (true) {
            int w = in.nextInt();
            int h = in.nextInt();
            int n = in.nextInt();
            if (w + h + n == 0) {
                break;
            }
            int ghosts = 0;
            int goals = 0;
            in.nextLine();

            char[][] map = new char[h][];
            for (int i = 0; i < h ; i++) {
                map[i] = in.nextLine().toCharArray();
                for (int j = 0; j < w; j++) {
                    if ('a' <= map[i][j] && map[i][j] <= 'c') {
                        ghosts |= (i * 16 + j) << ((map[i][j] - 'a') * 8);
                        map[i][j] = ' ';
                    } else if ('A' <= map[i][j] && map[i][j] <= 'C') {
                        goals |= (i * 16 + j) << ((map[i][j] - 'A') * 8);
                        map[i][j] = ' ';
                    }
                }
            }
            if (n < 2) {
                ghosts |= 254<<8;
                goals |= 254<<8;
            }
            if (n < 3) {
                ghosts |= 255<<16;
                goals |= 255<<16;
            }
            out.println(solve(map, ghosts, goals));
        }
        out.flush();
    }

    private static int solve(char[][] map, int ghosts, int goals) {
        int[][] table = new int[256][5];
        int n = map.length;
        int m = map[0].length;
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                if (map[i][j] != ' ') {
                    continue;
                }
                for (int d = 0; d < 5; d++) {
                    int ti = i + dy[d];
                    int tj = j + dx[d];
                    if (ti < 0 || tj < 0 || ti >= n || tj >= m || map[ti][tj] != ' ') {
                        table[i*16+j][d] = -1;
                    } else {
                        table[i*16+j][d] = ti*16+tj;
                    }

                }
            }
        }
        Arrays.fill(table[254], 254);
        Arrays.fill(table[255], 255);

        Arrays.fill(dp, -1);
        int qh = 0;
        int qt = 0;
        dp[ghosts] = 0;
        queue[qh++] = ghosts;
        queue[qh++] = 0;

        int cr = 0;
        while (qt < qh) {
            cr++;
            int stat = queue[qt++];
            int time = queue[qt++] + 1;
            int g1 = stat & 255;
            int g2 = (stat >> 8) & 255;
            int g3 = (stat >> 16) & 255;
            for (int d1 = 0; d1 < 5; d1++) {
                for (int d2 = 0; d2 < 5 ; d2++) {
                    for (int d3 = 0; d3 < 5 ; d3++) {
                        int t1 = table[g1][d1];
                        int t2 = table[g2][d2];
                        int t3 = table[g3][d3];
                        if (t1 == -1 || t2 == -1 || t3 == -1) {
                            continue;
                        }
                        if (t1 == t2 || t2 == t3 || t1 == t3) {
                            continue;
                        }
                        if (t1 == g2 && g1 == t2) {
                            continue;
                        }
                        if (t1 == g3 && g1 == t3) {
                            continue;
                        }
                        if (t2 == g3 && g2 == t3) {
                            continue;
                        }
                        int tstat = t1 | (t2 << 8) | (t3 << 16);
                        if (tstat == goals) {
                            return time;
                        }
                        if (dp[tstat] == -1) {
                            dp[tstat] = time;
                            queue[qh++] = tstat;
                            queue[qh++] = time;
                        }
                    }
                }
            }
        }
        return -1;
    }

    static int[] dx = {-1, 0, 1, 0, 0};
    static int[] dy = {0, 1, 0, -1, 0};

    static int[] dp = new int[1<<24];

    static int[] queue = new int[1<<24];

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
