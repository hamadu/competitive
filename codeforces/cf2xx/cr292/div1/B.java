package codeforces.cf2xx.cr292.div1;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class B {

    static int[] dx = {1, 0, 0, -1};
    static int[] dy = {0, 1, -1, 0};

    static char[][] map;

    private static int deg(int y, int x) {
        int deg = 0 ;
        for (int d = 0; d < 4; d++) {
            int ty = y + dy[d];
            int tx = x + dx[d];
            if (map[ty][tx] == '.') {
                deg++;
            }
        }
        return deg;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();
        int m = in.nextInt();
        map = new char[n+2][m+2];
        for (int i = 1; i <= n; i++) {
            char[] c = in.next().toCharArray();
            for (int j = 1 ; j <= m ; j++) {
                map[i][j] = c[j-1];
            }
            map[i][0] = map[i][m+1] = '*';
        }
        Arrays.fill(map[0], '*');
        Arrays.fill(map[n+1], '*');

        boolean[][] mark = new boolean[2010][2010];
        int[] que = new int[2010*2010*2];
        int qh = 0;
        int qt = 0;
        for (int i = 1; i <= n ; i++) {
            for (int j = 1; j <= m; j++) {
                if (map[i][j] != '.') {
                    continue;
                }
                if (deg(i, j) == 1) {
                    mark[i][j] = true;
                    que[qh++] = i;
                    que[qh++] = j;
                }
            }
        }

        while (qt < qh) {
            int y1 = que[qt++];
            int x1 = que[qt++];
            for (int d = 0 ; d < 4 ; d++) {
                int y2 = y1+dy[d];
                int x2 = x1+dx[d];
                if (map[y2][x2] != '.') {
                    continue;
                }
                int fx = Math.min(x1, x2);
                int tx = x1+x2-fx;
                int fy = Math.min(y1, y2);
                int ty = y1+y2-fy;
                if (fx != tx) {
                    map[y1][fx] = '<';
                    map[y1][tx] = '>';
                } else {
                    map[fy][x1] = '^';
                    map[ty][x1] = 'v';
                }
                for (int nd = 0 ; nd < 4 ; nd++) {
                    int tty = y2+dy[nd];
                    int ttx = x2+dx[nd];
                    if (map[tty][ttx] != '.') {
                        continue;
                    }
                    if (deg(tty, ttx) == 1 && !mark[tty][ttx]) {
                        mark[tty][ttx] = true;
                        que[qh++] = tty;
                        que[qh++] = ttx;
                    }
                }
            }
        }

        boolean existAndUnique = true;
        for (int i = 1; i <= n ; i++) {
            for (int j = 1; j <= m; j++) {
                if (map[i][j] == '.') {
                    existAndUnique = false;
                }
            }
        }

        if (existAndUnique) {
            for (int i = 1; i <= n ; i++) {
                for (int j = 1; j <= m; j++) {
                    out.print(map[i][j]);
                }
                out.println();
            }
        } else {
            out.println("Not unique");
        }
        out.flush();
    }

    static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
